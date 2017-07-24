package com.binaryic.customerapp.fashionic.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.binaryic.customerapp.fashionic.models.ProductModel;
import com.binaryic.customerapp.fashionic.models.ProductModelQty;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CART_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CART_QTY;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_PRODUCT_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.*;


/**
 * Created by Asd on 06-10-2016.
 */
public class CartController {


    public static void addProduct(Activity context, ProductModel productModel, String count) {

        String selection = COLUMN_PRODUCT_ID + " = '" + productModel.getProduct_ID() + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            ContentValues values = new ContentValues();
            values.put(COLUMN_PRODUCT_ID, productModel.getProduct_ID());
            if (Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_CART_QTY))) > 4) {
                values.put(COLUMN_CART_QTY, 5);
            } else if (Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_CART_QTY))) >= Double.parseDouble(productModel.getQuantity())) {
                values.put(COLUMN_CART_QTY, (Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_CART_QTY)))));

            } else if (Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_CART_QTY))) < Double.parseDouble(productModel.getQuantity())) {
                values.put(COLUMN_CART_QTY, (Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_CART_QTY))) + Double.parseDouble(count)));
            }

            int upd = context.getContentResolver().update(CONTENT_CART, values, selection, null);
            Log.e("upd", upd + "");
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PRODUCT_ID, productModel.getProduct_ID());
            values.put(COLUMN_NAME, new Gson().toJson(productModel));
            values.put(COLUMN_CART_QTY, count);
            context.getContentResolver().insert(CONTENT_CART, values);
        }

    }

    public static boolean getSingleProduct(Activity context, String product_Id) {
        boolean availalbe = false;
        Log.e("product_Id", product_Id);
        String selection = COLUMN_PRODUCT_ID + " = '" + product_Id + "'";
        Log.e("selection", selection);
        Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, selection, null, null);
        if (cursor.getCount() > 0) {
            availalbe = true;
        }
        return availalbe;
    }

    public static double getProductCount(Activity context, String product_Id) {
        double count = 1;
        Log.e("product_Id", product_Id);
        String selection = COLUMN_PRODUCT_ID + " = '" + product_Id + "'";
        Log.e("selection", selection);
        Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            count = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_CART_QTY)));
        }
        return count;
    }

    public static void changeQty(Activity context, ProductModelQty productModelQty, int qty) {

        String selection = COLUMN_CART_ID + " = '" + productModelQty.getId() + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            ContentValues values = new ContentValues();
            values.put(COLUMN_CART_ID, productModelQty.getId());
            values.put(COLUMN_PRODUCT_ID, productModelQty.getProductModel().getProduct_ID());
            values.put(COLUMN_NAME, new Gson().toJson(productModelQty.getProductModel()));
            values.put(COLUMN_CART_QTY, String.valueOf(qty));
            int upd = context.getContentResolver().update(CONTENT_CART, values, selection, null);
            Log.e("upd", upd + "");
        }


    }

    public static void deleteProduct(Activity context, ProductModelQty productModelQty) {

        String selection = COLUMN_CART_ID + " = '" + productModelQty.getId() + "'";
        int del = context.getContentResolver().delete(CONTENT_CART, selection, null);
        Log.e("del", del + "");

    }

    public static void removeCart(Activity context) {

        context.getContentResolver().delete(CONTENT_CART, null, null);

    }

    public static ArrayList<ProductModelQty> getCartProducts(Activity context) {
        ArrayList<ProductModelQty> productList = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ProductModel productModel = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)), ProductModel.class);
                productList.add(new ProductModelQty(productModel, Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_CART_QTY))), cursor.getInt(cursor.getColumnIndex(COLUMN_CART_ID))));
            }
        }
        cursor.close();

        return productList;
    }

    public static int getCartCount(Activity context) {
        int count = 0;

        Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, null, null, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public static String getProductInfo(Activity context) {
        String ids = "";

        Cursor cursor = context.getContentResolver().query(CONTENT_CART, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ids = ids + "," + cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
            }
            ids.substring(1);
        }

        return ids;
    }
}
