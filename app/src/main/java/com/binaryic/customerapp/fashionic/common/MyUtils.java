package com.binaryic.customerapp.fashionic.common;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.binaryic.customerapp.fashionic.R;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_ADDRESS;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_CART;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_CUSTOMER;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_SELLER;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_WISHLIST;

/**
 * Created by Binary_Apple on 3/23/17.
 */

public class MyUtils {

    public static ProgressDialog progressDialog;
    public static Dialog msgDialog;
    public static TextView btnYes, btnNo;

    public static Bitmap uriToBitmap(Context context, String uri) {
        Bitmap bitmap = null;
        try {
            Uri bitmapUri = Uri.parse(uri);
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), bitmapUri);
        } catch (Exception e) {

            Log.e("uriToBitmap", "error==" + e.getMessage());
        }
        return bitmap;
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Log.e("getBitmapFromURL", "error==" + e.getMessage());
            return null;
        }
    }
    public static String getRealPathFromURI(Activity context, Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = context.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static void addFragmentBackHome(int containerId, Fragment fragment, Activity context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            while (fragmentManager.popBackStackImmediate()) ;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    public static void addFragmentBack(int containerId, Fragment fragment, Activity context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_right_exit);
        transaction.add(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    public static void addFragment(int containerId, Fragment fragment, Activity context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

       /* transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_right_exit);*/
        transaction.add(containerId, fragment).commit();
    }

    public static void replaceFragment(int containerId, Fragment fragment, Activity context) {

        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            while (fragmentManager.popBackStackImmediate()) ;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment).commit();

    }

    public static ViewPropertyAnimation.Animator getAnimation() {
        ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                // if it's a custom view class, cast it here
                // then find subviews and do the animations
                // here, we just use the entire view for the fade animation
                view.setAlpha(0f);

                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(2500);
                fadeAnim.start();
            }
        };
        return animationObject;
    }

    public static String increamentCount(String first_Count) {
        int count = Integer.parseInt(first_Count);
        count++;
        if (count >= 5) {
            count = 5;
        }

        return String.valueOf(count);
    }

    public static void showProgress(final String title, Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(title);
        progressDialog.show();
    }

    public static String decrementCount(String first_Count) {
        int count = Integer.parseInt(first_Count);
        count--;
        if (count <= 0) {
            count = 1;
        }

        return String.valueOf(count);
    }

    public static void showMessageBox(String Title, String yesButton, String noButton, Boolean noShow, final Activity cont) {
        msgDialog = new Dialog(cont);
        msgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        msgDialog.setContentView(R.layout.fragment_dialog);
        WindowManager.LayoutParams wmlp = msgDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        msgDialog.setCanceledOnTouchOutside(false);
        TextView content = (TextView) msgDialog.findViewById(R.id.content);
        btnYes = (TextView) msgDialog.findViewById(R.id.btnDone);
        btnNo = (TextView) msgDialog.findViewById(R.id.btnCancel);
        content.setText(Title);
        btnYes.setText(yesButton);
        btnNo.setText(noButton);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgDialog.dismiss();

            }
        });
        if (noShow) {
            btnNo.setVisibility(ViewStub.VISIBLE);
        } else {
            btnNo.setVisibility(ViewStub.GONE);
        }
        msgDialog.show();
    }

    public static void showSnackBar(Activity context, View view, String text) {
        Log.e("InternetConnection", "showSnackBar");

        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void deleteAllDatabase(Activity context) {
        context.getContentResolver().delete(CONTENT_SELLER, null, null);
        // context.getContentResolver().delete(CONTENT_PRODUCT, null, null);
        // context.getContentResolver().delete(CONTENT_CATEGORY, null, null);
        context.getContentResolver().delete(CONTENT_CUSTOMER, null, null);
        context.getContentResolver().delete(CONTENT_WISHLIST, null, null);
        context.getContentResolver().delete(CONTENT_CART, null, null);
        context.getContentResolver().delete(CONTENT_ADDRESS, null, null);
        //  context.getContentResolver().delete(CONTENT_BANNER, null, null);
        // context.getContentResolver().delete(CONTENT_HOME, null, null);
    }
}
