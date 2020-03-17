package com.beautystudiocn.allsale.mvp.network;


import com.alibaba.fastjson.JSON;
import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.util.AppUtil;
import com.beautystudiocn.rxnetworklib.network.bean.ApiException;
import com.beautystudiocn.rxnetworklib.network.bean.HttpResult;
import com.beautystudiocn.rxnetworklib.network.callback.AbstractCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.beautystudiocn.allsale.util.ToastUtil;

import io.reactivex.disposables.Disposable;

/**
 * <br> ClassName:   NetworkCallback
 * <br> Description: 网络请求回调，数据解析
 * <br>
 * <br> Date:        2017/5/18 17:10
 */
public abstract class NetworkCallback<T> extends AbstractCallback<T> {
    private INetworkPresenter mNetWorkPresenter;
    private String mTips;
    private Object mResponse;

    public NetworkCallback(INetworkPresenter mNetWorkPresenter) {
        this.mNetWorkPresenter = mNetWorkPresenter;
    }

    public NetworkCallback(INetworkPresenter iNetworkPresenter, String mTips) {
        this.mNetWorkPresenter = iNetworkPresenter;
        this.mTips = mTips;
    }

    public INetworkPresenter getNetWorkPresenter() {
        return mNetWorkPresenter;
    }


    @Override
    public void onStart(Disposable disposable) {
        super.onStart(disposable);
        if (getNetWorkPresenter() != null) {
            getNetWorkPresenter().onRequestStart(mTips);
        }
    }

    @Override
    public void onFailure(String taskId, ApiException e) {
        if (getNetWorkPresenter() != null) {
            getNetWorkPresenter().onFailure(taskId, e);
        }
    }

    @Override
    public void onComplete(String taskId) {
        super.onComplete(taskId);
        if (getNetWorkPresenter() == null) {
            return;
        }
        getNetWorkPresenter().onComplete();
    }

    @Override
    public void onSuccess(String taskId, Object response) {
        if (response != null) {
            mResponse = response;
            getNetWorkPresenter().onSuccess(taskId, response);
            onSucceed(taskId);
        }
    }

    public abstract void onSucceed(String taskId);

    public String getMessage() {
        if (mResponse == null)
            return null;
        HttpResult<Object> httpResult = (HttpResult<Object>) mResponse;
        return httpResult.getMessage();
    }
    public int getCode(){
        HttpResult<Object> httpResult = (HttpResult<Object>) mResponse;
        return httpResult.getCode();
    }

    public T getData() {
        if (mResponse == null)
            return null;
        T t = null;
        try {
            HttpResult<Object> httpResult = (HttpResult<Object>) mResponse;
            Object data = httpResult.getData();
            if (data == null)
                data = new Object();
            if (t instanceof String || t instanceof Integer) {
                return t;
            }
            Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            Type actualTypeArgument = actualTypeArguments[0];
            String s = JSON.toJSONString(data);
            t = JSON.parseObject(s, actualTypeArgument);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast(AppUtil.getContext().getString(R.string.parse_error_tip));
        }
        return t;
    }
}
