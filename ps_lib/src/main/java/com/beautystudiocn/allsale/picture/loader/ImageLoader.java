package com.beautystudiocn.allsale.picture.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.beautystudiocn.allsale.picture.compress.BitmapUpLoader;
import com.beautystudiocn.allsale.picture.compress.IBitmapCompressCallback;
import com.beautystudiocn.allsale.picture.strategy.IImageLoaderStrategy;
import com.beautystudiocn.allsale.picture.target.ITarget;
import com.beautystudiocn.allsale.picture.compress.BitmapUpLoader;
import com.beautystudiocn.allsale.picture.compress.IBitmapCompressCallback;
import com.beautystudiocn.allsale.picture.scene.IConfigScene;
import com.beautystudiocn.allsale.picture.strategy.IImageLoaderStrategy;
import com.beautystudiocn.allsale.picture.target.ITarget;

/**
 * <br> ClassName:   ImageLoader
 * <br> Description: 图片框架基类
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/8/1 17:38
 * @deprecated      不再维护
 * @see              Monet
 */
public class ImageLoader<T> {
    /*图片配置信息*/
    private T mInfo;
    /*图片加载策略*/
    private IImageLoaderStrategy mImageLoader;
    /*上下文*/
    private Context mContext;

    /**
     *<br> Description: 图片框架构造方法
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:39
     * @param context
     *                  上下文
     * @param strategy
     *                  图片加载策略
     * @param info
     *                  图片配置信息
     */
    protected ImageLoader(Context context, IImageLoaderStrategy strategy, T info) {
        mImageLoader = strategy;
        mInfo = info;
        mContext = context;
    }

    /**
     *<br> Description: 缓存清理
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:40
     */
    public void clearMemory(){
        mImageLoader.clearMemory();
    }

    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:41
     * @param resourceID
     *                  本地资源ID
     * @return
     *                  配置器
     */
    public ImageBuilder<T> resource(int resourceID) {
        mImageLoader.resource(resourceID);
        return new ImageBuilder(this);
    }

    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:41
     * @param url
     *                  网络URL地址，本地文件地址等
     * @return
     *                  配置器
     */
    public ImageBuilder<T> resource(String url) {
        mImageLoader.resource(url);
        return new ImageBuilder(this);
    }

    /**
     *<br> Description: 设置加载资源
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:42
     * @param uri
     *                  本地资源uri
     * @return
     *                  配置器
     */
    public ImageBuilder<T> resource(Uri uri) {
        mImageLoader.resource(uri);
        return new ImageBuilder(this);
    }

