package com.beautystudiocn.allsale.widget.gestureview;

/**
 * <br> ClassName:   PSGestureView
 * <br> Description:  手势回调接口
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/10/13 15:31
 */
public interface IPSGestureChangeLinstener {
    /**
     * <br> Description: 开始
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 15:35
     */
    void onGestureViewStart(PSGestureView gestureView);

    /**
     * <br> Description: 改变中
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 15:35
     */
    void onGestureViewChange(PSGestureView gestureView, String currentPassword);

    /**
     * <br> Description: 结束
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 15:36
     */
    void onGestureViewFinish(PSGestureView gestureView, String currentPassword);
}
