package com.binaryic.customerapp.fashionic.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.CartController;
import com.binaryic.customerapp.fashionic.controller.WishListController;
import com.binaryic.customerapp.fashionic.fragments.FragmentHome1;
import com.binaryic.customerapp.fashionic.fragments.FragmentMyAccount;
import com.binaryic.customerapp.fashionic.fragments.FragmentWishlist;
import com.binaryic.customerapp.fashionic.fragments.FragmentsHomeMenuDrawer;

import static com.binaryic.customerapp.fashionic.common.MyUtils.showMessageBox;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static DrawerLayout drawer;
    private Dialog msgDialog;
    private TextView btnYes, btnNo;
    public ViewPager viewPager;
    public static FrameLayout layPager;
    public static FrameLayout layPager_Drawer;
    //public ImageView btnHome, btnMyAccount, btnMore;
    public static TextView toolbarTitle;
    static FragmentHome1 fragmentHome;
    RelativeLayout btnCart;
    private RelativeLayout rl_Account;
    private RelativeLayout rl_Notification;
    public static RelativeLayout btnWish;
    private ImageView iv_Notification;
    private ImageView iv_Account;
    private ImageView iv_Wish;
    // ImageView btnFav;
    ImageView btnSearch;
    public static TextView tv_Wish_Count, tvCount;


    BackPressListener backPressListener;

    public void setBackPressListener(BackPressListener backPressListener) {
        this.backPressListener = backPressListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("oncreate", "OnCreate");
        setSideMenu();
        setFragment();
    }

    private void setSideMenu() {

        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (Build.VERSION.SDK_INT >= 14)
            drawer.setFitsSystemWindows(true);
        init();

    }

    private void init() {

        layPager = (FrameLayout) findViewById(R.id.layPager);
        layPager_Drawer = (FrameLayout) findViewById(R.id.layPager_Drawer);
        //btnHome = (ImageView) findViewById(R.id.btnHome);
        //btnFav = (ImageView) findViewById(btnFav);
        // btnMyAccount = (ImageView) findViewById(R.id.btnMyAccount);
        //   imgFav = (ImageView) findViewById(R.id.imgFav);
        // btnMore = (ImageView) findViewById(R.id.btnMore);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tv_Wish_Count = (TextView) findViewById(R.id.tv_Wish_Count);
        btnCart = (RelativeLayout) findViewById(R.id.btnCart);
        rl_Account = (RelativeLayout) findViewById(R.id.rl_Account);
        btnWish = (RelativeLayout) findViewById(R.id.btnWish);
        iv_Notification = (ImageView) findViewById(R.id.iv_Notification);
        iv_Account = (ImageView) findViewById(R.id.iv_Account);
        iv_Wish = (ImageView) findViewById(R.id.iv_Wish);
        rl_Notification = (RelativeLayout) findViewById(R.id.rl_Notification);
        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        //  btnHome.setOnClickListener(this);
        // btnFav.setOnClickListener(this);
        //btnMyAccount.setOnClickListener(this);
        // btnMore.setOnClickListener(this);
        btnCart.setOnClickListener(this);
        rl_Account.setOnClickListener(this);
        btnWish.setOnClickListener(this);
        iv_Notification.setOnClickListener(this);
        iv_Account.setOnClickListener(this);
        iv_Wish.setOnClickListener(this);
        rl_Notification.setOnClickListener(this);
        toolbarTitle.setVisibility(View.GONE);

    }

    private void setFragment() {
        Log.e("Add", "addHomeFragment");
        if (fragmentHome == null)
            fragmentHome = new FragmentHome1();


        MyUtils.addFragment(layPager.getId(), fragmentHome, MainActivity.this);

        addHomeFragment(MainActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        btnWish.setVisibility(View.VISIBLE);


        Log.e("oncreate", "Resume");

        int count = CartController.getCartCount(this);
        if (count == 0) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setText(count + "");
            tvCount.setVisibility(View.VISIBLE);
        }
        int countWish = WishListController.getWishCount(this);
        if (countWish == 0) {
            tv_Wish_Count.setVisibility(View.GONE);
        } else {
            tv_Wish_Count.setText(countWish + "");
            tv_Wish_Count.setVisibility(View.VISIBLE);
        }
      /*  Fragment f = getSupportFragmentManager().findFragmentById(R.id.layPager);
        Log.e("MainActivity", "Fragment =" + f);
        if (f instanceof CatalogueFragment) {
            ((CatalogueFragment) f).resetList();
        }*/
        //getContentResolver().delete(CONTENT_TAGS, null, null);

    }

    public static void addHomeFragment(Activity context) {



        FragmentsHomeMenuDrawer homeDrawer = new FragmentsHomeMenuDrawer();
        layPager_Drawer.removeAllViews();

        MyUtils.addFragment(layPager_Drawer.getId(), homeDrawer, context);
        //imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home));
        // btnMyAccount.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
        //btnMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_more_home));
        toolbarTitle.setText("Shop Organikos");
    }

    public void homeClicked() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            onBackPressed();
            //imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home));
            //  btnMyAccount.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
            // btnMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_more_home));
            toolbarTitle.setText("Shop Organikos");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layPager);
        fragment.onActivityResult(requestCode, resultCode, data);

    }

    private void addWishFragment() {

        FragmentWishlist fragment = new FragmentWishlist();
        MyUtils.addFragmentBack(R.id.layPager, fragment, this);
        //imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home_selected));
        // btnMyAccount.setImageResource(R.drawable.ic_person);
        ///btnMore.setImageResource(R.drawable.ic_more_home);
        toolbarTitle.setText("Wishlist");

    }

    private void addMyAccountFragment() {

        FragmentMyAccount fragment = new FragmentMyAccount();
        MyUtils.addFragmentBackHome(layPager.getId(), fragment, this);


       /* FragmentMyAccount fragment = new FragmentMyAccount();
        MyUtil.addFragmentBackHome(layPager.getId(), fragment, this);
        //imgFav.setImageResource(R.drawable.ic_favorite_home);
        //  btnMyAccount.setImageResource(R.drawable.ic_person_home_selected);
        // btnMore.setImageResource(R.drawable.ic_more_home);
        toolbarTitle.setText("My Account");*/

    }

    private void addMoreFragment() {

      /*  FragmentMore fragment = new FragmentMore();
        MyUtil.addFragmentBackHome(layPager.getId(), fragment, this);
        //imgFav.setImageResource(R.drawable.ic_favorite_home);
        //  btnMyAccount.setImageResource(R.drawable.ic_person);
        ////   btnMore.setImageResource(R.drawable.ic_more_home_selected);
        toolbarTitle.setText("More");*/

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.iv_Wish:
                btnWish.setVisibility(View.GONE);
                Log.e("btnWbtnWishish", "iv_Wish==" + (R.id.iv_Wish == v.getId()));
                addWishFragment();
                break;
            case R.id.btnCart:
                btnWish.setVisibility(View.VISIBLE);

                Log.e("btnWbtnWishish", "btnCart==" + (R.id.btnCart == v.getId()));

                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.rl_Account:
                btnWish.setVisibility(View.VISIBLE);

                addMyAccountFragment();
                break;
            case R.id.iv_Notification:
                btnWish.setVisibility(View.VISIBLE);

                addWishFragment();
                break;

            case R.id.iv_Account:
                btnWish.setVisibility(View.VISIBLE);

                addMyAccountFragment();
                break;
            /*case R.id.btnMore:
                addMoreFragment();
                break;*/
            /*case R.id.btnHome:
                homeClicked();
                break;*/


        }

    }


    @Override
    public void onBackPressed() {
        MainActivity.btnWish.setVisibility(View.VISIBLE);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.layPager);
            Log.e("MainActivity", "Fragment =" + f);
            if (f instanceof FragmentHome1) {
                showMessageBox("Do you want to Exit ?", "Yes", "NO", true, MainActivity.this);
                MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                        MyUtils.msgDialog.dismiss();
                    }
                });
                MyUtils.btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtils.msgDialog.dismiss();
                    }
                });
            } else if (f instanceof FragmentWishlist) {

                super.onBackPressed();
            } else {
                super.onBackPressed();
            }
        }


           /* FragmentManager fragmentManager = getSupportFragmentManager();
            Log.e("count", fragmentManager.getBackStackEntryCount() + "");
            super.onBackPressed();
            if (fragmentManager.getBackStackEntryCount() == 0) {
                // imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_home));
                //btnMyAccount.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
                // btnMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_more_home));
                toolbarTitle.setText("Shop Organikos");
            }*/
    }

    public interface BackPressListener {
        public void backPress();
    }
}
