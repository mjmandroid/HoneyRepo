package com.streaming.better.honey.utils.systemstatusbar.strategy;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Window;
import android.view.WindowManager;

import com.streaming.better.honey.utils.systemstatusbar.SystemBarConfig;
import com.streaming.better.honey.utils.systemstatusbar.SystemBarTintManager;


/**
 * <br> ClassName:   SystemBar19Strategy
 * <br> Description: 兼容api19的沉浸式状态栏
 * <br>1.api19如果不开启FullScreen模式，无法设置状态栏颜色
 * <br>2.api19不支持修改状态栏字体颜色，目前只有小米跟魅族的ROM兼容
 * <br>3.小米和魅族的状态栏颜色使用参数color，其他机型使用lollipopColor
 * <br> Author:      yexiaochuan
 * <br> Date:        2018/4/27 16:57
 */
public class SystemBar19Strategy extends SystemBarBaseStrategy {

    public SystemBar19Strategy(Activity activity, SystemBarConfig config) {
        super(activity, config);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean setStatusBar() {
        if (!isFullScreen()) {
            return false;
        }

        setFitSystemWindow();

        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (isFullScreen()) winParams.flags |= bits;
        else winParams.flags &= ~bits;
        win.setAttributes(winParams);

        int color = getConfig().getColor();
        if (!MIUISetStatusBarDarkMode(win,getConfig().isFrontDark())
                && !FlymeSetStatusBarDarkMode(win,getConfig().isFrontDark())) {
            color = getConfig().getLollipopColor();
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(false);
        tintManager.setStatusBarTintColor(color);

        return true;
    }
}
