package com.jyt.baseapp.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.jyt.baseapp.App;
import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.PayResult;
import com.jyt.baseapp.bean.json.AliPayResult;
import com.jyt.baseapp.bean.json.GrabHistory;
import com.jyt.baseapp.bean.json.HomeToyResult;
import com.jyt.baseapp.bean.json.Machine;
import com.jyt.baseapp.bean.json.MachineStateAndPeopleResult;
import com.jyt.baseapp.bean.json.PersonalInfo;
import com.jyt.baseapp.bean.json.RechargePrice;
import com.jyt.baseapp.bean.json.ToyDetail;
import com.jyt.baseapp.bean.json.User;
import com.jyt.baseapp.bean.json.WxPayResult;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.helper.WeChartHelper;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.PayModel;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.model.RoomModel;
import com.jyt.baseapp.model.impl.PayModelImpl;
import com.jyt.baseapp.model.impl.PersonalInfoModelImpl;
import com.jyt.baseapp.model.impl.RoomModelImpl;
import com.jyt.baseapp.util.CountDownUtil;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.TimerUtil;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.util.WaWaAudioPlayUtil;
import com.jyt.baseapp.view.dialog.CaughtResultDialog;
import com.jyt.baseapp.view.dialog.LoadingDialog;
import com.jyt.baseapp.view.dialog.PayDialog;
import com.jyt.baseapp.view.dialog.RechargeCoinDialog;
import com.jyt.baseapp.view.dialog.WaitingDialog;
import com.jyt.baseapp.view.widget.CircleProgressView;
import com.jyt.baseapp.view.widget.GrabRecordItemView;
import com.jyt.baseapp.waWaJiControl.WaWaJiControlClient;
import com.jyt.baseapp.zego.ZegoApiManager;
import com.jyt.baseapp.zego.ZegoStream;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoLivePlayerCallback;
import com.zego.zegoliveroom.callback.IZegoLivePublisherCallback;
import com.zego.zegoliveroom.callback.IZegoLoginCompletionCallback;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zego.zegoliveroom.constants.ZegoVideoViewMode;
import com.zego.zegoliveroom.entity.AuxData;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;
import com.zego.zegoliveroom.entity.ZegoStreamQuality;
import com.zego.zegoliveroom.entity.ZegoUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;


/**
 * Created by chenweiqi on 2017/11/8.
 */
@ActivityAnnotation(showActionBar = false)
public class RoomActivity extends BaseActivity {
    //region view
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
    @BindView(R.id.img_play)
    ImageView imgPlay;
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
    @BindView(R.id.text_time)
    TextView textTime;
    @BindView(R.id.textureview1)
    TextureView textureview1;
    @BindView(R.id.textureview2)
    TextureView textureview2;
    @BindView(R.id.v_videoView)
    FrameLayout vVideoView;
    @BindView(R.id.img_gif)
    ImageView imgGif;
    @BindView(R.id.v_start_play_layout)
    RelativeLayout vStartPlayLayout;
    @BindView(R.id.v_bgLayout)
    FrameLayout vBgLayout;
    @BindView(R.id.v_recordLayout)
    LinearLayout vRecordLayout;
    @BindView(R.id.v_selfPreview)
    TextureView vSelfPreview;
    @BindView(R.id.img_pppgif)
    ImageView imgPppgif;
    @BindView(R.id.img_help)
    ImageView imgHelp;
    @BindView(R.id.v_userHeaderImg)
    LinearLayout vUserHeaderImg;
    @BindView(R.id.v_peopleCountAndChangeRoomBackgroundLayout)
    View vPeopleCountAndChangeRoomBackgroundLayout;
    @BindView(R.id.v_peopleCountAndChangeRoomLayout)
    LinearLayout vPeopleCountAndChangeRoomLayout;
    //endregion
    //充值对话框
    RechargeCoinDialog rechargeCoinDialog;


    int currentRoom = 0;

    RoomModel roomModel;
    PayModel payModel;
    PersonalInfoModel personalInfoModel;

    //房间娃娃实体类
    ToyDetail toyDetail;
    HomeToyResult homeToyResult;


    //娃娃机控制
    WaWaJiControlClient waWaJiControlClient;

    String TAG = getClass().getSimpleName();

    WaWaAudioPlayUtil waWaAudioPlayUtil;



    private ZegoLiveRoom mZegoLiveRoom = ZegoApiManager.getInstance().getZegoLiveRoom();


    //结果对话框 判断是否继续
    boolean dialogPlayContinue;

    boolean playing;

    LoadingDialog loadingDialog;

    WaitingDialog waitingDialog;

