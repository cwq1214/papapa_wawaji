package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.OrderItemViewHolder;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class OrderListAdapter extends BaseRcvAdapter{
    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderItemViewHolder(parent);
    }
}
