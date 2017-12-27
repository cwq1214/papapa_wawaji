package com.jyt.baseapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jyt.baseapp.bean.json.LoginResult;


/**
 * Created by chenweiqi on 2017/11/13.
 */

public class UserInfo extends UserInfoKey{
    private static UserInfo userInfo ;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private UserInfo(Context context) {
         sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
    }

    public static void init(Context context){
        userInfo = new UserInfo(context);
    }

    public static UserInfo getInstance(){
        return userInfo;
    }

    public static void setToken(String token){
        if (TextUtils.isEmpty(token)){
            remove(KEY_TOKEN);
        }else {
            put(KEY_TOKEN,token);
        }
    }

    public static String getShareLink(){
        return get(KEY_SHARE_LINK);
    }
    public static void setShareLink(String shareLink){
        put(KEY_SHARE_LINK,shareLink);
    }

    public static String getToken(){
        return get(KEY_TOKEN);
    }



    public static boolean isLogin(){
        return !TextUtils.isEmpty(getToken());
    }

    public static void setUserInfo(LoginResult result){
        setToken(result.getTokenSession());
        setShareLink(result.getInviteShare());
    }

    public static void setRoomBgEnable(boolean enable){
        editor.putBoolean(KEY_BGENABLE,enable);
        editor.commit();
    }
    public static boolean getRoomBgEnable(){
        return sharedPreferences.getBoolean(KEY_BGENABLE,true);
    }
    public static void setRoomEffectBgEnable(boolean enable){
        editor.putBoolean(KEY_EFENABLE,enable);
        editor.commit();
    }
    public static boolean getRoomEffectBgEnable(){
        return sharedPreferences.getBoolean(KEY_EFENABLE,true);
    }


    private static void remove(String key){
        editor.remove(key);
        editor.commit();
    }

    private static void put(String key,String value){

        if (value instanceof String){
            editor.putString(key, value);
            editor.commit();
        }else {
            throw new RuntimeException("暂不支持次类型储存");
        }
    }

    private static String get(String key){
        return get(key,null);
    }
    private static String get(String key,String defValue){
        return sharedPreferences.getString(key,defValue);
    }


    public static void clearUserInfo(){
        setToken(null);
    }
}
