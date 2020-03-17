package com.beautystudiocn.allsale.autoupdate.product.custom.builder;

/**
 * <br> ClassName:   IDownLoader
 * <br> Description: 下载构建器
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:25
 */
public interface IDownLoader {

    /**
     *<br> Description: 开始下载
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:39
     * @param url
     *                  下载地址
     * @param path
     *                  存储路径
     * @return
     *                  false表示正在下载中，true表示成功启动下载
     */
    boolean startDownLoad(String url, String path);
}
