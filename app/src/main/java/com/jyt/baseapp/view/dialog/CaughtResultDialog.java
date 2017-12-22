package com.jyt.baseapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.CountDownUtil;
import com.jyt.baseapp.util.TimerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/30.
 */

public class CaughtResultDialog extends Dialog {
    @BindView(R.id.text_catchResult)
    TextView textCatchResult;
    @BindView(R.id.text_continue)
    TextView textContinue;
    @BindView(R.id.text_share)
    TextView textShare;
    @BindView(R.id.text_resultMessage)
    TextView textResultMessage;
    OnDialogBtnClick onDialogBtnClick;

    String title;
    String message;
    String continueText;

    Handler handler;

    CountDownUtil countDownUtil;

    public CaughtResultDialog(@NonNull Context context) {
        this(context, R.style.pinkDimDialog);
    }

    protected CaughtResultDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        countDownUtil = new CountDownUtil(getContext(),5,1000);

        countDownUtil.setCountDownCallback(new CountDownUtil.CountDownCallback() {
            @Override
            public void countDownCallback(boolean finish, int currentCount) {
                if (finish){
                        dismiss();
                }else {
                    textShare.setText("   关    闭("+currentCount+")");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_caught_result);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(title)) {
            textCatchResult.setText(title);
        }
        if (!TextUtils.isEmpty(message)) {
            textResultMessage.setText(message);
        }
        if (!TextUtils.isEmpty(continueText)){
            textContinue.setText(continueText);
        }
    }

    @Override
    public void show() {

        super.show();
        countDownUtil.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        countDownUtil.stop();
    }

    public void setOnDialogBtnClick(OnDialogBtnClick onDialogBtnClick) {
        this.onDialogBtnClick = onDialogBtnClick;
    }

    @OnClick({R.id.text_continue, R.id.text_share})
    public void onViewClicked(View view) {
        if (onDialogBtnClick == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.text_continue:
                onDialogBtnClick.onClick(this, 0, view);
                break;
            case R.id.text_share:
                onDialogBtnClick.onClick(this, 1, view);
                break;
        }
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContinueText(String continueText){
        this.continueText = continueText;
    }

    public interface OnDialogBtnClick {
        void onClick(Dialog dialog, int btnIndex, View btn);
    }

}
