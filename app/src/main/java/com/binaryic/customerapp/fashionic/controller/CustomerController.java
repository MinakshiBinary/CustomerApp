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
import com.binaryic.customerapp.fashionic.models.AddressModel;
import com.binaryic.customerapp.fashionic.models.CustomerModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.customerapp.fashionic.common.Constants.ADD_ADDRESS_API;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_ADDRESS;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_ADDRESS_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_AREA;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CITY;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_COUNTRY;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CUSTOMER_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_DEFAULT_ADDRESS;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_EMAILID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_FIRST_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_LANDMARK;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_LAST_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_MOBILE_NUMBER;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_PINCODE;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_STATE;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_ADDRESS;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_CUSTOMER;
import static com.binaryic.customerapp.fashionic.common.Constants.DELETE_ADDRESS_API;
import static com.binaryic.customerapp.fashionic.common.Constants.EDIT_ADDRESS_API;
import static com.binaryic.customerapp.fashionic.common.Constants.GET_ADDRESS_API;
import static com.binaryic.customerapp.fashionic.common.Constants.UPDATE_ADDRESS_STATUS_API;

/**
 * Created by Asd on 23-09-2016.
 */
public class CustomerController {

    public static void addAddressApi(final Activity context, final AddressModel customerModel, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_ADDRESS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("addAddressApiResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        callback.onSuccess(object.getString("message"));
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
                Log.e("addAddressApiResponse", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("customer_id", LoginController.getCustomerID(context));
                params.put("customer_address", customerModel.getCustomer_Address());
                params.put("landmark", customerModel.getLandmark());
                params.put("mobile_number", customerModel.getMobileNumber());
                params.put("first_name", customerModel.getFirstName());
                params.put("last_name", customerModel.getLastName());
                params.put("area", customerModel.getArea());
                params.put("pincode", customerModel.getPincode());
                params.put("city", customerModel.getCity());
                params.put("state", customerModel.getState());
                params.put("seller_id", Constants.seller_id);

                Log.e("CustomerController", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "editAddressApi");

    }

    public static void editAddressApi(final Activity context, final AddressModel customerModel, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT_ADDRESS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("editAddressApiResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("success") == 1) {
                        callback.onSuccess(object.getString("message"));
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
                Log.e("editAddressApiResponse", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("customer_id", LoginController.getCustomerID(context));
                params.put("seller_id", Constants.seller_id);
                params.put("address_id", customerModel.getAddress_Id());
                params.put("customer_address", customerModel.getCustomer_Address());
                params.put("mobile_number", customerModel.getMobileNumber());
                params.put("first_name", customerModel.getFirstName());
                params.put("last_name", customerModel.getLastName());
                params.put("landmark", customerModel.getLandmark());
                params.put("area", customerModel.getArea());
                params.put("pincode", customerModel.getPincode());
                params.put("city", customerModel.getCity());
                params.put("state", customerModel.getState());
                Log.e("CustomerController", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "editAddressApi");

    }

    public static void getAllAddress(final Activity context, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ADDRESS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("getAllAddressResponse", response);

                try {
                    JSONObject object = new JSONObject(response);
                    ArrayList<AddressModel> array_Prodcts = new ArrayList<AddressModel>();

                    if (object.getInt("success") == 1) {
                        JSONArray jsonArray_Data = object.getJSONArray("Addresses");
                        deleteAllAddressTable(context);
                        for (int i = 0; i < jsonArray_Data.length(); i++) {
                            JSONObject jsonObject = jsonArray_Data.getJSONObject(i);

                            final AddressModel addressModel = new AddressModel();

                            addressModel.setAddress_Id(jsonObject.getString("address_id"));
                            addressModel.setCustomer_Address(jsonObject.getString("customer_address"));
                            addressModel.setCustomer_Id(LoginController.getCustomerID(context));
                            addressModel.setFirstName(jsonObject.getString("first_name"));
                            addressModel.setLastName(jsonObject.getString("last_name"));
                            addressModel.setMobileNumber(jsonObject.getString("mobile_number"));
                            addressModel.setPincode(jsonObject.getString("pincode"));
                            Log.e("CustomerController", "pincode==" + jsonObject.getString("pincode"));
                            addressModel.setLandmark(jsonObject.getString("landmark"));
                            addressModel.setArea(jsonObject.getString("area"));
                            addressModel.setCity(jsonObject.getString("city"));
                            addressModel.setState(jsonObject.getString("state"));
                            Log.e("CustomerController", "default_address status ==" + (jsonObject.getString("default_address").matches("1") ? true : false));
                            addressModel.setDefaultAddress((jsonObject.getString("default_address").matches("1") ? true : false));

                            addAddress(context, addressModel);

                            array_Prodcts.add(addressModel);
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
                Log.e("getAllAddressError", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("customer_id", LoginController.getCustomerID(context));
                params.put("seller_id", Constants.seller_id);

                Log.e("getAllAddressActivity", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "getAllAddress");


    }

    public static void updateAddressStatusApi(final Activity context, final String addressId, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_ADDRESS_STATUS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("updateAddressStatus", response);

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getInt("success") == 1) {
                        callback.onSuccess(object.getString("message"));
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
                Log.e("updateStatusError", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("address_id", addressId);
                params.put("customer_id", LoginController.getCustomerID(context));
                params.put("seller_id", Constants.seller_id);

                Log.e("updateAddressStatusApi", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "updateAddressStatusApi");


    }

    public static void deleteAddressApi(final Activity context, final String addressId, final ApiCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_ADDRESS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("updateAddressStatus", response);

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getInt("success") == 1) {
                        callback.onSuccess(object.getString("message"));
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
                Log.e("updateStatusError", error.toString());
                callback.onError(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("access_token", LoginController.getAccessTokenFromDatabase(context));
                params.put("address_id", addressId);
                params.put("customer_id", LoginController.getCustomerID(context));
                params.put("seller_id", Constants.seller_id);

                Log.e("updateAddressStatusApi", "params" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "updateAddressStatusApi");


    }

    public static void deleteAllAddressTable(Activity context) {
        context.getContentResolver().delete(CONTENT_ADDRESS, null, null);
    }

    public static void addAddress(Activity context, AddressModel customer) {
        Log.e("customerController", "address are adding into table ");


        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS_ID, customer.getAddress_Id());
        values.put(COLUMN_ADDRESS, customer.getCustomer_Address());
        values.put(COLUMN_CITY, customer.getCity());
        Log.e("customerController", "adding into table status==" + customer.isDefaultAddress());
        values.put(COLUMN_DEFAULT_ADDRESS, customer.isDefaultAddress());
        values.put(COLUMN_COUNTRY, "India");
        values.put(COLUMN_LANDMARK, customer.getLandmark());
        values.put(COLUMN_AREA, customer.getArea());
        values.put(COLUMN_STATE, customer.getState());
        values.put(COLUMN_FIRST_NAME, customer.getFirstName());
        values.put(COLUMN_LAST_NAME, customer.getLastName());
        values.put(COLUMN_MOBILE_NUMBER, customer.getMobileNumber());
        values.put(COLUMN_PINCODE, customer.getPincode());

        String selection = COLUMN_ADDRESS_ID + " = '" + customer.getAddress_Id() + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_ADDRESS, null, selection, null, null);
        if (cursor.getCount() > 0)
            context.getContentResolver().update(CONTENT_ADDRESS, values, selection, null);
        else
            context.getContentResolver().insert(CONTENT_ADDRESS, values);

        Cursor cursor1 = context.getContentResolver().query(CONTENT_ADDRESS, null, null, null, null);
        Log.e("customerController", "getAddresses==" + cursor1.getCount());

    }

    public static ArrayList<AddressModel> getAddresses(Activity context) {
        ArrayList<AddressModel> addresses = new ArrayList<AddressModel>();
        Log.e("customerController", "contextcontext==" + context);

        Cursor cursor = context.getContentResolver().query(CONTENT_ADDRESS, null, null, null, null);
        Log.e("customerController", "getAddresses==" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                AddressModel addressModel = new AddressModel();

                addressModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
                addressModel.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                addressModel.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                addressModel.setContry(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)));
                addressModel.setAddress_Id(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_ID)));
                addressModel.setCustomer_Address(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                addressModel.setCustomer_Id(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ID)));
                addressModel.setPincode(cursor.getString(cursor.getColumnIndex(COLUMN_PINCODE)));
                addressModel.setLandmark(cursor.getString(cursor.getColumnIndex(COLUMN_LANDMARK)));
                addressModel.setArea(cursor.getString(cursor.getColumnIndex(COLUMN_AREA)));
                addressModel.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
                addressModel.setState(cursor.getString(cursor.getColumnIndex(COLUMN_STATE)));
                Log.e("customerController", "status ==" + cursor.getString(cursor.getColumnIndex(COLUMN_DEFAULT_ADDRESS)));
                Log.e("customerController", "status ==" + (cursor.getString(cursor.getColumnIndex(COLUMN_DEFAULT_ADDRESS)).matches("1") ? true : false));
                addressModel.setDefaultAddress(cursor.getString(cursor.getColumnIndex(COLUMN_DEFAULT_ADDRESS)).matches("1") ? true : false);
                addresses.add(addressModel);
            }
        }
        Log.e("customerController", "ArrayList addresses ==" + addresses.size());
        return addresses;
    }

    public static AddressModel getSingleAddresses(Activity context, String addressId) {

        AddressModel addressModel = new AddressModel();
        String selection = COLUMN_ADDRESS_ID + " ='" + addressId + "'";
        Log.e("customerController", "selection==" + selection);

        Cursor cursor = context.getContentResolver().query(CONTENT_ADDRESS, null, selection, null, null);
        Log.e("customerController", "getSingleAddresses==" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToLast();

            addressModel.setContry(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)));
            addressModel.setAddress_Id(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_ID)));
            addressModel.setCustomer_Address(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            addressModel.setCustomer_Id(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ID)));
            addressModel.setPincode(cursor.getString(cursor.getColumnIndex(COLUMN_PINCODE)));
            addressModel.setLandmark(cursor.getString(cursor.getColumnIndex(COLUMN_LANDMARK)));
            addressModel.setArea(cursor.getString(cursor.getColumnIndex(COLUMN_AREA)));
            addressModel.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
            addressModel.setState(cursor.getString(cursor.getColumnIndex(COLUMN_STATE)));
            addressModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
            addressModel.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
            addressModel.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));

            addressModel.setDefaultAddress(cursor.getString(cursor.getColumnIndex(COLUMN_DEFAULT_ADDRESS)).matches("1") ? true : false);

        }
        return addressModel;
    }

    public static ArrayList getDefaultAddress(Activity context) {
        ArrayList<AddressModel> addresses = new ArrayList<AddressModel>();

        String selection = COLUMN_DEFAULT_ADDRESS + " = '" + 1 + "'";
        Log.e("customerController", "selection==" + selection);
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(CONTENT_ADDRESS, null, selection, null, null);
            Log.e("customerController", "getDefaultAddress==" + cursor.getCount());
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    AddressModel addressModel = new AddressModel();

                    addressModel.setContry(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)));
                    addressModel.setAddress_Id(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_ID)));
                    addressModel.setCustomer_Address(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                    addressModel.setCustomer_Id(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ID)));
                    addressModel.setPincode(cursor.getString(cursor.getColumnIndex(COLUMN_PINCODE)));
                    addressModel.setLandmark(cursor.getString(cursor.getColumnIndex(COLUMN_LANDMARK)));
                    addressModel.setArea(cursor.getString(cursor.getColumnIndex(COLUMN_AREA)));
                    addressModel.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
                    addressModel.setState(cursor.getString(cursor.getColumnIndex(COLUMN_STATE)));
                    addressModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
                    addressModel.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                    addressModel.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                    addressModel.setDefaultAddress(cursor.getString(cursor.getColumnIndex(COLUMN_DEFAULT_ADDRESS)).matches("1") ? true : false);
                    addresses.add(addressModel);
                }
            }
        }
        return addresses;
    }

    public static CustomerModel getCustomerData(Context context) {
        CustomerModel customer = null;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
            if (cursor.getCount() > 0) {
                customer = new CustomerModel();
                cursor.moveToLast();
                customer.setEmailID(cursor.getString(cursor.getColumnIndex(COLUMN_EMAILID)));
                customer.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                customer.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                customer.setMobile_Number(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER)));
            }
        } catch (Exception ex) {
        }
        return customer;
    }

    /*

    public static void signOut(Activity context) {
        context.getContentResolver().delete(CONTENT_USER, null, null);
        context.getContentResolver().delete(CONTENT_WISHLIST, null, null);
        context.getContentResolver().delete(CONTENT_CART, null, null);
        context.getContentResolver().delete(CONTENT_RECENT, null, null);
        context.finishAffinity();
        MyApplication.setting.edit().clear().commit();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

  public static CustomerModel getCustomerData(Activity context) {
        CustomerModel customer = null;

        Cursor cursor = context.getContentResolver().query(CONTENT_ADDRESS, null, null, null, null);
        if (cursor.getCount() > 0) {
            customer = new CustomerModel();
            cursor.moveToLast();
            customer.setEmailID(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            customer.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
            customer.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
            customer.set(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            List<Address> addresses = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESSES)), new TypeToken<List<Address>>() {
            }.getType());
            customer.setAddresses(addresses);
            customer.setDefaultAddress(new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DEFAULT_ADDRESS)), Address.class));
        }

        return customer;
    }

    public static void setAddress(Customer customer, Activity context) {

        CustomerModel customerModel = getCustomerData(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, customer.getEmail());
        values.put(COLUMN_USER_FIRST_NAME, customer.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, customer.getLastName());
        values.put(COLUMN_USER_ADDRESSES, new Gson().toJson(customer.getAddresses()));
        values.put(COLUMN_USER_DEFAULT_ADDRESS, new Gson().toJson(customer.getDefaultAddress()));
        values.put(COLUMN_USER_ID, customer.getId());
        values.put(COLUMN_USER_PASSWORD, customerModel.getPassword());
        context.getContentResolver().delete(CONTENT_USER, null, null);
        context.getContentResolver().insert(CONTENT_USER, values);

    }

    public static void setDefaultAddress(ArrayList<Address> listAddresses, Address address, Activity context) {

        CustomerModel customerModel = getCustomerData(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, customerModel.getEmail());
        values.put(COLUMN_USER_FIRST_NAME, customerModel.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, customerModel.getLastName());
        values.put(COLUMN_USER_ADDRESSES, new Gson().toJson(listAddresses));
        values.put(COLUMN_USER_DEFAULT_ADDRESS, new Gson().toJson(address));
        values.put(COLUMN_USER_ID, MyApplication.setting.getString(SH_USER_ID, ""));
        values.put(COLUMN_USER_PASSWORD, customerModel.getPassword());
        context.getContentResolver().delete(CONTENT_USER, null, null);
        context.getContentResolver().insert(CONTENT_USER, values);

    }

    public static void removeAddress(ArrayList<Address> listAddresses, Activity context) {

        CustomerModel customerModel = getCustomerData(context);
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, customerModel.getEmail());
        values.put(COLUMN_USER_FIRST_NAME, customerModel.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, customerModel.getLastName());
        values.put(COLUMN_USER_ADDRESSES, new Gson().toJson(listAddresses));
        values.put(COLUMN_USER_DEFAULT_ADDRESS, new Gson().toJson(customerModel.getDefaultAddress()));
        values.put(COLUMN_USER_ID, MyApplication.setting.getString(SH_USER_ID, ""));
        values.put(COLUMN_USER_PASSWORD, customerModel.getPassword());
        context.getContentResolver().delete(CONTENT_USER, null, null);
        context.getContentResolver().insert(CONTENT_USER, values);

    }

    public static boolean checkUserLogin(Activity context) {
        boolean isLogin = false;

        isLogin = MyApplication.setting.getBoolean(SH_USER_LOGIN, false);
        Log.e("CustomerController", "isLogin= " + isLogin);

        if (isLogin) {
            CustomerModel customerModel = getCustomerData(context);

            if (MyApplication.setting.getString(SH_USER_ID, "").equals("")) {
                if (customerModel != null) {
                    MyApplication.setting.edit().putString(SH_USER_ID, customerModel.getUserId()).commit();
                } else
                    isLogin = false;
            }
            if (customerModel != null) {
                renewCustomer(customerModel.getEmail(), customerModel.getPassword());
            }
        }

        Log.e("custId", MyApplication.setting.getString(SH_USER_ID, ""));
        return isLogin;
    }

    private static void setCustomerToSdk(String email, String password) {

        final AccountCredentials accountCredentials = new AccountCredentials(email, password);
        final BuyClient buyClient = MyApplication.newBuyInstance();
        buyClient.loginCustomer(accountCredentials, new Callback<Customer>() {
            @Override
            public void success(Customer customer) {
                CustomerToken token = buyClient.getCustomerToken();

            }

            @Override
            public void failure(BuyClientError error) {
                // handle error
            }
        });

    }

    private static void renewCustomer(final String email, final String password) {

        final BuyClient buyClient = MyApplication.newBuyInstance();
        buyClient.renewCustomer(new Callback<CustomerToken>() {
            @Override
            public void success(CustomerToken response) {
                buyClient.setCustomerToken(response);
            }

            @Override
            public void failure(BuyClientError error) {
                setCustomerToSdk(email, password);
            }
        });

    }

    public static Address getDefaultAddress(Activity context) {
        Address address = null;

        Cursor cursor = context.getContentResolver().query(CONTENT_USER, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            address = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DEFAULT_ADDRESS)), Address.class);
        }

        return address;
    }


*/

}
