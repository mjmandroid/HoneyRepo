package com.streaming.better.honey.wedget.autoupdate.product.custom;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;






import com.streaming.better.honey.utils.CommonUtils;
import com.streaming.better.honey.utils.FileProvider;
import com.streaming.better.honey.wedget.autoupdate.product.AbstractAutoUpdateProduct;
import com.streaming.better.honey.wedget.autoupdate.product.custom.builder.IDialogBuilder;
import com.streaming.better.honey.wedget.autoupdate.product.custom.builder.IDownLoader;
import com.streaming.better.honey.wedget.autoupdate.product.custom.builder.IDownLoaderBuilder;
import com.streaming.better.honey.wedget.autoupdate.product.custom.builder.INotificationBuilder;
import com.streaming.better.honey.wedget.autoupdate.product.custom.dialog.DefaultUpdateDialog;
import com.streaming.better.honey.wedget.autoupdate.product.custom.download.DefaultDownLoader;
import com.streaming.better.honey.wedget.autoupdate.product.custom.download.IDownLoadListener;
import com.streaming.better.honey.wedget.autoupdate.product.custom.notification.DefaultUpdateNotification;

import java.io.IOException;


/**
 * <br> ClassName:   CustomUpdateProduct
 * <br> Description: 自动更新产品类
 * <br>
 * <br> Author:      yexiaochuan产品类
 * <br> Date:        2017/5/23 14:05
 */
public class CustomUpdateProduct extends AbstractAutoUpdateProduct<ICustomUpdate> implements IDownLoadListener,ICustomUpdate {
    /**
     * 版本为最新
     */
    public static final int ERROR_IS_NEWEST = 1001;
    /**
     * 正在更新中
     */
    public static final int ERROR_IS_UPDATING = 1002;
    /**
     * 网络请求失败
     */
    public static final int ERROR_HTTP_ERROR = 1003;
    /**
     * 文件创建失败
     */
    public static final int ERROR_FILE_CREATE_ERROR = 1004;

    private IDialogBuilder mDialogBuilder;
    private INotificationBuilder mNotificationBuilder;
    private IDownLoader mDownLoader;
    String mFile = "";

    /**
     *<br> Description: 初始化
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:20
     * @param context    上下文
     */
    public CustomUpdateProduct(final Context context) {
        super(context);
        mDialogBuilder = new DefaultUpdateDialog(context);
        mNotificationBuilder = new DefaultUpdateNotification(context.getApplicationContext());
        mDownLoader = new DefaultDownLoader(this);
    }

    public void setDialogBuilder(IDialogBuilder mDialogBuilder) {
        this.mDialogBuilder = mDialogBuilder;
    }

    public void setNotificationBuilder(INotificationBuilder mNotificationBuilder) {
        this.mNotificationBuilder = mNotificationBuilder;
    }

    public void setDownLoaderBuilder(IDownLoaderBuilder downLoaderBuilder) {
        mDownLoader = downLoaderBuilder.onDownLoaderBuild(this);
    }

    @Override
    protected ICustomUpdate build() {

        Log.e("sdfdffghj", getUpdateVersionCode()+"="+ CommonUtils.getAppVersionCode(mContext,mContext.getPackageName())+"="+getUpdateVersionName().equals( CommonUtils.getAppVersion(mContext,mContext.getPackageName())));
        if (getUpdateVersionCode() > CommonUtils.getAppVersionCode(mContext,mContext.getPackageName())|| !getUpdateVersionName().equals( CommonUtils.getAppVersion(mContext,mContext.getPackageName())) ) {
            if (mDialogBuilder != null && isShowUpdateDialog()) {
                mDialogBuilder.onDialogInit(mDownLoader, this);
            }
            if (mNotificationBuilder != null && isShowNotification()) {
                mNotificationBuilder.onNotificationInit(this);
            }
            //静默下载
            if (mDialogBuilder != null && !isShowUpdateDialog()) {
                mDownLoader.startDownLoad(getUpdateUrl(), getDownLoadPath());
            }
        } else {
            if (!isSilentCheck()) {
                onUpdateFail(ERROR_IS_NEWEST);
            }
        }
        return this;
    }

    /**
     *<br> Description: 更新失败
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:17
     * @param errorCode    失败代码
     */
    private void onUpdateFail(int errorCode) {
        if (getErrorCall() != null) {
            getErrorCall().onFailed(errorCode);
        }
    }

    @Override
    public void showUpdateDialog() {
        if (mDialogBuilder != null) {
            mDialogBuilder.showDialog();
        }
    }

    @Override
    public void destroyUpdateDialog() {
        destroyAllCall();
        if (mDialogBuilder != null) {
            mDialogBuilder.releaseDialog();
            mDialogBuilder = null;
        }
    }

    @Override
    public void onDownLoadStart() {
        if (isShowUpdateDialog()) {
            if (mDialogBuilder != null) {
                mDialogBuilder.onDialogStart();
            }
        }

        if (isShowNotification()) {
            if (mNotificationBuilder != null) {
                mNotificationBuilder.onNotificationStart();
            }
        }
    }

    @Override
    public void onDownLoadProgress(int progress) {
        if (isShowUpdateDialog()) {
            if (mDialogBuilder != null) {
                mDialogBuilder.onDialogProgress(progress);
            }
        }

        if (isShowNotification()) {
            if (mNotificationBuilder != null) {
                mNotificationBuilder.onNotificationProgress(progress);
            }
        }
    }

    @Override
    public void onDownLoadFinish(String apkFile) {
        mFile = apkFile;
        if (isShowUpdateDialog()) {
            if (mDialogBuilder != null) {
                mDialogBuilder.onDialogFinish(apkFile);
            }
        }
        if (isShowNotification()) {
            if (mNotificationBuilder != null) {
                mNotificationBuilder.onNotificationFinish(apkFile);
            }
        }
        setPermission(apkFile);
        install(apkFile);
    }

    /**
     *<br> Description: 如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/26 14:18
     * @param apkFile
     *                  安装文件路径
     */
    private void setPermission(String apkFile) {
        String[] command = {"chmod", "777", apkFile};
        ProcessBuilder builder = new ProcessBuilder(command);

        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *<br> Description: 安装程序
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/26 14:20
     * @param apkFile
     *                  安装文件路径
     */
    @Override
    public void install(String apkFile) {
        Intent installAPKIntent = FileProvider.getInstallApkIntent(mContext,apkFile);
        mContext.startActivity(installAPKIntent);
    }

    /**
     *<br> Description: 执行安装
     *<br> Author:      yangyinglong
     *<br> Date:        2018/9/18 18:32
     */
    public void executeInstall() {
        if (!TextUtils.isEmpty(mFile)) {
            Intent installAPKIntent = FileProvider.getInstallApkIntent(mContext, mFile);
            mContext.startActivity(installAPKIntent);
        }
    }

    @Override
    public void onDownLoadFail(int errorCode) {
        onUpdateFail(errorCode);

        if (isShowUpdateDialog()) {
            if (mDialogBuilder != null) {
                mDialogBuilder.onDialogFail(errorCode);
            }
        }

        if (isShowNotification()) {
            if (mNotificationBuilder != null) {
                mNotificationBuilder.onNotificationFail(errorCode);
            }
        }
    }
}
