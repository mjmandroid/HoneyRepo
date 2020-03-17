package com.beautystudiocn.allsale.util.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: liaoshengjian
 * @Filename:
 * @Description: 反射获取泛型类型
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2017/5/4 11:33
 */
public class GenericType<T> {

    private final Type type;

    protected GenericType(){
        Type superClass = getClass().getGenericSuperclass();

        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
