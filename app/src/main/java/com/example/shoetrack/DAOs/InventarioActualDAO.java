package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.InventariosActual;

import java.util.List;

@Dao
public interface InventarioActualDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertInventario(InventariosActual inventario);

    @Update
    int updateInventario(InventariosActual inventario);

    @Delete
    int deleteInventario(InventariosActual inventario);

    @Query("SELECT * FROM inventario_actual")
    List<InventariosActual> getAllInventario();

    @Query("SELECT * FROM inventario_actual WHERE idProducto = :idProducto")
    InventariosActual getInventarioPorProductoId(int idProducto);

    @Query("UPDATE inventario_actual SET stock = :nuevoStock WHERE idProducto = :idProducto")
    int actualizarStock(int idProducto, int nuevoStock);

    @Query("UPDATE inventario_actual SET costoPromedio = :nuevoCostoPromedio WHERE idProducto = :idProducto")
    int actualizarCostoPromedio(int idProducto, double nuevoCostoPromedio);

    @Query("SELECT * FROM inventario_actual WHERE stock < :umbral")
    List<InventariosActual> getProductosStockBajo(int umbral);

    @Query("SELECT stock FROM inventario_actual WHERE idProducto = :idProducto")
    int getStockDisponible(int idProducto);

    @Query("SELECT * FROM inventario_actual WHERE stock > 0")
    List<InventariosActual> getProductosDisponibles();

    @Query("SELECT SUM(stock * costoPromedio) FROM inventario_actual")
    double getValorTotalInventario();

    @Query("DELETE FROM inventario_actual WHERE idProducto = :idProducto")
    int eliminarPorId(int idProducto);
}
