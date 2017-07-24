package com.binaryic.customerapp.fashionic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CallBackResult;
import com.binaryic.customerapp.fashionic.controller.ProductController;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


/**
 * Created by HP on 12-Mar-17.
 */

public class FragmentWriteReview extends Fragment {

    String rating = "0";
    ImageView iv_product;
    TextView tv_product_name;
    EditText et_title;
    EditText et_experience;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);
        inIt(view);
        return view;
    }



    private void inIt(View view) {
        iv_product = (ImageView) view.findViewById(R.id.iv_product);
        tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
        final ImageView iv_star1 = (ImageView) view.findViewById(R.id.iv_star1);
        final ImageView iv_star2 = (ImageView) view.findViewById(R.id.iv_star2);
        final ImageView iv_star3 = (ImageView) view.findViewById(R.id.iv_star3);
        final ImageView iv_star4 = (ImageView) view.findViewById(R.id.iv_star4);
        final ImageView iv_star5 = (ImageView) view.findViewById(R.id.iv_star5);
        et_title = (EditText) view.findViewById(R.id.et_title);
        et_experience = (EditText) view.findViewById(R.id.et_experience);
        final TextView tv_error_review = (TextView) view.findViewById(R.id.tv_error_review);
        final TextView tv_error_experiance = (TextView) view.findViewById(R.id.tv_error_experiance);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rating.equals("0")) {
                    boolean isValid = true;
                    if (et_title.getText().toString().trim().equals("")) {
                        tv_error_review.setVisibility(View.VISIBLE);
                        isValid = false;
                    } else
                        tv_error_review.setVisibility(View.GONE);
                    if(et_experience.getText().toString().trim().equals("")){
                        tv_error_experiance.setVisibility(View.VISIBLE);
                        isValid = false;
                    }else{
                        tv_error_experiance.setVisibility(View.GONE);
                    }
                    if (isValid) {
                        apiCall();
                    }
                }else{
                    Toast.makeText(getActivity(), "Please rate some star !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        iv_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "1";
                iv_star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
                iv_star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
                iv_star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
                iv_star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
            }
        });
        iv_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "2";
                iv_star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
                iv_star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
                iv_star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
            }
        });
        iv_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "3";
                iv_star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
                iv_star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
            }
        });
        iv_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "4";
                iv_star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_start_gray));
            }
        });
        iv_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "5";
                iv_star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
                iv_star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
            }
        });
        et_title.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    et_experience.requestFocus();
                }
                return false;
            }
        });
        setImageUI();
    }

    private void apiCall() {
        if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
            ProductController productController = new ProductController();
            MyUtils.showProgress("Save Ratings...", getActivity());
            productController.addReviewCall(getActivity(), MyApplication.productModel.getProduct_ID(), rating, et_title.getText().toString(), et_experience.getText().toString(), new CallBackResult<Boolean>() {
                @Override
                public void onSuccess(Boolean s) {
                    MyUtils.progressDialog.dismiss();
                    MyUtils.showMessageBox("Review Save successfully.","OK","",false, getActivity());
                    MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUtils.msgDialog.dismiss();
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                            if(closeListner != null)
                                closeListner.onClose();
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    MyUtils.showMessageBox(error,"OK","",false,getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Internet is not working.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setImageUI() {
        tv_product_name.setText(MyApplication.productModel.getProduct_Name());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightPixels = displayMetrics.heightPixels;
        ViewGroup.LayoutParams params = iv_product.getLayoutParams();
        params.height = (int) (long) Math.round(heightPixels / 3);
        iv_product.setLayoutParams(params);
        String product_image = MyApplication.productModel.getProduct_Image_Array().get(0);
        if (!product_image.equals(""))
            Glide.with(getActivity()).load(product_image).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.logo).thumbnail(0.1f).into(iv_product);
    }
    private CloseListner closeListner;


    public void setCloseListner(CloseListner closeListner) {
        this.closeListner = closeListner;
    }

    public interface CloseListner {
        public void onClose();
    }
}
