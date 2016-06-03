/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.benjaminlize.smilealarm.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

import com.example.benjaminlize.smilealarm.MainActivity;

/**
 * Defines table and column names for the weather database.
 */
/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Defines table and column names for the weather database.
 */
public class AlarmContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.benjaminlize.smilealarm";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_ALARM_DETAILS = "alarm";
    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static ContentValues queryContentProvider(Context context) {

        int COLUMN_ALARM_TIME;
        int COLUMN_ALARM_TIME_MILLIS;
        int COLUMN_RECURRENCE;
        int COLUMN_DAY_SUNDAY;
        int COLUMN_DAY_MONDAY;
        int COLUMN_DAY_TUESDAY;
        int COLUMN_DAY_WEDNESDAY;
        int COLUMN_DAY_THURSDAY;
        int COLUMN_DAY_FRIDAY;
        int COLUMN_DAY_SATURDAY;
        int COLUMN_SMILE_TIME;


        String RETURNED_ALARM_TIME = "00:00 am";
        String RETURNED_ALARM_TIME_MILLIS = "0";
        String RETURNED_RECURRENCE = "theRecurrence";
        int RETURNED_DAY_SUNDAY = 0;
        int RETURNED_DAY_MONDAY = 0;
        int RETURNED_DAY_TUESDAY = 0;
        int RETURNED_DAY_WEDNESDAY = 0;
        int RETURNED_DAY_THURSDAY = 0;
        int RETURNED_DAY_FRIDAY = 0;
        int RETURNED_DAY_SATURDAY = 0;
        String RETURNED_SMILE_TIME = "5secs";

        Cursor cursor = context.getContentResolver().query(
                AlarmEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        ContentValues contentValues = null;

        if (cursor.moveToLast()) {
            cursor.moveToLast();

            COLUMN_ALARM_TIME = cursor.getColumnIndex(AlarmEntry.COLUMN_ALARM_TIME
            );
            COLUMN_ALARM_TIME_MILLIS = cursor.getColumnIndex(AlarmEntry.COLUMN_ALARM_MILLS
            );
            COLUMN_RECURRENCE = cursor.getColumnIndex(AlarmEntry.COLUMN_RECURRENCE
            );
            COLUMN_DAY_SUNDAY = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY_SUNDAY
            );
            COLUMN_DAY_MONDAY = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY_MONDAY
            );
            COLUMN_DAY_TUESDAY = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY_TUESDAY
            );
            COLUMN_DAY_WEDNESDAY = cursor.getColumnIndex(AlarmEntry
                    .COLUMN_DAY_WEDNESDAY);
            COLUMN_DAY_THURSDAY = cursor.getColumnIndex(AlarmEntry
                    .COLUMN_DAY_THURSDAY);
            COLUMN_DAY_FRIDAY = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY_FRIDAY
            );
            COLUMN_DAY_SATURDAY = cursor.getColumnIndex(AlarmEntry
                    .COLUMN_DAY_SATURDAY);
            COLUMN_SMILE_TIME = cursor.getColumnIndex(AlarmEntry
                    .COLUMN_SMILE_TIME);

            RETURNED_ALARM_TIME        = cursor.getString(COLUMN_ALARM_TIME);
            RETURNED_ALARM_TIME_MILLIS = cursor.getString(COLUMN_ALARM_TIME_MILLIS);
            RETURNED_RECURRENCE        = cursor.getString(COLUMN_RECURRENCE);
            RETURNED_DAY_SUNDAY        = cursor.getInt(COLUMN_DAY_SUNDAY);
            RETURNED_DAY_MONDAY        = cursor.getInt(COLUMN_DAY_MONDAY);
            RETURNED_DAY_TUESDAY       = cursor.getInt(COLUMN_DAY_TUESDAY);
            RETURNED_DAY_WEDNESDAY     = cursor.getInt(COLUMN_DAY_WEDNESDAY);
            RETURNED_DAY_THURSDAY      = cursor.getInt(COLUMN_DAY_THURSDAY);
            RETURNED_DAY_FRIDAY        = cursor.getInt(COLUMN_DAY_FRIDAY);
            RETURNED_DAY_SATURDAY      = cursor.getInt(COLUMN_DAY_SATURDAY);
            RETURNED_SMILE_TIME        = cursor.getString(COLUMN_SMILE_TIME);

            contentValues = new ContentValues();

            contentValues.put(AlarmEntry.COLUMN_ALARM_TIME       , MainActivity.getAlarmTime(RETURNED_ALARM_TIME));
            contentValues.put(AlarmEntry.COLUMN_ALARM_MILLS,RETURNED_ALARM_TIME_MILLIS);
            contentValues.put(AlarmEntry.COLUMN_RECURRENCE       ,RETURNED_RECURRENCE       );
            contentValues.put(AlarmEntry.COLUMN_DAY_SUNDAY       ,RETURNED_DAY_SUNDAY       );
            contentValues.put(AlarmEntry.COLUMN_DAY_MONDAY       ,RETURNED_DAY_MONDAY       );
            contentValues.put(AlarmEntry.COLUMN_DAY_TUESDAY      ,RETURNED_DAY_TUESDAY      );
            contentValues.put(AlarmEntry.COLUMN_DAY_WEDNESDAY    ,RETURNED_DAY_WEDNESDAY    );
            contentValues.put(AlarmEntry.COLUMN_DAY_THURSDAY     ,RETURNED_DAY_THURSDAY     );
            contentValues.put(AlarmEntry.COLUMN_DAY_FRIDAY       ,RETURNED_DAY_FRIDAY       );
            contentValues.put(AlarmEntry.COLUMN_DAY_SATURDAY     ,RETURNED_DAY_SATURDAY     );
            contentValues.put(AlarmEntry.COLUMN_SMILE_TIME       ,RETURNED_SMILE_TIME       );
        }

        return contentValues;
    }

    /* Inner class that defines the table contents of the location table */
    public static final class AlarmEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ALARM_DETAILS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ALARM_DETAILS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ALARM_DETAILS;

        // Table name
        public static final String TABLE_NAME = "alarm_table";

        public static final String COLUMN_ALARM_TIME    = "alarm_time";
        public static final String COLUMN_ALARM_MILLS = "alarm_time_millis";

        public static final String COLUMN_RECURRENCE    = "recurrence";

        public static final String COLUMN_DAY_SUNDAY    = "sunday";
        public static final String COLUMN_DAY_MONDAY    = "monday";
        public static final String COLUMN_DAY_TUESDAY   = "tuesday";
        public static final String COLUMN_DAY_WEDNESDAY = "wednesday";
        public static final String COLUMN_DAY_THURSDAY  = "thursday";
        public static final String COLUMN_DAY_FRIDAY    = "friday";
        public static final String COLUMN_DAY_SATURDAY  = "saturday";

        public static final String COLUMN_SMILE_TIME    = "smile_time";

        public final static String SMILETIME_x5 = "5";
        public final static String SMILETIME_x10 = "10";

        public final static String FREQ_ONCE = "ONCE";
        public final static String FREQ_REPEAT = "REPEAT";

        public static Uri buildAlarmUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}