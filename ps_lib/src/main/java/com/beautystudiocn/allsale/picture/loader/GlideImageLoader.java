package com.beautystudiocn.allsale.picture.loader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.beautystudiocn.allsale.picture.strategy.GlideStrategy;
import com.beautystudiocn.allsale.picture.config.GlideConfig;
import com.beautystudiocn.allsale.picture.strategy.GlideStrategy;

/**
 * <br> ClassName:   GlideImageLoader
 * <br> Description: Glide框架的封装
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/8/1 17:36
 * @deprecated 请使用Monet
 */
public class GlideImageLoader extends ImageLoader<GlideConfig> {

    public GlideImageLoader(Context context) {
        super(context,new GlideStrategy(context),new GlideConfig());
    }

    public GlideImageLoader(Activity activity) {
        super(activity,new GlideStrategy(activity),new GlideConfig());
    }

    public GlideImageLoader(Fragment fragment) {
        super(fragment.getContext(),new GlideStrategy(fragment),new GlideConfig());
    }
}
