package com.beautystudiocn.rxnetworklib.network.net;

import android.net.ParseException;

import com.beautystudiocn.allsale.ps_lib_rxsmart.R;
import com.beautystudiocn.rxnetworklib.network.bean.ApiException;
import com.beautystudiocn.rxnetworklib.network.bean.HttpResultException;
import com.beautystudiocn.rxnetworklib.util.AppUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * @author: liaoshengjian
 * @Filename:
 * @Description: 统一请求错误处理类
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/2 10:45
 */
public class ExceptionEngine {
    /**
     * 约定异常__未知错误
     */
    public static final int UNKNOWN = 5000;
    /**
     * 约定异常__解析错误
     */
    public static final int PARSE_ERROR = 5001;
    /**
     * 约定异常__网络错误
     */
    public static final int NETWORD_ERROR = 5002;
    /**
     * 约定异常__请求超时
     */
    public static final int HTTP_TIMEOUT = 5003;
    /**
     * 约定异常__请求参数错误
     */
    public static final int HTTP_ERROR = 5004;


    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, httpException.code());
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    ex = new ApiException(e, UNAUTHORIZED);
                    break;
                case FORBIDDEN:
                    /*ex.setMessage("请求参数错误");  //均视为网络错误
                    break;*/
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.setMessage(AppUtil.getContext().getString(R.string.rtv_not_net));  //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof HttpResultException) {
            //服务器约定返回的错误
            HttpResultException resultException = (HttpResultException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.setMessage(resultException.getMessage());
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //均视为解析错误
            ex = new ApiException(e, PARSE_ERROR);
            ex.setMessage(AppUtil.getContext().getString(R.string.parse_error_tip));
            return ex;
        } else if (e instanceof ConnectException) {
            //连接失败
            ex = new ApiException(e, NETWORD_ERROR);
            ex.setMessage(AppUtil.getContext().getString(R.string.rtv_not_net));
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            //请求超时
            ex = new ApiException(e, HTTP_TIMEOUT);
            ex.setMessage(AppUtil.getContext().getString(R.string.request_out_of_time));
            return ex;
        } else {
            ex = new ApiException(e, UNKNOWN);
            //ex.setMessage(AppUtil.getContext().getString(R.string.un_know_error));          //未知错误
            ex.setMessage(null);
            return ex;
        }
    }
}
