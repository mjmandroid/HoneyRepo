package com.beautystudiocn.allsale.picture.strategy;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.beautystudiocn.allsale.picture.target.ITarget;


/**
 * <br> ClassName:   IImageLoaderStrategy
 * <br> Description: 图片加载策略接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/8/1 18:01
 * @deprecated     接口不再维护
 * @see             IImageLoadRequest
 * @see             IImageSceneRequest
 */
public interface IImageLoaderStrategy<T> {
    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:41
     * @param resourceID
     *                  本地资源ID
     * @return
     *                  配置器
     */
    void resource(int resourceID);

    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:41
     * @param url
     *                  网络URL地址，本地文件地址等
     * @return
     *                  配置器
     */
    void resource(String url);

    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:42
     * @param uri
     *                  本地资源uri
     * @return
     *                  配置器
     */
    void resource(Uri uri);

    /**
     *<br> Description: 输出Bitmap
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:50
     * @param target
     *                  Bitmap回调接口
     */
    void asBitmap(T info, ITarget<Bitmap> target);

    /**
     *<br> Description: 输出Drawable
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:51
     * @param target
     *                  Drawable回调接口
     */
    void asDrawable(T info, ITarget<Drawable> target);

    /**
     *<br> Description: 控件加载
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:52
     * @param iv
     *                  ImageVIew加载
     * @param target
     *                  Drawable回调接口
     */
    void loadIn(ImageView iv, T info, ITarget<Drawable> target);

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
