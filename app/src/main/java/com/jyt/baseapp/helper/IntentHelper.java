package com.jyt.baseapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.jyt.baseapp.bean.json.HomeToyResult;
import com.jyt.baseapp.util.FinishActivityManager;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.view.activity.AboutUsActivity;
import com.jyt.baseapp.view.activity.AddressListActivity;
import com.jyt.baseapp.view.activity.BrowserInviteCodeActivity;
import com.jyt.baseapp.view.activity.BrowserWebActivity;
import com.jyt.baseapp.view.activity.ContactUsActivity;
import com.jyt.baseapp.view.activity.DollDetailActivity;
import com.jyt.baseapp.view.activity.EditAddressActivity;
import com.jyt.baseapp.view.activity.FeedbackQuesActivity;
import com.jyt.baseapp.view.activity.InputInviteCodeActivity;
import com.jyt.baseapp.view.activity.LoginActivity;
import com.jyt.baseapp.view.activity.MainActivity;
import com.jyt.baseapp.view.activity.ModifyUserInfoActivity;
import com.jyt.baseapp.view.activity.MyCoinActivity;
import com.jyt.baseapp.view.activity.MyWaWaActivity;
import com.jyt.baseapp.view.activity.OrderDetailActivity;
import com.jyt.baseapp.view.activity.OrderListActivity;
import com.jyt.baseapp.view.activity.PersonCenterActivity;
import com.jyt.baseapp.view.activity.ResetPsdActivity;
import com.jyt.baseapp.view.activity.RoomActivity;
import com.jyt.baseapp.view.activity.VersionInfoActivity;

/**
 * Created by chenweiqi on 2017/11/6.
 */

public class IntentHelper extends IntentKey{

    public static void openBrowserWebActivity(Context context,String url){
        Intent intent = getIntent(context, BrowserWebActivity.class);
        intent.putExtra(IntentKey.KEY_URL,url);
        context.startActivity(intent);
    }

    public static void openBrowserInviteCodeActivity(Context context,Parcelable data){
        Intent intent = getIntent(context, BrowserInviteCodeActivity.class);
        intent.putExtra(IntentKey.KEY_DATA,data);
        context.startActivity(intent);
    }

    public static void openInputInviteCodeActivity(Context context,Parcelable data){
        Intent intent = getIntent(context, InputInviteCodeActivity.class);
        intent.putExtra(IntentKey.KEY_DATA,data);
        context.startActivity(intent);
    }

    public static void openFeedbackQuesActivity(Context context){
        Intent intent = getIntent(context, FeedbackQuesActivity.class);
        context.startActivity(intent);
    }

    public static void openContactUsActivity(Context context,Parcelable data){
        Intent intent = getIntent(context, ContactUsActivity.class);
        intent.putExtra(IntentKey.KEY_DATA,data);
        context.startActivity(intent);
    }

    public static void openAboutUsActivity(Context context,Parcelable data){
        Intent intent = getIntent(context, AboutUsActivity.class);
        intent.putExtra(IntentKey.KEY_DATA,data);
        context.startActivity(intent);

    }

    public static void openSelImageActivityForResult(Object context,int requestCode){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
//        openActivityForResult(context,intent,requestCode);
        Intent intent = getIntent();


    }

    public static void openModifyUserInfoActivity(String nickName,String userImage,Context context){
        Intent intent = getIntent(context, ModifyUserInfoActivity.class);
        intent.putExtra(IntentKey.KEY_IMAGE_URL,userImage);
        intent.putExtra(IntentKey.KEY_NAME,nickName);
        context.startActivity(intent);

    }
    public static void openMainActivity(Context context){
        FinishActivityManager.getManager().finishAllActivity();
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
        Intent intent;
        if (context instanceof Activity){
            intent = getIntent((Context) context, AddressListActivity.class);
            intent.putExtra(IntentKey.KEY_NEED_SET_RESULT,true);
            ((Activity) context).startActivityForResult(intent,requestCode);
        }else if (context instanceof Fragment){
            intent = getIntent((Context) context, AddressListActivity.class);
            intent.putExtra(IntentKey.KEY_NEED_SET_RESULT,true);
            ((Fragment) context).startActivityForResult(intent,requestCode);
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
        if (!UserInfo.isLogin()){
            openLoginActivity(context);
            return;
        }

        if (room instanceof HomeToyResult) {
            HomeToyResult homeToyResult = (HomeToyResult) room;
            if (homeToyResult.getMachineList() == null || homeToyResult.getMachineList().size() == 0) {
                T.showShort(context, "此房间内无可用机器");
                return;
            }
        }

        Intent intent = getIntent(context, RoomActivity.class);
        intent.putExtra(KEY_ROOM,room);
        context.startActivity(intent);
    }

    public static void openVersionInfoActivity(Context context){
        context.startActivity(getIntent(context, VersionInfoActivity.class));
    }

    public static void openMyWaWaActivity(Context context){
        context.startActivity(getIntent(context, MyWaWaActivity.class));

    }

    public static void openOrderDetailActivity(Object context , Parcelable order,int requestCode){
        Intent intent = getIntent();
        intent.putExtra(IntentKey.KEY_ORDER,order);
        openActivityForResult(context,OrderDetailActivity.class,intent,requestCode);
    }

    public static Intent getIntent(){
        return new Intent();
    }
    public static Intent getIntent(Context context,Class aClass){
        return new Intent(context,aClass);
    }
    private static void openActivityForResult(Object context,Class openClass,Intent intent,int requestCode){
        if (context instanceof Activity){
            intent.setClass((Context) context,openClass);
            ((Activity) context).startActivityForResult(intent,requestCode);
        }else if (context instanceof Fragment){
            intent.setClass(((Fragment) context).getContext(),openClass);
            ((Fragment) context).startActivityForResult(intent,requestCode);
        }


    }
    private static void openActivityForResult(Object context,Intent intent,int requestCode){
        if (context instanceof Activity){
            ((Activity) context).startActivityForResult(intent,requestCode);
        }else if (context instanceof Fragment){
            ((Fragment) context).startActivityForResult(intent,requestCode);
        }


    }
}
