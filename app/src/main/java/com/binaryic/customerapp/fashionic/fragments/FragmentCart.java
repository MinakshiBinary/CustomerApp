package com.binaryic.customerapp.fashionic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.CartActivity;
import com.binaryic.customerapp.fashionic.activities.LoginActivity;
import com.binaryic.customerapp.fashionic.adapters.CartProductAdapter;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CartController;
import com.binaryic.customerapp.fashionic.controller.CouponController;
import com.binaryic.customerapp.fashionic.controller.LoginController;
import com.binaryic.customerapp.fashionic.custom.TextViewPrimary;
import com.binaryic.customerapp.fashionic.models.ProductModelQty;

import java.util.ArrayList;


/**
 * Created by Asd on 16-09-2016.
 */
public class FragmentCart extends Fragment implements View.OnClickListener, CartProductAdapter.ClickListener {
    RecyclerView rv_Products;
    TextView tv_ShippingCharges, tv_TotalPrice, tv_SubTotal;
    TextView tv_DiscountCharges;
    LinearLayout ll_PlaceOrder;
    Boolean isClickOn = true;
    ArrayList<ProductModelQty> list;
    View infoView;
    LinearLayout ll_Retry;
    RelativeLayout rl_MainCart;
    RelativeLayout rl_CouponDiscount;

    Double Amount = 0.0;
    int Shipping = 0;
    int couponCharges = 0;
    CartProductAdapter adapter;
    Boolean isChange = false;
    private TextViewPrimary tv_Coupan_Apply;
    private EditText et_Coupan_Code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        init(view);
        setData();

        return view;
    }

    private void init(View view) {

        infoView = view.findViewById(R.id.info_View);//if cart is empty than show
        ll_Retry = (LinearLayout) infoView.findViewById(R.id.ll_Retry);

        rl_CouponDiscount = (RelativeLayout) view.findViewById(R.id.rl_CouponDiscount);
        rl_MainCart = (RelativeLayout) view.findViewById(R.id.rl_MainCart);
        rv_Products = (RecyclerView) view.findViewById(R.id.rv_Products);
        tv_ShippingCharges = (TextView) view.findViewById(R.id.tv_ShippingCharges);
        et_Coupan_Code = (EditText) view.findViewById(R.id.et_Coupan_Code);
        tv_Coupan_Apply = (TextViewPrimary) view.findViewById(R.id.tv_Coupan_Apply);
        tv_TotalPrice = (TextView) view.findViewById(R.id.tv_TotalPrice);
        tv_SubTotal = (TextView) view.findViewById(R.id.tv_SubTotal);
        tv_DiscountCharges = (TextView) view.findViewById(R.id.tv_DiscountCharges);
        ll_PlaceOrder = (LinearLayout) view.findViewById(R.id.ll_PlaceOrder);
        ll_PlaceOrder.setOnClickListener(this);
        ll_Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();

            }
        });


        tv_Coupan_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtils.hideKeyboard(getActivity());

                validation(et_Coupan_Code.getText().toString().trim());
            }
        });
    }

    private void validation(String coupon) {
        if (TextUtils.isEmpty(coupon)) {
            MyUtils.showMessageBox(" Please enter coupan code", "OK", "", false, getActivity());
        } else {
            if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
                CouponController.applyCouponCall(getActivity(), coupon, new ApiCallBack() {
                    @Override
                    public void onSuccess(Object success) {
                        MyUtils.showMessageBox(" Amount Will Be Deducted From Product Price", "OK", "", false, getActivity());
                        couponCharges = (int) success;
                        setData();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("FragmentCart", "error==" + error);
                        MyUtils.showMessageBox(error.toString(), "OK", "", false, getActivity());

                    }
                });
            } else {
                MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "No Internet Connection..!!");
            }
        }
    }

    private void setData() {

        if (checkCart()) {
            infoView.setVisibility(View.VISIBLE);
            rl_MainCart.setVisibility(View.GONE);
            TextView tvMessage = (TextView) infoView.findViewById(R.id.tvMessage);
            tvMessage.setText("Add to Cart \nAnd it would appear here.");

        } else {
            infoView.setVisibility(View.GONE);
            rl_MainCart.setVisibility(View.VISIBLE);
            tv_SubTotal.setText(Amount.doubleValue() + "");


            if (couponCharges == 0) {
                tv_TotalPrice.setText((Amount.doubleValue() + Shipping) + "");
                rl_CouponDiscount.setVisibility(View.GONE);
            } else {
                rl_CouponDiscount.setVisibility(View.VISIBLE);
                tv_TotalPrice.setText((Amount.doubleValue() + Shipping - ((Amount.doubleValue() * couponCharges) / 100)) + "");
                tv_DiscountCharges.setText(((Amount.doubleValue() * couponCharges) / 100) + "");
            }

            if (Shipping == 0)
                tv_ShippingCharges.setText("Free");
            else
                tv_ShippingCharges.setText(Shipping + "");


            setUpRecyclerView();
        }

    }

    private void setUpRecyclerView() {
        if (isChange) {
            adapter.list = list;
            adapter.notifyDataSetChanged();
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_Products.setLayoutManager(layoutManager);
            adapter = new CartProductAdapter(getActivity(), list);
            adapter.setClickListener(this);
            rv_Products.setAdapter(adapter);
        }
    }

    private boolean checkCart() {
        boolean isEmpty = false;

        Amount = 0.0;
        list = new ArrayList<>();
        list = CartController.getCartProducts(getActivity());
        if (list.size() > 0) {
            isEmpty = false;
            for (ProductModelQty productModelQty : list) {
                Amount = Amount + (Double.parseDouble(productModelQty.getProductModel().getSelling_Price()) * productModelQty.getQty());
            }
        } else
            isEmpty = true;


        return isEmpty;
    }

    @Override
    public void onClick(View v) {

        if (isClickOn) {
            if (v.getId() == R.id.ll_PlaceOrder) {

                if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
                    if (LoginController.isAllreadyLogin(getActivity())) {

                        if (couponCharges != 0) {
                            ((CartActivity) getActivity()).addDeliveryFragment(String.valueOf(Amount.doubleValue() + Shipping - ((Amount.doubleValue() * couponCharges) / 100)));
                        } else {
                            ((CartActivity) getActivity()).addDeliveryFragment(String.valueOf(Amount));
                        }
                    } else {
                        MyUtils.showMessageBox("Do You Want To Sign In", "Yes", "NO", true, getActivity());
                        MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                                MyUtils.msgDialog.dismiss();
                            }
                        });
                        MyUtils.btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyUtils.msgDialog.dismiss();
                            }
                        });

                    }
                }
            }

        }
    }

    @Override
    public void itemClicked(View view, final int position, int Qty) {

        if (view.getId() == R.id.ll_Remove) {
            MyUtils.showMessageBox("Remove item from cart?", "Remove", "NO", true, getActivity());
            MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartController.deleteProduct(getActivity(), list.get(position));
                    isChange = true;
                    setData();
                    MyUtils.msgDialog.dismiss();
                }
            });
            MyUtils.btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtils.msgDialog.dismiss();
                }
            });
        } else if (Qty != 0) {
            CartController.changeQty(getActivity(), list.get(position), Qty);
            isChange = true;
            setData();
        }

    }
}
