package com.beautystudiocn.allsale.widget.gestureview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * <br> ClassName:   PSGesturePointView
 * <br> Description:  用于显示的点
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/9/30 10:47
 */
@SuppressLint("AppCompatCustomView")
public class PSGesturePointView extends ImageView implements IPSGestureStatus {
    private Paint mPaint;
    private PSRenderStyle mRenderStyle = PSRenderStyle.NORMAL;
    /***参数配置对象***/
    private PSGesturePointConfig mPointItem = null;
    private TextPaint mTextPaint;
    private String drawTxt = "NORMARL绘制";

    private Bitmap currentBitmap;
    /***是否采用图片***/
    private boolean isPicturePoint;

    /***是否绘制文字***/
    private boolean isDrawText = true;
    /***点的中心在屏幕上的位置***/
    private PSPoint mCenterPoint = null;

    /***环形上的点***/
    private List<PSPoint> mCircularPoints = null;
    /*** 里面渲染 ***/
    private boolean isShowInnerRender = false;
    /*** 圆外渲染 ***/
    private boolean isShowOuterRender = true;
    /*** 绘制边框 ***/
    private boolean isShowOuterStroke = true;
    /*** 绘制小圆 ***/
    private boolean isShowCenterSmallCircle = true;

    @Override
    public void showGestureNormalStatus() {
        if (!isPicturePoint) { // 为手动绘制时
            PSGesturePointView.PSRenderStyle style = PSRenderStyle.CUSTOM;
            setRenderStyle(style);
            isShowInnerRender = getPointItemConfig().isShowInnerRenderNormal();
            isShowOuterRender = getPointItemConfig().isShowOuterRenderNormal();
            isShowOuterStroke = getPointItemConfig().isShowOuterStrokeNormal();
            isShowCenterSmallCircle = getPointItemConfig().isShowCenterSmallCircleNormal();
        }
        setDrawColor(getPointItemConfig().getNormalColor());
        refreshView();
    }

    @Override
    public void showGesturePressedStatus() {
        if (!isPicturePoint) {// 为手动绘制时
            PSGesturePointView.PSRenderStyle style = PSRenderStyle.CUSTOM;
            setRenderStyle(style);
            isShowInnerRender = getPointItemConfig().isShowInnerRenderPressed();
            isShowOuterRender = getPointItemConfig().isShowOuterRenderPressed();
            isShowOuterStroke = getPointItemConfig().isShowOuterStrokePressed();
            isShowCenterSmallCircle = getPointItemConfig().isShowCenterSmallCirclePressed();
        }
        setDrawColor(getPointItemConfig().getPressColor());
        refreshView();
    }

    @Override
    public void showGestureErrorStatus() {
        if (!isPicturePoint) {// 为手动绘制时
            PSGesturePointView.PSRenderStyle style = PSRenderStyle.CUSTOM;
            setRenderStyle(style);
            isShowInnerRender = getPointItemConfig().isShowInnerRenderError();
            isShowOuterRender = getPointItemConfig().isShowOuterRenderError();
            isShowOuterStroke = getPointItemConfig().isShowOuterStrokeError();
            isShowCenterSmallCircle = getPointItemConfig().isShowCenterSmallCircleError();
        }
        setDrawColor(getPointItemConfig().getErrorColor());
        refreshView();
    }

    /**
     * 设置渲染的图片
     *
     * @param icon
     */
    public void setRenderPic(int icon) {
        setRenderPic(BitmapFactory.decodeResource(getContext().getResources(), icon));
    }

    /**
     * 通过drawable 初始化当前的bitmap
     *
     * @param drawable
     */
    public void setRenderPic(Drawable drawable) {
        setRenderPic(((BitmapDrawable) drawable).getBitmap());
    }

    /**
     * <br> Description: 初始化本地bitmap
     * <br> Author:      wuheng
     * <br> Date:        2017/10/9 14:32
     */
    private void setRenderPic(Bitmap tmpBitmap) {
        if (currentBitmap != null) currentBitmap.recycle();
        this.currentBitmap = tmpBitmap;
        isPicturePoint = true;
    }

