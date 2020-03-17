package com.beautystudiocn.allsale.widget.pulltorefresh;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author: wujianghua
 * @Filename:
 * @Description:    RecyclerView的HeaderView、FooterView包装器
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2017/1/12 14:58
 */
public class HeaderAndFooterWrapper extends RecyclerView.Adapter{

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }
    public void addFooterView(View view) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }
    public void addHeaderView(List<View> view) {
        for (int i = 0; i < view.size(); i++) {
            addHeaderView(view.get(i));
        }
    }
    public void addFooterView(List<View> view) {
        for (int i = 0; i < view.size(); i++) {
            addFooterView(view.get(i));
        }
    }
    public void removeFooterView(View view) {
        int idx = mFooterViews.indexOfValue(view);
        if (idx != -1) {
            mFooterViews.removeAt(idx);
        }
    }


    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }
    private int getRealItemCount()
    {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        }else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            View headerView = mHeaderViews.get(viewType);
            headerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

            CommonViewHolder myViewHolder = new CommonViewHolder(headerView);
            return myViewHolder;
        }else if (mFooterViews.get(viewType) != null) {
            View footerView = mFooterViews.get(viewType);
            footerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

            CommonViewHolder myViewHolder = new CommonViewHolder(footerView);
            return myViewHolder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }else if (isFooterViewPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }
}
