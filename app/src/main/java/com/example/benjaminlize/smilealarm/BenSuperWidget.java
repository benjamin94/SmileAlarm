package com.example.benjaminlize.smilealarm;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.benjaminlize.smilealarm.data.AlarmContract;

import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class BenSuperWidget extends AppWidgetProvider {

    public static String YOUR_AWESOME_ACTION = "YourAwesomeAction";

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(YOUR_AWESOME_ACTION)) {
            //do some really cool stuff here
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            ContentValues contentValues = AlarmContract.queryContentProvider(context);

            AlarmConverter alarmConverter = new AlarmConverter(context,contentValues);
            Calendar calendarNextAlarm = alarmConverter.getCalendarNextAlarm();

            CharSequence widgetText;

            if (calendarNextAlarm == null) {
                widgetText = context.getString(R.string.appwidget_text);
            } else {
                long now = System.currentTimeMillis();
                long selected = Long.parseLong(contentValues.get
                        (AlarmContract.AlarmEntry.COLUMN_ALARM_MILLS).toString());
                long timeDifference = selected - now;
                if (timeDifference < 0){
                    widgetText = context.getString(R.string.appwidget_text);
                } else {
                    String nextAlarmIn = AlarmContract.msToHHMMSS(timeDifference);
                    widgetText = "next alarm in: " + nextAlarmIn.subSequence(0,5);
                }
            }
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ben_super_widget);
            views.setTextViewText(R.id.appwidget_text, widgetText);

            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), BenSuperWidget.class
                    .getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetIds, views);
        }
    }
}

