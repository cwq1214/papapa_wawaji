package com.jyt.baseapp.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.HomeToyResult;
import com.jyt.baseapp.bean.json.RechargePrice;
import com.jyt.baseapp.bean.json.ToyDetail;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.RoomModel;
import com.jyt.baseapp.model.impl.RoomModelImpl;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.view.dialog.CaughtSuccessDialog;
import com.jyt.baseapp.view.dialog.RechargeCoinDialog;
import com.jyt.baseapp.view.widget.CircleProgressView;
import com.jyt.baseapp.waWaJiControl.WaWaJiControlClient;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;


/**
 * Created by chenweiqi on 2017/11/8.
 */
@ActivityAnnotation(showActionBar = false)
public class RoomActivity extends BaseActivity {
    @BindView(R.id.img_help)
    ImageView imgHelp;
    @BindView(R.id.v_player)
    TXCloudVideoView mVideoView;
    @BindView(R.id.img_back2)
    ImageView imgBack2;
    @BindView(R.id.text_roomIndex)
    TextView textRoomIndex;
    @BindView(R.id.v_changeRoom)
    LinearLayout vChangeRoom;
    @BindView(R.id.img_changeChannel)
    ImageView imgChangeChannel;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.img_down)
    ImageView imgDown;
    @BindView(R.id.v_circleProgress)
    CircleProgressView vCircleProgress;
    @BindView(R.id.img_done)
    ImageView imgDone;
    @BindView(R.id.v_controlLayout)
    RelativeLayout vControlLayout;
    @BindView(R.id.text_peopleCounts)
    TextView textPeopleCounts;
    @BindView(R.id.text_balance)
    TextView textBalance;
    @BindView(R.id.v_recharge)
    LinearLayout vRecharge;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.v_playLayout)
    RelativeLayout vPlayLayout;
    @BindView(R.id.img_sendDanMu)
    ImageView imgSendDanMu;
    @BindView(R.id.v_grabLayout)
    LinearLayout vGrabLayout;
    @BindView(R.id.v_webView)
    WebView vWebView;
    @BindView(R.id.v_webviewLayout)
    LinearLayout vWebviewLayout;
    @BindView(R.id.v_bottomControl)
    RelativeLayout vBottomControl;
    @BindView(R.id.text_toyName)
    TextView textToyName;
    @BindView(R.id.v_mainScrollView)
    ScrollView vMainScrollView;
    @BindView(R.id.v_doneLayout)
    RelativeLayout vDoneLayout;


    //充值对话框
    RechargeCoinDialog rechargeCoinDialog;
    HomeToyResult homeToyResult;
    int currentRoom = 0;
    RoomModel roomModel;
    ToyDetail toyDetail;
    WaWaJiControlClient waWaJiControlClient;

    String TAG = getClass().getSimpleName();

    private boolean isScreenOff;
    private boolean isBackground;
    TXLivePlayer mLivePlayer;
    String camUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        waWaJiControlClient = new WaWaJiControlClient(getContext());
        waWaJiControlClient.setOnWaWaPlayedListener(new WaWaJiControlClient.OnWaWaPlayedListener() {
            @Override
            public void onPlayed(boolean caught) {
                setCaughtResult(caught);
                showResultDialog(caught);
                setWaWaControl(false);
            }
        });
        int dp_5 = DensityUtil.dpToPx(getContext(), 5);

        FrameLayout parent = (FrameLayout) mVideoView.getParent();
        parent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int width = right - left;
                v.setLayoutParams(new RelativeLayout.LayoutParams(width, width * 481 / 366));
                v.removeOnLayoutChangeListener(this);
            }
        });


        homeToyResult = getIntent().getParcelableExtra(IntentKey.KEY_ROOM);
        connectRoom();


    }

    private void initPlayer(String url) {

        //创建player对象
        mLivePlayer = new TXLivePlayer(getActivity());

        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        //关键player对象与界面view
        mLivePlayer.setPlayerView(mVideoView);
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);

        mLivePlayer.startPlay(url, TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);

        mVideoView.setRotationX(180);
        mVideoView.setRotationY(180);
    }

    public void changeIpCamUrl(String url) {
//        mVideoView.setVideoPath(url);
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer.startPlay(url, TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);
        }

    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        roomModel = new RoomModelImpl();
        list.add(roomModel);
        return list;
    }

    //连接房间
    private void connectRoom() {
        if (homeToyResult.getMachineList() == null || homeToyResult.getMachineList().size() == 0) {
            return;
        }
        roomModel.getToyDetailForAll(homeToyResult.getToyId(), homeToyResult.getMachineList().get(currentRoom).getMachineId(), new BeanCallback<BaseJson<ToyDetail>>() {
            @Override
            public void response(boolean success, BaseJson<ToyDetail> response, int id) {
                if (response.isRet()) {
                    toyDetail = response.getData();
                    setRoomInfo(response.getData());
                }
            }
        });
    }

    //设置房间基本详情
    private void setRoomInfo(final ToyDetail toyDetail) {
        camUrl = "";

        textToyName.setText(toyDetail.getToyName());
        textBalance.setText(toyDetail.getUserBalance());
        textPrice.setText(toyDetail.getNeedPay() + "/次");

//        toyDetail.setMainFlowLink("rtmp://live.hkstv.hk.lxdns.com/live/hks");
//        toyDetail.setFlankFlowLink("rtmp://live.hkstv.hk.lxdns.com/live/hks");

        initPlayer(camUrl.equals(toyDetail.getFlankFlowLink()) ? (camUrl = toyDetail.getMainFlowLink()) : (camUrl = toyDetail.getFlankFlowLink()));

        createRechargeDialog(toyDetail.getRule());

        vWebView.loadData(toyDetail.getToyDesc(), "text/html; charset=UTF-8", "UTF-8");


    }


    public void createRechargeDialog(List<RechargePrice> rechargePrices) {
        rechargeCoinDialog = new RechargeCoinDialog(getContext());
        rechargeCoinDialog.setPriceList(rechargePrices);
        rechargeCoinDialog.setOnPriceClick(new RechargeCoinDialog.OnPriceClick() {
            @Override
            public void onPriceClick(int price) {

                if (rechargeCoinDialog != null)
                    rechargeCoinDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLivePlayer != null)
            mLivePlayer.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLivePlayer != null)
            mLivePlayer.pause();
//        mVideoView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLivePlayer != null)
            mLivePlayer.stopPlay(true);
        mVideoView.onDestroy();
        waWaJiControlClient.stop();

        if (toyDetail!=null)
        roomModel.quitRoom(toyDetail.getMachineId(), new BeanCallback() {
            @Override
            public void response(boolean success, Object response, int id) {
                L.e("quit");
            }
        });
    }

    @OnClick(R.id.img_back2)
    public void onBackClick() {
        onBackPressed();
    }


    @OnClick({R.id.v_playLayout, R.id.img_changeChannel, R.id.img_help, R.id.v_changeRoom, R.id.v_recharge, R.id.img_done})
    public void onViewClicked(View view) {
        if (!UserInfo.isLogin()) {
            IntentHelper.openLoginActivity(getContext());
            return;
        }
        switch (view.getId()) {
            case R.id.img_changeChannel:
                //更换视频源   主次摄像头切换
                changeIpCamUrl(camUrl.equals(toyDetail.getFlankFlowLink()) ? (camUrl = toyDetail.getMainFlowLink()) : (camUrl = toyDetail.getFlankFlowLink()));
                break;
            case R.id.img_help:
                break;
            case R.id.v_changeRoom:
                break;
            case R.id.v_recharge:

                if (rechargeCoinDialog != null)
                    rechargeCoinDialog.show();
                break;
            case R.id.img_top:
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_TOP);
                break;
            case R.id.v_playLayout:
                playWaWaJi();
                break;
        }
    }


    private void playWaWaJi() {
        roomModel.getMachineState(toyDetail.getMachineId(), new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                if (response.isRet()) {
                    setWaWaControl(true);
                    waWaJiControlClient.start("4AAZU15J37FG6L4K111A", "pppzww168");
                } else {

                    T.showShort(getContext(),response.getForUser());
                }
            }
        });

    }

    private void setWaWaControl(final boolean play) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vBottomControl.setVisibility(play ? View.VISIBLE : View.GONE);

                if (play){
                    vMainScrollView.scrollTo(0,0);
                }
                vMainScrollView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        return play;
                    }});
                vPlayLayout.setVisibility(!play?View.VISIBLE:View.GONE);
            }
        });


    }

    //娃娃机控制
    @OnTouch({R.id.img_top, R.id.img_left, R.id.img_right, R.id.img_down, R.id.img_done})
    public boolean onBottomControlTouch(View v, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            if (v == imgTop) {
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_TOP);
            } else if (v == imgDown) {
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_DOWN);
            } else if (v == imgLeft) {
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_LEFT);
            } else if (v == imgRight) {
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_RIGHT);
            } else if (v == imgDone) {
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_CATCH);
            }
            L.e("touch down");
        } else if (action == MotionEvent.ACTION_UP) {
            waWaJiControlClient.action_up();
            L.e("touch up");

        }
        return true;
    }

    /**
     * 抓取结果，显示对话框
     */
    private void showResultDialog(final boolean caught){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CaughtSuccessDialog successDialog = new CaughtSuccessDialog(getContext());
                successDialog.setOnDialogBtnClick(new CaughtSuccessDialog.OnDialogBtnClick() {
                    @Override
                    public void onClick(Dialog dialog,int btnIndex, View btn) {
                        dialog.dismiss();
                    }
                });
                successDialog.show();
                if (caught){

                }else {

                }
            }
        });

    }

    /**
     * 设置抓取结果
     */
    private void setCaughtResult(boolean caught){
        roomModel.afterGrabToy(toyDetail.getMachineId(), caught, new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {

            }
        });
    }

}
