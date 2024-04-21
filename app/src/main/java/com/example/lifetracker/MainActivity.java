package com.example.lifetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends BaseLoggingActivity {

    private RecyclerView todaysLogsRecyclerView;
    private LogEntriesAdapter logEntriesAdapter; // You'll need to create this adapter
//    private Button addLogEntryButton;
    private LinearLayout loggingOptionsPanel;
    private FloatingActionButton addLogEntryButton;
    private boolean isPanelVisible = false; // To track the panel's state


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the buttons
//        View logEnergyButton = findViewById(R.id.logEnergyButton);
//        View logFoodButton = findViewById(R.id.logFoodButton);
        addLogEntryButton = findViewById(R.id.addLogEntryButton);
//        loggingOptionsPanel = findViewById(R.id.loggingOptionsPanel);

        // Set click listeners for the buttons (Implementation below)
//        logEnergyButton.setOnClickListener(view -> startEnergyLogging());
//        logFoodButton.setOnClickListener(view -> startFoodLogging());
//        addLogEntryButton.setOnClickListener(view -> togglePanel());
        addLogEntryButton.setOnClickListener(view -> showLoggingOptionsDialog());


        findViewById(R.id.mainActivityRootLayout).setOnTouchListener((v, event) -> {
            if (isPanelVisible && event.getAction() == MotionEvent.ACTION_DOWN) {
                // Calculate if click was inside or outside the panel
                Rect panelRect = new Rect();
                loggingOptionsPanel.getGlobalVisibleRect(panelRect);

                if(!panelRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    hidePanel();
                    isPanelVisible = false;
                    return true; // Consume the touch event
                }
            }
            return false;
        });

        todaysLogsRecyclerView = findViewById(R.id.todaysLogsRecyclerView);
        todaysLogsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logEntriesAdapter = new LogEntriesAdapter();
        loadTodaysEntries(); // Implement to fetch and set data on the adapter
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTodaysEntries(); // Reload your data
    }

    private void loadTodaysEntries() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Calendar today = Calendar.getInstance(TimeZone.getDefault());
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                long startOfDayTimestamp = today.getTimeInMillis();

                today.set(Calendar.HOUR_OF_DAY, 23);
                today.set(Calendar.MINUTE, 59);
                today.set(Calendar.SECOND, 59);
                long endOfDayTimestamp = today.getTimeInMillis();

                List<LogEntry> todaysLogs = getDatabase().logDao().getLogsBetween(startOfDayTimestamp, endOfDayTimestamp);

                runOnUiThread(() -> { // Update UI on the main thread
                    logEntriesAdapter.setLogEntries(todaysLogs);
                    todaysLogsRecyclerView.setAdapter(logEntriesAdapter);
                    logEntriesAdapter.notifyDataSetChanged();
                });
            }
        }).start();
    }

    private void showLoggingOptionsDialog() {
//        LogOptionsDialogFragment dialog = new LogOptionsDialogFragment();
//        dialog.show(getSupportFragmentManager(), "LogOptionsDialog");

        LogOptionsBottomSheet bottomSheet = new LogOptionsBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), "LogOptionsBottomSheet");
    }

    // Click listener for Energy Button (Implementation below)
    private void startEnergyLogging() {
        // Start Energy logging activity
        Intent intent = new Intent(this, EnergyLoggingActivity.class);
        startActivity(intent);
    }

    // Click listener for Food Button (Implementation below)
    private void startFoodLogging() {
        // Start Food logging activity
        Intent intent = new Intent(this, FoodLoggingActivity.class);
        startActivity(intent);
    }

    private void showPanel() {
        loggingOptionsPanel.setVisibility(View.VISIBLE);
        addLogEntryButton.setVisibility(View.GONE); // Hide the toggle button
        ObjectAnimator animation = ObjectAnimator.ofFloat(loggingOptionsPanel, "translationY", loggingOptionsPanel.getHeight(), 0);
        animation.setDuration(300); // Adjust duration as needed
        animation.start();
    }

    private void hidePanel() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(loggingOptionsPanel, "translationY", 0, loggingOptionsPanel.getHeight());
        addLogEntryButton.setVisibility(View.VISIBLE); // Hide the toggle button
        animation.setDuration(300); // Adjust duration as needed
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loggingOptionsPanel.setVisibility(View.GONE);
            }
        });
        animation.start();
    }

    // Update your togglePanel method
    private void togglePanel() {
        if (isPanelVisible) {
            hidePanel();
            isPanelVisible = false;
        } else {
            showPanel();
            isPanelVisible = true;
        }
    }

}
