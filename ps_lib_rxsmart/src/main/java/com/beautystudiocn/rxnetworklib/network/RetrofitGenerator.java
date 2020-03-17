package com.beautystudiocn.rxnetworklib.network;

import android.util.Log;

import com.beautystudiocn.rxnetworklib.network.interceptor.CustomLoggingInterceptor;
import com.beautystudiocn.rxnetworklib.network.interceptor.NotIOExceptionInterceptor;
import com.beautystudiocn.rxnetworklib.network.net.JsonConverterFactory;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <br> Description: 配置Retrofit
 * <br> Author:      wujianghua
 * <br> Date:        2017/12/19 14:29
 */
public class RetrofitGenerator {
    private static OkHttpClient mCustomOkHttpClient;
    private static final int DEFAULT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 60;

    /**
     * <br> Description: 获取Retrofit
     * <br> Author:      wujianghua
     * <br> Date:        2017/12/19 15:20
     */
    public static Retrofit getDefaultRetrofit(String baseUrl) {
        OkHttpClient okHttpClient = getOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * <br> Description: 默认的OkHttp配置
     * <br> Author:      wujianghua
     * <br> Date:        2017/12/19 15:20
     */
    private static OkHttpClient getDefaultOkHttpClient() {
        CustomLoggingInterceptor logging = new CustomLoggingInterceptor();
        if (RxSmart.getDebugMode()) {
            logging.setLevel(CustomLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(CustomLoggingInterceptor.Level.NONE);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
              //  .addInterceptor(new CustomRequestInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .sslSocketFactory(createSSLSocketFactory())
                .addInterceptor(new NotIOExceptionInterceptor())

                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//读取超时
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS);//写入超时
        return builder.build();
    }

    /**
     * <br> Description: 获取retrofit Builder
     * <br> Author:      wujianghua
     * <br> Date:        2017/12/29 10:18
     */
    public static Retrofit.Builder getRetrofitBuilder(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
    }

    /**
     * <br> Description: 获取client Builder
     * <br> Author:      wujianghua
     * <br> Date:        2017/12/29 10:18
     */
    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(10, TimeUnit.SECONDS)//读取超时
                .writeTimeout(10, TimeUnit.SECONDS);//写入超时
        return builder;
    }

    /**
     *<br> Description: createSSLSocketFactory
     *<br> Author:      wujianghua
     *<br> Date:        2017/6/13 15:10
     *
     * @return SSLSocketFactory
     */
    public static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sSLSocketFactory;
    }

    /**
     *<br> Description: TrustAllManager
     *<br> Author:      wujianghua
     *<br> Date:        2017/6/13 15:10
     */
    private static class TrustAllManager implements X509TrustManager {

        /**
         * checkClientTrusted
         */
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {
        }

        /**
         * checkClientTrusted
         */
        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {
        }

        /**
         * checkClientTrusted
         */
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    }

    /**
     * <br> Description: 设置自定义的OkHttpClient
     * <br> Author:      wujianghua
     * <br> Date:        2017/12/20 15:29
     */
    public static void setCustomOkHttpClient(OkHttpClient client) {
        mCustomOkHttpClient = client;
    }

    /**
     * <br> Description: 获取设置自定义的OkHttpClient
     * <br> Author:      wujianghua
     * <br> Date:        2017/12/20 15:30
     */
    public static OkHttpClient getOkHttpClient() {
        if (mCustomOkHttpClient != null)
            return mCustomOkHttpClient;
        else
            return getDefaultOkHttpClient();
    }

}
