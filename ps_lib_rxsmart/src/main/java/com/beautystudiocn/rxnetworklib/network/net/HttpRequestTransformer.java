package com.beautystudiocn.rxnetworklib.network.net;


import android.util.Log;

import com.beautystudiocn.rxnetworklib.network.bean.ApiException;
import com.beautystudiocn.rxnetworklib.network.bean.HttpResult;
import com.beautystudiocn.rxnetworklib.network.bean.HttpResultException;
import com.beautystudiocn.rxnetworklib.network.bean.LoginResult;
import com.beautystudiocn.rxnetworklib.network.util.LoggerManager;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: liaoshengjian
 * @Filename:
 * @Description: 统一处理转换器
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/2 17:37
 */
public class HttpRequestTransformer {
    /**
     * 统一线程处理
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一后台约定错误处理
     */
    public static Function<HttpResult<Object>, HttpResult<Object>> applyHttpResultFunc() {
        return new Function<HttpResult<Object>, HttpResult<Object>>() {
            @Override
            public HttpResult<Object> apply(HttpResult<Object> httpResult) {
                LoggerManager.json(new Gson().toJson(httpResult));
                Log.e("HttpResult", httpResult.getCode()+"");
                if (httpResult instanceof LoginResult) {
                    return httpResult;
                } else if (httpResult.getCode() != 0&&httpResult.getCode()!=200) {
                    Observable.error(ExceptionEngine.handleException(new ApiException(new Exception(), httpResult.getCode())));
                    throw new HttpResultException(httpResult.getCode(), httpResult.getMsg());
                }


                /*if (httpResult.getData() == null) {
                    return (T) new Object();
                }*/
                return httpResult;
            }
        };
    }


    /**
     * 统一请求错误处理
     */
    public static <T> Function<Throwable, Observable<T>> applyHttpResponseFunc() {
        return new Function<Throwable, Observable<T>>() {
            @Override
            public Observable<T> apply(Throwable throwable) {
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        };
    }


}
