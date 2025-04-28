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
import android.widget.Toast;

import com.example.shoetrack.DAOs.ClienteDAO;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.Moduls.Ventas;
import com.example.shoetrack.R;

import java.util.ArrayList;
import java.util.List;

public class VentaFragmentAgregar extends Fragment {

    private EditText txtFechaVenta, txtTotalVenta;
    private Spinner spinnerClientes;
    private Button btnAgregarVentas;

    private AppDatabase db;
    private ClienteDAO clientesDAO;

    private List<Clientes> listaClientes = new ArrayList<>();

    public VentaFragmentAgregar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(getContext());
        clientesDAO = db.clienteDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venta_agregar, container, false);

        spinnerClientes = view.findViewById(R.id.spinnerClientes);
        txtTotalVenta = view.findViewById(R.id.txtTotalVenta);
        txtFechaVenta = view.findViewById(R.id.txtFechaVenta);
        btnAgregarVentas = view.findViewById(R.id.btnAgregarVentas);

        cargarClientes();

        btnAgregarVentas.setOnClickListener(v -> {
            int posicionSeleccionada = spinnerClientes.getSelectedItemPosition();
            if (posicionSeleccionada >= 0 && posicionSeleccionada < listaClientes.size()) {
                Clientes clienteSeleccionado = listaClientes.get(posicionSeleccionada);

                String totalVentaStr = txtTotalVenta.getText().toString().trim();
                String fechaVenta = txtFechaVenta.getText().toString().trim();

                // Convertir totalVenta a double
                double totalVenta;
                try {
                    totalVenta = Double.parseDouble(totalVentaStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Total de venta no válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                Ventas ventas = new Ventas(clienteSeleccionado.getId(), fechaVenta, totalVenta);
                AppDatabase.getInstance(getContext()).ventasDao().insertar(ventas);

                Toast.makeText(getContext(), "Venta registrada para " + clienteSeleccionado.getNombre(), Toast.LENGTH_SHORT).show();

                txtFechaVenta.setText("");
                txtTotalVenta.setText("");
                spinnerClientes.setSelection(0);

            } else {
                Toast.makeText(getContext(), "Debe seleccionar un cliente válido", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void cargarClientes() {
        listaClientes = clientesDAO.obtenerTodos();

        if (listaClientes != null && !listaClientes.isEmpty()) {
            List<String> nombresClientes = new ArrayList<>();
            for (Clientes cliente : listaClientes) {
                nombresClientes.add(cliente.getNombre());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    nombresClientes
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerClientes.setAdapter(adapter);

        } else {
            Toast.makeText(getContext(), "No hay clientes registrados", Toast.LENGTH_SHORT).show();
        }
    }
}
