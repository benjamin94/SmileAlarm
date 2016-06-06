package com.example.benjaminlize.smilealarm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.benjaminlize.smilealarm.data.AlarmContract;
import com.example.benjaminlize.smilealarm.facedetection.FaceTrackerFragment;

public class SmileNow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile_now);

        Bundle bundle = new Bundle();
        String smileTime = getIntent().getStringExtra(AlarmContract.AlarmEntry.COLUMN_SMILE_TIME);
        bundle.putString(AlarmContract.AlarmEntry.COLUMN_SMILE_TIME, smileTime);

        TextView smileTimeTextView = (TextView)findViewById(R.id.smile_time_text_view);
        smileTimeTextView.setText("Give us your best smile for " + smileTime + " seconds!");

        FaceTrackerFragment faceTrackerFragment = new FaceTrackerFragment();
        faceTrackerFragment.setArguments(bundle);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, faceTrackerFragment);
        ft.commit();
    }
}
