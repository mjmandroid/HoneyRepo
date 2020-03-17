package com.beautystudiocn.allsale.widget.pulltorefresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import com.beautystudiocn.allsale.view.ViewHolder;
import com.beautystudiocn.allsale.view.ViewHolder;

/**
 * @author: wujianghua
 * @Filename:
 * @Description:    自定义下拉刷新、上拉加载 RecyclerView
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2017/4/14 10:53
 */
public abstract class PullToRefreshRecyclerView<T> extends PullToRefreshBaseView implements PullToRefreshBaseView.OnPullBothListener {

    private static final String TAG = "tuandai";
    private final String TIPS_LOAD_DATA = "加载中…";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PTRRecyclerViewDecoration myDecoration;
    private List<View> mHeaderViewList;
    private List<View> mFooterViewList;
    protected CommonBaseAdapter<T> commonBaseAdapter;

    public int loadRefreshId;       //下拉刷新TaskId（可为空）
    public int loadMoreTaskId;      //上拉加载TaskId（可为空）

    private OnPullListActionListener<T> mOnPullListActionListener;
    private OnLastPageHintListener mOnLastPageHintListener;

    public List<T> mList = new ArrayList<>();
    public int mTotalCount;
    public int mPageIndex;

    @Override
    public View onInitContent() {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutParams(new RecyclerView.LayoutParams(-1, -1));
        return mRecyclerView;
    }

