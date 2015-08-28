package com.larizon.prabu.AlertDialog;

/**
 * Created by EnoByte on 5/23/2015.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.larizon.prabu.MainActivity;
import com.larizon.prabu.R;


public class AlertDialogManager {

    public void showAlertDialog(final Context context, String title, String message, Boolean status) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        if (status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.pass : R.drawable.fail);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent();
                i.setClass(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

}