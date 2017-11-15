package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public interface PersonalInfoModel extends BaseModel {

    void getUserInfo(Callback callback);

    void modifyUserNameOrUserImage(String imgUrl,String username, Callback callback);

}
