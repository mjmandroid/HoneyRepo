package com.beautystudiocn.rxnetworklib.network;


import com.beautystudiocn.rxnetworklib.network.bean.HttpResult;
import com.beautystudiocn.rxnetworklib.network.bean.LoginResult;
import com.beautystudiocn.rxnetworklib.network.bean.RefreshTokenBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    @Headers({"Connection: close"})
    @GET
    Observable<HttpResult<Object>> executeGet(
            @Url String url,
            @QueryMap Map<String, String> maps,
            @Header("Authorization") String token
    );

    @PUT
    Observable<HttpResult<Object>> executePut(
            @Url String url,
            @Body RequestBody info,
            @Header("Authorization") String token
    );


    @DELETE
    Observable<HttpResult<Object>> executeDelete(
            @Url String url,
            @QueryMap Map<String, String> maps,
            @Header("Authorization") String token
    );


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Observable<HttpResult<Object>> executePost(
            @Url String url,
            @Body RequestBody info,
            @Header("Authorization") String token
    );

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Call<RefreshTokenBean> executePostSync(
            @Url String url,
            @Body RequestBody info,
            @Header("Authorization") String token
    );


    @FormUrlEncoded
    @POST
    Observable<LoginResult<Object>> executePost(
            @Url String url,
            @FieldMap Map<String, String> maps,
            @Header("Authorization") String token
    );

    @Multipart
    @POST
    Observable<HttpResult<Object>> executePostWithFile(
            @Url String url,
            @Part List<MultipartBody.Part> partList,
            @Header("Authorization") String token
    );

    @POST("{url}")
    Observable<ResponseBody> json(
            @Path("url") String url,
            @Body RequestBody jsonStr);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(
            @Path("url") String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @POST("{url}")
    Call<ResponseBody> uploadFiles(
            @Path("url") String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);


    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
