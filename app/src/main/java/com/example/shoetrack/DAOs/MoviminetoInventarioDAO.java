package com.example.shoetrack.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoetrack.Moduls.MovimientoIventario;

import java.util.List;

@Dao
public interface MoviminetoInventarioDAO {
    @Insert
    Long insertMovimineto(MovimientoIventario movimiento);

    @Update
    int updateMovimiento(MovimientoIventario movimiento);

    @Delete
    void deleteMovimiento(MovimientoIventario movimiento);

    // Consultar todos los movimientos
    @Query("SELECT * FROM movimientos_inventario")
    List<MovimientoIventario> getAllMovimientos();

    // Consultar movimientos de un producto específico
    @Query("SELECT * FROM movimientos_inventario WHERE idProducto = :idProducto")
    List<MovimientoIventario> obtenerMovimientosPorProducto(int idProducto);

    // Calcular costo promedio de un producto basado en movimientos de ENTRADA
    @Query("SELECT AVG(costo_unitario) FROM movimientos_inventario WHERE idProducto = :idProducto AND tipo = 'ENTRADA'")
    Double calcularCostoPromedioPorProducto(int idProducto);

    // Obtener el último movimiento (opcional)
    @Query("SELECT * FROM movimientos_inventario ORDER BY fecha DESC LIMIT 1")
    MovimientoIventario obtenerUltimoMovimiento();
}
