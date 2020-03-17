package com.beautystudiocn.allsale.vendor.materialcalendarview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.beautystudiocn.allsale.vendor.materialcalendarview.format.DayFormatter;
import com.beautystudiocn.allsale.vendor.materialcalendarview.format.WeekDayFormatter;
import com.beautystudiocn.allsale.vendor.materialcalendarview.format.DayFormatter;
import com.beautystudiocn.allsale.vendor.materialcalendarview.format.WeekDayFormatter;

import static com.beautystudiocn.allsale.vendor.materialcalendarview.MaterialCalendarView.SHOW_DEFAULTS;
import static com.beautystudiocn.allsale.vendor.materialcalendarview.MaterialCalendarView.showOtherMonths;
import static java.util.Calendar.DATE;

abstract class CalendarPagerView extends ViewGroup implements View.OnClickListener {

    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 6;
    protected static final int DAY_NAMES_ROW = 1;
    private RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

    private static final Calendar tempWorkingCalendar = CalendarUtils.getInstance();
    private final ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private final ArrayList<DecoratorResult> decoratorResults = new ArrayList<>();
    @MaterialCalendarView.ShowOtherDates
    protected int showOtherDates = MaterialCalendarView.SHOW_DEFAULTS;
    private MaterialCalendarView mcv;
    private CalendarDay firstViewDay;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;
    private int firstDayOfWeek;

    private final Collection<DayView> dayViews = new ArrayList<>();

    private boolean isShowWeek = false;
    private boolean isSignToday = true;

    private Calendar today;
    private int todayYear;
    private int todayMonth;
    private int todayDate;
    public CalendarPagerView(@NonNull MaterialCalendarView view,
                             CalendarDay firstViewDay,
                             int firstDayOfWeek,
                             boolean isShowWeek,
                             boolean isSignToday) {
        super(view.getContext());
        this.mcv = view;
        this.firstViewDay = firstViewDay;
        this.firstDayOfWeek = firstDayOfWeek;
        this.isShowWeek = isShowWeek;
        this.isSignToday = isSignToday;
        today = Calendar.getInstance();
        todayYear = today.get(Calendar.YEAR);
        todayMonth = today.get(Calendar.MONTH);
        todayDate = today.get(Calendar.DATE);

        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setClipChildren(false);
        setClipToPadding(false);

        if(isShowWeek){
            buildWeekDays(resetAndGetWorkingCalendar());
        }

        buildDayViews(dayViews, resetAndGetWorkingCalendar());
    }

