package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.UserInfo;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadEngine;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.upyun.library.utils.UpYunUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public class PersonalInfoModelImpl implements PersonalInfoModel {
    //空间名
    public static String SPACE = "papapa-img";
    //操作员
    public static String OPERATER = "jyt";
    //密码
    public static String PASSWORD = "12345678jyt";

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
        OkHttpUtils.get().url(Api.domain+Api.personalInfo).tag(context).build().execute(callback);
    }

    @Override
    public void modifyUserNameOrUserImage(String imgUrl, String username, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.modifyUserNameOrImage).tag(context).build().execute(callback);

    }

    @Override
    public void getUserCoinTransactionDetails(Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.userCoinDetail).tag(context).build().execute(callback);
    }

    @Override
    public void getChargeRole(Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.getChargeRole).addParams("token", UserInfo.getToken()).tag(context).build().execute(callback);

    }

    @Override
    public void modifyUserInfo(final String userImg, final String nickname, final Callback callback) {
        if (userImg!=null && !userImg.startsWith("http")){
            uploadImage(userImg, new UpCompleteListener() {
                @Override
                public void onComplete(boolean isSuccess, String result) {
                    L.e(result);
                    if (isSuccess){
//                        uploadToRemoteService(result,nickname,callback);

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String url = "http://papapa-img.test.upcdn.net"+jsonObject.getString("url");
                            uploadToRemoteService(url,nickname,callback);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
                        BaseJson baseJson = new BaseJson();
                        baseJson.setRet(false);
                        baseJson.setForUser("上传图片失败");
                        callback.onResponse(baseJson,0);
                    }
                }
            });
        }else {
            uploadToRemoteService(null,nickname,callback);
        }
    }


    private void uploadImage(String image , UpCompleteListener upCompleteListener){

        File file = new File(image);

        Map<String, Object> paramsMap = new HashMap<>();
        //上传空间
        paramsMap.put(Params.BUCKET, SPACE);
        //保存路径，任选其中一个
        paramsMap.put(Params.SAVE_KEY, "/Android/UserHead/"+UserInfo.getToken()+"-"+new Date().getTime()+image.substring(image.lastIndexOf(".")));

        UploadEngine.getInstance().formUpload(file, paramsMap, OPERATER, UpYunUtils.md5(PASSWORD),upCompleteListener, new UpProgressListener() {
            @Override
            public void onRequestProgress(long bytesWrite, long contentLength) {
                L.e(bytesWrite+"\t"+contentLength);
            }
        });

    }

    private void uploadToRemoteService(String userImg, String nickname, Callback callback){
        PostFormBuilder builder = OkHttpUtils.post().url(Api.domain+Api.modifyUserNameOrImage);

        if (userImg != null){
            builder.addParams("userImg",userImg);
        }

        if (nickname != null){
            builder.addParams("nickname",nickname);
        }
        builder.tag(context).build().execute(callback);
    }
}
