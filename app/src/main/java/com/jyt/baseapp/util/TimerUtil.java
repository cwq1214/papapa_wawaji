package com.jyt.baseapp.util;

import android.content.Context;
import android.os.Handler;
import android.support.v4.util.TimeUtils;

/**
 * Created by chenweiqi on 2017/12/1.
 */

public class TimerUtil {

    Handler handler;

    int time_ms;



    OnTimeCallback onTimeCallback;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (onTimeCallback!=null){
                onTimeCallback.onTime();
            }
            handler.postDelayed(runnable,time_ms);
        }
    };

    public TimerUtil(Context context,int time_ms){
        handler = new Handler();
        this.time_ms = time_ms;
    }

    public void start(){
        handler.post(runnable);
    }

    public void stop(){

            handler.removeCallbacks(runnable);

    }
    public void setOnTimeCallback(OnTimeCallback onTimeCallback) {
        this.onTimeCallback = onTimeCallback;
    }


    public interface OnTimeCallback{
        void onTime();
    }
}
