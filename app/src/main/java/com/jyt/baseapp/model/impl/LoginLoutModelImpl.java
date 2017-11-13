package com.jyt.baseapp.model.impl;

import com.jyt.baseapp.model.LoginLogoutModel;

import okhttp3.Callback;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class LoginLoutModelImpl implements LoginLogoutModel {


    @Override
    public void loginByMobile(String mobile, String pwd, Callback callback) {

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
}
