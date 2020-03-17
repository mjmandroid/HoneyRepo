package com.beautystudiocn.allsale.widget.dialog.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.KeyEvent;
import android.view.View;

import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.log.LoggerManager;


/**
 * <br> ClassName:   PSBaseDialog
 * <br> Description: 只显示弹框，但是没有内容
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/1/25 16:05
 */
public abstract class PSBaseDialog<T extends Dialog> implements IPSDialog {
    private Context mContext = null;
    private Dialog mDialog = null;
    private IPSDialogShowListener mShowLinstener = null;
    private IPSDialogHideListener mHideLinstener = null;
    private IPSDialogDismissListener mDismissLinstener = null;
    /***显示的view***/
    private View view;

    /***显示的回调***/
    private PSDialogStateAdapter mDialogAdapter = null;

    /***显示的级别***/
    private int level = 0;
    /***遇到高级弹窗时，是否被忽略，不显示弹窗，或被新的高级别弹窗替换***/
    private boolean isIgnore = false;

    public PSBaseDialog(@NonNull Context context) {
        this.mContext = context;
        builder(-1, true);
    }

    public PSBaseDialog(@NonNull Context context, boolean isTransparent) {
        this.mContext = context;
        builder(-1, isTransparent);
    }

    public PSBaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        this.mContext = context;
        builder(themeResId, true);
    }

    public PSBaseDialog(@NonNull Context context, @StyleRes int themeResId, boolean isTransparent) {
        this.mContext = context;
        builder(themeResId, isTransparent);
    }

    private void builder(@StyleRes int themeResId, boolean isTransparent) {
        if (themeResId == -1) {
            mDialog = new AlertDialog.Builder(mContext).create();
        } else {
            mDialog = new AlertDialog.Builder(mContext, themeResId).create();
        }
        if (isTransparent) {
            //设置背景为透明
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    protected final T getDialog() {
        return (T) this.mDialog;
    }

    protected void setDialog(T dialog) {
        this.mDialog = dialog;
    }

    /**
     * <br> Description: 设置状态的适配
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/26 15:08
     */
    public void setStateAdapter(PSDialogStateAdapter adapter) {
        if (adapter != null) {
            this.mDialogAdapter = adapter;
        }
        if (mDialog == null) {
            LoggerManager.e("dialog is null");
            return;
        }
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mDialogAdapter != null) {
                    mDialogAdapter.onPSDialogDimiss(PSBaseDialog.this);
                }
            }
        });
    }

    /**
     * <br> Description: 显示状态
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/26 18:13
     */
    public void setShowLinstener(IPSDialogShowListener showLinstener) {
        this.mShowLinstener = showLinstener;
    }

    /**
     * <br> Description: 隐藏状态
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/26 18:14
     */
    public void setHideLinstener(IPSDialogHideListener hideLinstener) {
        this.mHideLinstener = hideLinstener;
    }

    /**
     * <br> Description: 退出回调
     * <br> Author:      KevinWu
     * <br> Date:        2018/1/29 21:18
     */
    public void setDismissLinstener(IPSDialogDismissListener dismissLinstener) {
        this.mDismissLinstener = dismissLinstener;
        if (mDialog == null) {
            LoggerManager.e("dialog is null");
            return;
        }
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mDismissLinstener != null) {
                    mDismissLinstener.onPSDialogDimiss(PSBaseDialog.this);
                }
            }
        });
    }

    @Override
    public void show() {
        if (mDialog == null) {
            LoggerManager.e("dialog is null");
            return;
        }

        if (mDialogAdapter != null) {
            mDialogAdapter.onPSDialogWillShow(this, this.view);
        } else if (mShowLinstener != null) {
            mShowLinstener.onPSDialogWillShow(this, this.view);
        }
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (mDialogAdapter != null) {
                    mDialogAdapter.onPSDialogShow(PSBaseDialog.this, view);
                } else if (mShowLinstener != null) {
                    mShowLinstener.onPSDialogShow(PSBaseDialog.this, view);
                }
            }
        });
        mDialog.show();
        if (initView() != null) {
            setCustomView(initView());
        }

    }

    /**
     * <br> Description: 初始化界面
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/2 15:12
     */
    public View initView() {
        return null;
    }

    @Override
    public void hide() {
        if (mDialog == null) {
            LoggerManager.e("dialog is null");
            return;
        }
        if (mDialog.isShowing()) {
            if (mDialogAdapter != null) {
                mDialogAdapter.onPSDialogWillHide(this, this.view);
            } else if (mHideLinstener != null) {
                mHideLinstener.onPSDialogWillHide(this, this.view);
            }
            mDialog.hide();
            if (mDialogAdapter != null) {
                mDialogAdapter.onPSDialogHide(this, this.view);
            } else if (mHideLinstener != null) {
                mHideLinstener.onPSDialogHide(this, this.view);
            }
        }
    }

    @Override
    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialogAdapter = null;
        }
    }

    @Override
    public boolean isShow() {
        if (mDialog == null) {
            LoggerManager.e("dialog is null");
            return false;
        }
        return mDialog.isShowing();
    }

    @Override
    public void setCustomView(View view) {
        if (mDialog == null) {
            LoggerManager.e("dialog is null");
            return;
        }
        if (view != null) {
            if (this.view == null) {
                this.view = view;
            }
            mDialog.setContentView(this.view);
        }
    }


    public T setCanceledOnTouchOutside(boolean boo) {
        if (mDialog == null) {
            LoggerManager.e("dialog is null");
            return null;
        }
        mDialog.setCanceledOnTouchOutside(boo);
        return getDialog();
    }

    @Override
    public void setLevel(int level) {
        if (level < 0) {
            LoggerManager.e("level 级别不能小于 0 ");
            return;
        }
        this.level = level;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setIgnoreShow(boolean ignore) {
        this.isIgnore = ignore;
    }

    @Override
    public boolean getIgnoreShow() {
        return this.isIgnore;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 是否屏蔽back键
     * {@link KeyEvent#KEYCODE_BACK BACK} key.
     */
    public T setCancelable(boolean flag) {
        getDialog().setCancelable(flag);
        return getDialog();
    }

    /**
     * <br> Description: 判断context 是否是Activity的实例，是否有效
     * <br> Author:      KevinWu
     * <br> Date:        2018/2/2 15:22
     */
    protected boolean checkIsActive() {
        if (!(getContext() instanceof Activity) || (((Activity) getContext()).isFinishing())) {
            return false;
        }
        return true;
    }

}
