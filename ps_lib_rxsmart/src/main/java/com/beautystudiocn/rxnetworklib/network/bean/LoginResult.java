package com.beautystudiocn.rxnetworklib.network.bean;


import java.util.List;

public class LoginResult<T>  extends HttpResult<T>{
    private int status;
    private String msg;

    /**
     * additionalInformation : {}
     * expiration : 1529314791931
     * expired : false
     * expiresIn : 1650377
     * refreshToken : {"expiration":1536226791931,"value":"2f682185-a054-4a1f-ac2f-53ff86027027"}
     * scope : ["all"]
     * tokenType : bearer
     * value : e7ba627b-5b7f-416c-b93e-c7da6c78980f
     */

    private AdditionalInformationBean additionalInformation;
    private long expiration;
    private boolean expired;
    private int expiresIn;
    private RefreshTokenBean refreshToken;
    private String tokenType;
    private String value;
    private List<String> scope;

    public AdditionalInformationBean getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(AdditionalInformationBean additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public RefreshTokenBean getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshTokenBean refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public static class AdditionalInformationBean {
    }

    public static class RefreshTokenBean {
        /**
         * expiration : 1536226791931
         * value : 2f682185-a054-4a1f-ac2f-53ff86027027
         */

        private long expiration;
        private String value;

        public long getExpiration() {
            return expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
