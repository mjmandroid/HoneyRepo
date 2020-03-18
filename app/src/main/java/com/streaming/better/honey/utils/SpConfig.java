package com.streaming.better.honey.utils;

import android.content.Context;

import com.streaming.better.honey.base.App;


/**
 * Created by suxi on 2017/5/22.
 */

public class SpConfig extends PreferenceUtil {

    private static final String YOUR_APP_NAME = "AllSale";

    public SpConfig() {
        super(YOUR_APP_NAME);
    }

    @Override
    protected Context getContext() {
        return App.getInstance().getApplicationContext();
    }

    private static class SingletonHolder {
        private static final SpConfig INSTANCE = new SpConfig();
    }

    public static SpConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
