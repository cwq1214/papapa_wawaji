package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.view.widget.CircleProgressView;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/8.
 */
@ActivityAnnotation(showActionBar = false)
public class RoomActivity extends BaseActivity {
    @BindView(R.id.img_msg)
    ImageView imgMsg;
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

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(getContext())- DensityUtil.dpToPx(getContext(),50));
                vControlLayout.setLayoutParams(params);
                vControlLayout.removeOnLayoutChangeListener(this);
            }
        });

    }

    @OnClick(R.id.img_back2)
    public void onBackClick() {
        onBackPressed();
    }
}
