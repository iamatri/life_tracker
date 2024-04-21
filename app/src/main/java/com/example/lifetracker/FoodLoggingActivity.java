package com.example.lifetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// ... (You'll likely need imports for database/entity classes)

public class FoodLoggingActivity extends BaseLoggingActivity {

    private EditText foodDescription;
    private Button saveFoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_logging);

        // Initialize views
        foodDescription = findViewById(R.id.foodDescription);
        saveFoodButton = findViewById(R.id.saveFoodButton);

        saveFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFoodLog();
            }
        });
    }

    private void saveFoodLog() {
        LogEntry logEntry = new LogEntry();
        logEntry.logType = LogType.FOOD;
        logEntry.data = foodDescription.getText().toString(); // You might want better serialization
        logEntry.timestamp = System.currentTimeMillis();
        insertLog(logEntry); // Use the helper method from the base class
    }
}
