package com.example.benjaminlize.smilealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.example.benjaminlize.smilealarm.data.AlarmContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by benjamin.lize on 03/06/2016.
 */
public class AlarmConverter {

    public final static int ALARM_PENDING_INTENT_ID = 1;

    ContentValues mScreenValues;
    Context mContext;

    public AlarmConverter(Context mContext, ContentValues mScreenValues) {
        this.mScreenValues = mScreenValues;
        this.mContext = mContext;
    }

    protected void scheduleAlarm(Calendar targetCal){
        targetCal.set(Calendar.SECOND,0);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ALARM_PENDING_INTENT_ID,
                intent, 0);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    static String getDay(Calendar calendarWithAlarm) {
        String contentValueKey = "";
        switch (calendarWithAlarm.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:
                contentValueKey = AlarmContract.AlarmEntry.COLUMN_DAY_SUNDAY   ;
                break;
            case Calendar.MONDAY:
                contentValueKey = AlarmContract.AlarmEntry.COLUMN_DAY_MONDAY   ;
                break;
            case Calendar.TUESDAY:
                contentValueKey = AlarmContract.AlarmEntry.COLUMN_DAY_TUESDAY  ;
                break;
            case Calendar.WEDNESDAY:
                contentValueKey = AlarmContract.AlarmEntry.COLUMN_DAY_WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                contentValueKey = AlarmContract.AlarmEntry.COLUMN_DAY_THURSDAY ;
                break;
            case Calendar.FRIDAY:
                contentValueKey = AlarmContract.AlarmEntry.COLUMN_DAY_FRIDAY ;
                break;
            case Calendar.SATURDAY:
                contentValueKey = AlarmContract.AlarmEntry.COLUMN_DAY_SATURDAY   ;
                break;
        }
        return contentValueKey;
    }

    protected static List<Integer> getAlarmDayList(Calendar calendarNow,
                                                   ContentValues screenValues) {

        int today = calendarNow.get(Calendar.DAY_OF_WEEK);

        int sun = 0;
        int mon = 0;
        int tue = 0;
        int wed = 0;
        int thu = 0;
        int fri = 0;
        int sat = 0;

        List<Integer> list = new ArrayList<>(7);
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_SUNDAY) == 1) list.add
                (Calendar.SUNDAY);    sun = 1;
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_SUNDAY) == 0) list.add(-1*Calendar.SUNDAY);
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_MONDAY)== 1) list.add(Calendar.MONDAY);    mon = 1;
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_MONDAY)== 0) list.add(-1*Calendar.MONDAY);
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_TUESDAY)== 1) list.add(Calendar.TUESDAY);   tue = 1;
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_TUESDAY)== 0) list.add(-1*Calendar.TUESDAY);
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_WEDNESDAY)== 1) list.add(Calendar.WEDNESDAY); wed = 1;
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_WEDNESDAY)== 0) list.add(-1*Calendar.WEDNESDAY);
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_THURSDAY)== 1) list.add(Calendar.THURSDAY);    thu = 1;
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_THURSDAY)== 0) list.add(-1*Calendar.THURSDAY);
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_FRIDAY)== 1) list.add(Calendar.FRIDAY);      fri = 1;
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_FRIDAY)== 0) list.add(-1*Calendar.FRIDAY);
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_SATURDAY)== 1) list.add(Calendar.SATURDAY);    sat = 1;
        if ((int)screenValues.get(AlarmContract.AlarmEntry.COLUMN_DAY_SATURDAY)== 0) list.add(-1*Calendar.SATURDAY);

        List rearrangedList = new ArrayList(8);
        int day = today;

        for (int i = 0; i < 7 ; i++) {
            Integer value = list.get(day-1);
            rearrangedList.add(value);
            day = getNextDay(day);
        }
        return rearrangedList;
    }

    private static int getNextDay(int day) {
        if (day == Calendar.SATURDAY){
            day = Calendar.SUNDAY;
        }else day++;
        return day;
    }

    protected Calendar getCalendarNextAlarm() {
        Calendar calendarNow = Calendar.getInstance();
        Calendar calendarChosen = createCalendarChosen(String.valueOf(mScreenValues.get(AlarmContract
                .AlarmEntry.COLUMN_ALARM_TIME)));
        List<Integer> list = getAlarmDayList(calendarNow,mScreenValues);
        Calendar calendar = calendarChosen.getInstance();
        if (list.get(0)>0){
            if (calendarChosen.compareTo(calendarNow) == -1){
                //AlarmConverter is in 7 days
                calendar.add(Calendar.DAY_OF_MONTH,7);
                return calendar;
            } else {
                //Alarm is today
                return calendar;
            }
        } else {
            //Next AlarmConverter is not Today
            for (int day = 1; day < 7; day++) {
                if(list.get(day)>0){
                    Calendar calendarPlusi = calendarChosen.getInstance();
                    calendarPlusi.add(Calendar.DAY_OF_MONTH,day);
                    return calendar;
                }
            }
        }
        return null;
    }

    private Calendar createCalendarChosen(String alarmTime) {
        Calendar calendar = Calendar.getInstance();
        alarmTime = alarmTime;
        CharSequence hour = alarmTime.subSequence(0,2);
        int hr = Integer.parseInt(hour.toString());
        CharSequence minute = alarmTime.subSequence(3,5);
        int min = Integer.parseInt(minute.toString());

        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        return calendar;
    }

    protected void setAlarm(Calendar calendarWithAlarm) {
        //acknowledge alarm and set AlarmConverter
        String screenValueToToggle = getDay(calendarWithAlarm);
        mScreenValues.put(screenValueToToggle,0);
        mScreenValues.put(AlarmContract.AlarmEntry.COLUMN_ALARM_TIME_MILLIS,calendarWithAlarm.getTimeInMillis());
        scheduleAlarm(calendarWithAlarm);
        updateCP(mScreenValues, mContext);
    }

    protected static void updateCP(ContentValues screenValues,Context context) {
        int position = 0;
        position = context.getContentResolver().update(
                AlarmContract.AlarmEntry.CONTENT_URI,
                screenValues,
                null,
                null
        );
    }
}
