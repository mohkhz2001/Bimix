package com.mohammadkz.bimix.Notification;


import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;


public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        String code = intent.getStringExtra("payCode");


        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("payCode", code);

        clipboard.setPrimaryClip(clip);
        Log.e("awdawdawd" , " "  + code);

        Intent intent1 = new Intent(context , PayActivity.class);
        context.startActivity(intent1);

    }

}
