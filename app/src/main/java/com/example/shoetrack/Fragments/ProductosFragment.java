package com.example.shoetrack.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shoetrack.Adapters.CategoriaAdapter;
import com.example.shoetrack.Adapters.ProductoAdapter;
import com.example.shoetrack.DAOs.CategoriasDAO;
import com.example.shoetrack.DAOs.ProductosDAO;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Categoria;
import com.example.shoetrack.Moduls.Productos;
import com.example.shoetrack.R;

import java.util.ArrayList;
import java.util.List;


public class ProductosFragment extends Fragment {
    private Spinner spCategoria;
    private CategoriasDAO categoriasDAO;
    private AppDatabase db; // Base de datos
    private Button btnAgrgarProducto;
    private RecyclerView rvcProductos;
    private ProductoAdapter productoAdapter;
    private ArrayList<Productos> dataProductos;

    public ProductosFragment() {
        // Required empty public constructor
    }


    public static ProductosFragment newInstance(String param1, String param2) {
        ProductosFragment fragment = new ProductosFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        // Asociar RecyclerView
        rvcProductos = view.findViewById(R.id.rvcProductos);
        rvcProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        // Asociar adaptador
        dataProductos = new ArrayList<>();
        productoAdapter = new ProductoAdapter(getContext(),dataProductos);
        rvcProductos.setAdapter(productoAdapter);

        //cargar los producto
        cargarProductos();

        btnAgrgarProducto = view.findViewById(R.id.btnAgregarProductos);
        btnAgrgarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment insertarProductoFragment = new FragmentInsertarProductos();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, insertarProductoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return  view;
    }
    public void cargarProductos(){
        // Get ProductosDAO from your database
        ProductosDAO productosDAO = db.productosDAO();

        // Get all products
        List<Productos> productos = productosDAO.getAllProductos();

        // Clear existing data and add all products
        dataProductos.clear();
        dataProductos.addAll(productos);

        // Notify adapter about data change
        productoAdapter.notifyDataSetChanged();
    }
}