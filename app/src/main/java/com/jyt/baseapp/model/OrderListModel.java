package com.jyt.baseapp.model;

/**
 * Created by v7 on 2017/11/13.
 */

public interface OrderListModel {
    public static final int TYPE_READY=0;
    public static final int TYPE_SEND=1;
    public static final int TYPE_FINISH=2;

    public void getOrderList(int type);
}
