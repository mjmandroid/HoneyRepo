package com.beautystudiocn.allsale.sharedpreferences.storage;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 15:27
 */

public interface IStorageBaseType {

    void putInt(String key, int value);

    int getInt(String key, int defValue);

    void putLong(String key, long value);

    long getLong(String key, long defValue);

    void putFloat(String key, float value);

    float getFloat(String key, float defValue);

    void putBoolean(String key, boolean value);

    boolean getBoolean(String key, boolean defValue);
}
