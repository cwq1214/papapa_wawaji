package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 物流信息
 * Created by chenweiqi on 2017/11/23.
 */

public class OrderProgress implements Parcelable{
    private String msg;
    private String time;


    public String getMsg() {
        return msg;
    }

    public String getTime() {
        return time;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected OrderProgress(Parcel in) {
        msg = in.readString();
        time = in.readString();
    }

    public static final Creator<OrderProgress> CREATOR = new Creator<OrderProgress>() {
        @Override
        public OrderProgress createFromParcel(Parcel in) {
            return new OrderProgress(in);
        }

        @Override
        public OrderProgress[] newArray(int size) {
            return new OrderProgress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
        dest.writeString(time);
    }
}
