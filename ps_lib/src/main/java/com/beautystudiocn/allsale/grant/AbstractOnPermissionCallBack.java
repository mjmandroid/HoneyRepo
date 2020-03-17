package com.beautystudiocn.allsale.grant;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.util.ToastUtil;
import com.beautystudiocn.allsale.widget.dialog.CommonTipDialog;

import io.reactivex.functions.Consumer;

/**
 * <br> ClassName:   AbstractOnPermissionCallBack
 * <br> Description: 默认回调
 * <br>
 * <br> Author:      wujun
 * <br> Date:        2017/8/3 11:32
 */
public abstract class AbstractOnPermissionCallBack implements OnPermissionCallback
        , Consumer<List<PermissionResult>> {

    private Activity mActivity;

    /*** 是否显示Dialog ***/
    protected boolean mShowRefuseDialog = false;

    public AbstractOnPermissionCallBack(Activity activity) {
        mActivity = activity;
    }

    public AbstractOnPermissionCallBack(Activity activity, boolean showRefuseDialog) {
        this.mActivity = activity;
        this.mShowRefuseDialog = showRefuseDialog;
    }

    @Override
    public void accept(List<PermissionResult> permissionResults) throws Exception {

        LinkedList<String> mRefusedList = new LinkedList<>();
        LinkedList<String> mNoAskList = new LinkedList<>();
        LinkedList<String> mAllowList = new LinkedList<>();
        //
        for (PermissionResult result : permissionResults) {
            switch (result.getType()) {

                case GRANTED:
                    mAllowList.add(result.getName());
                    break;
                case DENIED:
                    mRefusedList.add(result.getName());
                    break;

                case NO_ASK:
                    mNoAskList.add(result.getName());
                    break;
            }
        }
        doRequestComplete(permissionResults, mRefusedList, mNoAskList, mAllowList);

    }

    /**
     * <br> Description: 权限处理
     * <br> Author:      wujun
     * <br> Date:        2017/9/12 18:28
     *
     * @param permissionResults 所有的权限
     * @param mRefusedList      拒绝的权限
     * @param mNoAskList        不再询问的权限
     * @param mAllowList        允许的权限
     */
    private void doRequestComplete(List<PermissionResult> permissionResults,
                                   LinkedList<String> mRefusedList, LinkedList<String> mNoAskList,
                                   LinkedList<String> mAllowList) {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        // 全部允许
        if (mAllowList.size() == permissionResults.size()) {
            onRequestAllow(mAllowList.toArray(new String[mAllowList.size()]));
            return;
        }
        //不允许或者不再询问
        if ((!mRefusedList.isEmpty() && !mNoAskList.isEmpty()) || mRefusedList.size() > 0) {
            onRequestRefuse(mRefusedList
                    .toArray(new String[mRefusedList.size()]));
            return;
        }
        if (mNoAskList.size() > 0) {
            onRequestNoAsk(mNoAskList
                    .toArray(new String[mNoAskList.size()]));
        }
    }

    @Override
    public void onRequestRefuse(String... permissionName) {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        if (mShowRefuseDialog) {
            showDialog(mActivity, mActivity.getPackageName(),
                    PermissionUtils.getStringBuilder(Arrays.asList(permissionName)) + mActivity.getString(R.string.permission_limit_tip));
        } else {
            ToastUtil.showResultToast(mActivity, PermissionUtils.getStringBuilder(Arrays
                    .asList(permissionName)) + mActivity.getString(R.string.permission_apply_fail));
        }

    }

    @Override
    public void onRequestNoAsk(String... permissionName) {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        showDialog(mActivity, mActivity.getPackageName(),
                PermissionUtils.getStringBuilder(Arrays.asList(permissionName)) + mActivity.getString(R.string.permission_close_tip));
    }


    /**
     * <br> Description: 显示对话框
     * <br> Author:      wujun
     * <br> Date:        2017/8/3 9:43
     *
     * @param context     上下文
     * @param packageName 包名
     * @param message     权限名称
     */

    private void showDialog(@NonNull final Context context, @NonNull final String packageName,
                            @NonNull String message) {
        new CommonTipDialog(context)
                .init(false)
                .setContent(message)
                .setDialogTitle("")
                .setConfirm(mActivity.getString(R.string.ensure))
                .setCancel(Html.fromHtml("<font color=\"#999999\">" + mActivity.getString(R.string.cancel) + "</font>"))
                .setOnClickListener(new CommonTipDialog.OnConfirmListener() {
                    @Override
                    public void clickConfirm() {
                        PermissionUtils.openPermissionSettings(mActivity, packageName);
                    }

                    @Override
                    public void clickCancel() {
                        onSettingDialogCancel();
                    }
                });
    }


    /**
     * <br> Description: 不去设置页-取消
     * <br> Author:      wujun
     * <br> Date:        2017/9/5 17:57
     */
    protected void onSettingDialogCancel() {

    }


}
