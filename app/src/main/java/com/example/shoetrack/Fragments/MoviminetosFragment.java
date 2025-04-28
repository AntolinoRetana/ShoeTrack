package com.example.shoetrack.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shoetrack.Adapters.MovimientoAdapter;
import com.example.shoetrack.DAOs.MoviminetoInventarioDAO;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.MovimientoIventario;
import com.example.shoetrack.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoviminetosFragment extends Fragment {
    private RecyclerView rvcMovimientos;
    private Button btnNuevoMovimiento;
    private MovimientoAdapter movimientoAdapter;
    private ArrayList<MovimientoIventario> dataMovimientos;
    private AppDatabase db;
    private MoviminetoInventarioDAO movimientosDAO;
    public MoviminetosFragment() {
        // Required empty public constructor
    }

    public static MoviminetosFragment newInstance(MovimientoIventario movimientoIventario) {
        MoviminetosFragment fragment = new MoviminetosFragment();
        Bundle args = new Bundle();
        // Pasar los datos al Bundle
        args.putSerializable("movimientoIventario", (Serializable) movimientoIventario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(getContext());
        movimientosDAO = db.movimientoInventarioDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_moviminetos, container, false);
        // Asociar RecyclerView
        rvcMovimientos = view.findViewById(R.id.rvcMovimientos);
        rvcMovimientos.setLayoutManager(new LinearLayoutManager(getContext()));

        // Asociar adaptador
        dataMovimientos = new ArrayList<>();
        movimientoAdapter = new MovimientoAdapter(getContext(), dataMovimientos);
        rvcMovimientos.setAdapter(movimientoAdapter);

        // Cargar movimientos
        cargarMovimientos();

        // Botón de agregar movimiento
        btnNuevoMovimiento = view.findViewById(R.id.btnNuevoMovimiento);
        btnNuevoMovimiento.setOnClickListener(v -> {
            // Implementar lógica para agregar nuevo movimiento
            Fragment insertarMovimientoFragment = new InsertarMoviminetoFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, insertarMovimientoFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    // Método para cargar los movimientos desde la base de datos
    public void cargarMovimientos() {
        // Obtener todos los movimientos desde la base de datos
        List<MovimientoIventario> movimientos = movimientosDAO.getAllMovimientos();

        // Limpiar datos existentes y agregar todos los movimientos
        dataMovimientos.clear();
        dataMovimientos.addAll(movimientos);

        // Notificar al adaptador que los datos han cambiado
        movimientoAdapter.notifyDataSetChanged();
    }
}
