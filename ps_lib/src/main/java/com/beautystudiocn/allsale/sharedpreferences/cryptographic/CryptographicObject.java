package com.beautystudiocn.allsale.sharedpreferences.cryptographic;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 9:25
 */

public class CryptographicObject extends Cryptographic implements ICryptographicObject {
    /**
     * 密钥与密文组装工具
     */
    private IContextDecorate contextDecorate;

    public CryptographicObject() {
        contextDecorate = new ContextDecorate();
    }

    @Override
    public String encryptObject(Object context) {
        if (context != null && context instanceof Serializable) {
            try {
                byte[] bytes;
                ByteArrayOutputStream bot = new ByteArrayOutputStream();
                ObjectOutputStream oot = new ObjectOutputStream(bot);
                oot.writeObject(context);
                bytes = bot.toByteArray();
                bot.close();
                oot.close();
                String key = getKey();
                String cipherText = new String(Base64.encode(encryptByte(bytes, key), Base64.DEFAULT), "UTF-8");
                return contextDecorate.getDecorateContext(key, cipherText);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Object decodeObject(String context) {
        try {
            String key = contextDecorate.getKey(context);
            String cipherText = contextDecorate.getCipherText(context);
            byte[] plainBytes;
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(cipherText)) {
                plainBytes = context.getBytes("UTF-8");
            } else {
                plainBytes = decodeByte(Base64.decode(cipherText.getBytes("UTF-8"), Base64.DEFAULT), key);
            }
            Object obj;
            ByteArrayInputStream bit = new ByteArrayInputStream(plainBytes);
            ObjectInputStream oit = new ObjectInputStream(bit);
            obj = oit.readObject();
            bit.close();
            oit.close();
            return obj;
        } catch (Exception e) {
            return null;
        }
    }
}
