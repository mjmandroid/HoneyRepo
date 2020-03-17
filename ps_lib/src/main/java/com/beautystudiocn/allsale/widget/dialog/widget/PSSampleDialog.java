package com.beautystudiocn.allsale.widget.dialog.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.widget.dialog.base.PSBaseDialog;

/**
 * <br> ClassName:   PSSampleDialog
 * <p>
 * <br> Description: 派生Tipdialog
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/1/29 17:38
 */
public class PSSampleDialog extends PSBaseDialog {
    public PSSampleDialog(@NonNull Context context) {
        super(context, false);
    }

    public PSSampleDialog(@NonNull Context context, @StyleRes int id) {
        super(context, id);
    }

    @Override
    public View initView() {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.lib_tabvp_layout_icon, null);
        TextView view = new TextView(getContext());
        view.setText("test");
        return null;
    }

    public PSSampleDialog setTitle(String title) {
        if (getDialog() != null && title != null) {
            getDialog().setTitle(title);
        } else {
            LoggerManager.e("设置Dialog title 失败，dialog is null");
        }
        return this;
    }

    public PSSampleDialog setMessage(String message) {
        if (getDialog() != null && message != null) {
            ((AlertDialog) getDialog()).setMessage(message);
        } else {
            LoggerManager.e("设置Dialog message 失败，dialog is null");
        }

        return this;
    }
}
