package com.beautystudiocn.rxnetworklib.network;

import com.beautystudiocn.rxnetworklib.network.request.AbRequestBuilder;
import com.beautystudiocn.rxnetworklib.network.request.DeleteRequestBuilder;
import com.beautystudiocn.rxnetworklib.network.request.GetRequestBuilder;
import com.beautystudiocn.rxnetworklib.network.request.PostRequestBuilder;
import com.beautystudiocn.rxnetworklib.network.request.PutRequestBuilder;
import com.beautystudiocn.rxnetworklib.network.request.IRequestMethod;

public class NetWorkRequest implements IRequestMethod {

    public static NetWorkRequest newBuilder() {
        return new NetWorkRequest();
    }

    /**
     * <br> Description: get 请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/13 10:36
     */
    @Override
    public AbRequestBuilder createGetRequest() {
        return new GetRequestBuilder();
    }

    /**
     * <br> Description: post请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/13 10:37
     */
    @Override
    public AbRequestBuilder createPostRequest() {
        return new PostRequestBuilder();
    }

    /**
     * <br> Description: put请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/13 10:37
     */
    @Override
    public AbRequestBuilder createPutRequest() {
        return new PutRequestBuilder();
    }

    /**
     * <br> Description: delete请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/13 10:37
     */
    @Override
    public AbRequestBuilder createDeleteRequest() {
        return new DeleteRequestBuilder();
    }
}
