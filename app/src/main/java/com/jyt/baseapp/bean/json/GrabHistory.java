package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 抓取记录
 * Created by chenweiqi on 2017/11/14.
 */

public class GrabHistory implements Parcelable {
    private String nickname;// 昵称
    private String userImg;//头像
    private String createdTime;//时间

    protected GrabHistory(Parcel in) {
        nickname = in.readString();
        userImg = in.readString();
        createdTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeString(userImg);
        dest.writeString(createdTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GrabHistory> CREATOR = new Creator<GrabHistory>() {
        @Override
        public GrabHistory createFromParcel(Parcel in) {
            return new GrabHistory(in);
        }

        @Override
        public GrabHistory[] newArray(int size) {
            return new GrabHistory[size];
        }
    };

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
