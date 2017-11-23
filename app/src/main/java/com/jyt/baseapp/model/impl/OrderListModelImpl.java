package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.model.OrderListModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public class OrderListModelImpl implements OrderListModel {
    Context context ;
    @Override
    public void onStart(Context context) {
        this.context = context;
    }

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(context);
    }

    @Override
    public void getOrderList(int type, String sequeue, Callback callback) {
        if (sequeue==null){
            sequeue = "";
        }
        OkHttpUtils.get().url(Api.domain+Api.orderList).addParams("type",type+"").addParams("sequeue",sequeue).tag(context).build().execute(callback);
    }

    @Override
    public void getOrderDetail(String orderNo, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.orderDetail).addParams("orderNo",orderNo).tag(context).build().execute(callback);
    }

    @Override
    public void receiveOrder(String orderNo, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.receiveOrder).addParams("orderNo",orderNo).tag(context).build().execute(callback);
    }

    @Override
    public void submitOrder(String orderNo, String addressId, Callback callback) {
        OkHttpUtils.post().url(Api.domain+Api.submitOrder).addParams("orderNo",orderNo).addParams("addressId",addressId).tag(context).build().execute(callback);

    }
}
