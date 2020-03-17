package com.beautystudiocn.allsale.mvp.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.beautystudiocn.allsale.R;
import com.beautystudiocn.rxnetworklib.network.bean.ApiException;

import com.beautystudiocn.allsale.mvp.base.AbstractMvpActivity;
import com.beautystudiocn.allsale.util.WifiUtil;
import com.beautystudiocn.allsale.widget.dialog.CommonTipDialog;


/**
 * <br> ClassName:   AbstractNetworkActivity
 * <br> Description: 网络回调处理Activity基类
 * <br>
 * <br> Date:        2017/5/24 17:26
 */
public abstract class AbstractNetworkActivity<T extends NetworkPresenter>
        extends AbstractMvpActivity<T> implements INetworkView {
    private CommonTipDialog mNetWorkErrorDialog;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void displaySuccess(String taskId, Object result) {
    }

    @Override
    public void displayRequestFailure(String taskId, ApiException e) {
        String errorInfo = null;
        if (e != null) {
            errorInfo = e.getMessage();
        }
        errorInfo = !TextUtils.isEmpty(errorInfo) ? errorInfo : getString(R.string.connect_error);
        showToast(errorInfo);
    }

    @Override
    public void displayNetworkError(String taskId, ApiException e) {
        if (e != null) {
            showErrorNetDialog(e);
        }
    }

    @Override
    public void displayRequestNotNet(String taskId, ApiException e) {
        if (e != null) {
            showErrorNetDialog(e);
        }
    }

    public void showErrorNetDialog(final ApiException e) {
        if (mNetWorkErrorDialog != null && mNetWorkErrorDialog.isShowing()) {
            mNetWorkErrorDialog.setContent(e.getMessage());
        } else {
            String strConfirm = getString(R.string.cancel);
            if (e.getCode() == ApiException.NETWORD_ERROR) { //无开启网络
                strConfirm = getString(R.string.setting);
            }
            mNetWorkErrorDialog = new CommonTipDialog(this)
                    .init(true)
                    .setDialogTitle("")
                    .setContent(e.getMessage())
                    .setConfirm(strConfirm)
                    .setCancel(getString(R.string.sure))
                    .setOnClickListener(new CommonTipDialog.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            if (e.getCode() == ApiException.NETWORD_ERROR) {
                                WifiUtil.jumpSystemWifiSettingActivity(AbstractNetworkActivity.this);
                            }
                        }
                        @Override
                        public void clickCancel() {
                        }
                    });
        }
    }

}