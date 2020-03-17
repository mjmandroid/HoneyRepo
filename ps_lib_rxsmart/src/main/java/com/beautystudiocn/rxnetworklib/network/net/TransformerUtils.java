package com.beautystudiocn.rxnetworklib.network.net;


import com.beautystudiocn.rxnetworklib.network.bean.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: liaoshengjian
 * @Filename:
 * @Description:    Http请求转换器 （可自己定制）
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/5 17:53
 */
public class TransformerUtils {

    /**
     * 一般请求转换器
     */
    public static <T> ObservableTransformer<HttpResult<Object>, HttpResult<Object>> applySimpleTransformer() {
        return new ObservableTransformer<HttpResult<Object>, HttpResult<Object>>() {
            @Override
            public Observable<HttpResult<Object>> apply(Observable<HttpResult<Object>> httpResultObservable) {
                return httpResultObservable
                        .map(HttpRequestTransformer.applyHttpResultFunc())                       //后台约定错误处理
                        .onErrorResumeNext(HttpRequestTransformer.<HttpResult<Object>>applyHttpResponseFunc())        //请求错误处理
                        .compose(HttpRequestTransformer.<HttpResult<Object>>switchSchedulers());
            }
        };
    }

    /* *********************************************************************************************************
     * 下面可自己定制所需转换器
     * *********************************************************************************************************
     */

    /**
     * FlatMap合并嵌套请求，转换器
     */
    public static <T> ObservableTransformer<HttpResult<Object>,HttpResult<Object>> applyFlatMapTransformer() {
        return new ObservableTransformer<HttpResult<Object>, HttpResult<Object>>() {
            @Override
            public Observable<HttpResult<Object>> apply(Observable<HttpResult<Object>> httpResultObservable) {
                return httpResultObservable
                        .map(HttpRequestTransformer.<T>applyHttpResultFunc())                       //后台约定错误处理
                        .subscribeOn(Schedulers.io());
            }
        };
    }


}
