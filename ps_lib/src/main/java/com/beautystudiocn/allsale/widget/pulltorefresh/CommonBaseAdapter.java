package com.beautystudiocn.allsale.widget.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beautystudiocn.allsale.view.ViewHolder;
import com.beautystudiocn.allsale.view.ViewHolder;

import java.util.List;

/**
 * @author: wujianghua
 * @Filename:
 * @Description: 封装RecyclerView.Adapter，兼容com.junte.ui.ViewHolder
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2017/1/9 11:19
 */
public abstract class CommonBaseAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mData;
    protected final int mItemLayoutId;
    private int position;


    public CommonBaseAdapter(Context context, List<T> mData, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemLayoutId = itemLayoutId;
        this.mData = mData;
    }

    /**
     * <br> Description: 刷新数据
     * <br> Author:     fangbingran
     * <br> Date:        2017/6/15 18:07
     */
    public void setData(List<T> mData) {
        this.mData = mData;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = getViewHolder(0, null, parent);
        CommonViewHolder myViewHolder = new CommonViewHolder(viewHolder.getConvertView());
        myViewHolder.viewHolder = viewHolder;

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int pos = position + 1;                 //+1，兼容旧版本
                onItemClick(holder.itemView, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongItemClick(holder, position);
                return false;
            }
        });
        holder.viewHolder.setPosition(position);
        convert(holder.viewHolder, mData.get(position), mData, position);
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    protected abstract void convert(ViewHolder holder, T item, List<T> list, int position);

    protected abstract void onItemClick(View itemView, int position);

    public void onLongItemClick(RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

}
