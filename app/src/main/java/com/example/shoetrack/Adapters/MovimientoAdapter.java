package com.example.shoetrack.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Fragments.FragmentEditarMovimiento;  // Aquí necesitas importar el fragmento de edición para movimientos
import com.example.shoetrack.Moduls.MovimientoIventario;
import com.example.shoetrack.R;

import java.util.ArrayList;

public class MovimientoAdapter extends RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder> {

    private ArrayList<MovimientoIventario> dataMovimientos;
    private Context context;
    private MovimientoIventario movimientoIventario;

    public MovimientoAdapter(Context context, ArrayList<MovimientoIventario> dataMovimientos) {
        this.context = context;
        this.dataMovimientos = dataMovimientos;
    }

    @NonNull
    @Override
    public MovimientoAdapter.MovimientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos la vista para los movimientos
        View view = LayoutInflater.from(context).inflate(R.layout.item_movimientos, parent, false);
        return new MovimientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientoAdapter.MovimientoViewHolder holder, int position) {
        // Creamos un objeto de MovimientoInventario
        MovimientoIventario movimientoIventario = dataMovimientos.get(position);

        // Asignamos los datos a los elementos de la vista
        holder.tipoMovimiento.setText(movimientoIventario.getTipo()); // Utilizando getTipo()
        holder.fechaMovimiento.setText(movimientoIventario.getFecha()); // Utilizando getFecha()
        holder.cantidadMovimiento.setText(String.valueOf(movimientoIventario.getCantidad()));
        // Mostrar el nombre del producto asociado al movimiento
        String nombreProducto = AppDatabase.getInstance(context).productosDao().getNombreProductoPorId(movimientoIventario.getIdProducto());
        holder.nombreProducto.setText(nombreProducto);

        holder.btnEliminarMovimiento.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(context)
                    .setTitle("¿Eliminar movimiento?")
                    .setMessage("¿Estás seguro de que deseas eliminar este movimiento?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Eliminar de la base de datos
                        AppDatabase.getInstance(context).movimientoInventarioDAO().deleteMovimiento(movimientoIventario);
                        // Actualizar la lista local
                        dataMovimientos.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, dataMovimientos.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // CLIC EN EDITAR
        holder.btnEditarMovimiento.setOnClickListener(v -> {
            // Usar el método newInstance() para pasar el objeto MovimientoIventario al fragmento
            FragmentEditarMovimiento fragmentEditarMovimiento = FragmentEditarMovimiento.newInstance(movimientoIventario);
            // Agregar un log para depuración
            Log.d("MovimientoAdapter", "Editando movimiento: " + movimientoIventario.getFecha());

            // Crear una transacción para reemplazar el fragmento en el contenedor
            ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragmentEditarMovimiento)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return dataMovimientos.size();
    }

    public class MovimientoViewHolder extends RecyclerView.ViewHolder {
        private TextView fechaMovimiento, tipoMovimiento, cantidadMovimiento, nombreProducto;
        private Button btnEliminarMovimiento, btnEditarMovimiento;

        public MovimientoViewHolder(@NonNull View itemView) {
            super(itemView);
            fechaMovimiento = itemView.findViewById(R.id.lblFechaMovimiento);
            tipoMovimiento = itemView.findViewById(R.id.lblTipoMovimiento);
            cantidadMovimiento = itemView.findViewById(R.id.lblCantidadMovimiento);
            nombreProducto = itemView.findViewById(R.id.lblNombreProductoMovimiento);
            btnEliminarMovimiento = itemView.findViewById(R.id.btnEliminarMovimiento);
            btnEditarMovimiento = itemView.findViewById(R.id.btnEditarMovimiento);
        }
    }
}