    /**
     * 初始化 bitmap
     *
     * @param bitmap
     */
    private void setRenderBitmap(Bitmap bitmap) {
        int tmp = getWidth() > getHeight() ? getHeight() : getWidth();
        currentBitmap = Bitmap.createScaledBitmap(bitmap, tmp,
                tmp, true);
//        currentBitmap = currentBitmap.extractAlpha();
    }

    public void setDrawText(boolean drawText) {
        isDrawText = drawText;
    }

    /**
     * <br> ClassName:   PSGesturePointView
     * <br> Description:  渲染的样式
     * <br>
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 11:14
     */
    public enum PSRenderStyle {
        INNER,
        NORMAL,
        OUTER,
        SOLID,
        OUTER_STROKE,
        INNER_STROKE,
        CUSTOM;

        private PSRenderStyle() {
        }
    }


    public PSGesturePointView(Context context) {
        super(context);
        init();
    }

    public PSGesturePointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PSGesturePointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCenterPoint = new PSPoint();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(40);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mCircularPoints = new LinkedList<>();
    }

    /***计算视图中心点相对屏幕的位置***/
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
            mCenterPoint.set(getMeasuredWidth() / 2 + getLeft(), getMeasuredHeight() / 2 + getTop());
//            countCircularPoints(mCenterPoint);
//            LoggerManager.d("zll", "PSGesturePointView  :　" + mCenterPoint.toString());
        }
    };

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        super.setLayoutParams(params);
    }

    /**
     * <br> Description: 控件的中心位置
     * <br> Author:      wuheng
     * <br> Date:        2017/10/10 14:10
     */
    public PSPoint getCenterPoint() {
        return mCenterPoint;
    }

    /**
     * <br> Description: 释放资源
     * <br> Author:      wuheng
     * <br> Date:        2017/10/9 15:39
     */
    public void onDestory() {
        if (currentBitmap != null) {
            currentBitmap.recycle();
        }
    }

    /**
     * <br> Description: PointView配置文件
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 16:47
     */
    public PSGesturePointConfig getPointItemConfig() {
        if (mPointItem == null) {
            mPointItem = new PSGesturePointConfig();
        }
        return mPointItem;
    }

    /**
     * <br> Description: 配置绘制的属性
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 14:28
     *
     * @param mPointItem
     */
    public void setPointConfig(PSGesturePointConfig mPointItem) {
        if (mPointItem == null) {
            return;
        }
        if (this.mPointItem != null) {
            this.mPointItem = null;
        }
        this.mPointItem = mPointItem;
    }

    /**
     * <br> Description: 绘制的颜色
     * <br> Author:      wuheng
     * <br> Date:        2017/10/9 10:36
     */
    public void setDrawColor(int color) {
        this.mPaint.setColor(color);
        this.mTextPaint.setColor(color);
        if (isPicturePoint) {
            currentBitmap = currentBitmap.extractAlpha();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        if (isPicturePoint) {
            //绘制图片
            onDrawPic(canvas);
        } else {
            //手动绘制
            onDrawCirclePoint(canvas);
        }
        if (isDrawText) {
            canvas.drawText(drawTxt,
                    getWidth() + getPointItemConfig().getOuterCircleWidth(),
                    getHeight() + getPointItemConfig().getOuterCircleWidth(),
                    mTextPaint);
        }
    }

    /**
     * <br> Description: 手动绘制
     * <br> Author:      wuheng
     * <br> Date:        2017/10/9 13:59
     */
    private void onDrawCirclePoint(Canvas canvas) {
        switch (mRenderStyle) {
            case INNER:
                this.drawTxt = "INNER绘制";
                setRenderInner(canvas);
                break;
            case NORMAL:
                this.drawTxt = "Normal绘制";
                setRenderNormal(canvas);
                break;
            case OUTER:
                this.drawTxt = "OUTER绘制";
                setRenderOuter(canvas);
                break;
            case SOLID:
                this.drawTxt = "SOLID绘制";
                setRenderSolid(canvas);
                break;
            case OUTER_STROKE:
                drawTxt = "OuterAndStroke绘制";
                drawCircleOuterAndStroke(canvas, getPointItemConfig().getPressColor());
                break;
            case INNER_STROKE:
                drawTxt = "InnerAndStroke绘制";
                drawCircleInnerAndStroke(canvas, getPointItemConfig().getPressColor());
                break;
            case CUSTOM:
                drawTxt = "自定义";
                drawCustomCircle(canvas);
                break;
            default:
                break;
        }
    }

    /**
     * <br> Description: 绘制图片
     * <br> Author:      wuheng
     * <br> Date:        2017/10/9 14:00
     */
    private void onDrawPic(Canvas canvas) {
        this.drawTxt = "图片绘制";
        setRenderBitmap(currentBitmap);
        int left = (this.getWidth() - currentBitmap.getWidth()) / 2;
        int top = (this.getHeight() - currentBitmap.getHeight()) / 2;
        canvas.drawBitmap(currentBitmap, left, top, mPaint);
    }

    /**
     * <br> Description: 刷新界面
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 11:47
     */
    public void refreshView() {
        invalidate();
    }

    /**
     * <br> Description: 配置渲染的样式
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 11:18
     */
    public void setRenderStyle(PSRenderStyle render) {
        this.mRenderStyle = render;
    }

    /**
     * <br> Description: 默认绘制
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 11:49
     */
    private void setRenderNormal(Canvas canvas) {
        drawRenderBaseCircle(canvas, mPaint,
                new BlurMaskFilter(getPointItemConfig().getOuterCircleWidth(), BlurMaskFilter.Blur.NORMAL));
    }

    /**
     * <br> Description: SOLID绘制
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 11:49
     */
    private void setRenderSolid(Canvas canvas) {
        drawRenderBaseCircle(canvas, mPaint,
                new BlurMaskFilter(getPointItemConfig().getOuterCircleWidth(), BlurMaskFilter.Blur.SOLID));
    }

    /**
     * <br> Description: outer绘制
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 11:49
     */
    private void setRenderOuter(Canvas canvas) {
        drawRenderBaseCircle(canvas, mPaint,
                new BlurMaskFilter(getPointItemConfig().getOuterCircleWidth(), BlurMaskFilter.Blur.OUTER));
    }

    /**
     * <br> Description: INNER绘制
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 11:49
     */
    private void setRenderInner(Canvas canvas) {
        drawRenderBaseCircle(canvas, mPaint,
                new BlurMaskFilter(getPointItemConfig().getOuterCircleWidth(), BlurMaskFilter.Blur.INNER));
    }

    private void drawRenderBaseCircle(Canvas canvas, Paint paint, BlurMaskFilter blurMaskFilter) {
        if (blurMaskFilter != null) {
            paint.setMaskFilter(blurMaskFilter);
        }
//        KLog.d("zll", "drawRenderBaseCircle getwidth :　　" + getWidth() + "   getHeight  " + getHeight());
        Point centerP = new Point(getWidth() / 2, getHeight() / 2);
        int radius;
        if (getWidth() > getHeight()) {
            radius = getHeight() / 2;
        } else {
            radius = getWidth() / 2;
        }
        canvas.drawCircle(centerP.x, centerP.y, radius, paint);
    }

    /**
     * <br> Description: 绘制小圆
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 17:05
     */
    private void drawRenderSmallCircle(Canvas canvas, Paint paint, BlurMaskFilter blurMaskFilter) {
        if (blurMaskFilter != null) {
            paint.setMaskFilter(blurMaskFilter);
        }
//        KLog.d("zll", "getwidth :　　" + getWidth() + "   getHeight  " + getHeight());
        Point centerP = new Point(getWidth() / 2, getHeight() / 2);
        int radius;
        if (getWidth() > getHeight()) {
            radius = getHeight() / 8;
        } else {
            radius = getWidth() / 8;
        }
        canvas.drawCircle(centerP.x, centerP.y, radius, paint);

        if (mCircularPoints.isEmpty()) return;
        for (PSPoint point : mCircularPoints) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(point.getX(), point.getY(), 2, paint);
        }
    }

    /**
     * <br> Description: 绘制圆外部渲染
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 16:03
     */
    public void drawCircleOuterAndStroke(Canvas canvas, int color) {
        setRenderOuter(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(getPointItemConfig().getStrokeWidth());
        drawRenderBaseCircle(canvas, paint, null);

        paint.setStyle(Paint.Style.FILL);
        this.drawRenderSmallCircle(canvas, paint, null);
    }

    /**
     * <br> Description: 绘制圆内部渲染
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 16:03
     */
    private void drawCircleInnerAndStroke(Canvas canvas, int color) {
        //透明度为20%
        int halfColor = Color.argb((int) (Color.alpha(color) * 0.2f),
                Color.red(color), Color.green(color), Color.blue(color));
        Paint paint = new Paint();
        paint.setColor(halfColor);
        drawFillCircle(canvas, paint);

        //绘制边框
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(getPointItemConfig().getStrokeWidth());
        drawRenderBaseCircle(canvas, paint, null);

        paint.setStyle(Paint.Style.FILL);
        this.drawRenderSmallCircle(canvas, paint, null);
    }

    private void drawFillCircle(Canvas canvas, Paint paint) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        drawRenderBaseCircle(canvas, paint, null);
    }

    /**
     * <br> Description: 自定义
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 18:05
     */
    private void drawCustomCircle(Canvas canvas) {
        PSGesturePointConfig pointItem = getPointItemConfig();
        Paint paint = new Paint();
        int color = mPaint.getColor();
        if (isShowInnerRender) { // 绘制圆，内部渲染

            //透明度为20%
            int halfColor = Color.argb((int) (Color.alpha(color) * 0.2f),
                    Color.red(color), Color.green(color), Color.blue(color));
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(halfColor);
            drawFillCircle(canvas, paint);
        }
        if (isShowOuterRender) { // 外渲边框常渲染
            setRenderOuter(canvas);
        }
        if (isShowOuterStroke) {  // 绘制边框
            //绘制边框
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(color);
            paint.setStrokeWidth(getPointItemConfig().getStrokeWidth());
            drawRenderBaseCircle(canvas, paint, null);
        }
        if (isShowCenterSmallCircle) { //绘制内面小圆
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            this.drawRenderSmallCircle(canvas, paint, null);
        }
    }

    /**
     * <br> Description: 判断点是否在pointView圆内
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 11:24
     */
    public static boolean isInCircle(PSPoint psPoint, PSGesturePointView pointView) {
        PSPoint centerP = new PSPoint();
        centerP.set(pointView.getMeasuredWidth() / 2 + pointView.getLeft(),
                pointView.getMeasuredHeight() / 2 + pointView.getTop());
        int radius;
        if (pointView.getWidth() > pointView.getHeight()) {
            radius = pointView.getHeight() / 2;
        } else {
            radius = pointView.getWidth() / 2;
        }
        //半径是R  如O(x,y)点圆心，任意一点P（x1,y1）
        // （x-x1）*(x-x1)+(y-y1)*(y-y1)>R*R 那么在圆外 反之在圆内
        int xx = (int) (Math.abs(centerP.getX() - psPoint.getX()) * Math.abs(centerP.getX() - psPoint.getX()));
        int yy = (int) (Math.abs(centerP.getY() - psPoint.getY()) * Math.abs(centerP.getY() - psPoint.getY()));

        return (xx + yy) > radius * radius ? false : true;
    }

    /**
     * <br> Description: 判断点是否小圆内
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 11:47
     */
    public static boolean isInSmallCircle(PSPoint psPoint, PSGesturePointView pointView) {
        PSPoint centerP = new PSPoint();
        centerP.set(pointView.getMeasuredWidth() / 2 + pointView.getLeft(),
                pointView.getMeasuredHeight() / 2 + pointView.getTop());
        int radius;
        if (pointView.getWidth() > pointView.getHeight()) {
            radius = pointView.getHeight() / 2;  // 比例调整到3，扩大圆圈的范围
        } else {
            radius = pointView.getWidth() / 2;
        }
        //半径是R  如O(x,y)点圆心，任意一点P（x1,y1）
        // （x-x1）*(x-x1)+(y-y1)*(y-y1)>R*R 那么在圆外 反之在圆内
        int xx = (Math.abs(centerP.getX() - psPoint.getX()) * Math.abs(centerP.getX() - psPoint.getX()));
        int yy = (Math.abs(centerP.getY() - psPoint.getY()) * Math.abs(centerP.getY() - psPoint.getY()));

        return (xx + yy) > radius * radius ? false : true;
    }
}
