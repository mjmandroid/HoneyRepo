package com.streaming.better.honey.wedget.autoupdate.product.custom.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


import com.streaming.better.honey.R;
import com.streaming.better.honey.wedget.autoupdate.product.AbstractAutoUpdateProduct;
import com.streaming.better.honey.wedget.autoupdate.product.custom.builder.IDialogBuilder;
import com.streaming.better.honey.wedget.autoupdate.product.custom.builder.IDownLoader;

/**
 * <br> ClassName:   DefaultUpdateDialog
 * <br> Description: 默认自动更新弹窗
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:50
 */
public class DefaultUpdateDialog implements IDialogBuilder {
    private Context mContext;
    private ProgressDialog mUpdateDialog;
    private AlertDialog mMessageDialog;
    private boolean isUpdating;

    /**
     *<br> Description: 初始化
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:50
     * @param context
     *                  上下文
     */
    public DefaultUpdateDialog(Context context) {
        mContext = context;
    }

    @Override
    public void onDialogInit(final IDownLoader downLoader, AbstractAutoUpdateProduct setting) {
        AlertDialog.Builder normalDialog = createMessageDialog(downLoader,setting);
        mMessageDialog = normalDialog.show();
    }

    /**
     *<br> Description: 创建更新信息窗口
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/26 10:10
     * @param downLoader
     *                  下载器
     * @param setting
     *                  配置
     * @return
     *                  返回弹窗构建器
     */
    private AlertDialog.Builder createMessageDialog(final IDownLoader downLoader, final AbstractAutoUpdateProduct setting) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        if (TextUtils.isEmpty(setting.getUpdateTitle())) {
            normalDialog.setTitle(mContext.getString(R.string.version_update));
        } else {
            normalDialog.setTitle(setting.getUpdateTitle());
        }
        normalDialog.setMessage(setting.getUpdateMessage());
        normalDialog.setPositiveButton(mContext.getString(R.string.sure),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createProgressDialog(downLoader,setting);
                        setting.getConfirmListener().onClick(null);
                    }
                });
        if (!setting.isForceUpdate()) {
            normalDialog.setNegativeButton(mContext.getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setting.getCancelListener().onClick(null);
                        }
                    });
        } else {
            normalDialog.setCancelable(false);
        }

        return  normalDialog;
    }

    /**
     *<br> Description: 创建加载等待窗口
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/26 10:11
     * @param downLoader
     *                  下载器
     * @param setting
     *                  配置
     */
    private void createProgressDialog(IDownLoader downLoader ,AbstractAutoUpdateProduct setting) {
        if (!downLoader.startDownLoad(setting.getUpdateUrl(),setting.getDownLoadPath())) {
            return;
        }
        mUpdateDialog = new ProgressDialog(mContext);
        mUpdateDialog.setTitle(mContext.getString(R.string.update));
        mUpdateDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mUpdateDialog.setMax(100);
        mUpdateDialog.show();
        if (setting.isForceUpdate()) {
            mUpdateDialog.setCancelable(false);
            mUpdateDialog.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onDialogStart() {

    }

    @Override
    public void onDialogProgress(int progress) {
        isUpdating = true;
        if (mUpdateDialog != null) {
            mUpdateDialog.setProgress(progress);
            mUpdateDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false);
        }
    }

    @Override
    public void onDialogFinish(String apkFile) {
        isUpdating = false;
        if (mUpdateDialog != null) {
            mUpdateDialog.dismiss();
        }

        if (mMessageDialog != null) {
            mMessageDialog.dismiss();
        }
    }

    @Override
    public void onDialogFail(int errorCode) {
        isUpdating = false;
        if (mUpdateDialog != null) {
            mUpdateDialog.dismiss();
        }

        if (mMessageDialog != null) {
            mMessageDialog.dismiss();
        }
    }

    @Override
    public void showDialog() {
        if (isActivityFinish()) {
            return ;
        }

        if (isUpdating && mUpdateDialog != null) {
            mUpdateDialog.show();
        }

        if (!isUpdating && mMessageDialog != null) {
            mMessageDialog.show();
        }
    }

    /**
     *<br> Description: 检测页面是否已关闭
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/26 13:59
     */
    private boolean isActivityFinish() {
        return mContext != null && ((Activity) mContext).isFinishing();
    }

    @Override
    public void releaseDialog() {
        if (mMessageDialog != null) {
            mMessageDialog.dismiss();
        }
        if (mUpdateDialog != null) {
            mUpdateDialog.dismiss();
        }
        mUpdateDialog = null;
        mMessageDialog = null;
        mContext = null;
    }
}
