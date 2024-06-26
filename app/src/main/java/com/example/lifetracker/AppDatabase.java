package com.example.lifetracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { LogEntry.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LogDao logDao();
}
