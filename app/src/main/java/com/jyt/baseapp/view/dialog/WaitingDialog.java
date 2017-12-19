package com.jyt.baseapp.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;

import com.jyt.baseapp.R;

/**
 * Created by chenweiqi on 2017/12/18.
 */

public class WaitingDialog extends AlertDialog {
    public WaitingDialog(@NonNull Context context) {
        this(context, R.style.customDialog);
    }

    public WaitingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_waiting);


        getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
    }
}
