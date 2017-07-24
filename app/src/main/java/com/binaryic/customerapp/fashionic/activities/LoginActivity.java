package com.binaryic.customerapp.fashionic.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.Constants;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.LoginController;
import com.binaryic.customerapp.fashionic.models.LoginModel;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_SignUp;
    private TextView tv_SignUp;
    private TextView tv_Skip;
    private LinearLayout ll_Submit;
    private EditText et_Phone_Number;
    private RelativeLayout rl_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_Submit:                MyUtils.hideKeyboard(this);

                validation(et_Phone_Number.getText().toString().trim());

                break;
            case R.id.ll_SignUp:
                MyUtils.hideKeyboard(this);
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
                break;
            case R.id.tv_Skip:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private void init() {
        ll_SignUp = (LinearLayout) findViewById(R.id.ll_SignUp);
        tv_Skip = (TextView) findViewById(R.id.tv_Skip);
        tv_SignUp = (TextView) findViewById(R.id.tv_SignUp);
        ll_Submit = (LinearLayout) findViewById(R.id.ll_Submit);
        et_Phone_Number = (EditText) findViewById(R.id.et_Phone_Number);
        rl_Login = (RelativeLayout) findViewById(R.id.rl_Login);

        et_Phone_Number.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_Phone_Number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        ll_Submit.setOnClickListener(this);
        ll_SignUp.setOnClickListener(this);
        tv_Skip.setOnClickListener(this);

        String mystring = new String("Sign Up for Free");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        tv_SignUp.setText(content);
    }


    private void validation(String phone_Number) {
        Log.e("Login", "Internet==" + InternetConnectionDetector.isConnectingToInternet(LoginActivity.this));

        if (InternetConnectionDetector.isConnectingToInternet(LoginActivity.this)) {
            Log.e("Login", "in validation");

            if (TextUtils.isEmpty(phone_Number) || phone_Number.length() != 10) {
                MyUtils.showSnackBar(LoginActivity.this, rl_Login, "Please Enter Proper Mobile Number");

            } else {

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Please Wait....!!!!");
                progressDialog.setCancelable(false);
                progressDialog.show();
                LoginController.registerApiCall(LoginActivity.this, phone_Number, Constants.seller_id, new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        LoginModel loginModel = (LoginModel) success;

                        Log.e("getSuccess", "==" + loginModel.getSuccess());
                        if (loginModel.getSuccess() == 1) {
                            progressDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(LoginActivity.this, loginModel.getOtp(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(String error) {
                        progressDialog.dismiss();
                        Log.e("LoginActivity", "error=" + error);
                        MyUtils.showMessageBox(error.toString(), "OK", "CANCEL", false, LoginActivity.this);
                        MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyUtils.msgDialog.dismiss();
                            }
                        });

                    }
                });
            }
        } else {
            MyUtils.showMessageBox("No Internet Connection..!!!", "OK", "CANCEL", false, LoginActivity.this);

        }


    }


}
