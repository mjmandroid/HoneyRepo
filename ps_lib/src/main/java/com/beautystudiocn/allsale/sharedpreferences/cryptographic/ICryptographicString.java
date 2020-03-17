package com.beautystudiocn.allsale.sharedpreferences.cryptographic;

/**
 * @author: xiewenliang
 * @Filename: 加解密（字符串）
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 9:18
 */

public interface ICryptographicString extends ICryptographic {
    /**
     * 加密字符串
     *
     * @param context 待加密字符串
     * @return 加密后的字符串
     */
    String encryptString(String context);

    /**
     * 解密字符串
     *
     * @param context 待解密字符串
     * @return 解密后的字符串
     */
    String decodeString(String context);
}
