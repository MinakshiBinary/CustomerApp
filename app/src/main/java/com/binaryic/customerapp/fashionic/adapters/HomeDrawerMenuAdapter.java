package com.binaryic.customerapp.fashionic.adapters;

import android.app.Activity;
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
import com.binaryic.customerapp.fashionic.activities.ProductListActivity;
import com.binaryic.customerapp.fashionic.models.CategoryModel;

import java.util.ArrayList;

import static com.binaryic.customerapp.fashionic.activities.MainActivity.drawer;

public class HomeDrawerMenuAdapter extends RecyclerView.Adapter<HomeDrawerMenuAdapter.ViewHolder> {
    Activity context;
    ArrayList<CategoryModel> drawerList;
    private Clicklistener clicklistener;

    public HomeDrawerMenuAdapter(Activity context, ArrayList<CategoryModel> drawerList) {
        this.context = context;
        this.drawerList = drawerList;
    }

    public void setOnClickListenr(Clicklistener clickListenr) {
        this.clicklistener = clickListenr;
    }


    public interface Clicklistener {
        public void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_product_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.e("drawerList", "==" + drawerList.get(position).toString());
        holder.tv_Header.setText(drawerList.get(position).getCategory_Name().toString());
    }

    @Override
    public int getItemCount() {
        return drawerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout ll_drawerItem;
        private TextView tv_Header;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_drawerItem = (LinearLayout) itemView.findViewById(R.id.ll_drawerItem);
            tv_Header = (TextView) itemView.findViewById(R.id.tv_Header);
            tv_Header.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }

            Intent intent = new Intent(context, ProductListActivity.class);
            intent.putExtra("categoryId", drawerList.get(getPosition()).getCategory_Id().toString());
            context.startActivity(intent);


            /*CatalogueFragment fragment = new CatalogueFragment();
            Bundle bundle = new Bundle();
            bundle.putString("categoryId", drawerList.get(getPosition()).getCategory_Id().toString());
            fragment.setArguments(bundle);
            MyUtils.addFragmentBackHome(layPager, fragment, context);*/
        }



           /* Log.e("onClick", "onClick");
            Log.e("clicklistener", "clicklistener=" + clicklistener);

            if (clicklistener != null) {
                clicklistener.onItemClick(view, getPosition());
            }*/

    }
}