package com.beautystudiocn.allsale.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * <br> Description: 解决viewPager多点触控异常，参考：http://blog.csdn.net/nnmmbb/article/details/28419779
 * <br> Author:      wujianghua
 * <br> Date:        2017/10/12 18:13
 */
public class ViewPagerFixed extends ViewPager {

    private static final String TAG = "ViewPagerFixed";

    private int mActivePointerId = INVALID_POINTER;
    private static final int INVALID_POINTER = -1;

    public ViewPagerFixed(Context context) {
        super(context);
    }

    public ViewPagerFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return false;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "onTouchEvent catch IllegalArgumentException");
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        printInvalidPointer(ev);
        try {
            return false;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "onInterceptTouchEvent catch IllegalArgumentException");
            return false;
        }
    }

    private void printInvalidPointer(MotionEvent ev) {
        switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex == INVALID_POINTER) {
                    Log.e(TAG, "ACTION_MOVE , pointerIndex == INVALID_POINTER");
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                break;
        }
    }
}  