    public PullToRefreshRecyclerView(Context context, OnPullListActionListener<T> mPullListActionListener) {
        super(context);
        mOnPullListActionListener = mPullListActionListener;
        initView();
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PullToRefreshRecyclerView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        setDefaultLoadingHeaderView();
        setDefaultLoadingFooterView();
        setOnRefreshListener(this);

        mLinearLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);    //确定每个item高度相同，提高性能
        mRecyclerView.setAdapter(new EmptyRecyclerViewAdapter(getContext()));
    }


    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onPullDownToRefresh() {
        loadRefreshData(loadRefreshId, false);
    }

    @Override
    public void onPullUpToRefresh() {
        if (mPageIndex <= mTotalCount) {
            loadMoreData(loadMoreTaskId, false);
        } else {
            if (mOnLastPageHintListener != null) {
                mOnLastPageHintListener.onLastPageHint();
            }

            onRefreshComplete();
            if (mOnPullListActionListener != null) {
                mOnPullListActionListener.onRefreshComplete();
            }
        }
    }

    /**
     * 下拉刷新加载数据
     */
    public void loadRefreshData(int taskId, boolean isShowTops) {
        String tips = isShowTops ? TIPS_LOAD_DATA : "";
        mPageIndex = 1;

        if (mOnPullListActionListener != null) {
            mOnPullListActionListener.loadData(getId(), taskId, mPageIndex, tips);
        }
    }
    /**
     * 上拉刷新加载更多数据
     */
    public void loadMoreData(int taskId, boolean isShowTops) {
        String tips = isShowTops ? TIPS_LOAD_DATA : "";

        if (mOnPullListActionListener != null) {
            mOnPullListActionListener.loadData(getId(), taskId, mPageIndex, tips);
        }
    }


    public void setDivider(int padding, int divider) {
        if (padding > 0 && divider >= 0) {
            Drawable _divider = divider != 0 ? getResources().getDrawable(divider) : null;
            myDecoration = new PTRRecyclerViewDecoration(getContext(), PTRRecyclerViewDecoration.VERTICAL_LIST, _divider, (int) getResources().getDimension(padding));
            mRecyclerView.addItemDecoration(myDecoration);

            if (mHeaderViewList != null) {
                myDecoration.hadHeaderCount = mHeaderViewList.size();
            }
        }
    }

    public void setDivider(RecyclerView.ItemDecoration decor) {
        if (mRecyclerView != null && decor != null) {
            mRecyclerView.addItemDecoration(decor);

            if (decor instanceof PTRRecyclerViewDecoration) {
                myDecoration = (PTRRecyclerViewDecoration) decor;
                if (mHeaderViewList != null) {
                    myDecoration.hadHeaderCount = mHeaderViewList.size();
                }
            }
        }
    }

    public void addHeaderView(View headerView) {
        if (mHeaderViewList == null){
            mHeaderViewList = new ArrayList<>();
        }
        mHeaderViewList.add(headerView);
        if(myDecoration != null) {
            myDecoration.hadHeaderCount = mHeaderViewList.size();
        }
    }
    public void addOtherView(View view) {
        addHeaderView(view);
    }

    public void addFooterView(View footerView) {
        if (mFooterViewList == null){
            mFooterViewList = new ArrayList<>();
        }
        mFooterViewList.add(footerView);
        if(myDecoration != null) {
            myDecoration.hadFooterCount = mFooterViewList.size();
        }

        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter != null && !(adapter instanceof EmptyRecyclerViewAdapter)) {
            if (adapter instanceof HeaderAndFooterWrapper) {
                ((HeaderAndFooterWrapper) adapter).addFooterView(footerView);
                adapter.notifyDataSetChanged();
            }else {
                HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
                headerAndFooterWrapper.addFooterView(footerView);
                mRecyclerView.setAdapter(headerAndFooterWrapper);
            }
        }

    }
    public void removeFooterView(View footerView) {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter != null && (adapter instanceof HeaderAndFooterWrapper)) {
            ((HeaderAndFooterWrapper) adapter).removeFooterView(footerView);

        }
        if (mFooterViewList != null && mFooterViewList.indexOf(footerView) != -1) {
            mFooterViewList.remove(footerView);
        }
    }

    public void scrollToTop() {
        mLinearLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    /* *********************************************************************************************************
     *  MyPullToRefreshListView 中弃用方法
     * *********************************************************************************************************
     */
    @Deprecated
    public void setBackground(int resid) {
    }

    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }
    public void setPageIndex(int page) {
        mPageIndex = page;
    }
    public List<T> getList(){
        return mList;
    }

    public void notifyDataSetChanged(){
        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 显示数据
     */
    public void showAllData(List<T> list, int itemLayoutId) {
        if (commonBaseAdapter == null) {
            commonBaseAdapter = new MyListAdapter(getContext(), list, itemLayoutId);
            mRecyclerView.setAdapter(getWrappedListAdapter(commonBaseAdapter));

        } else {
            getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 包装adapter，增加HeaderView，FooterView
     */
    protected RecyclerView.Adapter getWrappedListAdapter(RecyclerView.Adapter adapter) {

        if ((mHeaderViewList != null && mHeaderViewList.size() != 0) || (mFooterViewList != null && mFooterViewList.size() != 0)) {
            HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
            //增加HeaderView
            if (mHeaderViewList != null && mHeaderViewList.size() != 0) {
                headerAndFooterWrapper.addHeaderView(mHeaderViewList);
            }
            //增加FooterView
            if (mFooterViewList != null && mFooterViewList.size() != 0) {
                headerAndFooterWrapper.addFooterView(mFooterViewList);
            }
            return headerAndFooterWrapper;
        }
        return adapter;
    }


    /**
     * 封装RecyclerView.Adapter，兼容ViewHolder
     */
    private class MyListAdapter extends CommonBaseAdapter<T> {
        public MyListAdapter(Context context, List<T> mData, int itemLayoutId) {
            super(context, mData, itemLayoutId);
        }

        @Override
        protected void onItemClick(View itemView, int position) {
            if (position >= 0 && mList.size() > 0) {
                T item = mList.get(position);
                if (mOnPullListActionListener != null && item != null) {
          /*mOnPullListActionListener.clickItem(getId(), item, position);*/

                    //+numHeaderView，兼容旧版本
                    int numHeaderView = mHeaderViewList != null ? mHeaderViewList.size() : 0;
                    mOnPullListActionListener.clickItem(getId(), item, position + numHeaderView);
                }
            }
        }

        @Override
        protected void convert(ViewHolder holder, T item, List<T> list, int position) {
            if (mOnPullListActionListener != null && item != null) {
                mOnPullListActionListener.createListItem(getId(), holder, item, list, position);
            }
        }
    }

    public void setOnPullListActionListener(OnPullListActionListener mPullListActionListener) {
        mOnPullListActionListener = mPullListActionListener;
    }

    public void setOnLastPageHint(OnLastPageHintListener mLastPageHintListener) {
        mOnLastPageHintListener = mLastPageHintListener;
    }
}
