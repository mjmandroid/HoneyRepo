package com.streaming.better.honey.wedget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;


import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.streaming.better.honey.interfaces.ITransForm;

/**
 * 设置圆形图片
 */

public class GlideCircleTrans extends ITransForm {

    private float radius;   //半径

    public GlideCircleTrans(Context context) {
    }

    public GlideCircleTrans(Context context,float radius) {
        this.radius = radius;
    }
    @Override
    protected Bitmap round(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        radius = size / 2f;
        canvas.drawCircle(radius, radius, radius, paint);
        return result;
    }
}
