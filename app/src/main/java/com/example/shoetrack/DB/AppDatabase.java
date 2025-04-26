package com.example.shoetrack.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class AppDatabase extends RoomDatabase {
        private static AppDatabase INSTANCE;
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
