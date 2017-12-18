package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/14.
 */

public interface RoomModel extends BaseModel {
    /**
     * 娃娃详情页
     * @param toyId
     * @param machineId
     * @param callback
     */
    void getToyDetailForAll(String toyId,String machineId,Callback callback);

    /**
     * 获取娃娃机状态和机器人数
     * @param machineId
     * @param callback
     */
    void getToyMachineStateAndPeople(String machineId,Callback callback);

    /**
     * 判断次机器是否可玩
     * @param machineId
     * @param callback
     */
    void play(String machineId, Callback callback);

    /**
     * 退出房间
     * @param machineId
     * @param callback
     */
    void quitRoom(String machineId,Callback callback);

    /**
     * 抓完娃娃后修改娃娃状态
     * @param machineId
     * @param callback
     */
    void afterGrabToy(String machineId,boolean caught,Callback callback);

    /**
     * 设置娃娃机状态为空闲
     * @param machineId
     * @param callback
     */
    void setMachineFree(String machineId,Callback callback);


}
