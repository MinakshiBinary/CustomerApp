package com.binaryic.customerapp.fashionic.models;

/**
 * Created by Asd on 20-09-2016.
 */
public class FilterModel {
    String name;
    boolean isCheck;

    public FilterModel(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
