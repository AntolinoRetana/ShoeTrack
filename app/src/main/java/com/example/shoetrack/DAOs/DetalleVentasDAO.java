package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.DetalleVentas;

import java.util.List;

@Dao
public interface DetalleVentasDAO {
    @Insert
    void insertar(DetalleVentas detalleVenta);

    @Update
    void actualizar(DetalleVentas detalleVenta);

    @Delete
    void eliminar(DetalleVentas detalleVenta);

    @Query("SELECT * FROM detalle_ventas")
    List<DetalleVentas> obtenerTodos();
}
