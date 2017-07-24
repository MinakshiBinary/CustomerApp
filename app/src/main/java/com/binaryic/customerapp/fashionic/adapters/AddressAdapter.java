package com.binaryic.customerapp.fashionic.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.models.AddressModel;

import java.util.ArrayList;


/**
 * Created by Asd on 10-10-2016.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    public ArrayList<AddressModel> list;
    Context context;
    ClickListener clickListener;

    public AddressAdapter(Context context, ArrayList<AddressModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_Name.setText(list.get(position).getFirstName()+" "+list.get(position).getLastName());
        holder.tv_Address.setText(list.get(position).getCustomer_Address() + ", " + list.get(position).getCity() + ", " + list.get(position).getState() +", " + list.get(position).getContry());
        holder.tv_ContactNo.setText(list.get(position).getMobileNumber());
        holder.tv_Pincode.setText(list.get(position).getPincode());
        Log.e("AddressAdapter","address id=="+list.get(position).getAddress_Id());
        Log.e("AddressAdapter","is default address=="+list.get(position).isDefaultAddress());
        holder.chk_Default.setChecked(list.get(position).isDefaultAddress());
        if (list.get(position).isDefaultAddress())
            holder.ll_Remove.setVisibility(View.GONE);
        else
            holder.ll_Remove.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_Name, tv_Address, tv_Pincode, tv_ContactNo;
        AppCompatCheckBox chk_Default;
        LinearLayout ll_Remove, ll_Edit;

        public ViewHolder(View view) {
            super(view);
            tv_Name = (TextView) view.findViewById(R.id.tv_Name);
            tv_Address = (TextView) view.findViewById(R.id.tv_Address);
            tv_Pincode = (TextView) view.findViewById(R.id.tv_Pincode);
            tv_ContactNo = (TextView) view.findViewById(R.id.tv_ContactNo);
            chk_Default = (AppCompatCheckBox) view.findViewById(R.id.chk_Default);
            ll_Remove = (LinearLayout) view.findViewById(R.id.ll_Remove);
            ll_Edit = (LinearLayout) view.findViewById(R.id.ll_Edit);
            chk_Default.setOnClickListener(this);
            ll_Remove.setOnClickListener(this);
            ll_Edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.itemClicked(v, getPosition());
        }
    }
}
