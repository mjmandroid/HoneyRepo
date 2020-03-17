package com.beautystudiocn.allsale.widget.dialog.base;

import android.view.View;

/**
 * <br> ClassName:   IPSDialogHideListener
 * <br> Description: 关闭回调
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/1/26 14:32
 */
public interface IPSDialogHideListener {

    /**
     * <br> Description: 将要隐藏
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/25 16:09
     */
     void onPSDialogWillHide(IPSDialog dialog, View view);

    /**
     * <br> Description: 关闭时触发
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/26 14:33
     */
    void onPSDialogHide(IPSDialog dialog, View view);
}
