package com.example.shoetrack.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoetrack.DAOs.ClienteDAO;
import com.example.shoetrack.DAOs.EmpleadoDAO;
import com.example.shoetrack.Moduls.Clientes;
import com.example.shoetrack.Moduls.Empleados;

@Database(entities = {Clientes.class, Empleados.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
        private static AppDatabase INSTANCE;

        public abstract ClienteDAO clienteDao();
        public abstract EmpleadoDAO empleadoDao();
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
