package com.binaryic.customerapp.fashionic.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.custom.TextViewPrimary;
import com.binaryic.customerapp.fashionic.models.CategoryModel;

import java.util.ArrayList;

/**
 * Created by minakshi on 25/1/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Activity context;
    private ArrayList<CategoryModel> array_Data;
    private ClickListener clickListener;

    public CategoryAdapter(Activity context, ArrayList<CategoryModel> array_Data) {
        this.context = context;
        this.array_Data = array_Data;
    }

    public void setClickListner(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_view, parent, false);
        return new CategoryAdapter.CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        Log.e("CategoryAdapter", "Category_Id =" + array_Data.get(position).getCategory_Id());
        holder.tv_Category_Name.setText(array_Data.get(position).getCategory_Name());
    }

    @Override
    public int getItemCount() {
        return array_Data.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        RelativeLayout rl_Category;
        TextViewPrimary tv_Category_Name;
        ImageView iv_Close;

        public CategoryHolder(View itemView) {
            super(itemView);
            iv_Close = (ImageView) itemView.findViewById(R.id.iv_Close);
            tv_Category_Name = (TextViewPrimary) itemView.findViewById(R.id.tv_Category_Name);
            rl_Category = (RelativeLayout) itemView.findViewById(R.id.rl_Category);
            iv_Close.setOnClickListener(this);
        }

        /*public void showMessageBox(String Title, String yesButton, String noButton, Boolean noShow, Context cont) {
            msgDialog = new Dialog(cont);
            msgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            msgDialog.setContentView(R.layout.fragment_dialog);
            WindowManager.LayoutParams wmlp = msgDialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
            wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            msgDialog.setCanceledOnTouchOutside(false);
            final TextView content = (TextView) msgDialog.findViewById(R.id.content);
            btnYes = (TextView) msgDialog.findViewById(R.id.btnDone);
            btnNo = (TextView) msgDialog.findViewById(R.id.btnCancel);
            content.setText(Title);
            btnYes.setText(yesButton);
            btnNo.setText(noButton);
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    msgDialog.dismiss();

                }
            });
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (InternetConnectionDetector.isConnectingToInternet(context, rl_AddCategory, "No Internet Connection")) {
                        Log.e("CategoryAdapter", "Category_Id =" + array_Data.get(getPosition()).getCategory_Id());
                        CategoryController.deleteCategory(context,
                                array_Data.get(getPosition()).getCategory_Id(), new ApiCallBack() {
                                    @Override
                                    public void onSuccess(Object success) {
                                        removeAt(getPosition());
                                        Util.showSnackBar(context, rl_AddCategory, "Product is deleted successfully");
                                        ((MainActivity) context).setUpdrawerRecycleView();
                                    }

                                    @Override
                                    public void onError(String error) {
                                        Log.e("CategoryAdapter", "error=" + error);

                                    }
                                });

                        msgDialog.dismiss();
                    }
                }
            });
            if (noShow) {
                btnNo.setVisibility(ViewStub.VISIBLE);
            } else {
                btnNo.setVisibility(ViewStub.GONE);
            }
            msgDialog.show();
        }

        public void removeAt(int position) {
            array_Data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, array_Data.size());
            if (clickListener != null) {
                clickListener.itemClicked(array_Data.size());
            }
        }
*/
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                /*case R.id.iv_Close:
                    showMessageBox("Do you really want to delete..??", "Yes", "No",
                            true, context);
                    break;*/
            }

        }

        @Override
        public boolean onLongClick(View view) {

            return false;
        }

    }

    public interface ClickListener {
        public void itemClicked(int array_Size);
    }
}
