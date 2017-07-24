package com.binaryic.customerapp.fashionic.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.fragments.FragmentRatings;
import com.binaryic.customerapp.fashionic.fragments.FragmentWriteReview;

public class ReviewActivity extends AppCompatActivity {
    public FrameLayout layMain;
    public TextView tv_Header;
    FragmentRatings fragmentRatings;
    FragmentWriteReview fragmentWriteReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Init();
        AddReviewFragment();
    }
    private void Init() {

            layMain = (FrameLayout) findViewById(R.id.layMain);
            tv_Header = (TextView) findViewById(R.id.tv_Header);
            ImageView iv_BtnClose = (ImageView) findViewById(R.id.iv_BtnClose);
        iv_BtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


    }
    private void AddReviewFragment() {
        try {
            tv_Header.setText("Rating and Review");
            fragmentRatings = new FragmentRatings();
            MyUtils.addFragment(layMain.getId(), fragmentRatings, this);
        } catch (Exception ex) {
        }
    }

    public void AddWriteRatingFragment(){
        fragmentWriteReview = new FragmentWriteReview();
        MyUtils.addFragmentBack(layMain.getId(),fragmentWriteReview,this);
        tv_Header.setText(MyApplication.productModel.getProduct_Name());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


