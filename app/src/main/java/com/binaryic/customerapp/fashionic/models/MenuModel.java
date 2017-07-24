package com.binaryic.customerapp.fashionic.models;

/**
 * Created by Asd on 04-10-2016.
 */
public class MenuModel {
    String collection_id, img_src, title, header_id;
    Boolean isHeader;

    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeader_id() {
        return header_id;
    }

    public void setHeader_id(String header_id) {
        this.header_id = header_id;
    }

    public Boolean getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(Boolean isHeader) {
        this.isHeader = isHeader;
    }
}