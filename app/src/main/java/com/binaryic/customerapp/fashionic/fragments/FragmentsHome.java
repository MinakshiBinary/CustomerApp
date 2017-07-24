package com.binaryic.customerapp.fashionic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.adapters.HomeBannerAdapter;
import com.binaryic.customerapp.fashionic.models.HomeBannerModel;

import java.util.ArrayList;

/**
 * Created by Binary_Apple on 3/23/17.
 */

public class FragmentsHome extends Fragment implements HomeBannerAdapter.ClickListener {
    RecyclerView recycler;
    ArrayList<HomeBannerModel> list;
    ScrollView NstScroll;
    LinearLayout layHomeMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        list = new ArrayList<HomeBannerModel>();
        NstScroll = (ScrollView) view.findViewById(R.id.NstScroll);
        layHomeMain = (LinearLayout) view.findViewById(R.id.layHomeMain);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);

        HomeBannerModel homeBannerModel = new HomeBannerModel();
        homeBannerModel.setId("1");
        homeBannerModel.setImage("http://sellerapp.binaryicdirect.com/public/uploads/products/5_1_1_image");
        homeBannerModel.setType("banner");

        HomeBannerModel homeBannerModel1 = new HomeBannerModel();
        homeBannerModel1.setId("1");
        homeBannerModel1.setImage("http://sellerapp.binaryicdirect.com/public/uploads/products/5_1_1_image");
        homeBannerModel1.setType("product");
        homeBannerModel1.setText("Featured Collection");

        HomeBannerModel homeBannerModel2 = new HomeBannerModel();
        homeBannerModel2.setId("2");
        homeBannerModel2.setImage("http://sellerapp.binaryicdirect.com/public/uploads/products/5_1_1_image");
        homeBannerModel2.setText("New Collection");
        homeBannerModel2.setType("product");

        ArrayList<String> imageArr = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            imageArr.add("http://sellerapp.binaryicdirect.com/public/uploads/products/5_1_1_image");
        }
        list.add(homeBannerModel);
        list.add(homeBannerModel1);
        list.add(homeBannerModel2);
        list.add(homeBannerModel);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        HomeBannerAdapter adapter = new HomeBannerAdapter(getActivity(), list);
        adapter.setClickListener(this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void itemClicked(View view, int position) {

    }
}