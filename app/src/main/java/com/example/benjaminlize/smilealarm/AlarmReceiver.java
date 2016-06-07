package com.example.benjaminlize.smilealarm;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.example.benjaminlize.smilealarm.data.AlarmContract;

import java.util.Calendar;
/**
 * Created by benjamin.lize on 24/05/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ContentValues contentValues = AlarmContract.queryContentProvider(context);
        AlarmConverter alarmConverter = new AlarmConverter(context,contentValues);
        if (contentValues.get(AlarmContract.AlarmEntry.COLUMN_RECURRENCE).equals(AlarmContract
                .AlarmEntry.FREQ_ONCE)){
            alarmConverter.acknowledgeAlarm();
        }
        Calendar calendarNextAlarm = alarmConverter.getCalendarNextAlarm();
        alarmConverter.setAlarm(calendarNextAlarm);

        setVolumeToMax(context);
        startSmileNowActivity(context, contentValues);

    }

    private void setVolumeToMax(Context context) {
        AudioManager am =
                (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);
    }

    private void startSmileNowActivity(Context context, ContentValues contentValues) {
        Intent startActIntent = new Intent(context, SmileNow.class);
        startActIntent.putExtra(AlarmContract.AlarmEntry.COLUMN_SMILE_TIME, String.valueOf
                (contentValues.get(AlarmContract.AlarmEntry.COLUMN_SMILE_TIME)));
        startActIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startActIntent);
    }
}