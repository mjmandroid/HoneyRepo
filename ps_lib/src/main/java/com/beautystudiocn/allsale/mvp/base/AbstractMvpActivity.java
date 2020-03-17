package com.beautystudiocn.allsale.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * <br> ClassName:   AbstractMvpActivity
 * <br> Description: MVP架构 Activity基类
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2017/8/1 15:55
 */
public abstract class AbstractMvpActivity<T extends BasePresenter>
        extends AppCompatActivity implements IMvpView {
    protected T mCurrentPresenter;
    /**
     * 自定义一个标志位，标记Activity的状态是否已经保存
     */
    private boolean mStateSaved = false;

    /**
     * <br> Description: 创建Presenters
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 17:10
     *
     * @return List presenterList
     */
    protected abstract T createPresenter();

    public T getCurrentPresenter() {
        return mCurrentPresenter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPresenter = createPresenter();
        if (mCurrentPresenter != null) {
            mCurrentPresenter.attachView(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mStateSaved = true;
    }

    @Override
    protected void onResume() {
        mStateSaved = false;
        super.onResume();
    }

    /**
     * 获取activity的保存状态，是否已经保存了Activity的状态。
     */
    public boolean isStateSaved() {
        return mStateSaved;
    }

    @Override
    public void onFinish() {
        if (!isFinishing()) {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCurrentPresenter != null) {
            mCurrentPresenter.detachView();
        }
    }
}
