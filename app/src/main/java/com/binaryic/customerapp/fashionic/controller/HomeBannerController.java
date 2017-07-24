package com.binaryic.customerapp.fashionic.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.Constants;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.models.BannerModel;
import com.binaryic.customerapp.fashionic.models.ProductModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_BANNER_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_BANNER_TYPE;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_IMAGES;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_SELLER_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_BANNER;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_BANNERS_API;
import static com.binaryic.customerapp.fashionic.controller.ProductController.addProductInDatabse;

/**
 * Created by minakshi on 14/4/17.
 */

public class HomeBannerController {
    public static void getBannerApi(final Activity context, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BANNERS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    ArrayList<ProductModel> array_Prodcts = new ArrayList<ProductModel>();

                    if (object.getInt("success") == 1) {
                        deleteBannerTable(context);
                        JSONArray jsonArray_Data = null;

                        jsonArray_Data = object.getJSONArray("slider_banner");
                        for (int i = 0; i < jsonArray_Data.length(); i++) {
                            JSONObject jsonObject = jsonArray_Data.getJSONObject(i);
                            Log.e("BannerController", "slider_banner");

                            final BannerModel bannerModel = new BannerModel();
                            bannerModel.setBanner_type("slider");
                            bannerModel.setBanner_id(jsonObject.getString("banner_id"));
                            bannerModel.setBanner_image(jsonObject.getString("banner_image"));
                            bannerModel.setCategory_id(jsonObject.getString("category_id"));
                            bannerModel.setCategory_name(jsonObject.getString("category_name"));
                            addBannerToTable(context, bannerModel);
                        }


                        jsonArray_Data = object.getJSONArray("promotional_banner");
                        for (int i = 0; i < jsonArray_Data.length(); i++) {
                            JSONObject jsonObject = jsonArray_Data.getJSONObject(i);
                            Log.e("BannerController", "promotional_banner");

                            final BannerModel bannerModel = new BannerModel();
                            bannerModel.setBanner_type("promotional");
                            bannerModel.setBanner_id(jsonObject.getString("banner_id"));
                            bannerModel.setBanner_image(jsonObject.getString("banner_image"));
                            bannerModel.setCategory_id(jsonObject.getString("category_id"));
                            bannerModel.setCategory_name(jsonObject.getString("category_name"));
                            addBannerToTable(context, bannerModel);
                        }


                        JSONArray jsonArray_productsdata = object.getJSONArray("productsdata");
                        for (int i = 0; i < jsonArray_productsdata.length(); i++) {
                            JSONObject jsonObject = jsonArray_productsdata.getJSONObject(i);
                            Log.e("BannerController", "productsdata");

                            ProductModel productModel = new ProductModel();
                            productModel.setCollection(jsonObject.getString("collection"));
                            productModel.setProduct_CategoryId(jsonObject.getString("category_id"));

                            JSONArray jsonArray_result = jsonObject.getJSONArray("result");
                            for (int j = 0; j < jsonArray_result.length(); j++) {
                                JSONObject jsonObj = jsonArray_result.getJSONObject(j);
                                Log.e("BannerController", "result");

                                productModel.setProduct_Category(jsonObj.getString("category_name"));

                                JSONArray jsonArray_products = jsonObj.getJSONArray("products");
                                for (int k = 0; k < jsonArray_products.length(); k++) {
                                    JSONObject jsonObj_Product = jsonArray_products.getJSONObject(k);
                                    Log.e("BannerController", "products");

                                    productModel.setProduct_ID(jsonObj_Product.getString("product_id"));
                                    productModel.setSeller_ID(LoginController.getSellerID(context));
                                    productModel.setProduct_Name(jsonObj_Product.getString("product_name"));
                                    productModel.setProduct_Category(jsonObj_Product.getString("product_category"));
                                    productModel.setProduct_Category(CategoryController.getCategoryIdFromTable(context, jsonObj_Product.getString("product_category")));
                                    productModel.setSelling_Price(jsonObj_Product.getString("selling_price"));
                                    productModel.setDiscount_Price(jsonObj_Product.getString("discount_price"));
                                    productModel.setProduct_Description(jsonObj_Product.getString("product_description"));
                                    productModel.setQuantity(jsonObj_Product.getString("product_quantity"));
                                    productModel.setFav(WishListController.isFavProductId(context, jsonObj_Product.getString("product_id")) ? true : false);
                                    productModel.setSKUCode(jsonObj_Product.getString("sku_code"));

                                    JSONArray jsonArray_Product_Images = jsonObj_Product.getJSONArray("product_images");
                                    ArrayList<String> images_Array = new ArrayList<String>();

                                    for (int l = 0; l < jsonArray_Product_Images.length(); l++) {
                                        JSONObject jsonObj_Product_Images = jsonArray_Product_Images.getJSONObject(l);
                                        images_Array.add(jsonObj_Product_Images.getString("images"));
                                        productModel.setProduct_Image_Array(images_Array);

                                    }

                                }

                            }

                            addProductInDatabse(context, productModel);

                            array_Prodcts.add(productModel);
                        }


                        callback.onSuccess(array_Prodcts);

                    } else {
                        callback.onError("responce is 0 please check responce");

                    }

                } catch (Exception e) {
                    callback.onError(e.getMessage());

                    Log.e("errrorrrr", e.toString());
                }

            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getBanner", error.toString());
                callback.onError(error.getMessage());

            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", Constants.seller_id);

