package com.example.shoetrack.Moduls;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ventas",
        foreignKeys = {
                @ForeignKey(entity = Clientes.class,
                        parentColumns = "id",
                        childColumns = "id_cliente",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Empleados.class,
                        parentColumns = "id",
                        childColumns = "id_empleado",
                        onDelete = ForeignKey.CASCADE)
        })
public class Ventas {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_cliente")
    public int idCliente;

    @ColumnInfo(name = "id_empleado")
    public int idEmpleado;

    @ColumnInfo(name = "fecha")
    public String fecha;

    @ColumnInfo(name = "total")
    public double total;

    // Constructor, getters y setters
    public Ventas(int idCliente, String fecha, double total) {
        this.fecha = fecha;
        this.total = total;
    }

    public Ventas() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
