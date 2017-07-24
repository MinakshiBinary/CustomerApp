package com.binaryic.customerapp.fashionic.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.CartActivity;
import com.binaryic.customerapp.fashionic.activities.ReviewActivity;
import com.binaryic.customerapp.fashionic.adapters.ProductImagePagerAdapter;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CallBackResult;
import com.binaryic.customerapp.fashionic.controller.CartController;
import com.binaryic.customerapp.fashionic.controller.ProductController;
import com.binaryic.customerapp.fashionic.controller.WishListController;
import com.binaryic.customerapp.fashionic.custom.CirclePageIndicator;
import com.binaryic.customerapp.fashionic.models.ProductModel;

import java.util.ArrayList;


/**
 * Created by Asd on 19-09-2016.
 */
public class FragmentProductDetail extends Fragment implements View.OnClickListener /*FragmentCustomize.DismissListener, CustomAdapter.ClickListener */ {
    public boolean click = false;
    public static TextView tvp_Count;
    public TextView tv_Check_Pincode;
    Boolean isClickOn = true;
    ViewPager product_detail_viewPager;
    CirclePageIndicator indicator;
    ArrayList<String> images;
    FrameLayout LaySimilar;
    TextView tvOldPrice, tvSellingPrice;
    RelativeLayout btnShare, btnFav;
    ImageView imgfav;
    WebView webview;
    ImageView imgCart;
    TextView btnCustomize;
    TextView tv_QuantityStatus;
    TextView tv_abuse;
    TextView tv_total_rating;
    TextView product_detail_name;
    LinearLayout llPDPback;
    // RelativeLayout layPinCode;
    private TextView tvYellowGold, tvWhiteGold, tvRoseGold, tv14kt, tv18kt, tvSIIJ, tvVSGH, tvVVSEF, btnCancel, btnApply;
    private LinearLayout linear_Customize;
    private TextView tvAddtoCart;
    private TextView tv_DetailContent;
    private RecyclerView listSize;
    private String Cust_size = "";
    private String Cust_gold_color = "yellow";
    private String Cust_diamond = "SI IJ";
    private String Cust_gold_type = "18 KT";
    private Double price = 0.0;
    private Double gold_price = 0.0;
    private Double diamond_price = 65000.00;
    private Double ma_price = 0.0;
    private Double originalPrice = 0.0;
    private TextView tv_Gold_Color;
    private TextView tv_Product_Detail;
    private TextView tv_Dimond_Type;
    private TextView tv_Gold_Type;
    private ImageView iv_Close;
    private RelativeLayout rl_Cart;
    private RelativeLayout rl_Add_To_Cart;
    private EditText et_Pincode;
    private TextView tv_Product_Count;
    private TextView tv_Plus;
    private TextView tv_Minus;
    private LinearLayout ll_ratings;
    private RatingBar rating_bar;
    private String productId = "";
    ProductModel productModel = new ProductModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productdetail, container, false);
        getExtra();
        init(view);
        return view;
    }

    private void init(View view) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ll_ratings = (LinearLayout) view.findViewById(R.id.ll_ratings);
        imgCart = (ImageView) view.findViewById(R.id.imgCart);
        tvYellowGold = (TextView) view.findViewById(R.id.tvYellowGold);
        tvWhiteGold = (TextView) view.findViewById(R.id.tvWhiteGold);
        tvRoseGold = (TextView) view.findViewById(R.id.tvRoseGold);
        tv_Product_Count = (TextView) view.findViewById(R.id.tv_Product_Count);
        tv_Plus = (TextView) view.findViewById(R.id.tv_Plus);
        tv_Minus = (TextView) view.findViewById(R.id.tv_Minus);
        tv14kt = (TextView) view.findViewById(R.id.tv14kt);
        tv18kt = (TextView) view.findViewById(R.id.tv18kt);
        tvSIIJ = (TextView) view.findViewById(R.id.tvSIIJ);
        tvVSGH = (TextView) view.findViewById(R.id.tvVSGH);
        tvVVSEF = (TextView) view.findViewById(R.id.tvVVSEF);
        tv_Gold_Color = (TextView) view.findViewById(R.id.tv_Gold_Color);
        tv_Dimond_Type = (TextView) view.findViewById(R.id.tv_Dimond_Type);
        tv_Product_Detail = (TextView) view.findViewById(R.id.tv_Product_Detail);
        tv_DetailContent = (TextView) view.findViewById(R.id.tv_DetailContent);
        tv_Gold_Type = (TextView) view.findViewById(R.id.tv_Gold_Type);
        tvp_Count = (TextView) view.findViewById(R.id.tvp_Count);
        tv_Check_Pincode = (TextView) view.findViewById(R.id.tv_Check_Pincode);
        rl_Cart = (RelativeLayout) view.findViewById(R.id.rl_Cart);
        rl_Add_To_Cart = (RelativeLayout) view.findViewById(R.id.rl_Add_To_Cart);
        iv_Close = (ImageView) view.findViewById(R.id.iv_Close);
        et_Pincode = (EditText) view.findViewById(R.id.et_Pincode);

        llPDPback = (LinearLayout) view.findViewById(R.id.llPDPback);
        imgfav = (ImageView) view.findViewById(R.id.imgfav);

        product_detail_viewPager = (ViewPager) view.findViewById(R.id.product_detail_viewPager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

        linear_Customize = (LinearLayout) view.findViewById(R.id.linear_Customize);
        // layPinCode = (RelativeLayout) view.findViewById(R.id.layPinCode);
        //LaySimilar = (FrameLayout) view.findViewById(R.id.LaySimilar);
        tvOldPrice = (TextView) view.findViewById(R.id.tvOldPrice);
        tvSellingPrice = (TextView) view.findViewById(R.id.tvSellingPrice);
        btnCustomize = (TextView) view.findViewById(R.id.btnCustomize);
        tv_QuantityStatus = (TextView) view.findViewById(R.id.tv_QuantityStatus);
        rating_bar = (RatingBar) view.findViewById(R.id.rating_bar);
        tv_abuse = (TextView) view.findViewById(R.id.tv_abuse);
        tv_total_rating = (TextView) view.findViewById(R.id.tv_total_rating);
        product_detail_name = (TextView) view.findViewById(R.id.product_detail_name);
        tvAddtoCart = (TextView) view.findViewById(R.id.tvAddtoCart);
        btnFav = (RelativeLayout) view.findViewById(R.id.btnFav);
        webview = (WebView) view.findViewById(R.id.webview);
        listSize = (RecyclerView) view.findViewById(R.id.listSize);
        // Cust_size = MyApplication.productModel.getCust_size();


        imgCart.setColorFilter(getResources().getColor(R.color.white));

        LinearLayout laySize = (LinearLayout) view.findViewById(R.id.laySize);
        /*if (MyApplication.productModel.getProduct_type().equals("Rings")) {
            laySize.setVisibility(View.VISIBLE);
            setUpRecyclerView();
        } else {
            laySize.setVisibility(View.GONE);
            setUpRecyclerView();
        }*/

       /* tv_Check_Pincode.setOnClickListener(new View.OnClickListener() {
           *//* @Override
            public void onClick(View view) {
                PincodeController.checkPinCode(et_Pincode.getText().toString().trim(), new CallBackResult<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (s.matches("cod")) {
                            MyUtil.showMessageBox("Congratulations...!!!! \n  COD is available..", "OK", "", false, getActivity());
                        } else if (s.matches("delivery")) {
                            MyUtil.showMessageBox("Congratulations...!!!! \n  Delivery is available..", "OK", "", false, getActivity());
                        } else {
                            MyUtil.showMessageBox("Sorry...!!!! \n  COD and Delivery is not available..", "OK", "", false, getActivity());

                        }

                        MyMyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                msgDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }*//*
        });*/

        tv_Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("FragmentProductDetails", "product count" + Double.parseDouble(MyUtils.increamentCount(tv_Product_Count.getText().toString().trim())));

                if (Double.parseDouble(MyUtils.increamentCount(tv_Product_Count.getText().toString().trim())) <= Double.parseDouble(productModel.getQuantity())) {
                    tv_Product_Count.setText(MyUtils.increamentCount(tv_Product_Count.getText().toString().trim()));
                } else {
                    Toast.makeText(getActivity(), "Reached Maximum Limit", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_Product_Count.setText(MyUtils.decrementCount(tv_Product_Count.getText().toString().trim()));
            }
        });
        llPDPback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        //   layPinCode.setOnClickListener(this);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("FragmentProductDetails", "onClick" + productModel.isFav());
                if (productModel.isFav()) {
                    imgfav.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fav_white));
                    WishListController.deleteProduct(getActivity(), productModel);
                    productModel.setFav(false);
                } else {
                    imgfav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24px));
                    imgfav.setColorFilter(getResources().getColor(R.color.white));
                    WishListController.addProduct(getActivity(), productModel);
                    productModel.setFav(true);
                }
            }
        });
        tv_abuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abuseDialog();
            }
        });

        ll_ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReviewActivity.class);
                startActivity(i);
            }
        });
       /* tv_Product_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webview.getVisibility() == View.VISIBLE) {
                    webview.setVisibility(View.GONE);
                } else {
                    webview.setVisibility(View.VISIBLE);

                }
            }
        });*/

        rl_Add_To_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if (tvAddtoCart.getText().toString().trim().matches("BUY NOW")) {
                    tvAddtoCart.setText("GO TO CART");
                    tvp_Count.setVisibility(View.VISIBLE);
                    CartController.addProduct(getActivity(), MyApplication.productModel,tv_Product_Count.getText().toString().trim());
                    tvp_Count.setText("" + CartController.getCartCount(getActivity()));
                    MyMyUtils.showMessageBox("Add to Cart Successfully go to cart for buy.", "OK", "", false, getActivity());
                } else {
                    startActivity(new Intent(getActivity(), CartActivity.class));
                }
