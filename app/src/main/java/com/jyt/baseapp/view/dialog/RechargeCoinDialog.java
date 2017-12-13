package com.jyt.baseapp.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.json.RechargePrice;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.view.widget.RechargeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class RechargeCoinDialog extends AlertDialog {

    @BindView(R.id.text_cancel)
    TextView textCancel;
    @BindView(R.id.v_priceLayout)
    LinearLayout vPriceLayout;

    OnPriceClick onPriceClick;

    List<RechargePrice>  priceList;

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
        ButterKnife.bind(this);
        getWindow().setLayout((int) (ScreenUtils.getScreenWidth(getContext()) * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RechargePrice rechargePrice = (RechargePrice) v.getTag();
                onPriceClick.onPriceClick((RechargePrice) v.getTag());

            }
        };

        if (priceList!=null){
            vPriceLayout.removeAllViews();

            for (int i=0;i<priceList.size();i++){
                RechargeItem rechargeItem = new RechargeItem(getContext());
                rechargeItem.setCoin(priceList.get(i).getScore());
                rechargeItem.setPrice(priceList.get(i).getMoney());
                rechargeItem.setGive(priceList.get(i).getGive());
                rechargeItem.setTag(priceList.get(i));
                rechargeItem.setOnClickListener(onClickListener);
                vPriceLayout.addView(rechargeItem);
            }
        }
    }

    public void setPriceList(List<RechargePrice> priceList){
        this.priceList = priceList;
    }

    @OnClick({R.id.text_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_price5:
            case R.id.v_price10:
            case R.id.v_price20:
            case R.id.v_price50:
            case R.id.v_price100:
                onPriceClick.onPriceClick((RechargePrice) view.getTag());
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
        void onPriceClick(RechargePrice price);
    }
}
