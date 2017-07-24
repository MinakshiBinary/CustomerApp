package com.binaryic.customerapp.fashionic.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.Constants;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.common.Validation;
import com.binaryic.customerapp.fashionic.controller.LoginController;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_verify_number;
    private EditText et_Phone_Number;
    private EditText et_First_Name;
    private EditText et_Last_Name;
    private EditText et_EmailID;
    private EditText et_Address;
    private EditText et_Landmark;
    private EditText et_Area;
    private EditText et_Pincode;
    private EditText et_City;
    private AutoCompleteTextView spinner_State;
    private Spinner et_Category;
    private RelativeLayout rl_Login;
    private String[] category = new String[]{
            "Category", "Men", "Women", "Kid"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }


    private void init() {
        tv_verify_number = (TextView) findViewById(R.id.tv_Submit);
        et_Phone_Number = (EditText) findViewById(R.id.et_Phone_Number);
        et_EmailID = (EditText) findViewById(R.id.et_EmailID);
        et_Address = (EditText) findViewById(R.id.et_Address);
        et_Landmark = (EditText) findViewById(R.id.et_Landmark);
        et_Area = (EditText) findViewById(R.id.et_Area);
        et_Pincode = (EditText) findViewById(R.id.et_Pincode);
        et_City = (EditText) findViewById(R.id.et_City);
        spinner_State = (AutoCompleteTextView) findViewById(R.id.spinner_State);
        et_Category = (Spinner) findViewById(R.id.spinner_Category);
        et_First_Name = (EditText) findViewById(R.id.et_First_Name);
        et_Last_Name = (EditText) findViewById(R.id.et_Last_Name);
        rl_Login = (RelativeLayout) findViewById(R.id.rl_Login);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_item, category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_Category.setAdapter(dataAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(SignUpActivity.this,
                R.array.state, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_State.setAdapter(adapter);

        et_Phone_Number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        et_Pincode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        tv_verify_number.setOnClickListener(this);

        et_Phone_Number.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_First_Name.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_Last_Name.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_EmailID.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_Address.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_Landmark.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_Area.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_Pincode.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);
        et_City.setTypeface(Typeface.createFromAsset(getAssets(), "Cairo-Regular.ttf"), 0);


    }


    private void validation(final String phone_Number, final String first_Name, final String last_Name, final String email_ID, final String address, final String landmark, final String area, final String pincode, final String city, final String state) {
        if (InternetConnectionDetector.isConnectingToInternet(SignUpActivity.this)) {
            if (TextUtils.isEmpty(phone_Number) || phone_Number.length() != 10) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Proper Mobile Number");
            } else if (TextUtils.isEmpty(first_Name)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter First Name");

            } else if (TextUtils.isEmpty(last_Name)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Last Name");

            } else if (TextUtils.isEmpty(email_ID)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Email ID");

            } else if (!Validation.checkEmail(email_ID)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Proper Email ID");

            } /*else if (TextUtils.isEmpty(address)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Proper Address");

            } else if (TextUtils.isEmpty(landmark)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Proper Landmark");

            } else if (TextUtils.isEmpty(area)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Proper Area");

            } else if (TextUtils.isEmpty(pincode) || pincode.length() != 6) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Proper Pincode");

            } else if (TextUtils.isEmpty(city)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Enter Proper City");

            } else if (state.matches("State")||TextUtils.isEmpty(state)) {
                MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "Please Select State");

            }*/ else {

                final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setMessage("Please Wait....!!!!");
                progressDialog.setCancelable(false);
                progressDialog.show();
                LoginController.signUpApiCall(SignUpActivity.this, phone_Number, Constants.seller_id, new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        Log.e("SignUpActivity", "success =" + (String) success);
                        progressDialog.dismiss();
                        Intent intent = new Intent(SignUpActivity.this, OtpActivity.class);
                        Toast.makeText(SignUpActivity.this, success.toString(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("otp", (String) success);
                        intent.putExtra("mobile_no", phone_Number);
                        intent.putExtra("first_Name", first_Name);
                        intent.putExtra("last_Name", last_Name);
                        intent.putExtra("email_ID", email_ID);
                        intent.putExtra("address", address);
                        intent.putExtra("landmark", landmark);
                        intent.putExtra("area", area);
                        intent.putExtra("pincode", pincode);
                        intent.putExtra("city", city);
                        intent.putExtra("state", state);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        progressDialog.dismiss();
                        MyUtils.showMessageBox(error.toString(), "OK", "CANCEL", false, SignUpActivity.this);
                        MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            }
                        });

                    }
                });
            }
        } else {
            MyUtils.showSnackBar(SignUpActivity.this, rl_Login, "No Internet Connection..!!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_Submit:                MyUtils.hideKeyboard(this);

                validation(
                        et_Phone_Number.getText().toString().trim(), et_First_Name.getText().toString().trim(),
                        et_Last_Name.getText().toString().trim(), et_EmailID.getText().toString().trim(),
                        et_Address.getText().toString().trim(), et_Landmark.getText().toString().trim(),
                        et_Area.getText().toString().trim(), et_Pincode.getText().toString().trim(),
                        et_City.getText().toString().trim(), spinner_State.getText().toString().trim()
                );
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }
}