package com.example.shoetrack.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoetrack.Adapters.VentasAdapter;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Ventas;
import com.example.shoetrack.R;

import java.util.List;

public class VentasFragment extends Fragment {

    private Button btnAgregarVentas;
    private RecyclerView rvcVentas;
    private VentasAdapter adapter;

    public VentasFragment() {
        // Constructor vacío
    }

    public static VentasFragment newInstance(String param1, String param2) {
        VentasFragment fragment = new VentasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ventas, container, false);

        btnAgregarVentas = view.findViewById(R.id.btnAgregarVentas);
        rvcVentas = view.findViewById(R.id.rvcVentas);
        rvcVentas.setLayoutManager(new LinearLayoutManager(requireContext()));

        btnAgregarVentas.setOnClickListener(v -> {
            Fragment nuevoFragmento = new VentaFragmentAgregar();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, nuevoFragmento)
                    .addToBackStack(null)
                    .commit();
        });

        cargarVentas();

        return view;
    }

    private void cargarVentas() {
        List<Ventas> ventas = AppDatabase.getInstance(requireContext())
                .ventasDao()
                .obtenerTodas();
        adapter = new VentasAdapter(ventas, requireContext());
        rvcVentas.setAdapter(adapter);
    }
}
