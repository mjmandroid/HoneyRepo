package com.beautystudiocn.rxnetworklib.network.request;

import com.beautystudiocn.rxnetworklib.network.HttpRequestFactory;

/**
 * <br> ClassName:   GetRequestBuilder
 * <br> Description: GET 请求
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2018/1/13 10:32
 */
public class GetRequestBuilder extends AbRequestBuilder{

    @Override
    public void build() {
        mTaskId = HttpRequestFactory.executeGet(mUrl, mParams, getIRequestManager(), mMCallback);
    }
}
