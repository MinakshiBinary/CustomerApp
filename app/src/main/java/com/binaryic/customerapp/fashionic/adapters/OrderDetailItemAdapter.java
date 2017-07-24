package com.binaryic.customerapp.fashionic.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.models.OrderProductModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by HP on 14-Mar-17.
 */

public class OrderDetailItemAdapter extends RecyclerView.Adapter<OrderDetailItemAdapter.ViewHolder> {
    public ArrayList<OrderProductModel> list;
    Activity context;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    ClickListener clickListener;

    public OrderDetailItemAdapter(Activity context, ArrayList<OrderProductModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_price.setText(list.get(position).getSelling_price());
        holder.tv_product_name.setText(list.get(position).getProduct_name());
        holder.tv_order_no.setText("Order No " + list.get(position).getSub_order_id());
        holder.tv_qty.setText("Qty " + list.get(position).getProduct_quantity());
        holder.tv_status.setText(list.get(position).getStatus());
        if (list.get(position).getStatus().toUpperCase().equals("DELIVERED")||list.get(position).getStatus().toUpperCase().equals("CANCELLED")) {
            holder.ll_delivery.setVisibility(View.VISIBLE);
            holder.ll_action.setVisibility(View.GONE);
            holder.tv_delivery_date.setText(list.get(position).getDelivery_date());
        } else {
            holder.ll_delivery.setVisibility(View.GONE);
            holder.ll_action.setVisibility(View.VISIBLE);
        }
        if (!list.get(position).getProduct_image().equals(""))
            Glide.with(context).load(list.get(position).getProduct_image()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.logo).thumbnail(0.1f).into(holder.iv_product);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_product, iv_remove;
        TextView tv_product_name, tv_order_no, tv_qty, tv_price, tv_status, tv_delivery_date;
        LinearLayout ll_delivery, ll_action;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_product = (ImageView) itemView.findViewById(R.id.iv_product);
            iv_remove = (ImageView) itemView.findViewById(R.id.iv_remove);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_order_no = (TextView) itemView.findViewById(R.id.tv_order_no);
            tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_delivery_date = (TextView) itemView.findViewById(R.id.tv_delivery_date);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            ll_delivery = (LinearLayout) itemView.findViewById(R.id.ll_delivery);
            ll_action = (LinearLayout) itemView.findViewById(R.id.ll_action);
            iv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtils.showMessageBox("Are you sure, You want to remove product ?", "Yes", "No, Thanks", true, context);
                    MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUtils.msgDialog.dismiss();
                            Toast.makeText(context, "Product remove", Toast.LENGTH_SHORT).show();
                            if (clickListener != null)
                                clickListener.onClicked(v, getPosition());
                        }
                    });
                    MyUtils.btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUtils.msgDialog.dismiss();
                        }
                    });
                }
            });
        }
    }

    public interface ClickListener {
        public void onClicked(View v, int position);
    }

}
