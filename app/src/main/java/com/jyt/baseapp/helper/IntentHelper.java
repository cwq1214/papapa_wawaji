package com.jyt.baseapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.jyt.baseapp.view.activity.AddressListActivity;
import com.jyt.baseapp.view.activity.DollDetailActivity;
import com.jyt.baseapp.view.activity.EditAddressActivity;
import com.jyt.baseapp.view.activity.LoginActivity;
import com.jyt.baseapp.view.activity.MainActivity;
import com.jyt.baseapp.view.activity.MyCoinActivity;
import com.jyt.baseapp.view.activity.OrderListActivity;
import com.jyt.baseapp.view.activity.PersonCenterActivity;
import com.jyt.baseapp.view.activity.ResetPsdActivity;
import com.jyt.baseapp.view.activity.RoomActivity;

/**
 * Created by chenweiqi on 2017/11/6.
 */

public class IntentHelper extends IntentKey{

    public static void openMainActivity(Context context){
        context.startActivity(getIntent(context, MainActivity.class));

    }

    public static void openLoginActivity(Context context){
        context.startActivity(getIntent(context, LoginActivity.class));
    }

    public static void openResetPsdActivity(Context context){
        context.startActivity(getIntent(context, ResetPsdActivity.class));

    }

    public static void openPersonalActivity(Context context){
        context.startActivity(getIntent(context, PersonCenterActivity.class));
    }

    public static void openMyCoinActivity(Context context){
        context.startActivity(getIntent(context, MyCoinActivity.class));

    }

    public static void openAddressListActivity(Context context){
        context.startActivity(getIntent(context, AddressListActivity.class));
    }
    public static void openAddressListActivityForResult(Object context,int requestCode){
        if (context instanceof Activity){
            ((Activity) context).startActivityForResult(getIntent((Context) context, AddressListActivity.class),requestCode);
        }else if (context instanceof Fragment){
            ((Fragment) context).startActivityForResult(getIntent(((Fragment) context).getContext(), AddressListActivity.class),requestCode);
        }
    }

    public static void openDollDetailActivity(Context context,Parcelable doll){
        Intent intent = getIntent(context, DollDetailActivity.class);
        intent.putExtra(KEY_DOLL,doll);
        context.startActivity(intent);
    }


    public static void openEditAddressActivity(Context context, Parcelable address){
        Intent intent = getIntent(context, EditAddressActivity.class);
        intent.putExtra(KEY_ADDRESS,address);
        context.startActivity(intent);
    }

    public static void openOrderListActivity(Context context){
        context.startActivity(getIntent(context, OrderListActivity.class));
    }

    public static void openRoomActivity(Context context,Parcelable room){
        Intent intent = getIntent(context, RoomActivity.class);
        intent.putExtra(KEY_ROOM,room);
        context.startActivity(intent);
    }

    public static Intent getIntent(){
        return new Intent();
    }
    public static Intent getIntent(Context context,Class aClass){
        return new Intent(context,aClass);
    }
}
