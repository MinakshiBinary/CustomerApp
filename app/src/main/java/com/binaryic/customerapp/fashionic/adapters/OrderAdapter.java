package com.binaryic.customerapp.fashionic.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.models.OrderModel;
import com.binaryic.customerapp.fashionic.models.OrderProductModel;

import java.util.ArrayList;


/**
 * Created by HP on 14-Mar-17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ArrayList<OrderModel> list;
    ClickListener clickListener;
    Activity context;

    public OrderAdapter(ArrayList<OrderModel> orderModels, Activity context) {
        this.list = orderModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_status.setText(list.get(position).getStatus());
        holder.tv_order_no.setText("Order No : "+list.get(position).getOrder_no());
        holder.tv_date.setText("Placed On "+list.get(position).getCreated_date());
        holder.tv_total.setText(list.get(position).getTotal_amount());
        holder.tv_items.setText(list.get(position).getProductModels().size() + " items");
        holder.tv_delivery_date.setText(list.get(position).getDelivery_date());
        if(list.get(position).getStatus().toString().toUpperCase().equals("PENDING")){
            holder.ll_delivery.setVisibility(View.GONE);
            holder.ll_cancel.setVisibility(View.VISIBLE);
        }else{
            holder.ll_delivery.setVisibility(View.VISIBLE);
            holder.ll_cancel.setVisibility(View.GONE);
        }
        if(list.get(position).getStatus().toString().toUpperCase().equals("PENDING")){
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.temp_color));
        }else if(list.get(position).getStatus().toString().toUpperCase().equals("DELIVERED")){
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }else if(list.get(position).getStatus().toString().toUpperCase().equals("CANCELLED")){
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
        }
        setUpRecyclerViewImages(list.get(position).getProductModels(),holder.rv_order_product_images);
    }
    private void setUpRecyclerViewImages(ArrayList<OrderProductModel> images, RecyclerView rv){
        OrderProductImageAdapter orderProductImageAdapter = new OrderProductImageAdapter(context,images);
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(orderProductImageAdapter);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_view_detail,tv_status,tv_order_no,tv_date,tv_total,tv_items,tv_delivery_date,tv_cancel;
        RecyclerView rv_order_product_images;
        LinearLayout ll_delivery,ll_cancel;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_view_detail = (TextView) itemView.findViewById(R.id.tv_view_detail);
            ll_delivery = (LinearLayout) itemView.findViewById(R.id.ll_delivery);
            ll_cancel = (LinearLayout) itemView.findViewById(R.id.ll_cancel);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_order_no = (TextView) itemView.findViewById(R.id.tv_order_no);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_total = (TextView) itemView.findViewById(R.id.tv_total);
            tv_items = (TextView) itemView.findViewById(R.id.tv_items);
            tv_delivery_date = (TextView) itemView.findViewById(R.id.tv_delivery_date);
            tv_cancel = (TextView) itemView.findViewById(R.id.tv_cancel);
            rv_order_product_images = (RecyclerView) itemView.findViewById(R.id.rv_order_product_images);
            tv_view_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClicked(v, getPosition());
                    }
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClicked(v, getPosition());
                    }


                   /*
                    MyUtils.showMessageBox("Are you sure, You want to cance Order ?","Yes","No, Thanks",true,context);
                    MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUtils.msgDialog.dismiss();
                            Toast.makeText(context, "Cancel Order", Toast.LENGTH_SHORT).show();
                        }
                    });
                    MyUtils.btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUtils.msgDialog.dismiss();
                        }
                    });*/
                }
            });
        }
    }

    public interface ClickListener {
        public void onClicked(View v, int position);
    }
}
