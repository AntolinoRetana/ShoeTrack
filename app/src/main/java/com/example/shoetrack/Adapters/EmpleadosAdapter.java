package com.example.shoetrack.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmpleadosAdapter extends RecyclerView.Adapter<EmpleadosAdapter.EmpleadosViewHolder>{
    @NonNull
    @Override
    public EmpleadosAdapter.EmpleadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadosAdapter.EmpleadosViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class EmpleadosViewHolder extends RecyclerView.ViewHolder {
        public EmpleadosViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
