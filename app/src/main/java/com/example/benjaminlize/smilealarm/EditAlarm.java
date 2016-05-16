package com.example.benjaminlize.smilealarm;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.benjaminlize.smilealarm.data.AlarmContract;

import java.util.Calendar;

public class EditAlarm extends AppCompatActivity implements View.OnClickListener {

    final String FREQ_ONCE = "ONCE";
    final String FREQ_REPEAT = "REPEAT";
    final String SMILETIME_x5 = "5secs";
    final String SMILETIME_x10 = "10secs";

    static TextView alarmTime    ;

    ToggleButton sunday   ;
    ToggleButton monday   ;
    ToggleButton tuesday  ;
    ToggleButton wednesday;
    ToggleButton thursday ;
    ToggleButton friday   ;
    ToggleButton saturday ;

    RadioGroup radioGroupSmileTime;
    RadioGroup radioGroupFrequency;

    Button cancel;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        alarmTime = (TextView)findViewById(R.id.alarm_time);
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

        radioGroupFrequency = (RadioGroup)findViewById(R.id.radioGroupFrequency);
        radioGroupSmileTime = (RadioGroup)findViewById(R.id.radioGroupSmileTime);

        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
     int vId = v.getId();
        switch (vId){
            case R.id.save:
                ContentValues screenValues = getScreenContent();
                Uri position = getApplicationContext().getContentResolver().insert(
                        AlarmContract.AlarmEntry.CONTENT_URI,
                        screenValues
                );
                int hi = 1;
                break;
            case R.id.cancel:
                Cursor alarmCursor = getApplicationContext().getContentResolver().query(
                        AlarmContract.AlarmEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
                alarmCursor.moveToFirst();
                Toast.makeText(EditAlarm.this, "123", Toast.LENGTH_SHORT).show();
                int hyi = 1;
                break;

        }
    }

    public ContentValues getScreenContent() {
        ContentValues screenValues = new ContentValues();
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_ALARM_TIME   , getAlarmTime());
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_RECURRENCE   , radioButtonFreqWhich(radioGroupFrequency));
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_SUNDAY   , isButtonChecked(sunday));
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_MONDAY   , isButtonChecked(monday   ));
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_TUESDAY  , isButtonChecked(tuesday  ));
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_WEDNESDAY, isButtonChecked(wednesday));
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_THURSDAY , isButtonChecked(thursday ));
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_FRIDAY   , isButtonChecked(friday   ));
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_DAY_SATURDAY , isButtonChecked(saturday ));
        screenValues.put(AlarmContract.AlarmEntry.COLUMN_SMILE_TIME   , radioButtonSmileTimeWhich(radioGroupSmileTime));

        return screenValues;
    }

    private String getAlarmTime() {
        CharSequence text = alarmTime.getText();
        text = text.subSequence(0,text.length()-3);
        Toast.makeText(EditAlarm.this, text, Toast.LENGTH_SHORT).show();
        return String.valueOf(text);
    }

    private int isButtonChecked(ToggleButton toggleButton){
        if (toggleButton.isChecked())return 0;
        else return 1;
    }

    private String radioButtonFreqWhich(RadioGroup radioGroup){

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        if (idx == 0)return FREQ_ONCE;
        else return FREQ_REPEAT;
    }

    private String radioButtonSmileTimeWhich(RadioGroup radioGroup){
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        if (idx == 0)return SMILETIME_x5;
        else return SMILETIME_x10;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String ampm;
            if (hourOfDay>11) ampm="pm";
            else ampm = "am";

            String hr = "";
            if (hourOfDay<10) hr="0"+String.valueOf(hourOfDay);
            else hr = String.valueOf(hourOfDay);
            String min;

            if (minute<10) min="0"+String.valueOf(minute);
            else min = String.valueOf(minute);

            alarmTime.setText(hr+":"+min+" "+ampm);
        }
    }
}
