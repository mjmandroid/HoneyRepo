package com.beautystudiocn.allsale.widget.guideview;

import com.beautystudiocn.allsale.sharedpreferences.Shared;
import com.beautystudiocn.allsale.sharedpreferences.SharedConfig;
import com.beautystudiocn.allsale.sharedpreferences.SharedManager;
import com.beautystudiocn.allsale.util.AppUtil;
import com.beautystudiocn.allsale.sharedpreferences.Shared;
import com.beautystudiocn.allsale.sharedpreferences.SharedConfig;
import com.beautystudiocn.allsale.sharedpreferences.SharedManager;
import com.beautystudiocn.allsale.util.AppUtil;

/**
 * <br> ClassName:   PSGuideShareVersionUtil
 * <br> Description: 保存数据
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/4/23 15:09
 */
public class PSGuideShareVersionUtil {

    private static PSGuideShareVersionUtil instance;
    /***文件名***/
    private static final String FILE_NAME = "guideData";

    private static SharedManager mManager;

    public PSGuideShareVersionUtil(String versionName) {
        SharedConfig mSharedConfigFlag = SharedConfig.builder().setShareFileName(FILE_NAME).setShareFlag(versionName).build();
        mManager = Shared.with(AppUtil.getContext(), mSharedConfigFlag);
    }

    public static PSGuideShareVersionUtil getInstance(String versionName) {
        if (instance == null) {
            instance = new PSGuideShareVersionUtil(versionName);
        }

        return instance;
    }

    public void setValue(String key, boolean value) {
        mManager.putBoolean(key, value);
    }

    public boolean getValue(String key, boolean delValue) {
        return mManager.getBoolean(key, delValue);
    }
}
