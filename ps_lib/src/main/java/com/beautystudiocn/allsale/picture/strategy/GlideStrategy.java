package com.beautystudiocn.allsale.picture.strategy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.beautystudiocn.allsale.picture.GlideApp;
import com.beautystudiocn.allsale.picture.GlideRequest;
import com.beautystudiocn.allsale.picture.GlideRequests;
import com.beautystudiocn.allsale.picture.config.GlideConfig;
import com.beautystudiocn.allsale.picture.target.ITarget;

/**
 * <br> ClassName:   GlideStrategy
 * <br> Description: Glide策略类
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/8/1 17:56
 * @deprecated
 */
public class GlideStrategy implements IImageLoaderStrategy<GlideConfig> {
    private final int RESOURCE_STRING = 0;
    private final int RESOURCE_URI = 1;
    private final int RESOURCE_ID = 2;

    /*Glide配置请求*/
    private GlideRequests mRequest;
    /*默认资源ID*/
    private int mResourceID = -1;
    /*资源url*/
    private String mUrl;
    /*资源uri*/
    private Uri mUri;
    /*上下文*/
    private Context mContext;
    /*资源标识*/
    private int mResourceFlag;

    /**
     *<br> Description: Glide策略构造器
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:55
     * @param context
     *                  上下文
     */
    public GlideStrategy(Context context) {
        mRequest = GlideApp.with(context);
        mContext = context;
    }

    /**
     *<br> Description: Glide策略构造器
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:55
     * @param activity
     *                  上下文
     */
    public GlideStrategy(Activity activity) {
        mRequest = GlideApp.with(activity);
    }

    /**
     *<br> Description: Glide策略构造器
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:55
     * @param fragment
     *                  上下文
     */
    public GlideStrategy(Fragment fragment) {
        mRequest = GlideApp.with(fragment);
    }

    @Override
    public void resource(int resourceID) {
        mResourceFlag = RESOURCE_ID;
        mResourceID = resourceID;
    }

    @Override
    public void resource(String url) {
        mResourceFlag = RESOURCE_STRING;
        mUrl = url;
    }

    @Override
    public void resource(Uri uri) {
        mResourceFlag = RESOURCE_URI;
        mUri = uri;
    }

    private RequestBuilder<Bitmap> loadResourceAsBitmap() {
        switch (mResourceFlag) {
            case RESOURCE_ID:
                return mRequest.asBitmap().load(mResourceID);
            case RESOURCE_STRING:
                return mRequest.asBitmap().load(mUrl);
            case RESOURCE_URI:
                return mRequest.asBitmap().load(mUri);
            default:
                return mRequest.asBitmap().load("");
        }
    }

    private GlideRequest<Drawable> loadResourceAsDrawable() {
        switch (mResourceFlag) {
            case RESOURCE_ID:
                return mRequest.asDrawable().load(mResourceID);
            case RESOURCE_STRING:
                return mRequest.asDrawable().load(mUrl);
            case RESOURCE_URI:
                return mRequest.asDrawable().load(mUri);
            default:
                return mRequest.asDrawable().load("");
        }
    }

    @Override
    public void asBitmap(GlideConfig info, final ITarget<Bitmap> target) {
        loadResourceAsBitmap()
                .apply(info.getRequestOptions())
                .listener(new RequestListener(target))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource,
                                                Transition<? super Bitmap> transition) {
                        target.onResourceReady(resource);
                    }
                });
    }

    @Override
    public void asDrawable(GlideConfig info, final ITarget<Drawable> target) {
        loadResourceAsDrawable()
                .apply(info.getRequestOptions())
                .listener(new RequestListener(target))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource,
                                                Transition<? super Drawable> transition) {
                        target.onResourceReady(resource);
                    }
                });
    }

    @Override
    public void loadIn(ImageView iv, GlideConfig info, final ITarget<Drawable> target) {
        loadResourceAsDrawable()
                .apply(info.getRequestOptions())
                .transition(info.getTransitionOptions())
                .listener(new com.bumptech.glide.request.RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> t,
                                                boolean isFirstResource) {
                        if (target == null)
                            return false;

                        if (e != null)
                            target.onFailed(e.getMessage());
                        else
                            target.onFailed("");

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> t,
                                                   DataSource dataSource, boolean isFirstResource) {
                        return target != null && target.onResourceReady(resource);
                    }
                })
                .into(iv);
    }

    @Override
    public void clearMemory() {
        GlideApp.get(mContext).clearMemory();
    }

    @Override
    public void clearDisk() {
        GlideApp.get(mContext).clearDiskCache();
    }

    private class RequestListener implements com.bumptech.glide.request.RequestListener {
        private ITarget target;

        RequestListener(ITarget t){
            target = t;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target t,
                                    boolean isFirstResource) {
            if (e != null)
                target.onFailed(e.getMessage());
            else
                target.onFailed("");
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target,
                                       DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    }
}
