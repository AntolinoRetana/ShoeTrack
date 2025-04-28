package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.Ventas;

import java.util.List;

@Dao
public interface VentasDAO {
    @Insert
    void insertar(Ventas venta);

    @Update
    void actualizar(Ventas venta);

    @Delete
    void eliminar(Ventas venta);

    @Query("SELECT * FROM ventas")
    List<Ventas> obtenerTodas();
}
