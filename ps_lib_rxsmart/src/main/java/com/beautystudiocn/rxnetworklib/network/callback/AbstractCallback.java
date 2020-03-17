package com.beautystudiocn.rxnetworklib.network.callback;

import com.beautystudiocn.rxnetworklib.network.bean.ApiException;

import io.reactivex.disposables.Disposable;

public abstract class AbstractCallback<T> {

    /**
     * <br> Description: 发起请求前（主线程）
     * <br> Author:      wujianghua
     * <br> Date:        2017/6/13 15:17
     */
    public void onStart(Disposable disposable) {

    }


    /**
     * <br> Description: 请求失败（主线程）
     * <br> Author:      wujianghua
     * <br> Date:        2017/6/13 15:18
     *
     * @param e      ApiException
     */
    public abstract void onFailure(String taskId,ApiException e);

    /**
     * <br> Description: 请求成功（主线程）
     * <br> Author:      wujianghua
     * <br> Date:        2017/6/13 15:18
     *
     * @param response T
     */
    public abstract void onSuccess(String taskId,Object response);

    /**
     * <br> Description: （下载）进度（主线程）
     * <br> Author:      wujianghua
     * <br> Date:        2017/6/13 15:18
     *
     * @param progress float
     * @param total    long
     */
    public void onProgress(float progress, long total) {

    }

    public void onDownLoadSuccess(String path, String name, long fileSize) {

    }

    /**
     * <br> Description: 请求完成（成功、失败都会调用，主线程）
     * <br> Author:      wujianghua
     * <br> Date:        2017/6/13 15:19
     *
     */
    public void onComplete(String taskId) {

    }


}
