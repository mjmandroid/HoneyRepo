package com.beautystudiocn.rxnetworklib.network.request;

import com.beautystudiocn.rxnetworklib.network.callback.AbstractCallback;
import com.beautystudiocn.rxnetworklib.network.callback.IReloadAction;
import com.beautystudiocn.rxnetworklib.network.callback.IRequest;
import com.beautystudiocn.rxnetworklib.network.callback.IRequestManager;
import com.beautystudiocn.rxnetworklib.network.callback.ISetReloadAction;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br> ClassName:   AbRequestBuilder
 * <br> Description: 请求构造器
 * <br> Date:        2018/1/13 10:31
 */
public abstract class AbRequestBuilder implements IRequest<AbRequestBuilder>, IReloadAction {
    public String mUrl;
    public AbstractCallback mMCallback;
    public String mTaskId;
    public Map<String, String> mParams = new HashMap<>();
    public Map<String, Object> mParams2 = new HashMap<>();
    public Map<String, List<String>> mFileMap;
    public boolean mIsFormRequest = false;
    /**
     * 请求管理
     */
    protected Reference<IRequestManager> mIRequestManager;

    public AbRequestBuilder() {

    }

    @Override
    public AbRequestBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    @Override
    public AbRequestBuilder setReadTimeout(long readTimeOut) {
        //TODO 暂时不开放
        return this;
    }

    @Override
    public AbRequestBuilder setWriteTimeOut(long writeTimeOut) {
        //TODO 暂时不开放
        return this;
    }

    @Override
    public AbRequestBuilder setConnectTimeout(long connectTimeout) {
        //TODO 暂时不开放
        return this;
    }

    /**
     * <br> Description: 设置回调
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:57
     */
    @Override
    public AbRequestBuilder setCallback(AbstractCallback callback) {
        mMCallback = callback;
        return this;
    }

    /**
     * <br> Description: 设置请求管理
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/4 16:47
     */
    @Override
    public AbRequestBuilder setRequestManager(IRequestManager iRequestManager) {
        if (iRequestManager != null) {
            this.mIRequestManager = new WeakReference<>(iRequestManager);
        }
        return this;
    }

    /**
     * <br> Description: 网络请求管理
     * <br> Author:      wujianghua
     * <br> Date:        2018/10/23 11:56
     */
    protected IRequestManager getIRequestManager() {
        IRequestManager iRequestManager = null;
        if (this.mIRequestManager != null && this.mIRequestManager.get() != null) {
            iRequestManager = mIRequestManager.get();
        }
        return iRequestManager;
    }

    /**
     * <br> Description: 设置重新加载
     * <br> Author:      wujianghua
     * <br> Date:        2018/10/23 11:57
     */
    @Override
    public AbRequestBuilder setReloadAction(ISetReloadAction iSetReloadAction) {
        iSetReloadAction.setReloadAction(this);
        return this;
    }

    /**
     * <br> Description: 添加请求参数
     * <br> Author:     wujianghua
     * <br> Date:        2018/10/23 11:57
     */
    @Override
    public AbRequestBuilder addParams(Map<String, String> params) {
        if (params != null)
            this.mParams = params;
        return this;
    }

    @Override
    public AbRequestBuilder addParams2(Map<String, Object> params) {
        if (params != null)
            this.mParams2 = params;
        return this;
    }


    /**
     * <br> Description: 添加上傳文件
     * <br> Author:      wujianghua
     * <br> Date:        2018/10/23 11:57
     */
    @Override
    public AbRequestBuilder addFiles(Map<String, List<String>> fileMap) {
        this.mFileMap = fileMap;
        return this;
    }

    @Override
    public AbRequestBuilder setIsFormRequest(boolean formRequest) {
        mIsFormRequest = formRequest;
        return this;
    }

    public abstract void build();

    @Override
    public String getReloadId() {
        return mTaskId;
    }

    /**
     * <br> Description: 重新加载
     * <br> Author:      wujianghua
     * <br> Date:        2018/10/23 11:57
     */
    @Override
    public void doReload() {
        build();
    }
}
