package com.jyt.baseapp.waWaJiControl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private Thread receive;
    private Thread send;
    private int avIndex = -1;
    private int sid;

    public static final int MOVE_TOP = AVIOCTRLDEFs.AVIOCTRL_PTZ_UP;
    public static final int MOVE_DOWN = AVIOCTRLDEFs.AVIOCTRL_PTZ_DOWN;
    public static final int MOVE_LEFT = AVIOCTRLDEFs.AVIOCTRL_PTZ_LEFT;
    public static final int MOVE_RIGHT = AVIOCTRLDEFs.AVIOCTRL_PTZ_RIGHT;
    public static final int MOVE_CATCH = 9;
    public static final int MOVE_STOP = AVIOCTRLDEFs.AVIOCTRL_PTZ_STOP;


    boolean isReceive;

    OnWaWaPlayedListener onWaWaPlayedListener;

    public WaWaJiControlClient(Context context){

        //region 发送
        send = new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();

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
                Looper.myLooper().loop();
            }


        };
        //endregion
        //region 接收
        receive = new Thread(new Runnable() {
            @Override
            public void run() {
//                if (true)
//                return;
                int[] ioCtrlType = new int[1];
                byte[] ioCtrlBuf = new byte[1024];


                int timeourReceTimes = 60;//unit s
                int i = 0; //timeout immediately
//                             while (i++ < timeourReceTimes){
                while (isReceive) {
                    int nRet = AVAPIs.avRecvIOCtrl(avIndex, ioCtrlType, ioCtrlBuf, ioCtrlBuf.length, 0);
                    L.e(String.format("avRecvIOCtrl ret[%d]\n", nRet));
                    if (nRet >= 0) {


                        if (ioCtrlType[0] == AVIOCTRLDEFs.IOTYPE_USER_IPCAM_EVENT_COMMAND) {

                            L.e("IOTCamera", "avRecvIOCtrl(" +
                                    0 + ", 0x" + Integer.toHexString(ioCtrlType[0]) + ", " + getHex(ioCtrlBuf, nRet) + ")");
                            //recv get wawaji return Data
                            int evtType2 = Packet.byteArrayToInt_Little(ioCtrlBuf, 16);
                            L.e(evtType2+"");


                            if (onWaWaPlayedListener!=null){
                                //开始
                                if (evtType2==20){
                                    onWaWaPlayedListener.start();
                                }else {
                                    onWaWaPlayedListener.onPlayed(evtType2==2);
                                    break;
                                }
                            }

                        }
                        try {
                            L.e(String.format("avRecvIOCtrl ret[%d]sleep start\n", nRet));

                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            L.e(String.format("avRecvIOCtrl ret[%d]sleep out\n", nRet));
                            e.printStackTrace();
                        }
                    } else {
                        L.e(String.format(" ret < 0 %d\n", nRet));

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        //endregion
        send.start();

        AVAPIs avapIs = new AVAPIs();
//        receive = new Thread();

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

        isReceive = true;


    }


    public void setOnWaWaPlayedListener(OnWaWaPlayedListener onWaWaPlayedListener) {
        this.onWaWaPlayedListener = onWaWaPlayedListener;
    }

    /**
     *
     */
    public void stop(){
        try {
            if (avIndex == -1){
                return;
            }
            AVAPIs.avClientStop(avIndex);
            System.out.printf("avClientStop OK\n");
            IOTCAPIs.IOTC_Session_Close(sid);
            System.out.printf("IOTC_Session_Close OK\n");
            AVAPIs.avDeInitialize();
            IOTCAPIs.IOTC_DeInitialize();
            System.out.printf("StreamClient exit...\n");
            avIndex = -1;
            isReceive = false;

                if (receive!=null) {
                    receive.interrupt();
                    receive.stop();
                    receive = null;
                }

                if (send!=null){
                    send.interrupt();
                    send.stop();
                    send = null;
                }
        }catch (Exception e){
            e.printStackTrace();
        }

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
            isReceive = false;
            return;
        }

        AVAPIs.avInitialize(10);

        sid = IOTCAPIs.IOTC_Get_SessionID();
        if (sid < 0) {
            L.e(String.format("IOTC_Get_SessionID error code [%d]\n", sid));
            isReceive= false;
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
            isReceive = false;
            L.e(String.format("avClientStart failed[%d]\n", avIndex));
            return;
        }

        startIpcamStream(20);

        new Thread(){
            @Override
            public void run() {
                super.run();
                isReceive = true;
                receive.start();
            }
        }.start();


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
//        AVAPIs av = new AVAPIs();



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

    private  final String HEXES = "0123456789ABCDEF";

     String getHex(byte[] raw, int size) {

        if (raw == null) {
            return null;
        }

        final StringBuilder hex = new StringBuilder(2 * raw.length);

        int len = 0;

        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F))).append(" ");

            if (++len >= size)
                break;
        }

        return hex.toString();
    }

    /**
     * 结束回调
     */
    public interface OnWaWaPlayedListener{
        void onPlayed(boolean caught);

        void start();
    }

}
