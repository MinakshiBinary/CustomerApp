package com.binaryic.customerapp.fashionic.models;

/**
 * Created by user on 22-Jan-17.
 */
public class LoginModel {

    private int success;
    private String isLoginFirst;
    private String message;
    private String seller_name;
    private String mobile_Number;
    private String emailID;
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getMobile_Number() {
        return mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        this.mobile_Number = mobile_Number;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getIsLoginFirst() {
        return isLoginFirst;
    }

    public void setIsLoginFirst(String isLoginFirst) {
        this.isLoginFirst = isLoginFirst;
    }
}
