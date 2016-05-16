package com.example.benjaminlize.smilealarm;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by benjamin.lize on 12/05/2016.
 */
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}