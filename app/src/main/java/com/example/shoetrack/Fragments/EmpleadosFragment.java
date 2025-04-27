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
<<<<<<< HEAD
=======
import com.example.shoetrack.DB.AppDatabase;
>>>>>>> a976e422bbaa410e03cfa9abbf9157d13b3657a7
import com.example.shoetrack.Moduls.Empleados;
import com.example.shoetrack.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmpleadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpleadosFragment extends Fragment {

    private Button btnNuevoEmpleado;
    private RecyclerView rcvEmpleados;
    private EmpleadosAdapter adapter;
<<<<<<< HEAD
    private List<Empleados> listaEmpleados;

=======
>>>>>>> a976e422bbaa410e03cfa9abbf9157d13b3657a7
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmpleadosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmpleadosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmpleadosFragment newInstance(String param1, String param2) {
        EmpleadosFragment fragment = new EmpleadosFragment();
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
<<<<<<< HEAD
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_empleados, container, false);

=======
        View view = inflater.inflate(R.layout.fragment_empleados, container, false);

        rcvEmpleados = view.findViewById(R.id.rcvEmpleados);
        rcvEmpleados.setLayoutManager(new LinearLayoutManager(requireContext()));

        btnNuevoEmpleado = view.findViewById(R.id.btnNuevoEmpleado);

        btnNuevoEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragmento = new EmpleadoFragmentAgregar();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, nuevoFragmento)
                        .addToBackStack(null)
                        .commit();
            }
        });

        cargarEmpleados();

        return view;
    }

    private void cargarEmpleados() {
        List<Empleados> empleados = AppDatabase.getInstance(requireContext())
                .empleadoDao()
                .obtenerTodos();
        adapter = new EmpleadosAdapter(empleados, requireContext());
        rcvEmpleados.setAdapter(adapter);
>>>>>>> a976e422bbaa410e03cfa9abbf9157d13b3657a7
    }
}