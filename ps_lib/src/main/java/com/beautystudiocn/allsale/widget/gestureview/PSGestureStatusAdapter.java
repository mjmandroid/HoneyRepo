package com.beautystudiocn.allsale.widget.gestureview;


/**
 * <br> ClassName:   PSGestureStatusAdapter
 * <br> Description: 手势返回结果的适配类
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2017/11/7 18:12
 */
public abstract class PSGestureStatusAdapter implements IPSGestureChangeLinstener {
    protected static final String TAG = "zll";

    public abstract void onPwdShort(String password);

    public abstract void onGestureFinish(PSGestureView gestureView, String currentPassword);

    @Override
    public void onGestureViewStart(PSGestureView gestureView) {

    }

    @Override
    public void onGestureViewChange(PSGestureView gestureView, String currentPassword) {

    }

    @Override
    public final void onGestureViewFinish(PSGestureView gestureView, String currentPassword) {
        if (isPwdShort(gestureView, currentPassword)) return;
        this.onGestureFinish(gestureView, currentPassword);
    }

    /**
     * <br> Description: 判断密码是否太短
     * <br> Author:      KevinWu
     * <br> Date:        2017/11/8 9:17
     *
     * @param password
     * @return true 短 false 长
     */
    protected final boolean isPwdShort(PSGestureView gestureView, String password) {
        if (password.split(",").length < gestureView.getGestureConfig().getPwdMiniLength()) {
            onPwdShort(password);
            return true;
        }
        return false;
    }

}
