package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

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


    private String textReceiver;//是否发货：1代表是0代表不是
    private String trackingTime;//发货时间
    private String address;//地址
    private String contactPerson;//联系人
    private String contactMobile;//联系电话
    private String comfrimStatus;//确认状态：1代表是0代表不是
    private String status;//订单状态（中文）
    private List<OrderProgress> logistics;//物流信息


    private boolean isCheck = false;

    private String backFee;//可兑换的娃娃币

    private String freight;//邮费


    protected Order(Parcel in) {
        toyName = in.readString();
        toyImg = in.readString();
        orderNo = in.readString();
        createdTime = in.readString();
        sequeue = in.readString();
        orderType = in.readInt();
        textReceiver = in.readString();
        trackingTime = in.readString();
        address = in.readString();
        contactPerson = in.readString();
        contactMobile = in.readString();
        comfrimStatus = in.readString();
        status = in.readString();
        logistics = in.createTypedArrayList(OrderProgress.CREATOR);
        isCheck = in.readByte() != 0;
        backFee = in.readString();
        freight = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toyName);
        dest.writeString(toyImg);
        dest.writeString(orderNo);
        dest.writeString(createdTime);
        dest.writeString(sequeue);
        dest.writeInt(orderType);
        dest.writeString(textReceiver);
        dest.writeString(trackingTime);
        dest.writeString(address);
        dest.writeString(contactPerson);
        dest.writeString(contactMobile);
        dest.writeString(comfrimStatus);
        dest.writeString(status);
        dest.writeTypedList(logistics);
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeString(backFee);
        dest.writeString(freight);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getTextReceiver() {
        return textReceiver;
    }

    public void setTextReceiver(String textReceiver) {
        this.textReceiver = textReceiver;
    }

    public String getTrackingTime() {
        return trackingTime;
    }

    public void setTrackingTime(String trackingTime) {
        this.trackingTime = trackingTime;
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

    public String getComfrimStatus() {
        return comfrimStatus;
    }

    public void setComfrimStatus(String comfrimStatus) {
        this.comfrimStatus = comfrimStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderProgress> getLogistics() {
        return logistics;
    }

    public void setLogistics(List<OrderProgress> logistics) {
        this.logistics = logistics;
    }

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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getBackFee() {
        if (TextUtils.isEmpty(backFee)){
            return "0";
        }
        return backFee;
    }

    public void setBackFee(String backFee) {

        this.backFee = backFee;
    }

    public String getFreight() {
        if (TextUtils.isEmpty(freight)){
            return "0";
        }
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
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


}
