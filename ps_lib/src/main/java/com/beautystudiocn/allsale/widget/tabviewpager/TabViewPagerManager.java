package com.beautystudiocn.allsale.widget.tabviewpager;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.beautystudiocn.allsale.mvp.base.AbstractMvpActivity;
import com.beautystudiocn.allsale.picture.loader.Monet;
import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.mvp.base.AbstractMvpActivity;
import com.beautystudiocn.allsale.picture.loader.Monet;
import com.beautystudiocn.allsale.util.AppUtil;
import com.beautystudiocn.allsale.util.UIUtil;
import com.beautystudiocn.allsale.view.ViewHolder;
import com.beautystudiocn.allsale.view.ViewPagerFixed;
import com.beautystudiocn.allsale.widget.tabviewpager.materialtabs.MaterialTab;
import com.beautystudiocn.allsale.widget.tabviewpager.materialtabs.MaterialTabHost;
import com.beautystudiocn.allsale.widget.tabviewpager.materialtabs.MaterialTabListener;
import com.beautystudiocn.allsale.util.AppUtil;
import com.beautystudiocn.allsale.util.UIUtil;
import com.beautystudiocn.allsale.view.ViewHolder;
import com.beautystudiocn.allsale.view.ViewPagerFixed;
import com.beautystudiocn.allsale.widget.tabviewpager.materialtabs.MaterialTab;
import com.beautystudiocn.allsale.widget.tabviewpager.materialtabs.MaterialTabHost;
import com.beautystudiocn.allsale.widget.tabviewpager.materialtabs.MaterialTabListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理viewpager和TabHost
 *
 * @author yexiaochuan
 */
public class TabViewPagerManager {
    private ViewPagerFixed mViewPager;
    private FrameLayout mFlyTab;
    private MaterialTabHost mTabHost;
    private TabConfig mTabConfig;
    private PagerConfig pagerConfig;
    private ViewHolder mViewHolder;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private ArrayList<Fragment> fragments;
    private ViewPagerAdapter adapter;
    private boolean mAlphaChangeFlag = false; //viewpager滑动引起tab透明效果改变的标志
    private WeakReference<AbstractMvpActivity> mActivity;

    public TabViewPagerManager(ViewGroup root, FragmentManager manager) {
        mTabConfig = new TabConfig();
        pagerConfig = new PagerConfig();
        fragments = new ArrayList<Fragment>();
        createAndAttachToRoot(root);
        setupView(manager);
    }

    public TabViewPagerManager(ViewGroup root, FragmentManager manager, Context context) {
        this(root, manager);
        if (context instanceof AbstractMvpActivity) {
            mActivity = new WeakReference<>((AbstractMvpActivity) context);
        }
    }

