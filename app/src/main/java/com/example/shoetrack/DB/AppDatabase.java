package com.example.shoetrack.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoetrack.DAOs.CategoriasDAO;
import com.example.shoetrack.DAOs.ClienteDAO;
import com.example.shoetrack.DAOs.DetalleVentasDAO;
import com.example.shoetrack.DAOs.EmpleadoDAO;
import com.example.shoetrack.DAOs.ProductosDAO;
import com.example.shoetrack.DAOs.VentasDAO;
import com.example.shoetrack.Moduls.Categoria;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.Moduls.DetalleVentas;
import com.example.shoetrack.Moduls.Empleados;
import com.example.shoetrack.Moduls.Productos;
import com.example.shoetrack.Moduls.Ventas;

@Database(entities = {Clientes.class, Empleados.class, Categoria.class, Productos.class, Ventas.class, DetalleVentas.class},
        version = 4)
public abstract class AppDatabase extends RoomDatabase {

        private static AppDatabase INSTANCE;

        public abstract ClienteDAO clienteDao();
        public abstract VentasDAO ventasDao();
        public abstract EmpleadoDAO empleadoDao();
        public abstract CategoriasDAO categoriasDAO();
        public abstract ProductosDAO productosDao();
        public abstract DetalleVentasDAO detalleVentasDAO();


        public static synchronized AppDatabase getInstance(Context context) {
                if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                        context.getApplicationContext(),
                                        AppDatabase.class,
                                        "zapateria_db"
                                )
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries() // Solo para pruebas
                                .build();
                }
                return INSTANCE;
        }
}
