package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenweiqi on 2017/11/16.
 */

public class Address implements Parcelable{
    private String addressId;//地址
    private String pr;//省份
    private String city;//市
    private String area;//区
    private String isdefault;//是否是默认地址:1是，0不是
    private String address;//详细地址
    private String contactPerson;//联系人
    private String contactMobile;//联系方式


    public Address() {
    }

    protected Address(Parcel in) {
        addressId = in.readString();
        pr = in.readString();
        city = in.readString();
        area = in.readString();
        isdefault = in.readString();
        address = in.readString();
        contactPerson = in.readString();
        contactMobile = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressId);
        dest.writeString(pr);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(isdefault);
        dest.writeString(address);
        dest.writeString(contactPerson);
        dest.writeString(contactMobile);
    }


    public boolean isDefault(){
        return "1".equals(isdefault);
    }

    public void setIsDefault(boolean isDefault){
        isdefault = isDefault?"1":"0";
    }
}
