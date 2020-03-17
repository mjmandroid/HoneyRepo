package com.beautystudiocn.rxnetworklib.network.request;


import android.util.Log;

import com.beautystudiocn.rxnetworklib.network.HttpRequestFactory;

/**
 * <br> ClassName:   PostRequestBuilder
 * <br> Description: POST 请求
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2018/1/13 10:32
 */
public class PostRequestBuilder extends AbRequestBuilder {
    @Override
    public void build() {
        if(mIsFormRequest){
            if (mFileMap != null){
                mTaskId = HttpRequestFactory.executePostWithFile(mUrl, mParams, mFileMap, getIRequestManager(), mMCallback);
            }else {
                mTaskId = HttpRequestFactory.executePostForm(mUrl, mParams, getIRequestManager(), mMCallback);
            }/*else {
                mTaskId = HttpRequestFactory.executePostForm(mUrl, mParams2, getIRequestManager(), mMCallback);
            }*/
        }else if (mFileMap == null || mFileMap.isEmpty()) {
            mTaskId = HttpRequestFactory.executePost(mUrl, mParams, getIRequestManager(), mMCallback);
        } else {
            mTaskId = HttpRequestFactory.executePostWithFile(mUrl, mParams, mFileMap, getIRequestManager(), mMCallback);
        }
    }
}
