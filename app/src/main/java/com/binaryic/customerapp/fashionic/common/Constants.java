package com.binaryic.customerapp.fashionic.common;

import android.net.Uri;

/**
 * Created by minakshi on 31/3/17.
 */

public class Constants {
    public static final String PAGE_SIZE = "10";
    public static final String page = "1";
    public static final int PICK_PHOTO = 1001;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1002;
    public static final String AUTHORITY = "com.binaryic.customerapp";
    public static final String CONETNT_PROTOCOL = "content://";
    public static final String seller_id = "50";
    public static final String SH_USER_ID ="USER_ID";


    public static final String PATH_ADDRESS = "PATH_ADDRESS";
    public static final String PATH_CATEGORY = "PATH_CATEGORY";
    public static final String PATH_PRODUCT = "PATH_PRODUCT";
    public static final String PATH_SELLER = "PATH_SELLER";
    public static final String PATH_CUSTOMER = "PATH_CUSTOMER";
    public static final String PATH_ACCESS_TOKEN = "PATH_ACCESS_TOKEN";
    public static final String PATH_WISHLIST= "PATH_WISHLIST";
    public static final String PATH_CART = "PATH_CART";
    public static final String PATH_BANNER = "PATH_BANNER";
    public static final String PATH_HOME = "PATH_HOME";

    public static String TABLE_HOME = "TABLE_HOME";
    public static String TABLE_BANNER = "TABLE_BANNER";
    public static String TABLE_ADDRESS = "TABLE_ADDRESS";
    public static String TABLE_CATEGORY = "TABLE_CATEGORY";
    public static String TABLE_PRODUCT = "TABLE_PRODUCT";
    public static String TABLE_SELLER = "TABLE_SELLER";
    public static String TABLE_CUSTOMER = "TABLE_CUSTOMER";
    public static String TABLE_ACCESS_TOKEN = "TABLE_ACCESS_TOKEN";
    public static String TABLE_WISHLIST = "TABLE_WISHLIST";
    public static String TABLE_CART = "TABLE_CART";

    public static String PROMOTIONAL_JSON = "PROMOTIONAL_JSON";
    public static String SLIDER_JSON = "SLIDER_JSON";
    public static String COLLECTION_JSON = "COLLECTION_JSON";
    public static String COLUMN_ID = "COLUMN_ID";
    public static String COLUMN_SELLER_ID = "COLUMN_SELLER_ID";
    public static String COLUMN_CATEGORY_NAME = "COLUMN_CATEGORY_NAME";
    public static String COLUMN_CATEGORY_ID = "COLUMN_CATEGORY_ID";
    public static String COLUMN_PRODUCT_ID = "COLUMN_PRODUCT_ID";
    public static String COLUMN_DESCRIPTION = "COLUMN_DESCRIPTION";
    public static String COLUMN_QUANTITY = "COLUMN_QUANTITY";
    public static String COLUMN_SKU_CODE = "COLUMN_SKU_CODE";
    public static String COLUMN_NAME = "COLUMN_NAME";
    public static String COLUMN_CATEGORY = "COLUMN_CATEGORY";
    public static String COLUMN_SELL_PRICE = "COLUMN_SELL_PRICE";
    public static String COLUMN_IMAGES = "COLUMN_IMAGES";
    public static String COLUMN_DISCOUNT_PRICE = "COLUMN_DISCOUNT_PRICE";
    public static String COLUMN_MOBILE_NUMBER = "COLUMN_MOBILE_NUMBER";
    public static String COLUMN_ABOUT_US = "COLUMN_ABOUT_US";
    public static String COLUMN_CHECKED = "COLUMN_CHECKED";
    public static String COLUMN_ADDRESS = "COLUMN_ADDRESS";
    public static String COLUMN_CITY = "COLUMN_CITY";
    public static String COLUMN_COUNTRY = "COLUMN_COUNTRY";
    public static String COLUMN_STATE = "COLUMN_STATE";
    public static String COLUMN_PINCODE = "COLUMN_PINCODE";
    public static String COLUMN_PANCARDNUMBER = "COLUMN_PANCARDNUMBER";
    public static String COLUMN_EMAILID = "COLUMN_EMAILID";
    public static String COLUMN_FIRST_TIME = "COLUMN_FIRST_TIME";
    public static String COLUMN_IS_OTP_DONE = "COLUMN_IS_OTP_DONE";
    public static String COLUMN_COMPANY = "COLUMN_COMPANY";
    public static String COLUMN_PROFILE_IMAGE = "COLUMN_PROFILE_IMAGE";
    public static String COLUMN_CUSTOMER_ID = "COLUMN_CUSTOMER_ID";
    public static String COLUMN_FIRST_NAME = "COLUMN_FIRST_NAME";
    public static String COLUMN_LAST_NAME = "COLUMN_LAST_NAME";
    public static String COLUMN_LANDMARK = "COLUMN_LANDMARK";
    public static String COLUMN_AREA = "COLUMN_AREA";
    public static String COLUMN_ACCESS_TOKEN = "COLUMN_ACCESS_TOKEN";
    public static String COLUMN_CART_ID = "COLUMN_CART_ID";
    public static String COLUMN_CART_QTY = "COLUMN_CART_QTY";
    public static String COLUMN_IS_FAV = "COLUMN_IS_FAV";
    public static String COLUMN_WISHLIST_PRODUCT = "COLUMN_WISHLIST_PRODUCT";
    public static String COLUMN_ADDRESS_ID = "COLUMN_ADDRESS_ID";
    public static String COLUMN_DEFAULT_ADDRESS = "COLUMN_DEFAULT_ADDRESS";
    public static String COLUMN_COLLECTION = "COLUMN_COLLECTION";
    public static String COLUMN_BANNER_ID = "COLUMN_BANNER_ID";
    public static String COLUMN_SEQUENCE = "COLUMN_SEQUENCE";
    public static String COLUMN_BANNER_TYPE = "COLUMN_BANNER_TYPE";

