package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/12/12.
 */

public interface PayModel extends BaseModel{
    /**
     * 微信充值娃娃币
     * @param payType
     * @param ruleId
     * @param callback
     */
    void chargeCoinByWeChart(String payType, String ruleId, Callback callback);

    /**
     * 支付宝充值娃娃币
     * @param payType
     * @param ruleId
     * @param callback
     */
    void chargeCoinByAli(String payType, String ruleId, Callback callback);

}
