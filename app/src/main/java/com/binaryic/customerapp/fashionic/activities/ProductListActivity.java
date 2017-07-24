package com.binaryic.customerapp.fashionic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.binaryic.customerapp.fashionic.fragments.CatalogueFragment;
import com.binaryic.customerapp.fashionic.fragments.FragmentMyAccount;
import com.binaryic.customerapp.fashionic.fragments.FragmentWishlist;


public class ProductListActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView toolbarTitle, tvCount;
    public static TextView tv_Wish_Count;
    FrameLayout layMain;
    private ImageView iv_Wish;
    private ImageView iv_Account;
    private String categoryId;
    RelativeLayout btnCart, btnWish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        SetMenu();
    }

    private void SetMenu() {

        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetExtra();
        Init();

    }

    private void Init() {

        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        layMain = (FrameLayout) findViewById(R.id.layMain);
        btnCart = (RelativeLayout) findViewById(R.id.btnCart);
        btnWish = (RelativeLayout) findViewById(R.id.btnWish);
        iv_Wish = (ImageView) findViewById(R.id.iv_Wish);
        iv_Account = (ImageView) findViewById(R.id.iv_Account);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tv_Wish_Count = (TextView) findViewById(R.id.tv_Wish_Count);

        iv_Account.setOnClickListener(this);
        iv_Wish.setOnClickListener(this);
        btnCart.setOnClickListener(this);
        AddCatalogueFragment();

    }

    private void AddCatalogueFragment() {
        try {
            CatalogueFragment catalogueFragment = new CatalogueFragment();
            Bundle bundle = new Bundle();
            bundle.putString("categoryId", categoryId);
            catalogueFragment.setArguments(bundle);
            MyUtils.addFragment(layMain.getId(), catalogueFragment, this);
        } catch (Exception ex) {
        }
    }

    private void GetExtra() {
        try {
            Intent intent = getIntent();
            categoryId = intent.getStringExtra("categoryId");
        } catch (Exception ex) {
        }
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
                tv_Wish_Count.setVisibility(View.GONE);
            } else {
                tv_Wish_Count.setText(countWish + "");
                tv_Wish_Count.setVisibility(View.VISIBLE);
            }
            SetLikeList();
        } catch (Exception ex) {
        }
    }

    private void SetLikeList() {
        try {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof CatalogueFragment) {
                ((CatalogueFragment) f).SetLikeList();
            } else if (f instanceof FragmentWishlist) {
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
        super.onBackPressed();
        try {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            Log.e("ProductListActivity", "fragment=" + f);
            if (f instanceof CatalogueFragment) {
                btnWish.setVisibility(View.VISIBLE);
                onResume();
            } else if (f instanceof FragmentWishlist) {
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.iv_Wish:
                btnWish.setVisibility(View.GONE);

                Log.e("ProductListActivity", "onclick");
                addWishFragment();
                break;
            case R.id.btnCart:
                btnWish.setVisibility(View.VISIBLE);

                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.rl_Account:
                btnWish.setVisibility(View.VISIBLE);

                addMyAccountFragment();
                break;
            case R.id.iv_Notification:
                btnWish.setVisibility(View.VISIBLE);

                addWishFragment();
                break;
            case R.id.iv_Account:
                btnWish.setVisibility(View.VISIBLE);
                addMyAccountFragment();
                break;
        }

    }

    private void addWishFragment() {
        Log.e("ProductListActivity", "in addWishFragment");

        FragmentWishlist fragment = new FragmentWishlist();
        MyUtils.addFragmentBack(R.id.layMain, fragment, this);
        //imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home_selected));
        // btnMyAccount.setImageResource(R.drawable.ic_person);
        ///btnMore.setImageResource(R.drawable.ic_more_home);
        toolbarTitle.setText("Wishlist");

    }

    private void addMyAccountFragment() {

        FragmentMyAccount fragment = new FragmentMyAccount();
        MyUtils.addFragmentBackHome(R.id.layMain, fragment, this);


       /* FragmentMyAccount fragment = new FragmentMyAccount();
        MyUtil.addFragmentBackHome(layPager.getId(), fragment, this);
        //imgFav.setImageResource(R.drawable.ic_favorite_home);
        //  btnMyAccount.setImageResource(R.drawable.ic_person_home_selected);
        // btnMore.setImageResource(R.drawable.ic_more_home);
        toolbarTitle.setText("My Account");*/

    }
}
