package com.beautystudiocn.allsale.sharedpreferences.cryptographic;

/**
 * @author: xiewenliang
 * @Filename: 加解密（对象）
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 9:27
 */

public interface ICryptographicObject extends ICryptographic {
    /**
     * 加密对象
     *
     * @param context 待加密字对象
     * @return 加密后的字符串
     */
    String encryptObject(Object context);

    /**
     * 解密对象
     *
     * @param context 待解密字符串
     * @return 解密后的对象
     */
    Object decodeObject(String context);
}
