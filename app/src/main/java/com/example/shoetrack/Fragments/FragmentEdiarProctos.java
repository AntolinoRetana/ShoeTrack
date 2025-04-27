package com.example.shoetrack.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoetrack.DAOs.CategoriasDAO;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Categoria;
import com.example.shoetrack.Moduls.Productos;
import com.example.shoetrack.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentEdiarProctos extends Fragment {
    private Spinner spCategoria;
    private CategoriasDAO categoriasDAO;
    private AppDatabase db; // Base de datos
    private TextView nombreProducto, marcaProducto, tallaProducto, precioProducto;
    private Spinner SpCategoria;
    private Button btnInsertarProducto, btnEditarProducto;
    private Productos productoEditar;
    private TextView lblCancelarProducto;



    public FragmentEdiarProctos() {
        // Required empty public constructor
    }
    public FragmentEdiarProctos(Productos productoEditar) {
        this.productoEditar = productoEditar;
    }


    public static FragmentEdiarProctos newInstance() {
        FragmentEdiarProctos fragment = new FragmentEdiarProctos();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ediar_proctos, container, false);



        spCategoria = view.findViewById(R.id.idSpCategoraEditar);
        nombreProducto = view.findViewById(R.id.txtNombreProductoEditar);
        marcaProducto = view.findViewById(R.id.txtMarcaProductoEditar);
        tallaProducto = view.findViewById(R.id.txtTallaProductoEditar);
        precioProducto = view.findViewById(R.id.txtPrecioProductoEditar);
        btnEditarProducto = view.findViewById(R.id.btnEditarProductoss);
        lblCancelarProducto=view.findViewById(R.id.lblCancelarProducto);

        // Inicializar base de datos y DAO
        db = AppDatabase.getInstance(getContext());
        categoriasDAO = db.categoriasDAO();

        // Luego ya puedes llamar cargarCategorias
        cargarCategorias();


        // Rellenar los campos con los datos del producto a editar
        if (productoEditar != null) {
            nombreProducto.setText(productoEditar.getNombreProducto());
            marcaProducto.setText(productoEditar.getMarcaProducto());
            tallaProducto.setText(String.valueOf(productoEditar.getTallaProducto()));
            precioProducto.setText(String.valueOf(productoEditar.getPrecioProducto()));

            // Seleccionar la categoría actual en el Spinner
            String nombreCategoria = categoriasDAO.getNombreCategoriaPorId(productoEditar.getIdCategoria());
            if (nombreCategoria != null) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) spCategoria.getAdapter();
                int posicion = adapter.getPosition(nombreCategoria);
                if (posicion >= 0) {
                    spCategoria.setSelection(posicion);
                }
            }
        }

        // Evento de actualizar producto
        btnEditarProducto.setOnClickListener(v -> {
            String nombre = nombreProducto.getText().toString().trim();
            String marca = marcaProducto.getText().toString().trim();
            String tallaStr = tallaProducto.getText().toString().trim();
            String precioStr = precioProducto.getText().toString().trim();
            String categoriaSeleccionada = spCategoria.getSelectedItem() != null ? spCategoria.getSelectedItem().toString() : "";

            if (nombre.isEmpty() || marca.isEmpty() || tallaStr.isEmpty() || precioStr.isEmpty() || categoriaSeleccionada.isEmpty()) {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int talla;
            double precio;
            try {
                talla = Integer.parseInt(tallaStr);
                precio = Double.parseDouble(precioStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Talla o precio no son válidos", Toast.LENGTH_SHORT).show();
                return;
            }

            int idCategoria = categoriasDAO.getIdCategoriaPorNombre(categoriaSeleccionada);
            if (idCategoria == -1) {
                Toast.makeText(getContext(), "Categoría no encontrada", Toast.LENGTH_SHORT).show();
                return;
            }

            // Actualizar objeto Producto
            productoEditar.setNombreProducto(nombre);
            productoEditar.setMarcaProducto(marca);
            productoEditar.setTallaProducto(talla);
            productoEditar.setPrecioProducto(precio);
            productoEditar.setIdCategoria(idCategoria);

            // Actualizar en la base de datos
            int filasAfectadas = db.productosDao().updateProductos(productoEditar);

            if (filasAfectadas > 0) {
                Toast.makeText(getContext(), "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al actualizar producto", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void cargarCategorias() {
        List<Categoria> listaCategorias = categoriasDAO.getAllCategorias();
        if (listaCategorias != null && !listaCategorias.isEmpty()) {
            List<String> nombresCategorias = new ArrayList<>();
            for (Categoria categoria : listaCategorias) {
                nombresCategorias.add(categoria.getNombreCategoria());
            }
            // Aquí se establece el adaptador
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombresCategorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "No hay categorías registradas", Toast.LENGTH_SHORT).show();
        }

    }

}