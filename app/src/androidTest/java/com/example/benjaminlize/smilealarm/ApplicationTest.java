package com.example.benjaminlize.smilealarm;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

import com.example.benjaminlize.smilealarm.data.AlarmContract;
import com.example.benjaminlize.smilealarm.data.AlarmDbHelper;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testWriteReadAlarmSQLTable() {

        // First step: Get reference to writable database
        AlarmDbHelper dbHelper;
        SQLiteDatabase db;
        Cursor dbCursor;

        int COLUMN_ALARM_TIME    ;
        int COLUMN_RECURRENCE    ;
        int COLUMN_DAY_SUNDAY   ;
        int COLUMN_DAY_MONDAY   ;
        int COLUMN_DAY_TUESDAY  ;
        int COLUMN_DAY_WEDNESDAY;
        int COLUMN_DAY_THURSDAY ;
        int COLUMN_DAY_FRIDAY   ;
        int COLUMN_DAY_SATURDAY ;
        int COLUMN_SMILE_TIME;

        String  RETURNED_ALARM_TIME    ;
        String  RETURNED_RECURRENCE    ;
        int RETURNED_DAY_SUNDAY   ;
        int RETURNED_DAY_MONDAY   ;
        int RETURNED_DAY_TUESDAY  ;
        int RETURNED_DAY_WEDNESDAY;
        int RETURNED_DAY_THURSDAY ;
        int RETURNED_DAY_FRIDAY   ;
        int RETURNED_DAY_SATURDAY ;
        String  RETURNED_SMILE_TIME    ;

        long locationRowId;

        ContentValues testValues;

        dbHelper = new AlarmDbHelper(getContext());
        db = dbHelper.getWritableDatabase();

        // Create ContentValues of what you want to insert
        // (you can use the createNorthPoleLocationValues if you wish)
        testValues = TestUtilities.createFakeAlarmContentValues();

        // Insert ContentValues into database and get a row ID back

        locationRowId = db.insert(AlarmContract.AlarmEntry.TABLE_NAME, null , testValues);

        assertTrue("Error: Failure to insert North Pole Location Values", locationRowId != -1);

        // Query the database and receive a Cursor back

        dbCursor = db.query(AlarmContract.AlarmEntry.TABLE_NAME, null, null, null, null, null, null);

        // Move the cursor to a valid database row
        dbCursor.moveToFirst();

        // Validate data in resulting Cursor with the original ContentValues
        // (you can use the validateCurrentRecord function in TestUtilities to validate the
        // query if you like)

        COLUMN_ALARM_TIME   = dbCursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_ALARM_TIME
        );
        COLUMN_RECURRENCE   = dbCursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_RECURRENCE
        );
        COLUMN_DAY_SUNDAY   = dbCursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_DAY_SUNDAY
        );
        COLUMN_DAY_MONDAY   = dbCursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_DAY_MONDAY
        );
        COLUMN_DAY_TUESDAY  = dbCursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_DAY_TUESDAY
        );
        COLUMN_DAY_WEDNESDAY= dbCursor.getColumnIndex(AlarmContract.AlarmEntry
                .COLUMN_DAY_WEDNESDAY);
        COLUMN_DAY_THURSDAY = dbCursor.getColumnIndex(AlarmContract.AlarmEntry
                .COLUMN_DAY_THURSDAY );
        COLUMN_DAY_FRIDAY   = dbCursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_DAY_FRIDAY
        );
        COLUMN_DAY_SATURDAY = dbCursor.getColumnIndex(AlarmContract.AlarmEntry
                .COLUMN_DAY_SATURDAY );
        COLUMN_SMILE_TIME = dbCursor.getColumnIndex(AlarmContract.AlarmEntry
                .COLUMN_SMILE_TIME );


        RETURNED_ALARM_TIME   = dbCursor.getString (COLUMN_ALARM_TIME   );
        RETURNED_RECURRENCE   = dbCursor.getString (COLUMN_RECURRENCE   );
        RETURNED_DAY_SUNDAY   = dbCursor.getInt (COLUMN_DAY_SUNDAY   );
        RETURNED_DAY_MONDAY   = dbCursor.getInt (COLUMN_DAY_MONDAY   );
        RETURNED_DAY_TUESDAY  = dbCursor.getInt (COLUMN_DAY_TUESDAY  );
        RETURNED_DAY_WEDNESDAY= dbCursor.getInt (COLUMN_DAY_WEDNESDAY);
        RETURNED_DAY_THURSDAY = dbCursor.getInt (COLUMN_DAY_THURSDAY );
        RETURNED_DAY_FRIDAY   = dbCursor.getInt (COLUMN_DAY_FRIDAY   );
        RETURNED_DAY_SATURDAY = dbCursor.getInt (COLUMN_DAY_SATURDAY );
        RETURNED_SMILE_TIME   = dbCursor.getString(COLUMN_SMILE_TIME   );

        assertTrue("ALARM_TIME    DONT MATCH", RETURNED_ALARM_TIME   .equals(TestUtilities.TEST_ALARM_TIME   ));
        assertTrue("RECURRENCE    DONT MATCH", RETURNED_RECURRENCE   .equals(TestUtilities.TEST_RECURRENCE   ));
        assertTrue("DAY_SUNDAY    DONT MATCH", RETURNED_DAY_SUNDAY   == (TestUtilities.TEST_DAY_SUNDAY   ));
        assertTrue("DAY_MONDAY   DONT MATCH", RETURNED_DAY_MONDAY   == (TestUtilities.TEST_DAY_MONDAY));
        assertTrue("DAY_TUESDAY   DONT MATCH", RETURNED_DAY_TUESDAY  == (TestUtilities.TEST_DAY_TUESDAY  ));
        assertTrue("DAY_WEDNESDAY DONT MATCH", RETURNED_DAY_WEDNESDAY== (TestUtilities.TEST_DAY_WEDNESDAY));
        assertTrue("DAY_THURSDAY  DONT MATCH", RETURNED_DAY_THURSDAY == (TestUtilities.TEST_DAY_THURSDAY ));
        assertTrue("DAY_FRIDAY    DONT MATCH", RETURNED_DAY_FRIDAY   == (TestUtilities.TEST_DAY_FRIDAY   ));
        assertTrue("DAY_SATURDAY  DONT MATCH", RETURNED_DAY_SATURDAY == (TestUtilities.TEST_DAY_SATURDAY ));
        assertTrue("SMILE_TIME    DONT MATCH", RETURNED_SMILE_TIME   .equals(TestUtilities.TEST_SMILE_TIME   ));

        // Finally, close the cursor and database
        dbCursor.close();
        db.close();
    }

    public void testBasicContentProviderQuery() {
        TestUtilities.deleteAllRecordsFromDB(getContext());

        // insert our test records into the database
        AlarmDbHelper dbHelper = new AlarmDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createFakeAlarmContentValues();
        long locationRowId = TestUtilities.insertFakeAlarmValues(mContext);

        // Test the basic content provider query
        Cursor alarmCursor = mContext.getContentResolver().query(
                AlarmContract.AlarmEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        alarmCursor.moveToLast();

       // Make sure we get the correct cursor out of the database
        TestUtilities.validateCursor("testBasicLocationQueries, alarm query", alarmCursor,
                testValues);
    }

    public void testmsToHHMMSS() throws ParseException {
        long millis = 3600000;
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        System.out.println(hms);
    }


}