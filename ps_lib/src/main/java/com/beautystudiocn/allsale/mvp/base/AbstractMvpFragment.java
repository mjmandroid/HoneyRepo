package com.beautystudiocn.allsale.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <br> ClassName:   AbstractMvpFragment
 * <br> Description: MVP架构 Fragment 基类
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2017/8/1 17:06
 */
public abstract class AbstractMvpFragment<T extends BasePresenter>
        extends Fragment implements IMvpView {
    protected T mBasePresenter;
    protected Activity mActivity;

    /**
     *<br> Description: 创建Presenters
     *<br> Author:      wujianghua
     *<br> Date:        2017/5/24 17:10
     *
     * @return List presenterList
     */
    protected abstract T createPresenter();

    public T getCurrentPresenter() {
        return mBasePresenter;
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        mBasePresenter = createPresenter();
        if (mBasePresenter != null) {
            mBasePresenter.attachView(this);
        }
        return onBindView(inflater,container,savedInstanceState);
    }

    /**
     *<br> Description: 使用onBindView代替onCreateView
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/11/13 17:20
     */
    protected abstract View onBindView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onUnbindView();
        if (mBasePresenter != null) {
            mBasePresenter.detachView();
        }
    }

    /**
     *<br> Description: 使用onUnbindView代替onDestroyView
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/11/13 17:20
     */
    protected void onUnbindView(){}
}
