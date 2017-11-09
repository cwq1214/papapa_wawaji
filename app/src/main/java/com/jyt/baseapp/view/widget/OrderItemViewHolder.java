package com.jyt.baseapp.view.widget;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class OrderItemViewHolder extends BaseViewHolder {
    @BindView(R.id.text_goodsName)
    TextView textGoodsName;
    @BindView(R.id.text_state)
    TextView textState;
    @BindView(R.id.img_goodsImg)
    ImageView imgGoodsImg;
    @BindView(R.id.text_id)
    TextView textId;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_received)
    TextView textReceived;

    public OrderItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_order_list_item, parent, false));
    }
}
