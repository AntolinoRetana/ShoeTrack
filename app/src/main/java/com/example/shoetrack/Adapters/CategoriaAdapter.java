package com.example.shoetrack.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriasViewHolder> {
    @NonNull
    @Override
    public CategoriaAdapter.CategoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.CategoriasViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CategoriasViewHolder extends RecyclerView.ViewHolder {
        public CategoriasViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
