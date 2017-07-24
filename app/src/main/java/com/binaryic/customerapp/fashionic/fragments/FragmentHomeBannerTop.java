package com.binaryic.customerapp.fashionic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.adapters.BannerAdapter;
import com.binaryic.customerapp.fashionic.controller.HomeController;
import com.binaryic.customerapp.fashionic.custom.CirclePageIndicator;
import com.binaryic.customerapp.fashionic.custom.TextViewPrimary;
import com.binaryic.customerapp.fashionic.models.BannerModel;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


/**
 * Created by User on 22-01-16.
 */
public class FragmentHomeBannerTop extends Fragment {
    FrameLayout fl_Content;
    TextViewPrimary tv_NoData;
    AutoScrollViewPager mPager;
    CirclePageIndicator mIndicator;
    ArrayList<BannerModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_banner_top, container, false);
        fl_Content = (FrameLayout) view.findViewById(R.id.fl_Content);
        tv_NoData = (TextViewPrimary) view.findViewById(R.id.tv_NoData);
        mPager = (AutoScrollViewPager) view.findViewById(R.id.pager);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        SetList();
        return view;
    }

    public void SetList() {

        HomeController homeController = new HomeController();
        ArrayList<BannerModel> list = homeController.getSlider(getActivity());
        BannerAdapter mAdapter = new BannerAdapter(getActivity(), list);
        mPager.setAdapter(mAdapter);

        Log.e("FragmentHomeBanner", "list ==" + list.size());

        if (list.size() > 0) {
            tv_NoData.setVisibility(View.GONE);
            fl_Content.setVisibility(View.VISIBLE);
            if (list.size() > 1) {
                mIndicator.setViewPager(mPager);
                mPager.setInterval(3000);
                mPager.startAutoScroll();
            }
        } else {
            tv_NoData.setVisibility(View.VISIBLE);
            fl_Content.setVisibility(View.GONE);
        }

    }
}
