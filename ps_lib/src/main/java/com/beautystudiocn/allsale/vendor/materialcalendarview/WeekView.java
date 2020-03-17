package com.beautystudiocn.allsale.vendor.materialcalendarview;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Collection;

/**
 * Display a week of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
@Experimental
@SuppressLint("ViewConstructor")
public class WeekView extends CalendarPagerView {

    public WeekView(@NonNull MaterialCalendarView view,
                    CalendarDay firstViewDay,
                    int firstDayOfWeek,
                    boolean isShowWeek,
                    boolean isSignToday) {
        super(view, firstViewDay, firstDayOfWeek, isShowWeek, isSignToday);
    }

    @Override
    protected void buildDayViews(Collection<DayView> dayViews, Calendar calendar) {
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            addDayView(dayViews, calendar);
        }
    }

    @Override
    protected boolean isDayEnabled(CalendarDay day) {
        return true;
    }

    @Override
    protected int getRows() {
        return DAY_NAMES_ROW + 1;
    }
}
