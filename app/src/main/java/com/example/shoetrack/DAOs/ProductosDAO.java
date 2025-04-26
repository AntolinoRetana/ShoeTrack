package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.Productos;

import java.util.List;

@Dao
public interface ProductosDAO {
    @Insert
    void insertProductos(Productos productos);
    @Update
    int updateProductos(Productos productos);
    @Delete
    int deleteProductos(Productos productos);
    @Query("SELECT * FROM productos")
    List<Productos> getAllProductos();
}
