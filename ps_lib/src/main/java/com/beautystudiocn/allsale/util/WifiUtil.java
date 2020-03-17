package com.beautystudiocn.allsale.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;

/**
 * ClassName:   WifiUtil
 * Description: Wifi工具类
 * <p>
 * Author:      leeeyou
 * Date:        2018/4/13 14:50
 */
public class WifiUtil {

    /**
     * Description: 采用显示匹配方式跳转到系统wifi界面，防止三方应用拦截intent，产生bug。考虑到三方ROM对源码的修改，这里做了兼容处理
     * <p>
     * <p>step1：采用"com.android.settings", "com.android.settings.Settings$WifiSettingsActivity"的方式直接跳转</p>
     * <p>step2：如果跳转失败，则说明三方ROM对源码包名或类名进行更改过，此时通过PM找到所有符合ACTION_WIFI_SETTINGS条件的activity，再对其进行筛选，刷选规则：包名和类名必须以com.android开头</p>
     * <p>step3：筛选过程中，没有一个符合条件的，则采用隐式方式跳转</p>
     * <p>
     * <p>测试通过的手机：华为，锤子，oppo，小米，vivo</p>
     * <p>vivo手机会通过step2打开wifi设置页</p>
     * <p>
     * Author:      leeeyou
     * Date:        2017/10/13 16:08
     */
    public static void jumpSystemWifiSettingActivity(@NonNull Context context) {
        try {
            try {
                //step1：直接采用显示匹配打开
                Intent firstIntent = new Intent();
                firstIntent.setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
                context.startActivity(firstIntent);
            } catch (ActivityNotFoundException e) {
                //step2：查询手机可用wifi的列表，并查找包名以“com.android”开发以及类名以“com.android”开头的activity
                PackageManager packageManager = context.getPackageManager();
                Intent secondIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                List<ResolveInfo> list = packageManager.queryIntentActivities(secondIntent, 0);

                boolean isFindAvailableWifi = false;
                if (list != null && list.size() > 0) {
                    for (ResolveInfo info : list) {
                        if (info != null
                                && info.activityInfo.packageName.startsWith("com.android")
                                && info.activityInfo.name.startsWith("com.android")) {
                            isFindAvailableWifi = true;
                            Intent intent = new Intent();
                            intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                            context.startActivity(intent);
                            break;
                        }
                    }
                }

                //step3：如不存在符合筛选条件的activity，最后采用隐式方式跳转
                if (!isFindAvailableWifi) {
                    if (context.getPackageManager().resolveActivity(secondIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                        context.startActivity(secondIntent);
                    } else {
                        Toast.makeText(context, "抱歉，未找到系统Wifi设置项。", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
