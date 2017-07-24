package com.binaryic.customerapp.fashionic.models;

import java.util.ArrayList;

/**
 * Created by Asd on 28-09-2016.
 */
public class ProductModel {

    private int color;
    private int size;
    private String delivery_Charges;
    private String product_ID;
    private String seller_ID;
    private String product_Name;
    private String selling_Price;
    private String product_Description;
    private String product_Category;
    private String product_CategoryId;
    private String total_selling_Price;
    private String discount_Price;
    private String quantity;
    private String SKUCode;
    private String sort;
    private String collection;
    private boolean isFav;
    String average_rating = "0";
    String total_review = "0";

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getTotal_review() {
        return total_review;
    }

    public void setTotal_review(String total_review) {
        this.total_review = total_review;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public String getProduct_CategoryId() {
        return product_CategoryId;
    }

    public void setProduct_CategoryId(String product_CategoryId) {
        this.product_CategoryId = product_CategoryId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDelivery_Charges() {
        return delivery_Charges;
    }

    public void setDelivery_Charges(String delivery_Charges) {
        this.delivery_Charges = delivery_Charges;
    }

    public String getTotal_selling_Price() {
        return total_selling_Price;
    }

    public void setTotal_selling_Price(String total_selling_Price) {
        this.total_selling_Price = total_selling_Price;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSKUCode() {
        return SKUCode;
    }

    public void setSKUCode(String SKUCode) {
        this.SKUCode = SKUCode;
    }

    private ArrayList<String> product_Image_Array;

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getSeller_ID() {
        return seller_ID;
    }

    public void setSeller_ID(String seller_ID) {
        this.seller_ID = seller_ID;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getProduct_Description() {
        return product_Description;
    }

    public void setProduct_Description(String product_Description) {
        this.product_Description = product_Description;
    }

    public String getProduct_Category() {
        return product_Category;
    }

    public void setProduct_Category(String product_Category) {
        this.product_Category = product_Category;
    }

    public String getSelling_Price() {
        return selling_Price;
    }

    public void setSelling_Price(String selling_Price) {
        this.selling_Price = selling_Price;
    }

    public String getDiscount_Price() {
        return discount_Price;
    }

    public void setDiscount_Price(String discount_Price) {
        this.discount_Price = discount_Price;
    }

    public ArrayList<String> getProduct_Image_Array() {
        return product_Image_Array;
    }

    public void setProduct_Image_Array(ArrayList<String> product_Image_Array) {
        this.product_Image_Array = product_Image_Array;
    }




    /*
    String id,title,body_html,product_type,tags,compare_at_price,barcode,price,variant_id,sku;
    Boolean isFav = false;
    ArrayList<String> Images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody_html() {
        return body_html;
    }

    public void setBody_html(String body_html) {
        this.body_html = body_html;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCompare_at_price() {
        return compare_at_price;
    }

    public void setCompare_at_price(String compare_at_price) {
        this.compare_at_price = compare_at_price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(String variant_id) {
        this.variant_id = variant_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(Boolean isFav) {
        this.isFav = isFav;
    }

    public ArrayList<String> getImages() {
        return Images;
    }

    public void setImages(ArrayList<String> images) {
        Images = images;
    }*/



}
