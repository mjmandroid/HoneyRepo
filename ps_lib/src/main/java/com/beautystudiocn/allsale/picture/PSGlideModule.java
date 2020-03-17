package com.beautystudiocn.allsale.picture;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import com.beautystudiocn.allsale.picture.config.IModuleConfig;
import com.beautystudiocn.allsale.picture.config.IModuleConfig;

import java.io.InputStream;

/**
 * <br> ClassName:   PSGlideModule
 * <br> Description: 派生Glide图片框架配置入口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/8/1 17:33
 */
@GlideModule
public class PSGlideModule extends AppGlideModule {

    private static IModuleConfig mOptions;

    /**
     *<br> Description: Glide的配置入口，只会调用一次，建议在Application中全局配置
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:34
     * @param mOptions
     *                  配置接口
     */
    public static void setOptions(IModuleConfig mOptions) {
        PSGlideModule.mOptions = mOptions;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
        builder.setBitmapPool(new LruBitmapPool(10 * 1024 * 1024));
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));

        if(mOptions != null) {
            mOptions.applyOptions(context, builder);
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
        if(mOptions != null) {
            mOptions.registerComponents(context, registry);
        }
    }
}
