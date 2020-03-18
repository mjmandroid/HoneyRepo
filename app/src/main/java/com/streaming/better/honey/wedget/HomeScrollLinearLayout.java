package com.streaming.better.honey.wedget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.streaming.better.honey.utils.DensityUtil;


public class HomeScrollLinearLayout extends LinearLayout {


    private SmartRefreshLayout mMSmartRefreshLayout;

    public HomeScrollLinearLayout(Context context) {
        this(context, null);
    }

    public HomeScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout) {
        mMSmartRefreshLayout = smartRefreshLayout;
        mMSmartRefreshLayout.setEnableRefresh(true);
//        getParent().requestDisallowInterceptTouchEvent(true); sd modify 禁用父布局拦截功能
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);sd modify 禁用父布局拦截功能
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isMove) {
//                    mMSmartRefreshLayout.setEnableRefresh(false);sd modify
                } else {
//                    mMSmartRefreshLayout.setEnableRefresh(true);sd modify
                }
            case MotionEvent.ACTION_UP:
                if (getScrollY() < -DensityUtil.dip2px(getContext(), 60) / 2) {
//                    scrollTo(0, -UIUtil.dip2px(getContext(), 60));sd modify
                } else {
//                    scrollTo(0, 0);sd modify
                }
        }
        return super.dispatchTouchEvent(ev);
    }

    private float downY;
    private float downX;
    private int mScrollY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                downX = ev.getX();
                return false;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getY() - downY) > Math.abs(ev.getX() - downX)) {
                    return true;
                }
        }
        return false;
    }

    boolean isMove = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                mScrollY = getScrollY();
              /*  if (mMSmartRefreshLayout != null) {
                    mMSmartRefreshLayout.setEnableRefresh(false);
                }*/

                return true;
            case MotionEvent.ACTION_MOVE:
                isMove = false;
                int ddy = (int) (downY - ev.getY());
                if (ev.getY() >= downY && getScrollY() > -DensityUtil.dip2px(getContext(), 60)) {//sd modify 向上滑动
//                    scrollTo(0, (mScrollY + ddy) < -UIUtil.dip2px(getContext(), 60) ? -UIUtil.dip2px(getContext(), 60) : mScrollY + ddy);
//                    isMove = true; //sd modify 为true 禁止Refresh
                    return true;
                } else if (ev.getY() < downY && getScrollY() < 0) {//sd modify 向下滑动
//                    scrollTo(0, (mScrollY + ddy) > 0 ? 0 : mScrollY + ddy);
//                    isMove = true; //sd modify 为true 禁止Refresh
                    return true;
                } else {
                   /* if (mMSmartRefreshLayout != null) {
                        mMSmartRefreshLayout.setEnableRefresh(true);
                    }*/
                    if (!isMove)
                        getParent().requestDisallowInterceptTouchEvent(false);//sd modify 恢复父布局正常拦截
                }
                break;
           /* case MotionEvent.ACTION_UP:
                if (getScrollY() < -UIUtil.dip2px(getContext(), 55) / 2) {
                    scrollTo(0, -UIUtil.dip2px(getContext(), 55));
                } else {
                    scrollTo(0, 0);
                }
                break;*/
        }
        return false;
    }
}

