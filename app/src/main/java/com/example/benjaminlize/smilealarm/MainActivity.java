package com.example.benjaminlize.smilealarm;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjaminlize.smilealarm.data.AlarmContract;
import com.example.benjaminlize.smilealarm.data.AlarmContract.AlarmEntry;

public class MainActivity extends AppCompatActivity {

    TextView alarmTime;
    TextView timeToNextAlarm;

    Button sunday   ;
    Button monday   ;
    Button tuesday  ;
    Button wednesday;
    Button thursday ;
    Button friday   ;
    Button saturday ;

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

        ContentValues contentValues = AlarmContract.queryContentProvider(this);

        if(contentValues != null){

            String RETURNED_ALARM_TIME       = (String) contentValues.get((AlarmEntry.COLUMN_ALARM_TIME));
            String RETURNED_ALARM_TIME_MILLIS= (String) contentValues.get((AlarmEntry.COLUMN_ALARM_MILLS));
            String RETURNED_RECURRENCE = (String) contentValues.get((AlarmEntry.COLUMN_RECURRENCE   ));
            int RETURNED_DAY_SUNDAY    = (int) contentValues.get((AlarmEntry.COLUMN_DAY_SUNDAY   ));
            int RETURNED_DAY_MONDAY    = (int) contentValues.get((AlarmEntry.COLUMN_DAY_MONDAY   ));
            int RETURNED_DAY_TUESDAY   = (int) contentValues.get((AlarmEntry.COLUMN_DAY_TUESDAY  ));
            int RETURNED_DAY_WEDNESDAY = (int) contentValues.get((AlarmEntry.COLUMN_DAY_WEDNESDAY));
            int RETURNED_DAY_THURSDAY  = (int) contentValues.get((AlarmEntry.COLUMN_DAY_THURSDAY ));
            int RETURNED_DAY_FRIDAY    = (int) contentValues.get((AlarmEntry.COLUMN_DAY_FRIDAY   ));
            int RETURNED_DAY_SATURDAY  = (int) contentValues.get((AlarmEntry.COLUMN_DAY_SATURDAY ));
            String RETURNED_SMILE_TIME = (String) contentValues.get((AlarmEntry.COLUMN_SMILE_TIME   ));

            long now = System.currentTimeMillis();
            long selected = Long.parseLong(RETURNED_ALARM_TIME_MILLIS);
            long timeDifference = selected - now;
            String nextAlarmIn = AlarmContract.msToHHMMSS(timeDifference);
            if (timeDifference > 0){
                timeToNextAlarm.setText("Next alarm in: " + nextAlarmIn);
                alarmTime.setText("Next Alarm at " + RETURNED_ALARM_TIME);
            } else {
                alarmTime.setText(getString(R.string.alarm_time_default));
                timeToNextAlarm.setText("No next alarm, smile");
            }
            initButton (RETURNED_DAY_SUNDAY   ,sunday   );
            initButton (RETURNED_DAY_MONDAY   ,monday   );
            initButton (RETURNED_DAY_TUESDAY  ,tuesday  );
            initButton (RETURNED_DAY_WEDNESDAY,wednesday);
            initButton (RETURNED_DAY_THURSDAY ,thursday );
            initButton (RETURNED_DAY_FRIDAY   ,friday   );
            initButton (RETURNED_DAY_SATURDAY ,saturday );
            smileTime.setText("Smile " + RETURNED_SMILE_TIME + " seconds to dismiss alarm");
            if (RETURNED_SMILE_TIME.equals(AlarmEntry.SMILETIME_x5)){
                smileTimeiv.setImageResource(R.drawable.smiley1);
            } else if(RETURNED_SMILE_TIME.equals(AlarmEntry.SMILETIME_x10)){
                smileTimeiv.setImageResource(R.drawable.supersmiley1);
            }

        }

    }

    private void initViews() {
        alarmTime = (TextView)findViewById(R.id.alarm_time);
        timeToNextAlarm = (TextView)findViewById(R.id.next_alarm_time);

        sunday    = (Button)findViewById(R.id.togglebuttonsun);
        monday    = (Button)findViewById(R.id.togglebuttonmon);
        tuesday   = (Button)findViewById(R.id.togglebuttontue);
        wednesday = (Button)findViewById(R.id.togglebuttonwed);
        thursday  = (Button)findViewById(R.id.togglebuttonthu);
        friday    = (Button)findViewById(R.id.togglebuttonfri);
        saturday  = (Button)findViewById(R.id.togglebuttonsat);

        smileTime   = (TextView)findViewById(R.id.smileTime_main);
        smileTimeiv = (ImageView)findViewById(R.id.smile_image_main);


        alarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditAlarm.class));
            }
        });


    }

    public void initButton(int checked, Button button){
        if (checked == 1){
            //button.setChecked(true);
            Drawable drawable = ResourcesCompat.getDrawable(MyApplication.sContext.getResources(), R.color
                    .colorPrimary, null);
            button.setBackgroundDrawable(drawable);
        }
    }

    private class AsyncLoader extends AsyncTaskLoader {

        public AsyncLoader(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
            return null;
        }


    }
}
