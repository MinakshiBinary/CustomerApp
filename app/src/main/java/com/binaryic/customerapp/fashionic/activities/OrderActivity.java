package com.binaryic.customerapp.fashionic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CartController;
import com.binaryic.customerapp.fashionic.controller.WishListController;
import com.binaryic.customerapp.fashionic.fragments.FragmentMyAccount;
import com.binaryic.customerapp.fashionic.fragments.FragmentOrderDetail;
import com.binaryic.customerapp.fashionic.fragments.FragmentOrders;
import com.binaryic.customerapp.fashionic.fragments.FragmentWishlist;
import com.binaryic.customerapp.fashionic.models.OrderModel;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView toolbarTitle, tvCount, tvCountWish;
    public FrameLayout layMain;
    private ImageView iv_Account;
    private ImageView iv_Wish;
    private RelativeLayout rl_Account;
    RelativeLayout btnCart, btnWish;
    public ArrayList<OrderModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        SetMenu();
    }

    private void SetMenu() {
        try {
            Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Init();
        } catch (Exception ex) {
        }
    }

    private void Init() {

        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        layMain = (FrameLayout) findViewById(R.id.layMain);
        toolbarTitle.setText("My Orders");
        iv_Account = (ImageView) findViewById(R.id.iv_Account);
        iv_Wish = (ImageView) findViewById(R.id.iv_Wish);
        rl_Account = (RelativeLayout) findViewById(R.id.rl_Account);
        btnCart = (RelativeLayout) findViewById(R.id.btnCart);
        btnWish = (RelativeLayout) findViewById(R.id.btnWish);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCountWish = (TextView) findViewById(R.id.tv_Wish_Count);

        btnCart.setOnClickListener(this);
        iv_Account.setOnClickListener(this);
        iv_Wish.setOnClickListener(this);
        addOrderFragment();

    }

    private void addOrderFragment() {
        FragmentOrders fragmentOrders = new FragmentOrders();
        MyUtils.addFragment(layMain.getId(), fragmentOrders, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCart.getId()) {
            startActivity(new Intent(this, CartActivity.class));
        }  else if (v.getId() == iv_Wish.getId()) {
            Log.e("OrderActivity", "btnWish");
            addWishFragment();
        }  else if (v.getId() == iv_Account.getId()) {
            Log.e("OrderActivity", "rl_Account");
            addMyAccountFragment();
        }
    }

    private void addWishFragment() {
        btnWish.setVisibility(View.GONE);
        FragmentWishlist fragment = new FragmentWishlist();
        MyUtils.addFragmentBack(layMain.getId(), fragment, this);
        toolbarTitle.setText("Wishlist");

    }

    private void addMyAccountFragment() {
        btnWish.setVisibility(View.VISIBLE);
        rl_Account.setVisibility(View.GONE);
        FragmentMyAccount fragment = new FragmentMyAccount();
        MyUtils.addFragmentBackHome(layMain.getId(), fragment, this);
        toolbarTitle.setText("My Account");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCount();
    }

    private void setCount() {
        try {
            int count = CartController.getCartCount(this);
            if (count == 0) {
                tvCount.setVisibility(View.GONE);
            } else {
                tvCount.setText(count + "");
                tvCount.setVisibility(View.VISIBLE);
            }
            int countWish = WishListController.getWishCount(this);
            if (countWish == 0) {
                tvCountWish.setVisibility(View.GONE);
            } else {
                tvCountWish.setText(countWish + "");
                tvCountWish.setVisibility(View.VISIBLE);
            }
            SetLikeList();
        } catch (Exception ex) {
        }
    }

    private void SetLikeList() {
        try {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentWishlist) {
                ((FragmentWishlist) f).ResetList();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentOrderDetail) {
                toolbarTitle.setText("Order Detail");
                btnWish.setVisibility(View.VISIBLE);
            }
            if (f instanceof FragmentOrders) {
                toolbarTitle.setText("My Orders");
                btnWish.setVisibility(View.VISIBLE);
            }
        } else {
            finish();
        }
    }
}
