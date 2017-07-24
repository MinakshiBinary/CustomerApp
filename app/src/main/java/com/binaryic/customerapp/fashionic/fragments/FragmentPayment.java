package com.binaryic.customerapp.fashionic.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.CartActivity;
import com.binaryic.customerapp.fashionic.activities.PaymentActivity;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CartController;
import com.binaryic.customerapp.fashionic.controller.CustomerController;
import com.binaryic.customerapp.fashionic.controller.OrderController;
import com.binaryic.customerapp.fashionic.models.AddressModel;
import com.binaryic.customerapp.fashionic.models.CustomerModel;
import com.binaryic.customerapp.fashionic.models.ProductModelQty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Asd on 16-09-2016.
 */
public class FragmentPayment extends Fragment implements View.OnClickListener {
    Boolean isClickOn = true;
    float Amount = 0;
    int shipping = 0;
    float vat = 0;
    float Discount = 0;
    float SubTotal = 0;
    float Total = 0;
    public static String payment_Success = "";
    public static String transaction_ID = "";
    String transaction_type = "COD";
    ArrayList<ProductModelQty> productModelQties = null;
    RelativeLayout rl_COD, rl_Online;
    TextView tv_Price, tv_SubTotal, tv_ShippingCharges, tv_VatTotal, tvDiscount, tvProductAmount;
    RadioButton rb_OptionCOD, rb_OptionCard;
    EditText et_Note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        getExtra();
        init(view);

