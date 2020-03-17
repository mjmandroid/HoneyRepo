package com.beautystudiocn.allsale.picture.config;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * <br> ClassName:   GlideConfig
 * <br> Description: Glide配置类
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/8/1 17:26
 */
public class GlideConfig {
    /*Glide option配置*/
    private RequestOptions mRequestOptions;
    /*Glide transition option配置*/
    private DrawableTransitionOptions mTransitionOptions;

    public GlideConfig(){
        mRequestOptions = new RequestOptions();
        mTransitionOptions = new DrawableTransitionOptions();
    }

    /**
     *<br> Description: 获取Glide的请求配置
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:27
     * @return
     *                  返回RequestOptions
     */
    public RequestOptions getRequestOptions() {
        return mRequestOptions;
    }

    /**
     *<br> Description: 设置Glide的请求配置
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:28
     * @param options
     *                  Glide的RequestOptions
     */
    public void setRequestOptions(RequestOptions options) {
        this.mRequestOptions = options;
    }

    /**
     *<br> Description: 获取Glide的TransitionOptions
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:29
     * @return
     *                  Glide的TransitionOptions
     */
    public DrawableTransitionOptions getTransitionOptions() {
        return mTransitionOptions;
    }

    /**
     *<br> Description: 设置Glide的TransitionOptions
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:30
     * @param options
     *                  Glide的TransitionOptions
     */
    public void setTransitionOptions(DrawableTransitionOptions options) {
        this.mTransitionOptions = options;
    }
}
