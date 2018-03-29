package com.jyt.baseapp.api;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class Api {
//    public static final String domain = "http://192.168.3.16/babymc/";
    public static final String domain = "http://wwj.pppzww.cn/";

    public static final String domainText = "www.pppzww.cn";

    public static final String login = "api/login/do_login";

    public static final String HomeToy = domain + "api/home/toy_2";

    public static final String register = "api/login/register";

    public static final String resetPassword = "api/login/forget_pwd";

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
    public static final String receiveOrder = "api/order/confirm";

    //提交发货
    public static final String submitOrder = "api/order/submit_2";

    //获取我的娃娃币内容（娃娃币流水）
    public static final String userCoinDetail = "api/user/water";

    //我的地址
    public static final String userAddressList = "api/user/address";

    //修改、添加个人地址
    public static final String modifyAddress = "api/user/address";

    // 设置默认地址
    public static final String setDefaultAddress = "api/user/set_default_address";

    //删除地址
    public static final String deleteAddress = "api/user/del_address";

    //获取充值选项信息
    public static final String getChargeRole = "api/user/get_role";

    //微信充值
    public static final String chargeCoinByWeChart = "api/pay/wechat_pay";

    //支付宝充值
    public static final String chargeCoinByAli = "api/pay/alipay";

    //修改头像 昵称
    public static final String modifyUserInfo = "/api/user/update";

    //设置空闲
    public static final String setMachineFree = "api/toy/set_machine_status";

    //意见反馈
    public static final String submitFeedbackQues = "api/user/feedback";

    //输入邀请码加娃娃币
    public static final String submitInviteCode = "api/user/exchange_invite_code";

    //分享加积分
    public static final String afterShareToGetScore = "api/user/share_give";

    //签到
    public static final String sign = "api/user/sign";

    //检测今日是否已经签到
    public static final String isSign = "api/user/check_sign";

    public static final String exchangeCoin = "api/order/back_balance";

    public static final String checkedVersion = "api/login/check_version";
}
