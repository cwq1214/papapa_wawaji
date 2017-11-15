package com.jyt.baseapp.view.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.json.Order;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

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

    BaseViewHolder.OnViewHolderClickListener onConfirmReceiveGoodsClickListener;

    public OrderItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_order_list_item, parent, false));
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        Order order = (Order) data;


        textGoodsName.setText(order.getToyName());
        textState.setText(order.getOrderTypeText());

        ImageLoader.getInstance().loadWhiteRadiusBorder(imgGoodsImg,order.getToyImg());
        textId.setText(order.getOrderNo());
        textDate.setText(order.getCreatedTime());

        if (order.getOrderType() == 2){
            textReceived.setVisibility(View.VISIBLE);
        }else {
            textReceived.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.text_received)
    public void onConfirmClick(){
        if (onConfirmReceiveGoodsClickListener!=null)
            onConfirmReceiveGoodsClickListener.onClick(this);
    }
    public void setOnConfirmReceiveGoodsClickListener(BaseViewHolder.OnViewHolderClickListener onConfirmReceiveGoodsClickListener) {
        this.onConfirmReceiveGoodsClickListener = onConfirmReceiveGoodsClickListener;
    }


}
