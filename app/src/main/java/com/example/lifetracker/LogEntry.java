package com.example.lifetracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LogEntry {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public LogType logType; // "Energy", "Food", etc.
    public String data; // Data field - format dependent on logType
    public long timestamp;
}
