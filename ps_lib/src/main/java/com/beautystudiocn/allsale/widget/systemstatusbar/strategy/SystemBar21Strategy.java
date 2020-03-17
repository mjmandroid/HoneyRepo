package com.beautystudiocn.allsale.widget.systemstatusbar.strategy;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.beautystudiocn.allsale.widget.systemstatusbar.SystemBarConfig;

/**
 * <br> ClassName:   SystemBar21Strategy
 * <br> Description: 兼容api21与api22的沉浸式状态栏
 * <br>1.api21与api22不支持修改状态栏字体颜色，目前只有小米跟魅族的ROM兼容
 * <br>2.小米和魅族的状态栏颜色使用参数color，其他机型使用lollipopColor
 * <br> Author:      yexiaochuan
 * <br> Date:        2018/4/27 17:37
 */
public class SystemBar21Strategy extends SystemBarBaseStrategy {

    public SystemBar21Strategy(Activity activity, SystemBarConfig config) {
        super(activity, config);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean setStatusBar() {
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (isFullScreen()) {
            option = option | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            setFitSystemWindow();
        }
        getActivity().getWindow().getDecorView().setSystemUiVisibility(option);

        int color = getConfig().getColor();
        if (!MIUISetStatusBarDarkMode(getActivity().getWindow(),getConfig().isFrontDark())
                && !FlymeSetStatusBarDarkMode(getActivity().getWindow(),getConfig().isFrontDark())) {
            color = getConfig().getLollipopColor();
        }

        getActivity().getWindow().setStatusBarColor(color);
        return true;
    }
}