*/
            }
        });
        tvAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvAddtoCart.getText().toString().trim().matches("BUY NOW")) {
                    tvAddtoCart.setText("GO TO CART");
                    tvp_Count.setVisibility(View.VISIBLE);
                    CartController.addProduct(getActivity(), productModel, tv_Product_Count.getText().toString().trim());
                    tvp_Count.setText("" + CartController.getCartCount(getActivity()));
                    MyUtils.showMessageBox("Added To The Cart Successfully...!!", "OK", "NO", false, getActivity());
                    MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           MyUtils.msgDialog.dismiss();
                        }
                    });
                } else if (tvAddtoCart.getText().toString().trim().matches("GO TO CART")) {
                    startActivity(new Intent(getActivity(), CartActivity.class));
                }
            }
        });

        setCartCount(getActivity());

        setImages();
        setData();
        SetRatingData();
        addSimilarFragment();
        addToRecent();
        tvYellowGold.setOnClickListener(this);
        tvWhiteGold.setOnClickListener(this);
        tvRoseGold.setOnClickListener(this);
        tv14kt.setOnClickListener(this);
        tv18kt.setOnClickListener(this);
        tvSIIJ.setOnClickListener(this);
        tvVSGH.setOnClickListener(this);
        tvVVSEF.setOnClickListener(this);
        iv_Close.setOnClickListener(this);
        rl_Cart.setOnClickListener(this);

    }

    public void SetRatingData() {
        try {
            rating_bar.setRating(Float.parseFloat(MyApplication.productModel.getAverage_rating()));
            tv_total_rating.setText(MyApplication.productModel.getTotal_review() + " Reviews");
        } catch (Exception ex) {
        }
    }

    public void abuseDialog() {
        final Dialog abuseDialog = new Dialog(getActivity());
        abuseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        abuseDialog.setContentView(R.layout.dialog_abuse);
        WindowManager.LayoutParams wmlp = abuseDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        abuseDialog.setCanceledOnTouchOutside(false);
        final Spinner spn_reason = (Spinner) abuseDialog.findViewById(R.id.spn_reason);
        final EditText et_message = (EditText) abuseDialog.findViewById(R.id.et_message);
        TextView btnCancel = (TextView) abuseDialog.findViewById(R.id.btnCancel);
        TextView btnDone = (TextView) abuseDialog.findViewById(R.id.btnDone);
        setReasonSpinner(spn_reason);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                abuseDialog.dismiss();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!spn_reason.getSelectedItem().toString().equals("Select Reason")) {
                    if (et_message.getText().toString().trim().equals("")) {
                        Toast.makeText(getActivity(), "Please Enter Message", Toast.LENGTH_SHORT).show();
                    } else {

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        abuseCall(spn_reason.getSelectedItem().toString(), et_message.getText().toString(), abuseDialog);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Select Reason", Toast.LENGTH_SHORT).show();
                }
            }
        });

        abuseDialog.show();
    }

    private void abuseCall(String title, String message, final Dialog abuseDialog) {
        if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
            MyUtils.showProgress("Loading", getActivity());
            ProductController controller = new ProductController();
            controller.abuseApiCall(getActivity(), MyApplication.productModel.getProduct_ID(), title, message, new CallBackResult<Boolean>() {
                @Override
                public void onSuccess(Boolean s) {
                    MyUtils.progressDialog.dismiss();
                    if (s) {
                        Toast.makeText(getActivity(), "Your abuse report successfully.", Toast.LENGTH_SHORT).show();
                    }
                    abuseDialog.dismiss();
                }

                @Override
                public void onError(String error) {
                    MyUtils.progressDialog.dismiss();
                    MyUtils.showMessageBox(error, "OK", "", false, getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please check internet.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setReasonSpinner(Spinner spn) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_adapter_textview, getResources().getStringArray(R.array.array_reason));
        adapter.setDropDownViewResource(R.layout.spinner_adapter_textview);
        spn.setAdapter(adapter);
    }

    public static void setCartCount(Activity context) {

        if (CartController.getCartCount(context) == 0) {
            tvp_Count.setVisibility(View.GONE);
        } else {
            tvp_Count.setVisibility(View.VISIBLE);
            tvp_Count.setText("" + CartController.getCartCount(context));

        }
    }

    private void getExtra() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            productId = bundle.getString("productId");
            Log.e("FragmentProductDetails", "productId" + productId);

            productModel = MyApplication.productModel;
            //productModel = ProductController.getSingleProductTable(getActivity(), productId);
        }
    }

    private void setUpRecyclerView() {
        /*LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listSize.setLayoutManager(layoutManager);
        CustomAdapter adapter = new CustomAdapter(getActivity(), Cust_size);
        adapter.setClickListener(this);
        listSize.setAdapter(adapter);*/
    }

    private void addToRecent() {

        // RecentController.addProduct(getActivity(), MyApplication.productModel);

    }

    //Set Images and BodyHtml
    private void setImages() {

        images = new ArrayList<>();
        Log.e("FragmentProductDetails", "images==" + productModel);

        if (productModel == null) {
            productModel = MyApplication.productModel;
        }
        images = productModel.getProduct_Image_Array();

        //  Log.e("FragmentProductDetails", "imagessize==" + images.size());
        ProductImagePagerAdapter mAdapter = new ProductImagePagerAdapter(getActivity(), images);
        product_detail_viewPager.setAdapter(mAdapter);
        if (images.size() > 1) {
            indicator.setViewPager(product_detail_viewPager);
        }
     /*  String html = MyApplication.productModel.getBody_html();
       html = html.replace("\\n", "").replace("\\", "");
       String head = "<head><link href='https://fonts.googleapis.com/css?family=Dosis' rel='stylesheet' type='text/css'><style>body {font-family: 'Dosis', sans-serif;}.col-1 {float: left;width:150px;color: #757575;font-size:14px;padding:2px 0 2px 10px;text-transform: uppercase;}.col-2 {float: left;width:cal(100%-150px);color: #757575;font-weight: bold;font-size:14px;padding:2px 0 2px 10px;}.col-1.total,.col-2.total {font-weight: bold;color: #212121;}.clearfix {clear: both;}h3 {padding-bottom: 5px;text-transform: uppercase;margin-bottom: 2px;border-bottom: 1px solid #e0e0e0;float: left;width: 100%;color:#757575;font-size:18px;}h3 span {color: #D4AF37;}.box {margin-bottom: 20px;}p {color: #757575;font-size: 15px;}</style></head>";
      String htmlData = "<html>" + head + "<body>" + html + "</body></html>";
       webview.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);*/

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvWhiteGold:
                break;
            case R.id.rl_Cart:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
            /*case R.id.btnApply:
                Log.e("btnApply", "btnApply");
              *//*  MyApplication.productModel.setCust_diamond(Cust_diamond);
                MyApplication.productModel.setCust_gold_type(Cust_gold_type);
                MyApplication.productModel.setCust_gold_color(Cust_gold_color);
                MyApplication.productModel.setCust_size(Cust_size);
                MyApplication.productModel.setOriginal_price(price.intValue() + "");
                MyApplication.productModel.setDiscount_price(originalPrice.intValue() + "");*//*
                break;*/

            case R.id.iv_Close:
                tvAddtoCart.setText("BUY NOW");
                linear_Customize.setVisibility(View.GONE);
                break;


        }

    }

    private void setData() {

        if (CartController.getSingleProduct(getActivity(), productModel.getProduct_ID())) {
            tvAddtoCart.setText("GO TO CART");
        } else {
            tvAddtoCart.setText("BUY NOW");
        }
        Log.e("FragmentProductDetails", "Quantity==" + productModel.getQuantity());
        if (Double.parseDouble(productModel.getQuantity()) <= 0) {
            tv_QuantityStatus.setVisibility(View.VISIBLE);
            tvAddtoCart.setEnabled(false);
        } else {
            tv_QuantityStatus.setVisibility(View.GONE);
            tvAddtoCart.setEnabled(true);
        }
        String upperString = productModel.getProduct_Name().substring(0, 1).toUpperCase() + productModel.getProduct_Name().substring(1);
        Log.e("FragmentProductDetails", "isFav==" + productModel.isFav());

        String ids = WishListController.getFavProductId(getActivity());

        if (ids.contains(productModel.getProduct_ID())) {
            Log.e("FragmentProductDetails", "isFav==" + true);

            imgfav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_favorite_black_24px));
            imgfav.setColorFilter(getResources().getColor(R.color.white));

        } else

        {
            Log.e("FragmentProductDetails", "isFav==" + false);

            imgfav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_fav_white));
        }
        /*if (productModel.isFav())

        {
            imgfav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_favorite_black_24px));
        } else

        {
            imgfav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_fav_white));
        }*/

        product_detail_name.setText(upperString);
        tvSellingPrice.setText("Rs " + productModel.getSelling_Price());
        tvOldPrice.setText("Rs " + productModel.getDiscount_Price());
        tv_DetailContent.setText(productModel.getProduct_Description());


       /* if (MyApplication.productModel.getDiscountModels() == null) {
            tvOldPrice.setVisibility(View.GONE);
        } else {
            tvOldPrice.setVisibility(View.VISIBLE);
            //tvOldPrice.setText("` " + MyApplication.productModel.getDiscount_price());
        }*//*
        tvSellingPrice.setText("` " + MyApplication.productModel.getPrice());

        Log.e("","=="+MyApplication.productModel.getIsFav());
        if (MyApplication.productModel.getIsFav())
            imgfav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_favorite_home_selected));
        else
            imgfav.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_fav_white));*/

    }


    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void addSimilarFragment() {

        //FragmentSimilar fragmentSimilar = new FragmentSimilar();
        //MyUtil.addFragment(LaySimilar.getId(), fragmentSimilar, getActivity());

    }

    private Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }



/*
    public void checkPincodeDialog() {

        final Dialog msgDialog = new Dialog(getActivity());
        msgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        msgDialog.setContentView(R.layout.fragment_pincode_check);
        WindowManager.LayoutParams wmlp = msgDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        msgDialog.setCanceledOnTouchOutside(false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final AnimatedEditText etPinCode = (AnimatedEditText) msgDialog.findViewById(R.id.etPinCode);
        LinearLayout btnCheck = (LinearLayout) msgDialog.findViewById(R.id.btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyUtil.isConnectingToInternet(getActivity())) {
                    Boolean isValid = true;
                    if (etPinCode.et.getText().toString().trim().equals("")) {
                        isValid = false;
                        etPinCode.getInstatnce().SetError("Enter Pincode");
                    }
                    if (isValid) {
                        //Call API Pincode checker..
                        tvMainCheckDelivery.setText("Delivery is available to " + etPinCode.et.getText());
                        tvSubCheckDelivery.setText("Delivery within 6-9 days");
                        msgDialog.dismiss();
                    }
                } else {
                    MyUtil.showMessageBox("Please Check your internet connection.", "OK", "", false, getActivity());
                }

            }
        });
        msgDialog.show();

    }
*/


}