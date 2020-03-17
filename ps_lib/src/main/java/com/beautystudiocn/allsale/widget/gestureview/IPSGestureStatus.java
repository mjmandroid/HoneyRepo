package com.beautystudiocn.allsale.widget.gestureview;

/**
 * <br> ClassName:   IPSGestureStatus
 * <br> Description:  状态
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/9/30 14:33
 */
public interface IPSGestureStatus {
    /**
     * <br> Description: 正常状态
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 14:35
     */
    void showGestureNormalStatus();

    /**
     * <br> Description: 按下时的状态
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 14:36
     */
    void showGesturePressedStatus();

    /**
     * <br> Description: 错误时的状态
     * <br> Author:      wuheng
     * <br> Date:        2017/9/30 14:36
     */
    void showGestureErrorStatus();
}
