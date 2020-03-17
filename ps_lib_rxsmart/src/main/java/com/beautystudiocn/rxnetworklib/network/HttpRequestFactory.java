package com.beautystudiocn.rxnetworklib.network;


import android.content.Context;
import android.util.Log;

import com.beautystudiocn.rxnetworklib.network.bean.HttpResult;
import com.beautystudiocn.rxnetworklib.network.bean.RefreshTokenBean;
import com.beautystudiocn.rxnetworklib.network.callback.AbstractCallback;
import com.beautystudiocn.rxnetworklib.network.callback.IRequestManager;
import com.beautystudiocn.rxnetworklib.network.net.DownLoadManager;
import com.beautystudiocn.rxnetworklib.network.net.ExceptionEngine;
import com.beautystudiocn.rxnetworklib.network.net.TransformerUtils;
import com.beautystudiocn.rxnetworklib.network.request.AbRequestBuilder;
import com.beautystudiocn.rxnetworklib.network.request.PostRequestBuilder;
import com.beautystudiocn.rxnetworklib.network.util.LoggerManager;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * <br> ClassName:   HttpRequestFactory
 * <br> Description: 网络请求工厂
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2018/1/13 10:36
 */
public class HttpRequestFactory {
    /**
     * <br> Description: post请求 带上传文件
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/13 10:37
     */
    public AbRequestBuilder createPostWithFileRequest() {
        return new PostRequestBuilder();
    }

