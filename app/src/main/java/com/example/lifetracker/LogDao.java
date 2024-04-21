package com.example.lifetracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
interface LogDao {
    @Insert
    void insertLog(LogEntry logEntry);

    @Query("SELECT * FROM LogEntry WHERE timestamp BETWEEN :startTime AND :endTime")
    List<LogEntry> getLogsBetween(long startTime, long endTime);
}
