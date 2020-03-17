package com.beautystudiocn.allsale.vendor.materialcalendarview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.beautystudiocn.allsale.util.UIUtil;
import com.beautystudiocn.allsale.vendor.materialcalendarview.format.DayFormatter;
import com.beautystudiocn.allsale.vendor.materialcalendarview.format.DayFormatter;

import static com.beautystudiocn.allsale.vendor.materialcalendarview.MaterialCalendarView.showDecoratedDisabled;
import static com.beautystudiocn.allsale.vendor.materialcalendarview.MaterialCalendarView.showOtherMonths;
import static com.beautystudiocn.allsale.vendor.materialcalendarview.MaterialCalendarView.showOutOfRange;

/**
 * Display one day of a {@linkplain MaterialCalendarView}
 */
@SuppressLint("ViewConstructor")
class DayView extends android.support.v7.widget.AppCompatCheckedTextView {

    private CalendarDay date;
    private int selectionColor = Color.GRAY;

    private final int fadeTime;
    private Drawable customBackground = null;
    private Drawable selectionDrawable;
    private Drawable mCircleDrawable;
    private DayFormatter formatter = DayFormatter.DEFAULT;

    private boolean isInRange = true;
    private boolean isInMonth = true;
    private boolean isDecoratedDisabled = false;
    @MaterialCalendarView.ShowOtherDates
    private int showOtherDates = MaterialCalendarView.SHOW_DEFAULTS;

    //背景是否闪烁动画
    private boolean isNeedAnimation = false;
    private int customSelectColor = -1;

    private ObjectAnimator colorAnim;
    private Animation frontAnim;
    private boolean onDraw = true;

    public DayView(Context context, CalendarDay day) {
        super(context);

        fadeTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        setSelectionColor(this.selectionColor);

        setGravity(Gravity.CENTER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }

        setDay(day);
    }

    public void setDay(CalendarDay date) {
        this.date = date;
        setText(getLabel());
    }

