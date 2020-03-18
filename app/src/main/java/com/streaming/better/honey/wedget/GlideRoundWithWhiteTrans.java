package com.streaming.better.honey.wedget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;


import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.streaming.better.honey.interfaces.ITransForm;

public class GlideRoundWithWhiteTrans extends ITransForm {

    private Context context;

    public GlideRoundWithWhiteTrans(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap round(BitmapPool pool, Bitmap source) {

        Bitmap bitmap=Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        //混合模式中的圆 DST
        paint.setColor(Color.BLACK);
        //半径取宽高中小的并/2
        canvas.drawCircle(source.getWidth()/2f, source.getHeight()/2f,
                source.getWidth()/2f < source.getHeight()/2f?source.getWidth()/2f:source.getHeight()/2f,
                paint);
        //添加混合模式给paint，矩阵，切圆，方形和圆形，选择圆形重叠的部分
        //PorterDuffXfermode：该方法及是
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //再把原始头像，画到bitmap上
        canvas.drawBitmap(source, 0, 0, paint);
        /**画个白边*/
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);//设置一个模式
        //进行单位变换
        float strokeWidth =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,context
                        .getResources().getDisplayMetrics());
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(source.getWidth()/2f, source.getHeight()/2f,
                source.getWidth()/2f < source.getHeight()/2f ? source.getWidth()/2f:source.getHeight()/2f
                        -strokeWidth/2, paint);
        return bitmap;
    }
}
