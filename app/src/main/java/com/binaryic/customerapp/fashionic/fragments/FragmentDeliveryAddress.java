package com.binaryic.customerapp.fashionic.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.CartActivity;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CustomerController;
import com.binaryic.customerapp.fashionic.models.AddressModel;

import java.util.ArrayList;

/**
 * Created by Asd on 08-10-2016.
 */
public class FragmentDeliveryAddress extends Fragment implements View.OnClickListener, FragmentAddNewAddress.AddAddressCloseListener, FragmentAddresses.AddressesCloseListener {
    TextView tv_Name, tv_Address, tv_Pincode, tv_ContactNo, tv_TotalPrice;
    RelativeLayout rl_btnAddAddress;
    LinearLayout ll_btnProceed;
    View layout_DeliveryAddress;
    String Amount = "0.0";
    ArrayList<AddressModel> addressModels = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_address, container, false);

        getExtra();
        inIt(view);
        setUI();

        return view;
    }

    private void getExtra() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Amount = bundle.getString("Amount");
        }

    }

    private void inIt(View view) {

        layout_DeliveryAddress = (View) view.findViewById(R.id.layout_DeliveryAddress);
        tv_Name = (TextView) view.findViewById(R.id.tv_Name);
        tv_Address = (TextView) view.findViewById(R.id.tv_Address);
        tv_Pincode = (TextView) view.findViewById(R.id.tv_Pincode);
        tv_ContactNo = (TextView) view.findViewById(R.id.tv_ContactNo);
        tv_TotalPrice = (TextView) view.findViewById(R.id.tv_TotalPrice);
        tv_TotalPrice.setText(Amount);
        rl_btnAddAddress = (RelativeLayout) view.findViewById(R.id.rl_AddAddress);
        ll_btnProceed = (LinearLayout) view.findViewById(R.id.ll_BtnProceed);
        ll_btnProceed.setOnClickListener(this);
        rl_btnAddAddress.setOnClickListener(this);

    }

    private void setUI() {
        if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait....!!!!");
            progressDialog.setCancelable(false);
            progressDialog.show();
            CustomerController.getAllAddress(getActivity(), new ApiCallBack() {
                @Override
                public void onSuccess(Object success) {
                    progressDialog.dismiss();

                    addressModels = new ArrayList<AddressModel>();
                    addressModels = new CustomerController().getDefaultAddress(getActivity());

                    if (addressModels != null) {
                        if (addressModels.size() > 0) {
                            layout_DeliveryAddress.setVisibility(View.VISIBLE);
                            getDefaultAddress();
                        } else {
                            layout_DeliveryAddress.setVisibility(View.GONE);
                            addCreateAddressFragment();
                        }
                    } else {
                        layout_DeliveryAddress.setVisibility(View.GONE);
                        addCreateAddressFragment();
                    }
                }

                @Override
                public void onError(String error) {
                    progressDialog.dismiss();
                    addressModels = new ArrayList<AddressModel>();
                    addressModels = new CustomerController().getDefaultAddress(getActivity());

                    if (addressModels != null) {
                        if (addressModels.size() > 0) {
                            layout_DeliveryAddress.setVisibility(View.VISIBLE);
                            getDefaultAddress();
                        } else {
                            layout_DeliveryAddress.setVisibility(View.GONE);
                            addCreateAddressFragment();
                        }
                    } else {
                        layout_DeliveryAddress.setVisibility(View.GONE);
                        addCreateAddressFragment();
                    }
                }
            });
        } else {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "No Internet Connection..!!");
            addressModels = new ArrayList<AddressModel>();
            addressModels = new CustomerController().getDefaultAddress(getActivity());

            if (addressModels != null) {
                if (addressModels.size() > 0) {
                    layout_DeliveryAddress.setVisibility(View.VISIBLE);
                    getDefaultAddress();
                } else {
                    layout_DeliveryAddress.setVisibility(View.GONE);
                    addCreateAddressFragment();
                }
            } else {
                layout_DeliveryAddress.setVisibility(View.GONE);
                addCreateAddressFragment();
            }
        }


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rl_AddAddress) {
            FragmentAddresses fragmentAddresses = new FragmentAddresses();
            fragmentAddresses.setAddressesCloseListener(FragmentDeliveryAddress.this);
            MyUtils.addFragmentBack(FragmentDelivery.layAddress.getId(), fragmentAddresses, getActivity());
        } else if (v.getId() == R.id.ll_BtnProceed) {
            ((CartActivity) getActivity()).addPaymentFragment(Amount);
        }

    }

    private void addCreateAddressFragment() {

        FragmentAddNewAddress fragmentAddNewAddress = new FragmentAddNewAddress();
        fragmentAddNewAddress.setAddAddressCloseListener(this);
        MyUtils.addFragmentBack(FragmentDelivery.layAddress.getId(), fragmentAddNewAddress, getActivity());

    }

    public void getDefaultAddress() {
        Log.e("DeliveryAddress", "getDefaultAddress");
        addressModels = new CustomerController().getDefaultAddress(getActivity());
        tv_Name.setText(addressModels.get(0).getFirstName() + " " + addressModels.get(0).getLastName());
        tv_Address.setText(addressModels.get(0).getCustomer_Address() + " " + addressModels.get(0).getCity() + " " + addressModels.get(0).getState() + " " + addressModels.get(0).getContry());
        tv_Pincode.setText(addressModels.get(0).getPincode());
        tv_ContactNo.setText(addressModels.get(0).getMobileNumber());

    }

    @Override
    public void addAddressClose() {
        Log.e("DeliveryAddress", "addAddressClose");
        getActivity().onBackPressed();
        getDefaultAddress();
    }


    @Override
    public void addressesClose(boolean isClose) {
        Log.e("DeliveryAddress", "addressesClose");

        if (isClose)
            getActivity().onBackPressed();
        setUI();
    }

    @Override
    public void onResume() {
        Log.e("DeliveryAddress", "onResume");
        super.onResume();
        setUI();
    }
}
