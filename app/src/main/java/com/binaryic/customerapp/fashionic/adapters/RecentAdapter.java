package com.binaryic.customerapp.fashionic.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
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
 * Created by Asd on 04-10-2016.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductModel> list;
    ClickListener clickListener;

    public RecentAdapter(Context context, ArrayList<ProductModel> list) {
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

            Glide.with(context).load(list.get(position).getProduct_Image_Array().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).animate(MyUtils.getAnimation()).placeholder(R.drawable.loading_grey).into(holder.imgProduct);

/*
        Log.e("posipositiontion","=="+list.get(position).getDiscountModels());
*/
           /* if (list.get(position).getDiscountModels() != null) {
                holder.tvOldPrice.setText("` " + list.get(position).getDiscount_price());
                holder.tvSellingPrice.setText("` " + list.get(position).getOriginal_price());
            } else {
                holder.tvOldPrice.setVisibility(View.GONE);
                holder.tvSellingPrice.setText("` " + list.get(position).getOriginal_price());
            }*/
        holder.tvSellingPrice.setText("` " + list.get(position).getSelling_Price());

        holder.tvName.setText(list.get(position).getProduct_Name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgProduct;
        TextView tvOldPrice;
        TextView tvSellingPrice, tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            tvOldPrice = (TextView) itemView.findViewById(R.id.tvOldPrice);
            tvSellingPrice = (TextView) itemView.findViewById(R.id.tvSellingPrice);
            tvName = (TextView) itemView.findViewById(R.id.tv_Name);
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
