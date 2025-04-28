package com.example.shoetrack.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.InventariosActual;
import com.example.shoetrack.Moduls.MovimientoIventario;
import com.example.shoetrack.Moduls.Productos;
import com.example.shoetrack.R;

import java.util.Calendar;
import java.util.List;

public class MovimientosInventarioFragment extends Fragment {

    private Spinner spinnerProducto, spinnerTipoMovimiento;
    private EditText txtCantidad, txtCostoUnitario, txtFecha;
    private ImageButton btnGuardar;
    private AppDatabase db;

    public MovimientosInventarioFragment() {
        // Constructor vacío requerido
    }

    public static MovimientosInventarioFragment newInstance(String param1, String param2) {
        MovimientosInventarioFragment fragment = new MovimientosInventarioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movimientos_inventario, container, false);

        // Inicializa componentes
        spinnerProducto = view.findViewById(R.id.spinnerProducto);
        spinnerTipoMovimiento = view.findViewById(R.id.spinnerTipoMovimiento);
        txtCantidad = view.findViewById(R.id.txtCantidad);
        txtCostoUnitario = view.findViewById(R.id.txtCostoUnitario);
        txtFecha = view.findViewById(R.id.txtFecha);
        //btnGuardar = view.findViewById(R.id.btnGuardarMovimiento);

        cargarProductosEnSpinner();
        cargarTiposMovimiento();

        btnGuardar.setOnClickListener(v -> guardarMovimiento());

        Calendar calendar = Calendar.getInstance();
        txtFecha.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year1, month1, dayOfMonth) -> {
                String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month1 + 1, year1);
                txtFecha.setText(fechaSeleccionada);
            }, year, month, day);
            datePickerDialog.show();
        });

        return view;
    }

    private void cargarProductosEnSpinner() {
        List<Productos> productos = db.productosDAO().getAllProductos();
        ArrayAdapter<Productos> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducto.setAdapter(adapter);
    }

    private void cargarTiposMovimiento() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"ENTRADA", "SALIDA"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoMovimiento.setAdapter(adapter);
    }

    private void guardarMovimiento() {
        Productos productoSeleccionado = (Productos) spinnerProducto.getSelectedItem();
        String cantidadStr = txtCantidad.getText().toString();
        String costoUnitarioStr = txtCostoUnitario.getText().toString();
        String fecha = txtFecha.getText().toString();
        String tipoMovimiento = spinnerTipoMovimiento.getSelectedItem().toString();

        if (productoSeleccionado == null || cantidadStr.isEmpty() || costoUnitarioStr.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);
        double costoUnitario = Double.parseDouble(costoUnitarioStr);

        // Guardar movimiento
        MovimientoIventario movimiento = new MovimientoIventario();
        movimiento.idProducto = productoSeleccionado.getIdProducto();
        movimiento.cantidad = cantidad;
        movimiento.costoUnitario = costoUnitario;
        movimiento.fecha = fecha;
        movimiento.tipo = tipoMovimiento;

        db.moviminetoInventarioDAO().insertMovimineto(movimiento);

        // Actualizar inventario
        InventariosActual inventarioActual = db.inventarioActualDAO().getInventarioPorProductoId(productoSeleccionado.getIdProducto());

        if (inventarioActual == null) {
            inventarioActual = new InventariosActual();
            inventarioActual.setIdProducto(productoSeleccionado.getIdProducto());
            inventarioActual.setStock(0);
            inventarioActual.setCostoPromedio(0);
            db.inventarioActualDAO().insertInventario(inventarioActual);
        }

        if (tipoMovimiento.equals("ENTRADA")) {
            actualizarCostoPromedio(inventarioActual, cantidad, costoUnitario);
        } else if (tipoMovimiento.equals("SALIDA")) {
            actualizarStock(inventarioActual, cantidad);
        }

        Toast.makeText(getContext(), "Movimiento guardado exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void actualizarCostoPromedio(InventariosActual inventario, int cantidad, double costoUnitario) {
        int stockAnterior = inventario.getStock();
        double costoPromedioAnterior = inventario.getCostoPromedio();

        double totalCostoAnterior = stockAnterior * costoPromedioAnterior;
        double totalCostoNuevo = cantidad * costoUnitario;

        int nuevoStock = stockAnterior + cantidad;
        if (nuevoStock == 0) {
            inventario.setCostoPromedio(0);
        } else {
            double nuevoCostoPromedio = (totalCostoAnterior + totalCostoNuevo) / nuevoStock;
            inventario.setCostoPromedio(nuevoCostoPromedio);
        }
        inventario.setStock(nuevoStock);

        db.inventarioActualDAO().updateInventario(inventario);
    }

    private void actualizarStock(InventariosActual inventario, int cantidad) {
        int nuevoStock = inventario.getStock() - cantidad;
        if (nuevoStock < 0) nuevoStock = 0;

        inventario.setStock(nuevoStock);

        db.inventarioActualDAO().updateInventario(inventario);
    }
}
