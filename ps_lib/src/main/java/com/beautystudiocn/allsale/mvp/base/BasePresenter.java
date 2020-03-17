package com.beautystudiocn.allsale.mvp.base;

import android.content.Context;
import android.widget.ImageView;

import com.beautystudiocn.allsale.util.AppUtil;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: wujianghua
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2017/5/19 14:45
 */
public class BasePresenter<T extends IMvpView,M extends IBaseModel> {
    /***todo描述这个字段的含义***/
    protected Reference<T> mViewRef;//View接口类型弱引用
    private M module;
    private T mProxyView;
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view); //建立关联
        mViewRef = new WeakReference<>(view);
        mProxyView = (T) Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new MvpViewHandler(mViewRef.get()));
        if (this.module == null) {
            this.module = createModule();
        }
    }

    protected M createModule() {
        return null;
    }

    protected T getView() {
        return mProxyView;//获取View
    }

    protected M getModule() {
        return module;
    }

    protected Context getContext() {
        return getView().getContext();
    }

    public boolean isViewAttached() {//判断是否与View建立了关联
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {//解除关联
        this.module = null;
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public String getString(int stringId) {
        return AppUtil.getContext().getString(stringId);
    }
    /**
     * View代理类  防止 页面关闭P异步操作调用V 方法 空指针问题
     */
    private class MvpViewHandler implements InvocationHandler {

        private IMvpView mvpView;

        MvpViewHandler(IMvpView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果V层没被销毁, 执行V层的方法.
            if (isViewAttached()) {
                return method.invoke(mvpView, args);
            } //P层不需要关注V层的返回值
            return null;
        }
    }
}
