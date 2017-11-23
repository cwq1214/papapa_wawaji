package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.json.OrderProgress;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/23.
 */

public class OrderProgressItem extends FrameLayout {
    @BindView(R.id.text_order)
    TextView textOrder;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_date)
    TextView textDate;

    public OrderProgressItem(@NonNull Context context) {
        this(context, null);
    }

    public OrderProgressItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_order_progress, this, true);
        ButterKnife.bind(this);
    }


    public OrderProgressItem setData(OrderProgress orderProgress,int index){
        textOrder.setText(index+"");
        textTitle.setText(orderProgress.getMsg());
        textDate.setText(orderProgress.getTime());
        return this;
    }
}
