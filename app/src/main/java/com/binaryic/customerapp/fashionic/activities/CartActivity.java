package com.binaryic.customerapp.fashionic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.fragments.FragmentCart;
import com.binaryic.customerapp.fashionic.fragments.FragmentDelivery;
import com.binaryic.customerapp.fashionic.fragments.FragmentPayment;


/**
 * Created by Asd on 16-09-2016.
 */
public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    public static LinearLayout ll_cart;
    FrameLayout layMain;
    FragmentCart fragmentCart;
    FragmentDelivery fragmentDelivery;
    FragmentPayment fragmentPayment;
    TextView tvHeader;
    Boolean isClickOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        layMain = (FrameLayout) findViewById(R.id.layMain);
        addCartFragment();
    }

    private void init() {

        ll_cart = (LinearLayout) findViewById(R.id.ll_cart);
        tvHeader = (TextView) findViewById(R.id.tv_Header);
        ImageView btnClose = (ImageView) findViewById(R.id.iv_BtnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void addCartFragment() {

        tvHeader.setText("Your cart");
        fragmentCart = new FragmentCart();
        MyUtils.addFragment(layMain.getId(), fragmentCart, this);

    }

    public void addDeliveryFragment(String Amount) {
        Log.e("addDeliveryFragment","=="+Amount);
        tvHeader.setText("Delivery Address");
        fragmentDelivery = new FragmentDelivery();
        Bundle bundle = new Bundle();
        bundle.putString("Amount", Amount);
        fragmentDelivery.setArguments(bundle);
        MyUtils.addFragmentBack(layMain.getId(), fragmentDelivery, this);

    }

    public void addPaymentFragment(String Amount) {

        tvHeader.setText("Payment Option");
        fragmentPayment = new FragmentPayment();
        Bundle bundle = new Bundle();
        bundle.putString("Amount", Amount);
        fragmentPayment.setArguments(bundle);
        MyUtils.addFragmentBack(layMain.getId(), fragmentPayment, this);

    }

    @Override
    public void onClick(View v) {

        if (isClickOn) {
            if (v.getId() == R.id.iv_BtnClose) {
                finish();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
        if (f instanceof FragmentCart) {
            tvHeader.setText("Your cart");

        } else if (f instanceof FragmentDelivery) {
            tvHeader.setText("Delivery Address");
        } else if (f instanceof FragmentPayment) {
            tvHeader.setText("Payment Option");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                setResult(resultCode, data);
                Log.e("result", data.getStringExtra("result"));
                if (data.getStringExtra("result").equals("success")) {
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
                    if (f instanceof FragmentPayment) {
                        ((FragmentPayment) f).setOrder(true);
                    }
                }
            }
        }*/
    }
}
