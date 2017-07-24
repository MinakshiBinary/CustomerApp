package com.binaryic.customerapp.fashionic.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.CartActivity;
import com.binaryic.customerapp.fashionic.adapters.AddressAdapter;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CustomerController;
import com.binaryic.customerapp.fashionic.models.AddressModel;

import java.util.ArrayList;


/**
 * Created by Asd on 10-10-2016.
 */
public class FragmentAddresses extends Fragment implements AddressAdapter.ClickListener, FragmentAddNewAddress.AddAddressCloseListener, FragmentEditAddress.EditAddressCloseListener {
    RecyclerView rv_Address;
    RelativeLayout rl_AddAddress;
    AddressAdapter adapter;
    ArrayList<AddressModel> list;
    AddressesCloseListener addressesCloseListener;

    public void setAddressesCloseListener(AddressesCloseListener addressesCloseListener) {
        this.addressesCloseListener = addressesCloseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addresses, container, false);

        init(view);

        return view;
    }

    private void init(View view) {

        rv_Address = (RecyclerView) view.findViewById(R.id.rv_Address);
        rl_AddAddress = (RelativeLayout) view.findViewById(R.id.rl_AddAddress);
        rl_AddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAddNewAddress fragmentAddNewAddress = new FragmentAddNewAddress();
                fragmentAddNewAddress.setAddAddressCloseListener(FragmentAddresses.this);
                MyUtils.addFragmentBack(FragmentDelivery.layAddress.getId(), fragmentAddNewAddress, getActivity());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getAddresses();
    }

    private void setUpRecyclerView() {
        Log.e("FragmentAddresses", "RecyclerView list==" + list.size());
        rv_Address.hasFixedSize();
        rv_Address.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new AddressAdapter(getActivity(), list);
        adapter.setClickListener(this);
        rv_Address.setAdapter(adapter);
    }

    @Override
    public void itemClicked(View view, final int position) {

        if (view instanceof AppCompatCheckBox) {
            updateAdd(position);
        } else if (view instanceof LinearLayout) {
            if (view.getId() == R.id.ll_Remove) {
                removeAdd(position);
            } else if (view.getId() == R.id.ll_Edit) {
                FragmentEditAddress fragmentEditAddress = new FragmentEditAddress();
                Bundle bundle = new Bundle();
                bundle.putString("address", list.get(position).getAddress_Id());
                fragmentEditAddress.setArguments(bundle);
                fragmentEditAddress.setEditAddressCloseListener(FragmentAddresses.this);
                MyUtils.addFragmentBack(FragmentDelivery.layAddress.getId(), fragmentEditAddress, getActivity());
            }
        }

    }

    private void getAddresses() {
        Log.e("FragmentAddresses", "getAddresses");
        if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait....!!!!");
            progressDialog.setCancelable(false);
            progressDialog.show();
            CustomerController.getAllAddress(getActivity(), new ApiCallBack() {
                @Override
                public void onSuccess(Object success) {
                    progressDialog.dismiss();

                    list = CustomerController.getAddresses(getActivity());
                    Log.e("FragmentAddresses", "list==" + list.size());
                    if (list != null) {
                        setUpRecyclerView();
                    }
                }

                @Override
                public void onError(String error) {
                    progressDialog.dismiss();
                    list = CustomerController.getAddresses(getActivity());
                    Log.e("FragmentAddresses", "list==" + list.size());
                    if (list != null) {
                        setUpRecyclerView();
                    }
                }
            });
        } else {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "No Internet Connection..!!");
            list = CustomerController.getAddresses(getActivity());
            Log.e("FragmentAddresses", "list==" + list.size());
            if (list != null) {
                setUpRecyclerView();
            }

        }


    }

    private void updateAdd(final int position) {

        if (!list.get(position).isDefaultAddress()) {
            for (AddressModel add : list) {
                add.setDefaultAddress(false);
            }
            final AddressModel address = list.get(position);
            address.setDefaultAddress(true);
            if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Change Address");

                CustomerController.updateAddressStatusApi(getActivity(), address.getAddress_Id(), new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        progressDialog.dismiss();
                       // getAddresses();

                        if (addressesCloseListener != null)
                            addressesCloseListener.addressesClose(true);
                        // getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(String error) {
                        progressDialog.dismiss();
                        MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, error.toString());
                        Log.e("UpdateError", error);


                    }
                });
            } else {
                MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "No Internet Connection..!!");

            }

        }

    }

    private void removeAdd(final int position) {

        if (!list.get(position).isDefaultAddress()) {
            if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
                CustomerController.deleteAddressApi(getActivity(), list.get(position).getAddress_Id(), new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        list.remove(position);
                        getAddresses();
                    /*adapter.list = list;
                    adapter.notifyDataSetChanged();
                    if (addressesCloseListener != null)
                        addressesCloseListener.addressesClose(false);*/
                    }

                    @Override
                    public void onError(String error) {
                        MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart,error.toString());
                        Log.e("RemoveError", error);
                    }


                });
            } else {
                MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "No Internet Connection..!!");

            }

        }

    }

    @Override
    public void addAddressClose() {
        getActivity().onBackPressed();
        getAddresses();
        if (addressesCloseListener != null)
            addressesCloseListener.addressesClose(false);
    }

    @Override
    public void editAddressClose() {
        getActivity().onBackPressed();
        getAddresses();
        if (addressesCloseListener != null)
            addressesCloseListener.addressesClose(false);
    }


    public interface AddressesCloseListener {
        public void addressesClose(boolean isClose);
    }

}
