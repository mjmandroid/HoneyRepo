package com.beautystudiocn.allsale.sharedpreferences.storage;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.beautystudiocn.allsale.sharedpreferences.cryptographic.CryptographicObject;
import com.beautystudiocn.allsale.sharedpreferences.cryptographic.ICryptographicObject;
import com.beautystudiocn.allsale.sharedpreferences.cryptographic.CryptographicObject;
import com.beautystudiocn.allsale.sharedpreferences.cryptographic.ICryptographicObject;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 10:20
 */

public class StorageObject implements IStorageObject {
    /**
     * 对象加密工具
     */
    private ICryptographicObject cryptographicObject;
    /**
     * SharedPreferences类
     */
    private SharedPreferences sharedPreferences;

    public StorageObject(SharedPreferences sharedPreferences) {
        cryptographicObject = new CryptographicObject();
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void putObject(String key, Object value) {
        String encryptObject = cryptographicObject.encryptObject(value);
        encryptObject = TextUtils.isEmpty(encryptObject) ? "" : encryptObject;
        sharedPreferences.edit().putString(key, encryptObject).commit();
    }

    @Override
    public Object getObject(String key, Object defValue) {
        String context = sharedPreferences.getString(key, "");
        return TextUtils.isEmpty(context) ? defValue : cryptographicObject.decodeObject(context);
    }
}
