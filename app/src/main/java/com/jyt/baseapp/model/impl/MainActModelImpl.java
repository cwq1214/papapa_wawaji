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
    public void getBanner(Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.getBanner).addParams("tokenSession", UserInfo.getToken()).tag(context).build().execute(callback);
    }
}
