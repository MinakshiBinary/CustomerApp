package com.binaryic.customerapp.fashionic.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.customerapp.fashionic.activities.OtpActivity;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.Constants;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.models.CustomerModel;
import com.binaryic.customerapp.fashionic.models.LoginModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_ACCESS_TOKEN;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_ADDRESS;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_AREA;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CITY;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CUSTOMER_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_EMAILID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_FIRST_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_IS_OTP_DONE;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_LANDMARK;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_LAST_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_MOBILE_NUMBER;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_PINCODE;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_SELLER_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_STATE;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_ACCESS_TOKEN;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_CUSTOMER;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_ACCESS_TOKEN_API;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_LOGIN_OTP_API;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_SIGN_UP_OTP_API;
import static com.binaryic.customerapp.fashionic.common.Constants.LOGINAPI;
import static com.binaryic.customerapp.fashionic.common.Constants.SIGNUP_API;
import static com.binaryic.customerapp.fashionic.common.Constants.seller_id;


/**
 * Created by user on 22-Jan-17.
 */
public class LoginController {


    public static void getAccessTokenApiCall(final Activity context, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ACCESS_TOKEN_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("getAccessTokenApiCall", "response =" + response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        addAccessTokenInDatabase(context, object.getString("access_token"));
                        callback.onSuccess(object.getInt("success"));
                    } else {
                        callback.onError("success value is 0. please check responce");
                    }

                } catch (Exception e) {
                    callback.onError(e.getMessage());
                    Log.e("errrorrrr", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getAccessTokenApiCall", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("client_name", "Seller App");
                params.put("package_name", "com.binaryic.sellerapp");
                params.put("company_name", "BinaryIC");

                Log.e("getAccessTokenApiCall", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "getAccessTokenApiCall");


    }

    public static void addAccessTokenInDatabase(Activity context, String access_token) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCESS_TOKEN, access_token);
        Cursor cursor = context.getContentResolver().query(CONTENT_ACCESS_TOKEN, null, null, null, null);
        if (cursor.getCount() > 0)
            context.getContentResolver().update(CONTENT_ACCESS_TOKEN, values, null, null);
        else
            context.getContentResolver().insert(CONTENT_ACCESS_TOKEN, values);

    }

    public static String getAccessTokenFromDatabase(Activity context) {

        Cursor cursor = context.getContentResolver().query(CONTENT_ACCESS_TOKEN, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(COLUMN_ACCESS_TOKEN));
        } else return "";
    }

    /*
            public static void changeMobileNumberCall(final Activity context, final String current_mobile_no, final String new_mobile_no, final ApiCallBack callback) {
                final LoginModel loginModel = new LoginModel();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, CHANGE_MOBILE_NO_API, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("BannerResponse", response);

                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getInt("success") == 1) {
                                callback.onSuccess(object.getString("Message"));
                            } else {
                                callback.onError(object.getString("Message"));
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
                        params.put("current_mobile_no", current_mobile_no);
                        params.put("new_mobile_no", new_mobile_no);
                        Log.e("Comment Activity", "params" + params.toString());
                        return params;
                    }
                };
                MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");


            }

         */
    public static void registerApiCall(final Activity context, final String mobile_No, final String seller_id, final ApiCallBack callback) {
        final LoginModel loginModel = new LoginModel();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGINAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        loginModel.setSuccess(object.getInt("success"));
                        CustomerModel customerModel = new CustomerModel();
                        JSONObject jsonObject_Detail = object.getJSONObject("Details");
                        Log.e("LoginController", "jsonObject_Detail =" + jsonObject_Detail.toString());

                        customerModel.setSellerID(seller_id);
                        customerModel.setCustomerId(jsonObject_Detail.getString("customer_id"));
                        customerModel.setMobile_Number(jsonObject_Detail.getString("mobile_number"));
                        customerModel.setFirstName(jsonObject_Detail.getString("first_name"));
                        customerModel.setLastName(jsonObject_Detail.getString("last_name"));
                        customerModel.setEmailID(jsonObject_Detail.getString("customer_email_id"));
                        customerModel.setAddress(jsonObject_Detail.getString("customer_address"));
                        customerModel.setLandMark(jsonObject_Detail.getString("landmark"));
                        customerModel.setArea(jsonObject_Detail.getString("area"));
                        customerModel.setPincode(jsonObject_Detail.getString("pincode"));
                        customerModel.setCity(jsonObject_Detail.getString("city"));
                        customerModel.setState(jsonObject_Detail.getString("state"));
                        customerModel.setIsOtpDone("0");
                        loginModel.setOtp(jsonObject_Detail.getString("otp"));

                        addCustomerInDatabase(context, customerModel);
                        callback.onSuccess(loginModel);
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
                params.put("mobile_number", mobile_No);
                params.put("seller_id", seller_id);
                Log.e("Comment Activity", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");
    }

    public static void signUpApiCall(final Activity context, final String mobile_No, final String seller_id/*, final String name, final String last_Name, final String emailID, final String address, final String landmark, final String area, final String pincode, final String city, final String state*/, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNUP_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("signUpApiCallResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        callback.onSuccess(object.getString("OTP"));
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
                Log.e("signUpApiCallResponse", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("mobile_number", mobile_No);
                params.put("seller_id", seller_id);
                /*params.put("seller_id", Constants.seller_id);
                params.put("first_name", name);
                params.put("last_name", last_Name);
                params.put("customer_email_id", emailID);
                params.put("customer_address", address);
                params.put("landmark", landmark);
                params.put("area", area);
                params.put("pincode", pincode);
                params.put("city", city);
                params.put("state", state);*/
                Log.e("LoginController", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "signUpApiCall");


    }

    public static void loginOtpApiCall(final OtpActivity context, final String otp, final ApiCallBack apiCallBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LOGIN_OTP_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("getAccessTokenApiCall", "response =" + response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {

                        ContentValues values = new ContentValues();
                        values.put(COLUMN_IS_OTP_DONE, "1");
                        String selection = COLUMN_SELLER_ID + " =  '" + LoginController.getSellerID(context) + "' ";
                        context.getContentResolver().update(CONTENT_CUSTOMER, values, selection, null);

                        apiCallBack.onSuccess(object.getString("Message"));
                    } else {
                        apiCallBack.onError(object.getString("Message"));
                    }

                } catch (Exception e) {
                    apiCallBack.onError(e.getMessage());
                    Log.e("errrorrrr", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getAccessTokenApiCall", error.toString());
                apiCallBack.onError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_id", LoginController.getCustomerID(context));
                params.put("otp", otp);
                params.put("fcm_token", MyApplication.setting.getString("fcm_token", "no_access_token"));
                params.put("seller_id", Constants.seller_id);

                Log.e("getAccessTokenApiCall", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "getAccessTokenApiCall");
    }

    public static void signUpOtpApiCall(final OtpActivity context, final String mobile_No, final String name, final String last_Name, final String emailID, final String address, final String landmark, final String area, final String pincode, final String city, final String state, final String otp, final ApiCallBack apiCallBack) {
        {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_SIGN_UP_OTP_API, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("signUpOtpApiCall", "response =" + response);

                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getInt("success") == 1) {
                            apiCallBack.onSuccess(object.getString("Message"));
                        } else {
                            apiCallBack.onError(object.getString("Message"));
                        }

                    } catch (Exception e) {
                        apiCallBack.onError(e.getMessage());
                        Log.e("errrorrrr", e.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("signUpOtpApiCall", error.toString());
                    apiCallBack.onError(error.getMessage());

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("otp", otp);
                    params.put("mobile_number", mobile_No);
                    params.put("seller_id", seller_id);
                    params.put("first_name", name);
                    params.put("last_name", last_Name);
                    params.put("customer_email_id", emailID);
                    params.put("customer_address", address);
                    params.put("landmark", landmark);
                    params.put("area", area);
                    params.put("pincode", pincode);
                    params.put("city", city);
                    params.put("state", state);
                    Log.e("signUpOtpApiCall", "params" + params.toString());
                    return params;
                }
            };
            MyApplication.getInstance().addToRequestQueue(stringRequest, "signUpOtpApiCall");


        }

    }



/*
    public static void getSellerDetailsApiCall(final Activity context, final ApiCallBack callback) {
        final LoginModel loginModel = new LoginModel();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SELLER_INFO_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BannerResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        loginModel.setSuccess(object.getInt("success"));
                        SellerModel sellerModel = new SellerModel();
                        sellerModel.setSellerID(object.getString("seller_id"));
                        sellerModel.setMobile_Number(object.getString("mobile_no"));
                        sellerModel.setName(object.getString("seller_name"));
                        sellerModel.setEmailID(object.getString("seller_email_id"));
                        sellerModel.setUri(object.getString("seller_image"));
                        sellerModel.setCompany(object.getString("company_name"));
                        sellerModel.setAbout_Us(object.getString("about_us"));
                        sellerModel.setCategory(object.getString("seller_category"));
                        sellerModel.setIsOtpDone("1");
                        sellerModel.setSet_Checked("MyName");
                        addCustomerInDatabase(context, sellerModel);
                        callback.onSuccess(loginModel);

                    } else {
                        callback.onError("success value is 0. please check responce");
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
                params.put("access_token", MyApplication.Access_Token);
                params.put("seller_id", MyApplication.Access_Token);

                Log.e("Comment Activity", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "registerApiCall");
    }
*/


    /*


    public static void updateRegisterApiCall(final Activity context, final SellerModel sellerModel, final ArrayList<Drawable> array_Images, final ApiCallBack apiCallBack) {

        final VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPDATE_SELLER_INFO, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                String resultResponse = new String(response.data);
                Log.e("updateRegisterApiCall", "resultResponse11" + resultResponse);

                resultResponse = resultResponse.replace("Enter about_us", "");
                Log.e("updateRegisterApiCall", "resultResponse22" + resultResponse);

                try {
                    JSONObject object = new JSONObject(resultResponse);

                    if (object.getInt("success") == 1) {
                        addCustomerInDatabase(context, sellerModel);
                        apiCallBack.onSuccess(sellerModel);

                    } else {
                        apiCallBack.onError(object.getString("message"));
                    }

                } catch (JSONException e) {

                    Log.e("updateRegisterApiCall", "erro" + e.getMessage());
                }


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
                Map<String, String> params = new HashMap<String, String>();
                params.put("seller_id", LoginController.getSellerID(context));
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("seller_name", sellerModel.getName());
                params.put("company_name", sellerModel.getCompany());
                params.put("about_us", sellerModel.getAbout_Us());
                params.put("seller_email_id", sellerModel.getEmailID());
                params.put("category", sellerModel.getCategory());
                Log.e("LoginController", "params" + params.toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();

                if (array_Images.size() > 0)
                    params.put("seller_image", new DataPart("seller_image" + Util.getCurrentTimeStamp() + ".jpg", AppHelper.getFileDataFromDrawable(context, array_Images.get(0)), "image/jpeg"));
                Log.e("LoginController", "params" + params.toString());

                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest);

    }

    public static void sendImageMultipart(final Activity context, final SellerModel sellerModel, final ArrayList<Drawable> array_Images, final ApiCallBack apiCallBack) {

        final VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, USERINFOAPI, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                apiCallBack.onSuccess(response);

                String resultResponse = new String(response.data);

                try {
                    JSONObject object = new JSONObject(resultResponse);

                    if (object.getInt("success") == 1) {
                        final SellerModel sellerModelFinal = new SellerModel();

                        sellerModelFinal.setName(sellerModel.getName());
                        sellerModelFinal.setEmailID(sellerModel.getEmailID());
                        sellerModelFinal.setAddress(sellerModel.getAddress());
                        sellerModelFinal.setCity(sellerModel.getCity());
                        sellerModelFinal.setState(sellerModel.getState());
                        sellerModelFinal.setCountry(sellerModel.getCountry());
                        sellerModelFinal.setPincode(sellerModel.getPincode());
                        sellerModelFinal.setMobile_Number(sellerModel.getMobile_Number());
                        sellerModelFinal.setPanCard_Number(sellerModel.getPanCard_Number());
                        sellerModelFinal.setIsOtpDone("1");
                        sellerModel.setSet_Checked("MyName");

                        sellerModelFinal.setSellerID(String.valueOf(object.getInt("seller_id")));
                        addCustomerInDatabase(context, sellerModelFinal);

                    }

                } catch (Exception e) {

                    Log.e("sendImageMultipart", "erro==" + e.getMessage());
                }


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
                params.put("mobile_no", sellerModel.getMobile_Number());
                params.put("seller_name", sellerModel.getName());
                params.put("seller_address", sellerModel.getAddress());
                params.put("seller_email_id", sellerModel.getEmailID());
                params.put("city", sellerModel.getCity());
                params.put("pincode", sellerModel.getPincode());
                params.put("state", sellerModel.getState());
                params.put("country", sellerModel.getCountry());
                params.put("seller_pan_no", sellerModel.getPanCard_Number());
                Log.e("paramsparams", "==" + params.toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                params.put("seller_image", new DataPart("seller_image" + Util.getCurrentTimeStamp() + ".jpg", AppHelper.getFileDataFromDrawable(context, array_Images.get(0)), "image/jpeg"));
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest);

    }

    public static SellerModel getSingleSellerTable(Activity context) {
        SellerModel sellerModel = new SellerModel();
        Cursor cursor = context.getContentResolver().query(CONTENT_SELLER, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            sellerModel.setMobile_Number(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
            sellerModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            sellerModel.setEmailID(cursor.getString(cursor.getColumnIndex(COLUMN_EMAILID)));
            sellerModel.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME)));
            sellerModel.setCompany(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY)));
            sellerModel.setAbout_Us(cursor.getString(cursor.getColumnIndex(COLUMN_ABOUT_US)));
            sellerModel.setSet_Checked(cursor.getString(cursor.getColumnIndex(COLUMN_CHECKED)));
            sellerModel.setUri(cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)));

        }
        return sellerModel;
    }



    public static void deleteSellerTable(Activity context) {
        context.getContentResolver().delete(CONTENT_SELLER, null, null);
    }
*/
    public static boolean isAllreadyLogin(Activity context) {
        boolean isLogin = false;
        Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        Log.e("LoginController", "CONTENT_CUSTOMER cursor==" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            Log.e("LoginController", "COLUMN_IS_OTP_DONE ==" + cursor.getString(cursor.getColumnIndex(COLUMN_IS_OTP_DONE)));

            if (cursor.getString(cursor.getColumnIndex(COLUMN_IS_OTP_DONE)).matches("1")) {
                isLogin = true;
            }
        }
        Log.e("LoginController", "isLogin==" + isLogin);
        return isLogin;
    }

    public static void addCustomerInDatabase(Activity context, CustomerModel customerModel) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_ID, customerModel.getCustomerId());
        values.put(COLUMN_FIRST_NAME, customerModel.getFirstName());
        values.put(COLUMN_LAST_NAME, customerModel.getLastName());
        values.put(COLUMN_EMAILID, customerModel.getEmailID());
        values.put(COLUMN_MOBILE_NUMBER, customerModel.getMobile_Number());
        values.put(COLUMN_ADDRESS, customerModel.getAddress());
        values.put(COLUMN_LANDMARK, customerModel.getLandMark());
        values.put(COLUMN_AREA, customerModel.getArea());
        values.put(COLUMN_STATE, customerModel.getState());
        values.put(COLUMN_CITY, customerModel.getCity());
        values.put(COLUMN_PINCODE, customerModel.getPincode());
        values.put(COLUMN_SELLER_ID, customerModel.getSellerID());
        //values.put(COLUMN_PROFILE_IMAGE, customerModel.getUri());
        values.put(COLUMN_IS_OTP_DONE, customerModel.getIsOtpDone());

        Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        if (cursor.getCount() > 0)
            context.getContentResolver().update(CONTENT_CUSTOMER, values, null, null);
        else
            context.getContentResolver().insert(CONTENT_CUSTOMER, values);


    }

    public static void addSellerIDInDatabase(Activity context, String sellerId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SELLER_ID, sellerId);
        context.getContentResolver().insert(CONTENT_CUSTOMER, values);
    }

    public static String getSellerID(Activity context) {
        String sellerID = "";
        Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        if (cursor.getCount() > 0) {

            cursor.moveToLast();
            sellerID = cursor.getString(cursor.getColumnIndex(COLUMN_SELLER_ID));
            Log.e("LoginController", "getSellerID sellerID  =" + sellerID);

        }

        return sellerID;
    }

    public static String getCustomerID(Activity context) {
        String customerID = "";

        Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        Log.e("LoginController", "cursor   =" + cursor.getCount());

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            customerID = cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ID));
            Log.e("LoginController", "getCustomerID   =" + customerID);

        }

        return customerID;
    }

    public static String getMobileNumber(Activity context) {
        String MobileNumber = "";

        Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        Log.e("LoginController", "cursor   =" + cursor.getCount());

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            MobileNumber = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER));
            Log.e("LoginController", "MobileNumber   =" + MobileNumber);

        }

        return MobileNumber;
    }

    public static String getCustomerName(Activity context) {
        String customerName = "";

        Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        Log.e("LoginController", "cursor   =" + cursor.getCount());

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            customerName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)) + " " + cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
            Log.e("LoginController", "customerName   =" + customerName);

        }

        return customerName;
    }

    public static String getCustomerEmail(Activity context) {
        String CustomerEmail = "";

        Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        Log.e("LoginController", "cursor   =" + cursor.getCount());

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            CustomerEmail = cursor.getString(cursor.getColumnIndex(COLUMN_EMAILID));
            Log.e("LoginController", "CustomerEmail   =" + CustomerEmail);

        }

        return CustomerEmail;
    }

    public static String getCustomerNumber(Activity context) {
        String CustomerNumber = "";

        Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        Log.e("LoginController", "cursor   =" + cursor.getCount());

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            CustomerNumber = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER));
            Log.e("LoginController", "CustomerNumber   =" + CustomerNumber);

        }

        return CustomerNumber;
    }

  /*  public static String getMobileNumber(Activity context) {
        String mobile_no = "";
        Cursor cursor = context.getContentResolver().query(CONTENT_SELLER, null, null, null, null);
        if (cursor.getCount() > 0) {

            cursor.moveToLast();
            mobile_no = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER));
            Log.e("LoginController", "getMobileNumber  =" + mobile_no);

        }

        return mobile_no;
    }

    public static String getSellerName(Activity context) {
        String name = "";
        Cursor cursor = context.getContentResolver().query(CONTENT_SELLER, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        }
        return name;

    }




*/
}