    PayDialog payDialog;

    WeChartHelper weChartHelper;


    private List<ZegoStream> mListStream = new ArrayList<>();
    /**
     * 房间人数.
     */
    private TextView mTvRoomUserCount;
    /**
     * 当前是否从"加速服务器"拉流.
     */
    private boolean mIsPlayingStreamFromUltraSource = false;
    /**
     * 切换摄像头次数, 用于记录当前正在显示"哪一条流"
     */
    private int mSwitchCameraTimes = 0;
    /**
     * 当前排队人数.
     */
    private int mCurrentQueueCount = 0;

    //倒计时
    CountDownUtil countDownUtil;

    //定时循环
    TimerUtil timer;


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

        vUserHeaderImg.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(vPeopleCountAndChangeRoomLayout.getWidth(),vPeopleCountAndChangeRoomLayout.getHeight());
                vPeopleCountAndChangeRoomBackgroundLayout.setLayoutParams(params);
            }
        });

        homeToyResult = getIntent().getParcelableExtra(IntentKey.KEY_ROOM);
        if (homeToyResult.getMachineList() == null || homeToyResult.getMachineList().size() == 0) {
            T.showShort(getContext(), "此房间内无可用机器");
            finish();
            return;
        }

        checkPublishPermission();

        Glide.with(this).load(R.drawable.room_loading_gif).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).into(imgPppgif);
        Glide.with(this).load(R.mipmap.start).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgGif);

        waitingDialog = new WaitingDialog(getContext());
        waitingDialog.setCancelable(false);

        waWaAudioPlayUtil = new WaWaAudioPlayUtil();
        waWaAudioPlayUtil.init(getContext());


        weChartHelper = new WeChartHelper();
        weChartHelper.init(getContext(), App.weiXin_AppKey);
        weChartHelper.registerToWx();
        weChartHelper.setReceivePayResultListener(new WeChartHelper.ReceivePayResultListener() {
            @Override
            public void onPayResult(boolean payResult) {
                if (payResult) {
                    T.showShort(getContext(), "支付成功");

                    getSelfCoin();

                    if (payDialog != null) {
                        payDialog.dismiss();
                    }

                } else {
                    T.showShort(getContext(), "支付失败");
                }
            }
        });

        mZegoLiveRoom.setAudioDeviceMode(ZegoConstants.AudioDeviceMode.General);
        ZegoApiManager.getInstance().initSDK();
        // 设置开关，直接从 ZEGO 服务器拉流
