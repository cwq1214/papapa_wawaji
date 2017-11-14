package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 充值金额
 * Created by chenweiqi on 2017/11/14.
 */

public class RechargePrice implements Parcelable {
    private String score;//获得的娃娃币数量
    private String money;//充值金额
    private String give;//赠送的娃娃币数量

    protected RechargePrice(Parcel in) {
        score = in.readString();
        money = in.readString();
        give = in.readString();
    }

    public static final Creator<RechargePrice> CREATOR = new Creator<RechargePrice>() {
        @Override
        public RechargePrice createFromParcel(Parcel in) {
            return new RechargePrice(in);
        }

        @Override
        public RechargePrice[] newArray(int size) {
            return new RechargePrice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(score);
        dest.writeString(money);
        dest.writeString(give);
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGive() {
        return give;
    }

    public void setGive(String give) {
        this.give = give;
    }
}
