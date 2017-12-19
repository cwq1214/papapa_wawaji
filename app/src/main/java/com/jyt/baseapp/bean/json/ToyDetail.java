package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by chenweiqi on 2017/11/14.
 */

public class ToyDetail implements Parcelable {
    private String toyId;//娃娃id
    private String machineId;//  机器id
    private String toyImg;//娃娃图片
    private String toyName;//娃娃名称
    private String needPay;//所需娃娃币
    private String machineStatus;// 机器状态：0代表空闲，1代表占用
    private String toyDesc;//娃娃描述
    private String userBalance;//用户余额（娃娃币）
    private String mainFlowLink;//正面读流地址
    private String flankFlowLink;///侧面读流地址
    private List<RechargePrice> rule;//充值规则
    private List<GrabHistory> hist;//历史成功抓取记录
    private String remoteId;
    private String music;//1代表moinoi moinoi，2代表nyokki


    protected ToyDetail(Parcel in) {
        toyId = in.readString();
        machineId = in.readString();
        toyImg = in.readString();
        toyName = in.readString();
        needPay = in.readString();
        machineStatus = in.readString();
        toyDesc = in.readString();
        userBalance = in.readString();
        mainFlowLink = in.readString();
        flankFlowLink = in.readString();
        rule = in.createTypedArrayList(RechargePrice.CREATOR);
        hist = in.createTypedArrayList(GrabHistory.CREATOR);
        remoteId = in.readString();
        music = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toyId);
        dest.writeString(machineId);
        dest.writeString(toyImg);
        dest.writeString(toyName);
        dest.writeString(needPay);
        dest.writeString(machineStatus);
        dest.writeString(toyDesc);
        dest.writeString(userBalance);
        dest.writeString(mainFlowLink);
        dest.writeString(flankFlowLink);
        dest.writeTypedList(rule);
        dest.writeTypedList(hist);
        dest.writeString(remoteId);
        dest.writeString(music);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToyDetail> CREATOR = new Creator<ToyDetail>() {
        @Override
        public ToyDetail createFromParcel(Parcel in) {
            return new ToyDetail(in);
        }

        @Override
        public ToyDetail[] newArray(int size) {
            return new ToyDetail[size];
        }
    };

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getToyId() {
        return toyId;
    }

    public void setToyId(String toyId) {
        this.toyId = toyId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getToyImg() {
        return toyImg;
    }

    public void setToyImg(String toyImg) {
        this.toyImg = toyImg;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public String getNeedPay() {
        return needPay;
    }

    public void setNeedPay(String needPay) {
        this.needPay = needPay;
    }

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }

    public String getToyDesc() {
        return toyDesc;
    }

    public void setToyDesc(String toyDesc) {
        this.toyDesc = toyDesc;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getMainFlowLink() {
        return mainFlowLink;
    }

    public void setMainFlowLink(String mainFlowLink) {
        this.mainFlowLink = mainFlowLink;
    }

    public String getFlankFlowLink() {
        return flankFlowLink;
    }

    public void setFlankFlowLink(String flankFlowLink) {
        this.flankFlowLink = flankFlowLink;
    }

    public List<RechargePrice> getRule() {
        return rule;
    }

    public void setRule(List<RechargePrice> rule) {
        this.rule = rule;
    }

    public List<GrabHistory> getHist() {
        return hist;
    }

    public void setHist(List<GrabHistory> hist) {
        this.hist = hist;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }
}
