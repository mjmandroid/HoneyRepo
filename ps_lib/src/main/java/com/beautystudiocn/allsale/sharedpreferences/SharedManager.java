package com.beautystudiocn.allsale.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.beautystudiocn.allsale.sharedpreferences.storage.IStorageBaseType;
import com.beautystudiocn.allsale.sharedpreferences.storage.IStorageObject;
import com.beautystudiocn.allsale.sharedpreferences.storage.IStorageString;
import com.beautystudiocn.allsale.sharedpreferences.storage.StorageBaseType;
import com.beautystudiocn.allsale.sharedpreferences.storage.StorageObject;
import com.beautystudiocn.allsale.sharedpreferences.storage.StorageString;
import com.beautystudiocn.allsale.sharedpreferences.storage.IStorageBaseType;
import com.beautystudiocn.allsale.sharedpreferences.storage.IStorageObject;
import com.beautystudiocn.allsale.sharedpreferences.storage.IStorageString;
import com.beautystudiocn.allsale.sharedpreferences.storage.StorageBaseType;
import com.beautystudiocn.allsale.sharedpreferences.storage.StorageObject;
import com.beautystudiocn.allsale.sharedpreferences.storage.StorageString;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description: 本地数据存储(SharedPreferences)管理类
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 11:45
 */

public class SharedManager {

    /**
     * SharedManager存储集合
     */
    private static Map<String, WeakReference<SharedManager>> sharedMap = new HashMap<>();

    /**
     * SharedPreferences类
     */
    private SharedPreferences sharedPreferences;
    /**
     * 对象存储
     */
    private IStorageObject storageObject;
    /**
     * 字符串存储
     */
    private IStorageString storageString;
    /**
     * 基本数据类型存储
     */
    private IStorageBaseType storageBaseType;

    private SharedManager(Context context, SharedConfig sharedConfig) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot with a null Context");
        } else {
            context = context.getApplicationContext() == null ? context : context.getApplicationContext();
            sharedPreferences = context.getSharedPreferences(sharedConfig.getShareFlag() + sharedConfig.getShareFileName() + (sharedConfig.isVersionControl() ? getVersion(context) : ""), sharedConfig.getShareMode());
            storageObject = new StorageObject(sharedPreferences);
            storageString = new StorageString(sharedPreferences);
            storageBaseType = new StorageBaseType(sharedPreferences);
        }
    }

    /**
     * 获取本地存储管理类
     *
     * @param context      Context
     * @param sharedConfig 配置信息
     * @return 返回管理类
     */
    public static SharedManager get(Context context, SharedConfig sharedConfig) {
        String mapKey = sharedConfig.getShareFlag() + sharedConfig.getShareFileName();
        SharedManager mSharedManager = getSharedManager(mapKey);
        if (mSharedManager == null) {
            synchronized (SharedManager.class) {
                mSharedManager = getSharedManager(mapKey);
                if (mSharedManager == null) {
                    mSharedManager = new SharedManager(context, sharedConfig);
                    sharedMap.put(mapKey, new WeakReference<>(mSharedManager));
                }
            }
        }
        return mSharedManager;
    }

    /**
     * <br> Description: 获取SharedManager
     * <br> Author:      谢文良
     * <br> Date:        2017/10/13 11:42
     *
     * @param key key
     * @return SharedManager
     */
    private static SharedManager getSharedManager(String key) {
        SharedManager mSharedManager = null;
        WeakReference<SharedManager> mWeakReference = sharedMap.get(key);
        if (mWeakReference != null && mWeakReference.get() != null) {
            mSharedManager = mWeakReference.get();
        }
        return mSharedManager;
    }

    /**
     * 清除存储的数据
     */
    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

    /**
     * 保存字符串
     *
     * @param key   键
     * @param value 值
     */
    public void putString(String key, String value) {
        storageString.putString(key, value);
    }

    /**
     * 获取字符串
     *
     * @param key      键
     * @param defValue 默认值
     * @return 返回字符串
     */
    public String getString(String key, String defValue) {
        return storageString.getString(key, defValue);
    }

    /**
     * 保存int
     *
     * @param key   键
     * @param value 值
     */
    public void putInt(String key, int value) {
        storageBaseType.putInt(key, value);
    }

    /**
     * 获取int
     *
     * @param key      键
     * @param defValue 默认值
     * @return 返回int
     */
    public int getInt(String key, int defValue) {
        return storageBaseType.getInt(key, defValue);
    }

    /**
     * 保存long
     *
     * @param key   键
     * @param value 值
     */
    public void putLong(String key, long value) {
        storageBaseType.putLong(key, value);
    }

    /**
     * 获取long
     *
     * @param key      键
     * @param defValue 默认值
     * @return 返回long
     */
    public long getLong(String key, long defValue) {
        return storageBaseType.getLong(key, defValue);
    }

    /**
     * 保存float
     *
     * @param key   键
     * @param value 值
     */
    public void putFloat(String key, float value) {
        storageBaseType.putFloat(key, value);
    }

    /**
     * 获取float
     *
     * @param key      键
     * @param defValue 默认值
     * @return 返回float
     */
    public float getFloat(String key, float defValue) {
        return storageBaseType.getFloat(key, defValue);
    }

    /**
     * 保存boolean
     *
     * @param key   键
     * @param value 值
     */
    public void putBoolean(String key, boolean value) {
        storageBaseType.putBoolean(key, value);
    }

    /**
     * 获取boolean
     *
     * @param key      键
     * @param defValue 默认值
     * @return 返回boolean
     */
    public boolean getBoolean(String key, boolean defValue) {
        return storageBaseType.getBoolean(key, defValue);
    }

    /**
     * 本地存储中是否包涵该key的值
     *
     * @param key 键
     * @return 是否包涵
     */
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回版本号
     *
     * @param context Context
     * @return 版本号
     */
    private String getVersion(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(pi.versionCode);
        } catch (Exception e) {
            return "";
        }
    }
}
