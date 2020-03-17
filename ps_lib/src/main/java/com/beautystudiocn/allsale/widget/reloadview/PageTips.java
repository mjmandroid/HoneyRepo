package com.beautystudiocn.allsale.widget.reloadview;

import android.view.View;

import java.io.Serializable;

/**
 * 页面提示信息
 * @Filename:    PageTips.java
 * @Description: TODO
 * @Copyright:   Copyright (c) 2014 Tuandai Inc. All Rights Reserved.
 * @author longluliu 
 * Create at: 2015-5-12 下午5:38:07 
 *
 */
public class PageTips implements Serializable {
	/**
	 * 提示1
	 */
	private String tips;
	/**
	 * 提示2
	 */
	private String tips2;
	/**
	 * 描述1
	 */
	private String tipsDesc1;
	/**
	 * 描述2
	 */
	private String tipsDesc2;
	/**
	 * 图标
	 */
	private int iconResid;
	/**
	 * 背景
	 */
	private int bgResid;
	/**
	 * 是否可点页面击重新加载
	 */
	private boolean isReload = true;
	
	private View childView;
	
	public PageTips() {
		
	}

	public PageTips(String tips, String tipsDesc1, String tipsDesc2,
                    int iconResid, int bgResid, boolean isReload) {
		super();
		this.tips = tips;
		this.tipsDesc1 = tipsDesc1;
		this.tipsDesc2 = tipsDesc2;
		this.iconResid = iconResid;
		this.bgResid = bgResid;
		this.isReload = isReload;
	}
	
	public PageTips(String tips, String tipsDesc1, String tipsDesc2,
                    int iconResid, int bgResid, boolean isReload, View childView) {
		super();
		this.tips = tips;
		this.tipsDesc1 = tipsDesc1;
		this.tipsDesc2 = tipsDesc2;
		this.iconResid = iconResid;
		this.bgResid = bgResid;
		this.isReload = isReload;
		this.childView = childView;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getTipsDesc1() {
		return tipsDesc1;
	}

	public void setTipsDesc1(String tipsDesc1) {
		this.tipsDesc1 = tipsDesc1;
	}

	public String getTipsDesc2() {
		return tipsDesc2;
	}

	public void setTipsDesc2(String tipsDesc2) {
		this.tipsDesc2 = tipsDesc2;
	}

	public int getIconResid() {
		return iconResid;
	}

	public void setIconResid(int iconResid) {
		this.iconResid = iconResid;
	}

	public int getBgResid() {
		return bgResid;
	}

	public void setBgResid(int bgResid) {
		this.bgResid = bgResid;
	}

	public boolean isReload() {
		return isReload;
	}

	public void setReload(boolean isReload) {
		this.isReload = isReload;
	}

	public View getChildView() {
		return childView;
	}

	public void setChildView(View childView) {
		this.childView = childView;
	}

	public String getTips2() {
		return tips2;
	}

	public void setTips2(String tips2) {
		this.tips2 = tips2;
	}
}
