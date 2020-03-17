package com.beautystudiocn.allsale.widget.gestureview;


/**
 * <br> ClassName:   PSGestureManager
 * <br> Description: 手势的管理类
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/10/13 17:40
 */
public final class PSGestureManager implements IPSGestureChangeLinstener {
    private static final String TAG = "PSGestureManager";
    private IPSGestureChangeLinstener mGestureLinstener = null;
    private PSGestureConfig mConfig;
    /***状态***/
    private IPSGestureStatus mGestureViewStatus = null;

    /**
     * <br> Description: 设置手势的回调对象
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 15:37
     */
    public void setmGestureLinstener(IPSGestureChangeLinstener mGestureLinstener, PSGestureConfig config, IPSGestureStatus gestureViewStatus) {
        this.mGestureLinstener = mGestureLinstener;
        this.mConfig = config;
        this.setGestureViewStatus(gestureViewStatus);
    }

    /**
     * <br> Description: 返回配置属性
     * <br> Author:      KevinWu
     * <br> Date:        2017/11/9 9:04
     */
    public PSGestureConfig getConfig() {
        return mConfig;
    }

    public IPSGestureStatus getGestureViewStatus() {
        return mGestureViewStatus;
    }

    public void setGestureViewStatus(IPSGestureStatus mGestureViewStatus) {
        this.mGestureViewStatus = mGestureViewStatus;
    }

    @Override
    public void onGestureViewStart(PSGestureView gestureView) {
        if (mGestureLinstener != null) {
            mGestureLinstener.onGestureViewStart(gestureView);
        }
    }

    @Override
    public void onGestureViewChange(PSGestureView gestureView, String currentPassword) {
        if (mGestureLinstener != null) {
            mGestureLinstener.onGestureViewChange(gestureView, currentPassword);
        }
    }

    @Override
    public void onGestureViewFinish(PSGestureView gestureView, String currentPassword) {
        if (currentPassword.split(",").length < mConfig.getPwdMiniLength()) {
            mGestureViewStatus.showGestureErrorStatus();
        }
        if (mGestureLinstener != null) {
            mGestureLinstener.onGestureViewFinish(gestureView, currentPassword);
        }
    }

}
