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
import com.example.shoetrack.Moduls.Empleados;
import com.example.shoetrack.R;

import java.util.List;

public class EmpleadosAdapter extends RecyclerView.Adapter<EmpleadosAdapter.EmpleadosViewHolder>{

    private List<Empleados> listaempleados;
    private Context context;

    public EmpleadosAdapter(List<Empleados> listaempleados, Context context) {
        this.listaempleados = listaempleados;
        this.context = context;
    }

    @NonNull
    @Override
    public EmpleadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_items_empleados, parent, false);
        return new EmpleadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadosViewHolder holder, int position) {
        Empleados empleado = listaempleados.get(position);
        holder.txtNombreEmpleado.setText(empleado.getNombre());
        holder.txtCargoEmpleado.setText(empleado.getPuesto());
        holder.imgItem.setImageResource(R.drawable.boy); // Reemplaza 'boy' por la imagen adecuada

        // 🔥 CLIC EN ELIMINAR
        holder.btnEliminar.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(context)
                    .setTitle("¿Eliminar empleado?")
                    .setMessage("¿Estás seguro de que deseas eliminar a " + empleado.getNombre() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Eliminar de Room
                        AppDatabase.getInstance(context).empleadoDao().eliminar(empleado);
                        // Actualizar lista local
                        listaempleados.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaempleados.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listaempleados.size();
    }

    public class EmpleadosViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreEmpleado, txtCargoEmpleado;
        ImageView imgItem, btnEditarDatos, btnEliminar;

        public EmpleadosViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreEmpleado = itemView.findViewById(R.id.lblTituloEmpleado);
            txtCargoEmpleado = itemView.findViewById(R.id.lblCargo);
            imgItem = itemView.findViewById(R.id.imgItem);
            btnEditarDatos = itemView.findViewById(R.id.btnEditarDatos);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
