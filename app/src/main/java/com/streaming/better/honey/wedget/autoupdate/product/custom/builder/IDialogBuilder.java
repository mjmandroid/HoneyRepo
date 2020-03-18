package com.streaming.better.honey.wedget.autoupdate.product.custom.builder;


import com.streaming.better.honey.wedget.autoupdate.product.AbstractAutoUpdateProduct;

/**
 * <br> ClassName:   IDialogBuilder
 * <br> Description: 弹窗构建接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:21
 */
public interface IDialogBuilder {
    /**
     *<br> Description: 自定义初始化Dialog
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:22
     * @param downLoader
     *                  下载管理器
     * @param setting
     *                  更新配置信息
     */
    void onDialogInit(IDownLoader downLoader, AbstractAutoUpdateProduct setting);

   /**
    *<br> Description: 弹窗开始
    *<br> Author:      yexiaochuan
    *<br> Date:        2017/6/8 17:22
    */
    void onDialogStart();

    /**
     *<br> Description: 下载中
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:23
     * @param progress
     *                  下载进度（0 -100）
     */
    void onDialogProgress(int progress);

    /**
     *<br> Description: 下载完成
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:24
     * @param apkFile
     *                  下载文件地址
     */
    void onDialogFinish(String apkFile);

    /**
     *<br> Description: 下载失败,错误代码统一在{@link CustomUpdateProduct}类中写
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:24
     * @param errorCode
     *                  错误代码
     */
    void onDialogFail(int errorCode);

    /**
     *<br> Description: 展示Dialog
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:25
     */
    void showDialog();

    /**
     *<br> Description: 销毁Dialog，释放对页面Context的引用
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/26 16:02
     */
    void releaseDialog();
}
