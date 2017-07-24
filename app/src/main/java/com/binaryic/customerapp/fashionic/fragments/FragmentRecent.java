package com.binaryic.customerapp.fashionic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.adapters.RecentAdapter;
import com.binaryic.customerapp.fashionic.models.ProductModel;

import java.util.ArrayList;

/**
 * Created by Asd on 04-10-2016.
 */
public class FragmentRecent extends Fragment implements RecentAdapter.ClickListener {
    RecyclerView recycler;
    ArrayList<ProductModel> list;
    CardView layRecentMain;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);

            recycler = (RecyclerView) view.findViewById(R.id.recent_product_list);
            layRecentMain = (CardView) view.findViewById(R.id.layRecentMain);
            GetRecentList();


        return view;
    }
    private void GetRecentList(){

            list = new ArrayList<>();
           // list = RecentController.getRecentProducts(getActivity());
            if(list.size() > 0){
                layRecentMain.setVisibility(View.VISIBLE);
                SetUpRecyclerView();
            }else{
                layRecentMain.setVisibility(View.GONE);
            }

    }
    private void SetUpRecyclerView() {

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recycler.setLayoutManager(layoutManager);
            RecentAdapter adapter = new RecentAdapter(getActivity(),list);
            adapter.setClickListener(this);
            recycler.setAdapter(adapter);

    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
