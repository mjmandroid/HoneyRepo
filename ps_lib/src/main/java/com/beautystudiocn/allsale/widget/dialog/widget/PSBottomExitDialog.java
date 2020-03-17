package com.beautystudiocn.allsale.widget.dialog.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.util.UIUtil;
import com.beautystudiocn.allsale.widget.dialog.base.PSBaseDialog;
import com.beautystudiocn.allsale.util.UIUtil;


/**
 * <br> ClassName:   PSBottomExitDialog
 * <br> Description: 底部带退出框的弹框
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/1/26 15:02
 */
public class PSBottomExitDialog extends PSBaseDialog implements View.OnClickListener {
    /***关闭弹窗view***/
    private ImageView closeView = null;
    /***外部边缘layout***/
    private LinearLayout mOutLayout = null;


    public PSBottomExitDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
    }

    /**
     * <br> Description: 初始化基础控件
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/26 16:11
     */
    @Override
    public void setCustomView(View view) {
        mOutLayout = new LinearLayout(getContext());
        mOutLayout.setOrientation(LinearLayout.VERTICAL);
        mOutLayout.addView(view);
        closeView = new ImageView(getContext());
        closeView.setPadding(0, UIUtil.dp2px(18), 0, 0);
        closeView.setImageResource(R.drawable.home_window_close);
        closeView.setOnClickListener(this);
        mOutLayout.addView(closeView);
        super.setCustomView(mOutLayout);
    }

    /**
     * <br> Description: 关闭按钮
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/29 22:09
     */
    protected View getCloseView() {
        return closeView;
    }

    @Override
    public void onClick(View v) {
        if (v == closeView) {
            hide();
        }
    }
}
