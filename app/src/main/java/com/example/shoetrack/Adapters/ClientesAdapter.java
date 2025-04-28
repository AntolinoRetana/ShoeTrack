package com.example.shoetrack.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.R;

import java.util.List;
import java.util.concurrent.Executors;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder> {
    private List<Clientes> clientesList;
    private Context context;
    public ClientesAdapter(List<Clientes> clientesList, Context context) {
        this.clientesList = clientesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClientesAdapter.ClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_items, parent, false);
        return new ClientesViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ClientesAdapter.ClientesViewHolder holder, int position) {
        Clientes clientes = clientesList.get(position);
        holder.imgItem.setImageResource(R.drawable.boy);
        holder.lblTitulo.setText(clientes.getNombre());
        holder.lblTelefono.setText(clientes.getTelefono());

        // 🔥 CLIC EN ELIMINAR
        holder.btnEliminar.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(context)
                    .setTitle("¿Eliminar estudiante?")
                    .setMessage("¿Estás seguro de que deseas eliminar a " + clientes.getNombre() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            // Eliminar de Room
                            AppDatabase.getInstance(context).clienteDao().eliminar(clientes);
                            ((android.app.Activity) context).runOnUiThread(() -> {
                                clientesList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, clientesList.size());
                            });
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return clientesList.size();
    }



    public class ClientesViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem, btnEditar, btnEliminar;
        private TextView lblTitulo,lblTelefono;
        public ClientesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            lblTitulo = itemView.findViewById(R.id.lblTitulo);
            btnEditar = itemView.findViewById(R.id.btnEditarDatos);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            lblTelefono = itemView.findViewById(R.id.lblTelefono);
        }
    }
}
