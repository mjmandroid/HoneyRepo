package com.streaming.better.honey.wedget.autoupdate.product.custom.download;


import com.streaming.better.honey.wedget.autoupdate.product.custom.builder.IDownLoader;

/**
 * <br> ClassName:   AbstractDownLoader
 * <br> Description: 下载管理器基类
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:52
 */
public abstract class AbstractDownLoader implements IDownLoader {
    protected IDownLoadListener mDownLoadListener;
    protected static boolean isUpdate;

    /**
     * 更新下载管理器基类构造
     * @param context 上下文
     * @param listener 下载监听接口
     */
    /**
     *<br> Description: 更新下载管理器基类构造
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:53
     * @param listener
     *                  下载监听接口
     */
    public AbstractDownLoader(IDownLoadListener listener) {
        mDownLoadListener = listener;
    }

    public static boolean isUpdate() {
        return isUpdate;
    }

    public static void setIsUpdate(boolean isUpdate) {
        AbstractDownLoader.isUpdate = isUpdate;
    }
}
