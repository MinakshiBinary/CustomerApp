package com.binaryic.customerapp.fashionic.controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.binaryic.customerapp.fashionic.BuildConfig;
import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.common.Constants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MY PC on 19-Nov-16.
 */
public class UploadImageController {
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    public static Uri fileUri;
    public static String mCurrentPhotoPath;

    public static void selectUploadType(final Activity context) {

        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_select_type);
            LinearLayout ll_Gallary = (LinearLayout) dialog.findViewById(R.id.ll_Gallary);
            LinearLayout ll_Camera = (LinearLayout) dialog.findViewById(R.id.ll_Camera);
            ImageView iv_Camera = (ImageView) dialog.findViewById(R.id.iv_Camera);
            ImageView iv_Gallary = (ImageView) dialog.findViewById(R.id.iv_Gallary);

            iv_Camera.setColorFilter(context.getResources().getColor(R.color.grey_image));
            iv_Gallary.setColorFilter(context.getResources().getColor(R.color.grey_image));

            ll_Camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera(context);
                    dialog.dismiss();
                }
            });
            ll_Gallary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery(context);
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
            Log.e(context.getClass().getName(), "selectUploadType " + e.getMessage());
        }
    }

    private static void openGallery(Activity context) {
        Log.e("UploadImageController", "openGallery");

        try {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra("return-data", true);
            context.startActivityForResult(intent, Constants.PICK_PHOTO);
        } catch (Exception e) {
            Log.e(context.getClass().getName(), "openGallery " + e.getMessage());
        }
    }

    private static void openCamera(Activity context) {
        Log.e("UploadImageController", "openCamera");

        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(context, MEDIA_TYPE_IMAGE);
                Log.e("UploadImageController", "fileUri" + fileUri);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                context.startActivityForResult(cameraIntent, Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        } catch (Exception e) {
            Log.e(context.getClass().getName(), "openCamera " + e.getMessage());
        }
    }

    public static Uri getOutputMediaFileUri(Activity context, int type) {
        Uri photoURI = null;
        try {
            photoURI = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    createImageFile());
        } catch (IOException e) {
            Log.e("getOutputMediaFileUri", "errorr==" + e.getMessage());
        }
        return photoURI;
    }

    private static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private static File getOutputMediaFile(int type) {

// External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

// Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

// Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        Log.e("UploadImageController", "mediaFile==" + mediaFile);
        return mediaFile;
    }
}
