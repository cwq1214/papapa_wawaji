package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.view.dialog.RechargeCoinDialog;
import com.jyt.baseapp.view.widget.CircleProgressView;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.BindView;
import butterknife.OnClick;

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
    FrameLayout vWebviewLayout;
    @BindView(R.id.v_bottomControl)
    RelativeLayout vBottomControl;
    RechargeCoinDialog rechargeCoinDialog;
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
        vTxCloudVideoView.setRenderMode(TXCloudVideoView.ACCESSIBILITY_LIVE_REGION_ASSERTIVE);

//        //创建player对象
//        TXLivePlayer mLivePlayer = new TXLivePlayer(getContext());
//        //关键player对象与界面view
//        mLivePlayer.setPlayerView(vTxCloudVideoView);
//        mLivePlayer.startPlay("rtmp://live.hkstv.hk.lxdns.com/live/hks", TXLivePlayer.PLAY_TYPE_LIVE_RTMP);

        vTxCloudVideoView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                vTxCloudVideoView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (right - left) * 481 / 366));
                vTxCloudVideoView.removeOnLayoutChangeListener(this);
            }
        });
        vControlLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(getContext()) - DensityUtil.dpToPx(getContext(), 50));
                vControlLayout.setLayoutParams(params);
                vControlLayout.removeOnLayoutChangeListener(this);
            }
        });
        vWebviewLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(getContext()) - DensityUtil.dpToPx(getContext(), 50));
                vWebviewLayout.setLayoutParams(params);
                vWebviewLayout.removeOnLayoutChangeListener(this);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        vTxCloudVideoView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vTxCloudVideoView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vTxCloudVideoView.onDestroy();
    }

    @OnClick(R.id.img_back2)
    public void onBackClick() {
        onBackPressed();
    }


    @OnClick({R.id.img_help, R.id.v_changeRoom, R.id.v_recharge, R.id.img_top, R.id.img_left, R.id.img_right, R.id.img_down, R.id.img_done})
    public void onViewClicked(View view) {
        if (!UserInfo.isLogin()){
            IntentHelper.openLoginActivity(getContext());
            return;
        }
        switch (view.getId()) {
            case R.id.img_help:
                break;
            case R.id.v_changeRoom:
                break;
            case R.id.v_recharge:
                rechargeCoinDialog = new RechargeCoinDialog(getContext());
                rechargeCoinDialog.setOnPriceClick(new RechargeCoinDialog.OnPriceClick() {
                    @Override
                    public void onPriceClick(int price) {

                        if (rechargeCoinDialog!=null)
                            rechargeCoinDialog.dismiss();
                    }
                });
                rechargeCoinDialog.show();
                break;
            case R.id.img_top:
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
}
