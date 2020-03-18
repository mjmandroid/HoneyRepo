package com.streaming.better.honey.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

import com.beautystudiocn.allsale.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作用:
 * Created by bdf on 2017/4/9.
 */

public class TimerUtil {

    private EditText et;
    private CountDownTimer timer;
    private TextView tv;

    private OnCountDownCallBack onCountDownCallBack;

    public TimerUtil(TextView tv) {
        this.tv = tv;
    }

    public TimerUtil(TextView tv, OnCountDownCallBack onCountDownCallBack) {
        this.tv = tv;
        this.onCountDownCallBack = onCountDownCallBack;
    }

    //设置倒计时
    public void setCountTimer() {
        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if(tv != null){
                    tv.setTextColor(Color.parseColor("#ffffff"));
                    tv.setText(millisUntilFinished / 1000 + "秒后重新获取");
                }
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                if(tv != null){
                    tv.setTextColor(Color.parseColor("#ffffff"));
                    tv.setText("重新获取验证码");
                }
                if(onCountDownCallBack != null){
                    onCountDownCallBack.onCountDownFinished();
                }
            }
        };
    }

    //设置倒计时
    public void setCountTimer1() {
        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if(tv != null){
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setText(millisUntilFinished / 1000 + "秒后重新获取");
                }
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                if(tv != null){
                    tv.setTextColor(Color.parseColor("#80232321"));
                    tv.setText("重新获取验证码");
                }
            }
        };
    }

    //设置倒计时
    public void setCountTimerFindPass() {
        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if(tv != null){
                    tv.setTextColor(Color.parseColor("#ff232321"));
                    tv.setText(millisUntilFinished / 1000 + "秒后重新获取");
                }
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                if(tv != null){
                    tv.setTextColor(Color.parseColor("#ff232321"));
                    tv.setText("重新获取验证码");
                }
                if(onCountDownCallBack != null){
                    onCountDownCallBack.onCountDownFinished();
                }
            }
        };
    }

    public void startTimer() {
        timer.start();
    }


    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";

    private final static long MINUTE = 60 * 1000;// 1分钟
    private final static long HOUR = 60 * MINUTE;// 1小时
    private final static long DAY = 24 * HOUR;// 1天
    private final static long MONTH = 31 * DAY;// 月
    private final static long YEAR = 12 * MONTH;// 年



    /**
     * HH:mm:ss 得到毫秒数
     * @param time
     * @return
     */
    public static long getMillisins(String time){
        long m = 0;
        String[] arr = time.split(":");
        int h = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
        int sec = Integer.parseInt(arr[2]);
        m = (min * 60 + h * 60 * 60 + sec) * 1000L;
        return m;
    }

    public static String getTimeStamp(long millisUntilFinished){
        long sec = millisUntilFinished / 1000;
        int hour = (int) (sec / 60 / 60);
        int min = (int)((sec - hour * 3600) / 60 );
        int s = (int)(sec - hour * 3600 - min * 60);
        return hour+":"+min+":"+s;
    }

    /**
     * 新增回调接口
     */
    public interface OnCountDownCallBack{
        void onCountDownFinished();
    }

    public void onTimerStop(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
