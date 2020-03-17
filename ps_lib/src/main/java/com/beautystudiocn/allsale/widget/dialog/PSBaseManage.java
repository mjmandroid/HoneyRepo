package com.beautystudiocn.allsale.widget.dialog;


import android.content.Context;

import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.widget.dialog.base.IPSDialog;
import com.beautystudiocn.allsale.widget.dialog.base.IPSDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <br> ClassName:   PSBaseManage
 * <br> Description:
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/2/6 20:26
 */
class PSBaseManage {
    private static final String TAG = PSBaseManage.class.getSimpleName() + "  ";
    /*** 当前显示的dialog ***/
    private IPSDialog currentShowDialog = null;
    private Context context = null;

    /*** 管理dialog 对象 ***/
    private List<IPSDialog> arraySource = null;

    public PSBaseManage(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * <br> Description: 同步添加
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/6 20:41
     */
    public void addDialog2Array(IPSDialog dialog) {
        if (isDialogNull(dialog)) return;
        if (arraySource == null) {
            this.arraySource = new ArrayList<>();
            Collections.synchronizedList(this.arraySource);
        }
        this.arraySource.add(dialog);
    }

    /**
     * <br> Description: 从数组中移除相应的dialog
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/6 20:48
     */
    public void removeDialog2Array(IPSDialog dialog) {
        if (isDialogNull(dialog)) return;
        if (arraySource.contains(dialog)) {
            arraySource.remove(dialog);
        }
    }

    /**
     * <br> Description: 清空dialog列表
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/6 20:55
     */
    public boolean clearDialog2Array() {
        if (arraySource == null) {
            return false;
        } else {
            if (!arraySource.isEmpty()) {
                arraySource.clear();
            }
            return true;
        }
    }

    /**
     * <br> Description: 判断是否为null
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/6 20:47
     */
    private boolean isDialogNull(IPSDialog dialog) {
        if (dialog == null) {
            LoggerManager.e("TAG", TAG + "dialog is null");
            return true;
        } else {
            LoggerManager.e("TAG", TAG + dialog.getClass().getSimpleName());
            return false;
        }
    }


    /**
     * <br> Description: 设置当前显示的dialog
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/6 20:58
     */
    public void setCurrentShowDialog(IPSDialog dialog) {
        this.currentShowDialog = dialog;
    }

    /**
     * <br> Description: 当前显示的dialog
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/6 20:57
     */
    public IPSDialog getCurrentShowDialog() {
        return currentShowDialog;
    }
}
