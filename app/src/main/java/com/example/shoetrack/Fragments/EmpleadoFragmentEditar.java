package com.example.shoetrack.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Empleados;
import com.example.shoetrack.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmpleadoFragmentEditar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpleadoFragmentEditar extends Fragment {

    private EditText txtNombreEmpleadoEditar, txtCargoEmpleadoEditar;
    private Button btnEditarEmpleado;
    private TextView btnCancelarEmpleado;
    private Empleados empleados;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmpleadoFragmentEditar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmpleadoFragmentEditar.
     */
    // TODO: Rename and change types and number of parameters
    public static EmpleadoFragmentEditar newInstance(String param1, String param2) {
        EmpleadoFragmentEditar fragment = new EmpleadoFragmentEditar();
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
        View vista = inflater.inflate(R.layout.fragment_empleado_editar, container, false);
        txtNombreEmpleadoEditar = vista.findViewById(R.id.txtNombreEmpleadoEditar);
        txtCargoEmpleadoEditar = vista.findViewById(R.id.txtCargoEmpleadoEditar);
        btnCancelarEmpleado = vista.findViewById(R.id.btnCancelarEmpleado);
        btnEditarEmpleado = vista.findViewById(R.id.btnEditarEmpleado);

        // Prellenar campos
        txtNombreEmpleadoEditar.setText(empleados.getNombre());
        txtCargoEmpleadoEditar.setText(empleados.getPuesto());

        btnEditarEmpleado.setOnClickListener(v -> {
            String NombreEmpleadoEditar = txtNombreEmpleadoEditar.getText().toString().trim();
            String CargoEmpleado = txtCargoEmpleadoEditar.getText().toString().trim();

            empleados.setNombre(NombreEmpleadoEditar);
            empleados.setPuesto(CargoEmpleado);


            AppDatabase.getInstance(getContext()).empleadoDao().actualizar(empleados);

            Toast.makeText(getContext(), "Estudiante actualizado", Toast.LENGTH_SHORT).show();

            getActivity().getSupportFragmentManager().popBackStack();
        });


        return vista;
    }
}