package com.beautystudiocn.allsale.autoupdate.product.custom;

/**
 * <br> ClassName:   ICustomUpdate
 * <br> Description: 提供给外部使用的功能接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/7/7 9:49
 */
public interface ICustomUpdate {
    /**
     *<br> Description: 打开更新窗口
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/26 16:04
     */
    void showUpdateDialog();

    /**
     *<br> Description: 销毁窗口
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/26 16:03
     */
    void destroyUpdateDialog();
}
