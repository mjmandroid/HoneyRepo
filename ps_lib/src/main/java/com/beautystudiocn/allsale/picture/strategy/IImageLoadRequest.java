package com.beautystudiocn.allsale.picture.strategy;

import android.net.Uri;
import android.view.View;

/**
 * <br> ClassName:   IImageLoadRequest
 * <br> Description: 加载请求管理接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/9/12 14:09
 */
public interface IImageLoadRequest {
    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:41
     * @param resourceID
     *                  本地资源ID
     * @return
     *                  配置器
     */
    IImageSceneRequest load(int resourceID);

    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:41
     * @param url
     *                  网络URL地址，本地文件地址等
     * @return
     *                  配置器
     */
    IImageSceneRequest load(String url);

    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:42
     * @param uri
     *                  本地资源uri
     * @return
     *                  配置器
     */
    IImageSceneRequest load(Uri uri);

    /**
     *<br> Description: 清除控件bitmap缓存
     *<br> Author:      yexiaochuan
     *<br> Date:        2018/3/27 10:59
     */
    void clear(View view);

    /**
     *<br> Description: 缓存清理
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:40
     */
    void clearMemory();

    /**
     *<br> Description: 清除硬盘缓存
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:42
     */
    void clearDisk();
}
