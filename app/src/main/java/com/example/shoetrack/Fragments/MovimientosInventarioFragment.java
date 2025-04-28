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
        // Required empty public constructor
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

        // Inicializa los componentes
        spinnerProducto = view.findViewById(R.id.spinnerProducto);
        spinnerTipoMovimiento = view.findViewById(R.id.spinnerTipoMovimiento);
        txtCantidad = view.findViewById(R.id.txtCantidad);
        txtCostoUnitario = view.findViewById(R.id.txtCostoUnitario);
        txtFecha = view.findViewById(R.id.txtFecha);
        btnGuardar = view.findViewById(R.id.btnGuardarMovimiento);

        // Llenar el spinner con los productos
        cargarProductosEnSpinner();

        // Evento al dar clic en el botón "Guardar"
        btnGuardar.setOnClickListener(v -> guardarMovimiento());

        // Configuración de DatePicker para el campo de fecha
        EditText txtFecha = view.findViewById(R.id.txtFecha);
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
        // Obtener la lista de productos desde la base de datos
        List<Productos> productos = db.productosDao().getAllProductos();

        // Crear un ArrayAdapter para el spinner
        ArrayAdapter<Productos> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducto.setAdapter(adapter);
    }

    private void guardarMovimiento() {
        // Obtener los valores del formulario
        Productos productoSeleccionado = (Productos) spinnerProducto.getSelectedItem();
        String cantidadStr = txtCantidad.getText().toString();
        String costoUnitarioStr = txtCostoUnitario.getText().toString();
        String fecha = txtFecha.getText().toString();

        // Obtener el tipo de movimiento seleccionado
        String tipoMovimiento = spinnerTipoMovimiento.getSelectedItem().toString();

        if (cantidadStr.isEmpty() || costoUnitarioStr.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);
        double costoUnitario = Double.parseDouble(costoUnitarioStr);

        // Crear un nuevo movimiento
        MovimientoIventario movimiento = new MovimientoIventario();
        movimiento.idProducto = productoSeleccionado.getIdProducto();
        movimiento.cantidad = cantidad;
        movimiento.costoUnitario = costoUnitario;
        movimiento.fecha = fecha;
        movimiento.tipo = tipoMovimiento;  // Asignar el tipo de movimiento (ENTRADA o SALIDA)

        // Guardar el movimiento en la base de datos
        db.movimientoInventarioDAO().insertMovimineto(movimiento);

        // Si es una ENTRADA, actualizar el costo promedio
        if (tipoMovimiento.equals("ENTRADA")) {
            actualizarCostoPromedio(productoSeleccionado, cantidad, costoUnitario);
        } else {
            actualizarStock(productoSeleccionado, cantidad); // Si es salida, solo actualizar el stock
        }

        Toast.makeText(getContext(), "Movimiento guardado exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void actualizarCostoPromedio(Productos producto, int cantidad, double costoUnitario) {
        // Obtener los movimientos de inventario para el producto
        List<MovimientoIventario> movimientos = db.movimientoInventarioDAO().getAllMovimientos(producto.getIdProducto());

        // Calcular el costo promedio
        double totalCosto = 0;
        int totalCantidad = 0;

        for (MovimientoIventario movimiento : movimientos) {
            totalCosto += movimiento.costoUnitario * movimiento.cantidad;
            totalCantidad += movimiento.cantidad;
        }

        if (totalCantidad > 0) {
            double costoPromedio = totalCosto / totalCantidad;
            // Actualizar el costo promedio del producto en la base de datos
            producto.costo_promedio = costoPromedio;
            db.productosDao().updateProductos(producto);
        }
    }



}
