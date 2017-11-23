package com.jyt.baseapp.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by chenweiqi on 2017/1/6.
 */

public class T {
    public static void showShort(Context context , String message){
        if (TextUtils.isEmpty(message)||"请求成功".equals(message)){
            return;
        }
        Toast t = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();
    }
}
