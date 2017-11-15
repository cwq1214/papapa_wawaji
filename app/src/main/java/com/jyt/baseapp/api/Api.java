package com.jyt.baseapp.api;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class Api {
    public static final String domain = "http://192.168.3.16/babymc/";

    public static final String login = "api/login/do_login";

    public static final String HomeToy = domain + "api/home/toy";

    public static final String register = "api/login/register";

    //获取验证码
    public static final String getCode = "api/login/getVerifyCode";

    //首页轮播
    public static final String getBanner = "api/home";

    //娃娃详情页
    public static final String toyDetail = "api/toy/detail";

    //获取娃娃机状态和当前机器的人员数据
    public static final String getMachineStateAndPeople = "api/toy/get_machine_status";

    //抓娃娃之前请求服务器
    public static final String getMachineStateBeforeStart = "api/toy/play";

    //抓完娃娃后修改娃娃状态
    public static final String afterGrabToSetState = "api/toy/finish_play";

    //退出房间
    public static final String quitRoom = "api/toy/quit";

    //个人首页
    public static final String personalInfo = "api/user";

    //修改头像 名字
    public static final String modifyUserNameOrImage = "api/user/update";

    //配送订单列表
    public static final String orderList = "api/order";

    //订单详情
    public static final String orderDetail = "api/order/detail";

    //确认订单
    public static final String receiveOrder = "api/order/comfirm";
}
