package com.beautystudiocn.allsale.picture.loader;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;

import com.beautystudiocn.allsale.picture.strategy.GlideModuleStrategy;
import com.beautystudiocn.allsale.picture.strategy.IImageLoadRequest;
import com.beautystudiocn.allsale.picture.strategy.GlideModuleStrategy;
import com.beautystudiocn.allsale.picture.strategy.IImageLoadRequest;

/**
 * <br> ClassName:   Monet
 * <br> Description: 图片加载统一入口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/9/12 14:11
 */
public class Monet {
    public static final int STRATEGY_GLIDE = 1;
    private static int currentStrategy = STRATEGY_GLIDE;

    public synchronized static void init(@IntRange(from = STRATEGY_GLIDE , to = STRATEGY_GLIDE) int strategyId) {
        currentStrategy = strategyId;
    }

    public synchronized static int getCurrentStrategy() {
        return currentStrategy;
    }

    public static IImageLoadRequest get(Context context) {
        switch (currentStrategy) {
            case STRATEGY_GLIDE:
                return new GlideModuleStrategy(context);
            default:
                return new GlideModuleStrategy(context);
        }
    }

    public static IImageLoadRequest get(Activity context) {
        switch (currentStrategy) {
            case STRATEGY_GLIDE:
                return new GlideModuleStrategy(context);
            default:
                return new GlideModuleStrategy(context);
        }
    }

    public static IImageLoadRequest get(Fragment context) {
        switch (currentStrategy) {
            case STRATEGY_GLIDE:
                return new GlideModuleStrategy(context);
            default:
                return new GlideModuleStrategy(context);
        }
    }
}
