package com.beautystudiocn.allsale.mvp.network;

import android.text.TextUtils;

import com.beautystudiocn.allsale.mvp.base.AbstractMvpDialog;
import com.beautystudiocn.allsale.mvp.base.AbstractMvpFragment;
import com.beautystudiocn.allsale.util.WifiUtil;
import com.beautystudiocn.allsale.widget.dialog.CommonTipDialog;
import com.beautystudiocn.rxnetworklib.network.bean.ApiException;

public abstract class AbstractNetworkDialog <T extends NetworkPresenter> extends AbstractMvpDialog<T> implements INetworkView {

    private CommonTipDialog mNetWorkErrorDialog;

    @Override
    public void displaySuccess(String taskId, Object result) {

    }

    @Override
    public void displayRequestFailure(String taskId, ApiException e) {
        String errorInfo = null;
        if (e != null) {
            errorInfo = e.getMessage();
        }
        errorInfo = !TextUtils.isEmpty(errorInfo) ? errorInfo : "请求失败";
        showToast(errorInfo);
    }

    @Override
    public void displayNetworkError(String taskId, ApiException e) {
        if (mActivity == null) {
            return;
        }

        if (mActivity instanceof AbstractNetworkActivity) {
            ((AbstractNetworkActivity)mActivity).showErrorNetDialog(e);
        } else {
            showErrorNetDialog(e);
        }
    }

    @Override
    public void displayRequestNotNet(String taskId, ApiException e) {
        if (mActivity == null) {
            return;
        }
        if (mActivity instanceof AbstractNetworkActivity) {
            ((AbstractNetworkActivity)mActivity).showErrorNetDialog(e);
        } else {
            showErrorNetDialog(e);
        }
    }

    public void showErrorNetDialog(final ApiException e) {
        if (mActivity == null) {
            return;
        }
        String shortMessage = null;
        if (!TextUtils.isEmpty(e.getMessage())) {
            shortMessage = e.getMessage().split("：")[0];
        }
        if (mNetWorkErrorDialog != null && mNetWorkErrorDialog.isShowing()) {
            mNetWorkErrorDialog.setContent(shortMessage);
        } else {
            String strConfirm = "取消";
            if (e.getCode() == ApiException.NETWORD_ERROR) { //无开启网络
                strConfirm = "设置";
            } else if(e.toString().contains("127.0.0.1")){
                strConfirm = "";
            }
            mNetWorkErrorDialog = new CommonTipDialog(mActivity)
                    .init(true)
                    .setDialogTitle("")
                    .setContent(shortMessage)
                    .setConfirm(strConfirm)
                    .setCancel("确定")
                    .setOnClickListener(new CommonTipDialog.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            if (e.getCode() == ApiException.NETWORD_ERROR) {
                                WifiUtil.jumpSystemWifiSettingActivity(mActivity);
                            }
                        }
                        @Override
                        public void clickCancel() {
                        }
                    });
        }
    }

}
