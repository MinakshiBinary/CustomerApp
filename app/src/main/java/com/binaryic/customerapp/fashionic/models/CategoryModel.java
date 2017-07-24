package com.binaryic.customerapp.fashionic.models;

/**
 * Created by minakshi on 25/1/17.
 */

public class CategoryModel {
    private String category_Name;
    private String seller_Id;
    private String category_Id;
    private String message;

    public CategoryModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeller_Id() {
        return seller_Id;
    }

    public void setSeller_Id(String seller_Id) {
        this.seller_Id = seller_Id;
    }

    public String getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(String category_Id) {
        this.category_Id = category_Id;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public CategoryModel(String category_id, String category_name) {
        this.category_Id = category_id;
        this.category_Name = category_name;
    }
}