    /**
     * <br> Description: post请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:44
     *
     * @param path     请求方法函数路劲
     * @param mParams  请求参数
     * @param callback 回调
     */
    public static <T> String executePost(String path, Map<String, String> mParams, IRequestManager mIRequestManager, AbstractCallback<T> callback) {

        LoggerManager.json(new Gson().toJson(mParams));
        String taskId = RxSmart.getBaseUrl() + path;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(mParams));
        Log.e("executePost", "executePost: "+new Gson().toJson(mParams) );
        RxSmart.getDefaultApi()
                .executePost(path, body, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer())
                .subscribe(new BaseSubscriber(taskId, mIRequestManager, callback));
        return taskId;
    }

    /**
     * <br> Description: post请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:44
     *
     * @param path     请求方法函数路劲
     * @param mParams  请求参数
     * @param callback 回调
     */
    public static <T> String executePostForm(String path, Map<String, String> mParams, IRequestManager mIRequestManager, AbstractCallback<T> callback) {
        LoggerManager.json(new Gson().toJson(mParams));
        String taskId = RxSmart.getBaseUrl() + path;
        RxSmart.getDefaultApi()
                .executePost(path, mParams, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer())
                .subscribe(new BaseSubscriber(taskId, mIRequestManager, callback));
        return taskId;
    }

    /**
     * <br> Description: post同步请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:44
     *
     * @param path    请求方法函数路劲
     * @param mParams 请求参数
     */
    public static RefreshTokenBean executePostSync(String path, Map<String, String> mParams) {
        com.beautystudiocn.rxnetworklib.network.bean.RefreshTokenBean data = null;
        LoggerManager.json(new Gson().toJson(mParams));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(mParams));
        Call<RefreshTokenBean> objectCall = RxSmart.getDefaultApi()
                .executePostSync(path, body, RxSmart.getToken());
        try {
            data = objectCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static <T> String executePostWithFile(String path, Map<String, String> mParams, Map<String, List<String>> fileMap, IRequestManager mIRequestManager, AbstractCallback<T> callback) {
        LoggerManager.json(new Gson().toJson(mParams));
        String taskId = RxSmart.getBaseUrl() + path;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        if (mParams != null && !mParams.isEmpty()) {
            Set<Map.Entry<String, String>> entries = mParams.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                builder.addFormDataPart(key, value);
            }
        }
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");
        /*if (fileMap != null && !fileMap.isEmpty()) {
            Set<Map.Entry<String, File>> entries = fileMap.entrySet();
            Iterator<Map.Entry<String, File>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, File> next = iterator.next();
                String key = next.getKey();
                File file = next.getValue();
                if (key.contains(":")) {
                    key = key.substring(0, key.indexOf(":"));
                }
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                builder.addFormDataPart(key, file.getName(), imageBody);//imgfile 后台接收图片流的参数名
            }
        }*/
        Log.e("addFormDataPart", fileMap+"");
        Set<Map.Entry<String, List<String>>> entries = fileMap.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> next = iterator.next();
            String key = next.getKey();
            List<String> file = next.getValue();
            Log.e("key111", key);
            /*if (key.contains(":")) {
                key = key.substring(0, key.indexOf(":"));
            }*/
            //RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            for (String pathDta : file) {
                if (key.equals("imgs")) {
                    builder.addFormDataPart("imgs", "CN.png", RequestBody.create(MEDIA_TYPE_PNG, new File(pathDta)));
                }else {
                    builder.addFormDataPart("img", "CN.png", RequestBody.create(MEDIA_TYPE_PNG, new File(pathDta)));
                }
            }
        }
        List<MultipartBody.Part> parts = builder.build().parts();

        RxSmart.getDefaultApi()
                .executePostWithFile(path, parts, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer())
                .subscribe(new BaseSubscriber(taskId, mIRequestManager, callback));
        return taskId;
    }

    /**
     * <br> Description: delete请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:44
     *
     * @param path     请求方法函数路劲
     * @param callback 回调
     */
    public static <T> String executeDelete(String path, Map<String, String> mParams, IRequestManager mIRequestManager, AbstractCallback<T> callback) {
        String taskId = RxSmart.getBaseUrl() + path;
        RxSmart.getDefaultApi()
                .executeDelete(path,mParams, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer())
                .subscribe(new BaseSubscriber(taskId, mIRequestManager, callback));
        return taskId;
    }


    /**
     * <br> Description: put请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:44
     *
     * @param path     请求方法函数路劲
     * @param mParams  请求参数
     * @param callback 回调
     */
    public static <T> String executePut(String path, Map<String, String> mParams, IRequestManager mIRequestManager, AbstractCallback<T> callback) {
        LoggerManager.json(new Gson().toJson(mParams));
        String taskId = RxSmart.getBaseUrl() + path;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(mParams));
        RxSmart.getDefaultApi()
                .executePut(path, body, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer())
                .subscribe(new BaseSubscriber(taskId, mIRequestManager, callback));
        return taskId;
    }

    /**
     * <br> Description: get请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:44
     *
     * @param path     请求方法函数路劲
     * @param mParams  请求参数
     * @param callback 回调
     */
    public static <T> String executeGet(String path, Map<String, String> mParams, IRequestManager mIRequestManager, AbstractCallback<T> callback) {
        LoggerManager.json(new Gson().toJson(mParams));
        String taskId = RxSmart.getBaseUrl() + path;
        RxSmart.getDefaultApi()
                .executeGet(path, mParams, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer())
                .subscribe(new BaseSubscriber(taskId, mIRequestManager, callback));
        return taskId;
    }


    /**
     * <br> Description: 下载文件
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:54
     *
     * @param context
     * @param url      下载文件路劲
     * @param callback 回调
     */
    protected static <T> void executeDownload(final Context context, String url, final AbstractCallback<T> callback) {
        final String mTaskId = java.util.UUID.randomUUID().toString();
        RxSmart.getDefaultApi().downloadFile(url).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                if (callback != null) {
                    callback.onFailure(mTaskId, ExceptionEngine.handleException(e));
                }
            }

            @Override
            public void onComplete() {
                if (callback != null) {
                    callback.onComplete(mTaskId);
                }
            }


            @Override
            public void onSubscribe(Disposable d) {
                if (callback != null) {
                    callback.onStart(d);
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                DownLoadManager.getInstance(callback).writeResponseBodyToDisk(context.getApplicationContext(), mTaskId, responseBody);
            }
        });
    }
    /**
     * <br> Description: get请求
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/2 10:44
     *
     * @param path     请求方法函数路劲
     * @param mParams  请求参数

     */
    public static <T> Observable<HttpResult<Object>> executeGet(String path, Map<String, String> mParams) {
        LoggerManager.json(new Gson().toJson(mParams));
        String taskId = RxSmart.getBaseUrl() + path;
        return RxSmart.getDefaultApi()
                .executeGet(path, mParams, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer());
    }

    public static <T> Observable<HttpResult<Object>> executePost(String path, Map<String, String> mParams) {
        LoggerManager.json(new Gson().toJson(mParams));
        String taskId = RxSmart.getBaseUrl() + path;
        return RxSmart.getDefaultApi()
                .executePost(path, mParams, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer());
    }
    public static <T> Observable<HttpResult<Object>> executePostForBody(String path, Map<String, String> mParams) {
        LoggerManager.json(new Gson().toJson(mParams));
        String taskId = RxSmart.getBaseUrl() + path;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(mParams));
        return RxSmart.getDefaultApi()
                .executePost(path, body, RxSmart.getToken())
                .compose(TransformerUtils.<T>applySimpleTransformer());
    }
}
