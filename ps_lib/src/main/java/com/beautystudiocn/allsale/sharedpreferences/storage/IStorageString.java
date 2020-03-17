package com.beautystudiocn.allsale.sharedpreferences.storage;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 10:12
 */

public interface IStorageString {
    /**
     * 存储文本
     *
     * @param key   存储key
     * @param value 待存储文本
     */
    void putString(String key, String value);

    /**
     * 获取文本
     *
     * @param key      存储文本的key
     * @param defValue 默认值
     * @return 返回key对应的文本
     */
    String getString(String key, String defValue);
}
