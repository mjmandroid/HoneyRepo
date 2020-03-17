package com.beautystudiocn.rxnetworklib.network.bean;

public class HttpResult<T> {
    /**
     * 0 正常，其他参考接口请求错误码
     */
    private int reCode;
    private MetaBean meta;

    private int code;
    private String message;
    private T data;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getCode() {
        return code;
//        if (meta != null)
//            return meta.code;
//        return -1;
    }

    public String getMsg() {
        return message;
//        if (meta != null)
//            return meta.message;
//        return null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public class MetaBean {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
