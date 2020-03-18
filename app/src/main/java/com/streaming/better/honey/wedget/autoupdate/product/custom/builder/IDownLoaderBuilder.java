package com.streaming.better.honey.wedget.autoupdate.product.custom.builder;


import com.streaming.better.honey.wedget.autoupdate.product.custom.download.IDownLoadListener;

/**
 * <br> ClassName:   IDownLoaderBuilder
 * <br> Description: 下载管理器构建接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:40
 */
public interface IDownLoaderBuilder {

    /**
     *<br> Description: 下载构建
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:42
     * @param listener
     *                  下载监听接口
     * @return
     *                  返回下载器
     */
    IDownLoader onDownLoaderBuild(IDownLoadListener listener);
}
