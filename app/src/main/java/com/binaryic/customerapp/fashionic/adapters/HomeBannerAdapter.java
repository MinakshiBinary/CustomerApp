package com.binaryic.customerapp.fashionic.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.ProductsDetailActivity;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.fragments.CatalogueFragment;
import com.binaryic.customerapp.fashionic.models.HomeBannerModel;
import com.binaryic.customerapp.fashionic.models.ProductModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import static com.binaryic.customerapp.fashionic.R.id.layPager;


/**
 * Created by User on 08-09-2016.
 */
public class HomeBannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    ArrayList<ProductModel> listProduct = new ArrayList<>();
    ArrayList<HomeBannerModel> list = new ArrayList<>();
    int banner = 0;
    int discount = 1;
    int collection = 2;
    int feedback = 3;
    int recent = 4;
    int product = 5;
    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public HomeBannerAdapter(Activity context, ArrayList<HomeBannerModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        Log.e("viewType", "==" + viewType);

        switch (viewType) {

            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_banner_item, parent, false);
                return new ViewHolderBanner(view);
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_discount_banner, parent, false);
                return new ViewHolderDiscount(view);
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_collecttion_list_item, parent, false);
                return new ViewHolderCollection(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_feedback_layout, parent, false);
                return new ViewHolderFeedback(view);

            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_recent_layout, parent, false);
                return new ViewHolderRecent(view);
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recent, parent, false);
                return new ViewHolderProduct(view);
        }
        return new ViewHolderBanner(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderBanner) {
            String url = list.get(position).getImage().replace("\\", "");
            ViewHolderBanner viewHolderBanner = (ViewHolderBanner) holder;

            Log.e("positionposition", "==" + url);
            switch (position) {
                case 0:
                    Glide.with(context).load(R.drawable.baby_product_drink_banner).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.loading_grey).thumbnail(0.1f).animate(MyUtils.getAnimation()).into(viewHolderBanner.img_banner);

                    break;
                case 2:
                    Glide.with(context).load(R.drawable.bar_soap).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.loading_grey).thumbnail(0.1f).animate(MyUtils.getAnimation()).into(viewHolderBanner.img_banner);
                    viewHolderBanner.img_banner.setVisibility(View.GONE);
                    break;

                case 4:
                    Glide.with(context).load(R.drawable.breverage_banner).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.loading_grey).thumbnail(0.1f).animate(MyUtils.getAnimation()).into(viewHolderBanner.img_banner);

                    break;

                case 5:
                    Glide.with(context).load(R.drawable.baby_care).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.loading_grey).thumbnail(0.1f).animate(MyUtils.getAnimation()).into(viewHolderBanner.img_banner);
                    break;
            }
            //Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.loading_grey).thumbnail(0.1f).animate(MyUtil.getAnimation()).into(viewHolderBanner.img_banner);
            viewHolderBanner.img_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CatalogueFragment fragmentCatalogue = new CatalogueFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("categoryId", "2");
                    fragmentCatalogue.setArguments(bundle);
                    MyUtils.addFragmentBackHome(layPager, fragmentCatalogue, context);

                }
            });
        } else if (holder instanceof ViewHolderDiscount) {
            String url = list.get(position).getImage().replace("\\", "");
            ViewHolderDiscount viewHolderDiscount = (ViewHolderDiscount) holder;
            viewHolderDiscount.tvDiscount.setText(list.get(position).getText() + "% OFF");
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.loading_grey).thumbnail(0.1f).animate(MyUtils.getAnimation()).into(viewHolderDiscount.img_banner);
        } else if (holder instanceof ViewHolderCollection) {
            ViewHolderCollection viewHolderCollection = (ViewHolderCollection) holder;
            //SetData(viewHolderCollection, "", list.get(position).getText(), list.get(position).getId());
        } else if (holder instanceof ViewHolderProduct) {
            ViewHolderProduct viewHolderProduct = (ViewHolderProduct) holder;
            viewHolderProduct.tvTitle.setText(list.get(position).getText());
            setUpRecyclerView(viewHolderProduct, position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() + 1 == position)
            return feedback;
        else if (list.size() == position)
            return recent;
        else if (list.get(position).getType().equals("banner"))
            return banner;
        else if (list.get(position).getType().equals("discount"))
            return discount;
        else if (list.get(position).getType().equals("collection"))
            return collection;
        else if (list.get(position).getType().equals("product"))
            return product;
        else
            return 0;
    }

    @Override
    public int getItemCount() {
        return list.size() + 2;
    }


    public class ViewHolderBanner extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_banner;

        public ViewHolderBanner(View itemView) {
            super(itemView);
            img_banner = (ImageView) itemView.findViewById(R.id.img_banner);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.itemClicked(v, getPosition());
        }
    }

    public class ViewHolderDiscount extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_banner;
        TextView tvDiscount;

        public ViewHolderDiscount(View itemView) {
            super(itemView);
            img_banner = (ImageView) itemView.findViewById(R.id.img_banner);
            tvDiscount = (TextView) itemView.findViewById(R.id.tvDiscount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.itemClicked(v, getPosition());
        }
    }

    private void setUpRecyclerView(final ViewHolderProduct viewHolderProduct, final int pos) {
                /*JSONArray arr = null;
        try {
            arr = new JSONArray(list.get(pos).getId().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
       // listProduct = ProductController.getAllProductsFromDatabase(context);
        Log.e("lislistProducttProduct", "==" + list.get(pos).getId().toString());

/*
        ProductController.getCategoryProducts(context, list.get(pos).getId().toString(), new ApiCallBack() {
            @Override
            public void onSuccess(Object success) {
                listProduct = (ArrayList<ProductModel>) success;
                Log.e("lislistProducttProduct", "==" + listProduct.size());
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                viewHolderProduct.recycler.setLayoutManager(layoutManager);
                ProductHomeAdapter adapter = new ProductHomeAdapter(context, listProduct);
                viewHolderProduct.recycler.setAdapter(adapter);

            }

            @Override
            public void onError(String error) {

            }
        });
*/

    }

    public class ViewHolderProduct extends RecyclerView.ViewHolder {
        RecyclerView recycler;
        TextView tvTitle;

        public ViewHolderProduct(View itemView) {
            super(itemView);
            recycler = (RecyclerView) itemView.findViewById(R.id.recent_product_list);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

        }
    }

    public class ViewHolderBottom extends RecyclerView.ViewHolder {
        RecyclerView recycler;
        TextView tvTitle;

        public ViewHolderBottom(View itemView) {
            super(itemView);
            recycler = (RecyclerView) itemView.findViewById(R.id.recent_product_list);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    public class ViewHolderCollection extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvCollection;
        ImageView imgProduct1, imgProduct2, imgProduct3, imgProduct4;
        TextView tvName1, tvName2, tvName3, tvName4;
        TextView tvOldPrice1, tvOldPrice2, tvOldPrice3, tvOldPrice4;
        TextView tvSellingPrice1, tvSellingPrice2, tvSellingPrice3, tvSellingPrice4;

        public ViewHolderCollection(View view) {
            super(view);

            tvCollection = (TextView) view.findViewById(R.id.tvCollection);
            final CardView btnViewAll = (CardView) view.findViewById(R.id.btnViewAll);
            imgProduct1 = (ImageView) view.findViewById(R.id.imgProduct1);
            imgProduct2 = (ImageView) view.findViewById(R.id.imgProduct2);
            imgProduct3 = (ImageView) view.findViewById(R.id.imgProduct3);
            imgProduct4 = (ImageView) view.findViewById(R.id.imgProduct4);
            tvName1 = (TextView) view.findViewById(R.id.tvName1);
            tvName2 = (TextView) view.findViewById(R.id.tvName2);
            tvName3 = (TextView) view.findViewById(R.id.tvName3);
            tvName4 = (TextView) view.findViewById(R.id.tvName4);
            tvOldPrice1 = (TextView) view.findViewById(R.id.tvOldPrice1);
            tvOldPrice2 = (TextView) view.findViewById(R.id.tvOldPrice2);
            tvOldPrice3 = (TextView) view.findViewById(R.id.tvOldPrice3);
            tvOldPrice4 = (TextView) view.findViewById(R.id.tvOldPrice4);
            tvSellingPrice1 = (TextView) view.findViewById(R.id.tvSellingPrice1);
            tvSellingPrice2 = (TextView) view.findViewById(R.id.tvSellingPrice2);
            tvSellingPrice3 = (TextView) view.findViewById(R.id.tvSellingPrice3);
            tvSellingPrice4 = (TextView) view.findViewById(R.id.tvSellingPrice4);
            tvOldPrice1.setPaintFlags(tvOldPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvOldPrice2.setPaintFlags(tvOldPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvOldPrice3.setPaintFlags(tvOldPrice3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvOldPrice4.setPaintFlags(tvOldPrice4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            btnViewAll.setOnClickListener(this);


            imgProduct1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  // callDetailActivity(listProduct.get(getPosition()).getProduct_ID());
                }
            });
            imgProduct2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  callDetailActivity(listProduct.get(getPosition()).getProduct_ID());
                }
            });
            imgProduct3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  callDetailActivity(listProduct.get(getPosition()).getProduct_ID());
                }
            });
            imgProduct4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // callDetailActivity(listProduct.get(getPosition()).getProduct_ID());
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.itemClicked(v, getPosition());
        }
    }

    private void callDetailActivity(String productId) {
       /* ProductModel productModel = (ProductModel) ((ImageView) v).getTag();
        // MyApplication.productModel = productModel;
        context.startActivity(new Intent(context, ProductsDetailActivity.class));*/

        Intent intent = new Intent(context, ProductsDetailActivity.class);
        intent.putExtra("productId", productId);
        context.startActivity(intent);
    }

    public class ViewHolderFeedback extends RecyclerView.ViewHolder {
        public ViewHolderFeedback(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolderRecent extends RecyclerView.ViewHolder {
        public ViewHolderRecent(View itemView) {
            super(itemView);
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
