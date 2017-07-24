package com.binaryic.customerapp.fashionic.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.Constants;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.models.BannerModel;
import com.binaryic.customerapp.fashionic.models.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.fashionic.common.Constants.COLLECTION_JSON;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_CATEGORY;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_HOME;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_ALL_CATEGORY_API;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_BANNERS_API;
import static com.binaryic.customerapp.fashionic.common.Constants.PROMOTIONAL_JSON;
import static com.binaryic.customerapp.fashionic.common.Constants.SLIDER_JSON;

/**
 * Created by HP on 03-Apr-17.
 */

public class HomeController {

    public static void getBannerDataApiCall(final Activity context, final ApiCallBack callBackResult) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BANNERS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        JSONArray slider = object.getJSONArray("slider_banner");
                        JSONArray promotional_banner = object.getJSONArray("promotional_banner");
                        JSONArray productsdata = object.getJSONArray("productsdata");
                        ContentValues values = new ContentValues();
                        values.put(COLLECTION_JSON, productsdata.toString());
                        values.put(SLIDER_JSON, slider.toString());
                        values.put(PROMOTIONAL_JSON, promotional_banner.toString());
                        deleteBannerTable(context);

                        context.getContentResolver().insert(CONTENT_HOME, values);

                    }
                } catch (JSONException e) {
                    Log.e("getBannerDataApiCall","errorr=="+e.getMessage());
                }
                callBackResult.onSuccess("success");
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
                params.put("seller_id", Constants.seller_id);
                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "BannerApiCall");
    }
    private static void deleteBannerTable(Activity context) {
        context.getContentResolver().delete(CONTENT_HOME, null, null);
    }

    public ArrayList<BannerModel> getSlider(Context context) {
        ArrayList<BannerModel> brandModels = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_HOME, null, null, null, null);
            Log.e("HomeController", "cursor==" + cursor.getCount());

            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                JSONArray array = new JSONArray(cursor.getString(cursor.getColumnIndex(SLIDER_JSON)));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    BannerModel brandModel = new BannerModel(object.getString("banner_id"), object.getString("banner_image"), object.getString("category_id"), object.getString("category_name"));
                    brandModels.add(brandModel);
                }
            }
            cursor.close();
        } catch (Exception ex) {
            Log.e("HomeController", "error==" + ex.getMessage());
        }
        return brandModels;
    }

    public ArrayList<BannerModel> getPromotional(Context context) {
        ArrayList<BannerModel> brandModels = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_HOME, null, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                JSONArray array = new JSONArray(cursor.getString(cursor.getColumnIndex(PROMOTIONAL_JSON)));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    BannerModel brandModel = new BannerModel(object.getString("banner_id"), object.getString("banner_image"), object.getString("category_id"), object.getString("category_name"));
                    brandModels.add(brandModel);
                }
            }
            cursor.close();
        } catch (Exception ex) {
            Log.e("getPromotional","errorr=="+ex.getMessage());

        }
        return brandModels;
    }

    public JSONArray getCollection(Context context) {
        JSONArray array = new JSONArray();
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_HOME, null, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                array = new JSONArray(cursor.getString(cursor.getColumnIndex(COLLECTION_JSON)));
            }
            cursor.close();
        } catch (Exception ex) {
            Log.e("getCollection","errorr=="+ex.getMessage());

        }
        return array;
    }

    public void sendCrashReportApi(final String device,final String model,final String android_version,final String package_name,final String app_version,final String error,final String app) {
        String url = MyApplication.ApiUrl + "error_report";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("result",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("device", device);
                params.put("model", model);
                params.put("android_version", android_version);
                params.put("package_name", package_name);
                params.put("app_version", app_version);
                params.put("error", error);
                params.put("app", app);
                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "error_report");
    }


    public void getCategoryApiCall(final Activity context, final ApiCallBack callBackResult) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ALL_CATEGORY_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        JSONArray categories = object.getJSONArray("categories");
                        if (categories.length() > 0) {
                            setCategoryData(context, categories);
                        }
                    }
                } catch (JSONException e) {
                }
                callBackResult.onSuccess("success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackResult.onError("Error");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", Constants.seller_id);
                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "BannerApiCall");
    }

    private void setCategoryData(Context context, JSONArray categoryData) {
        try {
            context.getContentResolver().delete(CONTENT_CATEGORY, null, null);
            for (int i = 0; i < categoryData.length(); i++) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CATEGORY_ID, categoryData.getJSONObject(i).getString("category_id"));
                values.put(COLUMN_CATEGORY_NAME, categoryData.getJSONObject(i).getString("category_name"));
                context.getContentResolver().insert(CONTENT_CATEGORY, values);
            }
        } catch (Exception ex) {

            Log.e("setCategoryData","errorr=="+ex.getMessage());

        }
    }

    public ArrayList<CategoryModel> getCategories(Context context) {
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_CATEGORY, null, null, null, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    categoryModels.add(new CategoryModel(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME))));
                }
                cursor.close();
            }
        } catch (Exception ex) {
        }
        return categoryModels;
    }

  /*  public void setSettingData(Context context, String seller_id, String access_token) {
        try {
            context.getContentResolver().delete(CONTENT_SETTING, null, null);
            ContentValues values = new ContentValues();
            values.put(SELLER_ID, seller_id);
            values.put(ACCESS_TOKEN, access_token);
            context.getContentResolver().insert(CONTENT_SETTING, values);
        } catch (Exception ex) {
            String temp = ex.getMessage();
        }
    }

    public static String getSellerId(Context context) {
        String seller_id = "";
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_SETTING, null, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                seller_id = cursor.getString(cursor.getColumnIndex(SELLER_ID));
            }
            cursor.close();
        } catch (Exception ex) {
            String temp = ex.getMessage();
        }
        return seller_id;
    }
    public static String getAccessToken(Context context) {
        String access_token = "";
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_SETTING, null, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                access_token = cursor.getString(cursor.getColumnIndex(ACCESS_TOKEN));
            }
            cursor.close();
        } catch (Exception ex) {
            String temp = ex.getMessage();
        }
        return access_token;
    }*/
}
