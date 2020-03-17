package com.beautystudiocn.allsale.widget.tabviewpager.materialtabs;

/**
 * @Description : materialtabs
 * @Author       : mingweigao / gaomingwei@tuandai.com
 * @Date         : 2015/12/18.
 * @Version      : 1.0
 */
public interface MaterialTabListener {

	void onTabSelected(MaterialTab tab);
	
	void onTabReselected(MaterialTab tab);
	
	void onTabUnselected(MaterialTab tab);

}
