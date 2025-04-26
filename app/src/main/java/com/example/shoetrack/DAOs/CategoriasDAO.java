package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.Categoria;

import java.util.List;

@Dao
public interface CategoriasDAO {
    @Insert
    void insetCategoria(Categoria categoria);
    @Update
    int updateCtegoria(Categoria categoria);
    @Delete
    int deleteCateforia(Categoria categoria);
    @Query("SELECT * FROM categorias")
    List<Categoria> getAllCategorias();
}
