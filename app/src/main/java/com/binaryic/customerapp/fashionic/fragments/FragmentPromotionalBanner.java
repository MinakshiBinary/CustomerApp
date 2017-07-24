package com.binaryic.customerapp.fashionic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.ProductListActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by HP on 04-Apr-17.
 */

public class FragmentPromotionalBanner extends Fragment {
    ImageView iv_banner;
    String banner_id = "";
    String banner_image = "";
    String category_id = "";
    String category_name = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotional_banner, container, false);
        inIt(view);
        getExtra();
        setImage();
        return view;
    }

    private void inIt(View view) {
        iv_banner = (ImageView) view.findViewById(R.id.iv_banner);
        iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("categoryId", category_id);
                startActivity(intent);

               /* CatalogueFragment fragmentCatalogue = new CatalogueFragment();
                Bundle bundle = new Bundle();
                bundle.putString("categoryId", category_id);
                fragmentCatalogue.setArguments(bundle);
                MyUtils.addFragmentBackHome(layPager, fragmentCatalogue, getActivity());
*/

              /*  Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("collection", category_name);
                intent.putExtra("collection_id", category_id);
                startActivity(intent);*/
            }
        });
    }

    private void getExtra() {
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                banner_id = bundle.getString("banner_id");
                banner_image = bundle.getString("banner_image");
                category_id = bundle.getString("category_id");
                category_name = bundle.getString("category_name");
            }
        } catch (Exception ex) {
        }
    }

    private void setImage() {
        Glide.with(getActivity()).load(banner_image).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.logo).thumbnail(0.1f).into(iv_banner);
    }
}
