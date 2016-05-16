package com.example.benjaminlize.smilealarm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.benjaminlize.smilealarm.data.AlarmContract;

public class MainActivity extends AppCompatActivity {

/*    TextView alarmTime;
    Button */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = (TextView)findViewById(R.id.alarm_time);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditAlarm.class));
            }
        });
        Cursor cursor = getContentResolver().query(
                AlarmContract.AlarmEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        cursor.moveToLast();


    }


}
