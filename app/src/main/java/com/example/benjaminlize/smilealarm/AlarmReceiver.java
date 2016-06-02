package com.example.benjaminlize.smilealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by benjamin.lize on 24/05/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startActIntent = new Intent(context, SmileNow.class);
        startActIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startActIntent);

    }
}
