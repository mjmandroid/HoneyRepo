package com.streaming.better.honey.base;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }


    public static Application getInstance(){
        return mContext;
    }
}
