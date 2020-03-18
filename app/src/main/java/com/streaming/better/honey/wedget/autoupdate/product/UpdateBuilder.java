package com.streaming.better.honey.wedget.autoupdate.product;

import android.graphics.Bitmap;
import android.view.View;

/**
 * <br> ClassName:   UpdateBuilder
 * <br> Description: 自动更新产品构建类
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 18:06
 */
public class UpdateBuilder<T> {
    private AbstractAutoUpdateProduct mProduct;

    /**
     *<br> Description: 初始化
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 18:06
     * @param product
     *                  自动更新产品
     */
    public UpdateBuilder(AbstractAutoUpdateProduct product) {
        mProduct = product;
    }

  /**
   *<br> Description: 是否显示通知
   *<br> Author:      yexiaochuan
   *<br> Date:        2017/7/7 11:16
   */
    public UpdateBuilder<T> isShowUpdateNotification(boolean isShow) {
        mProduct.setIsShowNotification(isShow);
        return this;
    }

   /**
    *<br> Description: 是否展示弹窗
    *<br> Author:      yexiaochuan
    *<br> Date:        2017/7/7 11:17
    */
    public UpdateBuilder<T> isShowUpdateDialog(boolean isShow) {
        mProduct.setIsShowUpdateDialog(isShow);
        return this;
    }

    /**
     *<br> Description: 是否强制更新
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:17
     */
    public UpdateBuilder<T> isForceUpdate(boolean isForce) {
        mProduct.setIsForceUpdate(isForce);
        return this;
    }

    /**
     *<br> Description: 是否强制更新
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:17
     */
    public UpdateBuilder<T> isSilentCheck(boolean isSilent) {
        mProduct.setIsSilentCheck(isSilent);
        return this;
    }

    /**
     *<br> Description: 是否显示取消按钮
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/6 11:23
     */
    public UpdateBuilder<T> isShowCancelBtn(boolean isShow) {
        mProduct.setShowCancelBtn(isShow);
        return this;
    }

  /**
   *<br> Description: 设置下载地址
   *<br> Author:      yexiaochuan
   *<br> Date:        2017/7/7 11:17
   */
    public UpdateBuilder<T> setUpdateUrl(String url) {
        mProduct.setUpdateUrl(url);
        return this;
    }

   /**
    *<br> Description: 设置更新提示内容
    *<br> Author:      yexiaochuan
    *<br> Date:        2017/7/7 11:18
    */
    public UpdateBuilder<T> setUpdateMessage(String message) {
        mProduct.setUpdateMessage(message);
        return this;
    }

    /**
     *<br> Description: 设置更新提示内容
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:18
     */
    public UpdateBuilder<T> setUpdateMessage(CharSequence message) {
        mProduct.setUpdateMessage(message);
        return this;
    }

    /**
     *<br> Description: 设置更新版本的版本号
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:18
     */
    public UpdateBuilder<T> setUpdateVersionCode(int versionCode) {
        mProduct.setUpdateVersionCode(versionCode);
        return this;
    }

    /**
     *<br> Description: 设置当前版本的版本号
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:18
     */
    public UpdateBuilder<T> setCurrentVersionCode(int versionCode) {
        mProduct.setCurrentVersionCode(versionCode);
        return this;
    }

    /**
     *<br> Description: 设置更新版本的版本名称
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:18
     */
    public UpdateBuilder<T> setUpdateVersionName(String versionName) {
        mProduct.setUpdateVersionName(versionName);
        return this;
    }

    /**
     *<br> Description: 设置更新弹窗确定按钮的监听接口
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:18
     */
    public UpdateBuilder<T> setUpdateDialogConfirmClickListener(View.OnClickListener listener) {
        mProduct.setConfirmListener(listener);
        return this;
    }

    /**
     *<br> Description: 设置更新弹窗取消按钮的监听接口
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:18
     */
    public UpdateBuilder<T> setUpdateDialogCancelClickListener(View.OnClickListener listener) {
        mProduct.setCancelListener(listener);
        return this;
    }

    /**
     *<br> Description: 设置下载文件的存储路径
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/7 11:18
     */
    public UpdateBuilder<T> setDownLoadFilePath(String path) {
        mProduct.setDownLoadPath(path);
        return this;
    }

    /**
     *<br> Description: 设置失败回调
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/7/19 17:36
     * @param errorCall
     *                  失败回调
     * @return
     *                  错误代码
     */
    public UpdateBuilder<T> setUpdateErrorCall(AbstractAutoUpdateProduct.IUpdateError errorCall) {
        mProduct.setErrorCall(errorCall);
        return this;
    }

    /**
     *<br> Description: 设置更新标题
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/18 9:07
     */
    public UpdateBuilder<T> setUpdateTitle(CharSequence title) {
        mProduct.setUpdateTitle(title);
        return this;
    }

    /**
     *<br> Description: 设置通知栏小图标
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/10/27 8:48
     */
    public UpdateBuilder<T> setNotificationSmallIcon(int smallIcon) {
        mProduct.setNotifySmallIcon(smallIcon);
        return this;
    }

    /**
     *<br> Description: 设置通知栏大图标
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/10/27 8:49
     */
    public UpdateBuilder<T> setNotificationLargeIcon(Bitmap largeIcon) {
        mProduct.setNotifyLargeIcon(largeIcon);
        return this;
    }

    /**
     * 更新构建
     * @return 自动更新功能
     */
    public T build() {
        return (T)mProduct.build();
    }
}
