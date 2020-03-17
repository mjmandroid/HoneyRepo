package com.beautystudiocn.allsale.widget.gestureview;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * <br> ClassName:   PSGesturePointView
 * <br> Description:  PSGesturePointView中的参数配置类
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/9/30 11:27
 */
public class PSGesturePointConfig {
    private int renderRadius = 30;
    /***主色调***/
    private int mainColor = Color.RED;
    /***正常显示的颜色***/
    private int normalColor = Color.LTGRAY;
    /***选中时的颜色***/
    private int pressColor = Color.GREEN;
    /***显示错误的颜色***/
    private int errorColor = Color.RED;
    /***边框渲染宽度***/
    private int outerCircleWidth = 30;
    /***边框宽度***/
    private int strokeWidth = 1;
    /***是否显示外边框***/
    private boolean showOuterStrokeNormal = true;
    /***是否加上外部渲染***/
    private boolean showOuterRenderNormal = false;
    /***是否显示中间的小圆***/
    private boolean showCenterSmallCircleNormal = true;
    /***是否显示圆内渲染***/
    private boolean showInnerRenderNormal = true;

    /***是否显示外边框***/
    private boolean showOuterStrokeError = true;
    /***是否加上外部渲染***/
    private boolean showOuterRenderError = false;
    /***是否显示中间的小圆***/
    private boolean showCenterSmallCircleError = true;
    /***是否显示圆内渲染***/
    private boolean showInnerRenderError = true;

    /***是否显示外边框***/
    private boolean showOuterStrokePressed = true;
    /***是否加上外部渲染***/
    private boolean showOuterRenderPressed = false;
    /***是否显示中间的小圆***/
    private boolean showCenterSmallCirclePressed = true;
    /***是否显示圆内渲染***/
    private boolean showInnerRenderPressed = true;

    public int getRenderRadius() {
        return renderRadius;
    }

    public PSGesturePointConfig setRenderRadius(int renderRadius) {
        this.renderRadius = renderRadius;
        return this;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public PSGesturePointConfig setNormalColor(@ColorInt int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public int getPressColor() {
        return pressColor;
    }

    public PSGesturePointConfig setPressColor(@ColorInt int pressColor) {
        this.pressColor = pressColor;
        return this;
    }

    public int getErrorColor() {
        return errorColor;
    }

    public PSGesturePointConfig setErrorColor(@ColorInt int errorColor) {
        this.errorColor = errorColor;
        return this;
    }

    public int getOuterCircleWidth() {
        return outerCircleWidth;
    }

    public PSGesturePointConfig setOuterCircleWidth(int outerCircleWidth) {
        this.outerCircleWidth = outerCircleWidth;
        return this;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public PSGesturePointConfig setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public boolean isShowOuterStrokeNormal() {
        return showOuterStrokeNormal;
    }

    public PSGesturePointConfig setShowOuterStrokeNormal(boolean showOuterStrokeNormal) {
        this.showOuterStrokeNormal = showOuterStrokeNormal;
        return this;
    }

    public boolean isShowOuterRenderNormal() {
        return showOuterRenderNormal;
    }

    public PSGesturePointConfig setShowOuterRenderNormal(boolean showOuterRenderNormal) {
        this.showOuterRenderNormal = showOuterRenderNormal;
        return this;
    }

    public boolean isShowCenterSmallCircleNormal() {
        return showCenterSmallCircleNormal;
    }

    public PSGesturePointConfig setShowCenterSmallCircleNormal(boolean showCenterSmallCircleNormal) {
        this.showCenterSmallCircleNormal = showCenterSmallCircleNormal;
        return this;
    }

    public boolean isShowInnerRenderNormal() {
        return showInnerRenderNormal;
    }

    public PSGesturePointConfig setShowInnerRenderNormal(boolean showInnerRenderNormal) {
        this.showInnerRenderNormal = showInnerRenderNormal;
        return this;
    }

    public boolean isShowOuterStrokeError() {
        return showOuterStrokeError;
    }

    public PSGesturePointConfig setShowOuterStrokeError(boolean showOuterStrokeError) {
        this.showOuterStrokeError = showOuterStrokeError;
        return this;
    }

    public boolean isShowOuterRenderError() {
        return showOuterRenderError;
    }

    public PSGesturePointConfig setShowOuterRenderError(boolean showOuterRenderError) {
        this.showOuterRenderError = showOuterRenderError;
        return this;
    }

    public boolean isShowCenterSmallCircleError() {
        return showCenterSmallCircleError;
    }

    public PSGesturePointConfig setShowCenterSmallCircleError(boolean showCenterSmallCircleError) {
        this.showCenterSmallCircleError = showCenterSmallCircleError;
        return this;
    }

    public boolean isShowInnerRenderError() {
        return showInnerRenderError;
    }

    public PSGesturePointConfig setShowInnerRenderError(boolean showInnerRenderError) {
        this.showInnerRenderError = showInnerRenderError;
        return this;
    }

    public boolean isShowOuterStrokePressed() {
        return showOuterStrokePressed;
    }

    public PSGesturePointConfig setShowOuterStrokePressed(boolean showOuterStrokePressed) {
        this.showOuterStrokePressed = showOuterStrokePressed;
        return this;
    }

    public boolean isShowOuterRenderPressed() {
        return showOuterRenderPressed;
    }

    public PSGesturePointConfig setShowOuterRenderPressed(boolean showOuterRenderPressed) {
        this.showOuterRenderPressed = showOuterRenderPressed;
        return this;
    }

    public boolean isShowCenterSmallCirclePressed() {
        return showCenterSmallCirclePressed;
    }

    public PSGesturePointConfig setShowCenterSmallCirclePressed(boolean showCenterSmallCirclePressed) {
        this.showCenterSmallCirclePressed = showCenterSmallCirclePressed;
        return this;
    }

    public boolean isShowInnerRenderPressed() {
        return showInnerRenderPressed;
    }

    public PSGesturePointConfig setShowInnerRenderPressed(boolean showInnerRenderPressed) {
        this.showInnerRenderPressed = showInnerRenderPressed;
        return this;
    }
}
