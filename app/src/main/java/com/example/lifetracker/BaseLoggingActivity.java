package com.example.lifetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import kotlinx.coroutines.GlobalScope;

import android.content.DialogInterface;
import android.os.Bundle;

public abstract class BaseLoggingActivity extends AppCompatActivity {

    protected AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my-logs-database")
                .allowMainThreadQueries() // Temporary - don't do this in production!
                .build();
    }

    protected void insertLog(LogEntry logEntry) {
        new Thread(() -> database.logDao().insertLog(logEntry)).start();

        new AlertDialog.Builder(this)
                .setTitle("Log Saved!")
                .setMessage("Your energy log has been saved.")
                .setCancelable(false) // Prevent user from dismissing without confirmation
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish(); // This will close the current activity (EnergyLoggingActivity)
                    }
                })
                .show();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
