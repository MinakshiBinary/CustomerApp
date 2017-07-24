package com.binaryic.customerapp.fashionic.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CategoryController;
import com.binaryic.customerapp.fashionic.controller.HomeBannerController;
import com.binaryic.customerapp.fashionic.controller.LoginController;
import com.binaryic.customerapp.fashionic.controller.ProductController;


public class OtpActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout activity_otp;
    private EditText et_Otp;
    private TextView tv_Submit;
    private String mobile_no = "";
    private String first_Name = "";
    private String last_Name = "";
    private String email_ID = "";
    private String address = "";
    private String landmark = "";
    private String area = "";
    private String pincode = "";
    private String city = "";
    private String state = "";
    private String otp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        init();
        getExtra();
    }

    private void init() {
        activity_otp = (RelativeLayout) findViewById(R.id.activity_otp);
        et_Otp = (EditText) findViewById(R.id.et_Otp);
        tv_Submit = (TextView) findViewById(R.id.tv_Submit);
        tv_Submit.setOnClickListener(this);
        et_Otp.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_Submit:
                MyUtils.hideKeyboard(this);

                validation(et_Otp.getText().toString().trim());
                break;
        }
    }

    private void validation(String str_otp) {
        final ProgressDialog progressDialog = new ProgressDialog(OtpActivity.this);
        progressDialog.setMessage("Please Wait....!!!!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (TextUtils.isEmpty(str_otp)) {
            MyUtils.showSnackBar(OtpActivity.this, activity_otp, "Please Enter OTP");
        } else {
            if (mobile_no.matches("")) {
                if (InternetConnectionDetector.isConnectingToInternet(OtpActivity.this)) {
                    LoginController.loginOtpApiCall(OtpActivity.this, str_otp, new ApiCallBack() {
                        @Override
                        public void onSuccess(Object success) {
                            callCategory(progressDialog);
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("OtpActivity", "error=" + error);
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            MyUtils.showSnackBar(OtpActivity.this, activity_otp, error.toString());

                        }
                    });
                } else {
                    MyUtils.showSnackBar(OtpActivity.this, activity_otp, "No Internet Connection..!!");

                }

            } else if (!str_otp.matches(otp)) {
                MyUtils.showSnackBar(OtpActivity.this, activity_otp, "Please Enter Valid OTP");
            } else {
                LoginController.signUpOtpApiCall(OtpActivity.this, mobile_no, first_Name, last_Name, email_ID, address, landmark, area, pincode, city, state, otp, new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        MyUtils.showSnackBar(OtpActivity.this, activity_otp, "SignUp Successful");
                        startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("OtpActivity", "error=" + error);
                        MyUtils.showSnackBar(OtpActivity.this, activity_otp, "Enter correct otp");
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        }
    }

    public void getExtra() {
        if (getIntent().hasExtra("mobile_no")) {
            otp = getIntent().getStringExtra("otp");
            mobile_no = getIntent().getStringExtra("mobile_no");
            first_Name = getIntent().getStringExtra("first_Name");
            last_Name = getIntent().getStringExtra("last_Name");
            email_ID = getIntent().getStringExtra("email_ID");
            address = getIntent().getStringExtra("address");
            landmark = getIntent().getStringExtra("landmark");
            area = getIntent().getStringExtra("area");
            pincode = getIntent().getStringExtra("pincode");
            city = getIntent().getStringExtra("city");
            state = getIntent().getStringExtra("state");
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
                        startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                        finish();
                    }
                }, milliseconds);
            }
        });
    }

    public void callCategory(final Dialog progressDialog) {
        if (InternetConnectionDetector.isConnectingToInternet(OtpActivity.this)) {
            CategoryController.getAllCategoryApi(OtpActivity.this, new ApiCallBack() {
                        @Override
                        public void onSuccess(Object success) {
                            //callProducts(progressDialog);
                            callBanner(progressDialog);
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("OtpActivity", "Category error =" + error);
                            //callProducts(progressDialog);
                            callBanner(progressDialog);
                        }
                    }

            );
        } else {
            MyUtils.showSnackBar(OtpActivity.this, activity_otp, "No Internet Connection..!!");
            delay(2);
        }
    }

    private void callProducts(final Dialog progressDialog) {
        if (InternetConnectionDetector.isConnectingToInternet(OtpActivity.this)) {
            ProductController.getAllProducts(OtpActivity.this, new ApiCallBack() {
                @Override
                public void onSuccess(Object success) {
                    callBanner(progressDialog);
                }

                @Override
                public void onError(String error) {
                    Log.e("OtpActivity", "Category error =" + error);
                    callBanner(progressDialog);

                }
            });
        } else {
            MyUtils.showSnackBar(OtpActivity.this, activity_otp, "No Internet Connection..!!");
            delay(2);
        }
    }

    private void callBanner(final Dialog progressDialog) {
        if (InternetConnectionDetector.isConnectingToInternet(OtpActivity.this)) {
            HomeBannerController.getBannerApi(OtpActivity.this, new ApiCallBack() {

                @Override
                public void onSuccess(Object success) {
                    progressDialog.dismiss();
                    Toast.makeText(OtpActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    //  startActivity(new Intent(OtpActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(String error) {
                    Log.e("SplashActivity", " error =" + error);
                    progressDialog.dismiss();
                }
            });
        } else {
            MyUtils.showSnackBar(OtpActivity.this, activity_otp, "No Internet Connection..!!");
            delay(2);
        }
    }


}
