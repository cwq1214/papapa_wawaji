package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public class PersonalInfoModelImpl implements PersonalInfoModel {
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
    public void getUserInfo(Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.personalInfo).build().execute(callback);
    }

    @Override
    public void modifyUserNameOrUserImage(String imgUrl, String username, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.modifyUserNameOrImage).build().execute(callback);

    }
}
