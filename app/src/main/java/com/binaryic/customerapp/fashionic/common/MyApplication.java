package com.binaryic.customerapp.fashionic.common;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.binaryic.customerapp.fashionic.models.ProductModel;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Abhi on 02-Sep-16.
 */
@ReportsCrashes(formKey = "ogencencestallysterederh")
public class MyApplication extends Application {
    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    public static String ApiUrl = "http://sellerapp.binaryicdirect.com/v1/";

    public static final String TAG = MyApplication.class.getSimpleName();
    public static SharedPreferences setting;
    public static ProductModel productModel = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setting = getSharedPreferences("com.binaryic.customerapp.fashionic", MODE_PRIVATE);
        ACRA.init(this);
        ACRAReportSender reportSender = new ACRAReportSender();
        ACRA.getErrorReporter().setReportSender(reportSender);
    }


    public static synchronized MyApplication getInstance() {

        Log.e("mInstance", "==" + mInstance);
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        RetryPolicy policy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }


}
