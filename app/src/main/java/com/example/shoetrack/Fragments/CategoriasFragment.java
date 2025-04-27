package com.example.shoetrack.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoetrack.Adapters.CategoriaAdapter;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Dialog.CategoriasDialogo;
import com.example.shoetrack.Moduls.Categoria;
import com.example.shoetrack.R;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoriasFragment extends Fragment implements CategoriasDialogo.CategoriaListener {
    private Button btnNuevaCategoria;
    private RecyclerView rcvCategorias;
    private CategoriaAdapter adapter;
    private List<Categoria> listaCategoria;

    public CategoriasFragment() {
        // Required empty public constructor
    }

    public static CategoriasFragment newInstance(String param1, String param2) {
        return new CategoriasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);
        btnNuevaCategoria = view.findViewById(R.id.btnNuebaCategoria);
        rcvCategorias = view.findViewById(R.id.rvcCategorias);

        // Configurar el RecyclerView
        rcvCategorias.setLayoutManager(new LinearLayoutManager(getContext()));

        // Abrir el diálogo para agregar un cliente
        btnNuevaCategoria.setOnClickListener(v -> {
            CategoriasDialogo dialog = new CategoriasDialogo();
            dialog.show(getChildFragmentManager(), "CategoriasDilogo");
        });

        cargarCategorias();
        return view;
    }

    private void cargarCategorias() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getContext());
            List<Categoria> categorias = db.categoriasDAO().getAllCategorias();

            requireActivity().runOnUiThread(() -> {
                listaCategoria = categorias;
                if (adapter == null) {
                    // Create the adapter with the correct context, data, and FragmentManager
                    adapter = new CategoriaAdapter(getContext(), listaCategoria, getChildFragmentManager());
                    // Set the listener through the constructor or a setter method
                    setCategoriaListenerForAdapter();
                    rcvCategorias.setAdapter(adapter);
                } else {
                    // If adapter already exists, just update the data
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }

    // Method to set the listener for the adapter
    private void setCategoriaListenerForAdapter() {
        if (adapter != null) {
            // Create a field in the adapter to set the listener if not already present
            adapter.setCategoriaListener(this);
        }
    }

    // Implementing the CategoriaListener interface
    @Override
    public void onCategoriaListener() {
        // Make sure this method is being called when a category is added/updated
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getContext());
            List<Categoria> categorias = db.categoriasDAO().getAllCategorias();

            requireActivity().runOnUiThread(() -> {
                if (listaCategoria != null) {
                    // Clear and update the existing list
                    listaCategoria.clear();
                    listaCategoria.addAll(categorias);

                    // Notify the adapter about the changes
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        });
    }
}