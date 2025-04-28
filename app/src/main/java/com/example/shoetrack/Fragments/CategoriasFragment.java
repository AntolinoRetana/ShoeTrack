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


        rcvCategorias.setLayoutManager(new LinearLayoutManager(getContext()));


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
                    adapter = new CategoriaAdapter(getContext(), listaCategoria, getChildFragmentManager());
                    setCategoriaListenerForAdapter();
                    rcvCategorias.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }

    private void setCategoriaListenerForAdapter() {
        if (adapter != null) {
            adapter.setCategoriaListener(this);
        }
    }

    @Override
    public void onCategoriaListener() {
        // Make sure this method is being called when a category is added/updated
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getContext());
            List<Categoria> categorias = db.categoriasDAO().getAllCategorias();

            requireActivity().runOnUiThread(() -> {
                if (listaCategoria != null) {
                    listaCategoria.clear();
                    listaCategoria.addAll(categorias);

                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        });
    }
}