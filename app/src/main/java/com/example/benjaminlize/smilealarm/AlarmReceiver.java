package com.example.benjaminlize.smilealarm;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.example.benjaminlize.smilealarm.data.AlarmContract;

/**
 * Created by benjamin.lize on 24/05/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startActIntent = new Intent(context, SmileNow.class);
        startActIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        ContentValues contentValues = AlarmContract.queryContentProvider(context);

        //AlarmConverter alarmConverter = new AlarmConverter(context,contentValues);
        //ContentValues acknowledgedValues = alarmConverter.acknowledgeAlarm(contentValues);
        //Calendar calendarNextAlarm = alarmConverter.getCalendarNextAlarm();

        if(contentValues != null){
            String RETURNED_ALARM_TIME       = (String) contentValues.get((AlarmContract.AlarmEntry.COLUMN_ALARM_TIME));
            String RETURNED_ALARM_TIME_MILLIS= (String) contentValues.get((AlarmContract.AlarmEntry.COLUMN_ALARM_MILLS));
            String RETURNED_RECURRENCE = (String) contentValues.get((AlarmContract.AlarmEntry.COLUMN_RECURRENCE   ));
            int RETURNED_DAY_SUNDAY    = (int) contentValues.get((AlarmContract.AlarmEntry.COLUMN_DAY_SUNDAY   ));
            int RETURNED_DAY_MONDAY    = (int) contentValues.get((AlarmContract.AlarmEntry.COLUMN_DAY_MONDAY   ));
            int RETURNED_DAY_TUESDAY   = (int) contentValues.get((AlarmContract.AlarmEntry.COLUMN_DAY_TUESDAY  ));
            int RETURNED_DAY_WEDNESDAY = (int) contentValues.get((AlarmContract.AlarmEntry.COLUMN_DAY_WEDNESDAY));
            int RETURNED_DAY_THURSDAY  = (int) contentValues.get((AlarmContract.AlarmEntry.COLUMN_DAY_THURSDAY ));
            int RETURNED_DAY_FRIDAY    = (int) contentValues.get((AlarmContract.AlarmEntry.COLUMN_DAY_FRIDAY   ));
            int RETURNED_DAY_SATURDAY  = (int) contentValues.get((AlarmContract.AlarmEntry.COLUMN_DAY_SATURDAY ));
            String RETURNED_SMILE_TIME = (String) contentValues.get((AlarmContract.AlarmEntry.COLUMN_SMILE_TIME   ));
        context.startActivity(startActIntent);
        }
    }
}
