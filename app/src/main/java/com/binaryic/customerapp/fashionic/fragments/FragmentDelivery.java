package com.binaryic.customerapp.fashionic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyUtils;


/**
 * Created by Asd on 16-09-2016.
 */
public class FragmentDelivery extends Fragment implements View.OnClickListener {
    public static FrameLayout layAddress;
    Boolean isClickOn = true;
    String Amount = "0.0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deliverydetails, container, false);

        getExtra();
        init(view);

        return view;
    }

    private void init(View view) {

        layAddress = (FrameLayout) view.findViewById(R.id.layAddress);
        setUI();

    }

    private void getExtra() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Amount = bundle.getString("Amount");
        }

    }

    private void setUI() {
        try {
            FragmentDeliveryAddress fragmentDeliveryAddress = new FragmentDeliveryAddress();
            Bundle bundle = new Bundle();
            bundle.putString("Amount", Amount);
            fragmentDeliveryAddress.setArguments(bundle);
            MyUtils.addFragment(layAddress.getId(), fragmentDeliveryAddress, getActivity());
        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (isClickOn) {

            }
        } catch (Exception ex) {
        }
    }


}
