package com.jyt.baseapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2018/1/6.
 */

public class CenterImageDialog extends Dialog {
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.img_center)
    ImageView imgCenter;
    @BindView(R.id.text_desc)
    TextView textDesc;
    @BindView(R.id.text_left)
    TextView textLeft;
    @BindView(R.id.text_right)
    TextView textRight;

    OnButtonClickListener onButtonClickListener;
    public CenterImageDialog(@NonNull Context context) {
        this(context, R.style.customDialog);
    }

    public CenterImageDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_center_img);
        ButterKnife.bind(this);
    }

    public void setDialogTitle(String title){
        textTitle.setText(title);
    }

    public void setDialogCenterImgResId(int resId){
        imgCenter.setImageDrawable(getContext().getResources().getDrawable(resId));
    }

    public void setDialogDescText(String text){
        textDesc.setText(text);
    }

    public void setLeftBtnText(String text){
        textLeft.setText(text);
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

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public interface OnButtonClickListener {
        /**
         * @param index 0left 1right
         */
        void onClick(Dialog dialog,int index);
    }
}
