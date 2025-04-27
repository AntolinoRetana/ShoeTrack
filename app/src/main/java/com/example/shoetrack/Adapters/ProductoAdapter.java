package com.example.shoetrack.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoetrack.DB.AppDatabase;
import com.example.shoetrack.Fragments.FragmentEdiarProctos;
import com.example.shoetrack.Moduls.Productos;
import com.example.shoetrack.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private ArrayList<Productos> dataProductos;
    private Context context;

    public ProductoAdapter(Context context, ArrayList<Productos> dataProductos) {
        this.context = context;
        this.dataProductos = dataProductos;
    }

    @NonNull
    @Override
    public ProductoAdapter.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflamos la vista
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ProductoViewHolder holder, int position) {
        //Creamos on obj
        Productos productos = dataProductos.get(position);
        holder.nombreProducto.setText(productos.getNombreProducto());
        holder.marcaProducto.setText(productos.getMarcaProducto());

        // Convert int to String
        holder.tallaProducto.setText(String.valueOf(productos.getTallaProducto()));

        // Convert double to String
        holder.precioProducto.setText(String.valueOf(productos.getPrecioProducto()));

        // Convert int to String
        holder.categoriaProducto.setText(String.valueOf(productos.getIdCategoria()));

        //Obtener el nombre de la categoría usando el ID de la categoría
        String nombreCategoria = AppDatabase.getInstance(context).categoriasDAO().getNombreCategoriaPorId(productos.getIdCategoria());
        holder.categoriaProducto.setText(nombreCategoria);

        holder.btnEliminarProducto.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(context)
                    .setTitle("¿Eliminar estudiante?")
                    .setMessage("¿Estás seguro de que deseas eliminar a " + productos.getNombreProducto() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Eliminar de Room
                        AppDatabase.getInstance(context).productosDao().deleteProductos(productos);
                        // Actualizar lista local
                        dataProductos.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, dataProductos.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // CLIC EN EDITAR
       holder.btnEdiatarProducto.setOnClickListener(v -> {
            FragmentEdiarProctos fragmentEdiarProctos = new FragmentEdiarProctos(productos);
           // Agregado: Mostrar un log para verificar que el producto está pasando correctamente
           Log.d("ProductoAdapter", "Editando producto: " + productos.getNombreProducto());

           // Crear una instancia del fragmento y pasar el producto a editar
            ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragmentEdiarProctos)
                    .addToBackStack(null)
                    .commit();
        });


    }

    @Override
    public int getItemCount() {
        return dataProductos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreProducto, tallaProducto, precioProducto, categoriaProducto, marcaProducto;
        private Button btnEliminarProducto, btnEdiatarProducto;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.lblNombreProducto);
            marcaProducto = itemView.findViewById(R.id.lblMarcaProducto);
            tallaProducto = itemView.findViewById(R.id.lblTallaProducto);
            precioProducto = itemView.findViewById(R.id.lblPrecioProducto);
            categoriaProducto = itemView.findViewById(R.id.lblCategoriaProducto);
            btnEliminarProducto = itemView.findViewById(R.id.btnEliminarProducto);
            btnEdiatarProducto = itemView.findViewById(R.id.btnEditarProducto);
        }
    }
}
