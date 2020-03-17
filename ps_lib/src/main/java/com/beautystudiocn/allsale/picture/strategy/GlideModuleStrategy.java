package com.beautystudiocn.allsale.picture.strategy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.beautystudiocn.allsale.picture.GlideApp;
import com.beautystudiocn.allsale.picture.GlideRequest;
import com.beautystudiocn.allsale.picture.GlideRequests;
import com.beautystudiocn.allsale.picture.config.GlideConfig;
import com.beautystudiocn.allsale.picture.config.MonetConfig;
import com.beautystudiocn.allsale.picture.scene.IConfigScene;
import com.beautystudiocn.allsale.picture.scene.transform.FitXy;
import com.beautystudiocn.allsale.picture.scene.transform.RoundedCorner;
import com.beautystudiocn.allsale.picture.target.ITarget;

/**
 * <br> ClassName:   GlideModuleStrategy
 * <br> Description: Glide策略算法
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/9/12 14:05
 */
public class GlideModuleStrategy implements IImageLoadRequest, IImageSceneRequest<GlideConfig> {
    private final int RESOURCE_STRING = 0;
    private final int RESOURCE_URI = 1;
    private final int RESOURCE_ID = 2;

    /**
     * Glide配置请求
     */
    private GlideRequests mRequest;
    /**
     * 默认资源ID
     */
    private int mResourceID = -1;
    /**
     * 资源url
     */
    private String mUrl;
    /**
     * 资源uri
     */
    private Uri mUri;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 资源标识
     */
    private int mResourceFlag = -1;
    private GlideConfig config;

    /**
     * <br> Description: Glide策略构造器
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/1 17:55
     *
     * @param context 上下文
     */
    public GlideModuleStrategy(Context context) {
        mRequest = GlideApp.with(context);
        mContext = context;
        config = new GlideConfig();
    }

    /**
     * <br> Description: Glide策略构造器
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/1 17:55
     *
     * @param activity 上下文
     */
    public GlideModuleStrategy(Activity activity) {
        mRequest = GlideApp.with(activity);
        mContext = activity;
        config = new GlideConfig();
    }

    /**
     * <br> Description: Glide策略构造器
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/1 17:55
     *
     * @param fragment 上下文
     */
    public GlideModuleStrategy(Fragment fragment) {
        mRequest = GlideApp.with(fragment);
        mContext = fragment.getContext();
        config = new GlideConfig();
    }

