package com.monokoumacorporation.todoc.utils;

import android.app.Application;

public class MainApplication extends Application{
    private static Application application;

    public MainApplication() {
        application = this;
    }

    public static Application getInstance() {
        return application;
    }
}
