package com.binaryic.customerapp.fashionic.controller;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.models.OrderModel;
import com.binaryic.customerapp.fashionic.models.OrderProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.fashionic.common.Constants.CREATE_ORDER_API;
import static com.binaryic.customerapp.fashionic.common.Constants.seller_id;

/**
 * Created by minakshi on 31/1/17.
 */

public class OrderController {

    public void createOrder(final Activity context, final String order_data, final String coupon_code, final String transaction_type, final String transaction_id, final String other_details, final String grand_total, final String discount_price, final String selling_price, final String shipping_charges, final String taxes, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CREATE_ORDER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("OrderController", "response==" + response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callback.onSuccess("success");

                    } else {
                        callback.onError(object.getString("message"));
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("OrderController", "error==" + error.getMessage());

                callback.onError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", seller_id);
                params.put("customer_id", LoginController.getCustomerID(context));
                params.put("order_data", order_data);
                //if (!coupon_code.equals(""))
                params.put("applied_coupon_code", coupon_code);
                params.put("transaction_type", transaction_type);
                params.put("transaction_id", transaction_id);
                params.put("user_details", other_details);
                params.put("grand_total", grand_total);
                params.put("discount_price", discount_price);
                params.put("selling_price", selling_price);
                params.put("shipping_charges", shipping_charges);
                params.put("taxes", taxes);

                Log.e("OrderController", "params==" + params);

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "create_order");
    }

    public void getOrderApiCall(final Activity context, final CallBackResult<ArrayList<OrderModel>> callBackResult) {
        String url = MyApplication.ApiUrl + "get_customer_orders";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("OrderController", "Response==" + response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        JSONArray order_data = object.getJSONArray("order_data");
                        ArrayList<OrderModel> list = new ArrayList<>();
                        for (int i = 0; i < order_data.length(); i++) {
                            OrderModel orderModel = new OrderModel();
                            orderModel.setOrder_no(order_data.getJSONObject(i).getString("order_id"));
                            orderModel.setTotal_amount(order_data.getJSONObject(i).getString("grand_total"));
                            orderModel.setDiscount_amount(order_data.getJSONObject(i).getString("discount_price"));
                            orderModel.setSelling_price(order_data.getJSONObject(i).getString("selling_price"));
                            orderModel.setApplied_coupon_code(order_data.getJSONObject(i).getString("applied_coupon_code"));
                            orderModel.setPayment_mode(order_data.getJSONObject(i).getString("transaction_type"));
                            //   orderModel.setTransaction_id(order_data.getJSONObject(i).getString("transaction_id"));
                            orderModel.setShipping_address(order_data.getJSONObject(i).getString("customer_address") + " " + order_data.getJSONObject(i).getString("landmark") + " " + order_data.getJSONObject(i).getString("area") + " " + order_data.getJSONObject(i).getString("city") + " " + order_data.getJSONObject(i).getString("pincode") + " " + order_data.getJSONObject(i).getString("state"));
                            orderModel.setCustomer_name(order_data.getJSONObject(i).getString("first_name") + " " + order_data.getJSONObject(i).getString("last_name"));
                            orderModel.setStatus(order_data.getJSONObject(i).getString("order_status"));
                            orderModel.setDelivery_date(order_data.getJSONObject(i).getString("status_time"));
                            orderModel.setCreated_date(order_data.getJSONObject(i).getString("created_at"));
                            JSONArray sub_order = order_data.getJSONObject(i).getJSONArray("sub_order");
                            ArrayList<OrderProductModel> orderProductModels = new ArrayList<>();
                            for (int j = 0; j < sub_order.length(); j++) {
                                OrderProductModel orderProductModel = new OrderProductModel();
                                orderProductModel.setProduct_id(sub_order.getJSONObject(j).getString("product_id"));
                                orderProductModel.setSub_order_id(sub_order.getJSONObject(j).getString("sub_order_id"));
                                orderProductModel.setProduct_name(sub_order.getJSONObject(j).getString("product_name"));
                                orderProductModel.setProduct_image(sub_order.getJSONObject(j).getString("product_image"));
                                orderProductModel.setProduct_quantity(sub_order.getJSONObject(j).getString("product_quantity"));
                                orderProductModel.setSelling_price(sub_order.getJSONObject(j).getString("selling_price"));
                                orderProductModel.setStatus(sub_order.getJSONObject(j).getString("order_status"));
                                orderProductModel.setDelivery_date(sub_order.getJSONObject(j).getString("status_time"));
                                orderProductModels.add(orderProductModel);
                            }
                            orderModel.setProductModels(orderProductModels);
                            orderModel.setOrder_id("1");
                            orderModel.setTax("20");
                            list.add(orderModel);
                        }
                        callBackResult.onSuccess(list);
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", seller_id);
                if (TextUtils.isEmpty(LoginController.getCustomerID(context)))
                    params.put("customer_id", "-1");
                else
                    params.put("customer_id", LoginController.getCustomerID(context));

                Log.e("OrderController", "params==" + params.toString());

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "create_order");
    }

