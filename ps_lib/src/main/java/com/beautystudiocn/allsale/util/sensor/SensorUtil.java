package com.beautystudiocn.allsale.util.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.beautystudiocn.allsale.util.sensor.internal.SensorCaptureOnce;
import com.beautystudiocn.allsale.util.sensor.internal.SensorCaptureOnceWithLock;
import com.beautystudiocn.allsale.util.sensor.internal.SensorCaptureOnce;
import com.beautystudiocn.allsale.util.sensor.internal.SensorCaptureOnceWithLock;

import java.util.List;

/**
 * <br> ClassName:SensorUtil
 * <br> Description:传感器工具类
 * <br>
 * <br> Author:      yangyinglong
 * <br> Date:        2018/6/8 11:39
 */
public class SensorUtil {
    /***Sensor管理***/
    private SensorManager mSensorManager;
    private static SensorUtil sInstance;
    private List<Sensor> mSensors;
    private SensorUtil(Context context) {
        // 获取传感器管理服务
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            //获取支持的传感器
            mSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        }
    }

    /**
     *<br> Description: 初始化
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:28
     */
    public static void init(Context context) {
        sInstance = new SensorUtil(context);
        //告诉传感器捕获类需要刷新传感器的值了
        SensorCaptureOnce.getInstance(sInstance.mSensorManager).startCapture();
    }

    /**
     *<br> Description: 异步回调获取一次指定的所有传感器的值
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:28
     */
    public static void getSensorData(OnceSensorListener sensorListener) {
        if (null == sInstance) {
            throw new IllegalStateException("You must call method init at first!");
        }
        SensorCaptureOnce.getInstance(sInstance.mSensorManager)
                .startCapture(sensorListener);
    }

    /**
     *<br> Description: 获取上一次传感器写入缓冲区的值
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/12 11:48
     */
    public static List<SensorData> getCachedSensorData() {
        if (null == sInstance) {
            throw new IllegalStateException("You must call method init at first!");
        }
        //告诉传感器捕获类需要刷新传感器的值了
        SensorCaptureOnce.getInstance(sInstance.mSensorManager).startCapture();
        Thread.yield();
        return SensorCaptureOnce.getCachedSensorData();
    }

    /**
     *<br> Description: 同步等待获取传感器的值
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/12 11:50
     */
    public static List<SensorEvent> syncGetSensorData() {
        if (null == sInstance) {
            throw new IllegalStateException("You must call method init at first!");
        }
        return new SensorCaptureOnceWithLock(sInstance.mSensorManager)
                .syncGetSensorData();
    }

    /**
     *<br> Description: 注册传感器回调监听
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:31
     */
//    public static void registerOnSensorDataListener(OnSensorListener onSensorListener) {
//        if (null == sInstance) {
//            throw new IllegalStateException("You must call method init at first!");
//        }
//    }
}
