package com.binaryic.customerapp.fashionic.models;

/**
 * Created by Asd on 07-10-2016.
 */
public class ProductModelQty {
    double qty;
    ProductModel productModel;
    int id;

    public ProductModelQty(ProductModel productModel, double qty, int id) {
        this.productModel = productModel;
        this.qty = qty;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }
}
