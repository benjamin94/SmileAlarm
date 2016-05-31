package com.example.benjaminlize.smilealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by benjamin.lize on 24/05/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        Intent startActIntent = new Intent(context, SmileNow.class);
        startActIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startActIntent);

    }
}
