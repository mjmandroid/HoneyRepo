package com.beautystudiocn.allsale.sharedpreferences.storage;

import android.content.SharedPreferences;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 15:32
 */

public class StorageBaseType implements IStorageBaseType {
    /**
     * SharedPreferences类
     */
    private SharedPreferences sharedPreferences;

    public StorageBaseType(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    @Override
    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }

    @Override
    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    @Override
    public void putFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).commit();
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }
}
