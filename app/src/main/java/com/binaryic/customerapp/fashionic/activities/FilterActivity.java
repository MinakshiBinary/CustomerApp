package com.binaryic.customerapp.fashionic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.adapters.MultiFilterAdapter;
import com.binaryic.customerapp.fashionic.adapters.SingleFilterAdapter;
import com.binaryic.customerapp.fashionic.models.FilterModel;
import com.binaryic.customerapp.fashionic.models.SortModel;

import java.util.ArrayList;

/**
 * Created by Asd on 20-09-2016.
 */
public class FilterActivity extends AppCompatActivity /*implements View.OnClickListener, SingleFilterAdapter.ClickListener, MultiFilterAdapter.ClickListener */{
    boolean isClickOn = true;
    RecyclerView recyclerItems;
    LinearLayout btnSort, ll_SalesPrice, ll_DeliveryCharges, ll_MRP, ll_Size, ll_Color, ll_Category;
    TextView lblSort, tvSortCount, tv_SalesPrice, tv_SalesPriceCount, tv_DeliveryCharges, tv_DeliveryChargesCount, tv_MRP, tv_MRPCount, tv_Size, tv_SizeCount, tv_Color, tv_CategoryCount, tv_Category, tv_ColorCount;
    ArrayList<SortModel> listSort;
    SingleFilterAdapter adapter;
    ArrayList<FilterModel> listSalesPrice;
    MultiFilterAdapter adapterPrice;
    ArrayList<FilterModel> listDeliveryCharges;
    ArrayList<FilterModel> listMRP;
    ArrayList<FilterModel> listSize;
    ArrayList<FilterModel> listColor;
    MultiFilterAdapter adapterDiamond;
    MultiFilterAdapter adapterSize;
    MultiFilterAdapter adapterOccasion;
    MultiFilterAdapter adapterColor;
    ArrayList<String> selectedTag = new ArrayList<>();
    String sortOrder = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        GetExtra();
      //  Init();
    }

    private void GetExtra() {
        try {
            Intent intent = getIntent();
            selectedTag = intent.getStringArrayListExtra("filter");
            sortOrder = intent.getStringExtra("sort");
        } catch (Exception ex) {
        }
    }

    /*private void Init() {
        try {
            TextView tvHeader = (TextView) findViewById(R.id.tv_Header);
            tvHeader.setText("Filter");
            ImageView btnClose = (ImageView) findViewById(R.id.iv_BtnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            AppCompatButton btnClear = (AppCompatButton) findViewById(R.id.btnClear);
            AppCompatButton btnApply = (AppCompatButton) findViewById(R.id.btnApply);
            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedTag = new ArrayList<String>();
                    tv_SizeCount.setText("");
                    tv_SizeCount.setVisibility(View.GONE);
                    tv_SalesPriceCount.setText("");
                    tv_SalesPriceCount.setVisibility(View.GONE);
                    tvSortCount.setText("");
                    tvSortCount.setVisibility(View.GONE);
                    tv_MRPCount.setText("");
                    tv_MRPCount.setVisibility(View.GONE);
                    tv_DeliveryChargesCount.setText("");
                    tv_DeliveryChargesCount.setVisibility(View.GONE);
                    tv_ColorCount.setText("");
                    tv_ColorCount.setVisibility(View.GONE);
                    tv_CategoryCount.setText("");
                    tv_CategoryCount.setVisibility(View.GONE);
                    CreateList();
                    SetUpView();
                    SetUpRecyclerView();
                }
            });
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("filter", selectedTag);
                    intent.putExtra("sort", sortOrder);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            recyclerItems = (RecyclerView) findViewById(R.id.recyclerItems);
            btnSort = (LinearLayout) findViewById(R.id.btnSort);
            ll_SalesPrice = (LinearLayout) findViewById(R.id.ll_SalesPrice);
            ll_DeliveryCharges = (LinearLayout) findViewById(R.id.ll_DeliveryCharges);
            ll_MRP = (LinearLayout) findViewById(R.id.ll_MRP);
            ll_Size = (LinearLayout) findViewById(R.id.ll_Size);
            ll_Color = (LinearLayout) findViewById(R.id.ll_Color);
            ll_Category = (LinearLayout) findViewById(R.id.ll_Category);
            lblSort = (TextView) findViewById(R.id.lblSort);
            tvSortCount = (TextView) findViewById(R.id.tvSortCount);
            tv_SalesPrice = (TextView) findViewById(R.id.tv_SalesPrice);
            tv_SalesPriceCount = (TextView) findViewById(R.id.tv_SalesPriceCount);
            tv_DeliveryCharges = (TextView) findViewById(R.id.tv_DeliveryCharges);
            tv_DeliveryChargesCount = (TextView) findViewById(R.id.tv_DeliveryChargesCount);
            tv_MRP = (TextView) findViewById(R.id.tv_MRP);
            tv_MRPCount = (TextView) findViewById(R.id.tv_MRPCount);
            tv_Size = (TextView) findViewById(R.id.tv_Size);
            tv_SizeCount = (TextView) findViewById(R.id.tv_SizeCount);
            tv_Color = (TextView) findViewById(R.id.tv_Color);
            tv_Category = (TextView) findViewById(R.id.tv_Category);
            tv_CategoryCount = (TextView) findViewById(R.id.tv_CategoryCount);
            tv_ColorCount = (TextView) findViewById(R.id.tv_ColorCount);
            btnSort.setOnClickListener(this);
            ll_SalesPrice.setOnClickListener(this);
            ll_DeliveryCharges.setOnClickListener(this);
            ll_MRP.setOnClickListener(this);
            ll_Size.setOnClickListener(this);
            ll_Color.setOnClickListener(this);
            ll_Category.setOnClickListener(this);
            SetSortList();
            if (selectedTag.size() > 0)
                GetSelectedTag();
            else
                CreateList();
            SetUpRecyclerSort();
            SetUpView();
        } catch (Exception ex) {
        }
    }

    private void SetSortList() {
        if (sortOrder.equals("")) {
            listSort = new ArrayList<>();
            listSort.add(new SortModel("A-Z", false, "&order=title"));
            listSort.add(new SortModel("Z-A", false, "&order=title%20desc"));
            listSort.add(new SortModel("Newest To Oldest", false, "&order=created_at%20asc"));
            listSort.add(new SortModel("Oldest To Newest", false, "&order=created_at%20desc"));
        } else {
            listSort = new ArrayList<>();
            if (sortOrder.equals("&order=title"))
                listSort.add(new SortModel("A-Z", true, "&order=title"));
            else
                listSort.add(new SortModel("A-Z", false, "&order=title"));
            if (sortOrder.equals("&order=title%20desc"))
                listSort.add(new SortModel("Z-A", true, "&order=title%20desc"));
            else
                listSort.add(new SortModel("Z-A", false, "&order=title%20desc"));
            if (sortOrder.equals("&order=created_at%20asc"))
                listSort.add(new SortModel("Newest To Oldest", true, "&order=created_at%20asc"));
            else
                listSort.add(new SortModel("Newest To Oldest", false, "&order=created_at%20asc"));
            if (sortOrder.equals("&order=created_at%20desc"))
                listSort.add(new SortModel("Oldest To Newest", true, "&order=created_at%20desc"));
            else
                listSort.add(new SortModel("Oldest To Newest", false, "&order=created_at%20desc"));
            tvSortCount.setText("1");
            tvSortCount.setVisibility(View.VISIBLE);
        }
    }

    private void CreateList() {

        listSalesPrice = new ArrayList<>();
        listDeliveryCharges = new ArrayList<>();
        listMRP = new ArrayList<>();
        listSize = new ArrayList<>();
        listColor = new ArrayList<>();
        ProductController productController = new ProductController();
        HashSet<String> tags = productController.GetTags(FilterActivity.this);
        Log.e("tags", tags.toString());
        for (String s : tags) {
            if (s.contains("Rs"))
                listSalesPrice.add(new FilterModel(s, false));
            else if (s.contains("Carat"))
                listMRP.add(new FilterModel(s, false));
            else if (s.contains("XS")||s.contains("S")||s.contains("M")||s.contains("L")||s.contains("XL")||s.contains("XXL")||s.contains("XXXL"))
                listSize.add(new FilterModel(s, false));
            else if (s.equals("Blue") || s.equals("Green") || s.equals("Red"))
                listColor.add(new FilterModel(s, false));
            else {
                String type = productController.GetMatchTag(FilterActivity.this, s);
                if (type.equals("OCCASION"))
                    listDeliveryCharges.add(new FilterModel(s, false));
            }
        }
    }

    private void GetSelectedTag() {
        if (!sortOrder.equals("")) {
            tvSortCount.setText("1");
            tvSortCount.setVisibility(View.VISIBLE);
        }
        listSalesPrice = new ArrayList<>();
        listDeliveryCharges = new ArrayList<>();
        listMRP = new ArrayList<>();
        listSize = new ArrayList<>();
        listColor = new ArrayList<>();
        ProductController productController = new ProductController();
        HashSet<String> tags = productController.GetTagsSelected(FilterActivity.this, selectedTag);
        Log.e("tags", tags.toString());
        for (String s : tags) {
            if (s.contains("Rs")) {
                boolean isSelected = false;
                for (String tag : selectedTag) {
                    if (tag.equals(s)) {
                        isSelected = true;
                        break;
                    }
                }
                listSalesPrice.add(new FilterModel(s, isSelected));
            } else if (s.contains("Carat")) {
                boolean isSelected = false;
                for (String tag : selectedTag) {
                    if (tag.equals(s)) {
                        isSelected = true;
                        break;
                    }
                }
                listMRP.add(new FilterModel(s, isSelected));
            } else if (s.contains("gms")) {
                boolean isSelected = false;
                for (String tag : selectedTag) {
                    if (tag.equals(s)) {
                        isSelected = true;
                        break;
                    }
                }
                listSize.add(new FilterModel(s, isSelected));
            } else if (s.equals("Blue") || s.equals("Green") || s.equals("Red")) {
                boolean isSelected = false;
                for (String tag : selectedTag) {
                    if (tag.equals(s)) {
                        isSelected = true;
                        break;
                    }
                }
                listColor.add(new FilterModel(s, isSelected));
            } else {
                String type = productController.GetMatchTag(FilterActivity.this, s);
                if (type.equals("OCCASION")) {
                    boolean isSelected = false;
                    for (String tag : selectedTag) {
                        if (tag.equals(s)) {
                            isSelected = true;
                            break;
                        }
                    }
                    listDeliveryCharges.add(new FilterModel(s, isSelected));
                }
            }
        }
        int countPrice = 0;
        for (FilterModel model : listSalesPrice) {
            if (model.isCheck())
                countPrice++;
        }
        if (countPrice == 0) {
            tv_SalesPriceCount.setText("");
            tv_SalesPriceCount.setVisibility(View.GONE);
        } else {
            tv_SalesPriceCount.setText(countPrice + "");
            tv_SalesPriceCount.setVisibility(View.VISIBLE);
        }
        int countDiamond = 0;
        for (FilterModel model : listMRP) {
            if (model.isCheck())
                countDiamond++;
        }
        if (countDiamond == 0) {
            tv_MRPCount.setText("");
            tv_MRPCount.setVisibility(View.GONE);
        } else {
            tv_MRPCount.setText(countDiamond + "");
            tv_MRPCount.setVisibility(View.VISIBLE);
        }
        int countGold = 0;
        for (FilterModel model : listSize) {
            if (model.isCheck())
                countGold++;
        }
        if (countGold == 0) {
            tv_DeliveryChargesCount.setText("");
            tv_DeliveryChargesCount.setVisibility(View.GONE);
        } else {
            tv_DeliveryChargesCount.setText(countGold + "");
            tv_DeliveryChargesCount.setVisibility(View.VISIBLE);
        }
        int countStone = 0;
        for (FilterModel model : listColor) {
            if (model.isCheck())
                countStone++;
        }
        if (countStone == 0) {
            tv_ColorCount.setText("");
            tv_ColorCount.setVisibility(View.GONE);
        } else {
            tv_ColorCount.setText(countStone + "");
            tv_ColorCount.setVisibility(View.VISIBLE);
        }
        int countOccasion = 0;
        for (FilterModel model : listDeliveryCharges) {
            if (model.isCheck())
                countOccasion++;
        }
        if (countOccasion == 0) {
            tv_SizeCount.setText("");
            tv_SizeCount.setVisibility(View.GONE);
        } else {
            tv_SizeCount.setText(countOccasion + "");
            tv_SizeCount.setVisibility(View.VISIBLE);
        }
    }

    private void SetUpView() {
        try {
            if (listDeliveryCharges.size() > 0)
                ll_Size.setVisibility(View.VISIBLE);
            else
                ll_Size.setVisibility(View.GONE);
            if (listSalesPrice.size() > 0)
                ll_SalesPrice.setVisibility(View.VISIBLE);
            else
                ll_SalesPrice.setVisibility(View.GONE);
            if (listColor.size() > 0)
                ll_Color.setVisibility(View.VISIBLE);
            else
                ll_Color.setVisibility(View.GONE);
            if (listSize.size() > 0)
                ll_DeliveryCharges.setVisibility(View.VISIBLE);
            else
                ll_DeliveryCharges.setVisibility(View.GONE);
            if (listMRP.size() > 0)
                ll_MRP.setVisibility(View.VISIBLE);
            else
                ll_MRP.setVisibility(View.GONE);
        } catch (Exception ex) {
        }
    }

    private void SetUpRecyclerSort() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerItems.setLayoutManager(layoutManager);
        adapter = new SingleFilterAdapter(listSort, "sort", this);
        adapter.setClickListener(this);
        recyclerItems.setAdapter(adapter);
    }

    private void SetUpRecyclerPrice() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerItems.setLayoutManager(layoutManager);
        adapterPrice = new MultiFilterAdapter(listSalesPrice, "price", this);
        adapterPrice.setClickListener(this);
        recyclerItems.setAdapter(adapterPrice);
    }

    private void SetUpRecyclerDiamondWeight() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerItems.setLayoutManager(layoutManager);
        adapterDiamond = new MultiFilterAdapter(listMRP, "diamond", this);
        adapterDiamond.setClickListener(this);
        recyclerItems.setAdapter(adapterDiamond);
    }

    private void SetUpRecyclerGoldWeight() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerItems.setLayoutManager(layoutManager);
        adapterSize = new MultiFilterAdapter(listSize, "gold", this);
        adapterSize.setClickListener(this);
        recyclerItems.setAdapter(adapterSize);
    }

    private void SetUpRecyclerStone() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerItems.setLayoutManager(layoutManager);
        adapterColor = new MultiFilterAdapter(listColor, "stone", this);
        adapterColor.setClickListener(this);
        recyclerItems.setAdapter(adapterColor);
    }

    private void SetUpRecyclerOccasion() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerItems.setLayoutManager(layoutManager);
        adapterOccasion = new MultiFilterAdapter(listDeliveryCharges, "occasion", this);
        adapterOccasion.setClickListener(this);
        recyclerItems.setAdapter(adapterOccasion);
    }

    @Override
    public void onClick(View v) {
        try {
            if (isClickOn) {
                if (v.getId() == R.id.btnSort) {
                    SetUpRecyclerSort();
                    btnSort.setBackgroundColor(getResources().getColor(R.color.red_dark));
                    lblSort.setTextColor(getResources().getColor(R.color.colorWhite));
                    ll_SalesPrice.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_DeliveryCharges.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_MRP.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Size.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Color.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tv_SalesPrice.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_DeliveryCharges.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_MRP.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Size.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Color.setTextColor(getResources().getColor(R.color.textPrimary));
                } else if (v.getId() == R.id.ll_SalesPrice) {
                    SetUpRecyclerPrice();
                    ll_SalesPrice.setBackgroundColor(getResources().getColor(R.color.red_dark));
                    tv_SalesPrice.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnSort.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_DeliveryCharges.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_MRP.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Size.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Color.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    lblSort.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_DeliveryCharges.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_MRP.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Size.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Color.setTextColor(getResources().getColor(R.color.textPrimary));
                } else if (v.getId() == R.id.ll_DeliveryCharges) {
                    SetUpRecyclerGoldWeight();
                    ll_DeliveryCharges.setBackgroundColor(getResources().getColor(R.color.red_dark));
                    tv_DeliveryCharges.setTextColor(getResources().getColor(R.color.colorWhite));
                    ll_SalesPrice.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    btnSort.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_MRP.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Size.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Color.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tv_SalesPrice.setTextColor(getResources().getColor(R.color.textPrimary));
                    lblSort.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_MRP.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Size.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Color.setTextColor(getResources().getColor(R.color.textPrimary));
                } else if (v.getId() == R.id.ll_MRP) {
                    SetUpRecyclerDiamondWeight();
                    ll_MRP.setBackgroundColor(getResources().getColor(R.color.red_dark));
                    tv_MRP.setTextColor(getResources().getColor(R.color.colorWhite));
                    ll_SalesPrice.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    btnSort.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_DeliveryCharges.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Size.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Color.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tv_SalesPrice.setTextColor(getResources().getColor(R.color.textPrimary));
                    lblSort.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_DeliveryCharges.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Size.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Color.setTextColor(getResources().getColor(R.color.textPrimary));
                } else if (v.getId() == R.id.ll_Size) {
                    SetUpRecyclerOccasion();
                    ll_Size.setBackgroundColor(getResources().getColor(R.color.red_dark));
                    tv_Size.setTextColor(getResources().getColor(R.color.colorWhite));
                    ll_SalesPrice.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    btnSort.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_MRP.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_DeliveryCharges.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Color.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tv_SalesPrice.setTextColor(getResources().getColor(R.color.textPrimary));
                    lblSort.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_MRP.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_DeliveryCharges.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Color.setTextColor(getResources().getColor(R.color.textPrimary));
                } else if (v.getId() == R.id.ll_Color) {
                    SetUpRecyclerStone();
                    ll_Color.setBackgroundColor(getResources().getColor(R.color.red_dark));
                    tv_Color.setTextColor(getResources().getColor(R.color.colorWhite));
                    ll_SalesPrice.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    btnSort.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_MRP.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_Size.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    ll_DeliveryCharges.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tv_SalesPrice.setTextColor(getResources().getColor(R.color.textPrimary));
                    lblSort.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_MRP.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_Size.setTextColor(getResources().getColor(R.color.textPrimary));
                    tv_DeliveryCharges.setTextColor(getResources().getColor(R.color.textPrimary));
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void ItemClickedSingle(View view, int position, String mode) {
        try {
            if (mode.equals("sort")) {
                listSort.get(position).setIsCheck(!listSort.get(position).isCheck());
                for (int i = 0; i < listSort.size(); i++) {
                    if (i != position) {
                        listSort.get(i).setIsCheck(false);
                    }
                }
                if (listSort.get(position).isCheck()) {
                    tvSortCount.setText("1");
                    sortOrder = listSort.get(position).getSortOrder();
                } else {
                    tvSortCount.setText("");
                    sortOrder = "";
                }
                if (tvSortCount.getText().equals(""))
                    tvSortCount.setVisibility(View.GONE);
                else
                    tvSortCount.setVisibility(View.VISIBLE);
                adapter.list = listSort;
                adapter.notifyDataSetChanged();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void ItemClickedMultiple(View view, int position, String mode) {
        try {
            if (mode.equals("price")) {
                listSalesPrice.get(position).setIsCheck(!listSalesPrice.get(position).isCheck());
                int count = 0;
                for (int i = 0; i < listSalesPrice.size(); i++) {
                    if (listSalesPrice.get(i).isCheck())
                        count++;
                }
                if (listSalesPrice.get(position).isCheck())
                    selectedTag.add(listSalesPrice.get(position).getName());
                else
                    selectedTag.remove(listSalesPrice.get(position).getName());
                if (count == 0) {
                    tv_SalesPriceCount.setVisibility(View.GONE);
                    tv_SalesPriceCount.setText("");
                } else {
                    tv_SalesPriceCount.setVisibility(View.VISIBLE);
                    tv_SalesPriceCount.setText("" + count);
                }
                GetSelectedTag();
                adapterPrice.list = listSalesPrice;
                adapterPrice.notifyDataSetChanged();
            } else if (mode.equals("diamond")) {
                listMRP.get(position).setIsCheck(!listMRP.get(position).isCheck());
                int count = 0;
                for (int i = 0; i < listMRP.size(); i++) {
                    if (listMRP.get(i).isCheck())
                        count++;
                }
                if (listMRP.get(position).isCheck())
                    selectedTag.add(listMRP.get(position).getName());
                else
                    selectedTag.remove(listMRP.get(position).getName());
                if (count == 0) {
                    tv_MRPCount.setVisibility(View.GONE);
                    tv_MRPCount.setText("");
                } else {
                    tv_MRPCount.setVisibility(View.VISIBLE);
                    tv_MRPCount.setText("" + count);
                }
                GetSelectedTag();
                adapterDiamond.list = listMRP;
                adapterDiamond.notifyDataSetChanged();
            } else if (mode.equals("gold")) {
                listSize.get(position).setIsCheck(!listSize.get(position).isCheck());
                int count = 0;
                for (int i = 0; i < listSize.size(); i++) {
                    if (listSize.get(i).isCheck())
                        count++;
                }
                if (listSize.get(position).isCheck())
                    selectedTag.add(listSize.get(position).getName());
                else
                    selectedTag.remove(listSize.get(position).getName());
                if (count == 0) {
                    tv_DeliveryChargesCount.setVisibility(View.GONE);
                    tv_DeliveryChargesCount.setText("");
                } else {
                    tv_DeliveryChargesCount.setVisibility(View.VISIBLE);
                    tv_DeliveryChargesCount.setText("" + count);
                }
                GetSelectedTag();
                adapterSize.list = listSize;
                adapterSize.notifyDataSetChanged();
            } else if (mode.equals("stone")) {
                listColor.get(position).setIsCheck(!listColor.get(position).isCheck());
                int count = 0;
                for (int i = 0; i < listColor.size(); i++) {
                    if (listColor.get(i).isCheck())
                        count++;
                }
                if (listColor.get(position).isCheck())
                    selectedTag.add(listColor.get(position).getName());
                else
                    selectedTag.remove(listColor.get(position).getName());
                if (count == 0) {
                    tv_ColorCount.setVisibility(View.GONE);
                    tv_ColorCount.setText("");
                } else {
                    tv_ColorCount.setVisibility(View.VISIBLE);
                    tv_ColorCount.setText("" + count);
                }
                GetSelectedTag();
                adapterColor.list = listColor;
                adapterColor.notifyDataSetChanged();
            } else if (mode.equals("occasion")) {
                listDeliveryCharges.get(position).setIsCheck(!listDeliveryCharges.get(position).isCheck());
                int count = 0;
                for (int i = 0; i < listDeliveryCharges.size(); i++) {
                    if (listDeliveryCharges.get(i).isCheck())
                        count++;
                }
                if (listDeliveryCharges.get(position).isCheck())
                    selectedTag.add(listDeliveryCharges.get(position).getName());
                else
                    selectedTag.remove(listDeliveryCharges.get(position).getName());
                if (count == 0) {
                    tv_SizeCount.setVisibility(View.GONE);
                    tv_SizeCount.setText("");
                } else {
                    tv_SizeCount.setVisibility(View.VISIBLE);
                    tv_SizeCount.setText("" + count);
                }
                GetSelectedTag();
                adapterOccasion.list = listDeliveryCharges;
                adapterOccasion.notifyDataSetChanged();
            }
        } catch (Exception ex) {
        }

    }

    private void SetUpRecyclerView() {
        try {
            if (adapterPrice != null) {
                adapterPrice.list = listSalesPrice;
                adapterPrice.notifyDataSetChanged();
            }
            if (adapterDiamond != null) {
                adapterDiamond.list = listMRP;
                adapterDiamond.notifyDataSetChanged();
            }
            if (adapterSize != null) {
                adapterSize.list = listSize;
                adapterSize.notifyDataSetChanged();
            }
            if (adapterColor != null) {
                adapterColor.list = listColor;
                adapterColor.notifyDataSetChanged();
            }
            if (adapterOccasion != null) {
                adapterOccasion.list = listDeliveryCharges;
                adapterOccasion.notifyDataSetChanged();


            }
        } catch (Exception ex) {
        }
    }*/
}
