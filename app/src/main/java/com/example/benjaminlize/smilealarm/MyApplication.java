package com.example.benjaminlize.smilealarm;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by benjamin.lize on 12/05/2016.
 */
public class MyApplication extends Application {

    static Context sContext;

    public static Context getsContext() {
        return sContext;
    }

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        sContext = getApplicationContext();
    }

    }
