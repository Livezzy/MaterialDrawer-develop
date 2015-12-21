package com.mikepenz.materialdrawer.app.entity;

public class UserAddress {


    private String addressType;
    private String streetAddress;

    private String pinCode;

    private String landmark;

    private String addressNickname;

    private String phoneNumber;

    private String name;

    private boolean isDefaultAddress;

    private String state;

    private Location geoLocation;

    private String consumerNo;

    private String city;

    public boolean isDefaultAddress() {
        return isDefaultAddress;
    }

    public void setDefaultAddress(boolean isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressNickname() {
        return addressNickname;
    }

    public void setAddressNickname(String addressNickname) {
        this.addressNickname = addressNickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Location getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(Location geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "UserAddress [streetAddress = " + streetAddress + ", pinCode = "
                + pinCode + ", landmark = " + landmark + ", addressNickname = "
                + addressNickname + ", phoneNumber = " + phoneNumber
                + ", name = " + name + ", defaultAddress = " + isDefaultAddress
                + ", state = " + state + ", geoLocation = " + geoLocation
                + ", consumerNo = " + consumerNo + ", city = " + city + "]";
    }
}
