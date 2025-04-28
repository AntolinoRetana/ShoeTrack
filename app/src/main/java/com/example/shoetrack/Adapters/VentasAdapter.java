package com.example.shoetrack.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // 👈 Usa TextView, no EditText

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoetrack.Moduls.Ventas;
import com.example.shoetrack.R;

import java.util.List;

public class VentasAdapter extends RecyclerView.Adapter<VentasAdapter.VentasViewHolder> {

    private List<Ventas> listaVentas;
    private Context context;

    public VentasAdapter(List<Ventas> listaVentas, Context context) {
        this.listaVentas = listaVentas;
        this.context = context;
    }

    @NonNull
    @Override
    public VentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_venta, parent, false);
        return new VentasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentasViewHolder holder, int position) {
        Ventas venta = listaVentas.get(position);
        holder.lblTotalVenta.setText(String.valueOf(venta.getTotal()));
        holder.lblNombreClienteVenta.setText(String.valueOf(venta.getIdCliente())); // 👈 muestra ID, luego mejoramos para nombre
        holder.lblFechaVenta.setText(venta.getFecha());
    }

    @Override
    public int getItemCount() {
        return listaVentas.size();
    }

    public class VentasViewHolder extends RecyclerView.ViewHolder {

        private TextView lblNombreClienteVenta, lblFechaVenta, lblTotalVenta;

        public VentasViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNombreClienteVenta = itemView.findViewById(R.id.lblNombreClienteVenta);
            lblFechaVenta = itemView.findViewById(R.id.lblFechaVenta);
            lblTotalVenta = itemView.findViewById(R.id.lblTotalVenta);
        }
    }
}
