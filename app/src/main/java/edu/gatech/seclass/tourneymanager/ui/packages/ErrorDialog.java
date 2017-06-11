package edu.gatech.seclass.tourneymanager.ui.packages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by justinp on 3/10/17.
 */

public class ErrorDialog extends DialogFragment {
    private String message = "";
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing.
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
