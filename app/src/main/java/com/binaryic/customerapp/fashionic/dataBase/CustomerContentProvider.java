package com.binaryic.customerapp.fashionic.dataBase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static com.binaryic.customerapp.fashionic.common.Constants.*;


/**
 * Created by Asd on 23-09-2016.
 */
public class CustomerContentProvider extends ContentProvider {

    private static final int CODE_CATEGORY = 1;
    private static final int CODE_PRODUCT = 2;
    private static final int CODE_SELLER = 3;
    private static final int CODE_CUSTOMER = 4;
    private static final int CODE_ACCESS_TOKEN = 5;
    private static final int CODE_WISHLIST = 6;
    private static final int CODE_CART = 7;
    private static final int CODE_ADDRESS = 8;
    private static final int CODE_BANNER = 9;
    private static final int CODE_HOME = 10;


    private MyDbHelper helper;
    private SQLiteDatabase database;
    private UriMatcher matcher;

    @Override
    public boolean onCreate() {
        helper = new MyDbHelper(getContext());
        database = helper.getWritableDatabase();
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, PATH_CATEGORY, CODE_CATEGORY);
        matcher.addURI(AUTHORITY, PATH_SELLER, CODE_SELLER);
        matcher.addURI(AUTHORITY, PATH_PRODUCT, CODE_PRODUCT);
        matcher.addURI(AUTHORITY, PATH_CUSTOMER, CODE_CUSTOMER);
        matcher.addURI(AUTHORITY, PATH_ACCESS_TOKEN, CODE_ACCESS_TOKEN);
        matcher.addURI(AUTHORITY, PATH_WISHLIST, CODE_WISHLIST);
        matcher.addURI(AUTHORITY, PATH_CART, CODE_CART);
        matcher.addURI(AUTHORITY, PATH_ADDRESS, CODE_ADDRESS);
        matcher.addURI(AUTHORITY, PATH_BANNER, CODE_BANNER);
        matcher.addURI(AUTHORITY, PATH_HOME, CODE_HOME);


        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = matcher.match(uri);
        Cursor cursor = null;
        if (code == CODE_CATEGORY) {
            cursor = database.query(TABLE_CATEGORY, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_SELLER) {
            cursor = database.query(TABLE_SELLER, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_PRODUCT) {
            cursor = database.query(TABLE_PRODUCT, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_CUSTOMER) {
            cursor = database.query(TABLE_CUSTOMER, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_ACCESS_TOKEN) {
            cursor = database.query(TABLE_ACCESS_TOKEN, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_WISHLIST) {
            cursor = database.query(TABLE_WISHLIST, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_CART) {
            cursor = database.query(TABLE_CART, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_ADDRESS) {
            cursor = database.query(TABLE_ADDRESS, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_BANNER) {
            cursor = database.query(TABLE_BANNER, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_HOME) {
            cursor = database.query(TABLE_HOME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = matcher.match(uri);
        if (code == CODE_CATEGORY) {
            database.insert(TABLE_CATEGORY, null, values);
        }
        if (code == CODE_SELLER) {
            database.insert(TABLE_SELLER, null, values);
        }
        if (code == CODE_PRODUCT) {
            database.insert(TABLE_PRODUCT, null, values);
        }
        if (code == CODE_CUSTOMER) {
            database.insert(TABLE_CUSTOMER, null, values);
        }
        if (code == CODE_ACCESS_TOKEN) {
            database.insert(TABLE_ACCESS_TOKEN, null, values);
        }
        if (code == CODE_WISHLIST) {
            database.insert(TABLE_WISHLIST, null, values);
        }
        if (code == CODE_CART) {
            database.insert(TABLE_CART, null, values);
        }
        if (code == CODE_ADDRESS) {
            database.insert(TABLE_ADDRESS, null, values);
        }
        if (code == CODE_BANNER) {
            database.insert(TABLE_BANNER, null, values);
        }
        if (code == CODE_HOME) {
            database.insert(TABLE_HOME, null, values);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = matcher.match(uri);
        int delete = 0;
        if (code == CODE_CATEGORY) {
            delete = database.delete(TABLE_CATEGORY, selection, selectionArgs);
        }
        if (code == CODE_SELLER) {
            delete = database.delete(TABLE_SELLER, selection, selectionArgs);
        }
        if (code == CODE_PRODUCT) {
            delete = database.delete(TABLE_PRODUCT, selection, selectionArgs);
        }
        if (code == CODE_CUSTOMER) {
            delete = database.delete(TABLE_CUSTOMER, selection, selectionArgs);
        }
        if (code == CODE_ACCESS_TOKEN) {
            delete = database.delete(TABLE_ACCESS_TOKEN, selection, selectionArgs);
        }
        if (code == CODE_WISHLIST) {
            delete = database.delete(TABLE_WISHLIST, selection, selectionArgs);
        }
        if (code == CODE_CART) {
            delete = database.delete(TABLE_CART, selection, selectionArgs);
        }
        if (code == CODE_ADDRESS) {
            delete = database.delete(TABLE_ADDRESS, selection, selectionArgs);
        }
        if (code == CODE_BANNER) {
            delete = database.delete(TABLE_BANNER, selection, selectionArgs);
        }
        if (code == CODE_HOME) {
            delete = database.delete(TABLE_HOME, selection, selectionArgs);
        }
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int code = matcher.match(uri);
        int row = 0;
        if (code == CODE_CATEGORY) {
            row = database.update(TABLE_CATEGORY, values, selection, selectionArgs);
        }
        if (code == CODE_SELLER) {
            row = database.update(TABLE_SELLER, values, selection, selectionArgs);
        }
        if (code == CODE_PRODUCT) {
            row = database.update(TABLE_PRODUCT, values, selection, selectionArgs);
        }
        if (code == CODE_CUSTOMER) {
            row = database.update(TABLE_CUSTOMER, values, selection, selectionArgs);
        }
        if (code == CODE_ACCESS_TOKEN) {
            row = database.update(TABLE_ACCESS_TOKEN, values, selection, selectionArgs);
        }
        if (code == CODE_WISHLIST) {
            row = database.update(TABLE_WISHLIST, values, selection, selectionArgs);
        }
        if (code == CODE_CART) {
            row = database.update(TABLE_CART, values, selection, selectionArgs);
        }
        if (code == CODE_ADDRESS) {
            row = database.update(TABLE_ADDRESS, values, selection, selectionArgs);
        }
        if (code == CODE_BANNER) {
            row = database.update(TABLE_BANNER, values, selection, selectionArgs);
        }
        if (code == CODE_HOME) {
            row = database.update(TABLE_HOME, values, selection, selectionArgs);
        }
        return row;
    }
}
