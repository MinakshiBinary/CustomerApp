package com.binaryic.customerapp.fashionic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.models.FilterModel;

import java.util.ArrayList;


/**
 * Created by Asd on 20-09-2016.
 */
public class MultiFilterAdapter  extends RecyclerView.Adapter<MultiFilterAdapter.ViewHolder> {
    public ArrayList<FilterModel> list;
    Context context;
    String mode;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private ClickListener clickListener;
    public MultiFilterAdapter(ArrayList<FilterModel> list, String mode, Context context){
        this.list = list;
        this.mode = mode;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_filter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvFilterItem.setText(list.get(position).getName());
        holder.cbFilterItem.setChecked(list.get(position).isCheck());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFilterItem;
        CheckBox cbFilterItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvFilterItem = (TextView) itemView.findViewById(R.id.tvFilterItem);
            cbFilterItem = (CheckBox) itemView.findViewById(R.id.cbFilterItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.ItemClickedMultiple(v,getPosition(),mode);
            }
        }
    }
    public interface ClickListener {
        public void ItemClickedMultiple(View view, int position, String mode);
    }
}
