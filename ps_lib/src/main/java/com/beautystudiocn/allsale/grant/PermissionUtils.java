package com.beautystudiocn.allsale.grant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * <br> ClassName:   PermissionUtils
 * <br> Description: 权限工具类
 * <br>
 * <br> Author:      wujun`
 * <br> Date:        2017/9/9 14:01
 */
public class PermissionUtils {

    /**
     *<br> Description: 打开设置
     *<br> Author:      wujun
     *<br> Date:        2017/8/3 9:28
     * @param context        上下文
     * @param packageName 包名
     */
    public  static  void openPermissionSettings(@NonNull Context context, @NonNull String packageName) {
        Intent intent = new Intent();
        intent.setAction("miui.intent.action.APP_PERM_EDITOR"); ////适配小米MIUI系统权限管理
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("extra_pkgname", packageName);
        try {
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Uri packageURI = Uri.parse("package:" + packageName);
                intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", packageURI);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    jumptoApplicationPage(context);
                }
            }
        } catch (Exception e) {
            jumptoApplicationPage(context);
        }
    }


    /**
     *<br> Description: 跳转到应用页面
     *<br> Author:      wujun
     *<br> Date:        2017/8/4 8:54
     */
    public  static void jumptoApplicationPage(@NonNull Context context) {
        Intent intent;
        intent =  new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     *<br> Description: 获取权限
     *<br> Author:      wujun
     *<br> Date:        2017/8/3 14:43
     * @param permissionNames    权限名称
     * @return
     */
    @NonNull
    public  static String getStringBuilder(List<String> permissionNames) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i <  permissionNames.size();i++) {
            builder.append(PermissionType.getValueByKey(permissionNames.get(i)))
                    .append("、");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
