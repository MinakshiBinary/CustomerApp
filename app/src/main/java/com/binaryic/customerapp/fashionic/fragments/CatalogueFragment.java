package com.binaryic.customerapp.fashionic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.CartActivity;
import com.binaryic.customerapp.fashionic.activities.MainActivity;
import com.binaryic.customerapp.fashionic.activities.ProductListActivity;
import com.binaryic.customerapp.fashionic.activities.ProductsDetailActivity;
import com.binaryic.customerapp.fashionic.adapters.CatalogueAdapter;
import com.binaryic.customerapp.fashionic.common.ApiCallBack;
import com.binaryic.customerapp.fashionic.common.InternetConnectionDetector;
import com.binaryic.customerapp.fashionic.common.MyApplication;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CartController;
import com.binaryic.customerapp.fashionic.controller.ProductController;
import com.binaryic.customerapp.fashionic.controller.WishListController;
import com.binaryic.customerapp.fashionic.models.ProductModel;

import java.util.ArrayList;


/**
 * Created by Binary_Apple on 1/21/17.
 */

public class CatalogueFragment extends Fragment implements View.OnClickListener, CatalogueAdapter.ClickListener, CatalogueAdapter.ScrollListener, MainActivity.BackPressListener {

    public static RelativeLayout rl_SellProduct;
    private String categoryId = "";
    private FloatingActionButton fab_AddCategory;
    private TextView tv_Product_No_Available;
    private TextView tv_Product_No_Search;
    private RecyclerView rv_Product;
    View infoView;
    boolean isScrolled = true;
    int page = 1;
    int position = 0;
    int productCount = 0;
    LinearLayout ll_Retry;
    private ArrayList<ProductModel> list = new ArrayList<ProductModel>();
    private static ArrayList<ProductModel> array_Product_Model = new ArrayList<ProductModel>();
    private SwipeRefreshLayout swipeContainer;
    private CatalogueAdapter catalogueAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalogue, container, false);
        getExtra();
        init(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        // Log.e("Filter_Sort_Contort", "==" + Filter_Sort_Controller.sort);
        // getProducts(Filter_Sort_Controller.sort, "");
        Log.e("CatelogueFragment", "onResume");
        page = 1;
        list.clear();
        productCount = 0;
        callApi();
    }

    private void init(View view) {
        infoView = view.findViewById(R.id.info_View);//if cart is empty than show
        ll_Retry = (LinearLayout) infoView.findViewById(R.id.ll_Retry);
        fab_AddCategory = (FloatingActionButton) view.findViewById(R.id.fab_AddCategory);
        rl_SellProduct = (RelativeLayout) view.findViewById(R.id.rl_SellProduct);
        rv_Product = (RecyclerView) view.findViewById(R.id.rv_Category);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        tv_Product_No_Available = (TextView) view.findViewById(R.id.tv_Category_No_Available);
        tv_Product_No_Search = (TextView) view.findViewById(R.id.tv_Category_No_Search);
        rv_Product.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        fab_AddCategory.setOnClickListener(this);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //getProducts(Filter_Sort_Controller.sort, "");
                try {

                    if (isScrolled) {
                        page++;
                        callApi();
                    } else {
                        swipeContainer.setRefreshing(false);
                    }

                } catch (Exception ex) {
                    Log.e("CatelogueFragment", "error==" + ex.getMessage());

                }


            }
        });
        ll_Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).onBackPressed();
                } else if (getActivity() instanceof ProductListActivity) {
                    ((ProductListActivity) getActivity()).onBackPressed();
                } else if (getActivity() instanceof ProductsDetailActivity) {
                    ((ProductsDetailActivity) getActivity()).onBackPressed();
                }
            }
        });

    }

    public void setRecycleView() {

        Log.e("array_Product_Modelsize","=="+array_Product_Model.size());
        if (array_Product_Model.size() > 0) {
            rv_Product.setVisibility(View.VISIBLE);
            infoView.setVisibility(View.GONE);
            catalogueAdapter = new CatalogueAdapter(getActivity(), list);
            catalogueAdapter.notifyDataSetChanged();
            catalogueAdapter.setClickListener(this);
            catalogueAdapter.setScrollListener(this);
            rv_Product.setAdapter(catalogueAdapter);
        } else {
            rv_Product.setVisibility(View.GONE);
            infoView.setVisibility(View.VISIBLE);
            TextView tvMessage = (TextView) infoView.findViewById(R.id.tvMessage);
            tvMessage.setText("No Products Available.");
        }
        swipeContainer.setRefreshing(false);

    }

    private void SetUpList() {
        try {
            Log.e("CatelogueFragment", "productCount=" + productCount);
            Log.e("CatelogueFragment", "list==" + list.size());
            if (productCount == list.size()) {
                if (isScrolled) {
                    page++;
                    Log.e("CatelogueFragment", "SetUpList");
                    callApi();
                } else {
                    if (list.size() == 0) {
                        infoView.setVisibility(View.VISIBLE);
                        rv_Product.setVisibility(View.GONE);
                    }
                }
            } else {
                productCount = list.size();
                Log.e("CatelogueFragment", "rv_Product.getVisibility()=" + rv_Product.getVisibility());
                if (rv_Product.getVisibility() == View.VISIBLE) {
                    setRecycleView();
                    /*Log.e("notify", list.toString());
                    catalogueAdapter.array_Data = list;
                    catalogueAdapter.notifyDataSetChanged();*/
                } else
                    setRecycleView();
            }
        } catch (Exception ex) {
            Log.e("CatelogueFragment", "error==" + ex.getMessage());
        }
    }


    private void callApi() {
        swipeContainer.setRefreshing(true);
        Log.e("CatelogueFragment", "callApi==");

        if (InternetConnectionDetector.isConnectingToInternet(getActivity())) {
            ProductController.getCategoryProducts(getActivity(), categoryId, page, new ApiCallBack() {
                @Override
                public void onSuccess(Object success) {

                    // array_Product_Model.clear();
                    array_Product_Model = (ArrayList<ProductModel>) success;

                    if (array_Product_Model.size() == 0)
                        isScrolled = false;
                    else
                        isScrolled = true;
                    for (ProductModel product : array_Product_Model) {
                        list.add(product);
                    }
                    Log.e("CatelogueFragment", "list==" + list.size());
                    // SetLike();
                    SetUpList();
                    if (array_Product_Model.size() == 0)
                        isScrolled = false;
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    resetList();
                    swipeContainer.setRefreshing(false);

                }
            });
        } else {
            MyUtils.showSnackBar(getActivity(), rl_SellProduct, "No Internet Connection..!!");
            resetList();
            swipeContainer.setRefreshing(false);

        }
    }

    public void resetList() {
        if (array_Product_Model.size() != ProductController.getProductCategoryTableCount(getActivity(), categoryId)) {

            array_Product_Model = ProductController.getAllProductsofCategory(getActivity(), categoryId);

            for (ProductModel product : array_Product_Model) {
                list.add(product);
            }
        }

        setRecycleView();

    }

    private void getProducts(String sort, final String search) {
        swipeContainer.setRefreshing(true);
        // final ProgressDialog progressDialog = new ProgressDialog(getContext());
        //progressDialog.setMessage("Please Wait....!!!!");
        //  progressDialog.show();
        // progressDialog.setCancelable(false);
       /* if (InternetConnectionDetector.isConnectingToInternet(getActivity(), rl_SellProduct, "No Internet Connection"))
            ProductController.getCategoryProducts(getActivity(), sort, new ApiCallBack() {
                @Override
                public void onSuccess(Object success) {
                    setRecycleView(search);
                    // progressDialog.dismiss();
                }

                @Override
                public void onError(String error) {
                    //progressDialog.dismiss();
                }
            });*/
    }

    public void setRecycleView(String search) {
       /* array_Product_Model = ProductController.getAllProductsFromDatabase(getActivity(), LoginController.getSellerID(getActivity()), search);

        if (array_Product_Model.size() > 0) {
            rv_Product.setVisibility(View.VISIBLE);
            tv_Product_No_Available.setVisibility(View.GONE);
            tv_Product_No_Search.setVisibility(View.GONE);
            CatalogueAdapter catalogueAdapter = new CatalogueAdapter(getActivity(), array_Product_Model);
            catalogueAdapter.notifyDataSetChanged();
            rv_Product.setAdapter(catalogueAdapter);
        } else {
            if (TextUtils.isEmpty(search)) {
                tv_Product_No_Available.setVisibility(View.VISIBLE);
            } else {
                tv_Product_No_Search.setVisibility(View.VISIBLE);
            }
            rv_Product.setVisibility(View.GONE);

        }
        swipeContainer.setRefreshing(false);*/
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_AddCategory:
                //startActivity(new Intent(getActivity(), FilterActivity.class));
                break;
        }
    }

    public void getExtra() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            categoryId = bundle.getString("categoryId");
        }
    }

    @Override
    public void itemClicked(View view, int position) {
        Log.e("CatalogueFragment", "list" + list.size());
        if (view.getId() == R.id.iv_Favorite) {
            if (list.get(position).isFav()) {
                WishListController.deleteProduct(getActivity(), list.get(position));
                list.get(position).setFav(false);
            } else {
                WishListController.addProduct(getActivity(), list.get(position));
                list.get(position).setFav(true);
            }

            catalogueAdapter.array_Data = list;
            catalogueAdapter.notifyDataSetChanged();

            int wish_Count = WishListController.getWishCount(getActivity());
            Log.e("wish_Count", "==" + wish_Count);
            if (wish_Count == 0) {
                ((ProductListActivity) getActivity()).tv_Wish_Count.setVisibility(View.GONE);

            } else {
                ((ProductListActivity) getActivity()).tv_Wish_Count.setText(wish_Count + "");
                ((ProductListActivity) getActivity()).tv_Wish_Count.setVisibility(View.VISIBLE);

                Log.e("tv_Wish_Count", "===else" + wish_Count);
            }
        } else if (view.getId() == R.id.ll_Buy) {

            CartController.addProduct(getActivity(), list.get(position), "1");
            int Count = CartController.getCartCount(getActivity());

            if (Count == 0) {
                MainActivity.tvCount.setVisibility(View.GONE);
                ProductListActivity.tv_Wish_Count.setVisibility(View.GONE);

            } else {
                MainActivity.tvCount.setText(Count + "");
                ProductListActivity.tv_Wish_Count.setVisibility(View.GONE);

                MainActivity.tvCount.setVisibility(View.VISIBLE);
                ProductListActivity.tv_Wish_Count.setVisibility(View.GONE);

                Log.e("cart_Count", "===else" + Count);
            }
            startActivity(new Intent(getActivity(), CartActivity.class));


        } else if (view.getId() == R.id.iv_Product_Image) {
            MyApplication.productModel = list.get(position);

            Intent intent = new Intent(getActivity(), ProductsDetailActivity.class);
            intent.putExtra("productId", list.get(position).getProduct_ID());
            startActivity(intent);



           /* MyApplication.productModel = list.get(position);
            startActivity(new Intent(getActivity(), ProductDetailActivity.class));*/
        }
    }


    @Override
    public void Scrolled(int position) {
        Log.e("CatelogueFragment", "Scrolled position=" + position);
        Log.e("CatelogueFragment", "productCount==" + productCount);

        try {
            if (position >= productCount) {
                if (isScrolled) {
                    page++;
                    callApi();
                }
            }
        } catch (Exception ex) {
            Log.e("CatelogueFragment", "error==" + ex.getMessage());

        }
    }

    public void SetLikeList() {
        try {
            String ids = WishListController.getFavProductId(getActivity());
            for (ProductModel productModel : list) {
                if (ids.contains(productModel.getProduct_ID()))
                    productModel.setFav(true);
                else
                    productModel.setFav(false);
            }
            catalogueAdapter.array_Data = list;
            catalogueAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
        }
    }

    @Override
    public void backPress() {
        resetList();
    }
}