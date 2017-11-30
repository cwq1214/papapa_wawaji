package com.jyt.baseapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/30.
 */

public class CaughtSuccessDialog extends Dialog {
    @BindView(R.id.text_catchResult)
    TextView textCatchResult;
    @BindView(R.id.text_continue)
    TextView textContinue;
    @BindView(R.id.text_share)
    TextView textShare;

    OnDialogBtnClick onDialogBtnClick;

    public CaughtSuccessDialog(@NonNull Context context) {
        this(context, R.style.pinkDimDialog);
    }

    protected CaughtSuccessDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_caught_result);
    }

    public void setOnDialogBtnClick(OnDialogBtnClick onDialogBtnClick) {
        this.onDialogBtnClick = onDialogBtnClick;
    }

    @OnClick({R.id.text_continue, R.id.text_share})
    public void onViewClicked(View view) {
        if (onDialogBtnClick==null){
            return;
        }
        switch (view.getId()) {
            case R.id.text_continue:
                onDialogBtnClick.onClick(this,0,view);
                break;
            case R.id.text_share:
                onDialogBtnClick.onClick(this,1,view);
                break;
        }
    }

    public interface OnDialogBtnClick {
        void onClick(Dialog dialog,int btnIndex, View btn);
    }

}
