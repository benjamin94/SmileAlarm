package com.example.benjaminlize.smilealarm;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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

import com.example.benjaminlize.smilealarm.data.AlarmContract.AlarmEntry;

import java.util.Calendar;

public class EditAlarm extends AppCompatActivity implements View.OnClickListener {

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
                AlarmConverter alarmConverter = new AlarmConverter(getApplicationContext(),
                        screenValues);
                Calendar calendarWithAlarm = alarmConverter.getCalendarNextAlarm();

                if (calendarWithAlarm != null){
                    alarmConverter.setAlarm(calendarWithAlarm);
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,MainActivity.class));
                } else {
                    Toast.makeText(this, "Please select a day", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel:
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
        }
    }

    public ContentValues getScreenContent() {
        ContentValues screenValues = new ContentValues();
        String alarmTime = getAlarmTime();
        screenValues.put(AlarmEntry.COLUMN_ALARM_TIME   , alarmTime);
        screenValues.put(AlarmEntry.COLUMN_ALARM_TIME_MILLIS   , "default");
        screenValues.put(AlarmEntry.COLUMN_RECURRENCE   ,  radioButtonFreqWhich(radioGroupFrequency));
        screenValues.put(AlarmEntry.COLUMN_DAY_SUNDAY   , isButtonChecked(sunday   ));
        screenValues.put(AlarmEntry.COLUMN_DAY_MONDAY   , isButtonChecked(monday   ));
        screenValues.put(AlarmEntry.COLUMN_DAY_TUESDAY  , isButtonChecked(tuesday  ));
        screenValues.put(AlarmEntry.COLUMN_DAY_WEDNESDAY, isButtonChecked(wednesday));
        screenValues.put(AlarmEntry.COLUMN_DAY_THURSDAY , isButtonChecked(thursday ));
        screenValues.put(AlarmEntry.COLUMN_DAY_FRIDAY   , isButtonChecked(friday   ));
        screenValues.put(AlarmEntry.COLUMN_DAY_SATURDAY , isButtonChecked(saturday ));
        screenValues.put(AlarmEntry.COLUMN_SMILE_TIME   , radioButtonSmileTimeWhich(radioGroupSmileTime));

        return screenValues;
    }

    private static String getAlarmTime() {
        CharSequence text = alarmTime.getText();
        text = text.subSequence(0,text.length()-3);
        return String.valueOf(text);
    }

    private static int isButtonChecked(ToggleButton toggleButton){
        if (toggleButton.isChecked())return 1;
        else return 0;
    }

    private static String radioButtonFreqWhich(RadioGroup radioGroup){

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        if (idx == 0)return AlarmEntry.FREQ_ONCE;
        else return AlarmEntry.FREQ_REPEAT;
    }

    private static String radioButtonSmileTimeWhich(RadioGroup radioGroup){
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        if (idx == 0)return AlarmEntry.SMILETIME_x5;
        else return AlarmEntry.SMILETIME_x10;
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
