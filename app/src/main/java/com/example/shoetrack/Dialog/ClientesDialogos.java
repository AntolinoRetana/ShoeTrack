package com.example.shoetrack.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientesDialogos extends DialogFragment {
    private EditText txtNombreCliente, txtTelefonoCliente,txtCorreoCliente;
    private Button btnAgregarCliente;
    private TextView lblCancelarCliente;

    public interface ClientesListener{
        void onClientesListener();
    }
    private ClientesListener clientesListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment fragment = getParentFragment();
        if(fragment instanceof ClientesListener){
            clientesListener = (ClientesListener) fragment;
        }
        else if(context instanceof ClientesListener){
            clientesListener = (ClientesListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "Debe implementar una interfaz");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }
    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
        {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.dialog_clientes, container, false);
        txtNombreCliente = view.findViewById(R.id.txtNombreCliente);
        txtTelefonoCliente = view.findViewById(R.id.txtTelefonoCliente);
        txtCorreoCliente = view.findViewById(R.id.txtCorreoCliente);
        btnAgregarCliente = view.findViewById(R.id.btnAgregarCliente);
        lblCancelarCliente = view.findViewById(R.id.lblCancelarCliente);

        btnAgregarCliente.setOnClickListener(v -> {
            String nombre = txtNombreCliente.getText().toString();
            String Telefono = txtTelefonoCliente.getText().toString();

            Clientes nuevoCliente = new Clientes();
            nuevoCliente.setNombre(nombre, Telefono);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                AppDatabase db = AppDatabase.getInstance(getContext());
                db.clienteDao().insertar(nuevoCliente);

                if (clientesListener != null) {
                    clientesListener.onClientesListener();
                }

                requireActivity().runOnUiThread(() -> {
                    if (isAdded()) {
                        dismiss();
                    }
                });
            });
        });


        lblCancelarCliente.setOnClickListener(v -> dismiss());

        return view;
    }
}

