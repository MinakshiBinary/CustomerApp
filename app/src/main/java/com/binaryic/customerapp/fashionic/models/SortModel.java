package com.binaryic.customerapp.fashionic.models;

/**
 * Created by Asd on 18-10-2016.
 */
public class SortModel {
    String name;

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    String sortOrder;
    boolean isCheck;


    public SortModel(String name, boolean isCheck, String sortOrder) {
        this.name = name;
        this.isCheck = isCheck;
        this.sortOrder = sortOrder;
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
