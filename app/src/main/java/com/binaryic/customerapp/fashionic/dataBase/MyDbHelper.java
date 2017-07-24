package com.binaryic.customerapp.fashionic.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_CATEGORY_NAME;
import static com.binaryic.customerapp.fashionic.common.Constants.COLUMN_SELLER_ID;
import static com.binaryic.customerapp.fashionic.common.Constants.*;

/**
 * Created by Asd on 23-09-2016.
 */
public class MyDbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "com.binaryic.customerapp";
    public static int DATABASE_VERSION = 1;

    static String DATABASE_HOME = "create table " + TABLE_HOME + "( " + COLLECTION_JSON + " text, " + SLIDER_JSON + " text, " + PROMOTIONAL_JSON + " text );";
    static String DATABASE_ADDRESS = "create table " + TABLE_ADDRESS + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_ID + " text, " + COLUMN_SELLER_ID + " text, "+ COLUMN_ADDRESS_ID + " text, "+ COLUMN_FIRST_NAME + " text, " +COLUMN_LAST_NAME + " text, "+ COLUMN_MOBILE_NUMBER + " text, "+ COLUMN_DEFAULT_ADDRESS + " text, " + COLUMN_ADDRESS + " text, " + COLUMN_CITY + " text, " + COLUMN_COUNTRY + " text, " + COLUMN_LANDMARK + " text, " + COLUMN_AREA + " text, " + COLUMN_STATE + " text, " + COLUMN_PINCODE   + " text );";
    static String DATABASE_CATEGORY = "create table " + TABLE_CATEGORY + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  + COLUMN_CATEGORY_ID + " text, " + COLUMN_CATEGORY_NAME + " text );";
    static String DATABASE_PRODUCT = "create table " + TABLE_PRODUCT + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SELLER_ID + " text, " + COLUMN_PRODUCT_ID + " text, " + COLUMN_DESCRIPTION + " text, " + COLUMN_QUANTITY + " text, " + COLUMN_SKU_CODE + " text, " + COLUMN_NAME + " text, " + COLUMN_COLLECTION + " text, "+ COLUMN_CATEGORY + " text, " + COLUMN_CATEGORY_ID + " text, " + COLUMN_SELL_PRICE + " text, " + COLUMN_IMAGES + " text, " + COLUMN_IS_FAV + " text, " + COLUMN_DISCOUNT_PRICE + " text );";
    static String DATABASE_SELLER = "create table " + TABLE_SELLER + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SELLER_ID + " text, " + COLUMN_MOBILE_NUMBER + " text, " + COLUMN_NAME + " text, " + COLUMN_ABOUT_US + " text, " + COLUMN_CHECKED + " text, " + COLUMN_ADDRESS + " text, " + COLUMN_CITY + " text, " + COLUMN_COUNTRY + " text, " + COLUMN_STATE + " text, " + COLUMN_PINCODE + " text, " + COLUMN_PANCARDNUMBER + " text, " + COLUMN_EMAILID + " text, " + COLUMN_FIRST_TIME + " text, " + COLUMN_IS_OTP_DONE + " text, " + COLUMN_COMPANY + " text, " + COLUMN_CATEGORY_NAME + " text, " + COLUMN_PROFILE_IMAGE + " text );";
    static String DATABASE_CUSTOMER = "create table " + TABLE_CUSTOMER + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_ID + " text, " + COLUMN_SELLER_ID + " text, "+ COLUMN_MOBILE_NUMBER + " text, " + COLUMN_FIRST_NAME + " text, " + COLUMN_LAST_NAME + " text, " + COLUMN_EMAILID + " text, " + COLUMN_ADDRESS + " text, " + COLUMN_CITY + " text, " + COLUMN_COUNTRY + " text, " + COLUMN_LANDMARK + " text, " + COLUMN_AREA + " text, " + COLUMN_STATE + " text, " + COLUMN_PINCODE + " text, " + COLUMN_IS_OTP_DONE + " text, " + COLUMN_PROFILE_IMAGE + " text );";
    static String DATABASE_ACCESS_TOKEN = "create table " + TABLE_ACCESS_TOKEN + "( " + COLUMN_ACCESS_TOKEN + " text );";
    static String DATABASE_WISHLIST = "create table " + TABLE_WISHLIST + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUCT_ID + " text, " + COLUMN_WISHLIST_PRODUCT + " text );";
    static String DATABASE_CART = "create table " + TABLE_CART + "( " +  COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUCT_ID + " text, " + COLUMN_NAME + " text, " + COLUMN_CART_QTY + " text );";
    static String DATABASE_BANNER = "create table " + TABLE_BANNER + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SELLER_ID + " text, " + COLUMN_BANNER_ID + " text, " + COLUMN_NAME + " text, " + COLUMN_SEQUENCE + " text, " + COLUMN_BANNER_TYPE + " text, " + COLUMN_CATEGORY_NAME + " text, " + COLUMN_CATEGORY_ID + " text, " + COLUMN_IMAGES + " text );";



    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(DATABASE_CATEGORY);
        db.execSQL(DATABASE_PRODUCT);
        db.execSQL(DATABASE_SELLER);
        db.execSQL(DATABASE_CUSTOMER);
        db.execSQL(DATABASE_ACCESS_TOKEN);
        db.execSQL(DATABASE_WISHLIST);
        db.execSQL(DATABASE_CART);
        db.execSQL(DATABASE_ADDRESS);
        db.execSQL(DATABASE_BANNER);
        db.execSQL(DATABASE_HOME);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELLER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCESS_TOKEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANNER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOME);

        onCreate(db);
    }
}
