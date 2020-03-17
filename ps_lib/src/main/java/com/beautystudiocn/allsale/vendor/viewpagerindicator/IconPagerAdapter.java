package com.beautystudiocn.allsale.vendor.viewpagerindicator;

public interface IconPagerAdapter {
    /**
     * Get icon representing the page at {@code index} in the adapter.
     */
    int getIconResId(int index);

    // From PagerAdapter - getCount()
    //rename to getPagerCount()
    int getPagerCount();
}
