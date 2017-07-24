package com.binaryic.customerapp.fashionic.models;



/**
 * Created by minakshi on 5/4/17.
 */

public class AddressModel {

    String address_Id;
    String customer_Id;
    String firstName;
    String lastName;
    String mobileNumber;
    String contry;
    String contryCode;
    String customer_Address;
    String landmark;
    String area;
    String pincode;
    String city;
    String state;
    boolean defaultAddress;

    public String getAddress_Id() {
        return address_Id;
    }

    public void setAddress_Id(String address_Id) {
        this.address_Id = address_Id;
    }

    public String getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getContryCode() {
        return contryCode;
    }

    public void setContryCode(String contryCode) {
        this.contryCode = contryCode;
    }

    public String getCustomer_Address() {
        return customer_Address;
    }

    public void setCustomer_Address(String customer_Address) {
        this.customer_Address = customer_Address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