    private void setupView(FragmentManager manager) {
        mFlyTab = mViewHolder.getView(R.id.rly_tab);
        mViewPager = mViewHolder.getView(R.id.viewPager);
        mTabHost = mViewHolder.getView(R.id.tabHost);
        adapter = new ViewPagerAdapter(manager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!mAlphaChangeFlag) {
                    mTabHost.setSelectedNavigationItem(position, positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(position, false);
                }
                mTabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (0 == state && mAlphaChangeFlag) {
                    mAlphaChangeFlag = false;
                }
            }
        });
    }

    private void createAndAttachToRoot(ViewGroup root) {
        View view = LayoutInflater.from(root.getContext()).inflate(R.layout.lib_tabvp_tab_viewpager_layout, root, true);
        mViewHolder = new ViewHolder(view);
    }

    public void addPage(Fragment fragment, MaterialTab tab) {
        fragments.add(fragment);
        final MaterialTabListener listener = tab.getTabListener();
        tab.setTabListener(new MaterialTabListener() {
            @Override
            public void onTabSelected(MaterialTab tab) {
                if (listener != null) {
                    listener.onTabSelected(tab);
                }
                mAlphaChangeFlag = true;
            }

            @Override
            public void onTabReselected(MaterialTab tab) {
                if (listener != null) {
                    listener.onTabReselected(tab);
                }
            }

            @Override
            public void onTabUnselected(MaterialTab tab) {
                if (listener != null) {
                    listener.onTabUnselected(tab);
                }
            }
        });
        mTabHost.addTab(tab);
        adapter.notifyDataSetChanged();
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public MaterialTab newTab() {
        return mTabHost.newTab();
    }

    public TabConfig getTabConfig() {
        return mTabConfig;
    }

    public PagerConfig getPagerConfig() {
        return pagerConfig;
    }

    /**
     * <br> Description: 获取tab管理类
     * <br> Author:      xwl
     * <br> Date:        2018/5/7 16:42
     *
     * @return tab管理类
     */
    public MaterialTabHost getMaterialTabHost() {
        return mTabHost;
    }

    /**
     * <br> Description: tab显示红点
     * <br> Author:      谢文良
     * <br> Date:        2017/8/1 10:44
     *
     * @param item       item
     * @param visibility 红点显示与否
     */
    public void setTabHintSpotVisibility(int item, int visibility) {
        mTabHost.setTabHintSpotVisibility(item, visibility);
    }

    /**
     * <br> Description: tab 换红点图片
     * <br> Author:      wujun
     * <br> Date:        2017/8/9 10:25
     *
     * @param item  item
     * @param resId 红点图片
     */

    public void setHintSpotImageAndVisibility(int item, @DrawableRes int resId, int visibility) {
        mTabHost.setHintSpotImageAndVisibility(item, resId, visibility);
    }

    public void setAlphaChangeFlag(boolean mAlphaChangeFlag) {
        this.mAlphaChangeFlag = mAlphaChangeFlag;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            if (mActivity != null) {
                AbstractMvpActivity tempActivity = mActivity.get();
                if (tempActivity != null && !tempActivity.isStateSaved() && !tempActivity.isFinishing()) {
                    if (Build.VERSION.SDK_INT >= 17 && tempActivity.isDestroyed()) {
                        return;
                    }
                }
            }
            super.finishUpdate(container);
        }
    }

    public class PagerConfig {
        public PagerConfig setOffscreenPageLimit(int limit) {
            mViewPager.setOffscreenPageLimit(limit);
            return this;
        }

        public PagerConfig setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (!mAlphaChangeFlag) {
                        mTabHost.setSelectedNavigationItem(position, positionOffset);
                    }
                    if (listener != null)
                        listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(position, false);
                    }
                    mTabHost.setSelectedNavigationItem(position);
                    if (listener != null)
                        listener.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (0 == state && mAlphaChangeFlag) {
                        mAlphaChangeFlag = false;
                    }
                    if (listener != null)
                        listener.onPageScrollStateChanged(state);
                }
            });
            return this;
        }
    }

    public class TabConfig {

        public TabConfig setTabType(String tabType) {
            mTabHost.setTabType(tabType);
            return this;
        }

        public TabConfig setClickAnim(boolean clickAnim) {
            mTabHost.setClickAnim(clickAnim);
            return this;
        }

        public TabConfig setPrimaryColor(int primaryColor) {
            mTabHost.setPrimaryColor(primaryColor);
            return this;
        }

        public TabConfig setAccentColor(int accentColor) {
            mTabHost.setAccentColor(accentColor);
            return this;
        }

        public TabConfig setVisibleLine(int visible) {
            mViewHolder.setVisibility(R.id.tab_line, visible);
            return this;
        }

        public TabConfig setBgUrl(String bgUrl) {
            Monet.get(AppUtil.getContext()).load(bgUrl).centerCrop().into((ImageView) mViewHolder.getView(R.id.iv_indextabbanck));
            return this;
        }

        public TabConfig setTabHeight(float dpValue) {
            if (mFlyTab != null) {
                ViewGroup.LayoutParams lp = mFlyTab.getLayoutParams();
                lp.height = UIUtil.dp2px(dpValue);
                mFlyTab.setLayoutParams(lp);
            }
            return this;
        }
    }

    /**
     * <br> Description: todo(重新绘画，横竖屏切换时可能用到)
     * <br> Author:     fangbingran
     * <br> Date:        2017/5/31 17:30
     */
    public void requestLayout() {
        mTabHost.requestLayout();
    }

    public void initView(@NonNull List<Fragment> fragments, @NonNull String[] tabTitles,
                         @NonNull int[] localityTabIconNormal, @NonNull int[] localityTabIconActivate,
                         String[] tabIconUrlNormal, String[] tabIconUrlActivate, String backImageUrl) {
        initView(fragments, tabTitles, localityTabIconNormal, localityTabIconActivate,
                tabIconUrlNormal, tabIconUrlActivate, backImageUrl, new int[]{-1, -1});
    }

    /**
     * <br> Description: 初始化
     * <br> Author:      谢文良
     * <br> Date:        2017/8/1 9:50
     *
     * @param fragments               fragments
     * @param tabTitles               标题
     * @param localityTabIconNormal   本地默认tab图片
     * @param localityTabIconActivate 本地激活tab图片
     * @param tabIconUrlNormal        网络默认tab图片
     * @param tabIconUrlActivate      网络激活tab图片
     * @param backImageUrl            整个tab背景图片
     * @param textColor               tab文本颜色(0为默认背景色， 1为激活背景色)
     */
    public void initView(@NonNull List<Fragment> fragments, @NonNull String[] tabTitles,
                         @NonNull int[] localityTabIconNormal, @NonNull int[] localityTabIconActivate,
                         String[] tabIconUrlNormal, String[] tabIconUrlActivate, String backImageUrl, @NonNull int[] textColor) {
        if (fragments == null || tabTitles == null || localityTabIconNormal == null
                || localityTabIconActivate == null || textColor == null) {
            throw new RuntimeException("NonNull param must not null");
        }
        if (fragments.size() != tabTitles.length || fragments.size() != localityTabIconNormal.length
                || fragments.size() != localityTabIconActivate.length) {
            throw new RuntimeException("NonNull param size must same");
        }
        if (tabIconUrlNormal != null && tabIconUrlNormal.length != fragments.size()) {
            throw new RuntimeException("if tabIconUrlNormal param not null, it size must be same of fragments param");
        }
        if (tabIconUrlActivate != null && tabIconUrlActivate.length != fragments.size()) {
            throw new RuntimeException("if tabIconUrlActivate param not null, it size must be same of fragments param");
        }
        if (textColor.length != 2) {
            throw new RuntimeException("textColor length must be 2");
        }
        getPagerConfig().setOffscreenPageLimit(fragments.size())
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        setCurrentItem(position);
                    }
                });
        if (!TextUtils.isEmpty(backImageUrl)) {
            getTabConfig()
                    .setTabType(MaterialTab.TYPE_LARGE_ICON)
                    .setClickAnim(false)
                    .setPrimaryColor(Color.parseColor("#00000000"))
                    .setAccentColor(Color.parseColor("#00000000"))
                    .setVisibleLine(View.GONE)
                    .setBgUrl(backImageUrl);
        }
        for (int i = 0; i < fragments.size(); i++) {
            String iconNormalSurly = null;
            String iconActivatedSurly = null;
            if (tabIconUrlNormal != null && tabIconUrlNormal[i] != null) {
                iconNormalSurly = tabIconUrlNormal[i];
            }
            if (tabIconUrlActivate != null && tabIconUrlActivate[i] != null) {
                iconActivatedSurly = tabIconUrlActivate[i];
            }
            MaterialTab mTab = newTab()
                    .setIcon(localityTabIconNormal[i], localityTabIconActivate[i])
                    .setIconUrl(iconNormalSurly, iconActivatedSurly)
                    .setText(tabTitles[i], textColor[0], textColor[1])
                    .setSelectorVisibility(View.GONE)
                    .setTabListener(new MaterialTabListener() {
                        @Override
                        public void onTabSelected(MaterialTab tab) {
                            mViewPager.setCurrentItem(tab.getPosition(), false);
                        }

                        @Override
                        public void onTabReselected(MaterialTab tab) {

                        }

                        @Override
                        public void onTabUnselected(MaterialTab tab) {

                        }
                    });
            addPage(fragments.get(i), mTab);
        }
    }

    /**
     * <br> Description: 主动定位Item
     * <br> Author:      谢文良
     * <br> Date:        2017/8/1 10:34
     *
     * @param position position
     */
    public void setCurrentItem(int position) {
        if (mViewPager != null) {
            setAlphaChangeFlag(true);
            mViewPager.setCurrentItem(position, false);
        }
    }
}
