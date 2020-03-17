package com.beautystudiocn.allsale.widget.gestureview;

import android.support.annotation.NonNull;

/**
 * <br> ClassName:   保存点
 * <br> Description:
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/10/10 11:40
 */
public class PSPoint {
    private int x;
    private int y;
    private int index = -1;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("x:");
        sb.append(x);
        sb.append(" y:");
        sb.append(y);
        sb.append(" index :");
        sb.append(index);
        sb.append("   ");
        return sb.toString();
    }

    /**
     * <br> Description: 匹配两个点的值是否相同
     * <br> Author:      KevinWu
     * <br> Date:        2017/11/8 11:45
     */
    public static boolean equalsValue(@NonNull PSPoint point1, @NonNull PSPoint point2) {
        if (point1.getX() == point2.getX() && point1.getY() == point2.getY()) {
            return true;
        }
        return false;
    }
}