    @Override
    public IImageSceneRequest<GlideConfig> load(int resourceID) {
        mResourceFlag = RESOURCE_ID;
        mResourceID = resourceID;
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> load(String url) {
        mResourceFlag = RESOURCE_STRING;
        mUrl = url;
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> load(Uri uri) {
        mResourceFlag = RESOURCE_URI;
        mUri = uri;
        return this;
    }

    @Override
    public void clear(View view) {
        mRequest.clear(view);
    }

    @Override
    public IImageSceneRequest<GlideConfig> scene(IConfigScene<GlideConfig> configScene) {
        configScene.config(config);
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> placeholder(int resourceId) {
        config.getRequestOptions().placeholder(resourceId);
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> placeholder(Drawable drawable) {
        config.getRequestOptions().placeholder(drawable);
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> error(int resourceId) {
        config.getRequestOptions().error(resourceId);
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> error(Drawable drawable) {
        config.getRequestOptions().error(drawable);
        return this;
    }

    @Override
    public IImageSceneRequest fitCenter() {
        config.getRequestOptions().fitCenter();
        return this;
    }

    @Override
    public IImageSceneRequest fitXY() {
        config.getRequestOptions().transform(new FitXy());
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> centerCrop() {
        config.getRequestOptions().centerCrop();
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> centerInside() {
        config.getRequestOptions().centerInside();
        return this;
    }

    @Override
    public IImageSceneRequest connerCrop(int radius) {
        config.getRequestOptions().transform(new RoundedCorners(radius));
        return this;
    }

    @Override
    public IImageSceneRequest connerCrop(int radius, int margin, RoundedCorner.CornerType cornerType) {
        config.getRequestOptions().transform(new RoundedCorner(radius, margin, cornerType));
        return this;
    }

    @Override
    public IImageSceneRequest circleCrop() {
        config.getRequestOptions().transform(new CircleCrop());
        return this;
    }

    @Override
    public IImageSceneRequest skipMemoryCache(boolean isSkip) {
        config.getRequestOptions().skipMemoryCache(isSkip);
        return this;
    }

    @Override
    public IImageSceneRequest diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        config.getRequestOptions().diskCacheStrategy(diskCacheStrategy);
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> transform(Object... transform) {
        Transformation<Bitmap>[] array = new Transformation[transform.length];
        for (int i = 0; i < transform.length; i++) {
            if (transform[i] instanceof BitmapTransformation) {
                array[i] = (Transformation<Bitmap>) transform[i];
            } else {
                throw new ClassCastException("无法被转换为BitmapTransformation类型");
            }
        }

        config.getRequestOptions().transform(new MultiTransformation(array));
        return this;
    }

    @Override
    public IImageSceneRequest<GlideConfig> resize(int width, int height) {
        config.getRequestOptions().override(width, height);
        return this;
    }

    @Override
    public IImageSceneRequest cache(
            @IntRange(from = MonetConfig.CACHE_TYPE_NONE, to = MonetConfig.CACHE_TYPE_ALL) int type) {
        switch (type) {
            case MonetConfig.CACHE_TYPE_NONE:
                config.getRequestOptions().skipMemoryCache(true);
                config.getRequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case MonetConfig.CACHE_TYPE_DISK:
                config.getRequestOptions().skipMemoryCache(true);
                config.getRequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case MonetConfig.CACHE_TYPE_MEMORY:
                config.getRequestOptions().skipMemoryCache(false);
                config.getRequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case MonetConfig.CACHE_TYPE_ALL:
                config.getRequestOptions().skipMemoryCache(false);
                config.getRequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            default:
                config.getRequestOptions().skipMemoryCache(true);
                config.getRequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
        }
        return this;
    }

    @Override
    public IImageSceneRequest decode(
            @IntRange(from = MonetConfig.DECODE_TYPE_565, to = MonetConfig.DECODE_TYPE_8888) int type) {
        switch (type) {
            case MonetConfig.DECODE_TYPE_565:
                config.getRequestOptions().format(DecodeFormat.PREFER_RGB_565);
                break;
            case MonetConfig.DECODE_TYPE_4444:
                config.getRequestOptions().format(DecodeFormat.PREFER_RGB_565);
                break;
            case MonetConfig.DECODE_TYPE_8888:
                config.getRequestOptions().format(DecodeFormat.PREFER_ARGB_8888);
                break;
            default:
                config.getRequestOptions().format(DecodeFormat.PREFER_RGB_565);
                break;
        }
        return this;
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
    public void asBitmap(final ITarget<Bitmap> target) {
        loadResourceAsBitmap()
                .apply(config.getRequestOptions())
                .listener(new com.bumptech.glide.request.RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> drawableTarget,
                                                boolean isFirstResource) {
                        if (target == null) {
                            return false;
                        }

                        if (e != null) {
                            target.onFailed(e.getMessage());
                        } else {
                            target.onFailed("");
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model,
                                                   Target<Bitmap> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource,
                                                Transition<? super Bitmap> transition) {
                        if (target == null) {
                            return;
                        }

                        target.onResourceReady(resource);
                    }
                });
    }

    @Override
    public void asDrawable(final ITarget<Drawable> target) {
        loadResourceAsDrawable()
                .apply(config.getRequestOptions())
                .listener(new com.bumptech.glide.request.RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> drawableTarget,
                                                boolean isFirstResource) {
                        if (target == null) {
                            return false;
                        }

                        if (e != null) {
                            target.onFailed(e.getMessage());
                        } else {
                            target.onFailed("");
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource,
                                                Transition<? super Drawable> transition) {
                        if (target == null) {
                            return;
                        }

                        target.onResourceReady(resource);
                    }
                });
    }

    @Override
    public void into(ImageView iv, final ITarget<Drawable> target) {
        loadResourceAsDrawable()
                .apply(config.getRequestOptions())
                .transition(config.getTransitionOptions())
                .listener(new com.bumptech.glide.request.RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> t,
                                                boolean isFirstResource) {
                        if (target == null) {
                            return false;
                        }

                        if (e != null) {
                            target.onFailed(e.getMessage());
                        } else {
                            target.onFailed("");
                        }

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
    public void into(ImageView iv) {
        into(iv, null);
    }

    @Override
    public void clearMemory() {
        GlideApp.get(mContext).clearMemory();
    }

    @Override
    public void clearDisk() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideApp.get(mContext).clearDiskCache();
            }
        }).start();
    }
}
