package com.monokoumacorporation.todoc.utils;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application{
    private static Application application;

    public MainApplication() {
        application = this;
    }

    public static Application getInstance() {
        return application;
    }
}
