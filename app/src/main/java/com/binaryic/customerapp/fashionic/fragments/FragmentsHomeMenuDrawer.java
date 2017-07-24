package com.binaryic.customerapp.fashionic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.LoginActivity;
import com.binaryic.customerapp.fashionic.adapters.CategoryAdapter;
import com.binaryic.customerapp.fashionic.adapters.HomeDrawerAdapter;
import com.binaryic.customerapp.fashionic.adapters.HomeDrawerMenuAdapter;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CategoryController;
import com.binaryic.customerapp.fashionic.controller.LoginController;
import com.binaryic.customerapp.fashionic.models.CategoryModel;

import java.util.ArrayList;

/**
 * Created by Binary_Apple on 3/23/17.
 */

public class FragmentsHomeMenuDrawer extends Fragment {
    RecyclerView main_recycler;
    HomeDrawerAdapter adapter;
    ArrayList<String> list;
    private RelativeLayout rl_SignOut;
    private ImageView logo;
    private ImageView iv_Profile_Image;
    private ImageView iv_MenuIcon;
    private TextView tv_UserName;
    private TextView tv_UserMobileNumber;
    private CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> array_Category_Model = new ArrayList<CategoryModel>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu_drawer, container, false);
        iv_Profile_Image = (ImageView) view.findViewById(R.id.iv_Profile_Image);
        iv_MenuIcon = (ImageView) view.findViewById(R.id.iv_MenuIcon);
        rl_SignOut = (RelativeLayout) view.findViewById(R.id.rl_SignOut);
        tv_UserMobileNumber = (TextView) view.findViewById(R.id.tv_UserMobileNumber);
        tv_UserName = (TextView) view.findViewById(R.id.tv_UserName);
        iv_Profile_Image.setColorFilter(getResources().getColor(R.color.white));
        iv_MenuIcon.setColorFilter(getResources().getColor(R.color.white));

        getData();

        main_recycler = (RecyclerView) view.findViewById(R.id.main_recycler);
        main_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        main_recycler.hasFixedSize();
        rl_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtils.deleteAllDatabase(getActivity());
                getActivity().finishAffinity();
                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });
        SetUpRecyclerView();
        // logo = (ImageView) view.findViewById(R.id.logo);
        //MenuController menuController = new MenuController();
        // list = menuController.getMenuHeader(getActivity());
        return view;
    }

    private void getData() {
        tv_UserName.setText(LoginController.getCustomerName(getActivity()));
        tv_UserMobileNumber.setText(LoginController.getCustomerNumber(getActivity()));
    }

   /* private void getCategory() {
        Log.e("FragmentHomeMenuDrawer", "in get Category");
        if (InternetConnectionDetector.isConnectingToInternet(getActivity(), drawer, "No Internet Connection")) {
            // final ProgressDialog progressDialog = new ProgressDialog(getContext());
            // progressDialog.setMessage("Please Wait....!!!!");
            //  progressDialog.show();
            //  progressDialog.setCancelable(false);
            CategoryController.getAllCategoryApi(getActivity(), "", new ApiCallBack() {
                @Override
                public void onSuccess(Object success) {

                    SetUpRecyclerView();

                    // progressDialog.dismiss();

                }

                @Override
                public void onError(String error) {

                    // progressDialog.dismiss();
                }
            });
        }
    }*/

    private void SetUpRecyclerView() {
        array_Category_Model = CategoryController.getAllCategoryFromTable(getActivity(), array_Category_Model, "");
        HomeDrawerMenuAdapter adapter = new HomeDrawerMenuAdapter(getActivity(), array_Category_Model);
        main_recycler.setAdapter(adapter);
    }


/*
    public void setUpCategoryRecycleView(int upCategoryRecycleView) {
        this.upCategoryRecycleView = upCategoryRecycleView;
    }*/
}
