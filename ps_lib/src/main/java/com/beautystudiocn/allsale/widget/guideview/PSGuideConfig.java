package com.beautystudiocn.allsale.widget.guideview;

import android.graphics.Color;

/**
 * <br> ClassName:   PSGuideConfig
 * <br> Description: 引导图配置
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/4/28 15:57
 */
public class PSGuideConfig {
    /***默认为透明黑色***/
    private int backgroundColor = Color.parseColor("#99000000");
    /***默认为白色***/
    private int targetViewBackgroundColor = Color.WHITE;
    /*** 高亮是否打开 ***/
    private boolean isHighLightOn = true;
    /*** 高亮宽度 ***/
    private int highLightWidth = 15;
    /*** 高亮颜色默认为白色 ***/
    private int highLightColor = Color.WHITE;
    /*** 高亮是否闪烁 ***/
    private boolean isTwinklingHighLight;
    /*** 高亮闪烁次数，默认3次 ***/
    private int twinklingHighLightCount = 3;

    public boolean isHighLightOn() {
        return isHighLightOn;
    }

    public PSGuideConfig setHighLightOn(boolean highLightOn) {
        isHighLightOn = highLightOn;
        return this;
    }

    public int getHighLightWidth() {
        return highLightWidth;
    }

    public PSGuideConfig setHighLightWidth(int highLightWidth) {
        this.highLightWidth = highLightWidth;
        return this;
    }

    public int getHighLightColor() {
        return highLightColor;
    }

    public PSGuideConfig setHighLightColor(int highLightColor) {
        this.highLightColor = highLightColor;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public PSGuideConfig setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getTargetViewBackgroundColor() {
        return targetViewBackgroundColor;
    }

    public PSGuideConfig setTargetViewBackgroundColor(int targetViewBackgroundColor) {
        this.targetViewBackgroundColor = targetViewBackgroundColor;
        return this;
    }

    private boolean isTwinklingHighLight() {
        return isTwinklingHighLight;
    }

    private PSGuideConfig setTwinklingHighLight(boolean twinklingHighLight) {
        isTwinklingHighLight = twinklingHighLight;
        return this;
    }

    private int getTwinklingHighLightCount() {
        return twinklingHighLightCount;
    }

    private PSGuideConfig setTwinklingHighLightCount(int twinklingHighLightCount) {
        this.twinklingHighLightCount = twinklingHighLightCount;
        return this;
    }
}
