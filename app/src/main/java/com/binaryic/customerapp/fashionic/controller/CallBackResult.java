package com.binaryic.customerapp.fashionic.controller;

/**
 * Created by User on 07-07-2016.
 */
public interface CallBackResult<RESULT> {
    public void onSuccess(RESULT result);
    public void onError(String error);
}
