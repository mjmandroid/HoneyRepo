package com.beautystudiocn.allsale.util.sensor;

import java.util.List;

/**
 * <br> ClassName:
 * <br> Description:
 * <br>
 * <br> Author:      yangyinglong
 * <br> Date:        2018/6/8 16:53
 */
public interface OnceSensorListener {
    /**
     *<br> Description: 获取一次所有传感器的值回调
     *<br> Author:      yangyinglong
     *<br> Date:        2018/6/11 10:34
     * @param data 传感器数据
     */
    void sensorOnceCapture(List<SensorData> data);
}