    private void buildWeekDays(Calendar calendar) {
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            WeekDayView weekDayView = new WeekDayView(getContext(), CalendarUtils.getDayOfWeek(calendar));
            weekDayViews.add(weekDayView);
            addView(weekDayView);
            calendar.add(DATE, 1);
        }
    }

    protected void addDayView(Collection<DayView> dayViews, Calendar calendar) {
        CalendarDay day = CalendarDay.from(calendar);
        DayView dayView = new DayView(getContext(), day);
        dayView.setOnClickListener(this);
        dayViews.add(dayView);
        //加一层父容器
        RelativeLayout rly = new RelativeLayout(getContext());
        dayView.setGravity(Gravity.CENTER);
        rly.addView(dayView, lp);
        addView(rly, new LayoutParams());
        calendar.add(DATE, 1);
    }

    @SuppressLint("WrongConstant")
    protected Calendar resetAndGetWorkingCalendar() {
        getFirstViewDay().copyTo(tempWorkingCalendar);
        //noinspection ResourceType
        tempWorkingCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        int delta = getFirstDayOfWeek() - dow;
        //If the delta is positive, we want to remove a week
        boolean removeRow = MaterialCalendarView.showOtherMonths(showOtherDates) ? delta >= 0 : delta > 0;
        if (removeRow) {
            delta -= DEFAULT_DAYS_IN_WEEK;
        }
        tempWorkingCalendar.add(DATE, delta);
        return tempWorkingCalendar;
    }

    protected int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    protected abstract void buildDayViews(Collection<DayView> dayViews, Calendar calendar);

    protected abstract boolean isDayEnabled(CalendarDay day);

    void setDayViewDecorators(List<DecoratorResult> results) {
        this.decoratorResults.clear();
        if (results != null) {
            this.decoratorResults.addAll(results);
        }
        invalidateDecorators();
    }

    public void setWeekDayTextAppearance(int taId) {
        for (WeekDayView weekDayView : weekDayViews) {
            weekDayView.setTextAppearance(getContext(), taId);
        }
    }

    public void setDateTextAppearance(int taId) {
        for (DayView dayView : dayViews) {
            dayView.setTextAppearance(getContext(), taId);

            //标记今天颜色
            if(isSignToday && isToday(dayView.getDate().getCalendar())){
                dayView.setTextColor(0xfff78000);
            }
        }
    }
    private boolean isToday(Calendar calendar){
        if(calendar.get(Calendar.YEAR) == todayYear
                && calendar.get(Calendar.MONTH) == todayMonth
                && calendar.get(Calendar.DATE) == todayDate){
            return true;
        }
        return false;
    }

    public void setShowOtherDates(@MaterialCalendarView.ShowOtherDates int showFlags) {
        this.showOtherDates = showFlags;
        updateUi();
    }

    public void setSelectionEnabled(boolean selectionEnabled) {
        for (DayView dayView : dayViews) {
            dayView.setOnClickListener(selectionEnabled ? this : null);
            dayView.setClickable(selectionEnabled);
        }
    }

    public void setSelectionColor(int color) {
        for (DayView dayView : dayViews) {
            dayView.setSelectionColor(color);
        }
    }

    public void setSelectionPadding(int padding){
        for (DayView dayView : dayViews) {
            dayView.setPadding(padding,padding,padding,padding);
        }
    }
    public void setOnDraw(boolean onDraw) {
        for (DayView dayView : dayViews) {
            dayView.setOnDraw(onDraw);
        }
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        for (WeekDayView dayView : weekDayViews) {
            dayView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        for (DayView dayView : dayViews) {
            dayView.setDayFormatter(formatter);
        }
    }

    public void setMinimumDate(CalendarDay minDate) {
        this.minDate = minDate;
        updateUi();
    }

    public void setMaximumDate(CalendarDay maxDate) {
        this.maxDate = maxDate;
        updateUi();
    }

    public void setSelectedDates(Collection<CalendarDay> dates) {
        for (DayView dayView : dayViews) {
            CalendarDay day = dayView.getDate();
            dayView.setChecked(dates != null && dates.contains(day));
        }
        postInvalidate();
    }

    //设置指定日期动画
    public void setDateAnimation(CalendarDay calendarDay, int resId, int taId){
        for (DayView dayView : dayViews) {
            CalendarDay day = dayView.getDate();
            if(day.equals(calendarDay)){
                dayView.setDateAnimation(resId, taId);
                break;
            }
        }
    }

    //设置指定日期动画
    public void setDateAnimation(CalendarDay calendarDay, int resId, int taId,ValueAnimator animation) {
        for (DayView dayView : dayViews) {
            CalendarDay day = dayView.getDate();
            if(day.equals(calendarDay)) {
                dayView.setDateAnimation(resId, taId,animation);
                break;
            }
        }
    }

    ///设置指定日期缩放
    public void setDateScale(CalendarDay calendarDay, int resId, int taId,float scaleValue) {
        for (DayView dayView : dayViews) {
            CalendarDay day = dayView.getDate();
            if(day.equals(calendarDay)) {
                dayView.setDateScale(resId, taId,scaleValue);
                break;
            }
        }
    }

    protected void updateUi() {
        for (DayView dayView : dayViews) {
            CalendarDay day = dayView.getDate();
            dayView.setupSelection(
                    showOtherDates, day.isInRange(minDate, maxDate), isDayEnabled(day));
        }
        postInvalidate();
    }

    protected void invalidateDecorators() {
        final DayViewFacade facadeAccumulator = new DayViewFacade();
        for (DayView dayView : dayViews) {
            facadeAccumulator.reset();
            for (DecoratorResult result : decoratorResults) {
                if (result.decorator.shouldDecorate(dayView.getDate())) {
                    //根据日期类型，添加span
                    result.result.reset();
                    result.decorator.decorate(result.result, dayView.getDate());

                    result.result.applyTo(facadeAccumulator);
                }
            }
            dayView.applyFacade(facadeAccumulator);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof DayView) {
            final DayView dayView = (DayView) v;
            mcv.onDateClicked(dayView);
        }
    }

    /*
     * Custom ViewGroup Code
     */

    /**
     * {@inheritDoc}
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //We expect to be somewhere inside a MaterialCalendarView, which should measure EXACTLY
        if (specHeightMode == MeasureSpec.UNSPECIFIED || specWidthMode == MeasureSpec.UNSPECIFIED) {
            throw new IllegalStateException("CalendarPagerView should never be left to decide it's size");
        }

        //The spec width should be a correct multiple
        final int measureTileWidth = specWidthSize / DEFAULT_DAYS_IN_WEEK;
        final int measureTileHeight = specHeightSize / getRows();

        //Just use the spec sizes
        setMeasuredDimension(specWidthSize, specHeightSize);

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileWidth,
                    MeasureSpec.EXACTLY
            );

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileHeight,
                    MeasureSpec.EXACTLY
            );

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    /**
     * Return the number of rows to display per page
     * @return
     */
    protected abstract int getRows();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        final int parentLeft = 0;

        int childTop = 0;
        int childLeft = parentLeft;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childLeft += width;

            //We should warp every so many children
            if (i % DEFAULT_DAYS_IN_WEEK == (DEFAULT_DAYS_IN_WEEK - 1)) {
                childLeft = parentLeft;
                childTop += height;
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams();
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams();
    }


    @Override
    public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(CalendarPagerView.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CalendarPagerView.class.getName());
    }

    protected CalendarDay getFirstViewDay() {
        return firstViewDay;
    }

    /**
     * Simple layout params class for MonthView, since every child is the same size
     */
    protected static class LayoutParams extends MarginLayoutParams {

        /**
         * {@inheritDoc}
         */
        public LayoutParams() {
            super(WRAP_CONTENT, WRAP_CONTENT);
        }
    }
}
