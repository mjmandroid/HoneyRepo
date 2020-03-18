package com.streaming.better.honey.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public abstract class ITransForm extends BitmapTransformation {


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return round(pool, toTransform);
    }

    protected abstract Bitmap round(BitmapPool pool, Bitmap source);

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}