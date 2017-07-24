package com.binaryic.customerapp.fashionic.fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.activities.LoginActivity;
import com.binaryic.customerapp.fashionic.activities.OrderActivity;
import com.binaryic.customerapp.fashionic.common.Constants;
import com.binaryic.customerapp.fashionic.common.MyUtils;
import com.binaryic.customerapp.fashionic.controller.LoginController;
import com.binaryic.customerapp.fashionic.controller.UploadImageController;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_PROFILE_IMAGE;
import static com.binaryic.customerapp.fashionic.common.Constants.CONTENT_CUSTOMER;


/**
 * Created by User on 08-09-2016.
 */
public class FragmentMyAccount extends Fragment implements View.OnClickListener {
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static int RESULT_LOAD_IMG = 2;
    private TextView tv_Name;
    private TextView tv_Email;
    private ImageView iv_Notification;
    private ImageView iv_Offer;
    private ImageView iv_Order;
    private RelativeLayout rl_SignOut;
    private RelativeLayout rl_Notification;
    private RelativeLayout rl_Offer;
    private CircleImageView civ_Profile;
    private Button bt_Camera;
    private String imgDecodableString;
    private Button bt_Gallery;
    private Uri fileUri;
    private String mCurrentPhotoPath;
    private RelativeLayout rl_My_Orders;
    private String imageUri = "";
    private boolean isImage = false;

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

        return mediaFile;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        iv_Notification = (ImageView) view.findViewById(R.id.iv_Notification);
        iv_Offer = (ImageView) view.findViewById(R.id.iv_Offer);
        iv_Order = (ImageView) view.findViewById(R.id.iv_Order);
        tv_Name = (TextView) view.findViewById(R.id.tv_Name);
        tv_Email = (TextView) view.findViewById(R.id.tv_Email);
        rl_SignOut = (RelativeLayout) view.findViewById(R.id.rl_SignOut);
        civ_Profile = (CircleImageView) view.findViewById(R.id.civ_Profile);
        rl_My_Orders = (RelativeLayout) view.findViewById(R.id.rl_MyOrders);
        rl_Notification = (RelativeLayout) view.findViewById(R.id.rl_Notification);
        rl_Offer = (RelativeLayout) view.findViewById(R.id.rl_Offer);

        iv_Notification.setColorFilter(getResources().getColor(R.color.red_dark));
        iv_Offer.setColorFilter(getResources().getColor(R.color.red_dark));
        iv_Order.setColorFilter(getResources().getColor(R.color.red_dark));



