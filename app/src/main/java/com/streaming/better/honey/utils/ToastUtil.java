/*
    ShengDao Android Client, NToast
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.streaming.better.honey.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * [Toast工具类]
 * 
 * @author mashidong
 * @version 1.0
 * @date 2014-3-13
 * 
 **/
public class ToastUtil {
	
	public static void shortToast(Context context, int resId) {
		if(context != null){
			showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
		}
	}
	
	public static void shortToast(Context context, String text) {
		if(context != null){
			showToast(context, text, Toast.LENGTH_SHORT);
		}
	}

	public static void longToast(Context context, int resId) {
		if(context != null){
			showToast(context, context.getString(resId), Toast.LENGTH_LONG);
		}
	}
	
	public static void longToast(Context context, String text) {
		if(context != null){
			showToast(context, text, Toast.LENGTH_LONG);
		}
	}
	
	public static void showToast(Context context, String text, int duration) {
		if(context != null){
			Toast.makeText(context, text, duration).show();
		}
	}
}
