package com.example.benjaminlize.smilealarm;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
                Calendar calendarNow = Calendar.getInstance();

                Calendar calendarWithAlarm = setAlarm(calendarNow);

                if (calendarWithAlarm == null){
                    Toast.makeText(EditAlarm.this, "Please select a day", Toast.LENGTH_SHORT).show();
                } else {
                    //acknowledge alarm and set Alarm
                    String screenValueToToggle = getDay(calendarWithAlarm);
                    screenValues.put(screenValueToToggle,0);
                    screenValues.put(AlarmEntry.COLUMN_ALARM_TIME_MILLIS,calendarWithAlarm.getTimeInMillis());
                    updateCP(screenValues);
                    Toast.makeText(EditAlarm.this, "Success", Toast.LENGTH_SHORT)
                            .show();
                    startActivity(new Intent(this,MainActivity.class));
                }
                break;
            case R.id.cancel:
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
        }
    }

    private String getDay(Calendar calendarWithAlarm) {
        String contentValueKey = "";
        switch (calendarWithAlarm.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:
                contentValueKey = AlarmEntry.COLUMN_DAY_SUNDAY   ;
                break;
            case Calendar.MONDAY:
                contentValueKey = AlarmEntry.COLUMN_DAY_MONDAY   ;
                break;
            case Calendar.TUESDAY:
                contentValueKey = AlarmEntry.COLUMN_DAY_TUESDAY  ;
                break;
            case Calendar.WEDNESDAY:
                contentValueKey = AlarmEntry.COLUMN_DAY_WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                contentValueKey = AlarmEntry.COLUMN_DAY_THURSDAY ;
                break;
            case Calendar.FRIDAY:
                contentValueKey = AlarmEntry.COLUMN_DAY_FRIDAY ;
                break;
            case Calendar.SATURDAY:
                contentValueKey = AlarmEntry.COLUMN_DAY_SATURDAY   ;
                break;
        }
        return contentValueKey;
    }

    private Calendar setAlarm(Calendar calendarNow) {
        Calendar calendarChosen = createCalendarChosen();
        List<Integer> list = getAlarmDayList(calendarNow);
        Calendar calendar = calendarChosen.getInstance();
        if (list.get(0)>0){
            if (calendarChosen.compareTo(calendarNow) == -1){
                //Alarm is in 7 days
                calendar.add(Calendar.DAY_OF_MONTH,7);
                scheduleAlarm(calendar);
                return calendar;
            } else {
                //Next Alarm is Today
                scheduleAlarm(calendarChosen);
                return calendar;
            }
        } else {
            //Next Alarm is not Today
            for (int day = 1; day < 7; day++) {
                if(list.get(day)>0){
                    Calendar calendarPlusi = calendarChosen.getInstance();
                    calendarPlusi.add(Calendar.DAY_OF_MONTH,day);
                    return calendar;
                }
            }
        }
        return null;
    }

    private List<Integer> getAlarmDayList(Calendar calendarNow) {

        int today = calendarNow.get(Calendar.DAY_OF_WEEK);

        int sun = 0;
        int mon = 0;
        int tue = 0;
        int wed = 0;
        int thu = 0;
        int fri = 0;
        int sat = 0;

        List<Integer> list = new ArrayList<>(7);
        if (isButtonChecked(sunday   ) == 1) list.add(Calendar.SUNDAY);    sun = 1;
        if (isButtonChecked(sunday   ) == 0) list.add(-1*Calendar.SUNDAY);
        if (isButtonChecked(monday   ) == 1) list.add(Calendar.MONDAY);    mon = 1;
        if (isButtonChecked(monday   ) == 0) list.add(-1*Calendar.MONDAY);
        if (isButtonChecked(tuesday  ) == 1) list.add(Calendar.TUESDAY);   tue = 1;
        if (isButtonChecked(tuesday  ) == 0) list.add(-1*Calendar.TUESDAY);
        if (isButtonChecked(wednesday) == 1) list.add(Calendar.WEDNESDAY); wed = 1;
        if (isButtonChecked(wednesday) == 0) list.add(-1*Calendar.WEDNESDAY);
        if (isButtonChecked(thursday ) == 1) list.add(Calendar.THURSDAY);    thu = 1;
        if (isButtonChecked(thursday ) == 0) list.add(-1*Calendar.THURSDAY);
        if (isButtonChecked(friday   ) == 1) list.add(Calendar.FRIDAY);      fri = 1;
        if (isButtonChecked(friday   ) == 0) list.add(-1*Calendar.FRIDAY);
        if (isButtonChecked(saturday ) == 1) list.add(Calendar.SATURDAY);    sat = 1;
        if (isButtonChecked(saturday ) == 0) list.add(-1*Calendar.SATURDAY);

        List rearrangedList = new ArrayList(8);
        int day = today;

        for (int i = 0; i < 7 ; i++) {
            Integer value = list.get(day-1);
            rearrangedList.add(value);

            if (day == Calendar.SATURDAY){
                day = Calendar.SUNDAY;
            }else day = day + 1;
        }
        return rearrangedList;
    }

    private void scheduleAlarm(Calendar targetCal){
        targetCal.set(Calendar.SECOND,0);
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), AlarmEntry.ALARM_PENDING_INTENT_ID, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    private void updateCP(ContentValues screenValues) {
        int position = 0;
        position = getApplicationContext().getContentResolver().update(
                AlarmEntry.CONTENT_URI,
                screenValues,
                null,
                null
        );
    }

    private Calendar createCalendarChosen() {
        Calendar calendar = Calendar.getInstance();
        CharSequence hour = alarmTime.getText().subSequence(0,2);
        int hr = Integer.parseInt(hour.toString());
        CharSequence minute = alarmTime.getText().subSequence(3,5);
        int min = Integer.parseInt(minute.toString());

        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        return calendar;
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

    private String getAlarmTime() {
        CharSequence text = alarmTime.getText();
        text = text.subSequence(0,text.length()-3);
        return String.valueOf(text);
    }

    private int isButtonChecked(ToggleButton toggleButton){
        if (toggleButton.isChecked())return 1;
        else return 0;
    }

    private String radioButtonFreqWhich(RadioGroup radioGroup){

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        if (idx == 0)return AlarmEntry.FREQ_ONCE;
        else return AlarmEntry.FREQ_REPEAT;
    }

    private String radioButtonSmileTimeWhich(RadioGroup radioGroup){
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

    private int nextDay(int day){
        int nextDay;
        if (day == Calendar.SATURDAY){
            nextDay = Calendar.SUNDAY;
        }else nextDay = day + 1;
        return nextDay;
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
