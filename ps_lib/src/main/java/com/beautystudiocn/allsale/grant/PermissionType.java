package com.beautystudiocn.allsale.grant;

import android.Manifest;

/**
 * <br> ClassName:   PermissionType
 * <br> Description: 权限枚举
 * <br>
 * <br> Author:      wujun
 * <br> Date:        2017/8/3 9:48
 */
public enum  PermissionType {


    READ_CALENDAR(Manifest.permission.READ_CALENDAR, "读取日历"),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR,"写入日历"),

    CAMERA(Manifest.permission.CAMERA, "打开相机"),

    READ_CONTACTS(Manifest.permission.READ_CONTACTS, "读取通讯录"),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS, "写入通讯录"),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS, "访问账户"),

    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION, "获取粗略定位"),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION, "获取位置"),

    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO, "麦克风"),


    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE, "读取手机状态"),
    CALL_PHONE(Manifest.permission.CALL_PHONE, "打电话"),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG, "查看通话记录"),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG, "写入通话记录"),
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL, "添加语音信箱"),
    USE_SIP(Manifest.permission.USE_SIP, "使用SIP"),
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS, "过程输出调用"),

    BODY_SENSORS(Manifest.permission.BODY_SENSORS,"传感器"),

    SEND_SMS(Manifest.permission.SEND_SMS, "发送短信"),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS, "接收短信"),
    READ_SMS(Manifest.permission.READ_SMS, "读取短信"),
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH, "收到WAP推送"),
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS, " 接收彩信"),
    BROADCAST_SMS(Manifest.permission.BROADCAST_SMS, "读取广播"),

    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE, " 获取存储空间"),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入存储空间");


    /**权限对应Manifest中的值**/
    private String key;
    /**权限配置名**/
    private String value;



    PermissionType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    /**
     *<br> Description: 获取权限对应的中文名字
     *<br> Author:      wujun
     *<br> Date:        2017/8/3 9:52
     */
    static public String getValueByKey(String key) {
        for (PermissionType permissionType : PermissionType.values()) {
            if (key.equals(permissionType.getKey())) {
                return permissionType.getValue();
            }
        }
        return null;
    }


    static public PermissionType newInstance(String key) {
        for (PermissionType permissionType : PermissionType.values()) {
            if (key.equals(permissionType.getKey())) {
                return permissionType;
            }
        }
        return null;
    }
}

