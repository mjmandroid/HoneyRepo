package com.beautystudiocn.rxnetworklib.network.bean;

public class RefreshTokenBean {

    /**
     * data : {"refreshTokenValue":"4e20e4e0-bedb-4a55-8ab0-38f00cfe52e5","tokenExpiresIn":1358,"tokenValue":"23645016-76fc-43c2-ac8b-f4c0f28b79c4"}
     * meta : {"code":0,"message":null}
     */

    private DataBean data;
    private MetaBean meta;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public static class DataBean {
        /**
         * refreshTokenValue : 4e20e4e0-bedb-4a55-8ab0-38f00cfe52e5
         * tokenExpiresIn : 1358
         * tokenValue : 23645016-76fc-43c2-ac8b-f4c0f28b79c4
         */

        private String refreshTokenValue;
        private int tokenExpiresIn;
        private String tokenValue;

        public String getRefreshTokenValue() {
            return refreshTokenValue;
        }

        public void setRefreshTokenValue(String refreshTokenValue) {
            this.refreshTokenValue = refreshTokenValue;
        }

        public int getTokenExpiresIn() {
            return tokenExpiresIn;
        }

        public void setTokenExpiresIn(int tokenExpiresIn) {
            this.tokenExpiresIn = tokenExpiresIn;
        }

        public String getTokenValue() {
            return tokenValue;
        }

        public void setTokenValue(String tokenValue) {
            this.tokenValue = tokenValue;
        }
    }

    public static class MetaBean {
        /**
         * code : 0
         * message : null
         */

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