    /**
     *<br> Description: 清除硬盘缓存
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:42
     */
    public void clearDisk() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mImageLoader.clearDisk();
            }
        }).start();
    }

    /**
     *<br> Description: 设置配置场景
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:43
     * @param scene
     *                  场景
     * @return
     *                  配置器
     */
    private ImageLoader scene(IConfigScene<T> scene) {
        scene.config(mInfo);
        return this;
    }

    /**
     *<br> Description: 图片压缩
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:48
     * @param uri
     *                  本地资源地址
     * @param call
     *                  压缩回调
     * @deprecated    功能不再维护，可使用BitmapUpLoader
     * @see            BitmapUpLoader
     */
    public void compress(Uri uri, IBitmapCompressCallback call) {
        compress(uri,call,0);
    }

    /**
     *<br> Description: 图片压缩
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:48
     * @param uri
     *                  本地资源地址
     * @param call
     *                  压缩回调
     * @param minWidth
     *                  最小压缩分辨率
     * @deprecated    功能不再维护，可使用BitmapUpLoader
     * @see            BitmapUpLoader
     */
    public void compress(Uri uri, IBitmapCompressCallback call, int minWidth) {
        compress(uri,call,minWidth,0);
    }

    /**
     *<br> Description: 图片压缩
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:48
     * @param uri
     *                  本地资源地址
     * @param call
     *                  压缩回调
     * @param minWidth
     *                  最小压缩分辨率
     * @param maxSize
     *                  最大压缩质量大小
     * @deprecated    功能不再维护，可使用BitmapUpLoader
     * @see            BitmapUpLoader
     */
    public void compress(Uri uri, IBitmapCompressCallback call, int minWidth, int maxSize) {
        compress(uri,call,minWidth,maxSize,maxSize);
    }

    /**
     *<br> Description: 图片压缩
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:48
     * @param uri
     *                  本地资源地址
     * @param call
     *                  压缩回调
     * @param minWidth
     *                  最小压缩分辨率
     * @param maxSizeInMobileNet
     *                  移动网络下，最大的压缩质量大小
     * @param maxSizeInWifiNet
     *                  wifi网络下，最大的压缩质量大小
     * @deprecated    功能不再维护，可使用BitmapUpLoader
     * @see            BitmapUpLoader
     */
    public void compress(Uri uri, IBitmapCompressCallback call, int minWidth, int maxSizeInMobileNet, int maxSizeInWifiNet) {
        BitmapUpLoader bitmapUploader = new BitmapUpLoader(mContext);
        bitmapUploader.setConfig(minWidth,maxSizeInMobileNet,maxSizeInWifiNet);
        bitmapUploader.compress(uri,call);
    }

    /**
     *<br> Description: 输出Bitmap
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:50
     * @param target
     *                  Bitmap回调接口
     */
    private void asBitmap(ITarget<Bitmap> target) {
        mImageLoader.asBitmap(mInfo,target);
    }

    /**
     *<br> Description: 输出Drawable
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:51
     * @param target
     *                  Drawable回调接口
     */
    private void asDrawable(ITarget<Drawable> target) {
        mImageLoader.asDrawable(mInfo,target);
    }

    /**
     *<br> Description: 控件加载
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:52
     * @param iv
     *                  ImageVIew加载
     */
    private void loadIn(ImageView iv){
        mImageLoader.loadIn(iv,mInfo,null);
    }

    /**
     *<br> Description: 控件加载
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:52
     * @param iv
     *                  ImageVIew加载
     * @param target
     *                  Drawable回调接口
     */
    private void loadIn(ImageView iv, ITarget<Drawable> target) {
        mImageLoader.loadIn(iv,mInfo,target);
    }

    /**
     * <br> ClassName:   ImageLoader
     * <br> Description: 图片加载配置器
     * <br>
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/1 17:53
     */
    public static class ImageBuilder<T>{
        private ImageLoader mLoader;

        ImageBuilder(ImageLoader loader) {
            mLoader = loader;
        }

        /**
         *<br> Description: 设置配置场景
         *<br> Author:      yexiaochuan
         *<br> Date:        2017/8/1 17:43
         * @param scene
         *                  场景
         * @return
         *                  配置器
         */
        public ImageBuilder scene(IConfigScene<T> scene) {
            mLoader.scene(scene);
            return this;
        }

        /**
         *<br> Description: 输出Bitmap
         *<br> Author:      yexiaochuan
         *<br> Date:        2017/8/1 17:50
         * @param target
         *                  Bitmap回调接口
         */
        public void asBitmap(ITarget<Bitmap> target) {
            mLoader.asBitmap(target);
        }

        /**
         *<br> Description: 输出Drawable
         *<br> Author:      yexiaochuan
         *<br> Date:        2017/8/1 17:51
         * @param target
         *                  Drawable回调接口
         */
        public void asDrawable(ITarget<Drawable> target) {
            mLoader.asDrawable(target);
        }

        /**
         *<br> Description: 控件加载
         *<br> Author:      yexiaochuan
         *<br> Date:        2017/8/1 17:52
         * @param iv
         *                  ImageVIew加载
         */
        public void loadIn(ImageView iv){
            mLoader.loadIn(iv);
        }

        /**
         *<br> Description: 控件加载
         *<br> Author:      yexiaochuan
         *<br> Date:        2017/8/1 17:52
         * @param iv
         *                  ImageVIew加载
         * @param target
         *                  Drawable回调接口
         */
        public void loadIn(ImageView iv, ITarget<Drawable> target) {
            mLoader.loadIn(iv,target);
        }
    }
}
