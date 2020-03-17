package com.beautystudiocn.allsale.picture.scene.transform;

/**
 * <br> ClassName:   ITransform
 * <br> Description: 图片变换接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/9/22 10:22
 */
public interface ITransform {
    /**
     *<br> Description: 中心适应
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/22 9:17
     */
    Object fitCenter();

    /**
     *<br> Description: 铺满xy
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/22 9:17
     */
    Object fitXY();

    /**
     *<br> Description: 中心裁剪
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/11 15:01
     */
    Object centerCrop();

    /**
     *<br> Description: 中心包裹
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/11 15:02
     */
    Object centerInside();

    /**
     *<br> Description: 圆角裁剪
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/11 15:02
     * @param radius
     *                  角度
     * @return
     */
    Object connerCrop(int radius);

    /**
     *<br> Description: 圆角裁剪
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/11 15:02
     * @param radius
     *                  角度
     * @return
     */
    Object connerCrop(int radius, int margin, RoundedCorner.CornerType cornerType);

    /**
     *<br> Description: 圆形裁剪
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/11 15:02
     */
    Object circleCrop();
}
