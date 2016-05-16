package com.example.benjaminlize.smilealarm;

import android.graphics.Color;
import android.view.View;
import android.widget.ToggleButton;

/**
 * Created by benjamin.lize on 13/05/2016.
 */
public class ToggleButtonSA implements View.OnClickListener {

    ToggleButton mToggleButton;

    public ToggleButtonSA(ToggleButton toggleButton) {
        mToggleButton = toggleButton;
    }

    @Override
    public void onClick(View v) {
        if (mToggleButton.isChecked()) {
            mToggleButton.setBackgroundColor(Color.WHITE);
        } else {
            mToggleButton.setBackgroundColor(Color.GREEN);
        }
    }
}