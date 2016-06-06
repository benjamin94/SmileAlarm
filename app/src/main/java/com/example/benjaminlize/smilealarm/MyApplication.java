package com.example.benjaminlize.smilealarm;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

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
        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);
        sContext = getApplicationContext();
        //AnalyticsTrackers.initialize(sContext);
    }

}
