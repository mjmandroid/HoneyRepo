package com.beautystudiocn.rxnetworklib.network.bean;

/**
 * @author: wujianghua
 * @Filename:
 * @Description:    统一异常类
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/2 10:45
 */
public class ApiException extends Exception {
//    /***无网络连接***/
//    public static final int CODE_NO_NETWORK = 4000;
//
//    /***OkHttp 回调 onFailure***/
//    public static final int CODE_OKHTTP_FAILURE = 4001;
//    /***OkHttp response.isSuccessful() == false***/
//    public static final int CODE_OKHTTP_NO_SUCCESS = 4002;
//
//    /***response.code == 299***/
//    public static final int CODE_REQ_TOOFREQUEST = 4003;
//    /***!response.isSuccessful()***/
//    public static final int CODE_REQ_FAILURE = 4004;
//
//    /***OkHttp 回调 onFailure——SocketTimeoutException***/
//    public static final int CODE_OKHTTP_SOCKET_TIMEOUT = 4005;
//    /***OkHttp 回调 onFailure——SocketException***/
//    public static final int CODE_OKHTTP_SOCKET_EXP = 4006;
//    /***OkHttp 回调 onFailure——SSLException***/
//    public static final int CODE_OKHTTP_SSL_EXP = 4007;
//
//
//    //TODO 这个考虑放在哪里比较好
//    /***"数据异常: 500"; 请求JSON数据异常***/
//    public static final int CODE_REQ_JSON_ERROR = 5000;
//    /***"数据解析异常：501"; JSON解析错误***/
//    public static final int CODE_RES_JSON_PARSE_ERROR = 5001;
//    /***"数据解析异常：502"; 解密错误***/
//    public static final int CODE_RES_DECODE_ERROR = 5002;
//    /***"数据解析异常：503"; 加密错误***/
//    public static final int CODE_REQ_ENCRY_ERROR = 5003;
//    /***"数据解析异常：504"; JSON解析错误***/
//    public static final int CODE_RES_JSON_PARSE_ERROR_2 = 5004;
//    /***"数据异常：506"; 其它错误***/
//    public static final int CODE_RES_OTHER_ERROR = 5006;
//    /***"数据异常：507"; 服务器数据错误***/
//    public static final int CODE_RES_DATA_EMPTY = 5007;
//    /***"数据异常：508"; 服务器数据错误***/
//    public static final int CODE_RES_NO_RETURN_CODE = 5008;
//
//    /***系统维护中***/
//    public static final int CODE_REQ_SERVER_DOWN = 5009;
//    /***重新登录***/
//    public static final int CODE_REQ_RELOGIN = 5010;
//    /***学籍信息认证错误***/
//    public static final int CODE_REQ_SCHOOL_AUTH_ERROR = 5011;
//    /***请求时间异常***/
//    public static final int CODE_REQ_TIME_ERROR = 5012;
//    /***其他请求错误***/
//    public static final int CODE_REQ_OTHER_ERROR = 5013;
//
//    /***运营配置数据与本地相同，不做回掉处理***/
//    public static final int CODE_REQ_UNDO_ERROR = 5050;
//
//    /***其余异常***/
//    public static final int CODE_OTHER_EXCEPTION = 7000;

    /**
     * 约定异常__未知错误
     */
    public static final int UNKNOWN = 5000;
    /**
     * 约定异常__解析错误
     */
    public static final int PARSE_ERROR = 5001;
    /**
     * 约定异常__网络错误
     */
    public static final int NETWORD_ERROR = 5002;
    /**
     * 约定异常__请求超时
     */
    public static final int HTTP_TIMEOUT = 5003;
    /**
     * 约定异常__请求参数错误
     */
    public static final int HTTP_ERROR = 5004;


    //对应HTTP的状态码
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;






    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
