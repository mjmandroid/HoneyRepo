package com.beautystudiocn.allsale.mvp.network;


import com.beautystudiocn.allsale.mvp.base.IBaseModel;
import com.beautystudiocn.rxnetworklib.network.bean.ApiException;
import com.beautystudiocn.rxnetworklib.network.callback.IReloadAction;
import com.beautystudiocn.rxnetworklib.network.callback.IRequestManager;
import com.beautystudiocn.rxnetworklib.network.callback.ISetReloadAction;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.beautystudiocn.allsale.mvp.base.BasePresenter;
import io.reactivex.disposables.Disposable;

/**
 * <br> Description: todo(这里用一句话描述这个方法的作用)
 */
public abstract class NetworkPresenter<T extends INetworkView,M extends IBaseModel> extends BasePresenter<T,M> implements IRequestManager<Disposable>, ISetReloadAction, INetworkPresenter {
    /**
     * 请求管理队列
     */
    private BlockingQueue<Disposable> mCallBlockingQueue = new LinkedBlockingDeque<>();
    /***指定重新加载的请求***/
    private IReloadAction mIReloadAction;
    private IReloadActionView mReloadView;

    @Override
    public void addCalls(Disposable call) {
        //call.getCall() == null 同样加入，重新加载会重新生成call
        if (call != null && mCallBlockingQueue != null && !mCallBlockingQueue.contains(call)) {
            mCallBlockingQueue.add(call);
        }
    }

    @Override
    public void removeCall(Disposable call) {
        if (mCallBlockingQueue != null) {
            synchronized (NetworkPresenter.class) {
                if (mCallBlockingQueue != null) {
                    mCallBlockingQueue.remove(call);
                }
            }
        }
    }

    @Override
    public void removeAllCalls() {
        if (mCallBlockingQueue != null) {
            synchronized (NetworkPresenter.class) {
                if (mCallBlockingQueue != null) {
                    for (Disposable disposable : mCallBlockingQueue) {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                    mCallBlockingQueue.clear();
                    mCallBlockingQueue = null;
                }
            }
        }
    }

    public String getRetryReqTaskId() {
        if (mIReloadAction != null) {
            return mIReloadAction.getReloadId();
        }
        return "";
    }

    @Override
    public void setReloadAction(IReloadAction iReloadAction) {
        if (mReloadView != null) {
            mReloadView.onStartToRetry();
        }
        this.mIReloadAction = iReloadAction;
    }


    public void retryRequest() {
        if (mIReloadAction != null) {
            mIReloadAction.doReload();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        removeAllCalls();
    }

    @Override
    public void onRequestStart(String tip) {
        if (!isViewAttached()) {
            return;
        }
        getView().showLoading(tip);
    }

    @Override
    public void onFailure(String taskId, ApiException e) {
        if (!isViewAttached()) {
            return;
        }
        getView().dismissLoading();
        switch (e.getCode()) {
            //无网络
            case ApiException.NETWORD_ERROR:
                getView().displayRequestNotNet(taskId, e);
                break;
            //请求失败onFailure
            case ApiException.UNAUTHORIZED:
            case ApiException.FORBIDDEN:
            case ApiException.NOT_FOUND:
            case ApiException.INTERNAL_SERVER_ERROR:
            case ApiException.BAD_GATEWAY:
            case ApiException.SERVICE_UNAVAILABLE:
            case ApiException.GATEWAY_TIMEOUT:
            case ApiException.REQUEST_TIMEOUT:
            case ApiException.HTTP_TIMEOUT://超时
                //取消请求不做提示
                // if (!call.isCanceled()) {
                getView().displayNetworkError(taskId, e);
                // }
                break;
            case ApiException.HTTP_ERROR://参数异常
                //okHttp请求失败
                getView().displayNetworkError(taskId, e);
                break;
            case ApiException.PARSE_ERROR://解析异常
                getView().displayRequestFailure(taskId, e);
                break;
            default:
                getView().displayRequestFailure(taskId, e);
                break;
        }
    }

    @Override
    public void onSuccess(String taskId, Object response) {
        if (!isViewAttached()) {
            return;
        }
        getView().displaySuccess(taskId, response);
    }


    @Override
    public void onComplete() {
        if (!isViewAttached()) {
            return;
        }
        getView().dismissLoading();
    }

    public void setReloadView(IReloadActionView mReloadView) {
        this.mReloadView = mReloadView;
    }
}
