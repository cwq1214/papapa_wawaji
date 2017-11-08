package com.jyt.baseapp.helper;

import android.content.Context;
import android.content.Intent;

import com.jyt.baseapp.view.activity.PersonCenterActivity;
import com.jyt.baseapp.view.activity.ResetPsdActivity;

/**
 * Created by chenweiqi on 2017/11/6.
 */

public class IntentHelper {


    public static void openResetPsdActivity(Context context){
        context.startActivity(getIntent(context, ResetPsdActivity.class));

    }

    public static void openPersonalActivity(Context context){
        context.startActivity(getIntent(context, PersonCenterActivity.class));
    }


    public static Intent getIntent(){
        return new Intent();
    }
    public static Intent getIntent(Context context,Class aClass){
        return new Intent(context,aClass);
    }
}
