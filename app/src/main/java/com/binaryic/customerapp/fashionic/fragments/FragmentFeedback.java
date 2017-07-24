package com.binaryic.customerapp.fashionic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.custom.CirclePageIndicator;
import com.binaryic.customerapp.fashionic.models.FeedbackModel;

import java.util.ArrayList;


/**
 * Created by Asd on 27-09-2016.
 */
public class FragmentFeedback extends Fragment {
    ArrayList<FeedbackModel> list;
    ViewPager mPager;
    CirclePageIndicator mIndicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

            view = inflater.inflate(R.layout.fragment_feedback, container, false);
            mPager = (ViewPager) view.findViewById(R.id.pager);
            mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
          //  SetList();

        return view;
    }

   /* private void SetList() {
        list = new HomeController().GetFeedBackList(getActivity());
        FeedbackAdapter mAdapter = new FeedbackAdapter(getActivity(),list);
        mPager.setAdapter(mAdapter);
        if(list.size() > 1){
            mIndicator.setViewPager(mPager);
        }
    }*/
}
