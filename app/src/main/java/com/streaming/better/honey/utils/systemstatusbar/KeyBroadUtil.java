package com.streaming.better.honey.utils.systemstatusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * <br> ClassName:   KeyBroadUtil
 * <br> Description: 监视键盘的高度
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/8/7 14:23
 */

public class KeyBroadUtil {

    public interface IKeyBoardVisibleListener {
        void onSoftKeyBoardVisible(boolean visible, int keyboardHeight);
    }

    private static boolean isVisiableForLast = false;
    private static int keyBroadKeyHeight = 0;
    /***监控键盘的高度***/
    private static IKeyBoardVisibleListener keyBoardVisibleListener = null;

    public static boolean isKeyBroadShow() {
        return isVisiableForLast;
    }

    /**
     * <br> Description: 返回键盘的高度
     * <br> Author:      wuheng
     * <br> Date:        2017/9/4 14:37
     */
    public static int getKeyBroadKeyHeight() {
        return keyBroadKeyHeight;
    }

    /**
     * <br> Description: todo(监视键盘的高度变化)
     * <br> Author:      wuheng
     * <br> Date:        2017/8/4 16:22
     *
     * @param activity activity
     * @param listener 事件
     */
    public static void addSoftKeyBoardVisibleListener(final Activity activity, final IKeyBoardVisibleListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        keyBoardVisibleListener = listener;
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        decorView.getWindowVisibleDisplayFrame(rect);
                        //计算出可见屏幕的高度
                        int displayHeight = rect.bottom;//- rect.top;  // 顶部状态栏的高度
                        //获得屏幕整体的高度
                        int height = decorView.getHeight();
                        //获得键盘高度
                        keyBroadKeyHeight = height - displayHeight;
                        boolean visible = (double) displayHeight / height < 0.8;
                        if (visible != isVisiableForLast) {
                            listener.onSoftKeyBoardVisible(visible, keyBroadKeyHeight);
                        }
                        isVisiableForLast = visible;
                    }
                });
    }

    /**
     * <br> Description: todo(解绑)
     * <br> Author:      wuheng
     * <br> Date:        2017/8/7 14:25
     */

    public static void removeSofeKeyBoardVisibleListener() {
        keyBoardVisibleListener = null;
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(Activity activity){
        if(activity.isFinishing()){
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
