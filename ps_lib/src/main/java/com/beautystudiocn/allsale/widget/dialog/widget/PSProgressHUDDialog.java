package com.beautystudiocn.allsale.widget.dialog.widget;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beautystudiocn.allsale.widget.dialog.ProgressHUD;
import com.beautystudiocn.allsale.widget.dialog.base.PSBaseDialog;
import com.beautystudiocn.allsale.widget.dialog.ProgressHUD;

/**
 * <br> ClassName:   PSProgressHUDDialog
 * <br> Description: 整合进度加载的 dialog
 * <br> 管理的对象，参考 {@link ProgressHUD}
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/2/6 21:32
 */
public class PSProgressHUDDialog extends PSBaseDialog<ProgressHUD> {
    private ProgressHUD mProgressHud = null;

    public PSProgressHUDDialog(@NonNull Context context) {
        super(context);

    }

    public static ProgressHUD show(Context context, int message) {
        return ProgressHUD.show(context, message);
    }

    /**
     * <br> Description: 显示并返回相应的对象
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/6 21:40
     */
    public static ProgressHUD show(Context context, String message) {
        return ProgressHUD.show(context, message);
    }

}
