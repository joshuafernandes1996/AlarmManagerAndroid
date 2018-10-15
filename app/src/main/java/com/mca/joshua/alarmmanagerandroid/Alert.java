package com.mca.joshua.alarmmanagerandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Vibrator;
import android.widget.Toast;

public class Alert extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm On!", Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator)context
                .getSystemService(Context.VIBRATOR_SERVICE);

        vibrator.vibrate(new long[] { 500, 500, 500, 500, 500 },1);

    }
}
