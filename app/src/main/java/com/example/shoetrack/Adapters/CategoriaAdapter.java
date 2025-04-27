package com.example.shoetrack.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Dialog.CategoriasDialogo;
import com.example.shoetrack.Fragments.CategoriasFragment;
import com.example.shoetrack.Moduls.Categoria;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.R;

import java.util.List;
import java.util.concurrent.Executors;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {
    private List<Categoria> listCategoria;
    private Context context;
    private CategoriasDialogo.CategoriaListener categoriaListener;
    private FragmentManager manager;

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }
    public CategoriaAdapter(FragmentManager manager) {
        this.manager = manager;
    }


    // Add the setCategoriaListener method here, at the same level as your other methods
    public void setCategoriaListener(CategoriasDialogo.CategoriaListener listener) {
        this.categoriaListener = listener;
    }
    public CategoriaAdapter(Context context, List<Categoria> listCategoria, FragmentManager manager) {
        this.context = context;
        this.listCategoria = listCategoria;
        this.manager = manager;
    }

    public CategoriaAdapter(Context context, List<Categoria> listCategoria) {
        this.context = context;
        this.listCategoria = listCategoria;
    }

    @NonNull
    @Override
    public CategoriaAdapter.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.CategoriaViewHolder holder, int position) {
        Categoria categoria = listCategoria.get(position);
        holder.lblNombreCategoria.setText(categoria.getNombreCategoria());

        // 🔥 CLIC EN ELIMINAR
        holder.btnEliminarCtegoria.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(context)
                    .setTitle("¿Eliminar estudiante?")
                    .setMessage("¿Estás seguro de que deseas eliminar a " + categoria.getNombreCategoria() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            // Eliminar de Room
                            AppDatabase.getInstance(context).categoriasDAO().deleteCateforia(categoria);
                            ((android.app.Activity) context).runOnUiThread(() -> {
                                listCategoria.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, listCategoria.size());
                            });
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        holder.btnEditarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager != null) {
                    CategoriasDialogo categoriasDialogo = new CategoriasDialogo(categoria, categoriaListener);
                    categoriasDialogo.show(manager, "editar");
                } else {
                    // Handle the case when manager is null, perhaps with a Toast message
                    android.widget.Toast.makeText(context, "No se puede editar en este momento",
                            android.widget.Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return listCategoria.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {
        private Button btnEditarCategoria, btnEliminarCtegoria;
        private TextView lblNombreCategoria;
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNombreCategoria = itemView.findViewById(R.id.lblNombreCategoria);
            btnEditarCategoria = itemView.findViewById(R.id.btnEditarCategoria);
            btnEliminarCtegoria = itemView.findViewById(R.id.btnEliminarCategoria);
        }
    }
}
