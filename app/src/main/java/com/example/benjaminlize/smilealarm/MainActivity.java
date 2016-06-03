package com.example.benjaminlize.smilealarm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.benjaminlize.smilealarm.data.AlarmContract;

import static com.example.benjaminlize.smilealarm.ToggleButtonSA.initToggleButton;

public class MainActivity extends AppCompatActivity {

    TextView alarmTime;
    TextView timeToNextAlarm;

    ToggleButton sunday   ;
    ToggleButton monday   ;
    ToggleButton tuesday  ;
    ToggleButton wednesday;
    ToggleButton thursday ;
    ToggleButton friday   ;
    ToggleButton saturday ;

    TextView smileTime;
    ImageView smileTimeiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateViews();
    }

    private void populateViews() {

        int COLUMN_ALARM_TIME    ;
        int COLUMN_ALARM_TIME_MILLIS    ;
        int COLUMN_RECURRENCE    ;
        int COLUMN_DAY_SUNDAY   ;
        int COLUMN_DAY_MONDAY   ;
        int COLUMN_DAY_TUESDAY  ;
        int COLUMN_DAY_WEDNESDAY;
        int COLUMN_DAY_THURSDAY ;
        int COLUMN_DAY_FRIDAY   ;
        int COLUMN_DAY_SATURDAY ;
        int COLUMN_SMILE_TIME;


        String RETURNED_ALARM_TIME         = "00:00 am"       ;
        String RETURNED_ALARM_TIME_MILLIS  = "0"       ;
        String RETURNED_RECURRENCE         = "theRecurrence"  ;
        int RETURNED_DAY_SUNDAY            = 0;
        int RETURNED_DAY_MONDAY            = 0;
        int RETURNED_DAY_TUESDAY           = 0;
        int RETURNED_DAY_WEDNESDAY         = 0;
        int RETURNED_DAY_THURSDAY          = 0;
        int RETURNED_DAY_FRIDAY            = 0;
        int RETURNED_DAY_SATURDAY          = 0;
        String  RETURNED_SMILE_TIME        = "5secs"   ;

        Cursor cursor = getContentResolver().query(
                AlarmContract.AlarmEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToLast()) {
            cursor.moveToLast();

            COLUMN_ALARM_TIME   = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_ALARM_TIME
            );
            COLUMN_ALARM_TIME_MILLIS   = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_ALARM_TIME_MILLIS
            );
            COLUMN_RECURRENCE   = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_RECURRENCE
            );
            COLUMN_DAY_SUNDAY   = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_DAY_SUNDAY
            );
            COLUMN_DAY_MONDAY   = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_DAY_MONDAY
            );
            COLUMN_DAY_TUESDAY  = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_DAY_TUESDAY
            );
            COLUMN_DAY_WEDNESDAY= cursor.getColumnIndex(AlarmContract.AlarmEntry
                    .COLUMN_DAY_WEDNESDAY);
            COLUMN_DAY_THURSDAY = cursor.getColumnIndex(AlarmContract.AlarmEntry
                    .COLUMN_DAY_THURSDAY );
            COLUMN_DAY_FRIDAY   = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_DAY_FRIDAY
            );
            COLUMN_DAY_SATURDAY = cursor.getColumnIndex(AlarmContract.AlarmEntry
                    .COLUMN_DAY_SATURDAY );
            COLUMN_SMILE_TIME = cursor.getColumnIndex(AlarmContract.AlarmEntry
                    .COLUMN_SMILE_TIME );

            RETURNED_ALARM_TIME   = cursor.getString (COLUMN_ALARM_TIME   );
            RETURNED_ALARM_TIME_MILLIS   = cursor.getString (COLUMN_ALARM_TIME_MILLIS   );
            RETURNED_RECURRENCE   = cursor.getString (COLUMN_RECURRENCE   );
            RETURNED_DAY_SUNDAY   = cursor.getInt (COLUMN_DAY_SUNDAY   );
            RETURNED_DAY_MONDAY   = cursor.getInt (COLUMN_DAY_MONDAY   );
            RETURNED_DAY_TUESDAY  = cursor.getInt (COLUMN_DAY_TUESDAY  );
            RETURNED_DAY_WEDNESDAY= cursor.getInt (COLUMN_DAY_WEDNESDAY);
            RETURNED_DAY_THURSDAY = cursor.getInt (COLUMN_DAY_THURSDAY );
            RETURNED_DAY_FRIDAY   = cursor.getInt (COLUMN_DAY_FRIDAY   );
            RETURNED_DAY_SATURDAY = cursor.getInt (COLUMN_DAY_SATURDAY );
            RETURNED_SMILE_TIME   = cursor.getString(COLUMN_SMILE_TIME   );

            alarmTime.setText(RETURNED_ALARM_TIME);
            long now = System.currentTimeMillis();
            long selected = Long.parseLong(RETURNED_ALARM_TIME_MILLIS);
            long timeDifference = selected - now;
            timeToNextAlarm.setText("Next alarm in: " + String.valueOf(timeDifference) + " ms");
            initToggleButton (RETURNED_DAY_SUNDAY   ,sunday   );
            initToggleButton (RETURNED_DAY_MONDAY   ,monday   );
            initToggleButton (RETURNED_DAY_TUESDAY  ,tuesday  );
            initToggleButton (RETURNED_DAY_WEDNESDAY,wednesday);
            initToggleButton (RETURNED_DAY_THURSDAY ,thursday );
            initToggleButton (RETURNED_DAY_FRIDAY   ,friday   );
            initToggleButton (RETURNED_DAY_SATURDAY ,saturday );
            smileTime.setText("Smile " + RETURNED_SMILE_TIME + " seconds to dismiss alarm");
            if (RETURNED_SMILE_TIME == AlarmContract.AlarmEntry.SMILETIME_x5){
                smileTimeiv.setImageResource(R.drawable.smiley1);
            } else if(RETURNED_SMILE_TIME == AlarmContract.AlarmEntry.SMILETIME_x10){
                smileTimeiv.setImageResource(R.drawable.supersmiley1);
            }
        }
    }

    private void initViews() {
        alarmTime = (TextView)findViewById(R.id.alarm_time);
        timeToNextAlarm = (TextView)findViewById(R.id.next_alarm_time);
        sunday = (ToggleButton)findViewById(R.id.togglebuttonsun);
        sunday.setOnClickListener(new ToggleButtonSA(sunday));
        monday = (ToggleButton)findViewById(R.id.togglebuttonmon);
        monday.setOnClickListener(new ToggleButtonSA(monday));
        tuesday = (ToggleButton)findViewById(R.id.togglebuttontue);
        tuesday.setOnClickListener(new ToggleButtonSA(tuesday));
        wednesday = (ToggleButton)findViewById(R.id.togglebuttonwed);
        wednesday.setOnClickListener(new ToggleButtonSA(wednesday));
        thursday = (ToggleButton)findViewById(R.id.togglebuttonthu);
        thursday.setOnClickListener(new ToggleButtonSA(thursday));
        friday = (ToggleButton)findViewById(R.id.togglebuttonfri);
        friday.setOnClickListener(new ToggleButtonSA(friday));
        saturday = (ToggleButton)findViewById(R.id.togglebuttonsat);
        saturday.setOnClickListener(new ToggleButtonSA(saturday));
        smileTime = (TextView)findViewById(R.id.smileTime_main);
        smileTimeiv = (ImageView)findViewById(R.id.smile_image_main);


        alarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditAlarm.class));
            }
        });


    }


}
