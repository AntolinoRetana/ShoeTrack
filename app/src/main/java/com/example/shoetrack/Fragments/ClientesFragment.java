package com.example.shoetrack.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shoetrack.Adapters.ClientesAdapter;
import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Dialog.ClientesDialogos;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.R;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesFragment extends Fragment {
    private Button btnNuevoCliente;
    private RecyclerView rcvClientes;
    private ClientesAdapter adapter;
    private List<Clientes> listaClientes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientesFragment newInstance(String param1, String param2) {
        ClientesFragment fragment = new ClientesFragment();
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


        View view = inflater.inflate(R.layout.fragment_clientes, container, false);
        btnNuevoCliente = view.findViewById(R.id.btnNuevoCliente);
        rcvClientes = view.findViewById(R.id.rcvClientes);

        // Configurar el RecyclerView
        rcvClientes.setLayoutManager(new LinearLayoutManager(getContext()));

        // Abrir el diálogo para agregar un cliente
        btnNuevoCliente.setOnClickListener(v -> {
            ClientesDialogos dialog = new ClientesDialogos();
            dialog.show(getChildFragmentManager(), "ClienteDialog");
        });

        cargarClientes();
        return view;
    }

    private void cargarClientes() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getContext());
            List<Clientes> clientes = db.clienteDao().obtenerTodos();

            requireActivity().runOnUiThread(() -> {
                listaClientes = clientes;
                if (adapter == null) {
                    adapter = new ClientesAdapter(listaClientes, getContext());
                    rcvClientes.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }
}