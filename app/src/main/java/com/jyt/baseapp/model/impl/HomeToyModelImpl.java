package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.model.HomeToyModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * @author LinWei on 2017/11/13 17:05
 */
public class HomeToyModelImpl implements HomeToyModel {
    Context context;
    @Override
    public void onStart(Context context) {
        this.context= context;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getHomeToyData(String count, String type,String sequeue , Callback callback) {
        OkHttpUtils.get().url(Api.HomeToy).addParams("count",count).addParams("type",type).addParams("sequeue",sequeue).tag(context).build().execute(callback);
    }
}
