package com.example.benjaminlize.smilealarm;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ToggleButton;

/**
 * Created by benjamin.lize on 13/05/2016.
 */
public class ToggleButtonSA implements View.OnClickListener {

    ToggleButton mToggleButton;

    public ToggleButtonSA(ToggleButton toggleButton) {
        mToggleButton = toggleButton;
        mToggleButton.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        if (mToggleButton.isChecked()) {
            Resources resources = MyApplication.getsContext().getResources();
            Drawable drawable = resources.getDrawable(R.color.colorPrimary);
            mToggleButton.setBackgroundDrawable(drawable);
        } else {
            mToggleButton.setBackgroundColor(Color.WHITE);
        }

    }

    public static void initToggleButton(int checked, ToggleButton button){
        if (checked == 1){
            button.setChecked(true);
            Drawable drawable = ResourcesCompat.getDrawable(MyApplication.sContext.getResources(), R.color
                    .colorPrimary, null);
            button.setBackgroundDrawable(drawable);
        } else button.setChecked(false);
    }


}