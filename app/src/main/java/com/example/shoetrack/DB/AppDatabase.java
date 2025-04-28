package com.example.shoetrack.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.shoetrack.DAOs.CategoriasDAO;
import com.example.shoetrack.DAOs.ClienteDAO;
import com.example.shoetrack.DAOs.DetalleVentasDAO;
import com.example.shoetrack.DAOs.EmpleadoDAO;
import com.example.shoetrack.DAOs.MoviminetoInventarioDAO;
import com.example.shoetrack.DAOs.ProductosDAO;
import com.example.shoetrack.DAOs.VentasDAO;
import com.example.shoetrack.Moduls.Categoria;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.Moduls.DetalleVentas;
import com.example.shoetrack.Moduls.Empleados;
import com.example.shoetrack.Moduls.MovimientoIventario;
import com.example.shoetrack.Moduls.Productos;
import com.example.shoetrack.Moduls.Ventas;

<<<<<<< Updated upstream
@Database(entities = {Clientes.class, Empleados.class, Categoria.class, Productos.class, Ventas.class, DetalleVentas.class},
=======
@Database(entities = {Clientes.class, Empleados.class, Categoria.class,
        Productos.class,
        MovimientoIventario.class},
>>>>>>> Stashed changes
        version = 4)
public abstract class AppDatabase extends RoomDatabase {

        private static AppDatabase INSTANCE;

        public abstract ClienteDAO clienteDao();
        public abstract VentasDAO ventasDao();
        public abstract EmpleadoDAO empleadoDao();
        public abstract CategoriasDAO categoriasDAO();
        public abstract ProductosDAO productosDao();
<<<<<<< Updated upstream
        public abstract DetalleVentasDAO detalleVentasDAO();
=======
        public abstract MoviminetoInventarioDAO movimientoInventarioDAO(); // Agregamos el DAO de movimientos
>>>>>>> Stashed changes


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
        @Override
        protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                final SupportSQLiteOpenHelper helper = super.createOpenHelper(config);
                // Enable foreign keys
                helper.getWritableDatabase().execSQL("PRAGMA foreign_keys = ON");
                return helper;
        }
}
