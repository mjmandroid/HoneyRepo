package com.beautystudiocn.rxnetworklib.network.request;


public interface IRequestMethod {
    //get 请求
    AbRequestBuilder createGetRequest();

    //post 请求
    AbRequestBuilder createPostRequest();

    //put 请求
    AbRequestBuilder createPutRequest();

    //delete 请求
    AbRequestBuilder createDeleteRequest();

}
