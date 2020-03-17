package com.beautystudiocn.allsale.picture.config;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;

public interface IModuleConfig {
    /**
     *<br> Description: 配置Glide的缓存大小、Disk存储大小等
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:31
     * @param context
     *                  上下文
     * @param builder
     *                  配置Builder
     */
    void applyOptions(Context context, GlideBuilder builder);

    /**
     *<br> Description: 注册Glide的组件
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:32
     * @param context
     *                  上下文
     * @param registry
     *                  注册器
     */
    void registerComponents(Context context, Registry registry);
}
