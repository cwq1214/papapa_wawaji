package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.model.PayModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/12/12.
 */

public class PayModelImpl implements PayModel {
    Context mContext;
    @Override
    public void chargeCoinByWeChart(String payType, String ruleId, Callback callback) {
        OkHttpUtils.post().url(Api.domain+Api.chargeCoinByWeChart).addParams("payType",payType).addParams("ruleId",ruleId).tag(mContext).build().execute(callback);
    }

    @Override
    public void chargeCoinByAli(String payType, String ruleId, Callback callback) {
        OkHttpUtils.post().url(Api.domain+Api.chargeCoinByAli).addParams("payType",payType).addParams("ruleId",ruleId).tag(mContext).build().execute(callback);
    }

    @Override
    public void onStart(Context context) {
        this.mContext = context;
    }

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(mContext);
    }
}
