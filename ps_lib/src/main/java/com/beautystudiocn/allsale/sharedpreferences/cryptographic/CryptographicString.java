package com.beautystudiocn.allsale.sharedpreferences.cryptographic;

import android.text.TextUtils;
import android.util.Base64;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author: xiewenliangcontext
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 9:25
 */

public class CryptographicString extends Cryptographic implements ICryptographicString {
    /**
     * 密钥与密文组装工具
     */
    private IContextDecorate contextDecorate;

    public CryptographicString() {
        contextDecorate = new ContextDecorate();
    }

    /**
     * 加密字符串
     *
     * @param context 待加密字符串
     * @return 加密后字符串
     */
    @Override
    public String encryptString(String context) {
        if (TextUtils.isEmpty(context)) return null;
        try {
            String key = getKey();
            String cipherText = new String(Base64.encode(encryptByte(context.getBytes("UTF-8"), key), Base64.DEFAULT), "UTF-8");
            return contextDecorate.getDecorateContext(key, cipherText);
        } catch (UnsupportedEncodingException e) {
            encryptStringError(context, getExceptionMessage(e));
            return null;
        }
    }

    /**
     * 解密字符串
     *
     * @param context 待解密字符串
     * @return 已解密字符串
     */
    @Override
    public String decodeString(String context) {
        String key = contextDecorate.getKey(context);
        String cipherText = contextDecorate.getCipherText(context);
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(cipherText)) {
            return context;
        }
        try {
            return new String(decodeByte(Base64.decode(cipherText.getBytes("UTF-8"), Base64.DEFAULT), key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            decodeStringError(cipherText, getExceptionMessage(e));
            return null;
        }
    }

    /**
     * <br> Description: 获取错误信息
     * <br> Author:      xwl
     * <br> Date:        2018/8/10 15:23
     *
     * @param e Exception
     * @return 错误信息
     */
    private String getExceptionMessage(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            sw.close();
            pw.close();
            return sw.toString();
        } catch (IOException el) {
            el.printStackTrace();
        }
        return null;
    }

    /**
     * <br> Description: 加密错误
     * <br> Author:      xwl
     * <br> Date:        2018/8/10 15:18
     *
     * @param context 内容
     * @param message 错误信息
     */
    private void encryptStringError(String context, String message) {
    }

    /**
     * <br> Description: 解密错误
     * <br> Author:      xwl
     * <br> Date:        2018/8/10 15:18
     *
     * @param context 内容
     * @param message 错误信息
     */
    private void decodeStringError(String context, String message) {
    }
}
