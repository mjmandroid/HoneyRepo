package com.beautystudiocn.rxnetworklib.network;

import android.text.TextUtils;

import com.beautystudiocn.rxnetworklib.network.util.DebugLogger;
import com.beautystudiocn.rxnetworklib.network.request.IRequestMethod;
import com.beautystudiocn.rxnetworklib.network.util.LoggerManager;

import okhttp3.OkHttpClient;

/**
 * <br> ClassName:   RxSmart
 * <br> Description: 网络层封装类
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2018/10/18 15:54
 */
public class RxSmart {
    private static ApiService defaultApi = null;
    private static String mBaseUrl = "";
    private static boolean isDebug = true;
    private static String mToken = "";
    private static String mRefreshToken = "";
    private static boolean mIsNewUrl = false;
    private static String userId = "";
    private static String userPhone ="";
    public static final String WEIXIN_APP_ID = "wx93e7d9b4a477bb46";
    public static int PAY_STATE = 2;//微信支付
    public static Integer imId = 0;
    public static boolean setPayPassword = false;
    public static String IS_FIRST = "isFirst";


    public static ApiService getDefaultApi() {
        if (defaultApi == null || mIsNewUrl) {


            synchronized (ApiService.class) {
                if (defaultApi == null || mIsNewUrl) {
                    mIsNewUrl = false;
                    defaultApi = RetrofitGenerator.getDefaultRetrofit(mBaseUrl).create(ApiService.class);
                }
            }
        }
        return defaultApi;
    }

    public static boolean isSetPayPassword() {
        return setPayPassword;
    }

    public static void setSetPayPassword(boolean setPayPassword) {
        RxSmart.setPayPassword = setPayPassword;
    }

    public static String getUserPhone() {
        return userPhone;
    }

    public static void setUserPhone(String userPhone) {
        RxSmart.userPhone = userPhone;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        RxSmart.userId = userId;
    }
    /**
     * <br> Description: 自定义okHttpClient 正常不用
     * <br> Author:      Android-pc
     * <br> Date:        2018/10/23 11:49
     */
    public static void initOkHttpClient(OkHttpClient client) {
        RetrofitGenerator.setCustomOkHttpClient(client);
    }

    /**
     * <br> Description: 初始化baseUrl
     * <br> Date:        2018/10/23 11:47
     */
    public static boolean initBaseUrl(String baseUrl) {
        if (!TextUtils.isEmpty(baseUrl) && !baseUrl.equals(mBaseUrl)) {
            mIsNewUrl = true;
            mBaseUrl = baseUrl;
        }
        return mIsNewUrl;
    }

    public static String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * <br> Description: 設置Token
     * <br> Date:        2018/10/23 11:47
     */
    public static void setToken(String token)
    {
        mToken = token;
    }

    public static String getToken() {
        if (!TextUtils.isEmpty(mToken)) {
            return mToken;
        }else {
            return "Basic YXBwOmFwcA==";
        }
    }

    /**
     * <br> Description: 設置刷新的Token
     * <br> Date:        2018/10/23 11:47
     */
    public static void setRefreshToken(String refreshToken) {
        mRefreshToken = refreshToken;
    }

    public static String getRefreshToken() {
        return mRefreshToken;
    }

    /**
     * <br> Description: 设置为调试模式
     * <br> Date:        2017/11/3 14:38
     *
     * @param mode boolean 模式
     */
    public static void setDebugMode(boolean mode) {
        isDebug = mode;
        if (isDebug) {
            LoggerManager.setTag("RxSmart debug---->>");
            LoggerManager.setLogger(new DebugLogger());
        }
    }

    /**
     * <br> Description: 获取模式状态
     * <br> Date:        2017/11/3 14:38
     * @return boolean 是否为调试模式
     */
    public static boolean getDebugMode() {
        return isDebug;
    }

    /**
     * <br> Description: 新建网络请求
     * <br> Date:        2018/10/22 14:38
     */
    public static IRequestMethod newBuilder() {
        return NetWorkRequest.newBuilder();
    }

}
