package com.jyt.baseapp.model.impl;

import android.content.Context;
import android.text.TextUtils;

import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.model.AddressModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/16.
 */

public class AddressModelImpl implements AddressModel{
    Context context;
    @Override
    public void onStart(Context context) {
        this.context = context;
    }

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(context);
    }

    @Override
    public void getAddressList(Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.userAddressList).build().execute(callback);
    }

    @Override
    public void modifyAddress(String addressId, String contactPerson, String contactMobile, String address, String pr, String city, String area,boolean isdefault, Callback callback) {
        PostFormBuilder builder = OkHttpUtils.post().url(Api.domain+Api.modifyAddress)
                .addParams("contactPerson",contactPerson)
                .addParams("contactMobile",contactMobile)
                .addParams("address",address)
                .addParams("pr",pr)
                .addParams("city",city)
                .addParams("area",area)
                .addParams("isdefault",isdefault?"1":"0");

        if (!TextUtils.isEmpty(addressId)){
            builder.addParams("addressId",addressId);
        }
        builder.build().execute(callback);
    }

    @Override
    public void setDefaultAddress(String addressId, boolean isdefault, Callback callback) {
        OkHttpUtils.post().url(Api.domain+Api.setDefaultAddress).addParams("addressId",addressId).addParams("isdefault",isdefault?"1":"0").build().execute(callback);

    }

    @Override
    public void deleteAddress(String addressId, Callback callback) {
        OkHttpUtils.post().url(Api.domain+Api.deleteAddress).addParams("addressId",addressId).build().execute(callback);

    }
}
