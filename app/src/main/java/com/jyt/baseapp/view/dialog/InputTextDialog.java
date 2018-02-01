package com.jyt.baseapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2018/2/1.
 */

public class InputTextDialog extends Dialog {
    @BindView(R.id.input_danmuku)
    EditText inputDanmuku;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;

    OnDialogBtnClickListener onDialogBtnClickListener;
    public InputTextDialog(@NonNull Context context) {
        this(context, R.style.customDialog);
    }

    public InputTextDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        setContentView(R.layout.dialog_input_text);
        ButterKnife.bind(this);
    }

    public void setOnDialogBtnClickListener(OnDialogBtnClickListener onDialogBtnClickListener) {
        this.onDialogBtnClickListener = onDialogBtnClickListener;
    }

    @OnClick({R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClicked(View view) {
        if (onDialogBtnClickListener==null){
            dismiss();
            return;
        }
        switch (view.getId()) {
            case R.id.btn_cancel:
                onDialogBtnClickListener.onClick(this,0,null);
                break;
            case R.id.btn_confirm:
                onDialogBtnClickListener.onClick(this,1,inputDanmuku.getText().toString());
                break;
        }
    }

    @Override
    public void show() {
        inputDanmuku.setText("");
        super.show();
    }

    public interface OnDialogBtnClickListener{
        void onClick(Dialog dialog,int index,String text);
    }
}
