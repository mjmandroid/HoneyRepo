package com.beautystudiocn.allsale.mvp.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.beautystudiocn.allsale.mvp.network.AbstractNetworkFragment;
import com.beautystudiocn.allsale.mvp.network.NetworkPresenter;
import com.beautystudiocn.allsale.mvp.network.AbstractNetworkFragment;
import com.beautystudiocn.allsale.mvp.network.NetworkPresenter;
import com.beautystudiocn.allsale.util.ToastUtil;
import com.beautystudiocn.allsale.widget.dialog.ProgressHUD;
import com.beautystudiocn.allsale.util.ToastUtil;
import com.beautystudiocn.allsale.widget.dialog.ProgressHUD;

public abstract class AbstractViewFragment<T extends NetworkPresenter> extends AbstractNetworkFragment<T> implements FragmentUserVisibleController.UserVisibleCallback{
    protected ProgressHUD mProgressHUD;
    private FragmentUserVisibleController mUserVisibleController;
    /**
     * 显示的是否是当前页
     */
    protected boolean mIsVisible = false;

    /**
     *<br> Description: 构造方法
     *<br> Author:      wujianghua
     *<br> Date:        2017/5/26 9:47
     */
    public AbstractViewFragment() {
        mUserVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUserVisibleController.activityCreated();
    }

    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        mUserVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isWaitingShowToUser() {
        return mUserVisibleController.isWaitingShowToUser();
    }

    @Override
    public boolean isVisibleToUser() {
        return mUserVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public boolean isPageVisible() {
        return mIsVisible;
    }

    /**
     * 当Fragment对用户可见或不可见的就会回调此方法，可以在这个方法里记录页面显示日志或初始化页面
     * 解决回退到Fragment，不调用setUserVisibleHint的情况
     */
    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mUserVisibleController.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser && isVisible();
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserVisibleController.resume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mUserVisibleController.pause();
    }

    @Override
    public void onFinish() {
        if (mActivity != null && !mActivity.isFinishing()) {
            mActivity.finish();
        }
    }

    @Override
    public void showToast(String info) {
        if (!TextUtils.isEmpty(info)) {
            ToastUtil.showToast(info, getView());
        }
    }

    @Override
    public void showLoading(String tips) {
        if (TextUtils.isEmpty(tips) || getActivity() == null || getActivity().isFinishing()) {
            return;
        }

        if (mProgressHUD == null) {
            mProgressHUD = ProgressHUD.show(getActivity(),tips);
        } else {
            mProgressHUD.setMessage(tips);
            if (!mProgressHUD.isShowing()) {
                mProgressHUD.show();
            }
        }
    }

    @Override
    public void dismissLoading() {
        if (mProgressHUD != null && getActivity() != null && !getActivity().isFinishing()) {
            mProgressHUD.dismiss();
            mProgressHUD = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressHUD != null) {
            mProgressHUD.dismiss();
            mProgressHUD = null;
        }
    }
}