        /*CustomerModel customer = CustomerController.getCustomerData(getActivity());
        tv_Name.setText(customer.getFirstName() + " " + customer.getLastName());
        tv_Email.setText(customer.getEmail());*/
        rl_Offer.setOnClickListener(this);
        rl_Notification.setOnClickListener(this);
        rl_SignOut.setOnClickListener(this);
        civ_Profile.setOnClickListener(this);
        rl_My_Orders.setOnClickListener(this);
        setData();
        setProfileImage();
        return view;
    }

    private void setData() {
        tv_Name.setText(LoginController.getCustomerName(getActivity()));
        tv_Email.setText(LoginController.getCustomerEmail(getActivity()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_SignOut:
                MyUtils.deleteAllDatabase(getActivity());
                getActivity().finishAffinity();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.civ_Profile:
                getProfilePicture();

                break;
            case R.id.rl_Notification:
                Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_Offer:
                Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_MyOrders:

                if (LoginController.isAllreadyLogin(getActivity())) {
                    startActivity(new Intent(getActivity(), OrderActivity.class));
                } else {
                    MyUtils.showMessageBox("Do You Want To Sign In", "Yes", "NO", true, getActivity());
                    MyUtils.btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                            MyUtils.msgDialog.dismiss();
                        }
                    });
                    MyUtils.btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUtils.msgDialog.dismiss();
                        }
                    });

                }


                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "permission granted", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getActivity(), "Please accept permission for go ahead.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("FA", "onActivityResult");

        //ImageModel imageModel = new ImageModel();
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Constants.PICK_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
                try {
                    Uri uri = data.getData();
                    try {
                        imageUri = MyUtils.getRealPathFromURI(getActivity(), uri);
                        //imageModel.setUri(Util.getRealPathFromURI(PreferencesActivity.this, uri));
                        // imageModel.setBitmap(Util.uriToBitmap(AddProductActivity.this, uri.toString()));
                        civ_Profile.setImageBitmap(MyUtils.uriToBitmap(getActivity(), uri.toString()));
                        savePhoto(uri.toString());
                        isImage = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            } else if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

               /* final Bitmap bitmap = BitmapFactory.decodeFile(UploadImageController.fileUri.getPath(),
                        options);
                 imageUri = UploadImageController.fileUri.getPath();
                isImage = true;
                iv_Profile_Image.setImageBitmap(bitmap);*/
                Bitmap bitmap = null;
                if (UploadImageController.mCurrentPhotoPath != null) {
                    Uri imageUri = Uri.parse(UploadImageController.mCurrentPhotoPath);
                    File file = new File(imageUri.getPath());

                    InputStream ims = null;
                    try {
                        ims = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        Log.e("scanFilescanFile", "errroror==" + e.getMessage());
                    }

                    bitmap = BitmapFactory.decodeStream(ims);
                    isImage = true;
                    civ_Profile.setImageBitmap(bitmap);
                    savePhoto(imageUri.toString());
                }
            }

        } catch (Exception e) {
            e.getMessage();
            Log.e(this.getClass().getName(), "onResult " + e.getMessage());
        }
    }

    private void setProfileImage() {
        Cursor cursor = getActivity().getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        Log.e("cursorcount", "==" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            Log.e("COLUMN_PROFILE_IMAGE", "====" + cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)));
            if (!TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)))) {

                Glide.with(getActivity()).load(new File(cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)))).into(civ_Profile);
            }
        }
        cursor.close();
    }

    private void savePhoto(String image) {
        Cursor cursor_Image = getActivity().getContentResolver().query(CONTENT_CUSTOMER, null, null, null, null);
        Log.e("cursor_Image111", "==" + cursor_Image.getCount());
        if (cursor_Image.getCount() > 0) {
            cursor_Image.moveToLast();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PROFILE_IMAGE, image);
            getActivity().getContentResolver().update(CONTENT_CUSTOMER, contentValues, null, null);

        }
        cursor_Image.close();
    }

    private void getProfilePicture() {
        ///  if (CustomerController.checkUserLogin(getActivity())) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        } else {
            //loadImagefromGallery();
            UploadImageController.selectUploadType(getActivity());

        }
        /*} else {
            MyUtil.showMessageBox("Oops !", "Please login first", "OK", false, getActivity());

        }*/

    }
  /*


 public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

  *//*  public Uri getOutputMediaFileUri(int type) {
        return FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", (getOutputMediaFile(type)));
    }*//*


    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public void takePhoto(int token) {

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (fileUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        }
        this.startActivityForResult(intent, token);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //  showCameraBox("Please Select Image");
                loadImagefromGallery();

            } else
                Toast.makeText(getActivity(), "Please accept permission for go ahead.", Toast.LENGTH_LONG).show();
        }
    }

    private void savePhoto(String image) {
        Cursor cursor_Image = getActivity().getContentResolver().query(CONTENT_USER, null, null, null, null);
        Log.e("cursor_Image111", "==" + cursor_Image.getCount());
        if (cursor_Image.getCount() > 0) {
            cursor_Image.moveToLast();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_USER_PICTURE, image);
            getActivity().getContentResolver().update(CONTENT_USER, contentValues, null, null);

        }
        cursor_Image.close();
    }

    private void previewCapturedImage() {

        // hide video preview
        Log.e("fileUrifileUri", "==" + fileUri.getPath());

        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;

        final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                options);
        //here you can get base64 image

        savePhoto(fileUri.getPath());

        setProfileImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("fragment==" + resultCode, "onActivityResult==" + requestCode);
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getActivity(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            // Set the Image in ImageView after decoding the String
            Log.e("imgDecodableString", "==" + imgDecodableString);

            savePhoto(imgDecodableString);
            setProfileImage();

        } else {
            Toast.makeText(getActivity(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    }

*/
}
