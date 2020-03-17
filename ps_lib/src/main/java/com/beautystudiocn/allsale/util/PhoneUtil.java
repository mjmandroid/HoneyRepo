package com.beautystudiocn.allsale.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.beautystudiocn.allsale.log.LoggerManager;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;

/**
 * <br> ClassName:   PhoneUtil
 * <br> Description: 手机、系统相关工具类
 * <br>
 * <br> Author:      zhangweiqiang
 * <br> Date:        2017/8/2 14:09
 */
public class PhoneUtil {
    /**
     * <br> Description: 获取操作系统
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/24 10:33
     *
     * @return 返回操作系统
     */
    public static String getOS() {
        return "Android";
    }

    /**
     * <br> Description: 获取系统类别
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/24 15:23
     *
     * @return 返回系统类别
     */
    public static String getSysNo() {
        return "TDW_APP";
    }

    /**
     * <br> Description: 获取操作系统版本
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/24 10:33
     *
     * @return 返回操作系统版本
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * <br> Description: 获取设备语言
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/24 14:44
     *
     * @return 返回设备语言
     */
    public static String getDeviceLanguage() {
        Locale l = Locale.getDefault();
        return l.getLanguage();
    }

    /**
     * <br> Description: 获取手机品牌
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/24 14:46
     *
     * @return 返回手机品牌
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * <br> Description: 获取手机型号
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/24 14:47
     *
     * @return 返回手机型号
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * <br> Description: 获取运营商信息
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/24 09:56
     *
     * @return 返回运营商信息
     */
    public static String getSimOperatorInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String operatorString = telephonyManager.getSimOperator();

        if (operatorString == null) {
            return "";
        } else {
            return operatorString;
        }
    }

    /**
     * <br> Description: 获取手机IMEI
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/23 16:18
     *
     * @return 返回手机IMEI
     */
    public static String getIMEI() {
        try {
            String imei = ((TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * <br> Description: 获取手机屏幕密度api
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/8/2 14:45
     *
     * @return 返回手机屏幕密度
     */
    public static float getScreenDensity() {
        DisplayMetrics dm = AppUtil.getContext().getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * <br> Description: 获取屏幕宽度
     * <br> Author:      longluliu
     * <br> Date:        2013/6/03 10:35
     *
     * @param context 上下文
     * @return 返回屏幕宽度
     */
    public static int getScreenPixelsWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * <br> Description: 获取屏幕高度
     * <br> Author:      longluliu
     * <br> Date:        2013/6/03 10:35
     *
     * @param context 上下文
     * @return 返回屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * <br> Description: 获取屏幕分辨率
     * <br> Author:      hehaodong
     * <br> Date:        2014/12/24 14:17
     *
     * @return 返回屏幕分辨率
     */
    public static String getResolution() {
        return getScreenPixelsWidth(AppUtil.getContext()) + "*" + getScreenHeight(AppUtil.getContext());
    }

    /**
     * <br> Description: 获取屏幕宽高
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/7/26 9:56
     *
     * @param mContext 上下文
     * @return 返回屏幕宽高数组
     */
    public static int[] getDefaultDisplay(Context mContext) {
        int[] size = new int[]{0, 0};
        if (mContext != null) {
            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            size = new int[]{wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight()};
        }
        return size;
    }

    /**
     *<br> Description: 不需要权限的deviceId
     *<br> Author:      wujun
     *<br> Date:        2017/10/25 17:46
     */
    public static String getUniqueId(){
        String android_id =  Settings.Secure
                .getString(AppUtil.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(android_id)){
            android_id = "unknown";
        }
        return  android_id;
    }

    /**
     *<br> Description: 判断网络是否可用，并返回网络类型
     *<br> Author:      zhongweijie
     *<br> Date:        2017/10/26 11:18
     */
    public static String getNetworkType(Context context) {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }
    /***6.0之后默认获取到的MAC地址***/
    private final static String DEFAULT_ADDRESS = "02:00:00:00:00:00";

    /**
     *<br> Description: 获取设备Mac地址
     *<br> Author:      wangweifeng
     *<br> Date:        2018/3/7 15:42
     */
    public static String getPhoneMacAddress() {
        WifiManager wifiManager = (WifiManager) AppUtil.getContext().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        String macAddress = "";

        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                macAddress = wifiInfo.getMacAddress();
            }
        }

        if (TextUtils.isEmpty(macAddress) || DEFAULT_ADDRESS.equals(macAddress)) {
            macAddress = getMacAddressByNetwork();
        }

        return macAddress;
    }

    /**
     *<br> Description: 从NetworkInterfaces获取Mac地址
     *<br> Author:      wangweifeng
     *<br> Date:        2018/3/7 15:40
     */
    private static String getMacAddressByNetwork() {
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while(netInterfaces.hasMoreElements()) {
                NetworkInterface netTemp = netInterfaces.nextElement();
                if (netTemp != null && "wlan0".equals(netTemp.getName())) {
                    byte[] macBytes = netTemp.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }
        } catch (Exception e) {
            LoggerManager.e(e.getMessage());
        }

        return "";
    }
}