    /**
     * Set the new label formatter and reformat the current label. This preserves current spans.
     *
     * @param formatter new label formatter
     */
    public void setDayFormatter(DayFormatter formatter) {
        this.formatter = formatter == null ? DayFormatter.DEFAULT : formatter;
        CharSequence currentLabel = getText();
        Object[] spans = null;
        if (currentLabel instanceof Spanned) {
            spans = ((Spanned) currentLabel).getSpans(0, currentLabel.length(), Object.class);
        }
        SpannableString newLabel = new SpannableString(getLabel());
        if (spans != null) {
            for (Object span : spans) {
                newLabel.setSpan(span, 0, newLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        setText(newLabel);
    }

    @NonNull
    public String getLabel() {
        return formatter.format(date);
    }

    public void setSelectionColor(int color) {
        this.selectionColor = color;
        regenerateBackground();
    }

    public void setOnDraw(boolean onDraw) {
        this.onDraw = onDraw;
    }

    /**
     * @param drawable custom selection drawable
     */
    public void setSelectionDrawable(Drawable drawable, boolean isNeedAnimation) {
        this.isNeedAnimation = isNeedAnimation;
        if (drawable == null) {
            this.selectionDrawable = null;
            //设定色块圆圈的值
            if (isNeedAnimation) {
                offpx1 = UIUtil.dp2px(1);
            } else {
                offpx1 = UIUtil.dp2px(1);
            }
        } else {
            if (drawable.getConstantState() != null) {
                this.selectionDrawable = drawable.getConstantState().newDrawable(getResources());
            }
        }
        regenerateBackground();
    }

    /**
     * @param drawable background to draw behind everything else
     */
    public void setCustomBackground(Drawable drawable) {
        if (drawable == null) {
            this.customBackground = null;
        } else {
            if (drawable.getConstantState() != null) {
                this.customBackground = drawable.getConstantState().newDrawable(getResources());
            }
        }
        invalidate();
    }

    public CalendarDay getDate() {
        return date;
    }

    private void setEnabled() {
        boolean enabled = isInMonth && isInRange && !isDecoratedDisabled;
        super.setEnabled(isInRange && !isDecoratedDisabled);

        boolean showOtherMonths = showOtherMonths(showOtherDates);
        boolean showOutOfRange = showOutOfRange(showOtherDates) || showOtherMonths;
        boolean showDecoratedDisabled = showDecoratedDisabled(showOtherDates);

        boolean shouldBeVisible = enabled;

        if (!isInMonth && showOtherMonths) {
            shouldBeVisible = true;
        }

        if (!isInRange && showOutOfRange) {
            shouldBeVisible |= isInMonth;
        }

        if (isDecoratedDisabled && showDecoratedDisabled) {
            shouldBeVisible |= isInMonth && isInRange;
        }

        if (!isInMonth && shouldBeVisible) {
            setTextColor(getTextColors().getColorForState(
                    new int[]{-android.R.attr.state_enabled}, Color.GRAY));
        }
        setAimation(shouldBeVisible);
        setVisibility(shouldBeVisible ? View.VISIBLE : View.INVISIBLE);
    }

    private void setAimation(boolean visible) {
        if (visible) {
            if (frontAnim != null) {
                frontAnim.start();
            }
            if (colorAnim != null) {
                colorAnim.start();
            }
            if (this.getParent() != null) {
                ((RelativeLayout) this.getParent()).setVisibility(View.VISIBLE);
            }
        } else {
            if (frontAnim != null) {
                frontAnim.cancel();
            }
            if (colorAnim != null) {
                colorAnim.cancel();
            }
            if (this.getParent() != null) {
                ((RelativeLayout) this.getParent()).setVisibility(View.INVISIBLE);
            }
        }
    }

    //个别日期动画
    protected void setDateAnimation(int resId, int taId) {

        this.setTextAppearance(getContext(), taId);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        try {
            ImageView iv = new ImageView(getContext());
            iv.setBackgroundResource(resId);
            ((RelativeLayout) this.getParent()).addView(iv, lp2);

            AnimationDrawable animDrawable = (AnimationDrawable) iv.getBackground();
            animDrawable.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    protected void setupSelection(@MaterialCalendarView.ShowOtherDates int showOtherDates, boolean inRange, boolean inMonth) {
        this.showOtherDates = showOtherDates;
        this.isInMonth = inMonth;
        this.isInRange = inRange;
        setEnabled();
    }

    private final Rect tempRect = new Rect();

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if (onDraw) {
            if (customBackground != null) {
                customBackground.setBounds(tempRect);
                customBackground.setState(getDrawableState());
                customBackground.draw(canvas);
            }

            mCircleDrawable.setBounds(tempRect);
            mCircleDrawable.setAlpha(255);          //解决背景不规则时，闪烁问题

        }
        super.onDraw(canvas);

    }

    private void regenerateBackground() {
        if (selectionDrawable != null) {
            setBackgroundDrawable(selectionDrawable);
        } else {

            if (isNeedAnimation && customSelectColor != -1) {
                mCircleDrawable = generateBackground(customSelectColor, tempRect);
                setBackgroundDrawable(mCircleDrawable);
                mCircleDrawable.setAlpha(0);        //解决背景不规则时，闪烁问题

                colorAnim = ObjectAnimator.ofInt(mCircleDrawable, "alpha", 255, 0);
                colorAnim.setDuration(1000);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.setRepeatCount(-1);
                colorAnim.start();
            } else {
                mCircleDrawable = generateBackground(selectionColor, fadeTime, tempRect);
                setBackgroundDrawable(mCircleDrawable);
                mCircleDrawable.setAlpha(0);        //解决背景不规则时，闪烁问题

            }
        }
    }

    private static Drawable generateBackground(int color, int fadeTime, Rect bounds) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.setExitFadeDuration(fadeTime);
        drawable.addState(new int[]{android.R.attr.state_checked}, generateCircleDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.addState(new int[]{android.R.attr.state_pressed}, generateRippleDrawable(color, bounds));
        } else {
            drawable.addState(new int[]{android.R.attr.state_pressed}, generateCircleDrawable(color));
        }

        drawable.addState(new int[]{}, generateCircleDrawable(Color.TRANSPARENT));

        return drawable;
    }

    private static Drawable generateBackground(int color, Rect bounds) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{}, generateCircleDrawable(color));
        return drawable;
    }

    private static Drawable generateCircleDrawable(final int color) {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(color);
        return drawable;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Drawable generateRippleDrawable(final int color, Rect bounds) {
        ColorStateList list = ColorStateList.valueOf(color);
        Drawable mask = generateCircleDrawable(Color.WHITE);
        RippleDrawable rippleDrawable = new RippleDrawable(list, null, mask);
//        API 21
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            rippleDrawable.setBounds(bounds);
        }

//        API 22. Technically harmless to leave on for API 21 and 23, but not worth risking for 23+
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
            int center = (bounds.left + bounds.right) / 2;
            rippleDrawable.setHotspotBounds(center, bounds.top, center, bounds.bottom);
        }

        return rippleDrawable;
    }

