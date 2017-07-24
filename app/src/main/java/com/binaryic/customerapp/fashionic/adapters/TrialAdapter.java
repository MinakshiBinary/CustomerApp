package com.binaryic.customerapp.fashionic.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by minakshi on 13/4/17.
 */

public class TrialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int trial = 0;
    int demo = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TrialHolder extends RecyclerView.ViewHolder{
        public TrialHolder(View itemView) {
            super(itemView);
        }
    }
    public class DemoHolder extends RecyclerView.ViewHolder{
        public DemoHolder(View itemView) {
            super(itemView);
        }
    }
}
