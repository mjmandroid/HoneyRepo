package com.beautystudiocn.allsale.grant;

/**
 * <br> ClassName:   OnPermissionCallback
 * <br> Description: 申请回调类
 * <br>
 * <br> Author:      wujun
 * <br> Date:        2017/8/3 9:13
 */
public interface OnPermissionCallback {


    /**
     *<br> Description: 允许
     *<br> Author:      wujun
     *<br> Date:        2017/8/3 9:13
     */
    void onRequestAllow(String... permissionName);

    /**
     *<br> Description: 拒绝
     *<br> Author:      wujun
     *<br> Date:        2017/8/3 9:14
     */
    void onRequestRefuse(String... permissionName);


    /**
     *<br> Description: 不在询问
     *<br> Author:      wujun
     *<br> Date:        2017/8/3 9:14
     */
    void onRequestNoAsk(String... permissionName);

}
