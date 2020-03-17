package com.beautystudiocn.allsale.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperToast;
import com.beautystudiocn.allsale.R;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


/**
 * 提示工具类
 *
 * @author longluliu
 * Create at: 2015-1-5 下午2:19:05
 * @Filename: ToastUtil.java
 * @Description: TODO
 */
public class ToastUtil {
    private static SuperToast mToast;
    private static String mLastContent;
    private static SparseArray<PopupWindow> list = new SparseArray<>();

    private static Reference<View> defaultRootView;

    private static Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                try {
                    PopupWindow mPopupWindow = list.get(msg.arg1);
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    list.remove(msg.arg1);
                    mPopupWindow = null;
                } catch (Exception e) {
                }
            }
        }
    };


    /**
     * <br> Description: 设置展示Snackbar的默认View
     * <br> Author:      liaoshengjian
     * <br> Date:        2017/7/13 15:30
     *
     * @param rootView View
     */
    public static void setDefaultRootView(View rootView) {
        defaultRootView = new WeakReference(rootView);
    }

    /**
     * <br> Description: 获取展示Snackbar的默认View
     * <br> Author:      liaoshengjian
     * <br> Date:        2017/7/13 15:30
     *
     * @return View
     */
    public static View getDefaultRootView() {
        if (defaultRootView != null) {
            return defaultRootView.get();
        }
        return null;
    }

    /**
     * 显示toast （使用showToast(String content, View RootView)替换）
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:11
     */
    public static void showToast(String content) {
        //showToast(content, null);
        Toast.makeText(AppUtil.getContext(), content, Toast.LENGTH_SHORT).show();

    }

    public static void showToast(String lastContent, Context context) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 显示toast
     *
     * @param content  文本
     * @param RootView 消息提示依赖View , 不建议使用null, 使用MVP架构的类中禁止使用null
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:11
     */
    public static void showToast(String content, View RootView) {
        showToast(content);

        /*if (RootView != null) {
            try {
                final Snackbar sbView = Snackbar.make(RootView, content, Snackbar.LENGTH_SHORT).setAction(null, null);
                sbView.show();
                sbView.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sbView.dismiss();
                    }
                });
            } catch (Exception e) {
                showToastSuperToast(content);
            }
        } else if (getDefaultRootView() != null) {
            try {
//                if (sb != null && sb.isShown()) {
//                    sb.setText(content);
//                    sb.setDuration(Snackbar.LENGTH_SHORT);
//                    sb.show();
//                } else {
//                    sb = Snackbar.make(BaseActivity.getmRootView(), content, Snackbar.LENGTH_SHORT).setAction(null, null);
//                    sb.show();
//                    sb.getView().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            sb.dismiss();
//                        }
//                    });
//                }
                final Snackbar sb = Snackbar.make(getDefaultRootView(), content, Snackbar.LENGTH_SHORT).setAction(null, null);
                sb.show();
                sb.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sb.dismiss();
                    }
                });
            } catch (Exception e) {
                showToastSuperToast(content);
            }
        } else {
            showToastSuperToast(content);
        }*/
    }

    private static void showToastSuperToast(String content) {
        try {
            if (mToast != null) {
                if (mLastContent.equals(content)) {
                    mToast.cancelAllSuperToasts();
                }
                mToast.setText(content);
            } else {
                mToast = new SuperToast(AppUtil.getContext());
                mToast.setDuration(SuperToast.Duration.SHORT);
                mToast.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                mToast.setText("" + content);
            }
            if (!content.contains("<html>")) {
                mToast.show();
                mLastContent = content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showToast(int content) {
        String msg = AppUtil.getContext().getString(content).toString();
        showToast(msg);
    }

    /**
     * 显示信息
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:16
     */
    public static void showResultToast( int info, Activity mActivity) {
        String msg = AppUtil.getContext().getString(info).toString();
        showResultToast(mActivity, msg);
    }

    public static void showResultToast(Activity mActivity, String info) {
        //showResultToast(mActivity, info, R.drawable.lib_ic_success);
        showResultToast(mActivity, info, 0);
    }

    /**
     * 显示信息
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:16
     */
    public static void showResultToast(Activity mActivity, int info, int iconId) {
        String msg = AppUtil.getContext().getString(info).toString();
        showResultToast(mActivity, msg, iconId);
    }

    /**
     * 显示信息
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:16
     */
    public static void showResultToast(Activity mActivity, String info, int iconId) {
        PopupWindow mPopupWindow = showToastDialog(mActivity, info, iconId);
        if (mPopupWindow != null) {
            list.put(mPopupWindow.hashCode(), mPopupWindow);
            Message message = new Message();
            message.arg1 = mPopupWindow.hashCode();
            message.what = 1;
            mHandler.sendMessageDelayed(message, 2000);
        }
    }

    /**
     * ToastDialog
     *
     * @param mActivity
     * @param info
     * @param iconId
     */
    private static PopupWindow showToastDialog(Activity mActivity, String info, int iconId) {
        if (mActivity == null || mActivity.isFinishing()) {
            return null;
        }
        try {
            PopupWindow mPopupWindow = new PopupWindow(mActivity);
            View view = LayoutInflater.from(mActivity).inflate(R.layout.lib_toast_dialog, null);
            //view.getBackground().setAlpha(100);
            mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setContentView(view);
            ColorDrawable dw = new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent));
            mPopupWindow.setBackgroundDrawable(dw);
            mPopupWindow.showAtLocation(mActivity.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
            ((ImageView) view.findViewById(R.id.ivIcon)).setImageResource(iconId);
            ((TextView) view.findViewById(R.id.tv_content)).setText(info);
            return mPopupWindow;
        } catch (Exception e) {
            return null;
        }
    }
}
