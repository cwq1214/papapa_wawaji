package com.jyt.baseapp.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.OrderItemViewHolder;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class OrderListAdapter extends BaseRcvAdapter{

    BaseViewHolder.OnViewHolderClickListener onConfirmReceiveGoodsClickListener;
    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        OrderItemViewHolder holder = new OrderItemViewHolder(parent);
        holder.setOnConfirmReceiveGoodsClickListener(onConfirmReceiveGoodsClickListener);
        return holder;
    }

    public void setOnConfirmReceiveGoodsClickListener(BaseViewHolder.OnViewHolderClickListener onConfirmReceiveGoodsClickListener) {
        this.onConfirmReceiveGoodsClickListener = onConfirmReceiveGoodsClickListener;
    }
}
