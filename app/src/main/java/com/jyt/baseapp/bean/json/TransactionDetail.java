package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 我的娃娃币内容（娃娃币流水）
 * Created by chenweiqi on 2017/11/16.
 */

public class TransactionDetail implements Parcelable{

    private String waterId;//流水id
    private String waterType;//    流水类型：1代表抓娃娃，2代表邀请码赠送，3代表签到，4代表分享
    private String fee;//金额
    private String createdTime;//时间
    private String sequeue;//排序字段
    private String content;//内容


    protected TransactionDetail(Parcel in) {
        waterId = in.readString();
        waterType = in.readString();
        fee = in.readString();
        createdTime = in.readString();
        sequeue = in.readString();
        content = in.readString();
    }

    public static final Creator<TransactionDetail> CREATOR = new Creator<TransactionDetail>() {
        @Override
        public TransactionDetail createFromParcel(Parcel in) {
            return new TransactionDetail(in);
        }

        @Override
        public TransactionDetail[] newArray(int size) {
            return new TransactionDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(waterId);
        dest.writeString(waterType);
        dest.writeString(fee);
        dest.writeString(createdTime);
        dest.writeString(sequeue);
        dest.writeString(content);
    }


    public String getWaterId() {
        return waterId;
    }

    public void setWaterId(String waterId) {
        this.waterId = waterId;
    }

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
