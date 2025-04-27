package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.Clientes;

import java.util.List;

@Dao
public interface ClienteDAO {
    @Insert
    void insertar(Clientes cliente);

    @Update
    void actualizar(Clientes cliente);

    @Delete
    void eliminar(Clientes cliente);

    @Query("SELECT * FROM clientes")
    List<Clientes> obtenerTodos();
}
