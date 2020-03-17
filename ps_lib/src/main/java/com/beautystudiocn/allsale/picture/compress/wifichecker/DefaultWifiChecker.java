package com.beautystudiocn.allsale.picture.compress.wifichecker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <br> ClassName:   DefaultWifiChecker                        
 * <br> Description: wifi检测类
 * <br>  
 * <br> Author:      yexiaochuan                             
 * <br> Date:        2017/5/23 17:01
 */

public class DefaultWifiChecker implements IWifiChecker {

    private Context mContext;

    public DefaultWifiChecker(Context context) {
        mContext = context;
    }

    @Override
    public Boolean isWifiNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
