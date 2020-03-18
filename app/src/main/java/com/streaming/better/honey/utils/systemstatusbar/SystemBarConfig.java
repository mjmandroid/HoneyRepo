package com.streaming.better.honey.utils.systemstatusbar;

import android.graphics.Color;
import android.view.ViewGroup;

/**
 * <br> ClassName:   SystemBarConfig                        
 * <br> Description: todo(这里用一句话描述这个类的作用)   
 * <br>  
 * <br> Author:      yexiaochuan                             
 * <br> Date:        2018/4/26 16:53                     
 */
public class SystemBarConfig {
    int color;
    int lollipopColor = Color.BLACK;
    boolean isFrontDark;
    boolean isFullScreen;
    ViewGroup fitsLayout;

    public SystemBarConfig setColor(int color) {
        this.color = color;
        return this;
    }

    public SystemBarConfig setLollipopColor(int lollipopColor) {
        this.lollipopColor = lollipopColor;
        return this;
    }

    public SystemBarConfig setFrontDark(boolean frontDark) {
        isFrontDark = frontDark;
        return this;
    }

    public SystemBarConfig setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        return this;
    }

    public SystemBarConfig setFullScreen(ViewGroup fitsLayout) {
        this.fitsLayout = fitsLayout;
        return this;
    }

    public int getColor() {
        return color;
    }

    public int getLollipopColor() {
        return lollipopColor;
    }

    public boolean isFrontDark() {
        return isFrontDark;
    }

    public ViewGroup getFitsLayout() {
        return fitsLayout;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }
}