//        String config = ZegoConstants.Config.PREFER_PLAY_ULTRA_SOURCE + "=1";
//        ZegoLiveRoom.setConfig(config);


        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.setCancelable(false);
        timer = new TimerUtil(getContext(), 3 * 1000);
        timer.setOnTimeCallback(new TimerUtil.OnTimeCallback() {
            @Override
            public void onTime() {
                roomModel.getToyMachineStateAndPeople(homeToyResult.getMachineList().get(currentRoom).getMachineId(), new BeanCallback<BaseJson<MachineStateAndPeopleResult>>() {
                    @Override
                    public void response(boolean success, BaseJson<MachineStateAndPeopleResult> response, int id) {
                        if (response.isRet()) {
                            setRoomRefreshInfo(response.getData());
                        }
                    }
                });
            }
        });


        countDownUtil = new CountDownUtil(getContext(), 30, 1000);
        countDownUtil.setCountDownCallback(new CountDownUtil.CountDownCallback() {
            @Override
            public void countDownCallback(boolean finish, int currentCount) {
                if (finish && playing) {
                    waitingDialog.show();
                    new Thread() {
                        @Override
                        public void run() {

                            if (UserInfo.getRoomEffectBgEnable() && waWaAudioPlayUtil != null) {
                                waWaAudioPlayUtil.play(WaWaAudioPlayUtil.TYPE_CATCH);
                            }
                            waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_CATCH);
                            countDownUtil.stop();
                            vCircleProgress.stop();
                        }
                    }.start();
                }
                if (!finish) {
                    textTime.setText(currentCount + "''");
                }
            }
        });
        waWaJiControlClient = new WaWaJiControlClient(getContext());
        waWaJiControlClient.setOnWaWaPlayedListener(new WaWaJiControlClient.OnWaWaPlayedListener() {
            @Override
            public void onPlayed(boolean caught) {
                waitingDialog.dismiss();
                countDownUtil.stop();
                setCaughtResult(caught);
                showResultDialog(caught);
                setWaWaControl(playing = false);


            }

            @Override
            public void start() {
                loadingDialog.dismiss();
                countDownUtil.start();
                setWaWaControl(playing = true);

                if (UserInfo.getRoomEffectBgEnable())
                    waWaAudioPlayUtil.play(WaWaAudioPlayUtil.TYPE_START);


                // 开启流量自动控制
                int properties = ZegoConstants.ZegoTrafficControlProperty.ZEGOAPI_TRAFFIC_FPS
                        | ZegoConstants.ZegoTrafficControlProperty.ZEGOAPI_TRAFFIC_RESOLUTION;
                mZegoLiveRoom.enableTrafficControl(properties, true);
//                mZegoLiveRoom.setPreviewView(vSelfPreview);
//                mZegoLiveRoom.startPreview();
                mZegoLiveRoom.enableMic(false);
                mZegoLiveRoom.enableCamera(false);
                mZegoLiveRoom.enableSpeaker(false);
                mZegoLiveRoom.setAudioChannelCount(0);
                mZegoLiveRoom.startPublishing(toyDetail.getMachineId() + UserInfo.getToken() + System.currentTimeMillis(), "主动连接", 0);

            }
        });
        int dp_5 = DensityUtil.dpToPx(getContext(), 5);

        FrameLayout parent = (FrameLayout) vVideoView.getParent();
        parent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int width = right - left;
                int srcWidth = 360;
                int scrHeight = 640;
                int cropHeight = DensityUtil.dpToPx(getContext(), 60);
                v.setLayoutParams(new RelativeLayout.LayoutParams(width, width * scrHeight / srcWidth - cropHeight));
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, width * scrHeight / srcWidth);
                params.setMargins(0, -cropHeight, 0, 0);
                textureview1.setLayoutParams(params);
                textureview2.setLayoutParams(params);

                v.setPadding(0, 0, 0, 0);

                v.removeOnLayoutChangeListener(this);


            }
        });
        vWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        vWebView.setEnabled(false);
        vWebView.getSettings().setUseWideViewPort(true);
        vWebView.getSettings().setLoadWithOverviewMode(true);
        vWebView.getSettings().setJavaScriptEnabled(true);
        vWebView.setScrollContainer(false);
        vWebView.setVerticalScrollBarEnabled(false);
        vWebView.setHorizontalScrollBarEnabled(false);
//        vWebView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });

//        homeToyResult.getMachineList().addAll( homeToyResult.getMachineList());
//        homeToyResult.getMachineList().addAll( homeToyResult.getMachineList());
        connectRoom();


    }

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }

    //初始化播放器
    private void initPlayer(String url) {
        mZegoLiveRoom.setRoomConfig(true, true);
        mZegoLiveRoom.loginRoom(homeToyResult.getMachineList().get(currentRoom).getMachineId(), ZegoConstants.RoomRole.Anchor, new IZegoLoginCompletionCallback() {
            @Override
            public void onLoginCompletion(int errCode, ZegoStreamInfo[] zegoStreamInfos) {
                if (errCode == 0) {
                    for (ZegoStreamInfo streamInfo : zegoStreamInfos) {
                        if (!TextUtils.isEmpty(streamInfo.extraInfo)) {
//                            CommandUtil.getInstance().printLog("[onLoginCompletion], streamID: " + streamInfo.streamID + ", extraInfo: " + streamInfo.extraInfo);

                            L.e("[onLoginCompletion], streamID: " + streamInfo.streamID + ", extraInfo: " + streamInfo.extraInfo);

                            ZegoUser zegoUser = new ZegoUser();
                            zegoUser.userID = streamInfo.userID;
                            zegoUser.userName = streamInfo.userName;
//                            CommandUtil.getInstance().setAnchor(zegoUser);


                            Map<String, Object> map = getMapFromJson(streamInfo.extraInfo);
                            if (map != null) {
                                int count = ((Double) map.get("queue_number")).intValue();
                                mCurrentQueueCount = count;

                                //人数
                                int total = ((Double) map.get("total")).intValue();

                            }
                        }
                    }
                    imgChangeChannel.setEnabled(true);

                    // 拉两路流
                    mListStream.get(0).playStream(100);
                    mListStream.get(1).playStream(0);

//                    mZegoLiveRoom.setViewRotation(90, mListStream.get(0).getStreamID());
//                    mZegoLiveRoom.setViewRotation(90, mListStream.get(1).getStreamID());

                    mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, mListStream.get(0).getStreamID());
                    mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, mListStream.get(1).getStreamID());
                }
                L.e("[loginRoom]" + "error code " + errCode);
            }
        });


