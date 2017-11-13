package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.App;
import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.model.LoginLogoutModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;


/**
 * Created by chenweiqi on 2017/11/13.
 */

public class LoginLoutModelImpl implements LoginLogoutModel {


    @Override
    public void loginByMobile(String mobile, String pwd, Callback callback) {
        OkHttpUtils.post().url(Api.domain+Api.login).addParams("mobile",mobile).addParams("pwd",pwd).build().execute(callback);
    }

    @Override
    public void loginByWeiXin(String wechatId, String nickname, String userImg, Callback callback) {

    }

    @Override
    public void register(String mobile, String verifyCode, String pwd, Callback callback) {

    }

    @Override
    public void resetPassword(String mobile, String verifyCode, String pwd, Callback callback) {

    }

    @Override
    public void onStart(Context context) {

    }

    @Override
    public void onDestroy() {

    }
}
