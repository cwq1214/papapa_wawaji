package com.jyt.baseapp.waWaJiControl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jyt.baseapp.util.L;
import com.tutk.IOTC.AVAPIs;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.IOTCAPIs;
import com.tutk.IOTC.Packet;

import static android.net.sip.SipErrorCode.TIME_OUT;

/**
 * Created by chenweiqi on 2017/11/14.
 */

public class WaWaJiControlClient {
    private Handler handler;
    private Handler receiveHandler;
    private int avIndex;
    private int sid;

    public static final int MOVE_TOP = AVIOCTRLDEFs.AVIOCTRL_PTZ_UP;
    public static final int MOVE_DOWN = AVIOCTRLDEFs.AVIOCTRL_PTZ_DOWN;
    public static final int MOVE_LEFT = AVIOCTRLDEFs.AVIOCTRL_PTZ_LEFT;
    public static final int MOVE_RIGHT = AVIOCTRLDEFs.AVIOCTRL_PTZ_RIGHT;
    public static final int MOVE_CATCH = 9;
    public static final int MOVE_STOP = AVIOCTRLDEFs.AVIOCTRL_PTZ_STOP;


    public WaWaJiControlClient(Context context){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0){
                    String uid = msg.getData().getString("uid");
                    String pwd = msg.getData().getString("pwd");
                    startConnection(uid,pwd);
                }else if (msg.what == 1){
                    int move_action = msg.getData().getInt("ACTION");
                    startIpcamStream(move_action);
                }
            }
        };

        receiveHandler = new Handler(){
        };

    }

    /**
     * 开始
     * @param uid
     * @param pwd
     */
    public void start(String uid, String pwd ){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        bundle.putString("pwd",pwd);
        message.setData(bundle);
        handler.sendMessage(message);
        handler.post(new Runnable() {
            @Override
            public void run() {
//                byte[] bdaata = SMsgAVIoctrlPtzCmd.parseContent(
//                        (byte) acton,
//                        (byte) AVIOCTRLDEFs.PTZ_SPEED,
//                        (byte) AVIOCTRLDEFs.PTZ_POINT, (byte) 0, (byte) 0,
//                        (byte) 0);
//                int nRet = AVAPIs.avRecvIOCtrl(avIndex,, eventBuf, eventBuf.length, TIME_OUT);
            }
        });
//        receiveHandler.post();
    }

    /**
     *
     */
    public void stop(){
        AVAPIs.avClientStop(avIndex);
        System.out.printf("avClientStop OK\n");
        IOTCAPIs.IOTC_Session_Close(sid);
        System.out.printf("IOTC_Session_Close OK\n");
        AVAPIs.avDeInitialize();
        IOTCAPIs.IOTC_DeInitialize();
        System.out.printf("StreamClient exit...\n");
    }

    /**
     * handler 启动服务
     * @param uid
     * @param pwd
     */
    private void startConnection(String uid, String pwd ){
        L.e(String.format("StreamClient start..."));
        int port = (int) (10000 + (System.currentTimeMillis() % 10000));

        int ret = IOTCAPIs.IOTC_Initialize2(port);
        L.e(String.format("IOTC_Initialize() ret = %d\n", ret));
        if (ret != IOTCAPIs.IOTC_ER_NoERROR) {
            L.e(String.format("IOTCAPIs_Device exit...!!\n"));
            return;
        }

        AVAPIs.avInitialize(10);

        sid = IOTCAPIs.IOTC_Get_SessionID();
        if (sid < 0) {
            L.e(String.format("IOTC_Get_SessionID error code [%d]\n", sid));
            return;
        }

        ret = IOTCAPIs.IOTC_Connect_ByUID_Parallel(uid, sid);

        L.e(String.format("Step 2: call IOTC_Connect_ByUID_Parallel(%s).......(%d)\n", uid, ret));
        int[] mResend = new int[1];
        int[] srvType = new int[1];
        // avIndex = AVAPIs.avClientStart2(mSID, mAVChannel.getViewAcc(),
        // mAVChannel.getViewPwd(), 30, nServType, mAVChannel.getChannel(),
        // mResend);
        avIndex = AVAPIs.avClientStart2(sid, "admin", pwd, 20, srvType, 0,
                mResend);
        L.e("Step 2: call avClientStart(%d).......\n", avIndex);

        if (avIndex < 0) {
            L.e(String.format("avClientStart failed[%d]\n", avIndex));
            return;
        }
    }


    /**
     * 按键按下时调用
     * @param move_action
     */
    public void action_down(int move_action){
        switch (move_action){
            case MOVE_TOP:
            case MOVE_DOWN:
            case MOVE_LEFT:
            case MOVE_RIGHT:
            case MOVE_CATCH:
                Message message = new Message();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putInt("ACTION",move_action);
                message.setData(bundle);
                handler.sendMessage(message);
            default:
                break;
        }
    }

    /**
     * 按键松开时调用
     */
    public void action_up(){
        Message message = new Message();
        message.what = 1;
        Bundle bundle = new Bundle();
        bundle.putInt("ACTION",MOVE_STOP);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void startIpcamStream(int acton) {
        AVAPIs av = new AVAPIs();



        String strResp = null;

        int nDelayTime_ms = 0;
        AVAPIs.avSendIOCtrl(0, AVAPIs.IOTYPE_INNER_SND_DATA_DELAY,
                Packet.intToByteArray_Little(nDelayTime_ms), 4);


        byte[] bdaata = SMsgAVIoctrlPtzCmd.parseContent(
                (byte) acton,
                (byte) AVIOCTRLDEFs.PTZ_SPEED,
                (byte) AVIOCTRLDEFs.PTZ_POINT, (byte) 0, (byte) 0,
                (byte) 0);
        int ret = AVAPIs.avSendIOCtrl(avIndex,
                AVIOCTRLDEFs.IOTYPE_USER_IPCAM_PTZ_COMMAND, bdaata,
                bdaata.length);
        L.e(String.format("avSendIOCtrl ret[%d]max[%d]\n", ret,
                bdaata.length));
        int[] ioCtrlType = new int[1];
        byte[] ioCtrlBuf = new byte[1024];
        AVAPIs.avSendIOCtrlExit(0);
    }

    public static class SMsgAVIoctrlPtzCmd {
        byte control; // ptz control command
        byte speed; // ptz control speed
        byte point;
        byte limit;
        byte aux;
        byte channel; // camera index
        byte[] reserved = new byte[2];

        public static byte[] parseContent(byte control, byte speed, byte point,
                                          byte limit, byte aux, byte channel) {
            byte[] result = new byte[8];

            result[0] = control;
            result[1] = speed;
            result[2] = point;
            result[3] = limit;
            result[4] = aux;
            result[5] = channel;

            return result;
        }
    }


}
