package com.beautystudiocn.rxnetworklib.network.request;

import com.beautystudiocn.rxnetworklib.network.HttpRequestFactory;

/**
 * <br> ClassName:   DeleteRequestBuilder
 * <br> Description: DELETE 请求
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2018/1/13 10:32
 */
public class DeleteRequestBuilder extends AbRequestBuilder {

    @Override
    public void build() {
        mTaskId = HttpRequestFactory.executeDelete(mUrl, mParams, getIRequestManager(), mMCallback);
    }
}
