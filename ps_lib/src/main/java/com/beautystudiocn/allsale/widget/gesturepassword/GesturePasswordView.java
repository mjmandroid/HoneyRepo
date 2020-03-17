package com.beautystudiocn.allsale.widget.gesturepassword;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.widget.gesturepassword.utils.BitmapUtil;
import com.beautystudiocn.allsale.widget.gesturepassword.utils.MathUtil;
import com.beautystudiocn.allsale.widget.gesturepassword.utils.RoundUtil;
import com.beautystudiocn.allsale.widget.gesturepassword.utils.BitmapUtil;
import com.beautystudiocn.allsale.widget.gesturepassword.utils.MathUtil;
import com.beautystudiocn.allsale.widget.gesturepassword.utils.RoundUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 九宫格手势密码解锁
 *
 * @author way
 */
public class GesturePasswordView
        extends View {

    private       boolean     isCache         = false;
    private final Paint mPaint          = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Point[][]   mPoints         = new Point[3][3];
    private final Point       mMoveingPoint   = new Point(-1, 0, 0);
    // 圆的半径
    private       float       r               = 0;
    // 选中的点
    private final List<Point> mSelectedPoints = new ArrayList<>(9);
    private       boolean     checking        = false;

    private Bitmap locus_round_original;// 圆点初始状态时的图片
    private Bitmap locus_round_click;// 圆点点击时的图片
    private Bitmap locus_round_click_error;// 出错时圆点的图片
    private Bitmap locus_line;// 正常状态下线的图片
    private Bitmap locus_line_semicircle;
    private Bitmap locus_line_semicircle_error;
    private Bitmap locus_line_error;// 错误状态下的线的图片

    private static final long      CLEAR_TIME = 0;// 清除痕迹的时间
    private final Matrix mMatrix    = new Matrix();
    private              int       lineAlpha  = 50;// 连线的透明度
    private final Timer timer      = new Timer();
    private TimerTask task       = null;

    private boolean movingNoPoint;
    private float   moveingX;
    private float   moveingY;
    /** 是否显示轨迹 */
    private boolean isShowTrack;
    /***检测是否是分屏导致的崩溃***/
    private IMultiWindowExceptionInject mIExceptionInject;

    public GesturePasswordView(Context context) {
        this(context, null);
    }

    public GesturePasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GesturePasswordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GesturePasswordView);
        int drawablePointOriginal = typedArray.getResourceId(R.styleable.GesturePasswordView_drawablePointOriginal, R.drawable.gesture_point_original);
        int drawablePointClick = typedArray.getResourceId(R.styleable.GesturePasswordView_drawablePointClick, R.drawable.gesture_point_click);
        int drawablePointError = typedArray.getResourceId(R.styleable.GesturePasswordView_drawablePointError, R.drawable.gesture_point_click_error);
        int drawableLine = typedArray.getResourceId(R.styleable.GesturePasswordView_drawableLine, R.drawable.gesture_line);
        int drawableLineSemicircle = typedArray.getResourceId(R.styleable.GesturePasswordView_drawableLineSemicircle, R.drawable.gesture_line_semicircle);
        int drawableLineErroe = typedArray.getResourceId(R.styleable.GesturePasswordView_drawableLineErroe, R.drawable.gesture_line_error);
        int drawableLineErroeSemicircle = typedArray.getResourceId(R.styleable.GesturePasswordView_drawableLineErroeSemicircle, R.drawable.gesture_line_semicircle_error);
        isShowTrack = typedArray.getBoolean(R.styleable.GesturePasswordView_isShowTrack, false);
        typedArray.recycle();
        //加载资源
        locus_round_original = BitmapFactory.decodeResource(this.getResources(), drawablePointOriginal);
        locus_round_click = BitmapFactory.decodeResource(this.getResources(), drawablePointClick);
        locus_round_click_error = BitmapFactory.decodeResource(this.getResources(), drawablePointError);
        locus_line = BitmapFactory.decodeResource(this.getResources(), drawableLine);
        locus_line_semicircle = BitmapFactory.decodeResource(this.getResources(), drawableLineSemicircle);
        locus_line_error = BitmapFactory.decodeResource(this.getResources(), drawableLineErroe);
        locus_line_semicircle_error = BitmapFactory.decodeResource(this.getResources(), drawableLineErroeSemicircle);
    }

    /** 初始化Cache信息 *//**
     *<br> Description: todo(这里用一句话描述这个方法的作用)
     *<br> Author:      yangyinglong
     *<br> Date:        2018/3/23 15:08
     * @return 是否初始化成功，没有初始化成功过下次onDraw需要重新初始化,true:成功，false：失败
     */
    private boolean initCache() {
        float w = this.getWidth();
        float h = this.getHeight();
        if (w == 0 || h == 0) {
            //这里return，防止生成Bitmap时，传入0，导致抛出IllegalArgumentException("width and height must be > 0")异常
            return false;
        }
        float x = 0;
        float y = 0;
        // 以最小的为准
        // 纵屏
        if (w > h) {
            x = (w - h) / 8 * 5;
            w = h;
        } else {
            // 横屏
            y = (h - w) / 8 * 5;
            h = w;
        }

        // 计算圆圈图片的大小
        float canvasMinW = w > h ? h : w;
        float roundMinW = canvasMinW / 8.0f * 2;
        float roundW = roundMinW / 2.f;

        if (locus_round_original.getWidth() > roundMinW) {
            float sf = roundMinW * 1.0f / locus_round_original.getWidth(); // 取得缩放比例，将所有的图片进行缩放
            try {
                locus_round_original = BitmapUtil.zoom(locus_round_original, sf);
                locus_round_click = BitmapUtil.zoom(locus_round_click, sf);
                locus_round_click_error = BitmapUtil.zoom(locus_round_click_error, sf);
                locus_line = BitmapUtil.zoom(locus_line, sf);
                locus_line_semicircle = BitmapUtil.zoom(locus_line_semicircle, sf);

                locus_line_error = BitmapUtil.zoom(locus_line_error, sf);
                locus_line_semicircle_error = BitmapUtil.zoom(locus_line_semicircle_error, sf);
                roundW = locus_round_original.getWidth() / 2;
            } catch (IllegalArgumentException e) {
                if (null != mIExceptionInject) {
                    //是否分屏
                    boolean isMultiWindow = mIExceptionInject.multiWindowInject();
                    throw new IllegalArgumentException(e.getMessage() + " and status:"
                            + "width:" + getWidth() + " height:" + getHeight() + " sf:" + sf
                            + " isMultiWindow:" + isMultiWindow);
                } else {
                    throw new IllegalArgumentException(e.getMessage() + " and status:"
                            + "width:" + getWidth() + " height:" + getHeight() + " sf:" + sf);
                }
            }
        }

        mPoints[0][0] = new Point(0, x + 0 + roundW, y + 0 + roundW);
        mPoints[0][1] = new Point(1, x + w / 2, y + 0 + roundW);
        mPoints[0][2] = new Point(2, x + w - roundW, y + 0 + roundW);
        mPoints[1][0] = new Point(3, x + 0 + roundW, y + h / 2);
        mPoints[1][1] = new Point(4, x + w / 2, y + h / 2);
        mPoints[1][2] = new Point(5, x + w - roundW, y + h / 2);
        mPoints[2][0] = new Point(6, x + 0 + roundW, y + h - roundW);
        mPoints[2][1] = new Point(7, x + w / 2, y + h - roundW);
        mPoints[2][2] = new Point(8, x + w - roundW, y + h - roundW);
        r = locus_round_original.getHeight() / 2;// roundW;
        isCache = true;
        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (!isCache) {
            //如果view的宽/高为0，则不绘制点
            if (!initCache()){
                super.onDraw(canvas);
                return;
            }
        }
        // 画所有点
        for (Point[] mPoint : mPoints) {
            for (Point p : mPoint) {
                if (p.mState == Point.STATE_CHECK) {
                    if (isShowTrack) {
                        canvas.drawBitmap(locus_round_click, p.mX - r, p.mY - r, mPaint);
                    } else {
                        canvas.drawBitmap(locus_round_original, p.mX - r, p.mY - r, mPaint);
                    }
                } else if (p.mState == Point.STATE_CHECK_ERROR) {
                    canvas.drawBitmap(locus_round_click_error, p.mX - r, p.mY - r, mPaint);
                } else {
                    canvas.drawBitmap(locus_round_original, p.mX - r, p.mY - r, mPaint);
                }
            }
        }
        // 画连线
        if (isShowTrack) {
            if (mSelectedPoints.size() > 0) {
                int tmpAlpha = mPaint.getAlpha();
                mPaint.setAlpha(lineAlpha);
                Point tp = mSelectedPoints.get(0);
                for (int i = 1; i < mSelectedPoints.size(); i++) {
                    Point p = mSelectedPoints.get(i);
                    drawLine(canvas, tp, p);
                    tp = p;
                }
                if (this.movingNoPoint) {
                    mMoveingPoint.update(moveingX, moveingY);
                    drawLine(canvas, tp, mMoveingPoint);
                }
                mPaint.setAlpha(tmpAlpha);
                lineAlpha = mPaint.getAlpha();
            }
        }
    }

    /**
     * 画两点的连接
     *
     * @param canvas
     * @param a
     * @param b
     */
    private void drawLine(Canvas canvas, Point a, Point b) {
        canvas.save();
        float ah = (float) MathUtil.distance(a.mX, a.mY, b.mX, b.mY);
        float degrees = getDegrees(a, b);
        canvas.rotate(degrees, a.mX, a.mY);
        if (a.mState == Point.STATE_CHECK_ERROR) {
            mMatrix.setScale(ah / locus_line_error.getWidth(), 1);
            mMatrix.postTranslate(a.mX, a.mY - locus_line_error.getHeight() / 2.0f);
            canvas.drawBitmap(locus_line_error, mMatrix, mPaint);
            //TODO 这里应该是画箭头，但图片和计算有误，会错位
            /*canvas.drawBitmap(gesture_line_semicircle_error,
                    a.mX + ah,
                    a.mY - gesture_line_error.getHeight() / 2.0f,
                    mPaint);*/
        } else {
            mMatrix.setScale(ah / locus_line.getWidth(), 1);
            mMatrix.postTranslate(a.mX, a.mY - locus_line.getHeight() / 2.0f);
            canvas.drawBitmap(locus_line, mMatrix, mPaint);
            //TODO 这里应该是画箭头，但图片和计算有误，会错位
            /*canvas.drawBitmap(gesture_line_semicircle,
                    a.mX + ah,
                    a.mY - gesture_line.getHeight() / 2.0f,
                    mPaint);*/
        }
        canvas.restore();
    }

    private float getDegrees(Point a, Point b) {
        float ax = a.mX;// a.mIndex % 3;
        float ay = a.mY;// a.mIndex / 3;
        float bx = b.mX;// b.mIndex % 3;
        float by = b.mY;// b.mIndex / 3;
        float degrees = 0;
        if (bx == ax) {
            // y轴相等 90度或270
            if (by > ay) {
                // 在y轴的下边 90
                degrees = 90;
            } else if (by < ay) {
                // 在y轴的上边 270
                degrees = 270;
            }
        } else if (by == ay) {
            // y轴相等 0度或180
            if (bx > ax) {
                // 在y轴的下边 90
                degrees = 0;
            } else if (bx < ax) {
                // 在y轴的上边 270
                degrees = 180;
            }
        } else {
            if (bx > ax) {
                // 在y轴的右边 270~90
                if (by > ay) {
                    // 在y轴的下边 0 - 90
                    degrees = 0;
                    degrees = degrees + switchDegrees(Math.abs(by - ay), Math.abs(bx - ax));
                } else if (by < ay) {
                    // 在y轴的上边 270~0
                    degrees = 360;
                    degrees = degrees - switchDegrees(Math.abs(by - ay), Math.abs(bx - ax));
                }
            } else if (bx < ax) {
                // 在y轴的左边 90~270
                if (by > ay) {
                    // 在y轴的下边 180 ~ 270
                    degrees = 90;
                    degrees = degrees + switchDegrees(Math.abs(bx - ax), Math.abs(by - ay));
                } else if (by < ay) {
                    // 在y轴的上边 90 ~ 180
                    degrees = 270;
                    degrees = degrees - switchDegrees(Math.abs(bx - ax), Math.abs(by - ay));
                }
            }
        }
        return degrees;
    }

    /** 1=30度 2=45度 4=60度 */
    private float switchDegrees(float y, float x) {
        return (float) MathUtil.pointTotoDegrees(y, x);
    }

    /**
     * 检查
     *
     * @param x
     * @param y
     *
     * @return
     */
    private Point checkSelectPoint(float x, float y) {
        for (Point[] mPoint : mPoints) {
            for (Point p : mPoint) {
                if (p != null && RoundUtil.checkInRound(p.mX, p.mY, r, (int) x, (int) y)) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * 重置图案
     */
    private void resetView() {
        for (Point p : mSelectedPoints) {
            p.mState = Point.STATE_NORMAL;
        }
        mSelectedPoints.clear();
    }

    /**
     * 判断点是否有交叉 返回 0,新点 ,1 与上一点重叠 2,与非最后一点重叠
     *
     * @param p
     *
     * @return
     */
    private int crossPoint(Point p) {
        // 重叠的不最后一个则 reset
        if (mSelectedPoints.contains(p)) {
            if (mSelectedPoints.size() > 2) {
                // 与非最后一点重叠
                if (mSelectedPoints.get(mSelectedPoints.size() - 1).mIndex != p.mIndex) {
                    return 2;
                }
            }
            return 1; // 与最后一点重叠
        } else {
            return 0; // 新点
        }
    }

    /**
     * 添加一个点
     *
     * @param point
     */
    private void addPoint(Point point) {
        this.mSelectedPoints.add(point);
    }

    /** 转换为String */
    private String toPointString() {
        StringBuilder builder = new StringBuilder();
        boolean flag = true;
        for (Point p : mSelectedPoints) {
            if (flag) {
                flag = false;
            }else{
                builder.append(",");
            }
            builder.append(p.mIndex);
        }
        return builder.toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        movingNoPoint = false;
        float ex = event.getX();
        float ey = event.getY();
        boolean isFinish = false;
        Point p = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 点下
                // 如果正在清除密码,则取消
                if (task != null) {
                    task.cancel();
                    task = null;
                    Log.d("task", "touch cancel()");
                }
                // 删除之前的点
                resetView();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                p = checkSelectPoint(ex, ey);
                if (checking) {
                    if (p == null) {
                        movingNoPoint = true;
                        moveingX = ex;
                        moveingY = ey;
                    }
                } else {
                    //ACTION_DOWN时没有选中点的情况，可以在移动时选择最近的点
                    if (p != null) {
                        checking = true;
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 提起
                p = checkSelectPoint(ex, ey);
                checking = false;
                isFinish = true;
                break;
        }
        if (!isFinish && checking && p != null) {

            int rk = crossPoint(p);
            if (rk == 2) {
                // 与非最后一重叠
                movingNoPoint = true;
                moveingX = ex;
                moveingY = ey;
            } else if (rk == 0) {
                // 一个新点
                p.mState = Point.STATE_CHECK;
                addPoint(p);
            }
            // rk == 1 不处理

        }
        if (isFinish) {
            if (this.mSelectedPoints.size() <= 1) {
                this.resetView();
            } else if (mCompleteListener != null) {
                mCompleteListener.onComplete(mSelectedPoints.size(), toPointString());
            }
        }
        this.postInvalidate();
        return true;
    }

    /**
     * 设置为输入错误
     */
    public void markError(final long time) {
        for (Point p : mSelectedPoints) {
            p.mState = Point.STATE_CHECK_ERROR;
        }
        this.clearPassword(time);
    }

    /**
     * 清除密码
     */
    public void clearPassword() {
        clearPassword(CLEAR_TIME);
    }

    /**
     * 清除密码
     */
    public void clearPassword(final long time) {
        if (time > 1) {
            if (task != null) {
                task.cancel();
                Log.d("task", "clearPassword cancel()");
            }
            lineAlpha = 130;
            postInvalidate();
            task = new TimerTask() {
                public void run() {
                    resetView();
                    postInvalidate();
                    task = null;
                }
            };
            Log.d("task", "clearPassword schedule(" + time + ")");
            timer.schedule(task, time);
        } else {
            resetView();
            postInvalidate();
        }

    }

    public void setShowTrack(boolean showTrack) {
        isShowTrack = showTrack;
    }

    public boolean isShowTrack() {
        return isShowTrack;
    }

    //
    private OnCompleteListener mCompleteListener;

    public void setOnCompleteListener(OnCompleteListener mCompleteListener) {
        this.mCompleteListener = mCompleteListener;
    }

    public void setIExceptionInject(IMultiWindowExceptionInject IExceptionInject) {
        mIExceptionInject = IExceptionInject;
    }

    /**
     * 轨迹球画完成事件
     *
     * @author way
     */
    public interface OnCompleteListener {

        /** 画完了 */
        void onComplete(int length, String password);
    }

    private static final class Point {

        static final int STATE_NORMAL      = 0;
        static final int STATE_CHECK       = 1;
        static final int STATE_CHECK_ERROR = 2;

        final int mIndex;

        float mX;
        float mY;
        int   mState;

        Point(int index, float x, float y) {
            this.mIndex = index;
            this.mX = x;
            this.mY = y;
        }

        void update(float x, float y) {
            this.mX = x;
            this.mY = y;
        }
    }

    public interface IMultiWindowExceptionInject {
        /**
         *<br> Description: 是否处于分屏模式
         *<br> Author:      yangyinglong
         *<br> Date:        2018/4/12 16:32
         */
        boolean multiWindowInject();
    }
}
