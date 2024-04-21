package com.example.lifetracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class LogOptionsDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Logging Action")
                .setItems(new String[]{"Log Energy", "Log Food"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // "Log Energy" clicked
                                startActivity(new Intent(getActivity(), EnergyLoggingActivity.class));
                                break;
                            case 1: // "Log Food" clicked
                                startActivity(new Intent(getActivity(), FoodLoggingActivity.class));
                                break;
                        }
                    }
                });
        return builder.create();
    }
}
