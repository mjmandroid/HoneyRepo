package com.beautystudiocn.allsale.picture.strategy;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.widget.ImageView;

import com.beautystudiocn.allsale.picture.config.MonetConfig;
import com.beautystudiocn.allsale.picture.scene.IConfigScene;
import com.beautystudiocn.allsale.picture.scene.transform.RoundedCorner;
import com.beautystudiocn.allsale.picture.target.ITarget;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * <br> ClassName:   IImageSceneRequest
 * <br> Description: 加载场景请求接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/9/12 14:10
 */
public interface IImageSceneRequest<Config> {
    /**
     * <br> Description: 设置图片加载器的配置
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 14:30
     *
     * @param configScene 场景配置
     */
    IImageSceneRequest scene(IConfigScene<Config> configScene);

    /**
     * <br> Description: 占位符
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 14:59
     *
     * @param resourceId 资源id
     * @return
     */
    IImageSceneRequest placeholder(int resourceId);

    /**
     * <br> Description: 占位符
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 14:59
     *
     * @param drawable drawable
     * @return
     */
    IImageSceneRequest placeholder(Drawable drawable);

    /**
     * <br> Description: 错误占位符
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:00
     *
     * @param resourceId 资源ID
     * @return
     */
    IImageSceneRequest error(int resourceId);

    /**
     * <br> Description: 错误占位符
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:00
     *
     * @param drawable drawable
     * @return
     */
    IImageSceneRequest error(Drawable drawable);

    /**
     * <br> Description: 中心适应
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/22 9:17
     */
    IImageSceneRequest fitCenter();

    /**
     * <br> Description: 铺满xy
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/22 15:26
     */
    IImageSceneRequest fitXY();

    /**
     * <br> Description: 中心裁剪
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:01
     */
    IImageSceneRequest centerCrop();

    /**
     * <br> Description: 中心包裹
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:02
     */
    IImageSceneRequest centerInside();

    /**
     * <br> Description: 圆角裁剪
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:02
     *
     * @param radius 角度
     * @return
     */
    IImageSceneRequest connerCrop(int radius);

    /**
     * <br> Description: 圆角裁剪
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:02
     *
     * @param radius 角度
     * @return
     */
    IImageSceneRequest connerCrop(int radius, int margin, RoundedCorner.CornerType cornerType);

    /**
     * <br> Description: 圆形裁剪
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:02
     */
    IImageSceneRequest circleCrop();

    /**
     * <br> Description: 图形转换
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:03
     *
     * @param transform 转换参数
     * @return
     */
    IImageSceneRequest transform(Object... transform);

    /**
     * <br> Description: 图片大小限制
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:04
     *
     * @param width  宽度限制
     * @param height 高度限制
     * @return
     */
    IImageSceneRequest resize(int width, int height);

    /**
     * <br> Description: 缓存设置
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:02
     *
     * @param type 缓存类型
     * @return
     */
    IImageSceneRequest cache(@IntRange(from = MonetConfig.CACHE_TYPE_NONE, to = MonetConfig.CACHE_TYPE_ALL) int type);

    /**
     * <br> Description: 解码设置
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/9/11 15:02
     *
     * @param type 解码类型
     * @return
     */
    IImageSceneRequest decode(@IntRange(from = MonetConfig.DECODE_TYPE_565, to = MonetConfig.DECODE_TYPE_8888) int type);

    /**
     * <br> Description: 输出Bitmap
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/1 17:50
     *
     * @param target Bitmap回调接口
     */
    void asBitmap(ITarget<Bitmap> target);

    /**
     * <br> Description: 输出Drawable
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/1 17:51
     *
     * @param target Drawable回调接口
     */
    void asDrawable(ITarget<Drawable> target);

    /**
     * <br> Description: 控件加载
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/1 17:52
     *
     * @param iv     ImageVIew加载
     * @param target Drawable回调接口
     */
    void into(ImageView iv, ITarget<Drawable> target);

    /**
     * <br> Description: 控件加载
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/1 17:52
     *
     * @param iv ImageVIew加载
     */
    void into(ImageView iv);

    IImageSceneRequest skipMemoryCache(boolean isSkip);

    IImageSceneRequest diskCacheStrategy(DiskCacheStrategy diskCacheStrategy);
}
