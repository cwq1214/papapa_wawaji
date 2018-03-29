package com.jyt.baseapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.SoftInputUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2018/2/5.
 */

public class InputDanmakuDialog extends Dialog {
    @BindView(R.id.text_inputDanmaku)
    EditText textInputDanmaku;
    @BindView(R.id.btn_send)
    TextView btnSend;

    OnDialogBtnClickListener onDialogBtnClickListener;
    public InputDanmakuDialog(@NonNull Context context) {
        this(context, R.style.customDialog);
    }

    public InputDanmakuDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        getWindow().setGravity(Gravity.BOTTOM);

        setContentView(R.layout.dialog_input_danmaku);
        ButterKnife.bind(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void show() {
        SoftInputUtil.showSoftKeyboard(getContext(),textInputDanmaku);

        super.show();

    }

    @OnClick(R.id.btn_send)
    public void onBtnClick(){
        onDialogBtnClickListener.onClick(this,0,textInputDanmaku.getText().toString());
        textInputDanmaku.setText("");
    }

    public void setOnDialogBtnClickListener(OnDialogBtnClickListener onDialogBtnClickListener) {
        this.onDialogBtnClickListener = onDialogBtnClickListener;
    }

    public interface OnDialogBtnClickListener{
        void onClick(Dialog dialog,int index,String text);
    }
}
