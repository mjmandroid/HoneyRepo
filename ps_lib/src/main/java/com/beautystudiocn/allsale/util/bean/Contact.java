package com.beautystudiocn.allsale.util.bean;

import java.util.ArrayList;

/**
 * 手机联系人
 *
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2015 Tuandai Inc. All rights reserved.
 * @date: 2015/9/29 10:51
 */
public class Contact {
    private String name;
    private ArrayList<String> phoneNums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhoneNums() {
        return phoneNums;
    }

    public void setPhoneNums(ArrayList<String> phoneNums) {
        this.phoneNums = phoneNums;
    }
}
