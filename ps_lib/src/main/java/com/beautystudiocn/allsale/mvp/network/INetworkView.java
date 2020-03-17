package com.beautystudiocn.allsale.mvp.network;


import com.beautystudiocn.rxnetworklib.network.bean.ApiException;

import com.beautystudiocn.allsale.mvp.base.IMvpView;

public interface INetworkView extends IMvpView {
    void displaySuccess(String taskId, Object result);

    void displayRequestFailure(String taskId, ApiException e);

    void displayNetworkError(String taskId, ApiException e);

    void displayRequestNotNet(String taskId, ApiException e);
}
