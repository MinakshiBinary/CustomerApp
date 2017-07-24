package com.binaryic.customerapp.fashionic.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.TextUtils;
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
 * Created by MY PC on 07-Oct-16.
 */
public class FragmentAddNewAddress extends Fragment implements View.OnClickListener {

    EditText et_FirstName, et_LastName, et_Address, et_Pincode, et_MobileNumber, et_City, et_Landmark, et_Area;
    CardView cd_Save;
    AutoCompleteTextView spinner_State;
    AddAddressCloseListener addAddressCloseListener;

    public void setAddAddressCloseListener(AddAddressCloseListener addAddressCloseListener) {
        this.addAddressCloseListener = addAddressCloseListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_address, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        et_Landmark = (EditText) view.findViewById(R.id.et_Landmark);
        et_Area = (EditText) view.findViewById(R.id.et_Area);
        et_FirstName = (EditText) view.findViewById(R.id.et_FirstName);
        et_LastName = (EditText) view.findViewById(R.id.et_LastName);
        et_Address = (EditText) view.findViewById(R.id.et_Address);
        et_Pincode = (EditText) view.findViewById(R.id.et_Pincode);
        et_MobileNumber = (EditText) view.findViewById(R.id.et_MobileNumber);
        et_MobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        et_Pincode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        et_City = (EditText) view.findViewById(R.id.et_City);
        spinner_State = (AutoCompleteTextView) view.findViewById(R.id.spinner_State);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.state, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_State.setAdapter(adapter);


        cd_Save = (CardView) view.findViewById(R.id.cd_Save);
        cd_Save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cd_Save) {
            MyUtils.hideKeyboard(getActivity());
            validation(et_FirstName.getText().toString().trim(), et_LastName.getText().toString().trim(), et_Address.getText().toString().trim(), et_Pincode.getText().toString().trim(), et_MobileNumber.getText().toString().trim(), et_Landmark.getText().toString().trim(), et_Area.getText().toString().trim(), et_City.getText().toString().trim(), spinner_State.getText().toString().trim());
        }
    }

    private void validation(String firstName, String lastName, String address, String pincode, String mobileNumber, String landmark, String area, String city, String state) {
        if (firstName.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter First Name");
        } else if (lastName.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter Last Name");
        } else if (address.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter Address");
        } else if (pincode.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter Pincode");
        } else if (pincode.length() != 6) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter Vaild Pincode");
        } else if (mobileNumber.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter Mobile Number");
        } else if (mobileNumber.length() != 10) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter Valid Mobile Number");
        } else if (landmark.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter LandMark");
        } else if (area.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter Area");
        } else if (city.equals("")) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Enter City");
        } else if (state.matches("State") || TextUtils.isEmpty(state)) {
            MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Please Select State");
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


            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait....!!!!");
            progressDialog.setCancelable(false);
            progressDialog.show();
            if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {

                CustomerController.addAddressApi(getActivity(), addressModel, new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "Address registeration successful!!!");
                        progressDialog.dismiss();
                        if (addAddressCloseListener != null)
                            addAddressCloseListener.addAddressClose();
                    }

                    @Override
                    public void onError(String error) {
                        progressDialog.dismiss();
                        Log.e("AddAddressFragment", "error==" + error);

                        MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, error.toString());

                        if (addAddressCloseListener != null)
                            addAddressCloseListener.addAddressClose();
                    }
                });
            } else {
                MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "No Internet Connection..!!");
            }
        }

    }

    public interface AddAddressCloseListener {
        public void addAddressClose();
    }

}
