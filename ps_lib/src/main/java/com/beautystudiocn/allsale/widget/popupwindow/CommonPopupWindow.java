package com.beautystudiocn.allsale.widget.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.beautystudiocn.allsale.view.ViewHolder;
import com.beautystudiocn.allsale.view.ViewHolder;

/**
 * 通用PopupWindow
 *
 * @author longluliu Create at: 2015-4-15 下午5:15:28
 * @Filename: BaseWindow.java
 * @Description: TODO
 * @Copyright: Copyright (c) 2014 Tuandai Inc. All Rights Reserved.
 */
@SuppressLint("ViewConstructor")
public class CommonPopupWindow extends PopupWindow {

    private View view;
    private OnClickBack mOnClickBack;

    public CommonPopupWindow(Activity context, int layoutId, OnClickListener onClickListener, PopupWindowCallback callback) {
        super(context);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        view = LayoutInflater.from(context).inflate(layoutId, null);
        ViewHolder holder = new ViewHolder(view, onClickListener);
        if (callback != null) {
            callback.createView(holder);
        }

        setContentView(view);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#44444444"));
        setBackgroundDrawable(dw);
    }

    public void setOnClickBack(OnClickBack OnClickBack) {
        this.mOnClickBack = OnClickBack;
    }

    public void setClickViewClose() {
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
    }

    public void showBottom(View parentView) {
        showAtLocation(parentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void showCenter(View parentView) {
        showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }

    public interface PopupWindowCallback {
        public void createView(ViewHolder holder);
    }

    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        if (mOnClickBack != null) {
            mOnClickBack.back();
        }
        super.dismiss();
    }

    public interface OnClickBack {
        void back();
    }

    public void clickView() {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }
}
