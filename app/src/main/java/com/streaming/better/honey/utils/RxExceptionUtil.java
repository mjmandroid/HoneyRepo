package com.streaming.better.honey.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.adapter.rxjava2.HttpException;


public class RxExceptionUtil {

    public static void exceptionHandler(Throwable e, Context context){
        String errorMsg = "未知错误";
        if (e instanceof UnknownHostException) {
            errorMsg = "网络不可用";
            Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "请求网络超时";
            Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorMsg = convertStatusCode(httpException);
            if(!TextUtils.isEmpty(errorMsg)){
                Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();
            }
        } else if (e instanceof ParseException || e instanceof JSONException
                || e instanceof JSONException) {
            errorMsg = "数据解析错误";
            Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();
        }
    }

    private static String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg = "服务器处理请求出错";
        } else if (httpException.code() > 400 && httpException.code() < 500) {
            msg = "服务器无法处理请求";
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