        return view;
    }

    private void init(View view) {

        LinearLayout btnPlaceOrder = (LinearLayout) view.findViewById(R.id.ll_PlaceOrder);
        rl_COD = (RelativeLayout) view.findViewById(R.id.rl_COD);
        rl_Online = (RelativeLayout) view.findViewById(R.id.rl_Online);
        tvProductAmount = (TextView) view.findViewById(R.id.tvProductAmount);

        tv_Price = (TextView) view.findViewById(R.id.tv_Price);
        tv_SubTotal = (TextView) view.findViewById(R.id.tv_SubTotal);
        tv_VatTotal = (TextView) view.findViewById(R.id.tv_VatTotal);
        tvDiscount = (TextView) view.findViewById(R.id.tvDiscount);
        tv_ShippingCharges = (TextView) view.findViewById(R.id.tv_ShippingCharges);
        rb_OptionCOD = (RadioButton) view.findViewById(R.id.rb_OptionCOD);
        rb_OptionCard = (RadioButton) view.findViewById(R.id.rb_OptionCard);
        et_Note = (EditText) view.findViewById(R.id.et_Note);
        btnPlaceOrder.setOnClickListener(this);
        rl_COD.setOnClickListener(this);
        rl_Online.setOnClickListener(this);
        setUI();

    }

    private void setUI() {
       /* if (shipping == 0)
            tv_ShippingCharges.setText("FREE");
        else
            tv_ShippingCharges.setText(shipping);
        tv_SubTotal.setText(Amount+"");
        Double vat = ((Double.valueOf(Amount) * 1.2) / 100);
        tv_VatTotal.setText(vat.intValue() + "");
        tv_Price.setText((Double.valueOf(Amount).intValue() + shipping + vat.intValue()) + "");
*/


        if (shipping == 0)
            tv_ShippingCharges.setText("FREE");
        else
            tv_ShippingCharges.setText(shipping);

        tvProductAmount.setText(new DecimalFormat("##.##").format(Amount));
        tvDiscount.setText(new DecimalFormat("##.##").format(Discount));
        SubTotal = Amount + shipping - Discount;
        tv_SubTotal.setText(new DecimalFormat("##.##").format(SubTotal));
        vat = (float) (((SubTotal) * 1.2) / 100);
        tv_VatTotal.setText(new DecimalFormat("##.##").format(vat));
        Total = SubTotal + vat;
        tv_Price.setText(new DecimalFormat("##.##").format(Total));
    }

    private void getExtra() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Amount = Float.parseFloat(bundle.getString("Amount"));
        }

    }

    @Override
    public void onClick(View v) {
        try {
            if (isClickOn) {
                if (v.getId() == R.id.ll_PlaceOrder) {

                    if (rb_OptionCOD.isChecked()) {
                        setOrder(false);
                    } else {
                        //  Place Order
                  /*   CustomerModel customer = CustomerController.getCustomerData(getActivity());
                        Intent intent = new Intent(getActivity(), PaymentActivity.class);
                        intent.putExtra("amount", new DecimalFormat("##.##").format(Total));
                        intent.putExtra("name", customer.getFirstName() + " " + customer.getLastName());
                        intent.putExtra("email", customer.getEmailID());
                        intent.putExtra("product_info", CartController.getProductInfo(getActivity()));
                        startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
                        Toast.makeText(getActivity(), "Your order has been placed successfully..!!", Toast.LENGTH_SHORT).show();
                        getActivity().finish();*/
                        CustomerModel customer = CustomerController.getCustomerData(getActivity());
                        Intent intent = new Intent(getActivity(), PaymentActivity.class);
                        intent.putExtra("name", customer.getFirstName() + " " + customer.getLastName());
                        intent.putExtra("amount", new DecimalFormat("##.##").format(Total));
                        Log.e("FragmentPayment", "amount==" + new DecimalFormat("##.##").format(Total));
                        intent.putExtra("description", CartController.getProductInfo(getActivity()));
                        intent.putExtra("emailID", customer.getEmailID());
                        intent.putExtra("phone_Number", customer.getMobile_Number());
                        startActivity(intent);

                    }
                } else if (v.getId() == R.id.rl_COD) {
                    rb_OptionCOD.setChecked(true);
                    rb_OptionCard.setChecked(false);
                    transaction_type = "COD";
                } else if (v.getId() == R.id.rl_Online) {
                    rb_OptionCOD.setChecked(false);
                    rb_OptionCard.setChecked(true);
                    transaction_type = "ONLINE";

                }
            }
        } catch (Exception ex) {
        }


    }

    public void setOrder(boolean isPaid) {
        try {
            productModelQties = CartController.getCartProducts(getActivity());
            JSONArray order_data = new JSONArray();
            for (ProductModelQty productModelQty : productModelQties) {
                JSONObject lineItemObj = new JSONObject();
                lineItemObj.put("product_id", productModelQty.getProductModel().getProduct_ID());
                lineItemObj.put("product_quantity", productModelQty.getQty());
                lineItemObj.put("selling_price", productModelQty.getProductModel().getSelling_Price());
                order_data.put(lineItemObj);
            }
            JSONObject other_detail = new JSONObject();
            ArrayList<AddressModel> address = CustomerController.getDefaultAddress(getActivity());
            other_detail.put("first_name", address.get(0).getFirstName());
            other_detail.put("last_name", address.get(0).getLastName());
            other_detail.put("customer_address", address.get(0).getCustomer_Address());
            other_detail.put("landmark", address.get(0).getLandmark());
            other_detail.put("area", address.get(0).getArea());
            other_detail.put("pincode", address.get(0).getPincode());
            other_detail.put("city", address.get(0).getCity());
            other_detail.put("state", address.get(0).getState());
            other_detail.put("mobile_number", address.get(0).getMobileNumber());
            other_detail.put("default_address", "1");

            if (isPaid) {
                //   transaction_type = "cod";
                transaction_ID = "";
            }
            createOrder(order_data.toString(), other_detail.toString());
        } catch (Exception ex) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (payment_Success.matches("payment_Success")) {
            if (transaction_type.matches("COD")) {
                setOrder(false);
            } else {
                setOrder(true);
            }

        } else if (payment_Success.matches("payment_Failed")) {
            Toast.makeText(getActivity(), "Payment has been failed..!!", Toast.LENGTH_SHORT).show();

        }

    }

    public void createOrder(final String order_data, final String other_detail) {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Order...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            OrderController orderController = new OrderController();
            if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
                Log.e("FragmentPayment", "transaction_type==" + transaction_type);
                orderController.createOrder(getActivity(), order_data, "", transaction_type, transaction_ID, other_detail, Total + "", Discount + "", Amount + "", shipping + "", vat + "", new ApiCallBack() {

                    @Override
                    public void onSuccess(Object success) {
                        progressDialog.dismiss();
                        MyUtils.showMessageBox("Order Create Successfully", "OK", "", false, getActivity());
                        MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().finish();
                            }
                        });
                        CartController.removeCart(getActivity());
                        if (FragmentProductDetail.tvp_Count != null)
                            FragmentProductDetail.setCartCount(getActivity());
                    }

                    @Override
                    public void onError(String error) {
                        progressDialog.dismiss();
                        Log.e("FragmentPayment", "error==" + error.toString());
                        MyUtils.showMessageBox("Error In Network Connection.Please Try After Some Time!!!", "OK", "", false, getActivity());
                    }
                });
            } else {
                MyUtils.showSnackBar(getActivity(), CartActivity.ll_cart, "No Internet Connection..!!");
            }

        } catch (Exception ex) {
            Log.e("FragmentPayment", "error==" + ex.getMessage());
        }
    }

   /* private void setOrder() {
        try {
            ArrayList<ProductModelQty> productModelQties = CartController.getCartProducts(getActivity());
            JSONObject order = new JSONObject();
            JSONArray line_items = new JSONArray();
            for (ProductModelQty productModelQty : productModelQties) {
                JSONObject lineItemObj = new JSONObject();
                lineItemObj.put("product_id", productModelQty.getProductModel().getId());
                lineItemObj.put("quantity", productModelQty.getQty());
                lineItemObj.put("requires_shipping", true);
                lineItemObj.put("sku", productModelQty.getProductModel().getSku());
                lineItemObj.put("title", productModelQty.getProductModel().getTitle());
                lineItemObj.put("variant_id", productModelQty.getProductModel().getVariant_id());
                lineItemObj.put("vendor", "boxedjewel");
                lineItemObj.put("price", productModelQty.getProductModel().getPrice());
                lineItemObj.put("gift_card", false);
                lineItemObj.put("taxable", true);
                JSONArray tax_lines = new JSONArray();
                JSONObject tax = new JSONObject();
                Double tax_price = (((Double.parseDouble(productModelQty.getProductModel().getPrice()) * productModelQty.getQty()) * 1.2) / 100);
                tax.put("price", tax_price.intValue() + "");
                tax.put("rate", "1.2 %");
                tax.put("title", "vat");
                tax_lines.put(tax);
                lineItemObj.put("tax_lines", tax_lines);
                JSONObject properties = new JSONObject();
              *//* properties.put("Gold Weight", productModelQty.getProductModel().getGold_weight());
                properties.put("Gold Color", productModelQty.getProductModel().getCust_gold_color());
                properties.put("Labour Charges", MyApplication.labour_Charges);
                properties.put("Gold", productModelQty.getProductModel().getCust_gold_type());
                properties.put("Diamond Type", productModelQty.getProductModel().getCust_diamond());
                properties.put("Diamond Weight", productModelQty.getProductModel().getDiamond_weight());
                lineItemObj.put("properties", properties);*//*
                line_items.put(lineItemObj);
            }
            order.put("line_items", line_items);
//            JSONArray transactions = new JSONArray();
//            JSONObject trans = new JSONObject();
//            trans.put("kind", "sale");
//            trans.put("status", "success");
//            trans.put("amount", tv_Price.getText());
//            transactions.put(trans);
//            order.put("transactions", transactions);
            ArrayList<AddressModel> address = CustomerController.getDefaultAddress(getActivity());
            JSONObject billing_address = new JSONObject();
            billing_address.put("first_name", address.get(0).getFirstName());
            billing_address.put("last_name", address.get(0).getLastName());
            billing_address.put("address1", address.get(0).getAddress1() + " " + address.get(0).getAddress2());
            billing_address.put("phone", address.get(0).getPhone());
            billing_address.put("city", address.get(0).getCity());
            billing_address.put("province", address.get(0).getProvince());
            billing_address.put("country", address.get(0).getCountry());
            billing_address.put("zip", address.get(0).getZip());
            order.put("billing_address", billing_address);
            order.put("shipping_address", billing_address);
            order.put("currency", "INR");
            JSONObject customer = new JSONObject();
            customer.put("id", MyApplication.setting.getString(SH_USER_ID, ""));
            order.put("customer", customer);
            order.put("financial_status", "pending");
            order.put("note", et_Note.getText().toString().trim());
            JSONObject finalObj = new JSONObject();
            finalObj.put("order", order);
            createOrder(getActivity(), finalObj);

        } catch (Exception e) {

        }

    }

    public void createOrder(final Context context, JSONObject order) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Order place...");
        progressDialog.show();
        String url = MyApplication.Url + "orders.json?access_token=" + MyApplication.Access_Token + "&scope=write_orders";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, order, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                CartController.removeCart(getActivity());
                MyUtil.showMessageBox("Your Order is succefully placed,", "OK", "", false, getActivity());
                MyUtil.btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtil.msgDialog.dismiss();
                        getActivity().finish();
                    }
                });
                Log.e("Responce", response.toString());


                {
                }

            }
        }

                , new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;

                try {
                    JSONObject jsonObject = (new JSONObject(new String(error.networkResponse.data)));
                } catch (Exception ex) {
                    MyUtil.showMessageBox("Something went wrong please try again.", "OK", "", false, context);
                }

            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, "Register");

    }*/
}
