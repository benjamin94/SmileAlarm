package com.example.benjaminlize.smilealarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityTestCase;

import com.example.benjaminlize.smilealarm.data.AlarmContract;
import com.example.benjaminlize.smilealarm.data.AlarmDbHelper;

import java.util.Map;
import java.util.Set;

/**
 * Created by benjamin.lize on 12/05/2016.
 */
public class TestUtilities extends ActivityTestCase {

    static String  TEST_ALARM_TIME    = "Hello alarm time";
    static String  TEST_RECURRENCE    = "Hello recurrence";
    static int     TEST_DAY_SUNDAY    = 0                 ;
    static int     TEST_DAY_MONDAY    = 1                 ;
    static int     TEST_DAY_TUESDAY   = 1                 ;
    static int     TEST_DAY_WEDNESDAY = 1                 ;
    static int     TEST_DAY_THURSDAY  = 1                 ;
    static int     TEST_DAY_FRIDAY    = 1                 ;
    static int     TEST_DAY_SATURDAY  = 1                 ;
    static String  TEST_SMILE_TIME    = "hello smile time";

    public static ContentValues createFakeAlarmContentValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(AlarmContract.AlarmEntry.COLUMN_ALARM_TIME   , TEST_ALARM_TIME   );
        testValues.put(AlarmContract.AlarmEntry.COLUMN_RECURRENCE   , TEST_RECURRENCE   );
        testValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_SUNDAY   , TEST_DAY_SUNDAY   );
        testValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_MONDAY   , TEST_DAY_MONDAY   );
        testValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_TUESDAY  , TEST_DAY_TUESDAY  );
        testValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_WEDNESDAY, TEST_DAY_WEDNESDAY);
        testValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_THURSDAY , TEST_DAY_THURSDAY );
        testValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_FRIDAY   , TEST_DAY_FRIDAY   );
        testValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_SATURDAY , TEST_DAY_SATURDAY );
        testValues.put(AlarmContract.AlarmEntry.COLUMN_SMILE_TIME   , TEST_SMILE_TIME   );

        return testValues;
    }


    static long insertFakeAlarmValues(Context context) {
        // insert our test records into the database
        AlarmDbHelper dbHelper = new AlarmDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createFakeAlarmContentValues();

        long locationRowId;
        locationRowId = db.insert(AlarmContract.AlarmEntry.TABLE_NAME, null, testValues);

        return locationRowId;
    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    public static void deleteAllRecordsFromDB(Context context) {
        AlarmDbHelper dbHelper = new AlarmDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(AlarmContract.AlarmEntry.TABLE_NAME, null, null);
        db.close();
    }

}
