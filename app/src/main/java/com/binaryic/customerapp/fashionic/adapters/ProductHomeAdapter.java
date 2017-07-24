package com.binaryic.customerapp.fashionic.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.binaryic.customerapp.fashionic.models.ProductModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Asd on 05-10-2016.
 */
public class ProductHomeAdapter extends RecyclerView.Adapter<ProductHomeAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductModel> list;

    public ProductHomeAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e("listlist", "==" + list.get(position).getProduct_Image_Array());
        Log.e("listliaast", "==" + holder.imgProduct);
        if (list.get(position).getProduct_Image_Array()!= null)
        if (list.get(position).getProduct_Image_Array().size() > 0)
            Glide.with(context).load(list.get(position).getProduct_Image_Array().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).animate(MyUtils.getAnimation()).placeholder(R.drawable.loading_grey).into(holder.imgProduct);
           /* if (list.get(position).getDiscountModels() != null) {
                holder.tvOldPrice.setText("` " + list.get(position).getDiscount_price());

                holder.tvSellingPrice.setText("` " + list.get(position).getOriginal_price());

            } else {
                holder.tvOldPrice.setVisibility(View.GONE);
                holder.tvSellingPrice.setText("` " + list.get(position).getOriginal_price());
            }*/
        holder.tvSellingPrice.setText("Rs " + list.get(position).getSelling_Price());

        holder.tvName.setText(list.get(position).getProduct_Name());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductsDetailActivity.class);
                intent.putExtra("productId",  list.get(position).getProduct_ID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        ImageView imgProduct;
        TextView tvOldPrice;
        TextView tvSellingPrice, tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            card_view = (CardView) itemView.findViewById(R.id.card_view);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            tvOldPrice = (TextView) itemView.findViewById(R.id.tvOldPrice);
            tvSellingPrice = (TextView) itemView.findViewById(R.id.tvSellingPrice);
            tvName = (TextView) itemView.findViewById(R.id.tv_Name);
        }


    }
}
