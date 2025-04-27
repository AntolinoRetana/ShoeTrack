package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.Empleados;

import java.util.List;

@Dao
public interface EmpleadoDAO {
    @Insert
    void insertar(Empleados empleado);

    @Update
    void actualizar(Empleados empleado);

    @Delete
    void eliminar(Empleados empleado);

    @Query("SELECT * FROM empleados")
    List<Empleados> obtenerTodos();
}
