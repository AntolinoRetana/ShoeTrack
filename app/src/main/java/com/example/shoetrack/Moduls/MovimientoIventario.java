package com.example.shoetrack.Moduls;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "movimientos_inventario",
        foreignKeys = @ForeignKey(
                entity = Productos.class, // Clase relacionada
                parentColumns = "idProducto", // Columna en productos (clave primaria)
                childColumns = "idProducto", // Columna en movimientos_inventario
                onDelete = ForeignKey.CASCADE // Si se borra el producto, se borran sus movimientos
        )
)
public class MovimientoIventario implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int idMovimiento;

    @ColumnInfo(name = "idProducto")  // Mantener nombre tal cual
    public int idProducto;

    @ColumnInfo(name = "tipo")
    public String tipo; // Debería ser "ENTRADA" o "SALIDA"

    @ColumnInfo(name = "cantidad")
    public int cantidad;

    @ColumnInfo(name = "costo_unitario")  // Mantener nombre tal cual
    public double costoUnitario;

    @ColumnInfo(name = "fecha")
    public String fecha;

    // Constructor sin parámetros
    public MovimientoIventario() {
    }

    // Constructor con parámetros


    public MovimientoIventario(int cantidad, double costoUnitario, String fecha, int idMovimiento, int idProducto, String tipo) {
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.fecha = fecha;
        this.idMovimiento = idMovimiento;
        this.idProducto = idProducto;
        this.tipo = tipo;
    }

    // Constructor con parámetros para insertar
    public MovimientoIventario(int idProducto, int cantidad, double costoUnitario) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
    }

    // Getters y Setters


    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
