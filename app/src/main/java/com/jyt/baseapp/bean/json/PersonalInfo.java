package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public class PersonalInfo  implements Parcelable{
    private String userId;//id
    private String userImg;//头像
    private String nickname;//昵称
    private String userCode;//用户邀请码
    private String about;//关于我们
    private String useDesc;//使用说明
    private String contact;//联系我们
    private String version;//版本信息
    private String getCount;//成功抓取次数
    private String balance;//用户余额


    protected PersonalInfo(Parcel in) {
        userId = in.readString();
        userImg = in.readString();
        nickname = in.readString();
        userCode = in.readString();
        about = in.readString();
        useDesc = in.readString();
        contact = in.readString();
        version = in.readString();
        getCount = in.readString();
        balance = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userImg);
        dest.writeString(nickname);
        dest.writeString(userCode);
        dest.writeString(about);
        dest.writeString(useDesc);
        dest.writeString(contact);
        dest.writeString(version);
        dest.writeString(getCount);
        dest.writeString(balance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonalInfo> CREATOR = new Creator<PersonalInfo>() {
        @Override
        public PersonalInfo createFromParcel(Parcel in) {
            return new PersonalInfo(in);
        }

        @Override
        public PersonalInfo[] newArray(int size) {
            return new PersonalInfo[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUseDesc() {
        return useDesc;
    }

    public void setUseDesc(String useDesc) {
        this.useDesc = useDesc;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGetCount() {
        return getCount;
    }

    public void setGetCount(String getCount) {
        this.getCount = getCount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
