package com.streaming.better.honey.utils;

import android.os.Handler;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.beautystudiocn.allsale.R;

import java.util.regex.Pattern;

public class TextUtil {

    //邮箱表达式
    private final static Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    //手机号表达式
    private final static Pattern phone_pattern = Pattern.compile("^(13|15|18|17)\\d{9}$");

    /**
     * 验证邮箱是否正确
     *
     * @param email 邮箱地址
     * @return boolean
     */
    public static boolean isEmail(String email) {
        return email_pattern.matcher(email).matches();
    }


    /**
     * 验证手机号是否正确
     *
     * @param phone 手机号码
     * @return boolean
     */
    public static boolean isPhone(String phone) {
        return phone_pattern.matcher(phone).matches();
    }

    /**
     * get the last char index for max limit row,if not exceed the limit,return -1
     *
     * @param textView
     * @param content
     * @param width
     * @param maxLine
     * @return
     */
    public static int getLastCharIndexForLimitTextView(TextView textView, String content, int width, int maxLine) {
        Log.i("Alex", "宽度是" + width);
        TextPaint textPaint = textView.getPaint();
        StaticLayout staticLayout = new StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        if (staticLayout.getLineCount() > maxLine)
            return staticLayout.getLineStart(maxLine) - 1;//exceed
        else return -1;//not exceed the max line
    }

    /**
     * 在不绘制textView的情况下算出textView的高度，并且根据最大行数得到应该显示最后一个字符的下标，请在主线程顺序执行，第一个返回值是最后一个字符的下标，第二个返回值是TextView最终应该占用的高度
     *
     * @param textView
     * @param content
     * @param width
     * @param maxLine
     * @return
     */
    public static int[] measureTextViewHeight(TextView textView, String content, int width, int maxLine) {
        Log.i("Alex", "宽度是" + width);
        TextPaint textPaint = textView.getPaint();
        StaticLayout staticLayout = new StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        int[] result = new int[2];
        if (staticLayout.getLineCount() > maxLine) {//如果行数超出限制
            int lastIndex = staticLayout.getLineStart(maxLine) - 1;
            result[0] = lastIndex;
            result[1] = new StaticLayout(content.substring(0, lastIndex), textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false).getHeight();
            return result;
        } else {//如果行数没有超出限制
            result[0] = -1;
            result[1] = staticLayout.getHeight();
            return result;
        }
    }

    /**


    /**
     * 设置TextView显示特定长度 超过显示省略号
     * @param tv
     */
    public static void setTextEllips(TextView tv,int len){
        String str = tv.getText().toString();
        if(!TextUtils.isEmpty(str)){
            if(str.length() > len){
                tv.setText(str.substring(0,len)+"...");
            }
        }
    }

    public static String getPriceNum(String price){
        String num = price;
        if(price.indexOf(".") != -1){
            int n = (int) Float.parseFloat(price);
            num = String.valueOf(n);
        }
        return num;
    }
    public static String getPriceDecimal(String price){
        String dic = price;
        if(price.indexOf(".") != -1){
            dic = price.substring(price.indexOf(".")+1);
            if(dic.length() == 1){
                dic = dic+"0";
            }
        } else {
            dic = "00";
        }
        return dic;
    }

}
