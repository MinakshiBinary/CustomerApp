package com.binaryic.customerapp.fashionic.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.binaryic.customerapp.fashionic.models.ProductModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_IS_FAV;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_PRODUCT_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_WISHLIST_PRODUCT;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_PRODUCT;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_WISHLIST;

/**
 * Created by Asd on 29-09-2016.
 */
public class WishListController {

    public static void addProduct(Activity context, ProductModel productModel) {
        Log.e("like", "like");


        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, productModel.getProduct_ID());
        values.put(COLUMN_WISHLIST_PRODUCT, new Gson().toJson(productModel));
        String selection = COLUMN_PRODUCT_ID + " ='" + productModel.getProduct_ID() + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_WISHLIST, null, selection, null, null);
        if (cursor.getCount() > 0) {
            context.getContentResolver().update(CONTENT_WISHLIST, values, selection, null);
        } else {
            context.getContentResolver().insert(CONTENT_WISHLIST, values);
        }


        ContentValues val = new ContentValues();
        val.put(COLUMN_PRODUCT_ID, productModel.getProduct_ID());
        val.put(COLUMN_IS_FAV, true);
        Cursor c = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        if (c.getCount() > 0) {
            context.getContentResolver().update(CONTENT_PRODUCT, val, selection, null);
        } else {
            context.getContentResolver().insert(CONTENT_PRODUCT, val);
        }


    }

    public static void deleteProduct(Activity context, ProductModel productModel) {
        Log.e("like", "unlike");
        String selection = COLUMN_PRODUCT_ID + " = '" + productModel.getProduct_ID() + "'";
        context.getContentResolver().delete(CONTENT_WISHLIST, selection, null);


        ContentValues val = new ContentValues();
        val.put(COLUMN_PRODUCT_ID, productModel.getProduct_ID());
        val.put(COLUMN_IS_FAV, false);
        Cursor c = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        if (c.getCount() > 0) {
            context.getContentResolver().update(CONTENT_PRODUCT, val, selection, null);
        } else {
            context.getContentResolver().insert(CONTENT_PRODUCT, val);
        }

    }

    public static String getFavProductId(Activity context) {
        String ids = "";

        Cursor cursor = context.getContentResolver().query(CONTENT_WISHLIST, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ids = ids + "," + cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
            }
            ids.substring(1);
        }

        return ids;
    }

    public static boolean isFavProductId(Activity context, String productId) {

        String selection = COLUMN_PRODUCT_ID + " ='" + productId + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_WISHLIST, null, selection, null, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<ProductModel> getFavProducts(Activity context) {
        ArrayList<ProductModel> productList = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(CONTENT_WISHLIST, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ProductModel productModel = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_WISHLIST_PRODUCT)), ProductModel.class);
                productModel.setFav(true);
                Log.e("like", "productModel.setIsFav(true)");
                productList.add(productModel);
            }
        }

        return productList;
    }

    public static int getWishCount(Activity context) {
        int count = 0;

        Cursor cursor = context.getContentResolver().query(CONTENT_WISHLIST, null, null, null, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }
}
