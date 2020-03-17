package com.beautystudiocn.allsale.widget.dialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.widget.dialog.base.PSBaseDialog;

/**
 * <br> ClassName:   PSFullScreenDialog
 * <br> Description: 占满整个屏幕
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/1/29 22:51
 */
public class PSFullScreenDialog extends PSBaseDialog {
    private FrameLayout contentLayout;

    public PSFullScreenDialog(@NonNull Context context) {
        super(context, false);
        contentLayout = new FrameLayout(getContext());
        initDialog();
    }

    /**
     * <br> Description: 初始化
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/30 11:15
     */
    private void initDialog() {
        Dialog dialog = new Dialog(getContext());
        //设置背景为透明
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setDialog(dialog);
    }

    @Override
    public void show() {
        super.show();
    }


    @Override
    public void setCustomView(View view) {
        contentLayout.addView(view);
        contentLayout.setBackgroundColor(Color.parseColor("#7f000000"));
        super.setCustomView(contentLayout);
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        // 设置宽度全屏
        WindowManager.LayoutParams winParams = getDialog().getWindow().getAttributes();
        winParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        winParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getDialog().getWindow().setAttributes(winParams);
    }

    /**
     * <br> Description: 点击外部退出
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/30 11:35
     */
    @Override
    public Dialog setCanceledOnTouchOutside(boolean boo) {
        if (boo) {
            contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            LoggerManager.e("zll", "setCanceledOnTouchOutside false");
        }
        return super.setCanceledOnTouchOutside(boo);
    }
}
