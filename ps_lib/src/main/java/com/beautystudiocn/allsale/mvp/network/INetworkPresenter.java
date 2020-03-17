package com.beautystudiocn.allsale.mvp.network;

import com.beautystudiocn.rxnetworklib.network.bean.ApiException;

public interface INetworkPresenter {
    void onRequestStart(String tip);

    void onFailure(String taskId, ApiException var2);

    void onComplete();

    void onSuccess(String taskId, Object response);
}
