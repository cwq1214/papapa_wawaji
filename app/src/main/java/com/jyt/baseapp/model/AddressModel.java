package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/16.
 */

public interface AddressModel extends BaseModel {
    /**
     * 我的地址
     * @param callback
     */
    public void getAddressList(Callback callback);

    /**
     *  修改、添加个人地址
     * @param addressId 地址id；修改地址的时候需要提供
     * @param contactPerson
     * @param contactMobile
     * @param address
     * @param pr    省
     * @param city  市
     * @param area  区
     * @param callback
     */
    public void modifyAddress(String addressId,String contactPerson,String contactMobile,String address,String pr,String city,String area,boolean isdefault,Callback callback);

    /**
     * 设置默认地址
     * @param addressId
     * @param isdefault
     * @param callback
     */
    public void setDefaultAddress(String addressId,boolean isdefault,Callback callback);

    /**
     * 删除地址
     * @param addressId
     * @param callback
     */
    public void deleteAddress(String addressId,Callback callback);
}
