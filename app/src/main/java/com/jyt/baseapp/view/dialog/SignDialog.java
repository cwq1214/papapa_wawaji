package com.jyt.baseapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/12/26.
 */

public class SignDialog extends Dialog {
    @BindView(R.id.text_add_coin)
    TextView textAddCoin;
    @BindView(R.id.text_add_coinDesc)
    TextView textAddCoinDesc;
    @BindView(R.id.text_done)
    TextView textDone;

    public SignDialog(@NonNull Context context) {
        this(context, R.style.customDialog);
    }

    public SignDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);


        setContentView(R.layout.dialog_sign);
        ButterKnife.bind(this);
    }

    public void setCoin(String coin){

        textAddCoin.setText("金币+"+coin);
        textAddCoinDesc.setText("获取"+coin+"个金币");
    }

    @OnClick(R.id.text_done)
    public void onDoneClick(){
        dismiss();
    }
}
