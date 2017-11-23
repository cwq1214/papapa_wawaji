package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by v7 on 2017/11/13.
 */

public interface OrderListModel extends BaseModel{
    public static final int TYPE_READY=1;
    public static final int TYPE_SEND=2;
    public static final int TYPE_FINISH=3;
    public static final int TYPE_UNSEL_ADDRESS=4;
    /**
     * 配送订单列表
     * @param type TYPE_READY TYPE_SEND TYPE_FINISH
     * @param sequeue
     * @param callback
     */
    public void getOrderList(int type, String sequeue, Callback callback);

    /**
     * 订单详情
     * @param orderNo
     * @param callback
     */
    public void getOrderDetail(String orderNo, Callback callback);

    /**
     * 确认订单
     * @param orderNo
     * @param callback
     */
    public void receiveOrder(String orderNo, Callback callback);


    /**+
     *  提交发货
     * @param orderNo
     * @param addressId
     * @param callback
     */
    public void submitOrder(String orderNo,String addressId,Callback callback);
}
