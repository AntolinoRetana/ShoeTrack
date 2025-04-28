package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.MovimientoIventario;
import com.example.shoetrack.Moduls.Productos;

import java.util.List;

@Dao
public interface ProductosDAO {
    @Insert
    Long insertProductos(Productos productos);
    @Update
    int updateProductos(Productos productos);
    @Delete
    int deleteProductos(Productos productos);
    @Query("SELECT * FROM productos")
    List<Productos> getAllProductos();
    @Query("SELECT * FROM movimientos_inventario WHERE idProducto = :productoId")
    List<MovimientoIventario> getMovimientosPorProducto(int productoId);

    // Consulta para obtener el nombre del producto por id
    @Query("SELECT nombreProducto FROM productos WHERE idProducto = :idProducto")
    String getNombreProductoPorId(int idProducto);

    @Query("SELECT * FROM productos WHERE idCategoria = :idCategoria")
    List<Productos> getProductosPorCategoria(int idCategoria);
}
