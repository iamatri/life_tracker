package com.example.lifetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LogOptionsBottomSheet extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet_options, container, false);

        Button logEnergyButton = view.findViewById(R.id.logEnergyButton);
        Button logFoodButton = view.findViewById(R.id.logFoodButton);

        logEnergyButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), EnergyLoggingActivity.class));
            dismiss(); // Close the bottom sheet after launching activity
        });

        logFoodButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), FoodLoggingActivity.class));
            dismiss(); // Close the bottom sheet after launching activity
        });

        return view;
    }
}
