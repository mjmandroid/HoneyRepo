package com.beautystudiocn.allsale.widget.gesturepassword.utils;

public class MathUtil {

	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2) + Math.abs(y1 - y2) * Math.abs(y1 - y2));
	}

	public static double pointTotoDegrees(double y, double x) {
		return Math.toDegrees(Math.atan2(y, x));
	}
}
