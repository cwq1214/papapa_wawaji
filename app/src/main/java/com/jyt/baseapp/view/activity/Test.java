package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.CountDownUtil;
import com.jyt.baseapp.view.widget.CircleProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/12/15.
 */

public class Test extends BaseActivity {
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
    @BindView(R.id.v_doneLayout)
    RelativeLayout vDoneLayout;
    @BindView(R.id.v_bottomControl)
    RelativeLayout vBottomControl;


    //倒计时
    CountDownUtil countDownUtil;
    @BindView(R.id.time)
    TextView time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


        countDownUtil = new CountDownUtil(getContext(), 5, 1000);
        countDownUtil.setCountDownCallback(new CountDownUtil.CountDownCallback() {
            @Override
            public void countDownCallback(boolean finish, int currentCount) {
                if (finish) {
//                    waWaJiControlClient.action_down(WaWaJiControlClient.MOVE_CATCH);
//                    waWaJiControlClient.action_up();
//                    countDownUtil.stop();
//                    vCircleProgress.stop();
//                    setWaWaControl(playing = false);
                    new Thread() {
                        @Override
                        public void run() {
                            countDownUtil.stop();
                            vCircleProgress.stop();
//                            waWaJiControlClient.action_up();
                        }
                    }.start();


                }
                if (!finish) {
                    time.setText(currentCount + "''");
                }
            }
        });
    }

    @OnClick(R.id.img_top)
    public void onViewClicked() {
        vCircleProgress.start();
        countDownUtil.start();
    }

    @OnClick(R.id.img_down)
    public void onViewClicked2() {
        vCircleProgress.stop();
        countDownUtil.stop();
    }
}
