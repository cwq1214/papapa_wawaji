package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public class Order implements Parcelable{
    private String toyName;//
    private String toyImg ;//
    private String orderNo;//
    private String createdTime;
    private String sequeue;
    private int orderType;

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public String getToyImg() {
        return toyImg;
    }

    public void setToyImg(String toyImg) {
        this.toyImg = toyImg;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getSequeue() {
        return sequeue;
    }

    public void setSequeue(String sequeue) {
        this.sequeue = sequeue;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeText(){
        String text = null;
        if (orderType == 1){
            text = "待配送";
        }else if (orderType == 2){
            text = "配送中";
        }else if (orderType == 3){
            text = "已完成";
        }

        return text;
    }

    protected Order(Parcel in) {
        toyName = in.readString();
        toyImg = in.readString();
        orderNo = in.readString();
        createdTime = in.readString();
        sequeue = in.readString();
        orderType = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toyName);
        dest.writeString(toyImg);
        dest.writeString(orderNo);
        dest.writeString(createdTime);
        dest.writeString(sequeue);
        dest.writeInt(orderType);
    }
}
