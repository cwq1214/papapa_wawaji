package com.jyt.baseapp.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.view.widget.RechargeItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class RechargeCoinDialog extends AlertDialog {
    @BindView(R.id.v_price5)
    RechargeItem vPrice5;
    @BindView(R.id.v_price10)
    RechargeItem vPrice10;
    @BindView(R.id.v_price20)
    RechargeItem vPrice20;
    @BindView(R.id.v_price50)
    RechargeItem vPrice50;
    @BindView(R.id.v_price100)
    RechargeItem vPrice100;
    @BindView(R.id.text_cancel)
    TextView textCancel;


    OnPriceClick onPriceClick;
    public RechargeCoinDialog(@NonNull Context context) {
        this(context, R.style.pinkDimDialog);
    }

    public RechargeCoinDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recharge);
        getWindow().setLayout((int) (ScreenUtils.getScreenWidth(getContext()) * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @OnClick({R.id.v_price5, R.id.v_price10, R.id.v_price20, R.id.v_price50, R.id.v_price100, R.id.text_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_price5:
            case R.id.v_price10:
            case R.id.v_price20:
            case R.id.v_price50:
            case R.id.v_price100:
                onPriceClick.onPriceClick(Integer.valueOf(((RechargeItem) view).getPrice()));
                break;
            case R.id.text_cancel:
                dismiss();
                break;
        }
    }

    public void setOnPriceClick(OnPriceClick onPriceClick) {
        this.onPriceClick = onPriceClick;
    }

    public interface OnPriceClick {
        void onPriceClick(int price);
    }
}
