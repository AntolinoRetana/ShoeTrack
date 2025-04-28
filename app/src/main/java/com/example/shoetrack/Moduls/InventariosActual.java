package com.example.shoetrack.Moduls;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventario_actual",
        foreignKeys = @ForeignKey(
                entity = Productos.class,
                parentColumns = "idProducto",
                childColumns = "idProducto",
                onDelete = CASCADE))
public class InventariosActual {
    @PrimaryKey
    @ColumnInfo(name = "idProducto")
    private int idProducto;

    @ColumnInfo(name = "stock")
    private int stock;

    @ColumnInfo(name = "costoPromedio")
    private double costoPromedio;

    public InventariosActual() {
    }

    public InventariosActual(double costoPromedio, int idProducto, int stock) {
        this.costoPromedio = costoPromedio;
        this.idProducto = idProducto;
        this.stock = stock;
    }

    public double getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(double costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "InventarioActual{" +
                "idProducto=" + idProducto +
                ", stock=" + stock +
                ", costoPromedio=" + costoPromedio +
                '}';
    }
}
