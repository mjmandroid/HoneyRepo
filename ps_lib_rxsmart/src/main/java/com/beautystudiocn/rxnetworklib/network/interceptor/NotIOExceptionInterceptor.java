package com.beautystudiocn.rxnetworklib.network.interceptor;

import android.text.TextUtils;

import com.beautystudiocn.rxnetworklib.network.HttpRequestFactory;
import com.beautystudiocn.rxnetworklib.network.RxSmart;
import com.beautystudiocn.rxnetworklib.network.bean.RefreshTokenBean;
import com.beautystudiocn.rxnetworklib.network.util.LoggerManager;
import com.beautystudiocn.rxnetworklib.util.AppUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <br> ClassName:   NotIOExceptionInterceptor
 * <br> Description: todo(这里用一句话描述这个类的作用)
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2018/11/19 15:59
 */
public class NotIOExceptionInterceptor implements Interceptor {
    private static final String TAG = "Smart.OkHttp3";

    private CatchListener mListener;

    public NotIOExceptionInterceptor() {
    }

    public NotIOExceptionInterceptor(CatchListener listener) {
        this.mListener = listener;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        return IfRefreshToken(chain, chain.request());
    }

    private int mRefreshCount = 0;

    public Response IfRefreshToken(Chain chain, Request request) throws IOException {
        try {
            LoggerManager.d("requestUrL------>>>" + request.method() + "-->url:" + request.url() + "-->Authorization:" + request.header("Authorization"));
            Response proceed = chain.proceed(request);

            if (proceed != null && proceed.code() == 301) {   /***AllSale token 失效***/
                mRefreshCount++;
                if (mRefreshCount <= 8) {
                    String newToken = refreshToken();
                    Request newRequest = request.newBuilder()
                            .header("Authorization", "bearer" + newToken)
                            .build();
                    return IfRefreshToken(chain, newRequest);
                }
            } else {
                mRefreshCount = 0;
                return proceed;
            }
        } catch (Throwable e) {
            if (RxSmart.getDebugMode()) {
                e.printStackTrace();
            }

            if (e instanceof IOException) {
                throw e;
            }

            if (mListener != null) {
                mListener.reportException();
            }
            throw new IOException(e);
        }
        return chain.proceed(request);
    }

    private interface CatchListener {
        void reportException();
    }


    /**
     *<br> Description: 刷新Token
     *<br> Author:      wujianghua
     *<br> Date:        2018/11/20 15:04
     */
    public String refreshToken() {
        if (!TextUtils.isEmpty(RxSmart.getRefreshToken())) {
            HashMap params = new HashMap();
            params.put("grantType", "refresh_token");
            params.put("refreshToken", RxSmart.getRefreshToken());
            RxSmart.setToken("");
            RefreshTokenBean refreshTokenBean = HttpRequestFactory.executePostSync("woauth/token", params);
            if (refreshTokenBean != null && refreshTokenBean.getMeta() != null && refreshTokenBean.getMeta().getCode() == 0) {
                LoggerManager.d("refreshTokenSuccess");
                String refreshTokenValue = refreshTokenBean.getData().getRefreshTokenValue();
                String tokenValue = refreshTokenBean.getData().getTokenValue();
                RxSmart.setToken(tokenValue);
                RxSmart.setRefreshToken(refreshTokenValue);
                return tokenValue;
            }
        }
        return "";
    }

}