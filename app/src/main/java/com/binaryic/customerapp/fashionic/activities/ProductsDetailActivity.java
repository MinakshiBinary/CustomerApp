package com.binaryic.customerapp.fashionic.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.fragments.FragmentProductDetail;
import com.binaryic.customerapp.fashionic.fragments.FragmentProductList;
import com.binaryic.customerapp.fashionic.fragments.FragmentWishlist;

public class ProductsDetailActivity extends AppCompatActivity {
    String productId;
    TextView toolbarTitle;
    FrameLayout layMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_detail);
        setMenu();
    }

    private void setMenu() {
        getExtra();
        init();
    }

    private void getExtra() {
        if (getIntent().hasExtra("productId")) {
            productId = getIntent().getStringExtra("productId");
        }
    }

    private void init() {
        layMain = (FrameLayout) findViewById(R.id.layMain);
        addDetailFragment();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
        if (f instanceof FragmentProductList) {
            toolbarTitle.setText("");
        } else if (f instanceof FragmentWishlist) {
            toolbarTitle.setText("Wishlist");
        }

    }

    private void addDetailFragment() {
        FragmentProductDetail fragmentProductDetail = new FragmentProductDetail();
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        fragmentProductDetail.setArguments(bundle);
        MyUtils.addFragment(layMain.getId(), fragmentProductDetail, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layMain);
            if (f instanceof FragmentProductDetail) {
                ((FragmentProductDetail) f).SetRatingData();


               /* if (CartController.getCartCount(ProductDetailActivity.this) == 0) {
                    ((FragmentProductDetail)f).tvp_Count.setVisibility(View.GONE);
                } else {
                    ((FragmentProductDetail)f).tvp_Count.setVisibility(View.VISIBLE);
                    ((FragmentProductDetail)f).tvp_Count.setText(""+CartController.getCartCount(ProductDetailActivity.this));
                }*/
            }
        } catch (Exception ex) {
        }
    }
}
