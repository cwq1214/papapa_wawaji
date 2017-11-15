package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.view.dialog.RechargeCoinDialog;
import com.jyt.baseapp.view.widget.CircleProgressView;
import com.jyt.baseapp.waWaJiControl.WaWaJiControlClient;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;


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
    @BindView(R.id.v_txCloudVideoView)
    TXCloudVideoView vTxCloudVideoView;
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

    //充值对话框
    RechargeCoinDialog rechargeCoinDialog;
    HomeToyResult homeToyResult;
    int currentRoom = 0;
    RoomModel roomModel;
    ToyDetail toyDetail;
    WaWaJiControlClient waWaJiControlClient;


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
        int dp_5 = DensityUtil.dpToPx(getContext(),5);

        vTxCloudVideoView.setRenderMode(TXCloudVideoView.ACCESSIBILITY_LIVE_REGION_ASSERTIVE);
        vTxCloudVideoView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (ScreenUtils.getScreenWidth(getContext())-dp_5*2)* 481 / 366));

        homeToyResult = getIntent().getParcelableExtra(IntentKey.KEY_ROOM);
        connectRoom();


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
        camUrl ="";

        textToyName.setText(toyDetail.getToyName());
        textBalance.setText(toyDetail.getUserBalance());
        textPrice.setText(toyDetail.getNeedPay()+"/次");
        createIpcam(camUrl.equals(toyDetail.getFlankFlowLink())?(camUrl=toyDetail.getMainFlowLink()):(camUrl=toyDetail.getFlankFlowLink()));
        createRechargeDialog(toyDetail.getRule());

        vWebView.loadData(toyDetail.getToyDesc(), "text/html", "UTF-8");

        waWaJiControlClient.start("4AAZU15J37FG6L4K111A","123456");
    }

    public void createIpcam(String url){
        //创建player对象
        TXLivePlayer mLivePlayer = new TXLivePlayer(getContext());
        //关键player对象与界面view
        mLivePlayer.setPlayerView(vTxCloudVideoView);
        mLivePlayer.startPlay(url, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);

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
//        vTxCloudVideoView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        vTxCloudVideoView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        vTxCloudVideoView.onDestroy();
    }

    @OnClick(R.id.img_back2)
    public void onBackClick() {
        onBackPressed();
    }


    @OnClick({R.id.img_changeChannel,R.id.img_help, R.id.v_changeRoom, R.id.v_recharge,  R.id.img_done})
    public void onViewClicked(View view) {
        if (!UserInfo.isLogin()) {
            IntentHelper.openLoginActivity(getContext());
            return;
        }
        switch (view.getId()) {
            case R.id.img_changeChannel:
                //更换视频源   主次摄像头切换
                createIpcam(camUrl.equals(toyDetail.getFlankFlowLink())?(camUrl=toyDetail.getMainFlowLink()):(camUrl=toyDetail.getFlankFlowLink()));
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
            case R.id.img_left:
                break;
            case R.id.img_right:
                break;
            case R.id.img_down:
                break;
            case R.id.img_done:
                break;
        }
    }

    @OnTouch({R.id.img_top, R.id.img_left, R.id.img_right, R.id.img_down,R.id.img_done})
    public boolean onBottomControlTouch(View v, MotionEvent event){
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            if (v==imgTop){
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_TOP);
            }else if (v==imgDown){
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_DOWN);
            }else if (v==imgLeft){
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_LEFT);
            }else if (v==imgRight){
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_RIGHT);
            }else if (v==imgDone){
                waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_CATCH);
            }
            L.e("touch down");
        }else if (action == MotionEvent.ACTION_UP){
            waWaJiControlClient.action_up();
            L.e("touch up");

        }
        return true;
    }

}
