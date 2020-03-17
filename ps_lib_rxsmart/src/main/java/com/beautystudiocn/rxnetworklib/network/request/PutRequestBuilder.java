package com.beautystudiocn.rxnetworklib.network.request;

import com.beautystudiocn.rxnetworklib.network.HttpRequestFactory;

/**
 * <br> ClassName:   PutRequestBuilder
 * <br> Description: PUT 请求
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2018/1/13 10:32
 */
public class PutRequestBuilder extends AbRequestBuilder {

    @Override
    public void build() {
        mTaskId = HttpRequestFactory.executePut(mUrl, mParams, getIRequestManager(), mMCallback);
    }
}
