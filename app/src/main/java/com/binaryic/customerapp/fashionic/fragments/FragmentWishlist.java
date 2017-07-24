package com.binaryic.customerapp.fashionic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.CartActivity;
import com.binaryic.customerapp.fashionic.activities.MainActivity;
import com.binaryic.customerapp.fashionic.activities.ProductListActivity;
import com.binaryic.customerapp.fashionic.activities.ProductsDetailActivity;
import com.binaryic.customerapp.fashionic.adapters.CatalogueAdapter;
import com.binaryic.customerapp.fashionic.adapters.ProductAdapter;
import com.binaryic.customerapp.fashionic.controller.CartController;
import com.binaryic.customerapp.fashionic.controller.WishListController;
import com.binaryic.customerapp.fashionic.models.ProductModel;

import java.util.ArrayList;

/**
 * Created by User on 08-09-2016.
 */
public class FragmentWishlist extends Fragment implements ProductAdapter.ClickListener, CatalogueAdapter.ClickListener {
    RecyclerView recycler;
    View layMessage;
    ArrayList<ProductModel> list;
    CatalogueAdapter adapter;
    TextView tvMessage, tvBtnMessage;
    LinearLayout ll_Retry;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        list = new ArrayList<ProductModel>();
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        layMessage = view.findViewById(R.id.layMessage);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        tvBtnMessage = (TextView) view.findViewById(R.id.tv_Message);
        ll_Retry = (LinearLayout) view.findViewById(R.id.ll_Retry);
        layMessage.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);

        ll_Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).onBackPressed();
                } else if (getActivity() instanceof ProductListActivity) {
                    ((ProductListActivity) getActivity()).onBackPressed();
                } else if (getActivity() instanceof ProductsDetailActivity) {
                    ((ProductsDetailActivity) getActivity()).onBackPressed();
                }
            }
        });

        return view;
    }


    private void SetUpRecyclerView() {

        if (list.size() > 0) {
            layMessage.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            adapter = new CatalogueAdapter(getActivity(), list);
            adapter.setClickListener(this);
            recycler.setAdapter(adapter);
        } else {
            layMessage.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            tvMessage.setText("Make a wish \nAnd it would appear here.");
            tvBtnMessage.setText("Browse collection");
        }

    }

    private void GetList() {
        list = WishListController.getFavProducts(getActivity());
        SetUpRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        GetList();
        SetUpRecyclerView();
    }

    @Override
    public void itemClicked(View view, int position) {
        try {
            if (view.getId() == R.id.iv_Favorite) {
                if (list.get(position).isFav()) {
                    WishListController.deleteProduct(getActivity(), list.get(position));
                    list.get(position).setFav(false);
                    list.remove(position);
                }
                adapter.array_Data = list;
                adapter.notifyDataSetChanged();

                if (list.size() > 0) {
                    layMessage.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                } else {
                    layMessage.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }

                int wish_Count = WishListController.getWishCount(getActivity());
                //   Log.e("wish_Count","=="+wish_Count);

                if (getActivity() instanceof ProductListActivity) {
                    if (wish_Count == 0) {
                        ((ProductListActivity) getActivity()).tv_Wish_Count.setVisibility(View.GONE);
                    } else {
                        ((ProductListActivity) getActivity()).tv_Wish_Count.setText(wish_Count + "");
                        ((ProductListActivity) getActivity()).tv_Wish_Count.setVisibility(View.VISIBLE);
                        Log.e("tv_Wish_Count", "===else" + wish_Count);
                    }
                } else if (getActivity() instanceof MainActivity) {
                    if (wish_Count == 0) {
                        MainActivity.tv_Wish_Count.setVisibility(View.GONE);
                    } else {
                        MainActivity.tv_Wish_Count.setText(wish_Count + "");
                        MainActivity.tv_Wish_Count.setVisibility(View.VISIBLE);
                        Log.e("tv_Wish_Count", "===else" + wish_Count);
                    }
                }
            } else if (view.getId() == R.id.ll_Buy) {

                CartController.addProduct(getActivity(), list.get(position), "1");
                int Count = CartController.getCartCount(getActivity());

                if (Count == 0) {
                    MainActivity.tvCount.setVisibility(View.GONE);
                } else {
                    MainActivity.tvCount.setText(Count + "");
                    MainActivity.tvCount.setVisibility(View.VISIBLE);
                    Log.e("cart_Count", "===else" + Count);
                }
                startActivity(new Intent(getActivity(), CartActivity.class));


            } else {


                Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
                intent.putExtra("productId", list.get(position).getProduct_ID());
                startActivity(intent);
            }
        } catch (Exception ex) {
        }
    }
    public void ResetList(){
        try{
            list = WishListController.getFavProducts(getActivity());
            adapter.array_Data = list;
            adapter.notifyDataSetChanged();
        }catch (Exception ex){}
    }


}
