package com.beautystudiocn.allsale.util.sensor.internal;

import android.os.Handler;
import android.os.Looper;

/**
 * <br> ClassName:
 * <br> Description:
 * <br>
 * <br> Author:      yangyinglong
 * <br> Date:        2018/6/11 19:44
 */
public class ChangeSensorCaptureThread {
    private static Handler handler;
    private static Object lock = new Object();
    ChangeSensorCaptureThread(int i) {

    }
    public static Handler getHandler() {
        if (null == handler) {
            throw new IllegalStateException("You must call method changeCaptureThread at first!");
        }
        return handler;
    }

    /**
     *<br> Description: 改变传感器回调线程
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 20:25
     */
    public static void changeCaptureThread() {
        if (null != handler) {
            return;
        }
        //传感器真正回调的线程
        final Thread thread = new Thread() {
            @Override
            public void run() {
                setPriority(Thread.MAX_PRIORITY);
                Looper.prepare();
                handler = new Handler();
                synchronized (lock) {
                    lock.notify();
                }
                Looper.loop();
            }
        };
        thread.setName("Sensor callback Dispacher");
        thread.start();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
