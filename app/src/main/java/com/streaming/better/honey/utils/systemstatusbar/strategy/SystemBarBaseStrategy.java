package com.streaming.better.honey.utils.systemstatusbar.strategy;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;


import com.streaming.better.honey.utils.systemstatusbar.SystemBarConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <br> ClassName:   SystemBarBaseStrategy
 * <br> Description: todo(这里用一句话描述这个类的作用)
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2018/4/27 16:56
 */
public abstract class SystemBarBaseStrategy {
    private SystemBarConfig mConfig;
    private Activity mActivity;

    public SystemBarBaseStrategy(Activity activity, SystemBarConfig config) {
        mConfig = config;
        mActivity = activity;
    }

    public abstract boolean setStatusBar();

    protected void setFitSystemWindow() {
        if (getConfig().getFitsLayout() != null) {
            getConfig().getFitsLayout().setFitsSystemWindows(true);
            getConfig().getFitsLayout().setClipToPadding(false);
        }
    }

    public boolean isFullScreen() {
        return mConfig != null && (mConfig.isFullScreen() || mConfig.getFitsLayout() != null);
    }
    /**
     * <br> Description: 设置状态栏图标为深色和魅族特定的文字风格,可以用来判断是否为Flyme用户
     * <br> Author:      wuheng
     * <br> Date:        2017/9/1 16:41
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    protected boolean FlymeSetStatusBarDarkMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * <br> Description: 已知系统类型时，设置状态栏黑色字体图标。
     * <br>适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * <br> Author:      wuheng
     * <br> Date:        2017/9/1 16:41
     *
     * @param window
     * @param dark
     * @return
     */
    protected boolean MIUISetStatusBarDarkMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {
//                Log.e("zll", "zll", e);
                return false;
            }
        }
        return result;
    }

    public SystemBarConfig getConfig() {
        return mConfig;
    }

    public void setmConfig(SystemBarConfig mConfig) {
        this.mConfig = mConfig;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }
}
