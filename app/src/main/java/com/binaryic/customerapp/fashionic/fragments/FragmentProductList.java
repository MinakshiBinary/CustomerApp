package com.binaryic.customerapp.fashionic.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.adapters.ProductAdapter;
import com.binaryic.customerapp.fashionic.models.ProductModel;

import java.util.ArrayList;

/**
 * Created by Asd on 17-09-2016.
 */
public class FragmentProductList extends Fragment implements ProductAdapter.ClickListener, View.OnClickListener, ProductAdapter.ScrollListener {
    public ArrayList<ProductModel> list;
    RecyclerView recycler;
    boolean isClickOn = true;
    LinearLayout progress;
    TextView tvMessage, tvBtnMessage;
    LinearLayout btnRetry;
    ImageView imgMessage;
    View infoView;
    boolean isScrolled = true;
    int page = 1;
    int productCount = 0;
    ProductAdapter adapter;
    String collection = "";
    String collection_id = "";
    boolean isFilter = false;
    String sortOrder = "";

    ArrayList<String> listFilter = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productlisting, container, false);
        list = new ArrayList<>();
        Init(view);
        GetProductList();
        SetUpList();
        return view;
    }


    private void Init(View view) {

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        progress = (LinearLayout) view.findViewById(R.id.progress);
        FloatingActionButton filterAB = (FloatingActionButton) view.findViewById(R.id.filterAB);
        filterAB.setOnClickListener(this);
        infoView = view.findViewById(R.id.infoView);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        tvBtnMessage = (TextView) view.findViewById(R.id.tv_Message);
        btnRetry = (LinearLayout) view.findViewById(R.id.ll_Retry);
        btnRetry.setOnClickListener(this);
        imgMessage = (ImageView) view.findViewById(R.id.imgMessage);
        tvMessage.setText("Product is not available for this collection");
        infoView.setVisibility(View.GONE);
        GetExtra();

    }


    private void GetExtra() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            collection = bundle.getString("collection");
            collection_id = bundle.getString("collection_id");
        }

    }

    private void SetUpRecyclerView() {

        if (list.size() == 0) {
            infoView.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        } else {
            infoView.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);

        }
        Log.e("ProductAdapter", "ProductAdapter");
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new ProductAdapter(getActivity(), list);
        adapter.setClickListener(this);
        adapter.setScrollListener(this);
       // adapter.list = list;
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void itemClicked(View view, int position) {


       /* if (view.getId() == R.id.imgfav) {
            if (list.get(position).getIsFav()) {
                WishListController.deleteProduct(getActivity(), list.get(position));
                list.get(position).setIsFav(false);
            } else {
                WishListController.addProduct(getActivity(), list.get(position));
                list.get(position).setIsFav(true);
            }


            adapter.list = list;
            adapter.notifyDataSetChanged();
            int wish_Count = WishListController.getWishCount(getActivity());
            Log.e("wish_Count", "==" + wish_Count);
            if (wish_Count == 0) {
                ProductListActivity.tv_Wish_Count.setVisibility(View.GONE);
            } else {
                ProductListActivity.tv_Wish_Count.setText(wish_Count + "");
                ProductListActivity.tv_Wish_Count.setVisibility(View.VISIBLE);
                Log.e("tv_Wish_Count", "===else" + wish_Count);
            }
        } else {
            MyApplication.productModel = list.get(position);
            startActivity(new Intent(getActivity(), ProductDetailActivity.class));
        }*/


    }

    @Override
    public void onClick(View v) {

        /*if (isClickOn) {
            if (v.getId() == R.id.filterAB) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                intent.putStringArrayListExtra("filter", listFilter);
                intent.putExtra("sort", sortOrder);
                startActivityForResult(intent, 1);
            } else if (v.getId() == R.id.ll_Retry) {
                getActivity().finish();
            }
        }*/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {

                listFilter = data.getStringArrayListExtra("filter");
                sortOrder = data.getStringExtra("sort");
                Log.e("filter", listFilter.toString());
                progress.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
                page = 1;
                list = new ArrayList<>();
                productCount = 0;
                if (listFilter.size() > 0)
                    isFilter = true;
                else
                    isFilter = false;
                GetProductList();

            } else {
                isFilter = false;
            }
        }
    }

    private void Filtering(ArrayList<ProductModel> productList) {

     /*   ProductController productController = new ProductController();
        ArrayList<String> filteredProductIds = productController.getFilteredProducts(getActivity(), listFilter);
        ArrayList<ProductModel> tempProduct = productList;
        for (ProductModel productModel : tempProduct) {
            for (String id : filteredProductIds) {
                if (id.equals(productModel.getId())) {
                    Log.e("filter", productModel.getTags());
                    list.add(productModel);
                    break;
                }
            }
        }*/

    }

    private void GetProductList() {

      /*  if (MyUtil.isConnectingToInternet(getActivity())) {
            progress.setVisibility(View.VISIBLE);
            ProductController productController = new ProductController();
            // productController.setProductResponse(this);

            Log.e("GetProductList", "collection_id==" + collection_id);
            Log.e("GetProductList", "page==" + page);
            Log.e("GetProductList", "sortOrder==" + sortOrder);
            productController.getProductApiCall(getActivity(), collection_id, page, sortOrder, new CallBackResult<ArrayList<ProductModel>>() {
                @Override
                public void onSuccess(ArrayList<ProductModel> productModels) {
                    if (productModels.size() == 0)
                        isScrolled = false;
                    else
                        isScrolled = true;
                    Log.e("isFilter", isFilter + "");
                    if (isFilter)
                        Filtering(productModels);
                    else {
                        for (ProductModel product : productModels) {
                            list.add(product);
                        }
                    }
                    SetUpList();
                    if (productModels.size() == 0)
                        isScrolled = false;
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError(String error) {

                }
            });
        } else {
            MyUtil.showMessageBox("Internet is not working now.", "OK", "", false, getActivity());
        }
*/
    }


    private void SetUpList() {

        if (productCount == list.size()) {
            if (isScrolled) {
                page++;
                GetProductList();
            } else {
                if (list.size() == 0) {
                    infoView.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }
            }
        } else {
            productCount = list.size();
            if (recycler.getVisibility() == View.VISIBLE) {
                Log.e("notify", list.toString());
                adapter.list = list;
                adapter.notifyDataSetChanged();
            } else
                SetUpRecyclerView();
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        Log.e("superonResume","onResume");
        SetUpRecyclerView();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void scrolled(int position) {

        if (position >= productCount) {
            if (isScrolled) {
                page++;
                GetProductList();
            }
        }

    }


}
