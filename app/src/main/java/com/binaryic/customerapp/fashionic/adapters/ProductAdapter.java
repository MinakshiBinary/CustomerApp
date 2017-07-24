package com.binaryic.customerapp.fashionic.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.models.ProductModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Asd on 17-09-2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public ArrayList<ProductModel> list;
    Context context;
    ClickListener clickListener;
    private ScrollListener scrollListener;

    public ProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.e("notify", "notifyAdapter");
        if ((list.size() - 1) == position) {
            if (scrollListener != null)
                scrollListener.scrolled(position + 1);
        }
        Glide.with(context).load(list.get(position).getProduct_Image_Array().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).animate(MyUtils.getAnimation()).placeholder(R.drawable.loading_grey).into(holder.imgProduct);
            /*if (list.get(position).getDiscountModels() != null) {
                holder.tvOldPrice.setText("` " + list.get(position).getDiscount_price());
                MyUtil.setMargins(holder.tvOldPrice, 0, 0, 50, 0);

                holder.tvSellingPrice.setText("` " + list.get(position).getOriginal_price());
            } else {
                holder.tvOldPrice.setVisibility(View.GONE);
                holder.tvSellingPrice.setText("` " + list.get(position).getOriginal_price());
            }*/
        holder.tvSellingPrice.setText("Rs " + list.get(position).getSelling_Price());
/*

        if (list.get(position).getIsFav())
            holder.imgfav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_home_selected));
        else
            holder.imgfav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite));
*/


        holder.tvName.setText(list.get(position).getProduct_Name());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

    public interface ScrollListener {
        public void scrolled(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgfav, imgProduct;
        TextView tvOldPrice;
        TextView tvSellingPrice, tvName;
        private TextView tv_Product_Count;
        private TextView tv_Plus;
        private TextView tv_Minus;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgfav = (ImageView) itemView.findViewById(R.id.imgfav);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            tvOldPrice = (TextView) itemView.findViewById(R.id.tvOldPrice);
            tv_Product_Count = (TextView) itemView.findViewById(R.id.tv_Product_Count);
            tv_Minus = (TextView) itemView.findViewById(R.id.tv_Minus);
            tv_Plus = (TextView) itemView.findViewById(R.id.tv_Plus);
            tvSellingPrice = (TextView) itemView.findViewById(R.id.tvSellingPrice);
            tvName = (TextView) itemView.findViewById(R.id.tv_Name);
            imgfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if (list.get(getPosition()).getIsFav())
                        imgfav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite));
                    else
                        imgfav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_home_selected));
                    if (clickListener != null) {
                        clickListener.itemClicked(v, getPosition());
                    }*/
                }
            });

            tv_Plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_Product_Count.setText(MyUtils.increamentCount(tv_Product_Count.getText().toString().trim()));

                }
            });
            tv_Minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_Product_Count.setText(MyUtils.decrementCount(tv_Product_Count.getText().toString().trim()));

                }
            });
            tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }


}

