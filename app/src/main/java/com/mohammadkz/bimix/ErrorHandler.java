package com.mohammadkz.bimix;

import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mohammadkz.bimix.Activity.LoginActivity;

public class ErrorHandler {

    public static void alertDialog_connectionFail(Context context) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(R.string.connection_failed_title);
        builder.setMessage(R.string.connection_failed_text);
        String positive = "بستن";

        // have one btn ==> close
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.show();
    }

    public static void alertDialog_error_login(Context context) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(R.string.faileLogin_title);
        builder.setMessage(R.string.faileLogin_text);
        String positive = "بستن";

        // have one btn ==> close
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.show();
    }


}