//        switchPlaySource(true);
        mZegoLiveRoom.setZegoLivePlayerCallback(new IZegoLivePlayerCallback() {
            @Override
            public void onPlayStateUpdate(int errCode, String streamID) {

                int currentShowIndex = mSwitchCameraTimes % 2;

                if (errCode != 0) {
                    // 设置流的状态
                    for (ZegoStream zegoStream : mListStream) {
                        if (zegoStream.getStreamID().equals(streamID)) {
                            zegoStream.setStreamSate(ZegoStream.StreamState.PlayFail);
                            break;
                        }
                    }

                    ZegoStream currentShowStream = mListStream.get(currentShowIndex);
//                    if (currentShowStream.getStreamID().equals(streamID)) {
//                        mTvStreamSate.setText(currentShowStream.getStateString());
//                        mTvStreamSate.setVisibility(View.VISIBLE);
//                    }
                }
                L.e("[onPlayStateUpdate], streamID: " + streamID + " ,errorCode: " + errCode + ", currentShowIndex: " + currentShowIndex);
//                CommandUtil.getInstance().printLog("[onPlayStateUpdate], streamID: " + streamID + " ,errorCode: " + errCode + ", currentShowIndex: " + currentShowIndex);
            }

            @Override
            public void onPlayQualityUpdate(String streamID, ZegoStreamQuality zegoStreamQuality) {
                // 当前显示的流质量
//                if (mListStream.get(mSwitchCameraTimes % 2).getStreamID().equals(streamID)) {
//                    switch (zegoStreamQuality.quality) {
//                        case 0:
//                            mTvQuality.setText("网络优秀");
//                            mIvQuality.setImageResource(R.mipmap.excellent);
//                            mTvQuality.setTextColor(getResources().getColor(android.R.color.holo_green_light));
//                            break;
//                        case 1:
//                            mTvQuality.setText("网络流畅");
//                            mIvQuality.setImageResource(R.mipmap.good);
//                            mTvQuality.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
//                            break;
//                        case 2:
//                            mTvQuality.setText("网络缓慢");
//                            mIvQuality.setImageResource(R.mipmap.average);
//                            mTvQuality.setTextColor(getResources().getColor(R.color.bg_yellow_p));
//                            break;
//                        case 3:
//                            mTvQuality.setText("网络拥堵");
//                            mIvQuality.setImageResource(R.mipmap.pool);
//                            mTvQuality.setTextColor(getResources().getColor(R.color.text_red));
//                            break;
//                    }
//                    mIvQuality.setVisibility(View.VISIBLE);
//                    mTvQuality.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onInviteJoinLiveRequest(int i, String s, String s1, String s2) {

            }

            @Override
            public void onRecvEndJoinLiveCommand(String s, String s1, String s2) {

            }

            @Override
            public void onVideoSizeChangedTo(String streamID, int i, int i1) {
                for (ZegoStream zegoStream : mListStream) {
                    if (zegoStream.getStreamID().equals(streamID)) {
                        zegoStream.setStreamSate(ZegoStream.StreamState.PlaySuccess);
                        break;
                    }
                }

                int currentShowIndex = mSwitchCameraTimes % 2;
                ZegoStream currentShowStream = mListStream.get(currentShowIndex);
                if (currentShowStream.getStreamID().equals(streamID)) {
//                    mTvStreamSate.setVisibility(View.GONE);
                    currentShowStream.show();
                }

                if (mListStream != null && mListStream.size() != 0 && mListStream.get(0).isPlaySuccess()) {
                    vBgLayout.setVisibility(View.GONE);
                }
                L.e("[onVideoSizeChanged], streamID: " + streamID + ", currentShowIndex: " + currentShowIndex);
//                CommandUtil.getInstance().printLog("[onVideoSizeChanged], streamID: " + streamID + ", currentShowIndex: " + currentShowIndex);
            }
        });

        mZegoLiveRoom.setZegoLivePublisherCallback(new IZegoLivePublisherCallback() {
            @Override
            public void onPublishStateUpdate(int errCode, String streamID, HashMap<String, Object> hashMap) {
                L.e("[onPublishStateUpdate], streamID: " + streamID + ", errorCode: " + errCode);
            }

            @Override
            public void onJoinLiveRequest(int i, String s, String s1, String s2) {

            }

            @Override
            public void onPublishQualityUpdate(String s, ZegoStreamQuality zegoStreamQuality) {

            }

            @Override
            public AuxData onAuxCallback(int i) {
                return null;
            }

            @Override
            public void onCaptureVideoSizeChangedTo(int i, int i1) {

            }

            @Override
            public void onMixStreamConfigUpdate(int i, String s, HashMap<String, Object> hashMap) {

            }
        });

