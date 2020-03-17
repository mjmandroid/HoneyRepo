package com.beautystudiocn.allsale.widget.dialog.base;

import android.view.View;

/**
 * <br> ClassName:   IPSDialog
 * <br> Description: dialog实现接口
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/1/25 16:06
 */
public interface IPSDialog {
    /**
     * <br> Description: 显示
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/26 14:59
     */
    void show();

    /**
     * <br> Description: 隐藏
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/26 14:59
     */
    void hide();

    /**
     * <br> Description:
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/29 17:25
     */
    void dismiss();

    /**
     * <br> Description: 显示状态
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/25 16:10
     */
    boolean isShow();

    /**
     * <br> Description: 用户自定义View
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/25 16:10
     */
    void setCustomView(View view);

    /**
     * <br> Description: 设置级别
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/2 15:06
     *
     * @param level 参数从0开始，往后累加，数字越大级别越高
     */
    void setLevel(int level);

    /**
     * <br> Description: 返回级别
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/2 15:07
     */
    int getLevel();

    /**
     * <br> Description: 如果有高级别弹窗，是否忽略此弹窗
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/2 15:28
     *
     * @param ignore true 忽略
     */
    void setIgnoreShow(boolean ignore);

    boolean getIgnoreShow();
}
