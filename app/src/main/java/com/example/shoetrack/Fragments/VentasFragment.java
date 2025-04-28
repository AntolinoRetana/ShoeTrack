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
import com.example.shoetrack.Adapters.VentasAdapter;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Empleados;
import com.example.shoetrack.Moduls.Ventas;
import com.example.shoetrack.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VentasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VentasFragment extends Fragment {

    private Button btnAgregarVentas;
    private RecyclerView rvcVentas;
    private VentasAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VentasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VentasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VentasFragment newInstance(String param1, String param2) {
        VentasFragment fragment = new VentasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        cargarEmpleados();

        return view;

    }
    private void cargarEmpleados() {
        // Load list of employees from the database
        List<Ventas> ventas = AppDatabase.getInstance(requireContext())
                .ventasDao()
                .obtenerTodas();
        adapter = new VentasAdapter(ventas, requireContext());
        rvcVentas.setAdapter(adapter);
    }
}