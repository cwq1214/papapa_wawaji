package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class RechargeItem extends FrameLayout {

    @BindView(R.id.text_coin)
    TextView textCoin;
    @BindView(R.id.text_give)
    TextView textGive;
    @BindView(R.id.text_des)
    TextView textDes;
    @BindView(R.id.text_price)
    TextView textPrice;



    String price;
    String give;
    String coin;
    public RechargeItem(@NonNull Context context) {
        this(context, null);
    }

    public RechargeItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.widget_recharge_item, this, true);
        ButterKnife.bind(this);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RechargeItem);
        String price = typedArray.getString(R.styleable.RechargeItem_price);
        String give = typedArray.getString(R.styleable.RechargeItem_give);
        String coin = typedArray.getString(R.styleable.RechargeItem_coin);
        setPrice(price);
        setCoin(coin);
        setGive(give);
        typedArray.recycle();
    }


    public void setPrice(String price) {
        this.price = price;
        textPrice.setText("¥  " + price);
    }

    public void setCoin(String coin){
        this.coin = coin;
        textCoin.setText(coin);
    }

    public void setGive(String givePrice) {
        this.give = givePrice;
        boolean isEmpty = TextUtils.isEmpty(givePrice);
        if (!isEmpty) {
            textDes.setText("充" + price + "元送" + give + "个币");
            textGive.setText("+" + give);
        }
        textGive.setVisibility(isEmpty ? GONE : VISIBLE);
        textDes.setVisibility(isEmpty ? GONE : VISIBLE);
    }

    public String getPrice() {
        return price;
    }

    public String getGive() {
        return give;
    }

    public String getCoin() {
        return coin;
    }
}
