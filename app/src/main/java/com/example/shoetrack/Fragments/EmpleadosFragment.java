package com.example.shoetrack.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoetrack.Adapters.EmpleadosAdapter;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Empleados;
import com.example.shoetrack.R;

import java.util.List;

public class EmpleadosFragment extends Fragment {

    private Button btnNuevoEmpleado;
    private RecyclerView rcvEmpleados;
    private EmpleadosAdapter adapter;

    public EmpleadosFragment() {
        // Required empty public constructor
    }

    public static EmpleadosFragment newInstance(String param1, String param2) {
        EmpleadosFragment fragment = new EmpleadosFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve arguments if necessary
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empleados, container, false);

        rcvEmpleados = view.findViewById(R.id.rcvEmpleados);
        rcvEmpleados.setLayoutManager(new LinearLayoutManager(requireContext()));

        btnNuevoEmpleado = view.findViewById(R.id.btnNuevoEmpleado);

        btnNuevoEmpleado.setOnClickListener(v -> {
            Fragment nuevoFragmento = new EmpleadoFragmentAgregar();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, nuevoFragmento)
                    .addToBackStack(null)
                    .commit();
        });

        cargarEmpleados();

        return view;
    }

    private void cargarEmpleados() {
        // Load list of employees from the database
        List<Empleados> empleados = AppDatabase.getInstance(requireContext())
                .empleadoDao()
                .obtenerTodos();
        adapter = new EmpleadosAdapter(empleados, requireContext());
        rcvEmpleados.setAdapter(adapter);
    }
}
