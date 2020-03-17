package com.beautystudiocn.allsale.widget.gestureview;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * <br> ClassName:   PSGestureConfig
 * <br> Description:  手势的相关配置类
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/10/13 15:57
 */
public final class PSGestureConfig {
    /***画布的直径,默认为150***/
    private int diameter = 150;
    /***PointItemView之间的间距***/
    private int margin = 70;
    /***一排显示的个数，默认为3***/
    private int columnNum = 3;
    /***绘制线条的颜色,默认浅灰色***/
    private int drawLineColor = Color.LTGRAY;
    /***错误状态下的颜色***/
    private int drawLineErrorColor = Color.RED;
    /***密码的最少长度,默认为4***/
    private int pwdMiniLength = 4;
    /***线延时消失的时间***/
    private int lineDisppearDelayTime = 250;
    /*** 线宽，默认为5 ***/
    private int drawLineWidth = 5;
    /***重新调整大小***/
    private boolean isAutoResize = true;

    /*** 点View的配置文件 ***/
    private PSGesturePointConfig pointViewConfig;

    public int getDiameter() {
        return diameter;
    }

    public PSGestureConfig setDiameter(int diameter) {
        this.diameter = diameter;
        return this;
    }

    public int getMargin() {
        return margin;
    }

    public PSGestureConfig setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public PSGestureConfig setColumnNum(int columnNum) {
        this.columnNum = columnNum;
        return this;
    }

    public int getDrawLineColor() {
        return drawLineColor;
    }

    public PSGestureConfig setDrawLineColor(@ColorInt int drawLineColor) {
        this.drawLineColor = drawLineColor;
        return this;
    }

    public int getPwdMiniLength() {
        return pwdMiniLength;
    }

    public PSGestureConfig setPwdMiniLength(int pwdMiniLength) {
        this.pwdMiniLength = pwdMiniLength;
        return this;
    }

    public int getLineDisppearDelayTime() {
        return lineDisppearDelayTime;
    }

    public PSGestureConfig setLineDisppearDelayTime(int lineDisppearDelayTime) {
        this.lineDisppearDelayTime = lineDisppearDelayTime;
        return this;
    }

    public int getDrawLineErrorColor() {
        return drawLineErrorColor;
    }

    public PSGestureConfig setDrawLineErrorColor(int drawLineErrorColor) {
        this.drawLineErrorColor = drawLineErrorColor;
        return this;
    }

    public PSGesturePointConfig getPointViewConfig() {
        return pointViewConfig;
    }

    public PSGestureConfig setPointViewConfig(PSGesturePointConfig pointViewConfig) {
        this.pointViewConfig = pointViewConfig;
        return this;
    }

    public int getDrawLineWidth() {
        return drawLineWidth;
    }

    public PSGestureConfig setDrawLineWidth(int drawLineWidth) {
        this.drawLineWidth = drawLineWidth;
        return this;
    }

    /**
     * <br> Description: 是否自动调整布局
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/26 19:14
     */
    public PSGestureConfig setAutoResize(boolean autoResize) {
        this.isAutoResize = autoResize;
        return this;
    }

    public boolean getAutoResize() {
        return isAutoResize;
    }
}
