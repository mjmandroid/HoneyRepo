package com.streaming.better.honey.wedget.autoupdate.product.custom.download;

/**
 * <br> ClassName:   IDownLoadListener
 * <br> Description: 下载监听接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:55
 */
public interface IDownLoadListener {
   /**
    *<br> Description: 下载开始
    *<br> Author:      yexiaochuan
    *<br> Date:        2017/6/8 17:55
    */
    void onDownLoadStart();

    /**
     *<br> Description: 下载中
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:56
     * @param progress
     *                  下载进度 （0 - 100）
     */
    void onDownLoadProgress(int progress);

    /**
     *<br> Description: 下载完成
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:56
     * @param apkFile
     *                  下载文件地址
     */
    void onDownLoadFinish(String apkFile);

    /**
     *<br> Description: 下载失败
     *                  <br>错误代码统一在{@link CustomUpdateProduct}类中写
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:57
     * @param errorCode
     *                  错误代码
     */
    void onDownLoadFail(int errorCode);
}
