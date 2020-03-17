package com.beautystudiocn.allsale.util.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/9.
 */
public class Contacts implements Serializable {
    /**
     * 姓名
     */
    private String Name;
    /**
     * 手机号码
     */
    private String TelNo;

    public Contacts() {

    }

    public Contacts(String name, String telNo) {
        Name = name;
        TelNo = telNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelNo() {
        return TelNo;
    }

    public void setTelNo(String telNo) {
        TelNo = telNo;
    }
}
