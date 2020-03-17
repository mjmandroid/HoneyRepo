package com.beautystudiocn.allsale.vendor.materialcalendarview;

/**
 * Decorate Day views with drawables and text manipulation
 */
public interface DayViewDecorator {

    /**
     * Determine if a specific day should be decorated
     *
     * @param day {@linkplain CalendarDay} to possibly decorate
     * @return true if this decorator should be applied to the provided day
     */
    boolean shouldDecorate(CalendarDay day);

    /**
     * Set decoration options onto a facade to be applied to all relevant days
     *
     * @param view View to decorate
     */
    void decorate(DayViewFacade view);

    /**
     * Set decoration options onto a facade to be applied to custom relevant days
     *
     * @param view View to decorate
     */
    void decorate(DayViewFacade view, CalendarDay day);


    //只设置"需要描述"标识，不创建span
    void setDecorated(DayViewFacade view, boolean value);

}