    public void deleteOrder(final Activity context, final String order_id, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_order_status_update";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("OrderController", "response =" + response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess("success");
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("order_id", order_id);
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("action", "Cancel");
                Log.e("OrderController", "params =" + params);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "customer_sub_order_delete");
    }

    public void deleteSubOrder(final Activity context, final String order_id, final String sub_order_id, final CallBackResult<String> callBackResult) {
        String url = MyApplication.ApiUrl + "customer_sub_order_status_update";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("OrderController", "response =" + response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equals("1")) {
                        callBackResult.onSuccess("success");
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("order_id", order_id);
                params.put("sub_order_id", sub_order_id);
                params.put("action", "Cancel");
                Log.e("OrderController", "params =" + params);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "customer_sub_order_delete");
    }

/*

    public static void deleteOrder(final Activity context, final String order_id, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_ORDER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("deleteresponce", response);

                try {
                    JSONObject object = new JSONObject(response);
                    ArrayList<ProductModel> array_Prodcts = new ArrayList<ProductModel>();

                    if (object.getInt("success") == 1) {
                        deleteOrderFromTable(context, order_id);
                        callback.onSuccess(array_Prodcts);

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
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("order_id", order_id);
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));

                Log.e("Comment Activity", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");


    }

    public static void deleteOrderFromTable(Activity context, String Order_ID) {
        String selection = COLUMN_ORDER_ID + " = '" + Order_ID + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_ORDER, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            context.getContentResolver().delete(CONTENT_ORDER, selection, null);
        }
    }

    public static OrderModel getSingleOrderTable(Activity context, String order_ID) {
        OrderModel orderModel = new OrderModel();
        String selection = COLUMN_ORDER_ID + " = '" + order_ID + "'";

        Cursor cursor = context.getContentResolver().query(CONTENT_ORDER, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            orderModel.setOrderId(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_ID)));
            orderModel.setSellerId(cursor.getString(cursor.getColumnIndex(COLUMN_SELLER_ID)));
        orderModel.setProductQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
            orderModel.setProductId(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
            orderModel.setCustomerName(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)));
            orderModel.setCustomerNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_MOBILE_NUMBER)));
            orderModel.setPaymentType(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_TYPE)));
            orderModel.setProductName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            orderModel.setOrderDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            orderModel.setProductSellingPrice(cursor.getString(cursor.getColumnIndex(COLUMN_SELL_PRICE)));

        }
        return orderModel;
    }

    public static boolean isOrderAvailable(Activity context) {
        boolean product_Available = false;
        Cursor cursor = context.getContentResolver().query(CONTENT_ORDER, null, null, null, null);
        if (cursor.getCount() > 0) {

            product_Available = true;

        }
        return product_Available;
    }

    public static void getAllOrdersApi(final Activity context, final String sort, final ApiCallBack callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ALL_ORDER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    ArrayList<OrderModel> array_Order = new ArrayList<OrderModel>();
                    Log.e("getAllOrdersApi", response);
                    if (object.getInt("success") == 1) {
                        JSONObject jsonObject_Order_Data = object.getJSONObject("Orderdata");
                        JSONArray jsonArray_Order_Data = jsonObject_Order_Data.getJSONArray("data");

                        for (int i = 0; i < jsonArray_Order_Data.length(); i++) {
                            JSONObject jsonObject = jsonArray_Order_Data.getJSONObject(i);

                            final OrderModel orderModel = new OrderModel();
                            orderModel.setOrderId(jsonObject.getString("order_id"));
                            orderModel.setProductId(jsonObject.getString("product_id"));
                            orderModel.setSellerId(jsonObject.getString("seller_id"));
                            orderModel.setProductQuantity(jsonObject.getString("quantity"));
                            orderModel.setProductName(jsonObject.getString("product_name"));
                            orderModel.setCustomerName(jsonObject.getString("customer_name"));
                            orderModel.setCustomerNumber(jsonObject.getString("mobile_no"));
                            orderModel.setProductSellingPrice(jsonObject.getString("selling_price"));
                            orderModel.setPaymentType(jsonObject.getString("payment_type"));
                            orderModel.setOrderDescription(jsonObject.getString("order_description"));
                            addOrderToTable(context, orderModel);
                            array_Order.add(orderModel);
                        }
                        callback.onSuccess(array_Order);

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
        })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("sort", sort);
                params.put("page", String.valueOf(Constants.page));
                params.put("limit", String.valueOf(Constants.PAGE_SIZE));

                return params;
            }
        };
        MyApplication.getInstance().

                addToRequestQueue(stringRequest, "getAllOrdersApi");


    }

    public static void createOrderApi(final Activity context, final String product_id, final String quantity, final String customer_name, final String mobile_no, final String selling_price, final String payment_type, final String order_description, final String transaction_ID, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CREATE_ORDER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("createOrderApi", response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        callback.onSuccess(response);
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
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("product_id", product_id);
                params.put("customer_name", customer_name);
                params.put("mobile_no", mobile_no);
                params.put("selling_price", selling_price);
                params.put("payment_type", payment_type);
                params.put("order_description", order_description);
                params.put("quantity", quantity);

                params.put("payment_transaction_id", transaction_ID);
                Log.e("createOrderApi", String.valueOf(params));

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "createOrderApi");


    }

    public static int totalOrderCount(Activity context) {
        Cursor cursor = context.getContentResolver().query(CONTENT_ORDER, null, null, null, null);
        return cursor.getCount();
    }

    public static double totalOrderPrice(Activity context) {
        double price = 0;
        Cursor cursor = context.getContentResolver().query(CONTENT_ORDER, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                Log.e("OrderController", "price =" + cursor.getDouble(cursor.getColumnIndex(COLUMN_SELL_PRICE)));
                price = price + cursor.getDouble(cursor.getColumnIndex(COLUMN_SELL_PRICE));
            }
        }
        cursor.close();
        return price;
    }

    public static void addOrderToTable(Activity context, OrderModel orderModel) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, orderModel.getCustomerName());
        values.put(COLUMN_SELLER_ID, orderModel.getSellerId());
        values.put(COLUMN_QUANTITY, orderModel.getProductQuantity());
        values.put(COLUMN_PRODUCT_ID, orderModel.getProductId());
        values.put(COLUMN_ORDER_ID, orderModel.getOrderId());
        values.put(COLUMN_SELL_PRICE, orderModel.getProductSellingPrice());
        values.put(COLUMN_NAME, orderModel.getProductName());
        values.put(COLUMN_CUSTOMER_MOBILE_NUMBER, orderModel.getCustomerNumber());
        values.put(COLUMN_PAYMENT_TYPE, orderModel.getPaymentType());
        values.put(COLUMN_DESCRIPTION, orderModel.getOrderDescription());
        String selection = COLUMN_ORDER_ID + " ='" + orderModel.getOrderId() + "'";
        Log.e("OrderController", "selection =" + selection);
        Cursor cursor = context.getContentResolver().query(CONTENT_ORDER, null, selection, null, null);
        if (cursor.getCount() > 0) {
            Log.e("OrderController", "update");
            context.getContentResolver().update(CONTENT_ORDER, values, selection, null);
        } else {
            Log.e("OrderController", "insert");
            context.getContentResolver().insert(CONTENT_ORDER, values);
        }
    }

    public static boolean isProductNameSelectedFromList(Activity context, String product_name) {
        boolean isPresent = false;
        String selection = COLUMN_NAME + " ='" + product_name + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        if (cursor.getCount() > 0) {
            isPresent = true;
            cursor.close();
        }
        return isPresent;
    }

    public static ArrayList getProductName(Activity context) {
        ArrayList productName = new ArrayList();
        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                productName.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                Log.e("OrderController", "productName =" + cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            }
            cursor.close();
        }
        return productName;
    }

    public static String getProductId(Activity context, String product_name) {
        String productId = "";
        String selection = COLUMN_NAME + " ='" + product_name + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_PRODUCT, null, selection, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                productId = (cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
            }
            cursor.close();
        }
        return productId;
    }

    public static ArrayList<OrderModel> getAllOrdersFromDatabase(Activity context, ArrayList<OrderModel> array_Order_Model, String search) {
        array_Order_Model.clear();
        Cursor cursor = context.getContentResolver().query(CONTENT_ORDER, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                OrderModel orderModel = new OrderModel();
                orderModel.setCustomerName((cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME))));
                orderModel.setCustomerNumber((cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_MOBILE_NUMBER))));
                orderModel.setOrderId((cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_ID))));
                orderModel.setSellerId((cursor.getString(cursor.getColumnIndex(COLUMN_SELLER_ID))));
                orderModel.setProductQuantity((cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY))));
                orderModel.setProductId((cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID))));
                orderModel.setProductSellingPrice((cursor.getString(cursor.getColumnIndex(COLUMN_SELL_PRICE))));
                orderModel.setProductName((cursor.getString(cursor.getColumnIndex(COLUMN_NAME))));
                orderModel.setPaymentType((cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_TYPE))));
                orderModel.setOrderDescription((cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))));
                Log.e("search", search);
                if (!TextUtils.isEmpty(search)) {

                    Log.e("ifffffffff search", search);

                    if (orderModel.getOrderId().equalsIgnoreCase(search) || orderModel.getCustomerName().equalsIgnoreCase(search) ||
                            orderModel.getCustomerNumber().equalsIgnoreCase(search) || orderModel.getProductId().equalsIgnoreCase(search) ||
                            orderModel.getProductName().equalsIgnoreCase(search) || orderModel.getProductSellingPrice().equalsIgnoreCase(search) ||
                            orderModel.getPaymentType().equalsIgnoreCase(search) || orderModel.getOrderId().contains(search) || orderModel.getCustomerName().contains(search) ||
                            orderModel.getCustomerNumber().contains(search) || orderModel.getProductId().contains(search) ||
                            orderModel.getProductName().contains(search) || orderModel.getProductSellingPrice().contains(search) ||
                            orderModel.getPaymentType().contains(search)) {
                        array_Order_Model.add(orderModel);
                    }
                } else {
                    Log.e("elseeeeee search", search);
                    array_Order_Model.add(orderModel);
                }

            }
            cursor.close();
        }

        return array_Order_Model;
    }
*/

}
