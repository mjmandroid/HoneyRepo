package com.beautystudiocn.allsale.vendor.materialcalendarview;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction layer to help in decorating Day views
 */
public class DayViewFacade {

    private boolean isDecorated;

    private Drawable backgroundDrawable = null;
    private Drawable selectionDrawable = null;
    private Drawable frontDrawable = null;
    private int customSelectColor = -1;

    private Animation frontDrawabelAnimation = null;
    private ValueAnimator customDrawabelAnimation = null;
    private View mBottomView;
    private int dayTextColor= -1;
    private ViewGroup.LayoutParams params ;
    private int frontDrableMarginTop = 0 ;
    private final LinkedList<Span> spans = new LinkedList<>();
    private boolean daysDisabled = false;

    //背景是否需要动画
    public boolean isNeedAnimation = false;

    DayViewFacade() {
        isDecorated = false;
    }

    /**
     * Set a drawable to draw behind everything else
     *
     * @param drawable Drawable to draw behind everything
     */
    public void setBackgroundDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        this.backgroundDrawable = drawable;
        isDecorated = true;
    }

    /**
     * Set a custom selection drawable
     * TODO: define states that can/should be used in StateListDrawables
     *
     * @param drawable the drawable for selection
     */
    public void setSelectionDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        selectionDrawable = drawable;
        isDecorated = true;
    }

    public void setCustomSelectColor(int color) {
        if(color == -1){
            throw new IllegalArgumentException("color be invalid");
        }
        customSelectColor = color;
        isDecorated = true;
    }

    public void setFrontDrawable(@NonNull Drawable drawable, @NonNull Animation animation) {
        if(drawable == null) {
            throw new IllegalArgumentException("Front Drawable Cannot be null");
        }
        frontDrawable = drawable;
        frontDrawabelAnimation = animation;
        isDecorated = true;
    }
    public void setCustomBackGround(@NonNull Drawable drawable, @NonNull ValueAnimator animator) {
        if(drawable == null) {
            throw new IllegalArgumentException(" Drawable Cannot be null");
        }
        backgroundDrawable = drawable;
        customDrawabelAnimation = animator;
        isDecorated = true;
    }

    public void setDayTextColor(int color) {
        if(color == -1) {
            throw new IllegalArgumentException("color be invalid");
        }
        this.dayTextColor = color;
        isDecorated = true;
    }



    public void setBottomView(@NonNull View view, ViewGroup.LayoutParams params) {
        if(view == null) {
            throw new IllegalArgumentException(" view Cannot be null");
        }
        this.mBottomView = view;
        this.params = params;
        isDecorated = true;
    }

    public void setFrontDrableMarginTop(int frontDrableMarginTop) {
        this.frontDrableMarginTop = frontDrableMarginTop;
        isDecorated = true;
    }

    /**
     * Add a span to the entire text of a day
     *
     * @param span text span instance
     */
    public void addSpan(@NonNull Object span) {
        if (spans != null) {
            this.spans.add(new Span(span));
            isDecorated = true;
        }
    }

    /**
     * <p>Set days to be in a disabled state, or re-enabled.</p>
     * <p>Note, passing true here will <b>not</b> override minimum and maximum dates, if set.
     * This will only re-enable disabled dates.</p>
     *
     * @param daysDisabled true to disable days, false to re-enable days
     */
    public void setDaysDisabled(boolean daysDisabled) {
        this.daysDisabled = daysDisabled;
        this.isDecorated = true;
    }

    void reset() {
        backgroundDrawable = null;
        selectionDrawable = null;
        frontDrawable = null;
        frontDrawabelAnimation = null;
        frontDrableMarginTop = 0;
        customSelectColor = -1;
        customDrawabelAnimation = null;
        dayTextColor = -1;
        params  = null;
        mBottomView = null;
        spans.clear();
        isDecorated = false;
        daysDisabled = false;
    }

    /**
     * Apply things set this to other
     *
     * @param other facade to apply our data to
     */
    void applyTo(DayViewFacade other) {
        if (selectionDrawable != null) {
            other.setSelectionDrawable(selectionDrawable);
        }
        if (backgroundDrawable != null) {
            other.setBackgroundDrawable(backgroundDrawable);
        }
        if(frontDrawable != null) {
            other.setFrontDrawable(frontDrawable, frontDrawabelAnimation);
        }
        if (customSelectColor != -1) {
            other.setCustomSelectColor(customSelectColor);
        }
        if (customDrawabelAnimation != null && backgroundDrawable != null ) {
            other.setCustomBackGround(backgroundDrawable,customDrawabelAnimation);
        }
        if (dayTextColor!=-1) {
            other.setDayTextColor(dayTextColor);
        }

        if (mBottomView != null) {
            other.setBottomView(mBottomView,params);
        }
        other.setFrontDrableMarginTop(frontDrableMarginTop);
        other.isNeedAnimation = this.isNeedAnimation;
        other.spans.addAll(spans);
        other.isDecorated |= this.isDecorated;
        other.daysDisabled = daysDisabled;
    }

    boolean isDecorated() {
        return isDecorated;
    }

    //设置描述标识
    public void setIsDecorated(boolean isDecorated) {
        if (spans != null) {
            this.isDecorated = isDecorated;
        }
    }

    Drawable getSelectionDrawable() {
        return selectionDrawable;
    }

    Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    Drawable getFrontDrawable() {
        return frontDrawable;
    }

    Animation getFrontDrawabelAnimation() {
        return frontDrawabelAnimation;
    }

    public int getFrontDrableMarginTop() {
        return frontDrableMarginTop;
    }

    int getCustomSelectColor() {
        return customSelectColor;
    }

    List<Span> getSpans() {
        return Collections.unmodifiableList(spans);
    }

    public ValueAnimator getCustomDrawabelAnimation() {
        return customDrawabelAnimation;
    }

    public int getDayTextColor() {
        return dayTextColor;
    }

    public ViewGroup.LayoutParams getParams() {
        return params;
    }

    public View getBottomView() {
        return mBottomView;
    }

    /**
     * Are days from this facade disabled
     *
     * @return true if disabled, false if not re-enabled
     */
    public boolean areDaysDisabled() {
        return daysDisabled;
    }

    static class Span {

        final Object span;

        public Span(Object span) {
            this.span = span;
        }
    }
}
