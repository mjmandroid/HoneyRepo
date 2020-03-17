package com.beautystudiocn.allsale.sharedpreferences.cryptographic;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description: 密钥与密文组装实例
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/11 14:17
 */

public class ContextDecorate implements IContextDecorate {
    private static final String JSON_KEY = "key";
    private static final String JSON_VALUE = "value";

    @Override
    public String getDecorateContext(String key, String cipherText) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JSON_KEY, key);
            jsonObject.put(JSON_VALUE, cipherText);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey(String decorateContext) {
        if (decorateContext.startsWith("{") && decorateContext.endsWith("}")) {
            try {
                JSONObject jsonObject = new JSONObject(decorateContext);
                return jsonObject.optString(JSON_KEY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getCipherText(String decorateContext) {
        if (decorateContext.startsWith("{") && decorateContext.endsWith("}")) {
            try {
                JSONObject jsonObject = new JSONObject(decorateContext);
                return jsonObject.optString(JSON_VALUE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
