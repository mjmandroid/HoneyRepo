package com.beautystudiocn.rxnetworklib.network.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author: liaoshengjian
 * @Filename:
 * @Description:    自定义响应ResponseBody
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2017/3/17 15:59
 */
public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();

        //兼容 "data":"" 数据格式问题
        int index = response.indexOf("\"data\":\"\"");
        if (index != -1) {
            adapter.fromJson(response.replace("\"data\":\"\"", "\"data\":{}"));
        }

        /*response = "{\"errorcode\":201,\"status\":\"success\",\"msg\":\"成功\",\"data\":{\"errorcode\":0,\"errorMsg\":\"图片验证码输入有误\",\"status\":\"0\"}}";

        HttpResult<InnerHttpResult> tempResult = gson.fromJson(response, new TypeToken<HttpResult<InnerHttpResult>>() {}.getType());
        if (tempResult.getCode() == 201
                || tempResult.getCode() == 202
                || tempResult.getCode() == 203) {


            throw new HttpResultException(tempResult.getCode(), tempResult.getData().getErrorMsg());
        }*/

        return adapter.fromJson(response);

    }
}
