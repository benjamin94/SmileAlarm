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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.benjaminlize.smilealarm.data.AlarmContract.AlarmEntry;

/**
 * Manages a local database for weather data.
 */
public class AlarmDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "AlarmDbHelper";

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "alarm.db";

    public AlarmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + AlarmEntry.TABLE_NAME + " (" +

                // the ID of the alarm entry and data types
                AlarmEntry.COLUMN_ALARM_TIME    + " TEXT NOT NULL, "    +
                AlarmEntry.COLUMN_ALARM_MILLS + " TEXT NOT NULL, "    +
                AlarmEntry.COLUMN_RECURRENCE    + " TEXT NOT NULL, "    +
                AlarmEntry.COLUMN_DAY_SUNDAY    + " BOOLEAN NOT NULL, " +
                AlarmEntry.COLUMN_DAY_MONDAY    + " BOOLEAN NOT NULL, " +
                AlarmEntry.COLUMN_DAY_TUESDAY   + " BOOLEAN NOT NULL, " +
                AlarmEntry.COLUMN_DAY_WEDNESDAY + " BOOLEAN NOT NULL, " +
                AlarmEntry.COLUMN_DAY_THURSDAY  + " BOOLEAN NOT NULL, " +
                AlarmEntry.COLUMN_DAY_FRIDAY    + " BOOLEAN NOT NULL, " +
                AlarmEntry.COLUMN_DAY_SATURDAY  + " BOOLEAN NOT NULL, " +
                AlarmEntry.COLUMN_SMILE_TIME    + " TEXT NOT NULL); "   ;

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);



    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AlarmEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
