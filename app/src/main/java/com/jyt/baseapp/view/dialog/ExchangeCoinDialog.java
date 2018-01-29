package com.jyt.baseapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2018/1/6.
 */

public class ExchangeCoinDialog extends Dialog {
    @BindView(R.id.img_toy)
    ImageView imgToy;
    @BindView(R.id.text_toyCount)
    TextView textToyCount;
    @BindView(R.id.text_allCoin)
    TextView textAllCoin;
    @BindView(R.id.text_left)
    TextView textLeft;
    @BindView(R.id.text_right)
    TextView textRight;

    CenterImageDialog.OnButtonClickListener onButtonClickListener;


    public ExchangeCoinDialog(@NonNull Context context) {
        this(context, R.style.customDialog);
    }

    public ExchangeCoinDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        setContentView(R.layout.dialog_exchange_coin);
        ButterKnife.bind(this);
    }

    public void setToyImage(String url){
        Glide.with(getContext()).load(url).asBitmap().into(imgToy);
    }

    public void setTextToyCount(int count){
        if (count==1){
            textToyCount.setText("");
        }else {
            textToyCount.setText("共"+count+"个");
        }
    }

    public void setAllCoin(int coin){
        textAllCoin.setText(coin+"娃娃币");
    }

    public void setOnButtonClickListener(CenterImageDialog.OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    @OnClick({R.id.text_left, R.id.text_right})
    public void onViewClicked(View view) {
        if (onButtonClickListener==null){
            dismiss();
            return;
        }
        switch (view.getId()) {
            case R.id.text_left:
                onButtonClickListener.onClick(this,0);
                break;
            case R.id.text_right:
                onButtonClickListener.onClick(this,1);
                break;
        }
    }
}
