package com.beautystudiocn.allsale.vendor.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.vendor.viewpagerindicator.IconPagerAdapter;


/**
 * Pager adapter backing the calendar view
 */
class MonthPagerAdapter extends CalendarPagerAdapter<MonthView> implements IconPagerAdapter {

    MonthPagerAdapter(MaterialCalendarView mcv, boolean isNeedAnimation) {
        super(mcv, isNeedAnimation);
    }

    @Override
    protected MonthView createView(int position) {
        return new MonthView(mcv, getItem(position), mcv.getFirstDayOfWeek(), mcv.getIsShowWeek(), mcv.getIsSignToday());
    }

    @Override
    protected int indexOf(MonthView view) {
        CalendarDay month = view.getMonth();
        return getRangeIndex().indexOf(month);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof MonthView;
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
        return new Monthly(min, max);
    }

    @Override
    public int getPagerCount() {
        return 3;
    }

    @Override
    public int getIconResId(int index) {
        // TODO Auto-generated method stub
        return R.drawable.lib_find_banner_selector;
    }


    public static class Monthly implements DateRangeIndex {

        private final CalendarDay min;
        private final int count;

        private SparseArrayCompat<CalendarDay> dayCache = new SparseArrayCompat<>();

        public Monthly(@NonNull CalendarDay min, @NonNull CalendarDay max) {
            this.min = CalendarDay.from(min.getYear(), min.getMonth(), 1);
            max = CalendarDay.from(max.getYear(), max.getMonth(), 1);
            this.count = indexOf(max) + 1;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public int indexOf(CalendarDay day) {
            int yDiff = day.getYear() - min.getYear();
            int mDiff = day.getMonth() - min.getMonth();

            return (yDiff * 12) + mDiff;
        }

        @Override
        public CalendarDay getItem(int position) {

            CalendarDay re = dayCache.get(position);
            if (re != null) {
                return re;
            }

            int numY = position / 12;
            int numM = position % 12;

            int year = min.getYear() + numY;
            int month = min.getMonth() + numM;
            if (month >= 12) {
                year += 1;
                month -= 12;
            }

            re = CalendarDay.from(year, month, 1);
            dayCache.put(position, re);
            return re;
        }
    }
}
