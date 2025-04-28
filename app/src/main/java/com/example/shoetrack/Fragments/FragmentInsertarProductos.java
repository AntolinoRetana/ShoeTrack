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

import com.example.shoetrack.DAOs.CategoriasDAO;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Categoria;
import com.example.shoetrack.Moduls.Productos;
import com.example.shoetrack.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentInsertarProductos extends Fragment {

    private Spinner spCategoria;
    private CategoriasDAO categoriasDAO;
    private AppDatabase db; // Base de datos
    private EditText nombreProducto, marcaProducto, tallaProducto, precioProducto;
    private Button btnInsertarProducto;
    private TextView lblCancelar;

    public FragmentInsertarProductos() {
        // Required empty public constructor
    }


    public static FragmentInsertarProductos newInstance(String param1, String param2) {
        FragmentInsertarProductos fragment = new FragmentInsertarProductos();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializar base de datos
        db = AppDatabase.getInstance(getContext());
        categoriasDAO = db.categoriasDAO();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insertar_productos, container, false);
        //Asociar
        spCategoria = view.findViewById(R.id.idSpCategora);
        nombreProducto=view.findViewById(R.id.txtNombreProducto);
        marcaProducto=view.findViewById(R.id.txtMarcaProducto);
        tallaProducto=view.findViewById(R.id.txtTallaProducto);
        precioProducto=view.findViewById(R.id.txtPrecioProducto);
        btnInsertarProducto=view.findViewById(R.id.btnInsertarProducto);
        lblCancelar=view.findViewById(R.id.lblCancelar);

        cargarCategorias();

        //Eventos del boton insert
        btnInsertarProducto.setOnClickListener(v ->{
            // Validar campos vacíos
            String nombre = nombreProducto.getText().toString().trim();
            String marca = marcaProducto.getText().toString().trim();
            String tallaStr = tallaProducto.getText().toString().trim();
            String precioStr = precioProducto.getText().toString().trim();
            String categoriaSeleccionada = spCategoria.getSelectedItem() != null ? spCategoria.getSelectedItem().toString() : "";

            if (nombre.isEmpty() || marca.isEmpty() || tallaStr.isEmpty() || precioStr.isEmpty() || categoriaSeleccionada.isEmpty()) {
                Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convertir talla y precio
            int talla;
            double precio;
            try {
                talla = Integer.parseInt(tallaStr);
                precio = Double.parseDouble(precioStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Talla o precio no son válidos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener el ID de la categoría seleccionada
            int idCategoria = categoriasDAO.getIdCategoriaPorNombre(categoriaSeleccionada);

            if (idCategoria == -1) {
                Toast.makeText(getContext(), "Categoría no encontrada", Toast.LENGTH_SHORT).show();
                return;
            }



            // Crear objeto Producto
            Productos nuevoProducto = new Productos();
            nuevoProducto.setNombreProducto(nombre);
            nuevoProducto.setMarcaProducto(marca);
            nuevoProducto.setTallaProducto(talla);
            nuevoProducto.setPrecioProducto(precio);
            nuevoProducto.setIdCategoria(idCategoria);

            // Insertar en la base de datos
            long resultado = db.productosDAO().insertProductos(nuevoProducto);

            if (resultado > 0) {
                Toast.makeText(getContext(), "Producto insertado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                // Cerrar el fragmento automáticamente
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(FragmentInsertarProductos.this)
                        .commit();
            } else {
                Toast.makeText(getContext(), "Error al insertar producto", Toast.LENGTH_SHORT).show();
            }

        });

        cargarCategorias();

        lblCancelar.setOnClickListener(v -> {
            // Remover el fragmento actual
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(FragmentInsertarProductos.this)  // El fragmento actual
                    .commit();
        });
        return view;
    }
    private void limpiarCampos() {
        nombreProducto.setText("");
        marcaProducto.setText("");
        tallaProducto.setText("");
        precioProducto.setText("");
        spCategoria.setSelection(0);
    }

    private void cargarCategorias() {
        List<Categoria> listaCategorias = categoriasDAO.getAllCategorias();

        if (listaCategorias != null && !listaCategorias.isEmpty()) {
            // Crear una lista de nombres de categorías
            List<String> nombresCategorias = new ArrayList<>();
            for (Categoria categoria : listaCategorias) {
                nombresCategorias.add(categoria.getNombreCategoria()); // Usa tu método getter real
            }

            // Adaptador
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    nombresCategorias
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapter);

        } else {
            Toast.makeText(getContext(), "No hay categorías registradas", Toast.LENGTH_SHORT).show();
        }
    }


}