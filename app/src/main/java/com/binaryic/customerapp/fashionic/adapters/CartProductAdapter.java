package com.binaryic.customerapp.fashionic.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.ProductController;
import com.binaryic.customerapp.fashionic.models.ProductModelQty;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Asd on 16-09-2016.
 */
public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
    public ArrayList<ProductModelQty> list;
    Activity context;
    String quantity = "";
    ClickListener clickListener;

    public CartProductAdapter(Activity context, ArrayList<ProductModelQty> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (list.get(position).getProductModel().getProduct_Image_Array().size() != 0)
            Glide.with(context).load(list.get(position).getProductModel().getProduct_Image_Array().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).animate(MyUtils.getAnimation()).placeholder(R.drawable.loading_grey).into(holder.iv_ProductImage);
     /* if (list.get(position).getProductModel().getDiscountModels() != null) {
            holder.tv_OldPrice.setText((Double.valueOf(list.get(position).getProductModel().getDiscount_price()).intValue() * list.get(position).getQty()) + "");
            holder.tv_Price.setText((Double.valueOf(list.get(position).getProductModel().getOriginal_price()).intValue() * list.get(position).getQty()) + "");
            holder.ll_OldPrice.setVisibility(View.VISIBLE);
        } else {
            holder.tv_OldPrice.setText("");
            holder.ll_OldPrice.setVisibility(View.GONE);
            holder.tv_Price.setText((Double.valueOf(list.get(position).getProductModel().getOriginal_price()).intValue() * list.get(position).getQty()) + "");
        }
*/
        holder.tv_Price.setText((Double.valueOf(list.get(position).getProductModel().getSelling_Price()).intValue() * list.get(position).getQty()) + "");

        quantity = ProductController.getProductQuantity(context, list.get(position).getProductModel().getProduct_ID());
        Log.e("CartProductAdapter", "product quantity==" + quantity);

        switch (quantity) {
            case "4":

                holder.tv_QtyFifth.setVisibility(View.GONE);
                break;
            case "3":

                holder.tv_QtyForth.setVisibility(View.GONE);
                holder.tv_QtyFifth.setVisibility(View.GONE);
                break;
            case "2":

                holder.tv_QtyThird.setVisibility(View.GONE);
                holder.tv_QtyForth.setVisibility(View.GONE);
                holder.tv_QtyFifth.setVisibility(View.GONE);
                break;
            case "1":

                holder.tv_QtySecond.setVisibility(View.GONE);
                holder.tv_QtyThird.setVisibility(View.GONE);
                holder.tv_QtyForth.setVisibility(View.GONE);
                holder.tv_QtyFifth.setVisibility(View.GONE);
                break;

            case "0":
                holder.tv_QtyFirst.setVisibility(View.GONE);
                holder.tv_QtySecond.setVisibility(View.GONE);
                holder.tv_QtyThird.setVisibility(View.GONE);
                holder.tv_QtyForth.setVisibility(View.GONE);
                holder.tv_QtyFifth.setVisibility(View.GONE);
                break;

            case "-1":

                holder.tv_QtyFirst.setVisibility(View.GONE);
                holder.tv_QtySecond.setVisibility(View.GONE);
                holder.tv_QtyThird.setVisibility(View.GONE);
                holder.tv_QtyForth.setVisibility(View.GONE);
                holder.tv_QtyFifth.setVisibility(View.GONE);
                break;
        }

        holder.tv_ProductName.setText(list.get(position).getProductModel().getProduct_Name());
        holder.tv_Qty.setText((int)list.get(position).getQty() + "");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void dropDownAnimation(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", -40, 0),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1)

        );
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(350);

        animatorSet.start();
    }

    private void dropDownAnimationReverse(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 0, -40),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0)

        );
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(350);

        animatorSet.start();
    }

    private void closeQty(final View layQty) {
        dropDownAnimationReverse(layQty);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {


                }
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layQty.setVisibility(View.GONE);
                    }
                });

            }
        }).start();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position, int Qty);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_ProductImage, iv_ChangeQty;
        LinearLayout ll_Remove, ll_OldPrice, ll_Qty;
        TextView tv_ProductName, tv_OldPrice, tv_Price, tv_Qty;
        TextView tv_QtyFirst, tv_QtySecond, tv_QtyThird, tv_QtyForth, tv_QtyFifth;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_ProductImage = (ImageView) itemView.findViewById(R.id.iv_ProductImage);
            iv_ChangeQty = (ImageView) itemView.findViewById(R.id.iv_ChangeQty);
            ll_Remove = (LinearLayout) itemView.findViewById(R.id.ll_Remove);
            ll_Qty = (LinearLayout) itemView.findViewById(R.id.ll_Qty);
            ll_OldPrice = (LinearLayout) itemView.findViewById(R.id.ll_OldPrice);
            tv_ProductName = (TextView) itemView.findViewById(R.id.tv_ProductName);
            tv_OldPrice = (TextView) itemView.findViewById(R.id.tv_OldPrice);
            tv_Price = (TextView) itemView.findViewById(R.id.tv_Price);
            tv_Qty = (TextView) itemView.findViewById(R.id.tv_Qty);
            tv_QtyFirst = (TextView) itemView.findViewById(R.id.tv_QtyFirst);
            tv_QtySecond = (TextView) itemView.findViewById(R.id.tv_QtySecond);
            tv_QtyThird = (TextView) itemView.findViewById(R.id.tv_QtyThird);
            tv_QtyForth = (TextView) itemView.findViewById(R.id.tv_QtyForth);
            tv_QtyFifth = (TextView) itemView.findViewById(R.id.tv_QtyFifth);
            ll_Remove.setOnClickListener(this);
            tv_QtyFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeQty(ll_Qty);
                    tv_Qty.setText("1");
                    if (clickListener != null)
                        clickListener.itemClicked(v, getPosition(), 1);
                }
            });
            tv_QtySecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeQty(ll_Qty);
                    tv_Qty.setText("2");
                    if (clickListener != null)
                        clickListener.itemClicked(v, getPosition(), 2);
                }
            });
            tv_QtyThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeQty(ll_Qty);
                    tv_Qty.setText("3");
                    if (clickListener != null)
                        clickListener.itemClicked(v, getPosition(), 3);
                }
            });
            tv_QtyForth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeQty(ll_Qty);
                    tv_Qty.setText("4");
                    if (clickListener != null)
                        clickListener.itemClicked(v, getPosition(), 4);
                }
            });
            tv_QtyFifth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeQty(ll_Qty);
                    tv_Qty.setText("5");
                    if (clickListener != null)
                        clickListener.itemClicked(v, getPosition(), 5);
                }
            });
            iv_ChangeQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ll_Qty.getVisibility() == View.VISIBLE) {
                        dropDownAnimationReverse(ll_Qty);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Thread.sleep(350);
                                } catch (InterruptedException e) {


                                }
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ll_Qty.setVisibility(View.GONE);
                                    }
                                });

                            }
                        }).start();

                    } else {
                        ll_Qty.setVisibility(View.VISIBLE);
                        dropDownAnimation(ll_Qty);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.itemClicked(v, getPosition(), 0);
        }
    }
}
