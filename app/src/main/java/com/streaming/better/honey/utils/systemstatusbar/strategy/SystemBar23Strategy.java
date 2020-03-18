package com.streaming.better.honey.utils.systemstatusbar.strategy;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.streaming.better.honey.utils.systemstatusbar.SystemBarConfig;


/**
 * <br> ClassName:   SystemBar23Strategy
 * <br> Description: api23以上兼容沉浸式状态栏
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2018/4/27 17:37
 */
public class SystemBar23Strategy extends SystemBarBaseStrategy {
    public SystemBar23Strategy(Activity activity, SystemBarConfig config) {
        super(activity, config);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean setStatusBar() {
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (isFullScreen()) {
            option = option | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            setFitSystemWindow();
        }

        //小米机型需要使用定制方法设置状态栏字体颜色
        MIUISetStatusBarDarkMode(getActivity().getWindow(),getConfig().isFrontDark());
        FlymeSetStatusBarDarkMode(getActivity().getWindow(),getConfig().isFrontDark());

        if (getConfig().isFrontDark()) {
            option = option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        getActivity().getWindow().getDecorView().setSystemUiVisibility(option);

        getActivity().getWindow().setStatusBarColor(getConfig().getColor());
        return false;
    }
}
