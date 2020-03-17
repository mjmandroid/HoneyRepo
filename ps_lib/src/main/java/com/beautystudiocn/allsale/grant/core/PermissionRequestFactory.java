package com.beautystudiocn.allsale.grant.core;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * <br> ClassName:   PermissionRequestFactory
 * <br> Description: 权限工厂类
 * <br>
 * <br> Author:      wujun
 * <br> Date:        2017/9/12 11:26
 */
public class PermissionRequestFactory {


    public static IPermissionRequest create(Activity activity) {
        return create(new WeakReference<>(activity));
    }

    /**
     * 启用弱应用，暂时保留Activity强引用的构造函数
     * @param weakReference
     * @return
     */
    public static IPermissionRequest create(WeakReference<Activity> weakReference) {
        return new PermissionRequestImpl(weakReference);
    }

}
