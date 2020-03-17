package com.beautystudiocn.allsale.widget.guideview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.util.UIUtil;
import com.beautystudiocn.allsale.util.UIUtil;

/**
 * <br> ClassName:   GuideItemView
 * <br> Description: 引导子视图
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/4/23 11:21
 */
public class PSGuideImageView extends android.support.v7.widget.AppCompatImageView implements IPSGuideShowState {
    /*** 当前bitmap ***/
    private Bitmap mCurrentBitmap;
    /*** 边框渲染色 ***/
    private Bitmap mOutShadowBitmap;
    /*** 背景渲染色 ***/
    private Bitmap mInShadowBitmap;
    /*** 画笔 ***/
    private Paint mPaint = null;
    private PSGuideConfig mConfing = null;

    public PSGuideImageView(@NonNull Context context) {
        super(context);
        initView();
    }

    public PSGuideImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PSGuideImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /**
     * <br> Description: 初始化视图
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/23 11:22
     */
    private void initView() {
        mPaint = new Paint();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm == null) {
            return;
        }
        this.mCurrentBitmap = bm;
        mInShadowBitmap = mCurrentBitmap.extractAlpha();
        try {
            updateBitmapColor(mInShadowBitmap, Color.WHITE);  // 去半透明状态
        } catch (Exception e) {
            LoggerManager.e(e.getMessage());
        }
        mOutShadowBitmap = mInShadowBitmap.extractAlpha();
    }

    public PSGuideConfig getConfing() {
        return mConfing;
    }

    public void setConfing(PSGuideConfig mConfing) {
        this.mConfing = mConfing;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawPic(canvas);
    }

    /**
     * <br> Description: 绘制图片
     * <br> Author:      wuheng
     * <br> Date:        2017/10/9 14:00
     */
    private void onDrawPic(Canvas canvas) {
        if (mCurrentBitmap == null) {
            return;
        }
        int left = (this.getWidth() - mCurrentBitmap.getWidth()) / 2;
        int top = (this.getHeight() - mCurrentBitmap.getHeight()) / 2;

        if (mOutShadowBitmap != null && getConfing().isHighLightOn()) {
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setColor(getConfing().getHighLightColor());
            mPaint.setMaskFilter(new BlurMaskFilter(UIUtil.dp2px(getConfing().getHighLightWidth()), BlurMaskFilter.Blur.OUTER));
            //  ViewGroup 初始化时设置相应属性      setLayerType(View.LAYER_TYPE_SOFTWARE, null);    setClipChildren(false);
            // 先绘制阴影
            canvas.drawBitmap(mOutShadowBitmap, left, top, mPaint);
        }
        /*** 设置背景色 ***/
        if (mInShadowBitmap != null && getConfing().getTargetViewBackgroundColor() != Color.TRANSPARENT) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAntiAlias(true);  // 这种方式抗据齿无效
            paint.setFilterBitmap(true);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setMaskFilter(new BlurMaskFilter(2, BlurMaskFilter.Blur.SOLID));
            paint.setColor(getConfing().getTargetViewBackgroundColor());
            canvas.drawBitmap(mInShadowBitmap, left, top, paint);
        }
        canvas.drawBitmap(mCurrentBitmap, left, top, null);
    }

    /**
     * <br> Description: 修改bitmap中的颜色值
     * <br> Author:      KevinWu
     * <br> Date:        2018/6/25 14:48
     */
    private void updateBitmapColor(Bitmap bitmap, int color) {
        if (bitmap == null) {
            return;
        }
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];//保存所有的像素的数组，图片宽×高
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int colortmp = pixels[i];
            int alpha = Color.alpha(colortmp);  // 颜色的透明度
            if (alpha != 0) {
                pixels[i] = color;
            }
        }
        bitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public void destory() {
        if (mCurrentBitmap != null) {
            mCurrentBitmap.recycle();
        }
        if (mOutShadowBitmap != null) {
            mOutShadowBitmap.recycle();
        }
        if (mInShadowBitmap != null) {
            mInShadowBitmap.recycle();
        }
    }

    @Override
    public void refreshView() {
        invalidate();
    }
}
