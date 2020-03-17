package com.beautystudiocn.allsale.util.sensor.internal;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.beautystudiocn.allsale.util.sensor.OnceSensorListener;
import com.beautystudiocn.allsale.util.sensor.SensorData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <br> ClassName:SensorCaptureOnce
 * <br> Description:获取所有传感器的值一次
 * <br>
 * <br> Author:      yangyinglong
 * <br> Date:        2018/6/8 16:57
 */
public class SensorCaptureOnce implements android.hardware.SensorEventListener{

    OnceSensorListener mSensorListener;
    private SensorManager mSensorManager;
    private List<Sensor> mSensors = new ArrayList<>();
    private Map<Integer,SensorEvent> mSensorEventMap = new ConcurrentHashMap<>();
    private boolean isDestroy = false;
    /***记录所有传感器数值都刷新一次***/
    private Map<Integer,Integer> mCapturedRecord =new HashMap<>();
    private static SensorCaptureOnce sInstance;
    private boolean capturing = false;
    private static final ExecutorService SINGLE_THREAD = Executors.newSingleThreadExecutor();
    private SensorCaptureOnce(SensorManager sensorManager) {
        mSensorManager = sensorManager;
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (null != sensor) {
            mSensors.add(sensor);
        }
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (null != sensor) {
            mSensors.add(sensor);
        }
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (null != sensor) {
            mSensors.add(sensor);
        }
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (null != sensor) {
            mSensors.add(sensor);
        }
    }

    public static SensorCaptureOnce getInstance(SensorManager sensorManager) {
        if (null == sInstance) {
            sInstance = new SensorCaptureOnce(sensorManager);
        } else {
            if (sInstance.isDestroy) {
                sInstance = new SensorCaptureOnce(sensorManager);
            }
        }
        return sInstance;
    }
    /**
     *<br> Description: todo(这里用一句话描述这个方法的作用)
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:18
     * @param sensorListener
     */
    public void startCapture(OnceSensorListener sensorListener) {
        ChangeSensorCaptureThread.changeCaptureThread();
        if (capturing) {
            return;
        }
        capturing = true;
        mSensorListener = sensorListener;
        //异步开启传感器数据捕获，防止过长时间阻塞线程
        SINGLE_THREAD.execute(new Runnable() {
            @Override
            public void run() {
                for (Sensor s : mSensors) {
                    mSensorManager.registerListener(SensorCaptureOnce.this,
                            mSensorManager.getDefaultSensor(s.getType()),
                            SensorManager.SENSOR_DELAY_GAME,ChangeSensorCaptureThread.getHandler());
                }
            }
        });
    }

    /**
     *<br> Description: todo(这里用一句话描述这个方法的作用)
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:18
     */
    public void startCapture() {
        if (capturing) {
            return;
        }
        capturing = true;
        ChangeSensorCaptureThread.changeCaptureThread();
        //异步开启传感器数据捕获，防止过长时间阻塞线程
        SINGLE_THREAD.execute(new Runnable() {
            @Override
            public void run() {
                for (Sensor s : mSensors) {
                    mSensorManager.registerListener(SensorCaptureOnce.this,
                            mSensorManager.getDefaultSensor(s.getType()),
                            SensorManager.SENSOR_DELAY_GAME,ChangeSensorCaptureThread.getHandler());
                }
            }
        });
    }

    /**
     *<br> Description: 获取缓存的数据
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/12 10:22
     */
    public static List<SensorData> getCachedSensorData() {
        if (null == sInstance) {
            throw new IllegalStateException("You must call method getInstance at first!");
        }
        List<SensorData> sensorDatas = new ArrayList<>();
        Set<Integer> key = sInstance.mSensorEventMap.keySet();
        for (Integer k : key) {
            SensorEvent sensorEvent = sInstance.mSensorEventMap.get(k);
            SensorData sensorData = new SensorData();
            sensorData.setValue(sensorEvent.values);
            sensorData.setType(sensorEvent.sensor.getType());
            sensorDatas.add(sensorData);
        }
        return sensorDatas;
    }

    /**
     *<br> Description: 传感器值变化回调
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:18
     * @param event 传感器值
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //缓存中是否已经存在这个传感器的数值，如果存在则替换，不存在则添加
        mSensorEventMap.put(event.sensor.getType(),event);
        if (!mCapturedRecord.containsKey(event.sensor.getType())) {
            mCapturedRecord.put(event.sensor.getType(),event.sensor.getType());
            mSensorManager.unregisterListener(SensorCaptureOnce.this,event.sensor);
        }
        //判断是否已经过更新了一次所有传感器的值
        if (mCapturedRecord.size() == mSensors.size()) {
            List<SensorData> sensorDatas = new ArrayList<>();
            Set<Integer> key = sInstance.mSensorEventMap.keySet();
            for (Integer k : key) {
                SensorEvent sensorEvent = sInstance.mSensorEventMap.get(k);
                SensorData sensorData = new SensorData();
                sensorData.setValue(sensorEvent.values);
                sensorData.setType(sensorEvent.sensor.getType());
                sensorDatas.add(sensorData);
            }
            if (null != mSensorListener) {
                mSensorListener.sensorOnceCapture(sensorDatas);
            }
            capturing = false;
            mCapturedRecord.clear();
        }
    }

    /**
     *<br> Description: 传感器精度变化回调
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:18
     * @param sensor 传感器
     * @param accuracy 精度
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void destroy() {
        isDestroy = true;
    }
}
