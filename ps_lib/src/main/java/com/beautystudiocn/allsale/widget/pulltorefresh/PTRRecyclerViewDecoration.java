package com.beautystudiocn.allsale.widget.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author: wujianghua
 * @Filename:
 * @Description: PullToRefreshRecyclerView分割线
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2017/1/11 16:17
 */
public class PTRRecyclerViewDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "tuandai";
    private Drawable mDivider;
    private int dividerHeight;
    private int dividerWidth;
    private int mOrientation;

    /**
     * 使用hadHeaderCount 和 hadFooterCount代替
     */
    /*public boolean isHadHeader = false;
    public boolean isHadFooter = false;*/
    public int hadHeaderCount = -1;
    public int hadFooterCount = -1;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    public static final int[] ATRRS = new int[]{
            android.R.attr.listDivider
    };

    public PTRRecyclerViewDecoration(Context context, int orientation) {
        final TypedArray ta = context.obtainStyledAttributes(ATRRS);
        this.mDivider = ta.getDrawable(0);
        this.dividerHeight = mDivider.getIntrinsicHeight();
        this.dividerWidth = mDivider.getIntrinsicWidth();
        ta.recycle();
        setOrientation(orientation);
    }

    public PTRRecyclerViewDecoration(Context context, int orientation, Drawable drawable) {
        this.mDivider = drawable;
        this.dividerHeight = mDivider != null ? mDivider.getIntrinsicHeight() : 0;
        this.dividerWidth = mDivider != null ? mDivider.getIntrinsicWidth() : 0;
        setOrientation(orientation);
    }

    public PTRRecyclerViewDecoration(Context context, int orientation, Drawable drawable, int dividerHeight) {
        this.mDivider = drawable;
        this.dividerHeight = dividerHeight;
        this.dividerWidth = mDivider != null ? mDivider.getIntrinsicWidth() : 0;
        setOrientation(orientation);
    }

    //设置屏幕方向
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildCount() > 1) {
            if (mOrientation == HORIZONTAL_LIST) {
                drawVerticalLine(c, parent, state);
            } else {
                drawHorizontalLine(c, parent, state);
            }
        }
    }

    //横向
    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();

        int dataEndPosition = parent.getAdapter().getItemCount();
        for (int i = 0; i < childCount - 1; i++) {
            if (mDivider == null) {
                break;
            }
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);

            //获取child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            int bottom = top + dividerHeight;

            //处理第一个HeaderView、最后一个FooterView分割线
            if (position < hadHeaderCount || (position >= (dataEndPosition - hadFooterCount - 1))) {
                bottom = top;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    //竖向
    public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();

        int dataEndPosition = parent.getAdapter().getItemCount();
        for (int i = 0; i < childCount - 1; i++) {
            if (mDivider == null) {
                break;
            }
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);

            //获取child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            //处理第一个HeaderView、最后一个FooterView分割线
            if (position < hadHeaderCount || (position >= (dataEndPosition - hadFooterCount - 1))) {
                right = left;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL_LIST) {
            outRect.set(0, 0, dividerWidth, 0);
        } else {
            outRect.set(0, 0, 0, dividerHeight);


        }
    }
}
