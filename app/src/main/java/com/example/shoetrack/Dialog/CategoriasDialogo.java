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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Categoria;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoriasDialogo extends DialogFragment {
    private EditText txtNombreCategoria;
    private Button btnAgrgarCategoria, btnEditarCategoria;
    private TextView lblCancelarCategoria;
    private Categoria categoria;//Objeto



    public interface CategoriaListener{
        void onCategoriaListener();
    }
    private CategoriasDialogo.CategoriaListener categoriaListener;

    public CategoriasDialogo() {
    }

    public CategoriasDialogo(Categoria categoria, CategoriaListener categoriaListener) {
        this.categoria = categoria;
        this.categoriaListener = categoriaListener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment fragment = getParentFragment();
        if(fragment instanceof CategoriasDialogo.CategoriaListener){
            categoriaListener = (CategoriasDialogo.CategoriaListener) fragment;
        }
        else if(context instanceof CategoriasDialogo.CategoriaListener){
            categoriaListener = (CategoriasDialogo.CategoriaListener) context;
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
        View view = inflater.inflate(R.layout.dialog_categoria, container, false);
        txtNombreCategoria = view.findViewById(R.id.txtNombreCategoria);
        btnAgrgarCategoria = view.findViewById(R.id.btnAgrgarCategoria);
        lblCancelarCategoria = view.findViewById(R.id.lblCancelarCategoria);

// Si estamos editando, mostramos el nombre de la categoría
        if (categoria != null) {
            txtNombreCategoria.setText(categoria.getNombreCategoria());
            btnAgrgarCategoria.setText("Actualizar Categoria"); // Cambiar el texto del botón
        }

        btnAgrgarCategoria.setOnClickListener(v -> {
            String nombreCategoria = txtNombreCategoria.getText().toString();

            if (categoria == null) {
                // Agregar nueva categoría
                Categoria nuevaCategoria = new Categoria();
                nuevaCategoria.setNombreCategoria(nombreCategoria);

                // Ejecutar en hilo de fondo para insertar en la base de datos
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    AppDatabase db = AppDatabase.getInstance(getContext());
                    db.categoriasDAO().insetCategoria(nuevaCategoria);

                    // Move UI operations to the main thread
                    requireActivity().runOnUiThread(() -> {
                        if (categoriaListener != null) {
                            categoriaListener.onCategoriaListener();
                        }
                        dismiss();
                    });
                });
            } else {
                // Editar categoría existente
                categoria.setNombreCategoria(nombreCategoria);

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    AppDatabase db = AppDatabase.getInstance(getContext());
                    db.categoriasDAO().updateCtegoria(categoria);

                    // Move UI operations to the main thread
                    requireActivity().runOnUiThread(() -> {
                        if (categoriaListener != null) {
                            categoriaListener.onCategoriaListener();
                        }
                        dismiss();
                    });
                });
            }
        });
        // Acción del botón de cancelar
        lblCancelarCategoria.setOnClickListener(v -> dismiss());

        return view;
    }


}