    /**
     * @param facade apply the facade to us
     */
    void applyFacade(DayViewFacade facade) {
        removeOldView();
        this.isDecoratedDisabled = facade.areDaysDisabled();
        setEnabled();

        customSelectColor = facade.getCustomSelectColor();
        setFrontDrawable(facade.getFrontDrawable(), facade.getFrontDrawabelAnimation(),
                facade.getFrontDrableMarginTop());
        setCustomBackground(facade.getBackgroundDrawable(),facade.getCustomDrawabelAnimation());
        setSelectionDrawable(facade.getSelectionDrawable(), facade.isNeedAnimation);
        setBottomView(facade.getBottomView(),facade.getParams());
        if (facade.isDecorated()&& facade.getDayTextColor() != -1) {
            setTextAppearance(getContext(),facade.getDayTextColor());
        }
        // Facade has spans
        List<DayViewFacade.Span> spans = facade.getSpans();
        if (!spans.isEmpty()) {
            String label = getLabel();
            SpannableString formattedLabel = new SpannableString(getLabel());
            for (DayViewFacade.Span span : spans) {
                formattedLabel.setSpan(span.span, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            setText(formattedLabel);
        } else {
            setText(getLabel());
        }
    }


    private void  setFrontDrawable(Drawable drawable, Animation animation, int marginTop) {
        removeOldView();
        if (drawable == null) {
            return;
        }
        frontAnim = animation;
        RelativeLayout.LayoutParams lp2 = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.setMargins(0, UIUtil.dp2px(marginTop), UIUtil.dp2px(1), 0);
        try {
            ImageView iv = new ImageView(getContext());
            iv.setImageDrawable(drawable);
            ((RelativeLayout) this.getParent()).addView(iv, lp2);

            if (frontAnim != null) {
                iv.startAnimation(frontAnim);
            }
        } catch (Exception ex)  {
            ex.printStackTrace();
        }
    }


    /**
     *<br> Description: 当刷新时删除旧的View
     *<br> Author:      wujun
     *<br> Date:        2017/9/28 16:56
     */
    private void removeOldView() {
        if (this.getParent() == null) {
            return;
        }
        RelativeLayout rlLayout =(RelativeLayout) this.getParent();
        for (int i = rlLayout.getChildCount() - 1; i >= 0; i--) {
            View view = rlLayout.getChildAt(i);
            if (view instanceof TextView) {
                continue;
            }
            Animation animation1 = view.getAnimation();
            if (animation1 != null) {
                animation1.cancel();
                view.setAnimation(null);
            }
            rlLayout.removeView(view);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        calculateBounds(right-left, bottom - top);
        regenerateBackground();
    }



    private  int offpx1 = UIUtil.dp2px(8);
    private int offpx2 = UIUtil.dp2px(12);
    private int offpx3 = UIUtil.dp2px(4);
    private int offpx4 = UIUtil.dp2px(0.5f);

    private void calculateBounds(int width, int height) {

        final int radius = Math.min(height, width);
        // Lollipop platform bug. Rect offset needs to be divided by 4 instead of 2
        final int offsetDivisor = Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP ? 4 : 2;
        final int offset = Math.abs(height - width) / offsetDivisor;
        //缩小背景图
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            if (width >= height) {
                tempRect.set(offset+offpx4 , 0 + offpx4 , radius + offset - offpx4 ,
                        height - offpx4);

            } else {
                tempRect.set(0 + offpx1, offset , width - offpx1, radius + offset - offpx1 *2);
            }
        } else {
            if (width >= height) {
                tempRect.set(offset + offpx1, 0 + offpx1, radius + offset - offpx1, height - offpx1);

            } else {
                tempRect.set(0 + offpx1, offset + offpx1, width - offpx1, radius + offset - offpx1);
            }
        }

    }

    /**
     * @param drawable background to draw behind everything else
     */
    public void setCustomBackground(Drawable drawable, ValueAnimator customBackAnimator) {
        if (drawable == null) {
            return;
        }
        if (customBackAnimator  == null) {
            setCustomBackground(drawable);
        } else {

            RelativeLayout.LayoutParams lp = new RelativeLayout.
                    LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);

            try {

                final ImageView iv = new ImageView(getContext());
                iv.setImageDrawable(drawable);
                ((RelativeLayout) this.getParent()).addView(iv,0,lp);
                iv.setVisibility(GONE);
                if (customBackAnimator != null) {
                    customBackAnimator
                            .addListener(new Animator.AnimatorListener() {
                                             @Override
                                             public void onAnimationStart(Animator animation) {
                                                 iv.setVisibility(VISIBLE);
                                             }

                                             @Override
                                             public void onAnimationEnd(Animator animation) {

                                             }

                                             @Override
                                             public void onAnimationCancel(Animator animation) {

                                             }

                                             @Override
                                             public void onAnimationRepeat(Animator animation) {

                                             }
                                         }

                            );

                    customBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float  animValue= (float)animation.getAnimatedValue();
                            iv.setScaleX(animValue);
                            iv.setScaleY(animValue);
                        }
                    });
                    customBackAnimator.start();

                }

            }  catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *<br> Description: 设置底部View
     *<br> Author:      wujun
     *<br> Date:        2017/9/28 16:49
     */
    private void setBottomView(View view, ViewGroup.LayoutParams params) {
        if (view == null) {
            return;
        }
        if (params ==  null) {
            RelativeLayout.LayoutParams layoutParams;
            ((RelativeLayout)this.getParent()).addView(view);
        } else  {
            ((RelativeLayout)this.getParent()).addView(view,params);
        }

//        RelativeLayout.LayoutParams lp2 = null;
//        lp2 =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
//
//        if (view instanceof ImageView) {
//            if (signType == 1) {
//                lp2.setMargins(0, 0, 0, UIUtil.dp2px(4));
//            } else {
//                lp2.setMargins(0, UIUtil.dp2px(25), 0, 0);
//            }
//        }
//        try {
//            ((RelativeLayout)this.getParent()).addView(view,lp2);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    /**
     *<br> Description: 个别日期动画
     *<br> Author:      wujun
     *<br> Date:        2017/9/28 16:49
     */
    protected void setDateAnimation(int resId, int taId,ValueAnimator animation) {
        this.setTextAppearance(getContext(), taId);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp2.setMargins(0, 0, 0, UIUtil.dp2px(2));
        try {
            final ImageView iv = new ImageView(getContext());
            Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
            iv.setImageDrawable(drawable);

            ((RelativeLayout)this.getParent()).addView(iv, lp2);
            if (animation != null) {
                animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float scale = (float)animation.getAnimatedValue();
                        float dayScale = scale * 0.2f + 1f;
                        iv.setScaleY(scale);
                        iv.setScaleX(scale);
                        DayView.this.setScaleX(dayScale);
                        DayView.this.setScaleY(dayScale);
                    }
                });
                animation.start();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *<br> Description: 个别日期缩放
     *<br> Author:      wujun
     *<br> Date:        2017/9/28 16:48
     */
    protected void setDateScale(int resId, int taId,float scaleVale) {
        this.setTextAppearance(getContext(), taId);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp2.setMargins(0, 0, 0, UIUtil.dp2px(2));
        try {
            final ImageView iv = new ImageView(getContext());
            Drawable drawable = ContextCompat.getDrawable(getContext(),resId);
            iv.setImageDrawable(drawable);
            ((RelativeLayout) this.getParent()).addView(iv, lp2);
            iv.setScaleX(scaleVale);
            iv.setScaleY(scaleVale);
            DayView.this.setScaleX(scaleVale);
            DayView.this.setScaleY(scaleVale);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
