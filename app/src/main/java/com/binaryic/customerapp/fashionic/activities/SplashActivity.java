package com.binaryic.customerapp.fashionic.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CategoryController;
import com.binaryic.customerapp.fashionic.controller.HomeController;
import com.binaryic.customerapp.fashionic.controller.LoginController;
import com.binaryic.customerapp.fashionic.controller.ProductController;

public class SplashActivity extends AppCompatActivity {
    private RelativeLayout rl_spash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rl_spash = (RelativeLayout) findViewById(R.id.rl_spash);
        requestPermission();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.e("MyUtils", "in if");
            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, Manifest.permission.CAMERA)) {
                Log.e("MyUtils", "in if if");
                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE}, 0);
            } else {
                Log.e("MyUtils", "in if else");
                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE}, 0);
            }
        } else {

            callApi();
        }
    }

    public void delay(int seconds) {
        final int milliseconds = seconds * 1000;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }, milliseconds);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            Log.e("SplashActivity", "grantResults.length  =" + grantResults.length);
            if (grantResults.length == 4
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                callApi();
            } else
                Toast.makeText(SplashActivity.this, "Please accept permission for go ahead.", Toast.LENGTH_LONG).show();
        }

    }

    private void callApi() {
        if (InternetConnectionDetector.isConnectingToInternet(SplashActivity.this)) {
            LoginController.getAccessTokenApiCall(SplashActivity.this, new ApiCallBack() {
                        @Override
                        public void onSuccess(Object success) {
                            callCategory();

                            //checkLogin();
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("SplashActivity", "access token error =" + error);
                            callCategory();

                            // checkLogin();
                        }
                    }

            );
        } else {
            MyUtils.showSnackBar(SplashActivity.this, rl_spash, "No Internet Connection..!!");
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            // checkLogin();
        }
    }

    public void callProduct() {
        if (InternetConnectionDetector.isConnectingToInternet(SplashActivity.this)) {
            ProductController.getAllProducts(SplashActivity.this, new ApiCallBack() {
                @Override
                public void onSuccess(Object success) {

                    callBanner();
                }

                @Override
                public void onError(String error) {
                    Log.e("SplashActivity", "product error =" + error);
                    callBanner();
                }
            });
        } else {
            MyUtils.showSnackBar(SplashActivity.this, rl_spash, "No Internet Connection..!!");
            callBanner();
        }
    }

    public void callBanner() {
        if (InternetConnectionDetector.isConnectingToInternet(SplashActivity.this)) {
            HomeController.getBannerDataApiCall(SplashActivity.this, new ApiCallBack() {

                @Override
                public void onSuccess(Object success) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();


                }

                @Override
                public void onError(String error) {
                    Log.e("SplashActivity", "banner error =" + error);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();

                }


            });
        } else {
            MyUtils.showSnackBar(SplashActivity.this, rl_spash, "No Internet Connection..!!");
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();

        }
    }

    public void checkLogin() {

        if (InternetConnectionDetector.isConnectingToInternet(SplashActivity.this)) {

            callCategory();


        } else {
            MyUtils.showSnackBar(SplashActivity.this, rl_spash, "No Internet Connection..!!");
            delay(2);
        }

    }

    private void callCategory() {
        if (InternetConnectionDetector.isConnectingToInternet(SplashActivity.this)) {
            CategoryController.getAllCategoryApi(SplashActivity.this, new ApiCallBack() {
                        @Override
                        public void onSuccess(Object success) {
                            //callProduct();
                            callBanner();
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("SplashActivity", "category error =" + error);
                            // callProduct();
                            callBanner();

                        }
                    }

            );
        } else {
            MyUtils.showSnackBar(SplashActivity.this, rl_spash, "No Internet Connection..!!");
            delay(2);
        }
    }
}
