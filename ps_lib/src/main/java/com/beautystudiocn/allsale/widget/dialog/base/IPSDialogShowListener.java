package com.beautystudiocn.allsale.widget.dialog.base;

import android.view.View;

/**
 * <br> ClassName:   IPSDialogShowListener
 * <br> Description: 显示的状态
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/1/26 14:36
 */
public interface IPSDialogShowListener {

    /**
     * <br> Description: 显示前
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/25 16:09
     */
    void onPSDialogWillShow(IPSDialog dialog, View view);

    /**
     * <br> Description: 显示
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/25 16:08
     */
    void onPSDialogShow(IPSDialog dialog, View view);

}
