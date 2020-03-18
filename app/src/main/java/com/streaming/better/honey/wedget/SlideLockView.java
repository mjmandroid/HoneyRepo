package com.streaming.better.honey.wedget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.streaming.better.honey.R;


/**
 * Created by zhang shuailing on 2016/9/28.
 * email bitxiaozhang@163.com
 */
public class SlideLockView extends View {

    private Bitmap mLockBitmap;
    private int mLockDrawableId;
    private Paint mPaint;
    private int mLockRadius;
    private String mTipText;
    private int mTipsTextSize;
    private int mTipsTextColor;
    private Rect mTipsTextRect = new Rect();

    private float mLocationX;
    private boolean mIsDragable = false;
    private OnLockListener mLockListener;
    private int lastX;
    private int lastY;
    private int type = 0;  //0 向右滑   1向左滑
    private boolean touchable;


    public SlideLockView(Context context) {
        this(context, null);

    }

    public SlideLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray tp = context.obtainStyledAttributes(attrs, R.styleable.SlideLockView, defStyleAttr, 0);
        mLockDrawableId = tp.getResourceId(R.styleable.SlideLockView_lock_drawable, -1);
        mLockRadius = tp.getDimensionPixelOffset(R.styleable.SlideLockView_lock_radius, 1);
        mTipText = tp.getString(R.styleable.SlideLockView_lock_tips_tx);
        mTipsTextSize = tp.getDimensionPixelOffset(R.styleable.SlideLockView_locl_tips_tx_size, 12);
        mTipsTextColor = tp.getColor(R.styleable.SlideLockView_lock_tips_tx_color, Color.BLACK);

        tp.recycle();

        if (mLockDrawableId == -1) {
            throw new RuntimeException("未设置滑动解锁图片");
        }
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTipsTextSize);
        mPaint.setColor(mTipsTextColor);

        mLockBitmap = BitmapFactory.decodeResource(context.getResources(), mLockDrawableId);
        int oldSize = mLockBitmap.getHeight();
        int newSize = mLockRadius * 2;
        float scale = newSize * 1.0f / oldSize;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        mLockBitmap = Bitmap.createBitmap(mLockBitmap, 0, 0, oldSize, oldSize, matrix, true);
    }

    public void setmTipText(String tip) {
        mTipText = tip;
        invalidate();
    }

    public void setRightMax() {
//        int rightMax = getWidth() - mLockRadius * 2;
        mLocationX = 570 + 1;
        invalidate();
        if (mLockListener != null) {
            mLockListener.onCloseLockSuccess();
        }
    }

    public void setTouchAble(boolean canTouch) {
        this.touchable = canTouch;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.getClipBounds(mTipsTextRect);
        int cHeight = mTipsTextRect.height();
        int cWidth = mTipsTextRect.width();
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTipsTextRect);
        float x = cWidth / 2f - mTipsTextRect.width() / 2f - mTipsTextRect.left;
        float y = cHeight / 2f + mTipsTextRect.height() / 2f - mTipsTextRect.bottom;
        canvas.drawText(mTipText, x, y, mPaint);

        int rightMax = getWidth() - mLockRadius * 2;
        if (mLocationX < 0) {
            canvas.drawBitmap(mLockBitmap, 0, 0, mPaint);
        } else if (mLocationX > rightMax) {
            canvas.drawBitmap(mLockBitmap, rightMax, 0, mPaint);
        } else {
            canvas.drawBitmap(mLockBitmap, mLocationX, 0, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = x;
                lastY = y;
                float xPos = event.getX();
                float yPos = event.getY();
                if (isTouchLock(xPos, yPos)) {
                    mLocationX = xPos - mLockRadius;
                    mIsDragable = true;
                    invalidate();
                } else {
                    mIsDragable = false;
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if (touchable) {
                    int daltaY = y - lastY;
                    int daltaX = x - lastX;
                    if (Math.abs(daltaX) < Math.abs(daltaY)) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);

                    }
                    if (!mIsDragable) return true;

                    int rightMax = getWidth() - mLockRadius * 2;
                    if (type == 0) {
                        resetLocationX(event.getX(), rightMax);
                        invalidate();
                        if (mLocationX >= rightMax) {
                            mIsDragable = false;
//                    mLocationX = 0;
                            invalidate();
                            if (mLockListener != null) {
                                mLockListener.onOpenLockSuccess();
                            }
                            Log.e("AnimaterListener", "解锁成功");
                            type = 1;
                        }
                    } else {
                        resetLocationX1(event.getX(), rightMax);
                        invalidate();
                        if (mLocationX <= 0) {
                            mIsDragable = false;
                            invalidate();
                            if (mLockListener != null) {
                                mLockListener.onCloseLockSuccess();
                            }
                            Log.e("AnimaterListener", "解锁成功2");
                            type = 0;
                        }
                    }
                    return false;
                }
            }
            case MotionEvent.ACTION_UP: {
                if (!mIsDragable) return true;
                if (type == 0) {
                    resetLock();
                } else {
                    returnLock();
                }
                break;
            }
        }
        return super.

                onTouchEvent(event);
    }

    private void resetLock() {
        ValueAnimator anim = ValueAnimator.ofFloat(mLocationX, 0);
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mLocationX = (Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }


    private void returnLock() {
        ValueAnimator anim = ValueAnimator.ofFloat(mLocationX, getWidth() - mLockRadius * 2);
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mLocationX = (Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    /**
     * 右滑没到最大 重置x值
     *
     * @param eventXPos 当前x的位置
     * @param rightMax  最大宽度
     */
    private void resetLocationX(float eventXPos, float rightMax) {

        float xPos = eventXPos;
        mLocationX = xPos - mLockRadius;
//        Logger.e("mLocationX =" + mLocationX + " xPos =" + xPos + " rightMax =" + rightMax + " mLockRadius =" + mLockRadius);
        if (mLocationX < 0) {
            mLocationX = 0;
        } else if (mLocationX >= rightMax) {
            mLocationX = rightMax;
        }
    }

    /**
     * 左滑没到最大 重置x值
     *
     * @param eventXPos
     * @param rightMax
     */
    private void resetLocationX1(float eventXPos, float rightMax) {

        float xPos = eventXPos;
        mLocationX = xPos - mLockRadius;
        if (mLocationX < 0) {
            mLocationX = 0;
        } else if (mLocationX >= rightMax) {
            mLocationX = rightMax;
        }
    }

    private boolean isTouchLock(float xPos, float yPox) {
        float centerX = mLocationX + mLockRadius;
        float diffX = xPos - centerX;
        float diffY = yPox - mLockRadius;

        return diffX * diffX + diffY * diffY < mLockRadius * mLockRadius;
    }


    public void setmLockListener(OnLockListener mLockListener) {
        this.mLockListener = mLockListener;
    }

    public interface OnLockListener {
        void onOpenLockSuccess();

        void onCloseLockSuccess();
    }
}