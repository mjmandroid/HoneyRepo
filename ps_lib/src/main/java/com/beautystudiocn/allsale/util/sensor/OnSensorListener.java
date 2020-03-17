package com.beautystudiocn.allsale.util.sensor;

import android.hardware.SensorEvent;

import java.util.List;

/**
 * <br> ClassName:OnSensorListener
 * <br> Description:传感器数据回调
 * <br>
 * <br> Author:      yangyinglong
 * <br> Date:        2018/6/11 10:32
 */
public interface OnSensorListener {
    /**
     *<br> Description: 传感器数据回调
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:32
     * @param data 传感器数据
     */
    void onSensorCapture(List<SensorEvent> data);
}
