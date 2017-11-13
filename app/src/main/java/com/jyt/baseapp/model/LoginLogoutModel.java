package com.jyt.baseapp.model;


import android.telecom.Call;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public interface LoginLogoutModel extends BaseModel{

    void loginByMobile(String mobile, String pwd, Callback callback);

    void loginByWeiXin(String wechatId, String nickname,String userImg, Callback callback);

    void register(String mobile,String verifyCode,String pwd,Callback callback);

    void resetPassword(String mobile,String verifyCode,String pwd,Callback callback);

    void getVerifyCode(String mobile, Callback callback);

}
