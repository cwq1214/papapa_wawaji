package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public interface PersonalInfoModel extends BaseModel {

    void getUserInfo(Callback callback);

    void modifyUserNameOrUserImage(String imgUrl,String username, Callback callback);

    /**
     * 获取我的娃娃币内容（娃娃币流水
     * @param callback
     */
    void getUserCoinTransactionDetails(Callback callback);

    /**
     * 获取充值按钮列表
     * @param callback
     */
    void getChargeRole(Callback callback);

    /**
     * 修改个人信息
     * @param userImg
     * @param nickname
     * @param callback
     */
    void modifyUserInfo(String userImg,String nickname,Callback callback);


    /**
     * 意见反馈
     * @param content
     * @param phone
     * @param callback
     */
    void submitFeedBack(String content,String phone,Callback callback);

    /**
     * 输入邀请码
     * @param userCode
     * @param callback
     */
    void submitInviteCode(String userCode,Callback callback);

    /**
     * 游戏后分享获取积分
     * @param callback
     */
    void afterShareToGetScore(Callback callback);

    /**
     * 签到
     * @param callback
     */
    void sign(Callback callback);


    /**
     * 今天是否已签到
     * @param callback
     */
    void isSign(Callback callback);

}
