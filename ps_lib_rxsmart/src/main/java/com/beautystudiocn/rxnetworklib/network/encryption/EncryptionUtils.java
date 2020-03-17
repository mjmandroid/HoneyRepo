package com.beautystudiocn.rxnetworklib.network.encryption;

import com.alibaba.fastjson.JSONObject;
import com.beautystudiocn.allsale.ps_lib_rxsmart.R;
import com.beautystudiocn.rxnetworklib.network.net.ExceptionEngine;
import com.beautystudiocn.rxnetworklib.network.util.LoggerManager;
import com.beautystudiocn.rxnetworklib.util.AppUtil;

import java.text.ParseException;

public class EncryptionUtils {
    //私钥
    private static final String clientPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMYC6ECqZv8VlIEx" +
            "d6q1Shis7VzZWflOV6dlqw0XFpqbbUwNNkLvgTN/QiHueBmbMvvfKB+6dSIbgdVl" +
            "eRnDb2UCYn5Dn8UynzOEl5oufQDUf3q61sgwzyZGS98UahplHs6PhoVW43884uk9" +
            "nWGGISTmOL5MdcAugMI9D+Y0isLlAgMBAAECgYEApxEG4qCjnC+aB9Mz811Yci9d" +
            "agydBGMcQ8ndI4NKeBIRiqxPDvTDHy8NHlH1FS3EO40SborEj42D4wflwF4L2kIx" +
            "FRAypfuQLiJS/EAtOR/luwhcAaWaAjooRpgN7x0QMxd6hlwY8w2QOt0Ega+8L7xh" +
            "UY9lxkUDECNWojOANqECQQDuqNG/v1WcsaauL4cq/QaaRt1Q+W//Fhlt4+QFDkoC" +
            "J4mOmjoDMwspbPfosr+7wYgF0DY8m+sCPg9bEC+/K32dAkEA1GYDh4DLX5Vm3L57" +
            "B1hSk2BPdT+4pGllkYJQz11DKD282ZAfPWoO1MErP47V3UqEhF1fyOPGNL2aYphI" +
            "TZd76QJAIRzxRT6B3WTUsJRNl8xVjzBH4sVJIcZqLtIQwBbUc+oSbuO9KtZ5NP02" +
            "hGXQrndSrSPPcqdbewsrTEI5rbeWDQJBAIuGE+1wWqiIcRCzBAh4KY5sZuXjnPxL" +
            "zA/A5irB3frSS3szpIHoaKOz3SAcSTrb159H40MI9Uvx/TelR2HJD/kCQEsuREeu" +
            "UNxspUUk2x4sP0w7A51VuExnBg2Cmk5kx6yykbNxdp5zCK0S3CCSrtKXwAx7vDjU" +
            "USpdqiICQJj65bc=";

    //公钥
    private static final String clientPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOmHrkwqVRaARXWImTdyjcRzHk" +
            "YCG5135TyJabPgzxPcGVaalnG7j2jjUn5VM9xFo7H+vln4gY/POQl1qXEOEvVi5k" +
            "KNiJ6kg44937Lhn+nbphHd7BnvT6qAGaR+wfoyYawMvDt+h1xp42mTY9Ww6XMfBt" +
            "2VBUuoA52FwfYTbe5wIDAQAB";

    /**
     * <br> Description: 加密
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/2 15:20
     *
     * @param dataJsonStr 数据
     * @return 加密数据
     */
    public static String encrypt(String dataJsonStr) {
        LoggerManager.json(dataJsonStr);
        try {
            String encodeStr = clientEncode(dataJsonStr);
            LoggerManager.json(encodeStr);
            return encodeStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * <br> Description: 解密
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/2 16:06
     */
    public static String decrypt(String response) throws Exception {
        return clientDecode(response);
    }

    /**
     * <br> Description: 客户端加密
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/2 16:35
     *
     * @param data 明文
     * @return 密文
     * @throws Exception
     */
    private static String clientEncode(String data) throws Exception {
        JSONObject reqBody = new JSONObject();
        String uuid = java.util.UUID.randomUUID().toString();
//        final String desKey = JNIUtil.get3Deskey(AppUtil.getContext(), uuid);
        final String desKey = uuid;
        //3DES加密原文
        reqBody.put("data", Cryptography.TripleDESEncrypt(data, desKey));
        //RSA加密3DES Key
        reqBody.put("key", TraderRSAUtil.encrypt(clientPublicKey, desKey));
        //串Data和3DES Key
        String keyAndData = reqBody.getString("key") + "&" + reqBody.getString("data");
        //RSA签名
        reqBody.put("sign", TraderRSAUtil.sign(clientPrivateKey, keyAndData));

        return reqBody.toString();
    }

    /**
     * <br> Description: 客户端解密
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/8/2 16:36
     *
     * @param response 密文
     * @return 明文
     * @throws Exception
     */
    private static String clientDecode(String response) throws Exception {
        JSONObject requestBodyVO = JSONObject.parseObject(response);
        //验签
        boolean flag = TraderRSAUtil.checksign(clientPublicKey,
                requestBodyVO.getString("key") + "&" + requestBodyVO.getString("data"),
                requestBodyVO.getString("sign"));
        if (flag) {
            try {
                String resultDesKey = TraderRSAUtil.decrypt(clientPrivateKey,
                        requestBodyVO.getString("key"));
                return Cryptography.TripleDESDecrypt(requestBodyVO.getString("data"), resultDesKey);
            } catch (Exception e) {
                throw ExceptionEngine.handleException(e);
            }
        } else {
            throw ExceptionEngine.handleException(new ParseException(AppUtil.getContext().getString(R.string.parse_error_tip),5001));
        }
    }
}
