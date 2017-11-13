package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.model.MainActModel;
import com.jyt.baseapp.util.UserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class MainActModelImpl implements MainActModel {
    @Override
    public void onStart(Context context) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getBanner(Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.getBanner).addParams("tokenSession", UserInfo.getToken()).build().execute(callback);
    }
}
