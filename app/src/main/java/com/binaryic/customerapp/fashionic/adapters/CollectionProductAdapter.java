package com.binaryic.customerapp.fashionic.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.models.ProductModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by HP on 12-Apr-17.
 */

public class CollectionProductAdapter extends RecyclerView.Adapter<CollectionProductAdapter.ViewHolder> {
    public ArrayList<ProductModel> list;
    Context context;
    ClickListener clickListener;
    public CollectionProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.get(position).getProduct_Image_Array().size() > 0)
            Glide.with(context).load(list.get(position).getProduct_Image_Array().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).into(holder.imgProduct);
        holder.tvOldPrice.setText("Rs " + list.get(position).getDiscount_Price());
        holder.tvSellingPrice.setText("Rs "+ list.get(position).getSelling_Price());
        holder.tv_Name.setText(list.get(position).getProduct_Name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_product1;
        CardView card_view;
        ImageView imgProduct;
        TextView tv_Name,tvOldPrice,tvSellingPrice;
        public ViewHolder(View view) {
            super(view);
            card_view = (CardView) view.findViewById(R.id.card_view);
            rl_product1 = (RelativeLayout) view.findViewById(R.id.rl_product1);
            imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
            tv_Name = (TextView) view.findViewById(R.id.tv_Name);
            tvOldPrice = (TextView) view.findViewById(R.id.tvOldPrice);
            tvSellingPrice = (TextView) view.findViewById(R.id.tvSellingPrice);
            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.ItemClicked(v, getPosition());
                    }
                }
            });
        }
    }
    public interface ClickListener {
        public void ItemClicked(View view, int position);
    }
}
