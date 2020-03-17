package com.beautystudiocn.allsale.autoupdate.product.custom.builder;


import com.beautystudiocn.allsale.autoupdate.product.AbstractAutoUpdateProduct;

/**
 * <br> ClassName:   INotificationBuilder
 * <br> Description: 通知构建接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:43
 */
public interface INotificationBuilder {
    /**
     *<br> Description: 通知初始化
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:43
     * @param setting
     *                  通知初始化
     */
    void onNotificationInit(AbstractAutoUpdateProduct setting);

    /**
     *<br> Description: 下载前
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:43
     */
    void onNotificationStart();

    /**
     *<br> Description: 下载中
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:44
     * @param progress
     *                  下载进度 （0-100）
     */
    void onNotificationProgress(int progress);

    /**
     *<br> Description: 下载结束
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:44
     * @param apkFile
     *                  下载文件地址
     */
    void onNotificationFinish(String apkFile);

    /**
     *<br> Description: 下载失败<br>错误代码统一在{@link CustomUpdateProduct}类中写
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:45
     * @param errorCode
     *                  错误代码
     */
    void onNotificationFail(int errorCode);
}
