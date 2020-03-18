package com.streaming.better.honey.wedget.wheelview.model;

public class DistrictModel {
    private String name;
    private int zipcode;

    public DistrictModel() {
        super();
    }

    public DistrictModel(String name, int zipcode) {
        super();
        this.name = name;
        this.zipcode = zipcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "DistrictModel [name=" + name + ", zipcode=" + zipcode + "]";
    }

}
