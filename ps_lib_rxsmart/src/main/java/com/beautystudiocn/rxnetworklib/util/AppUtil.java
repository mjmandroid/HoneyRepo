package com.beautystudiocn.rxnetworklib.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.beautystudiocn.rxnetworklib.network.util.LoggerManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: liaoshengjian
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016
 * @date: 2017/6/14 16:43
 */
public class AppUtil {
    /***跨模块全局Application***/
    public static Application INSTANCE;
    public static Application DEFAULT_APP;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (Exception e) {
            LoggerManager.e("Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null);
            } catch (Exception ex) {
                LoggerManager.e("Failed to get current application from ActivityThread." + ex.getMessage());
            }
        } finally {
            INSTANCE = app;
        }
        if (INSTANCE == null) {
            INSTANCE = DEFAULT_APP;
        }
    }


    public static Application getContext() {
        if (DEFAULT_APP == null) {
            LoggerManager.w("init should be called first!!!");
        }
        return INSTANCE;
    }

    /**
     * <br> Description: 是否有网络
     * <br> Author:      liaoshengjian
     * <br> Date:        2017/6/14 16:49
     *
     * @return boolean
     */
    public static boolean isNetWorkAvailable() {
        ConnectivityManager mgr = (ConnectivityManager) INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }



    /**
     * MD5加密
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
