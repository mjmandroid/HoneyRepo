package com.beautystudiocn.allsale.util;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.beautystudiocn.allsale.log.LoggerManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <br> ClassName:   DataConversionUtil
 * <br> Description: 各种数值转换工具类
 * <br>
 * <br> Author:      zhangweiqiang
 * <br> Date:        2017/8/2 11:34
 */
public class DataConversionUtil {

    /**
     * <br> Description: 将bean转换成键值对列表
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/7/25 11:36
     *
     * @param bean 要转换的bean类
     * @return 返回转换后的键值对列表集合
     */
    public static List<NameValuePair> bean2Parameters(Object bean) {
        if (bean == null) {
            return null;
        }
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        // 取得bean所有public 方法
        Method[] Methods = bean.getClass().getMethods();
        for (Method method : Methods) {
            if (method != null && method.getName().startsWith("get") && !method.getName().startsWith("getClass")) {
                // 得到属性的类名
                String value = "";
                // 得到属性值
                try {
                    String className = method.getReturnType().getSimpleName();
                    if (className.equalsIgnoreCase("int")) {
                        int val = 0;
                        try {
                            val = (Integer) method.invoke(bean);
                        } catch (InvocationTargetException e) {
                            LoggerManager.e("InvocationTargetException", e.getMessage(), e);
                        }
                        value = String.valueOf(val);
                    } else if (className.equalsIgnoreCase("String")) {
                        try {
                            value = (String) method.invoke(bean);
                        } catch (InvocationTargetException e) {
                            LoggerManager.e("InvocationTargetException", e.getMessage(), e);
                        }
                    }
                    if (value != null && value != "") {
                        // 添加参数
                        // 将方法名称转化为id，去除get，将方法首字母改为小写
                        String param = method.getName().replaceFirst("get", "");
                        if (param.length() > 0) {
                            String first = String.valueOf(param.charAt(0)).toLowerCase();
                            param = first + param.substring(1);
                        }
                        parameters.add(new BasicNameValuePair(param, value));
                    }
                } catch (IllegalArgumentException e) {
                    LoggerManager.e("IllegalArgumentException", e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    LoggerManager.e("IllegalAccessException", e.getMessage(), e);
                }
            }
        }
        return parameters;
    }

    /**
     * <br> Description: 根据概率判断是否被选中
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/7/25 11:23
     *
     * @param probalility 概率
     * @return 返回1代表选中，返回0代表未选中，返回-1代表出错
     */
    public static int getProbalility(double probalility) {
        /**
         * 0出现的概率
         */
        double rate0 = 1 - probalility;
        /**
         * 1出现的概率
         */
        double rate1 = probalility;
        double randomNumber = Math.random();
        if (randomNumber >= 0 && randomNumber <= rate0) {
            return 0;
        } else if (randomNumber >= rate0 / 100 && randomNumber <= rate0 + rate1) {
            return 1;
        }
        return -1;
    }

    /**
     * <br> Description: object对象转为string
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/8/2 14:03
     *
     * @param obj obj对象
     * @return 返回string类型
     */
    public static String toJSONString(Object obj) {
        if (obj == null) {
            return "";
        }
        JSONObject json = new JSONObject();
        try {
            List<NameValuePair> list = bean2Parameters(obj);
            for (NameValuePair nv : list) {
                json.put(nv.getName(), nv.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * <br> Description: string类型对象转为JSONObject
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/8/2 14:04
     *
     * @param str string对象
     * @return 返回JSONObject对象
     */
    public static JSONObject string2JSON(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * <br> Description: 获取UTF-8编码格式化后的url
     * <br> Author:      hehaodong
     * <br> Date:        2017-06-19 17:10
     *
     * @param url 网络地址
     * @return 返回格式化url
     */
    public static String encodeUrl(String url) {
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (Exception ex) {
            LoggerManager.e(ex.toString());
        }
        return url;
    }

    /**
     * <br> Description: 二进制转字符串
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/7/20 10:05
     *
     * @param b 要转换的二进制
     * @return 返回字符串
     */
    private static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    /**
     * <br> Description: 将图片Uri对象转成Bitmap
     * <br> Author:      longluliu
     * <br> Date:        2014/09/03 20:40
     *
     * @param uri 图片uri
     * @return 返回图片bitmap对象
     */
    public static Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            ContentResolver cr = AppUtil.getContext().getContentResolver();
            InputStream is = cr.openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