                Log.e("getBanner", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().

                addToRequestQueue(stringRequest, "getBanner");


    }

    private static void deleteBannerTable(Activity context) {
        context.getContentResolver().delete(CONTENT_BANNER, null, null);
    }



    private static void addBannerToTable(Activity context, BannerModel bannerModel) {
        Log.e("BannerController", "addBannerToTable");

        ContentValues values = new ContentValues();

        values.put(COLUMN_SELLER_ID, Constants.seller_id);
        values.put(COLUMN_BANNER_ID, bannerModel.getBanner_id());
        values.put(COLUMN_IMAGES, bannerModel.getBanner_image());
        Log.e("BannerController", "addBannerToTable ==" + bannerModel.getBanner_type());

        //values.put(COLUMN_NAME, bannerModel.getBanner_type());
        values.put(COLUMN_BANNER_TYPE, bannerModel.getBanner_type());
        values.put(COLUMN_CATEGORY_ID, bannerModel.getCategory_id());
        values.put(COLUMN_CATEGORY_NAME, bannerModel.getCategory_name());

        String selection = COLUMN_BANNER_ID + " ='" + bannerModel.getBanner_id() + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_BANNER, null, selection, null, null);
        if (cursor.getCount() > 0) {
            context.getContentResolver().update(CONTENT_BANNER, values, selection, null);
        } else {
            context.getContentResolver().insert(CONTENT_BANNER, values);
        }
    }

    public static int getSliderBannerCount(Context context) {

        String selection = COLUMN_BANNER_TYPE + " = '" + "slider" + "'";

        Cursor cursor = context.getContentResolver().query(CONTENT_BANNER, null, selection, null, null);

        return cursor.getCount();
    }

    public static int getPromotionalBannerCount(Context context) {

        String selection = COLUMN_BANNER_TYPE + " = '" + "promotional" + "'";

        Cursor cursor = context.getContentResolver().query(CONTENT_BANNER, null, selection, null, null);

        return cursor.getCount();
    }

    public static ArrayList<BannerModel> getAllSliderBannerFromDatabase(Activity context) {

        ArrayList<BannerModel> array_Banner_Model = new ArrayList<BannerModel>();
        String selection = COLUMN_BANNER_TYPE + " = '" + "slider" + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_BANNER, null, selection, null, null);
        Log.e("BannerController", "cursor =" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                BannerModel bannerModel = new BannerModel();
                bannerModel.setBanner_type(cursor.getString(cursor.getColumnIndex(COLUMN_BANNER_TYPE)));
                bannerModel.setCategory_name(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME)));
                bannerModel.setCategory_id(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID)));
                bannerModel.setBanner_id(cursor.getString(cursor.getColumnIndex(COLUMN_BANNER_ID)));
                bannerModel.setBanner_image(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGES)));

                array_Banner_Model.add(bannerModel);
            }
        }
        Log.e("BannerController", "array_Banner_Model =" + array_Banner_Model.size());
        return array_Banner_Model;
    }

    public static ArrayList<BannerModel> getAllPromotionalBannerFromDatabase(Activity context) {

        ArrayList<BannerModel> array_Banner_Model = new ArrayList<BannerModel>();
        String selection = COLUMN_BANNER_TYPE + " = '" + "promotional" + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_BANNER, null, selection, null, null);
        Log.e("BannerController", "cursor =" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                BannerModel bannerModel = new BannerModel();
                bannerModel.setBanner_type(cursor.getString(cursor.getColumnIndex(COLUMN_BANNER_TYPE)));
                bannerModel.setCategory_name(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME)));
                bannerModel.setCategory_id(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID)));
                bannerModel.setBanner_id(cursor.getString(cursor.getColumnIndex(COLUMN_BANNER_ID)));
                bannerModel.setBanner_image(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGES)));

                array_Banner_Model.add(bannerModel);
            }
        }
        Log.e("BannerController", "array_Banner_Model =" + array_Banner_Model.size());
        return array_Banner_Model;
    }

}
