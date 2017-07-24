package com.binaryic.customerapp.fashionic.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.Constants;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.models.ProductModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_COLLECTION;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_DESCRIPTION;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_DISCOUNT_PRICE;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_IMAGES;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_IS_FAV;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_PRODUCT_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_QUANTITY;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_SELLER_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_SELL_PRICE;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_SKU_CODE;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_PRODUCT;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_CATEGORY_PRODUCTS_API;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_PRODUCTS_API;
import static com.binaryic.customerapp.fashionic.common.Constants.page;

/**
 * Created by user on 22-Jan-17.
 */

public class ProductController {

    public static int getProductCategoryTableCount(Activity context, String category_Id) {

        String selection = COLUMN_CATEGORY_ID + " = '" + category_Id + "'" + " AND " + COLUMN_QUANTITY + " != '0'";

        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);

        return cursor.getCount();

    }

    public void abuseApiCall(final Activity context, final String product_id, final String title, final String message, final CallBackResult<Boolean> callBackResult) {
        String url = MyApplication.ApiUrl + "report_abuse";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess(true);
                    } else {
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Error");
                Log.e("BannerErrorResponse", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("product_id", product_id);
                params.put("message", message);
                params.put("title", title);
                params.put("customer_id", LoginController.getCustomerID(context));
                Log.e("BannerErrorResponse", "params=" + params);

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "BannerApiCall");
    }


   /* private static void addTags(Context context, String product_id, String collection_id, String tags) {
        try {
            ContentValues values = new ContentValues();
            values.put(TAG_COLLECTION_ID, collection_id);
            values.put(TAG_PRODUCT_ID, product_id);
            values.put(TAGS, tags);
            context.getContentResolver().insert(CONTENT_TAGS, values);
        } catch (Exception ex) {
        }
    }

    public HashSet<String> GetTags(Context context) {
        ArrayList<String> tags = new ArrayList<>();
        HashSet<String> hsTags = new HashSet();
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_TAGS, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    for (String tag : (cursor.getString(cursor.getColumnIndex(TAGS))).split(",")) {
                        hsTags.add(tag.trim());
                    }

                    //tags.add(cursor.getString(cursor.getColumnIndex(TAGS)));
                }
            }
        } catch (Exception ex) {
        }
        return hsTags;
    }

    public HashSet<String> GetTagsSelected(Context context, ArrayList<String> tagSelected) {
        ArrayList<String> tags = new ArrayList<>();
        HashSet<String> hsTags = new HashSet();
        Log.e("SelectedTags", tagSelected.toString());
        try {
            String arg = "";
            for (int i = 0; i < tagSelected.size(); i++) {
                if (i == 0)
                    arg = arg + TAGS + " like '%" + tagSelected.get(i) + "%'";
                else
                    arg = arg + " and " + TAGS + " like '%" + tagSelected.get(i) + "%'";
            }
            Log.e("args", arg);
            Cursor cursor = context.getContentResolver().query(CONTENT_TAGS, null, arg, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    for (String tag : (cursor.getString(cursor.getColumnIndex(TAGS))).split(",")) {
                        hsTags.add(tag.trim());
                    }
                }
            }
        } catch (Exception ex) {
        }
        return hsTags;
    }

    public String GetMatchTag(Context context, String filterValue) {
        String type = "";
        try {
            String selection = FILTER_VALUE + " == '" + filterValue + "'";
            Cursor cursor = context.getContentResolver().query(CONTENT_FILTER, null, selection, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                type = cursor.getString(cursor.getColumnIndex(FILTER_TYPE));
            }
        } catch (Exception ex) {
            String temp = ex.getMessage();
        }
        return type;
    }*/

    public static void getAllProducts(final Activity context, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_PRODUCTS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    ArrayList<ProductModel> array_Prodcts = new ArrayList<ProductModel>();

                    if (object.getInt("success") == 1) {
                        JSONArray jsonArray_Data = object.getJSONArray("Productdata");
                        deleteAllProductTable(context);
                        for (int i = 0; i < jsonArray_Data.length(); i++) {
                            JSONObject jsonObject = jsonArray_Data.getJSONObject(i);

                            final ProductModel productModel = new ProductModel();

                            productModel.setProduct_ID(jsonObject.getString("product_id"));
                            // productModel.setSeller_ID(jsonObject.getString("seller_id"));
                            productModel.setSeller_ID(LoginController.getSellerID(context));
                            productModel.setProduct_Name(jsonObject.getString("product_name"));
                            productModel.setProduct_Category(jsonObject.getString("product_category"));
                            productModel.setProduct_Category(CategoryController.getCategoryIdFromTable(context, jsonObject.getString("product_category")));
                            productModel.setSelling_Price(jsonObject.getString("selling_price"));
                            productModel.setDiscount_Price(jsonObject.getString("discount_price"));
                            productModel.setProduct_Description(jsonObject.getString("product_description"));
                            productModel.setQuantity(jsonObject.getString("product_quantity"));
                            productModel.setFav(WishListController.isFavProductId(context, jsonObject.getString("product_id")) ? true : false);
                            productModel.setSKUCode(jsonObject.getString("sku_code"));

                            JSONArray jsonArray_Product_Images = jsonObject.getJSONArray("product_images");
                            ArrayList<String> images_Array = new ArrayList<String>();

                            for (int j = 0; j < jsonArray_Product_Images.length(); j++) {
                                JSONObject jsonObject_Images = jsonArray_Product_Images.getJSONObject(j);
                                images_Array.add(jsonObject_Images.getString("image"));


                            }
                            productModel.setProduct_Image_Array(images_Array);

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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BannerErrorResponse", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", Constants.seller_id);
                params.put("sort", "");
                params.put("page", String.valueOf(page));
                params.put("limit", String.valueOf(Constants.PAGE_SIZE));

                Log.e("Comment Activity", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");


    }

    public static void getCategoryProducts(final Activity context, final String category_Id, final int page_count, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_CATEGORY_PRODUCTS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    ArrayList<ProductModel> array_Prodcts = new ArrayList<ProductModel>();

                    if (object.getInt("success") == 1) {
                        JSONArray jsonArray_Data = object.getJSONArray("products");

                        for (int i = 0; i < jsonArray_Data.length(); i++) {
                            JSONObject jsonObject = jsonArray_Data.getJSONObject(i);

                            final ProductModel productModel = new ProductModel();

                            productModel.setProduct_ID(jsonObject.getString("product_id"));
                            productModel.setSeller_ID(jsonObject.getString("seller_id"));
                            productModel.setProduct_Name(jsonObject.getString("product_name"));
                            productModel.setProduct_Category(jsonObject.getString("product_category"));
                            productModel.setSelling_Price(jsonObject.getString("selling_price"));
                            productModel.setDiscount_Price(jsonObject.getString("discount_price"));
                            productModel.setProduct_Description(jsonObject.getString("product_description"));
                            productModel.setQuantity(jsonObject.getString("product_quantity"));
                            Log.e("ProductController", "product_quantity==" + jsonObject.getString("product_quantity"));

                            productModel.setFav(WishListController.isFavProductId(context, jsonObject.getString("product_id")) ? true : false);

                            productModel.setSKUCode(jsonObject.getString("sku_code"));

                            JSONArray jsonArray_Product_Images = jsonObject.getJSONArray("product_images");
                            ArrayList<String> images_Array = new ArrayList<String>();

                            for (int j = 0; j < jsonArray_Product_Images.length(); j++) {
                                JSONObject jsonObject_Images = jsonArray_Product_Images.getJSONObject(j);
                                images_Array.add(jsonObject_Images.getString("images"));
                            }
                            productModel.setProduct_Image_Array(images_Array);

                            if (Double.parseDouble(jsonObject.getString("product_quantity")) > 0) {
                                array_Prodcts.add(productModel);
                                addProductInDatabse(context, productModel);
                            }
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BannerErrorResponse", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", Constants.seller_id);
                params.put("category_id", category_Id);
                params.put("limit", Constants.PAGE_SIZE);
                params.put("page", page_count + "");


                Log.e("Comment Activity", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");
    }

    public static void getFilterProducts(final Activity context, final String sort_category, final String price_range, final String select_category, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_CATEGORY_PRODUCTS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    ArrayList<ProductModel> array_Prodcts = new ArrayList<ProductModel>();

                    if (object.getInt("success") == 1) {
                        JSONArray jsonArray_Data = object.getJSONArray("products");
                        //deleteAllProductTable(context);

                        for (int i = 0; i < jsonArray_Data.length(); i++) {
                            JSONObject jsonObject = jsonArray_Data.getJSONObject(i);

                            final ProductModel productModel = new ProductModel();

                            productModel.setProduct_ID(jsonObject.getString("product_id"));
                            productModel.setSeller_ID(jsonObject.getString("seller_id"));
                            productModel.setProduct_Name(jsonObject.getString("product_name"));
                            productModel.setProduct_Category(jsonObject.getString("product_category"));
                            productModel.setProduct_Category(CategoryController.getCategoryIdFromTable(context, jsonObject.getString("product_category")));
                            productModel.setSelling_Price(jsonObject.getString("selling_price"));
                            productModel.setDiscount_Price(jsonObject.getString("discount_price"));
                            productModel.setProduct_Description(jsonObject.getString("product_description"));
                            productModel.setQuantity(jsonObject.getString("product_quantity"));
                            productModel.setFav(WishListController.isFavProductId(context, jsonObject.getString("product_id")) ? true : false);
                            productModel.setSKUCode(jsonObject.getString("sku_code"));

                            JSONArray jsonArray_Product_Images = jsonObject.getJSONArray("product_images");
                            ArrayList<String> images_Array = new ArrayList<String>();

                            for (int j = 0; j < jsonArray_Product_Images.length(); j++) {
                                JSONObject jsonObject_Images = jsonArray_Product_Images.getJSONObject(j);
                                images_Array.add(jsonObject_Images.getString("images"));
                            }
                            productModel.setProduct_Image_Array(images_Array);
//it should be store in different table or directly show these products
                            // addProductInDatabse(context, productModel);

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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BannerErrorResponse", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", Constants.seller_id);
                params.put("sort_category", sort_category);
                params.put("price_range", price_range);
                params.put("select_category", select_category);

                Log.e("Comment Activity", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");
    }

    public static void addProductInDatabse(Activity context, ProductModel productModel) {
        Log.e("ProductController", "addProductInDatabse==");

        ContentValues values = new ContentValues();
        values.put(COLUMN_COLLECTION, productModel.getCollection());
        values.put(COLUMN_NAME, productModel.getProduct_Name());
        values.put(COLUMN_PRODUCT_ID, productModel.getProduct_ID());
        values.put(COLUMN_SELLER_ID, productModel.getSeller_ID());
        values.put(COLUMN_CATEGORY, productModel.getProduct_Category());
        values.put(COLUMN_CATEGORY_ID, productModel.getProduct_Category());
        values.put(COLUMN_DESCRIPTION, productModel.getProduct_Description());
        values.put(COLUMN_DISCOUNT_PRICE, productModel.getDiscount_Price());
        values.put(COLUMN_SKU_CODE, productModel.getSKUCode());
        values.put(COLUMN_QUANTITY, productModel.getQuantity());
        Log.e("COLUMN_QUANTITY", "Quantity==" + productModel.getQuantity());
        values.put(COLUMN_IS_FAV, productModel.isFav());

        Log.e("COLUMN_IMAGES", "==" + new Gson().toJson(productModel.getProduct_Image_Array()));
        values.put(COLUMN_IMAGES, new Gson().toJson(productModel.getProduct_Image_Array()));
        values.put(COLUMN_SELL_PRICE, productModel.getSelling_Price());


        String selection = COLUMN_PRODUCT_ID + " = '" + productModel.getProduct_ID() + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        if (cursor.getCount() > 0)
            context.getContentResolver().update(CONTENT_PRODUCT, values, selection, null);
        else
            context.getContentResolver().insert(CONTENT_PRODUCT, values);

    }


    public static ArrayList<ProductModel> getListFromArrayProduct(JSONArray products, String count) {
        ArrayList<ProductModel> productModels = new ArrayList<>();
        try {
            for (int i = 0; i < products.length(); i++) {
                ProductModel productModel = new ProductModel();
                productModel.setProduct_ID(products.getJSONObject(i).getString("product_id"));
                productModel.setProduct_Name(products.getJSONObject(i).getString("product_name"));
                productModel.setProduct_Description(products.getJSONObject(i).getString("product_description"));
                productModel.setSelling_Price(products.getJSONObject(i).getString("selling_price"));
                productModel.setDiscount_Price(products.getJSONObject(i).getString("discount_price"));
                productModel.setSKUCode(products.getJSONObject(i).getString("sku_code"));
                productModel.setQuantity(products.getJSONObject(i).getString("product_quantity"));
                productModel.setAverage_rating(products.getJSONObject(i).getString("average_rating"));
                productModel.setTotal_review(products.getJSONObject(i).getString("rating_count"));
                JSONArray product_images = products.getJSONObject(i).getJSONArray("product_images");
                ArrayList<String> product_image = new ArrayList<>();
                for (int j = 0; j < product_images.length(); j++) {
                    product_image.add(product_images.getJSONObject(j).getString("images"));
                }
                productModel.setProduct_Image_Array(product_image);

                if (!count.matches("all")) {
                    if (productModels.size() < Integer.parseInt(count))
                        productModels.add(productModel);

                } else {
                    productModels.add(productModel);

                }


            }
        } catch (Exception ex) {
        }
        return productModels;
    }


    public static ArrayList<ProductModel> getAllProductsFromDatabase(Activity context, String count) {


        ArrayList<ProductModel> array_Product_Model = new ArrayList<ProductModel>();
        String selection = COLUMN_QUANTITY + " != '0'";
        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        Log.e("ProductController", "cursor =" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                ProductModel productModel = new ProductModel();
                productModel.setCollection(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION)));
                productModel.setProduct_ID(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
                productModel.setSeller_ID(cursor.getString(cursor.getColumnIndex(COLUMN_SELLER_ID)));
                productModel.setSelling_Price(cursor.getString(cursor.getColumnIndex(COLUMN_SELL_PRICE)));
                productModel.setDiscount_Price(cursor.getString(cursor.getColumnIndex(COLUMN_DISCOUNT_PRICE)));
                productModel.setProduct_Category(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                productModel.setProduct_Description(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                productModel.setSKUCode(cursor.getString(cursor.getColumnIndex(COLUMN_SKU_CODE)));
                productModel.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
                productModel.setFav(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAV))));

                ArrayList<String> product_Image_Array = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGES)), new TypeToken<ArrayList<String>>() {
                }.getType());
                productModel.setProduct_Image_Array(product_Image_Array);
                productModel.setProduct_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                if (!count.matches("all")) {
                    if (array_Product_Model.size() < Integer.parseInt(count))
                        array_Product_Model.add(productModel);

                } else {
                    array_Product_Model.add(productModel);

                }
            }
        }
        Log.e("ProductController", "array_Product_Model =" + array_Product_Model.size());
        return array_Product_Model;
    }

    public static ArrayList<ProductModel> getAllProductsofCollection(Activity context, String collection) {


        ArrayList<ProductModel> array_Product_Model = new ArrayList<ProductModel>();
        String selection = COLUMN_COLLECTION + " = '" + collection + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        Log.e("ProductController", "cursor =" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                ProductModel productModel = new ProductModel();
                productModel.setCollection(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION)));
                productModel.setProduct_ID(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
                productModel.setSeller_ID(cursor.getString(cursor.getColumnIndex(COLUMN_SELLER_ID)));
                productModel.setSelling_Price(cursor.getString(cursor.getColumnIndex(COLUMN_SELL_PRICE)));
                productModel.setDiscount_Price(cursor.getString(cursor.getColumnIndex(COLUMN_DISCOUNT_PRICE)));
                productModel.setProduct_Category(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                productModel.setProduct_Description(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                productModel.setSKUCode(cursor.getString(cursor.getColumnIndex(COLUMN_SKU_CODE)));
                productModel.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
                productModel.setFav(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAV))));

                ArrayList<String> product_Image_Array = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGES)), new TypeToken<ArrayList<String>>() {
                }.getType());
                productModel.setProduct_Image_Array(product_Image_Array);
                productModel.setProduct_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                array_Product_Model.add(productModel);
            }
        }
        Log.e("ProductController", "array_Product_Model =" + array_Product_Model.size());
        return array_Product_Model;
    }

    public static ArrayList<ProductModel> getAllProductsofCategory(Activity context, String
            categoryId) {
        String selection = "";

        ArrayList<ProductModel> array_Product_Model = new ArrayList<ProductModel>();
        if (TextUtils.isEmpty(categoryId)) {
            selection = COLUMN_QUANTITY + " != '0'";
        } else {
            selection = COLUMN_CATEGORY_ID + " = '" + categoryId + "'" + " AND " + COLUMN_QUANTITY + " != '0'";
        }
        Log.e("ProductController", "selection =" + selection);

        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        Log.e("ProductController", "cursor =" + cursor.getCount());

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                ProductModel productModel = new ProductModel();
                productModel.setProduct_ID(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
                productModel.setSeller_ID(cursor.getString(cursor.getColumnIndex(COLUMN_SELLER_ID)));
                productModel.setSelling_Price(cursor.getString(cursor.getColumnIndex(COLUMN_SELL_PRICE)));
                productModel.setDiscount_Price(cursor.getString(cursor.getColumnIndex(COLUMN_DISCOUNT_PRICE)));
                productModel.setProduct_Category(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                productModel.setProduct_Description(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                productModel.setSKUCode(cursor.getString(cursor.getColumnIndex(COLUMN_SKU_CODE)));
                productModel.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
                productModel.setFav(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAV))));

                ArrayList<String> product_Image_Array = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGES)), new TypeToken<ArrayList<String>>() {
                }.getType());
                productModel.setProduct_Image_Array(product_Image_Array);
                productModel.setProduct_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                array_Product_Model.add(productModel);
            }
        }
        Log.e("ProductController", "array_Product_Model =" + array_Product_Model.size());
        return array_Product_Model;
    }


    /*   public static ArrayList<ProductModel> searchProduct(Activity context, String search) {

           ArrayList<ProductModel> array_Search = new ArrayList<ProductModel>();


           return array_Search;

       }

       public static int totalProductCount(Activity context) {
           Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, null, null, null);
           return cursor.getCount();
       }

       public static void deleteProduct(final Activity context, final String seller_ID, final String product_ID, final ApiCallBack callback) {

           StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_PRODUCT_API, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   Log.e("deleteresponce", response);

                   try {
                       JSONObject object = new JSONObject(response);

                       if (object.getInt("success") == 1) {
                           deleteProductFromTable(context, product_ID);
                           callback.onSuccess(object.getInt("success"));
                       } else {
                           callback.onError(object.getString("message"));
                       }

                   } catch (Exception e) {
                       callback.onError(e.getMessage());

                       Log.e("errrorrrr", e.toString());
                   }

               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Log.e("BannerErrorResponse", error.toString());
                   callback.onError(error.getMessage());

               }
           }) {
               @Override
               protected Map<String, String> getParams() {
                   Map<String, String> params = new HashMap<String, String>();
                   params.put("seller_id", seller_ID);
                   params.put("product_id", product_ID);
                   params.put("access_token", LoginController.getAccessTokenFromDatabase(context));

                   Log.e("Comment Activity", "params" + params.toString());
                   return params;
               }
           };
           MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");


       }

       public static void deletePhoto(final Activity context, final String seller_ID, final String product_ID, final int position, final ApiCallBack callback) {
           final String[] imageName = {""};
           StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_PHOTO_API, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   Log.e("deleteresponce", response);

                   try {
                       JSONObject object = new JSONObject(response);

                       if (object.getInt("success") == 1) {
                           callback.onSuccess(object.getInt("success"));

                       } else {
                           callback.onError(object.getString("message"));
                       }

                   } catch (Exception e) {
                       callback.onError(e.getMessage());

                       Log.e("errrorrrr", e.toString());
                   }

               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Log.e("BannerErrorResponse", error.toString());
                   callback.onError(error.getMessage());

               }
           }) {
               @Override
               protected Map<String, String> getParams() {
                   switch (position) {
                       case 0:
                           imageName[0] = "product_image_one";
                           break;
                       case 1:
                           imageName[0] = "product_image_two";
                           break;
                       case 2:
                           imageName[0] = "product_image_three";
                           break;
                       case 3:
                           imageName[0] = "product_image_four";
                           break;
                       case 4:
                           imageName[0] = "product_image_five";
                           break;
                   }
                   Map<String, String> params = new HashMap<String, String>();
                   params.put("seller_id", seller_ID);
                   params.put("product_id", product_ID);
                   params.put("delete_image", imageName[0]);
                   params.put("access_token", LoginController.getAccessTokenFromDatabase(context));

                   Log.e("Comment Activity", "params" + params.toString());
                   return params;
               }
           };
           MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");


       }

       */

    public static void deleteAllProductTable(Activity context) {
        context.getContentResolver().delete(CONTENT_PRODUCT, null, null);
    }

    public static ProductModel getSingleProductTable(Activity context, String product_ID) {
        Log.e("ProductController", "getSingleProductTable=");

        ProductModel productModel = new ProductModel();
        String selection = COLUMN_PRODUCT_ID + " = '" + product_ID + "'";
        Log.e("ProductController", "selection=" + selection);

        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        Log.e("ProductController", "cursor=" + cursor.getCount());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            productModel.setCollection(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION)));
            productModel.setProduct_ID(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
            productModel.setSeller_ID(cursor.getString(cursor.getColumnIndex(COLUMN_SELLER_ID)));
            productModel.setProduct_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            productModel.setSelling_Price(cursor.getString(cursor.getColumnIndex(COLUMN_SELL_PRICE)));
            productModel.setDiscount_Price(cursor.getString(cursor.getColumnIndex(COLUMN_DISCOUNT_PRICE)));
            productModel.setProduct_Category(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
            productModel.setProduct_Description(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            productModel.setSKUCode(cursor.getString(cursor.getColumnIndex(COLUMN_SKU_CODE)));
            productModel.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
            productModel.setFav(WishListController.isFavProductId(context, cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID))) ? true : false);

            ArrayList<String> product_Image_Array = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGES)), new TypeToken<ArrayList<String>>() {
            }.getType());
            Log.e("ProductController", "product_Image_Array=" + product_Image_Array);
            productModel.setProduct_Image_Array(product_Image_Array);
        }
        return productModel;
    }

    public static String getProductQuantity(Activity context, String product_id) {
        String quantity = "0";

        String selection = COLUMN_PRODUCT_ID + " = '" + product_id + "'";
        Log.e("ProductController", "selection=" + selection);

        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        Log.e("ProductController", "cursor=" + cursor.getCount());

        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            quantity = cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY));
        }
        return quantity;
    }

    public void addReviewCall(final Activity context, final String product_id, final String star, final String title, final String message, final CallBackResult<Boolean> callBackResult) {
        String url = MyApplication.ApiUrl + "product_review";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess(true);
                    } else {
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Error");
                Log.e("BannerErrorResponse", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("product_id", product_id);
                params.put("stars", star);
                params.put("title", title);
                params.put("review_message", message);
                params.put("customer_id", LoginController.getCustomerID(context));
                Log.e("ProductController", "params =" + params);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "BannerApiCall");
    }

    public void getReviewCall(final Activity context, final String product_id, final String order, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "get_product_review";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess(response);
                    } else {
                        callBackResult.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Something went wrong.");
                Log.e("BannerErrorResponse", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("product_id", product_id);
                params.put("sort", order);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "get_product_review");
    }


    /*public static void sendImageMultipart(final Activity context, final String sequence, final String productId, final Bitmap array_Images, final ApiCallBack apiCallBack) {

        final VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_PRODUCT_IMAGES_API, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                apiCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VolleyError", "==" + error);
                apiCallBack.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("product_id", productId);
                params.put("sequence", "" + sequence);


                Log.e("paramsparams", "==" + params.toString());
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();


                if (array_Images != null) {
                    params.put("image", new DataPart("image" + Util.getCurrentTimeStamp() + ".jpg", AppHelper.getFileDataFromDrawable(context, array_Images), "image/jpeg"));

                } else {
                    params.put("image", new DataPart());
                }


                Log.e("paramsparams image", "==" + array_Images);
                Log.e("paramsparams image", "==" + params.toString());

                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest);

    }



    public static void createProductApi(final Activity context, final ProductModel productModel, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CREATE_PRODUCT_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("createOrderApi", response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        callback.onSuccess(object.getString("product_id"));
                    } else {
                        callback.onError(object.getString("message"));
                    }


                } catch (Exception e) {
                    callback.onError(e.getMessage());
                    Log.e("errrorrrr", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", productModel.getSeller_ID());
                params.put("product_name", productModel.getProduct_Name());
                params.put("product_description", productModel.getProduct_Description());
                params.put("product_category", productModel.getProduct_Category());
                params.put("selling_price", productModel.getSelling_Price());
                params.put("discount_price", productModel.getDiscount_Price());
                params.put("product_quantity", productModel.getQuantity());
                params.put("sku_code", productModel.getSKUCode());

                Log.e("paramsparams", "==" + params.toString());
                return params;

            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "createProductApi");


    }

    public static void editProductApi(final Activity context, final ProductModel productModel, final ArrayList<Drawable> array_Images, final ApiCallBack apiCallBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CREATE_PRODUCT_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("createOrderApi", response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        apiCallBack.onSuccess(object.getString("message"));
                    } else {
                        apiCallBack.onError(object.getString("message"));
                    }


                } catch (Exception e) {
                    apiCallBack.onError(e.getMessage());
                    Log.e("errrorrrr", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiCallBack.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("product_id", productModel.getProduct_ID());
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("product_name", productModel.getProduct_Name());
                params.put("product_description", productModel.getProduct_Description());
                params.put("product_category", productModel.getProduct_Category());
                params.put("selling_price", productModel.getSelling_Price());
                params.put("discount_price", productModel.getDiscount_Price());
                params.put("product_quantity", productModel.getQuantity());
                params.put("sku_code", productModel.getSKUCode());


                Log.e("paramsparams", "==" + params.toString());
                return params;

            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "createProductApi");


    }

    public static void checkSKUCodeApi(final Activity context, final String sku_code, final ApiCallBack callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_SKU_CODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ProductController", "checkSKUCodeApi==" + response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        callback.onSuccess(object.getInt("success"));
                    } else {
                        callback.onError(object.getString("message"));
                    }

                } catch (Exception e) {
                    callback.onError(e.getMessage());
                    Log.e("errrorrrr", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("seller_id", LoginController.getSellerID(context));
                params.put("sku_code", sku_code);
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "checkSKUCodeApi");


    }


    public static void deleteProductFromTable(Activity context, String product_ID) {
        String selection = COLUMN_PRODUCT_ID + " = '" + product_ID + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            context.getContentResolver().delete(CONTENT_PRODUCT, selection, null);
        }
    }

    public static boolean isProductAvailable(Activity context) {
        boolean product_Available = false;
        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, null, null, null);
        if (cursor.getCount() > 0) {

            product_Available = true;

        }
        return product_Available;
    }


*/

}