//        mZegoLiveRoom.setZegoRoomCallback(new IZegoRoomCallback() {
//            @Override
//            public void onKickOut(int reason, String roomID) {
//
//            }
//
//            @Override
//            public void onDisconnect(int errorCode, String roomID) {
//            }
//
//            @Override
//            public void onReconnect(int i, String s) {
//
//            }
//
//            @Override
//            public void onTempBroken(int i, String s) {
//
//            }
//
//            @Override
//            public void onStreamUpdated(final int type, final ZegoStreamInfo[] listStream, final String roomID) {
//            }
//
//            @Override
//            public void onStreamExtraInfoUpdated(ZegoStreamInfo[] zegoStreamInfos, String s) {
//
//            }
//
//            @Override
//            public void onRecvCustomCommand(String userID, String userName, String content, String roomID) {
//                // 只接收当前房间，当前主播发来的消息
////                if (CommandUtil.getInstance().isCommandFromAnchor(userID) && mRoom.roomID.equals(roomID)) {
//                    if (!TextUtils.isEmpty(content)) {
//                        L.e(content);
////                        handleRecvCustomCMD(content);
//                    }
////                }
//            }
//        });
    }

    private void doLogout() {

        // 回复从CDN拉流
        ZegoLiveRoom.setConfig(ZegoConstants.Config.PREFER_PLAY_ULTRA_SOURCE + "=0");

        for (ZegoStream zegoStream : mListStream) {
            zegoStream.stopPlayStream();
        }

        mZegoLiveRoom.stopPublishing();
        mZegoLiveRoom.logoutRoom();
//        CommandUtil.getInstance().reset();

//        if (mCountDownTimerConfirmBoard != null) {
//            mCountDownTimerConfirmBoard.cancel();
//        }
//
//        if (mCountDownTimerBoarding != null) {
//            mCountDownTimerBoarding.cancel();
//        }
    }

    private Map<String, Object> getMapFromJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map = gson.fromJson(json, map.getClass());

        return map;
    }

    //换流
    public void changeIpCamUrl(String url) {
        L.e("change channel");
        mSwitchCameraTimes++;

//        mTvStreamSate.setVisibility(View.GONE);

        int currentShowIndex = mSwitchCameraTimes % 2;
        if (currentShowIndex == 1) {
            // 隐藏第一路流
            mListStream.get(0).hide();
            // 显示第二路流
            if (mListStream.get(1).isPlaySuccess()) {
                mListStream.get(1).show();
                vBgLayout.setVisibility(View.GONE);
            } else {
                vBgLayout.setVisibility(View.VISIBLE);
//                mTvStreamSate.setText(mListStream.get(1).getStateString());
//                mTvStreamSate.setVisibility(View.VISIBLE);
            }
        } else {
            // 隐藏第二路流
            mListStream.get(1).hide();
            // 显示第一路流
            if (mListStream.get(0).isPlaySuccess()) {
                mListStream.get(0).show();
                vBgLayout.setVisibility(View.GONE);

            } else {
                vBgLayout.setVisibility(View.VISIBLE);
//                mTvStreamSate.setText(mListStream.get(0).getStateString());
//                mTvStreamSate.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 初始化流信息. 当前, 一个房间内只需要2条流用于播放，少了用"空流"替代.
     */
    private void initStreamList(String stream1, String stream2) {

        mListStream.add(constructStream(0, stream1));
        mListStream.add(constructStream(1, stream2));

    }

    private ZegoStream constructStream(int index, String streamID) {
//        if (streamID.startsWith("rtmp://")) {
        streamID = streamID.substring(streamID.lastIndexOf("/") + 1);
//        }

        String sateStrings[];
        TextureView textureView;
        if (index == 0) {
            sateStrings = getResources().getStringArray(R.array.video1_state);
            textureView = textureview1;
        } else {
            sateStrings = getResources().getStringArray(R.array.video2_state);
            textureView = textureview2;
        }
        return new ZegoStream(streamID, textureView, sateStrings);
    }


    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        roomModel = new RoomModelImpl();
        list.add(roomModel);
        list.add(payModel = new PayModelImpl());
        list.add(personalInfoModel = new PersonalInfoModelImpl());
        return list;
    }

    //连接房间
    private void connectRoom() {
        if (waWaAudioPlayUtil != null) {
            waWaAudioPlayUtil.stopPlayAction();
            waWaAudioPlayUtil.stopPlayBackgroundMusic();
        }

        if (homeToyResult.getMachineList() == null || homeToyResult.getMachineList().size() == 0) {
            return;
        }
        loadingDialog.show();
        roomModel.getToyDetailForAll(homeToyResult.getToyId(), homeToyResult.getMachineList().get(currentRoom).getMachineId(), new BeanCallback<BaseJson<ToyDetail>>() {
            @Override
            public void response(boolean success, BaseJson<ToyDetail> response, int id) {
                if (response.isRet()) {
                    toyDetail = response.getData();
                    setRoomInfo(response.getData());
                    if (timer != null) {
                        timer.stop();
                        timer.start();

                    }
                }
                loadingDialog.dismiss();
            }
        });
    }

    //设置房间基本详情
    private void setRoomInfo(final ToyDetail toyDetail) {
        String camUrl = "";

        if (waWaAudioPlayUtil != null) {
            waWaAudioPlayUtil.stopPlayAction();
            waWaAudioPlayUtil.stopPlayBackgroundMusic();
        }
        if (UserInfo.getRoomBgEnable())
            waWaAudioPlayUtil.setBackgroundMusicIndex(Integer.valueOf(toyDetail.getMusic()));
//        waWaAudioPlayUtil.playBackgroundMusic();

        textToyName.setText(toyDetail.getToyName());
        textBalance.setText(toyDetail.getUserBalance());
        textPrice.setText(toyDetail.getNeedPay() + "/次");


        vRecordLayout.removeAllViews();
        for (GrabHistory grabHistory : toyDetail.getHist()) {

            GrabRecordItemView grabRecordItemView = new GrabRecordItemView(getContext());
            grabRecordItemView.setBean(grabHistory);
            vRecordLayout.addView(grabRecordItemView);
        }

//        toyDetail.setMainFlowLink("rtmp://live.hkstv.hk.lxdns.com/live/hks");
//        toyDetail.setFlankFlowLink("rtmp://live.hkstv.hk.lxdns.com/live/hks");

        initStreamList(toyDetail.getMainFlowLink(), toyDetail.getFlankFlowLink());
        initPlayer(null);


        createRechargeDialog(toyDetail.getRule());

        vWebView.loadData(toyDetail.getToyDesc(), "text/html; charset=UTF-8", "UTF-8");


    }

    //设置房间刷新数据
    public void setRoomRefreshInfo(MachineStateAndPeopleResult machineStateAndPeopleResult) {
        textRoomIndex.setText(currentRoom + 1 + "");
        if (machineStateAndPeopleResult != null) {

            if ("0".equals(machineStateAndPeopleResult.getMachineStatus())) {
//            if (System.currentTimeMillis()%2==0){
                System.out.println("1");
                imgPlay.setImageDrawable(getResources().getDrawable(R.mipmap.play));
            } else {
                System.out.println("2");

                imgPlay.setImageDrawable(getResources().getDrawable(R.mipmap.startgamenoctrl));
            }


            textPeopleCounts.setText(machineStateAndPeopleResult.getRoomPeopleCount());

            vUserHeaderImg.removeAllViews();
            for (User browser : machineStateAndPeopleResult.getUser()) {


                ImageView imageView = new ImageView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dpToPx(getContext(), 30), DensityUtil.dpToPx(getContext(), 30));
                params.setMargins(4, 0, 0, 0);
                imageView.setLayoutParams(params);
                ImageLoader.getInstance().loadHeader(imageView, browser.getUserImg());
                vUserHeaderImg.addView(imageView);
            }
        }
    }


    public void createRechargeDialog(List<RechargePrice> rechargePrices) {
        rechargeCoinDialog = new RechargeCoinDialog(getContext());
        rechargeCoinDialog.setPriceList(rechargePrices);
        rechargeCoinDialog.setOnPriceClick(new RechargeCoinDialog.OnPriceClick() {
            @Override
            public void onPriceClick(final RechargePrice price) {
                rechargeCoinDialog.dismiss();

                payDialog = new PayDialog(getContext());
                payDialog.setOnDoneClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {//wx
                            payModel.chargeCoinByWeChart("1", price.getRuleId(), new BeanCallback<BaseJson<WxPayResult>>() {
                                @Override
                                public void response(boolean success, BaseJson<WxPayResult> response, int id) {
                                    if (response.isRet()) {
                                        weChartHelper.pay(response.getData().getPartnerId(), response.getData().getPrepayId(), response.getData().getTimeStamp(), response.getData().getNonceStr(), response.getData().getPaySign());
                                    } else {
                                        T.showShort(getContext(), response.getForUser());
                                    }

                                }
                            });
                        } else if (which == 2) {//ali
                            payModel.chargeCoinByAli("1", price.getRuleId(), new BeanCallback<BaseJson<AliPayResult>>() {
                                @Override
                                public void response(boolean success, final BaseJson<AliPayResult> response, int id) {
                                    if (response.isRet()) {
                                        Runnable payRunnable = new Runnable() {

                                            @Override
                                            public void run() {
                                                PayTask alipay = new PayTask(getActivity());
                                                Map<String, String> result = alipay.payV2(response.getData().getSign(), true);
                                                PayResult payResult = new PayResult(result);
                                                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                                                String resultStatus = payResult.getResultStatus();
                                                if (TextUtils.equals(resultStatus, "9000")) {
                                                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                                                    T.showShort(getContext(), "支付成功");
                                                    getSelfCoin();
                                                } else {
                                                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                                                    T.showShort(getContext(), "支付失败");
                                                }
                                                if (payDialog != null)
                                                    payDialog.dismiss();

                                            }
                                        };
                                        new Thread(payRunnable).start();
                                    } else {
                                        T.showShort(getContext(), response.getForUser());
                                    }
                                }
                            });
                        }

                    }
                });
                payDialog.show();


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mLivePlayer != null)
//            mLivePlayer.resume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        doLogout();

        if (weChartHelper != null)
            weChartHelper.unInit();

        if (waWaAudioPlayUtil != null) {
            waWaAudioPlayUtil.stopPlayAction();
            waWaAudioPlayUtil.stopPlayBackgroundMusic();
        }

        if (waWaJiControlClient != null)
            waWaJiControlClient.stop();

        if (toyDetail != null) {
            quitRoom();
        }

        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }

        if (timer != null) {
            timer.stop();
        }
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
        if (playing && view != imgChangeChannel) {

            waitingDialog.show();
//            T.showShort(getContext(), "正在等待结果");
            return;
        }
        switch (view.getId()) {
            case R.id.img_changeChannel:
                //更换视频源   主次摄像头切换
//                changeIpCamUrl(camUrl.equals(toyDetail.getFlankFlowLink()) ? (camUrl = toyDetail.getMainFlowLink()) : (camUrl = toyDetail.getFlankFlowLink()));
                changeIpCamUrl(null);
                break;
            case R.id.img_help:
                break;
            case R.id.v_changeRoom:
                onChangeRoomClick();
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


    // 点击 开始玩
    private void playWaWaJi() {


        if (toyDetail != null) {
            loadingDialog.show();
            roomModel.play(toyDetail.getMachineId(), new BeanCallback<BaseJson>() {
                @Override
                public void response(boolean success, BaseJson response, int id) {
                    if (response.isRet()) {
                        waWaJiControlClient.stop();
                        waWaJiControlClient.start(toyDetail.getRemoteId(), "pppzww168");
                    } else {
                        setWaWaControl(false);
                        if (loadingDialog.isShowing())
                            loadingDialog.dismiss();

                        T.showShort(getContext(), response.getForUser());
                    }
                    getSelfCoin();

                }
            });
        }

    }

    private void setWaWaControl(final boolean play) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (play) {
                    vCircleProgress.start();
                    vMainScrollView.scrollTo(0, 0);
                } else {
                    textTime.setText("30''");
                    vCircleProgress.stop();
                }
                textTime.setVisibility(play ? View.VISIBLE : View.GONE);

                vBottomControl.setVisibility(play ? View.VISIBLE : View.GONE);

//                vMainScrollView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View arg0, MotionEvent arg1) {
//                        return play;
//                    }
//                });
                vStartPlayLayout.setVisibility(!play ? View.VISIBLE : View.GONE);


            }
        });


    }

    //娃娃机控制
    @OnTouch({R.id.img_top, R.id.img_left, R.id.img_right, R.id.img_down, R.id.img_done})
    public boolean onBottomControlTouch(View v, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {

            if (v == imgTop || v == imgDown || v == imgLeft || v == imgRight) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (UserInfo.getRoomEffectBgEnable())
                            waWaAudioPlayUtil.play(WaWaAudioPlayUtil.TYPE_DIRECTION);

                    }
                }.start();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (UserInfo.getRoomEffectBgEnable())
                            waWaAudioPlayUtil.play(WaWaAudioPlayUtil.TYPE_CATCH);

                    }
                }.start();
            }

            if (mSwitchCameraTimes % 2 == 1) {//
                if (v == imgTop) {
                    waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_LEFT);

                } else if (v == imgDown) {

                    waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_RIGHT);

                } else if (v == imgLeft) {

                    waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_DOWN);

                } else if (v == imgRight) {
                    waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_TOP);

                } else if (v == imgDone) {
                    waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_CATCH);

                    waitingDialog.show();

                    countDownUtil.stop();
                    vCircleProgress.stop();
                }
            } else {
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
                    countDownUtil.stop();
                    vCircleProgress.stop();
                }
            }

            return false;
        } else if (action == MotionEvent.ACTION_UP) {
            if (v == imgDone) {
                return false;
            }
            waWaJiControlClient.action_up();
            L.e("touch up");
            return false;

        }
        return false;
    }

    /**
     * 抓取结果，显示对话框
     */
    private void showResultDialog(final boolean caught) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CaughtResultDialog successDialog = new CaughtResultDialog(getContext());
                successDialog.setOnDialogBtnClick(new CaughtResultDialog.OnDialogBtnClick() {
                    @Override
                    public void onClick(Dialog dialog, int btnIndex, View btn) {
                        if (btnIndex == 0) {
                            onViewClicked(vPlayLayout);
                            dialogPlayContinue = true;
                            dialog.dismiss();
                        } else if (btnIndex == 1) {
                            dialogPlayContinue = false;
                            dialog.dismiss();
                            openShareDialog();

                        }
                    }
                });
                successDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (!dialogPlayContinue) {
                            setMachineFree();
//                            dialogPlayContinue = true;
                        }
                        dialogPlayContinue = false;
                    }
                });
                if (caught) {
                    successDialog.setTitle("抓取成功");
                    successDialog.setMessage("恭喜抓取成功！");
                    successDialog.setContinueText("好运继续");
                } else {
                    successDialog.setTitle("抓取失败");
                    successDialog.setMessage("抓取失败！");
                    successDialog.setContinueText("再接再厉");

                }
