package com.beautystudiocn.allsale.sharedpreferences;

import android.content.Context;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description: 本地数据存储(SharedPreferences)
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 10:44
 */

public class Shared {

    /**
     * 获取本地数据存储管理类
     *
     * @param context Context
     * @return 返回管理类
     */
    public static SharedManager with(Context context) {
        return with(context, SharedConfig.builder().build());
    }

    /**
     * 获取本地数据存储管理类
     *
     * @param context      Context
     * @param sharedConfig 配置信息
     * @return 返回管理类
     */
    public static SharedManager with(Context context, SharedConfig sharedConfig) {
        return SharedManager.get(context, sharedConfig);
    }

    /**
     * 清除本地数据
     *
     * @param context Context
     */
    public static void clear(Context context) {
        clear(context, SharedConfig.builder().build());
    }

    /**
     * 清除本地数据
     *
     * @param context      Context
     * @param sharedConfig 配置信息
     */
    public static void clear(Context context, SharedConfig sharedConfig) {
        with(context, sharedConfig).clear();
    }
}
