package com.binaryic.customerapp.fashionic.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.MainActivity;
import com.binaryic.customerapp.fashionic.activities.ProductListActivity;
import com.binaryic.customerapp.fashionic.models.MenuModel;

import java.util.ArrayList;

/**
 * Created by Asd on 17-09-2016.
 */
public class HomeDrawerAdapter extends RecyclerView.Adapter<HomeDrawerAdapter.ViewHolder> {
    Context context;
    ArrayList<MenuModel> drawerList;
    private Clicklistener clicklistener;

    public HomeDrawerAdapter(Context context, ArrayList<MenuModel> drawerList) {
        this.context = context;
        this.drawerList = drawerList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_product_item, parent, false);
        return new ViewHolder(view);
    }

    public void setOnClickListenr(Clicklistener clickListenr) {
        this.clicklistener = clickListenr;
    }


    public interface Clicklistener {
        public void onItemClickDrawer(View view, int position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.e("getgetIssHeader", "==" + drawerList.get(position).getIsHeader());

        if (drawerList.get(position).getIsHeader()) {
            holder.lblHeader.setText(drawerList.get(position).getTitle());
            holder.lblOption.setVisibility(View.GONE);
            holder.layHeader.setVisibility(View.VISIBLE);
           /* if (!drawerList.get(position).isVisible())
                holder.lblHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_drop, 0);
            else
                holder.lblHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_drop, 0);
*/
        } else {
/*

            if (drawerList.get(position).isVisible())
                holder.lblOption.setVisibility(View.VISIBLE);
            else
                holder.lblOption.setVisibility(View.GONE);
*/


            holder.layHeader.setVisibility(View.GONE);
            holder.lblOption.setText(drawerList.get(position).getTitle());
        }

/*

        if (drawerList.get(position).getIsHeader()) {
            holder.lblHeader.setText(drawerList.get(position).getTitle());
           holder.lblOption.setVisibility(View.GONE);
            holder.layHeader.setVisibility(View.VISIBLE);
            if (!drawerList.get(position).isVisible())
                holder.lblHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_drop, 0);
            else
                holder.lblHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_drop, 0);
        } else {
            Log.e("getgisVisibleder", "==" + drawerList.get(position).isVisible());

            if (drawerList.get(position).isVisible())
                holder.lblOption.setVisibility(View.VISIBLE);
            else
                holder.lblOption.setVisibility(View.GONE);


            holder.layHeader.setVisibility(View.GONE);
            holder.lblOption.setText(drawerList.get(position).getTitle());
        }
*/


    }

    @Override
    public int getItemCount() {
        return drawerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblHeader, lblOption;
        LinearLayout layHeader;

        @Override
        public void onClick(View view) {
            if (clicklistener != null) {
                clicklistener.onItemClickDrawer(view, getPosition());
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            lblHeader = (TextView) itemView.findViewById(R.id.lblHeader);
            lblOption = (TextView) itemView.findViewById(R.id.lblOption);
            layHeader = (LinearLayout) itemView.findViewById(R.id.ll_drawerItem);
            lblHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("collection", drawerList.get(getPosition()).getTitle());
                    intent.putExtra("collection_id", drawerList.get(getPosition()).getCollection_id());
                    context.startActivity(intent);

                }
            });

            lblOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("collection", drawerList.get(getPosition()).getTitle());
                    intent.putExtra("collection_id", drawerList.get(getPosition()).getCollection_id());
                    context.startActivity(intent);

                }
            });
        }
    }
}
