package com.example.shoetrack.Moduls;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "detalle_ventas",
        foreignKeys = {
                @ForeignKey(entity = Ventas.class,
                        parentColumns = "id",
                        childColumns = "id_venta",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Productos.class,
                        parentColumns = "idProducto",
                        childColumns = "id_producto",
                        onDelete = ForeignKey.CASCADE)
        })
public class DetalleVentas {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_venta")
    public int idVenta;

    @ColumnInfo(name = "id_producto")
    public int idProducto;

    @ColumnInfo(name = "cantidad")
    public int cantidad;

    @ColumnInfo(name = "precio_unitario")
    public double precioUnitario;

    @ColumnInfo(name = "subtotal")
    public double subtotal;

    // Constructor, getters y setters
    public DetalleVentas(int idVenta, int idProducto, int cantidad, double precioUnitario, double subtotal) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public DetalleVentas() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

}
