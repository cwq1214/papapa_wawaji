package com.jyt.baseapp.util;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class UserInfo extends UserInfoKey{
    private static UserInfo userInfo = new UserInfo();

    private UserInfo() {

    }

    public static UserInfo getInstance(){
        return userInfo;
    }

    public static void setToken(String token){
        Hawk.put(KEY_TOKEN,token);
    }

    public static String getToken(){
        return Hawk.get(KEY_TOKEN);
    }



    public static boolean isLogin(){
        return !TextUtils.isEmpty(getToken());
    }
}
