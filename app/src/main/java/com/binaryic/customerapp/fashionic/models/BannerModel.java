package com.binaryic.customerapp.fashionic.models;

/**
 * Created by HP on 03-Apr-17.
 */

public class BannerModel {
    String banner_id;
    String banner_image;
    String category_id;
    String category_name;
    int sequence;
    String banner_type;

    public BannerModel() {
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public BannerModel(String banner_id, String banner_image, String category_id, String category_name) {
        this.banner_id = banner_id;
        this.banner_image = banner_image;
        this.category_id = category_id;
        this.category_name = category_name;
    }
}
