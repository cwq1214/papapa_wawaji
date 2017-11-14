package com.jyt.baseapp.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author LinWei on 2017/11/13 17:16
 */
public class HomeToyResult implements Parcelable{
    private String toyId;
    private String toyImg;
    private String toyName;
    private String needPay;
    private String leisure;
    private String useing;
    private List<Machine> machineList;

    protected HomeToyResult(Parcel in) {
        toyId = in.readString();
        toyImg = in.readString();
        toyName = in.readString();
        needPay = in.readString();
        leisure = in.readString();
        useing = in.readString();
        machineList = in.createTypedArrayList(Machine.CREATOR);
    }

    public static final Creator<HomeToyResult> CREATOR = new Creator<HomeToyResult>() {
        @Override
        public HomeToyResult createFromParcel(Parcel in) {
            return new HomeToyResult(in);
        }

        @Override
        public HomeToyResult[] newArray(int size) {
            return new HomeToyResult[size];
        }
    };

    public String getToyId() {
        return toyId;
    }

    public void setToyId(String toyId) {
        this.toyId = toyId;
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

    public String getLeisure() {
        return leisure;
    }

    public void setLeisure(String leisure) {
        this.leisure = leisure;
    }

    public String getUseing() {
        return useing;
    }

    public void setUseing(String useing) {
        this.useing = useing;
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public void setMachineList(List<Machine> machineList) {
        this.machineList = machineList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toyId);
        dest.writeString(toyImg);
        dest.writeString(toyName);
        dest.writeString(needPay);
        dest.writeString(leisure);
        dest.writeString(useing);
        dest.writeTypedList(machineList);
    }
}
