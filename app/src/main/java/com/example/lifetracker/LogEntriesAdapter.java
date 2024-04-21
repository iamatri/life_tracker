package com.example.lifetracker;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

// ... Other imports

public class LogEntriesAdapter extends RecyclerView.Adapter<LogEntriesAdapter.LogViewHolder> {

    private List<LogEntry> logEntries; // Assuming you have your LogEntry class

    // ... (Constructor to initialize logEntries)

    public void setLogEntries(List<LogEntry> newLogEntries) {
        this.logEntries = newLogEntries;
    }

    // ... Implement the following methods (Required by RecyclerView.Adapter)
    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        int layoutId;
        LogViewHolder viewHolder = new LogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card, parent, false));
        switch (LogType.values()[viewType]) {
            case ENERGY: // Assuming 0 is for 'Energy'
                layoutId = R.layout.energy_card;
                break;
            case FOOD: // Assuming 1 is for 'Food'
                layoutId = R.layout.food_card;
                break;
            default:
                layoutId = R.layout.food_card; // A default layout
        }
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        viewHolder.setView(itemView);
        return new LogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        LogEntry currentEntry = logEntries.get(position); // Assuming logEntries is your List
        if (currentEntry.logType == LogType.ENERGY) {
            holder.iconView.setImageResource(R.drawable.energy);
            holder.energyProgressBar.setProgress(20 * Integer.parseInt(currentEntry.data));
        } else if (currentEntry.logType == LogType.FOOD) {
            holder.dataView.setText(currentEntry.data);
            holder.iconView.setImageResource(R.drawable.food);
        }
    }

    @Override
    public int getItemCount() {
        return logEntries.size();
    }

    public int getItemViewType(int position) {
        // Your logic to determine the viewType based on the data at 'position'
        return logEntries.get(position).logType.ordinal();
    }

    // You'll need a ViewHolder class as well
    public static class LogViewHolder extends RecyclerView.ViewHolder {
        // Define TextViews here to hold logType, data, timestamp
        TextView logTypeView;
        TextView dataView;
        TextView timestampView;

        ImageView iconView;

        ProgressBar energyProgressBar;

        public LogViewHolder(View itemView) {
            super(itemView);
            setView(itemView);
        }

        public void setView(View itemView) {
            logTypeView = itemView.findViewById(R.id.logTypeTextView); // Adjust your IDs
            dataView = itemView.findViewById(R.id.dataTextView);
            timestampView = itemView.findViewById(R.id.timestampTextView);
            iconView = itemView.findViewById(R.id.logTypeIconImageView);
            energyProgressBar = itemView.findViewById(R.id.energyProgressBar);
        }
    }
}
