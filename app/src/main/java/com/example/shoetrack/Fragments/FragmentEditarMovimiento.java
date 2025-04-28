package com.example.shoetrack.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shoetrack.DAOs.MoviminetoInventarioDAO;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.MovimientoIventario;
import com.example.shoetrack.Moduls.Productos;
import com.example.shoetrack.R;
import java.util.ArrayList;
import java.util.List;

public class FragmentEditarMovimiento extends Fragment {

    private Spinner spProducto, spTipoMovimiento;
    private EditText txtCantidad, txtCostoUnitario, txtFecha;
    private Button btnActualizarMovimiento;
    private TextView lblCancelarMovimiento;
    private AppDatabase db;
    private MoviminetoInventarioDAO moviminetoInventarioDAO;
    private MovimientoIventario movimientoEditar;

    public FragmentEditarMovimiento() {
        // Constructor vacío requerido
    }

    public static FragmentEditarMovimiento newInstance(MovimientoIventario movimiento) {
        FragmentEditarMovimiento fragment = new FragmentEditarMovimiento();
        Bundle args = new Bundle();
        args.putSerializable("movimiento", movimiento); // Movimiento debe ser Serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movimientoEditar = (MovimientoIventario) getArguments().getSerializable("movimiento");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_movimiento, container, false);

        // Inicializar vistas
        spProducto = view.findViewById(R.id.spinnerProductoEditar);
        spTipoMovimiento = view.findViewById(R.id.spinnerTipoMovimientoEditar);
        txtCantidad = view.findViewById(R.id.txtCantidadEditar);
        txtCostoUnitario = view.findViewById(R.id.txtCostoUnitarioEditar);
        txtFecha = view.findViewById(R.id.txtFechaEditar);
        btnActualizarMovimiento = view.findViewById(R.id.btnActualizarMovimiento);
        lblCancelarMovimiento = view.findViewById(R.id.lblCancelarEdicion);

        db = AppDatabase.getInstance(getContext());
        moviminetoInventarioDAO = db.movimientoInventarioDAO();

        cargarProductos();
        cargarTiposMovimiento();

        if (movimientoEditar != null) {
            spProducto.setSelection(getProductoPosition(movimientoEditar.getIdProducto()));
            spTipoMovimiento.setSelection(getTipoMovimientoPosition(movimientoEditar.getTipo()));
            txtCantidad.setText(String.valueOf(movimientoEditar.getCantidad()));
            txtCostoUnitario.setText(String.valueOf(movimientoEditar.getCostoUnitario()));
            txtFecha.setText(movimientoEditar.getFecha());
        }

        btnActualizarMovimiento.setOnClickListener(v -> actualizarMovimiento());
        lblCancelarMovimiento.setOnClickListener(v -> getFragmentManager().popBackStack());

        return view;
    }

    private void actualizarMovimiento() {
        String cantidadStr = txtCantidad.getText().toString().trim();
        String costoStr = txtCostoUnitario.getText().toString().trim();
        String fecha = txtFecha.getText().toString().trim();

        if (cantidadStr.isEmpty() || costoStr.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double costo = Double.parseDouble(costoStr);

            Productos productoSeleccionado = (Productos) spProducto.getSelectedItem();
            int idProducto = productoSeleccionado.getIdProducto();
            String tipoMovimiento = spTipoMovimiento.getSelectedItem().toString();

            movimientoEditar.setIdProducto(idProducto);
            movimientoEditar.setCantidad(cantidad);
            movimientoEditar.setCostoUnitario(costo);
            movimientoEditar.setFecha(fecha);
            movimientoEditar.setTipo(tipoMovimiento);

            int filasAfectadas = moviminetoInventarioDAO.updateMovimiento(movimientoEditar);

            if (filasAfectadas > 0) {
                Toast.makeText(getContext(), "Movimiento actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al actualizar movimiento", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Cantidad o costo no son válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarProductos() {
        List<Productos> listaProductos = db.productosDao().getAllProductos();
        if (listaProductos != null && !listaProductos.isEmpty()) {
            ArrayAdapter<Productos> adapter = new ArrayAdapter<Productos>(getContext(), android.R.layout.simple_spinner_item, listaProductos) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    textView.setText(listaProductos.get(position).getNombreProducto());
                    return view;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    textView.setText(listaProductos.get(position).getNombreProducto());
                    return view;
                }
            };
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProducto.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "No hay productos registrados", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarTiposMovimiento() {
        List<String> tiposMovimiento = new ArrayList<>();
        tiposMovimiento.add("Entrada");
        tiposMovimiento.add("Salida");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tiposMovimiento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoMovimiento.setAdapter(adapter);
    }

    private int getProductoPosition(int idProducto) {
        for (int i = 0; i < spProducto.getCount(); i++) {
            Productos producto = (Productos) spProducto.getItemAtPosition(i);
            if (producto.getIdProducto() == idProducto) {
                return i;
            }
        }
        return 0;
    }

    private int getTipoMovimientoPosition(String tipoMovimiento) {
        if ("Entrada".equals(tipoMovimiento)) {
            return 0;
        } else if ("Salida".equals(tipoMovimiento)) {
            return 1;
        }
        return 0;
    }
}
