package com.beautystudiocn.allsale.util.sensor.internal;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br> ClassName:
 * <br> Description:
 * <br>
 * <br> Author:      yangyinglong
 * <br> Date:        2018/6/11 17:17
 */
public class SensorCaptureOnceWithLock implements android.hardware.SensorEventListener{

    private SensorManager mSensorManager;
    private List<Sensor> mSensors;
    private List<SensorEvent> mSensorEvents = Collections.synchronizedList(new ArrayList<SensorEvent>());
    private Map<Integer,Integer> mCapturedRecord =new HashMap<>();
    SensorCaptureOnceWithLock.SensorThread mSensorThread = new SensorCaptureOnceWithLock.SensorThread();
    ReentrantLock mLock;
    Condition mCondition;
    public SensorCaptureOnceWithLock(SensorManager sensorManager) {
        mSensorManager = sensorManager;
        mSensors = new ArrayList<>();
        mLock = new ReentrantLock();
        mCondition = mLock.newCondition();
    }

    /**
     *<br> Description: todo(这里用一句话描述这个方法的作用)
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:18
     */
    public List<SensorEvent> syncGetSensorData() {
        ChangeSensorCaptureThread.changeCaptureThread();
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
        for (Sensor s : mSensors) {
            mSensorManager.registerListener(this, s,
                    SensorManager.SENSOR_DELAY_FASTEST, ChangeSensorCaptureThread.getHandler());
        }
        mLock.lock();
        try {
            mSensorThread.start();
            mCondition.await(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }
        return mSensorEvents;
    }

    /**
     *<br> Description: 传感器值变化回调
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:18
     * @param event 传感器值
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        mSensorThread.addSensorData(event);
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

    private class SensorThread extends Thread {
        private LinkedBlockingDeque<SensorEvent> mSensorEventArrayBlockingQueue = new LinkedBlockingDeque<>();
        private boolean running = false;
        public SensorThread() {
            setName("SensorThread");
        }
        @Override
        public void run() {
            setPriority(Thread.MAX_PRIORITY);
            running = true;
            try {
                perform();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (mLock.isHeldByCurrentThread()) {
                    mCondition.signalAll();
                    mLock.unlock();
                }
            }
        }

        private void perform() throws InterruptedException {
            while (running) {
                SensorEvent event = mSensorEventArrayBlockingQueue.take();
                if (!mCapturedRecord.containsKey(event.sensor.getType())) {
                    mCapturedRecord.put(event.sensor.getType(),event.sensor.getType());
                    mSensorManager.unregisterListener(SensorCaptureOnceWithLock.this,event.sensor);
                    mSensorEvents.add(event);
                    if (mCapturedRecord.size() == mSensors.size()) {
                        mLock.lock();
                        mCondition.signalAll();
                        mLock.unlock();
                        running = false;
                        break;
                    }
                } else {
                    for (int i = 0;i < mSensorEvents.size();i ++) {
                        if (mSensorEvents.get(i).sensor.getType() == event.sensor.getType()) {
                            mSensorEvents.set(i,event);
                        }
                    }
                }
            }
        }

        public void addSensorData(SensorEvent event) {
            mSensorEventArrayBlockingQueue.add(event);
        }
    }
}
