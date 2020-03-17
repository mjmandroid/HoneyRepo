package com.beautystudiocn.allsale.widget.dialog.widget;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beautystudiocn.allsale.widget.dialog.CommonTipDialog;
import com.beautystudiocn.allsale.widget.dialog.base.PSBaseDialog;
import com.beautystudiocn.allsale.widget.dialog.base.PSDialogStateAdapter;
import com.beautystudiocn.allsale.widget.dialog.CommonTipDialog;

/**
 * <br> ClassName:   PSTipDialog
 * <br> Description: 提示框
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/2/5 17:12
 */
public class PSTipDialog extends PSBaseDialog<CommonTipDialog> {

    /*** commonTipDialog ***/
    private CommonTipDialog dialog = null;

    public PSTipDialog(@NonNull Context context) {
        super(context);
        initDialog();
    }

    /**
     * <br> Description: 初始化
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/30 11:15
     */
    private void initDialog() {
        dialog = new CommonTipDialog(getContext());
        setDialog(dialog);
    }

    public CommonTipDialog init(boolean isCanCancel) {
        return init(isCanCancel, null);
    }

    /**
     * <br> Description:
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/6 11:39
     */
    public CommonTipDialog init(boolean isCanCancel, PSDialogStateAdapter adapter) {
        setStateAdapter(adapter);
        show();
        return dialog.init(isCanCancel);
    }

}
