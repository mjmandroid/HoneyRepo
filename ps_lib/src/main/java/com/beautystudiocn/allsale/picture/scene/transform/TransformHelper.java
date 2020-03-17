package com.beautystudiocn.allsale.picture.scene.transform;

import com.beautystudiocn.allsale.picture.loader.Monet;
import com.beautystudiocn.allsale.picture.loader.Monet;

/**
 * <br> ClassName:   TransformHelper
 * <br> Description: 图片变换Helper
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/9/22 9:29
 */
public class TransformHelper {

    /**
     *<br> Description: 获取Helper
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/22 9:30
     */
    private static ITransform getHelper() {
        switch (Monet.getCurrentStrategy()) {
            case Monet.STRATEGY_GLIDE:
                return new GlideTransform();
            default:
                return new GlideTransform();
        }
    }

    public static Object fitCenter() {
        return getHelper().fitCenter();
    }

    public static Object centerCrop() {
        return getHelper().centerCrop();
    }

    public static Object centerInside() {
        return getHelper().centerInside();
    }

    public static Object connerCrop(int radius) {
        return getHelper().connerCrop(radius);
    }

    public static Object connerCrop(int radius, int margin, RoundedCorner.CornerType cornerType) {
        return getHelper().connerCrop(radius,margin,cornerType);
    }

    public static Object circleCrop() {
        return getHelper().circleCrop();
    }

    public static Object fitXY() { return getHelper().fitXY(); }
}
