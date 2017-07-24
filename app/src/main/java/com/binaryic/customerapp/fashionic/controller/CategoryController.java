package com.binaryic.customerapp.fashionic.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.Constants;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.models.CategoryModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_CATEGORY;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_ALL_CATEGORY_API;


/**
 * Created by minakshi on 30/1/17.
 */

public class CategoryController {


    public static void getAllCategoryApi(final Activity context, final ApiCallBack callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ALL_CATEGORY_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    ArrayList<CategoryModel> array_Category = new ArrayList<CategoryModel>();
                    Log.e("CategoryController", "responce==" + response);
                    if (object.getInt("success") == 1) {
                        deleteCategoryTable(context);
                        JSONArray jsonArray_Category_Data = object.getJSONArray("categories");

                        for (int i = 0; i < jsonArray_Category_Data.length(); i++) {
                            JSONObject jsonObject = jsonArray_Category_Data.getJSONObject(i);

                            final CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCategory_Name(jsonObject.getString("category_name"));
                            categoryModel.setCategory_Id(jsonObject.getString("category_id"));
                            addCategoryToTable(context, categoryModel);
                            array_Category.add(categoryModel);
                        }
                        callback.onSuccess(array_Category);

                    }
                } catch (Exception e) {
                    callback.onError(e.getMessage());
                    Log.e("errrorrrr", e.toString());
                }

            }
        }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.getMessage());

            }
        }

        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", Constants.seller_id);

                Log.e("CategoryController", "params==" + params);
                return params;

            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "getAllCategoryApi");


    }

    private static void deleteCategoryTable(Activity context) {
        context.getContentResolver().delete(CONTENT_CATEGORY, null, null);
    }


    private static void addCategoryToTable(Activity context, CategoryModel categoryModel) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, categoryModel.getCategory_Name());
        values.put(COLUMN_CATEGORY_ID, categoryModel.getCategory_Id());
        String selection = COLUMN_CATEGORY_ID + " ='" + categoryModel.getCategory_Id() + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_CATEGORY, null, selection, null, null);
        if (cursor.getCount() > 0) {
            context.getContentResolver().update(CONTENT_CATEGORY, values, selection, null);
        } else {
            context.getContentResolver().insert(CONTENT_CATEGORY, values);
        }
    }

    public static ArrayList<CategoryModel> getAllCategoryFromTable(Activity context, ArrayList<CategoryModel> array_Category_Model, String search) {
        array_Category_Model.clear();
        Cursor cursor = context.getContentResolver().query(CONTENT_CATEGORY, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setCategory_Name((cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME))));
                categoryModel.setCategory_Id((cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID))));

                if (!TextUtils.isEmpty(search)) {
                    Log.e("ifffffffff search", search);

                    if (categoryModel.getCategory_Name().contains(search) || categoryModel.getCategory_Name().equalsIgnoreCase(search) || categoryModel.getCategory_Id().contains(search) || categoryModel.getCategory_Id().equalsIgnoreCase(search)) {
                        array_Category_Model.add(categoryModel);
                    }
                } else {
                    Log.e("elseeeeee search", search);
                    array_Category_Model.add(categoryModel);
                }
            }
            cursor.close();
        }
        return array_Category_Model;
    }

    public static String getCategoryIdFromTable(Activity context, String categoryName) {
        String categoryId = "";
        String selection = COLUMN_CATEGORY_NAME + " ='" + categoryName.toLowerCase() + "'";
        Log.e("CategoryController", "selection=" + selection);
        Cursor cursor = context.getContentResolver().query(CONTENT_CATEGORY, null, selection, null, null);
        Log.e("CategoryController", "cursor=" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                categoryId = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                Log.e("CategoryController", "categoryId=" + categoryId);

            }
            cursor.close();
        }

        return categoryId;
    }

    public static String getCategoryNameFromTable(Activity context, String categoryId) {
        String categoryName = "";
        String selection = COLUMN_CATEGORY_ID + " ='" + categoryId + "'";
        Log.e("CategoryController", "selection=" + selection);
        Cursor cursor = context.getContentResolver().query(CONTENT_CATEGORY, null, selection, null, null);
        Log.e("CategoryController", "cursor=" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
                Log.e("CategoryController", "categoryId=" + categoryId);

            }
            cursor.close();
        }

        return categoryName;
    }
}
