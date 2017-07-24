package com.binaryic.customerapp.fashionic.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.models.ProductModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


/**
 * Created by abhi on 18/1/17.
 */

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ProductHolder> {
    private Activity context;
    public ArrayList<ProductModel> array_Data;
    ClickListener clickListener;
    private ScrollListener scrollListener;

    public CatalogueAdapter(Activity context, ArrayList<ProductModel> array_Data) {
        this.context = context;
        this.array_Data = array_Data;
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogue_list_view, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, final int position) {
        if ((array_Data.size() - 1) == position) {
            if (scrollListener != null)
                scrollListener.Scrolled(position + 1);
        }
        holder.ll_Catalog.setVisibility(View.VISIBLE);
        holder.tv_Product_Name.setText(array_Data.get(position).getProduct_Name());
        holder.tv_Price.setText("Rs. " + array_Data.get(position).getSelling_Price());
        holder.tv_OldPrice.setText("Rs. " + array_Data.get(position).getDiscount_Price());

        if (array_Data.get(position).getProduct_Image_Array().size() > 0)
            Glide.with(context).load(array_Data.get(position).getProduct_Image_Array().get(0)).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_Product_Image);
        if (array_Data.get(position).isFav())
            holder.iv_Favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_home));
        else
            holder.iv_Favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite));


    }


    @Override
    public int getItemCount() {
        return array_Data.size();
    }

    /*
        private void shareImage(Activity context, int position) {
            try {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    } else {
                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    }
                } else {
                    //  Util.shareApp(context, array_Data.get(position).getProduct_Name(), array_Data.get(position).getSelling_Price(), array_Data.get(position).getProduct_Image_Array().get(0));
                    Util.shareMultipleApp(context, array_Data.get(position).getProduct_Name(), array_Data.get(position).getSelling_Price(), array_Data.get(position).getProduct_Image_Array());
                }
            } catch (Exception e) {
                Log.e(context.getClass().getName(), "ShareImage " + e.getMessage());
            }
        }
    */


    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_Product_Name;
        private TextView tv_OldPrice;
        private TextView tv_Price;
        private ImageView iv_Product_Image;
        private ImageView iv_Favorite;
        private LinearLayout ll_Sell;
        private LinearLayout ll_Catalog;

        public ProductHolder(View itemView) {
            super(itemView);

            tv_OldPrice = (TextView) itemView.findViewById(R.id.et_OldPrice);
            tv_Price = (TextView) itemView.findViewById(R.id.et_Price);
            tv_Product_Name = (TextView) itemView.findViewById(R.id.tv_Product_Name);
            iv_Product_Image = (ImageView) itemView.findViewById(R.id.iv_Product_Image);
            iv_Favorite = (ImageView) itemView.findViewById(R.id.iv_Favorite);
            ll_Sell = (LinearLayout) itemView.findViewById(R.id.ll_Buy);
            ll_Catalog = (LinearLayout) itemView.findViewById(R.id.ll_Catalog);

            ll_Sell.setOnClickListener(this);
            iv_Product_Image.setOnClickListener(this);
            iv_Favorite.setOnClickListener(this);

        }


        public void removeAt(int position) {
            array_Data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, array_Data.size());
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.ll_Buy:
                    // CartController.addCartProductInDatabse(context, array_Data.get(getPosition()).getProduct_ID());
                    if (clickListener != null) {
                        clickListener.itemClicked(view, getPosition());
                    }
                    break;
                case R.id.iv_Product_Image:

                    if (clickListener != null) {
                        clickListener.itemClicked(view, getPosition());
                    }
                    break;
                case R.id.iv_Favorite:

                    if (array_Data.get(getPosition()).isFav())
                        iv_Favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite));
                    else
                        iv_Favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_home));


                    if (clickListener != null) {
                        clickListener.itemClicked(view, getPosition());
                    }

                    break;
            }
        }


    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

    public interface ScrollListener {
        public void Scrolled(int position);
    }
}
