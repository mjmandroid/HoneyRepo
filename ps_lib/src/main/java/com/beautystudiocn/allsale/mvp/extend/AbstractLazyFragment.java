package com.beautystudiocn.allsale.mvp.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.mvp.network.NetworkPresenter;
import com.beautystudiocn.allsale.sharedpreferences.Shared;


public abstract class AbstractLazyFragment<T extends NetworkPresenter> extends AbstractViewFragment<T> {
    protected FrameLayout mRootView;
    protected ViewStub mVsContent;
    protected boolean mIsLoadUi = true;
    private boolean mIsDestroy = false;
    protected Bundle mSavedInstanceState;

    /**
     * <br> Description: 延迟加载UI
     * <br> Date:        2017/7/21 9:33
     *
     * @return View
     */
    protected abstract View onLoadUI();

    /**
     * <br> Description: 设置setPageId
     * <br> Date:        2017/7/21 9:33
     */
    protected abstract void setPageId();

    @Nullable
    @Override
    public View onBindView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        this.mSavedInstanceState = savedInstanceState;
        mIsLoadUi = false;
        mIsDestroy = false;
        View view = inflater.inflate(R.layout.lib_lazy_fragment, null);
        mRootView = (FrameLayout) view.findViewById(R.id.frt_main);
        mVsContent = (ViewStub) view.findViewById(R.id.vs_content);
        setPageId();
        return view;
    }


    /**
     * <br> Description: 初始化UI
     * <br> Date:        2017/7/21 9:33
     */
    private void setupUI() {
        mVsContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mIsLoadUi || mIsDestroy) {
                    return;
                }
                View view = onLoadUI();
                mIsLoadUi = true;
                if (view != null) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setFillAfter(true);
                    alphaAnimation.setDuration(350);
                    view.startAnimation(alphaAnimation);
                }
            }
        }, 150);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsLoadUi && mVisible) {
            onLazyResume();
        }
    }

    /**
     * <br> Description: onResume
     * <br> Date:        2017/7/21 9:36
     */
    protected void onLazyResume() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsDestroy = true;
    }

    @Override
    public final void onPause() {
        super.onPause();
        if (mIsLoadUi) {
            onLazyPause();
        }
    }

    /**
     * <br> Description: onPause
     * <br> Date:        2017/7/21 9:36
     */
    protected void onLazyPause() {

    }

    private boolean mVisible = true;

    @Override
    public final void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        super.onVisibleToUserChanged(isVisibleToUser, invokeInResumeOrPause);
        mVisible = isVisibleToUser;
        if (mIsLoadUi) {
            onVisibilityChangedToUser(isVisibleToUser, invokeInResumeOrPause);
        } else if (isVisibleToUser && isVisible()) {
            setupUI();
        }
    }

    /**
     * <br> Description: 对用户是否可见
     * <br> Date:        2017/7/21 9:37
     *
     * @param isVisibleToUser       isVisibleToUser
     * @param invokeInResumeOrPause invokeInResumeOrPause
     */
    protected void onVisibilityChangedToUser(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

    }

    public void showAuthenticationDialog(){
        //长按删除当前地址
        //弹出对话框，跳转接单成功

    }
}
