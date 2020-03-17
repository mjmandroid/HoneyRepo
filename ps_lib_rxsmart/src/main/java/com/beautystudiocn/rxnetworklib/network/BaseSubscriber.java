package com.beautystudiocn.rxnetworklib.network;

import com.beautystudiocn.rxnetworklib.network.bean.ApiException;
import com.beautystudiocn.rxnetworklib.network.bean.HttpResultException;
import com.beautystudiocn.rxnetworklib.network.callback.AbstractCallback;
import com.beautystudiocn.rxnetworklib.network.callback.IRequestManager;
import com.beautystudiocn.rxnetworklib.network.net.ExceptionEngine;
import com.beautystudiocn.rxnetworklib.util.AppUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: wujianghua
 * @Filename:
 * @Description: 对观察者Subscriber的封装，统一错误处理
 * @date: 2018/10/15 14:47
 */
public class BaseSubscriber <T> implements Observer<T> {
    /**
     * 唯一id，用于ReloadView判断刷新
     */
    private Disposable mDisposable;

    private AbstractCallback mCallback;

    private IRequestManager mIRequestManager;

    private String mTaskId;

    public BaseSubscriber(AbstractCallback callback) {
        this.mCallback = callback;
    }

    public BaseSubscriber(String taskId,IRequestManager mIRequestManager, AbstractCallback callback) {
        this.mCallback = callback;
        this.mIRequestManager = mIRequestManager;
        this.mTaskId = taskId;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        if (!AppUtil.isNetWorkAvailable()) {
            onError(ExceptionEngine.handleException(new HttpResultException(ApiException.NETWORD_ERROR, "连接失败")));
            dispose();
            return;
        }
        if (mIRequestManager != null)
            mIRequestManager.addCalls(d);
        if (null != mCallback) {
            this.mCallback.onStart(mDisposable);
        }
    }

    /**
     * onNext
     */
    @Override
    public void onNext(T t) {
        if (null != mCallback) {
            this.mCallback.onSuccess(mTaskId,t);
        }
        if (mIRequestManager != null && mDisposable != null)
            mIRequestManager.removeCall(mDisposable);
    }

    /**
     * 执行完成
     */
    @Override
    public void onComplete() {
        if (null != mCallback)
            this.mCallback.onComplete(mTaskId);
    }


    /**
     * 执行异常
     */
    @Override
    public void onError(Throwable e) {
        if (null != mCallback)
            this.mCallback.onFailure(mTaskId,(ApiException) e);
        if (mIRequestManager != null && mDisposable != null)
            mIRequestManager.removeCall(mDisposable);
    }


    public void dispose() {
        if (null != mDisposable) {
            mDisposable.dispose();
        }
    }
}
