package com.binaryic.customerapp.fashionic.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.CartActivity;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CustomerController;
import com.binaryic.customerapp.fashionic.models.AddressModel;


/**
 * Created by Asd on 14-10-2016.
 */
public class FragmentEditAddress extends Fragment implements View.OnClickListener {
    EditText et_FirstName, et_LastName, et_Address, et_Pincode, et_MobileNumber, et_City, et_Landmark, et_Area;
    CardView cd_Save;
    AutoCompleteTextView et_State;
    AddressModel AddressPrev;
    EditAddressCloseListener editAddressCloseListener;

    public void setEditAddressCloseListener(EditAddressCloseListener editAddressCloseListener) {
        this.editAddressCloseListener = editAddressCloseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_address, container, false);

        getExtra();
        initView(view);

        return view;
    }

    private void getExtra() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            AddressPrev = CustomerController.getSingleAddresses(getActivity(), bundle.getString("address"));
        }

    }

    private void initView(View view) {

        et_FirstName = (EditText) view.findViewById(R.id.et_FirstName);
        et_LastName = (EditText) view.findViewById(R.id.et_LastName);
        et_Address = (EditText) view.findViewById(R.id.et_Address);
        et_Landmark = (EditText) view.findViewById(R.id.et_Landmark);
        et_Area = (EditText) view.findViewById(R.id.et_Area);
        et_Pincode = (EditText) view.findViewById(R.id.et_Pincode);
        et_MobileNumber = (EditText) view.findViewById(R.id.et_MobileNumber);
        et_MobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        et_Pincode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        et_City = (EditText) view.findViewById(R.id.et_City);
        et_State = (AutoCompleteTextView) view.findViewById(R.id.spinner_State);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.state, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_State.setAdapter(adapter);


        cd_Save = (CardView) view.findViewById(R.id.cd_Save);
        cd_Save.setOnClickListener(this);
        et_Address.setText(AddressPrev.getCustomer_Address());
        et_City.setText(AddressPrev.getCity());
        et_Landmark.setText(AddressPrev.getLandmark());
        et_Area.setText(AddressPrev.getArea());
        et_FirstName.setText(AddressPrev.getFirstName());
        et_LastName.setText(AddressPrev.getLastName());
        et_MobileNumber.setText(AddressPrev.getMobileNumber());
        et_Pincode.setText(AddressPrev.getPincode());
        et_State.setText(AddressPrev.getState());

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cd_Save) {
            MyUtils.hideKeyboard(getActivity());
            validation(et_FirstName.getText().toString().trim(), et_LastName.getText().toString().trim(), et_Address.getText().toString().trim(), et_Pincode.getText().toString().trim(), et_MobileNumber.getText().toString().trim(), et_Landmark.getText().toString().trim(), et_Area.getText().toString().trim(), et_City.getText().toString().trim(), et_State.getText().toString().trim());
        }
    }

    private void validation(String firstName, String lastName, String address, String pincode, String mobileNumber, String landmark, String area, String city, String state) {
        if (firstName.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter Proper Mobile Number");
        } else if (lastName.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter Last Name");
        } else if (address.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter Address1");
        } else if (pincode.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter Pincode");
        } else if (pincode.length() != 6) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter Vaild Pincode");
        } else if (mobileNumber.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter Mobile");
        } else if (mobileNumber.length() != 10) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter Valid Mobile");
        } else if (landmark.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter LandMark");
        } else if (area.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter Area");
        } else if (city.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter City");
        } else if (state.matches("State")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Enter State");
        } else {

            AddressModel addressModel = new AddressModel();
            addressModel.setFirstName(firstName);
            addressModel.setLastName(lastName);
            addressModel.setCustomer_Address(address);
            addressModel.setPincode(pincode);
            addressModel.setMobileNumber(mobileNumber);
            addressModel.setLandmark(landmark);
            addressModel.setArea(area);
            addressModel.setCity(city);
            addressModel.setState(state);
            addressModel.setAddress_Id(AddressPrev.getAddress_Id());

            if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait....!!!!");
                progressDialog.setCancelable(false);
                progressDialog.show();
                CustomerController.editAddressApi(getActivity(), addressModel, new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Address registeration successful!!!");
                        progressDialog.dismiss();
                        if (editAddressCloseListener != null)
                            editAddressCloseListener.editAddressClose();
                    }

                    @Override
                    public void onError(String error) {
                        progressDialog.dismiss();
                        MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, error.toString());
                        Log.e("AddAddressFragment", "error==" + error);
                        if (editAddressCloseListener != null)
                            editAddressCloseListener.editAddressClose();
                    }
                });
            } else {
                MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "No Internet Connection..!!");
            }
        }

    }

    public interface EditAddressCloseListener {
        public void editAddressClose();
    }
}
