package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.model.RoomModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/14.
 */

public class RoomModelImpl implements RoomModel {
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
    public void getToyDetailForAll(String toyId, String machineId, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.toyDetail).addParams("toyId",toyId).addParams("machineId",machineId).tag(context).build().execute(callback);
    }

    @Override
    public void getToyMachineStateAndPeople(String machineId, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.getMachineStateAndPeople).addParams("machineId",machineId).tag(context).build().execute(callback);
    }

    @Override
    public void getMachineState(String machineId, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.getMachineStateBeforeStart).addParams("machineId",machineId).tag(context).build().execute(callback);

    }

    @Override
    public void quitRoom(String machineId, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.quitRoom).addParams("machineId",machineId).tag(context).build().execute(callback);
    }

    @Override
    public void afterGrabToy(String machineId, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.afterGrabToSetState).addParams("machineId",machineId).tag(context).build().execute(callback);

    }
}
