package com.jyt.baseapp.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class ChoosePayTypeDialog extends AlertDialog {


    @BindView(R.id.v_weixin)
    LinearLayout vWeixin;
    @BindView(R.id.v_alipay)
    LinearLayout vAlipay;
    @BindView(R.id.text_cancel)
    TextView textCancel;

    OnPayTypeClickListener onPayTypeClickListener;

    protected ChoosePayTypeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_type);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.v_weixin,R.id.v_alipay})
    public  void onPayTypeClick(View v){
        int index = ((ViewGroup) v.getParent()).indexOfChild(v);
        if (onPayTypeClickListener!=null){
            onPayTypeClickListener.onPayTypeClick(index);
        }
    }
    public interface OnPayTypeClickListener{
        /**
         *
         * @param type type 为按钮的index
         */
        void onPayTypeClick(int type);
    }
}
