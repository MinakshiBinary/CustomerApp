package com.binaryic.customerapp.fashionic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.MainActivity;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CategoryController;
import com.binaryic.customerapp.fashionic.controller.HomeController;
import com.binaryic.customerapp.fashionic.controller.LoginController;
import com.binaryic.customerapp.fashionic.custom.TextViewPrimary;
import com.binaryic.customerapp.fashionic.models.BannerModel;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by HP on 03-Apr-17.
 */

public class FragmentHome1 extends Fragment {
    ScrollView sv_Banner;
    TextViewPrimary tv_NoData;
    LinearLayout ll_main;
    ArrayList<BannerModel> listPromotional;
    JSONArray arrayCollection;
    private SwipeRefreshLayout swipeContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home1, container, false);
        sv_Banner = (ScrollView) view.findViewById(R.id.sv_Banner);
        ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
        tv_NoData = (TextViewPrimary) view.findViewById(R.id.tv_NoData);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("CatelogueFragment", "swipeContainer");
                //getProducts(Filter_Sort_Controller.sort, "");
                try {
                    MainActivity.btnWish.setVisibility(View.VISIBLE);

                    bannerApi();
                    swipeContainer.setRefreshing(false);
                } catch (Exception ex) {
                    Log.e("FragmentHome1", "error==" + ex.getMessage());

                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        bannerApi();

    }

    private void getSlider() {
        HomeController homeController = new HomeController();
        ArrayList<BannerModel> list = homeController.getSlider(getActivity());
        Log.e("FragmentHome1", "list==" + list.size());
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.ll_Main);

        FragmentHomeBannerTop homeBannerTop =   new FragmentHomeBannerTop();

        Log.e("inseteddd", "is==" + (fragment instanceof FragmentHomeBannerTop));

        MyUtils.addFragment(ll_main.getId(), homeBannerTop, getActivity());

    }

    private void getPromotional() {
        HomeController homeController = new HomeController();
        listPromotional = homeController.getPromotional(getActivity());
    }

    private void getCollection() {
        HomeController homeController = new HomeController();
        arrayCollection = homeController.getCollection(getActivity());
        Log.e("FragmentHome1", "arrayCollection==" + arrayCollection.length());
    }

    private void setCollectionBanner() {
        int total = listPromotional.size() + arrayCollection.length();
        if (total > 0) {
            sv_Banner.setVisibility(View.VISIBLE);
            tv_NoData.setVisibility(View.GONE);
            ll_main.removeAllViews();

            for (int i = 0; i < total; i++) {


                if (i < arrayCollection.length()) {
                    try {
                        FragmentHomeCollection fragmentHomeCollection = new FragmentHomeCollection();
                        Log.e("FragmentHome1", "arrayCollection=getJSONObject=" + arrayCollection.getJSONObject(i).toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("json", arrayCollection.getJSONObject(i).toString());
                        fragmentHomeCollection.setArguments(bundle);


                        MyUtils.addFragment(ll_main.getId(), fragmentHomeCollection, getActivity());
                    } catch (Exception ex) {
                    }
                }
                if (i < listPromotional.size()) {
                    BannerModel bannerModel = listPromotional.get(i);
                    FragmentPromotionalBanner fragmentPromotionalBanner = new FragmentPromotionalBanner();
                    Bundle bundle = new Bundle();
                    bundle.putString("banner_id", bannerModel.getBanner_id());
                    bundle.putString("banner_image", bannerModel.getBanner_image());
                    bundle.putString("category_id", bannerModel.getCategory_id());
                    bundle.putString("category_name", bannerModel.getCategory_name());
                    fragmentPromotionalBanner.setArguments(bundle);

                    MyUtils.addFragment(ll_main.getId(), fragmentPromotionalBanner, getActivity());
                }
            }
        } else {
            tv_NoData.setVisibility(View.VISIBLE);

        }
    }

    private void bannerApi() {

        LoginController.getAccessTokenApiCall(getActivity(), new ApiCallBack() {
            @Override
            public void onSuccess(Object success) {
                getAllCategories();
            }

            @Override
            public void onError(String error) {
                getAllCategories();
            }
        });

    }

    private void getAllCategories() {

        CategoryController.getAllCategoryApi(getActivity(), new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        //callProduct();
                        getAllData();
                        MainActivity.addHomeFragment(getActivity());
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("SplashActivity", "category error =" + error);
                        // callProduct();
                        getAllData();

                    }
                }

        );
    }


    private void getAllData() {
        if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
            HomeController.getBannerDataApiCall(getActivity(), new ApiCallBack() {

                @Override
                public void onSuccess(Object success) {
                    getSlider();
                    getPromotional();
                    getCollection();
                    setCollectionBanner();
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    getSlider();
                    getPromotional();
                    getCollection();
                    setCollectionBanner();
                    swipeContainer.setRefreshing(false);
                }


            });
        } else {
            MyUtils.showSnackBar(getActivity(), MainActivity.drawer, "No Internet Connection..!!");
            getSlider();
            getPromotional();
            getCollection();
            setCollectionBanner();
            swipeContainer.setRefreshing(false);
        }
    }

}