//                successDialog.setCancelable(false);
                successDialog.show();

            }
        });

    }

    /**
     * 设置抓取结果
     */
    private void setCaughtResult(boolean caught) {
//        caught = true;
        roomModel.afterGrabToy(toyDetail.getMachineId(), caught, new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (playing) {
            new AlertDialog.Builder(getContext()).setMessage("游戏尚未结束，是否要退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    playing = false;
//                    setMachineFree();
                    quitRoom();
                    onBackPressed();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        } else {
            super.onBackPressed();
            quitRoom();
        }
    }

    public void openShareDialog(){
        if (TextUtils.isEmpty(UserInfo.getShareLink())){
            return;
        }
        UMWeb umWeb = new UMWeb(UserInfo.getShareLink());
        umWeb.setTitle("PPP抓娃娃");
        umWeb.setDescription("不一样的线上抓娃娃机");
        new ShareAction(getActivity()).withMedia(umWeb)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setDisplayList(SHARE_MEDIA.WEIXIN)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        L.e("[onStart]" + share_media);
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        L.e("[onResult]" + share_media);
                        afterShareToGetScore();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        L.e("[onError]" + share_media);

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        L.e("[onCancel]" + share_media);

                    }
                }).open();
    }

    /**
     * 退出房间
     */
    private void quitRoom() {
        roomModel.quitRoom(toyDetail.getMachineId(), new BeanCallback<String>() {
            @Override
            public void response(boolean success, String response, int id) {
                L.e("quit");
            }
        });
    }

    /**
     * 不玩的时候设置机器空闲
     */
    private void setMachineFree() {
        roomModel.setMachineFree(toyDetail.getMachineId(), new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                L.e("free");
            }
        });
    }

    private void onChangeRoomClick() {
        List<Machine> roomList = homeToyResult.getMachineList();
        int cr = currentRoom;
        if (++currentRoom >= roomList.size() - 1) {
            currentRoom = 0;
        }
        if (cr == currentRoom) {
            T.showShort(getContext(), "没有房间可以切换");
            return;
        }
        if (playing) {
            new AlertDialog.Builder(getContext()).setMessage("游戏尚未结束，是否要换房").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    playing = false;
                    connectRoom();
                    doLogout();


                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        } else {
            connectRoom();
            doLogout();
        }

//        Machine machine = roomList.get(currentRoom);
    }

    private void getSelfCoin() {
        personalInfoModel.getUserInfo(new BeanCallback<BaseJson<PersonalInfo>>() {
            @Override
            public void response(boolean success, BaseJson<PersonalInfo> response, int id) {
                if (response.isRet()) {
                    textBalance.setText(response.getData().getBalance());
                }
            }
        });
    }

    /**
     * 分享后获取积分
     */
    private void afterShareToGetScore(){
        personalInfoModel.afterShareToGetScore(new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {

            }
        });
    }

}