    public static final Uri CONTENT_HOME = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_HOME);
    public static final Uri CONTENT_BANNER = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_BANNER);
    public static final Uri CONTENT_ADDRESS = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_ADDRESS);
    public static final Uri CONTENT_CATEGORY = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_CATEGORY);
    public static final Uri CONTENT_PRODUCT = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_PRODUCT);
    public static final Uri CONTENT_SELLER = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_SELLER);
    public static final Uri CONTENT_CUSTOMER = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_CUSTOMER);
    public static final Uri CONTENT_ACCESS_TOKEN = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_ACCESS_TOKEN);
    public static final Uri CONTENT_WISHLIST = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_WISHLIST);
    public static final Uri CONTENT_CART = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_CART);


    public static final String GET_BANNERS_API = "http://sellerapp.binaryicdirect.com/v1/get_homepage";
    public static final String GET_ALL_CATEGORY_API = "http://sellerapp.binaryicdirect.com/v1/get_drawer_categories";
    public static final String GET_ACCESS_TOKEN_API = "http://sellerapp.binaryicdirect.com/v1/create_access_token";
    public static final String GET_PRODUCTS_API = "http://sellerapp.binaryicdirect.com/v1/get_Products";
    public static final String LOGINAPI = "http://sellerapp.binaryicdirect.com/v1/customer_register";
    public static final String GET_LOGIN_OTP_API = "http://sellerapp.binaryicdirect.com/v1/customer_login_otp";
    public static final String SELLER_INFO_API = "http://sellerapp.binaryicdirect.com/v1/get_SellerDetails";
    public static final String GET_SIGN_UP_OTP_API = "http://sellerapp.binaryicdirect.com/v1/customer_signup_otp";
    public static final String SIGNUP_API = "http://sellerapp.binaryicdirect.com/v1/customer_signup_details";
    public static final String GET_CATEGORY_PRODUCTS_API = "http://sellerapp.binaryicdirect.com/v1/get_category_products";
    public static final String GET_FILTER_PRODUCTS_API = "http://sellerapp.binaryicdirect.com/v1/customer_get_Products";
    public static final String APPLY_COUPON_API = "http://sellerapp.binaryicdirect.com/v1/coupon_apply";
    public static final String CREATE_ORDER_API = "http://sellerapp.binaryicdirect.com/v1/customer_orders_create";


    public static final String ADD_ADDRESS_API = "http://sellerapp.binaryicdirect.com/v1/customer_addresses";
    public static final String DELETE_ADDRESS_API = "http://sellerapp.binaryicdirect.com/v1/delete_address";
    public static final String UPDATE_ADDRESS_STATUS_API = "http://sellerapp.binaryicdirect.com/v1/update_address_status";
    public static final String EDIT_ADDRESS_API = "http://sellerapp.binaryicdirect.com/v1/edit_address";
    public static final String GET_ADDRESS_API = "http://sellerapp.binaryicdirect.com/v1/get_addresses";

}
