package com.binaryic.customerapp.fashionic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.ProductsDetailActivity;
import com.binaryic.customerapp.fashionic.adapters.CollectionProductAdapter;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.controller.ProductController;
import com.binaryic.customerapp.fashionic.models.ProductModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

;

/**
 * Created by HP on 04-Apr-17.
 */

public class FragmentHomeCollection extends Fragment implements CollectionProductAdapter.ClickListener {
    TextView tvCollection;
    TextView tv_Category_No_Available;
    String json = "";
    String title = "";
    String category_id = "";
    ArrayList<ProductModel> productModels;
    RecyclerView recycler;
    CollectionProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_collection, container, false);
        inIt(view);
        getExtra();
        getData();
        return view;
    }

    private void getExtra() {
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                json = bundle.getString("json");
            }
        } catch (Exception ex) {
            Log.e("FragmentHomeCollection", "getExtra error==" + ex.getMessage());

        }
    }

    private void inIt(View view) {
        tvCollection = (TextView) view.findViewById(R.id.tvCollection);
        tv_Category_No_Available = (TextView) view.findViewById(R.id.tv_Category_No_Available);
        final CardView btnViewAll = (CardView) view.findViewById(R.id.btnViewAll);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);

       /* btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("collection", title);
                intent.putExtra("collection_id", category_id);
                startActivity(intent);
            }
        });*/
    }

    private void getData() {
        try {
            productModels = new ArrayList<>();
            JSONObject object = new JSONObject(json);
            title = object.getString("collection");
            tvCollection.setText("----  " + title + "  ----");

            category_id = object.getString("category_id");
            JSONArray result = object.getJSONArray("result");
            JSONArray products = result.getJSONObject(0).getJSONArray("products");
            productModels = ProductController.getListFromArrayProduct(products, "10");
            SetUpRecyclerView();
        } catch (Exception ex) {
            Log.e("FragmentHomeCollection", "error==" + ex.getMessage());
        }
    }

    private void SetUpRecyclerView() {
        try {

            if (productModels.size() > 0) {
                tv_Category_No_Available.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                // GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                // gridLayoutManager.setAutoMeasureEnabled(true);
                recycler.setLayoutManager(layoutManager);
                adapter = new CollectionProductAdapter(getActivity(), productModels);
                adapter.setClickListener(this);
                recycler.setAdapter(adapter);
            } else {
                tv_Category_No_Available.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Log.e("FragmentHomeCollection", " SetUpRecyclerView error==" + ex.getMessage());
        }
    }


    @Override
    public void ItemClicked(View view, int position) {
        MyApplication.productModel = productModels.get(position);
        Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
        intent.putExtra("productId", productModels.get(position).getProduct_ID());
        getActivity().startActivity(intent);
    }
}
