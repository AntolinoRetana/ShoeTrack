package com.example.shoetrack.Moduls;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "productos",
        foreignKeys = @ForeignKey(
                entity = Categoria.class,
                parentColumns = "idCategoria",
                childColumns = "idCategoria",
                onDelete = ForeignKey.RESTRICT
        )
)
public class Productos {
    @PrimaryKey(autoGenerate = true)
    private int idProducto;
    @ColumnInfo(name = "nombreProducto")
    private String nombreProducto;
    @ColumnInfo(name = "marcaProducto")
    private String marcaProducto;
    @ColumnInfo(name = "tallaProducto")
    private int tallaProducto;
    @ColumnInfo(name = "precioProducto")
    private double precioProducto;
    @ColumnInfo(name = "idCategoria" )
    private int idCategoria; //LLave Foranea que hacer referencia la la tb categia


    public Productos() {
    }

    public Productos(int idProducto, String nombreProducto, String marcaProducto, int tallaProducto, double precioProducto, int idCategoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.tallaProducto = tallaProducto;
        this.precioProducto = precioProducto;
        this.idCategoria = idCategoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public int getTallaProducto() {
        return tallaProducto;
    }

    public void setTallaProducto(int tallaProducto) {
        this.tallaProducto = tallaProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
