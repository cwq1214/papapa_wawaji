package com.jyt.baseapp.waWaJiControl;


import com.tutk.IOTC.AVAPIs;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.IOTCAPIs;
import com.tutk.IOTC.Packet;

public class Client {
	public static String strError = "error";
	static int avIndex;
	public static void start(String uid, String pwd ) {

		System.out.println("StreamClient start...");
		int port = (int) (10000 + (System.currentTimeMillis() % 10000));

		int ret = IOTCAPIs.IOTC_Initialize2(port);
		System.out.printf("IOTC_Initialize() ret = %d\n", ret);
		if (ret != IOTCAPIs.IOTC_ER_NoERROR) {
			System.out.printf("IOTCAPIs_Device exit...!!\n");
			return;
		}

		AVAPIs.avInitialize(10);

		int sid = IOTCAPIs.IOTC_Get_SessionID();
		if (sid < 0) {
			System.out.printf("IOTC_Get_SessionID error code [%d]\n", sid);
			return;
		}

		ret = IOTCAPIs.IOTC_Connect_ByUID_Parallel(uid, sid);
		System.out.printf(
				"Step 2: call IOTC_Connect_ByUID_Parallel(%s).......(%d)\n", uid, ret);
		int[] mResend = new int[1];
		int[] srvType = new int[1];
		// avIndex = AVAPIs.avClientStart2(mSID, mAVChannel.getViewAcc(),
		// mAVChannel.getViewPwd(), 30, nServType, mAVChannel.getChannel(),
		// mResend);
		avIndex = AVAPIs.avClientStart2(sid, "admin", pwd, 20, srvType, 0,
				mResend);
		System.out.printf("Step 2: call avClientStart(%d).......\n", avIndex);

		if (avIndex < 0) {
			System.out.printf("avClientStart failed[%d]\n", avIndex);
			return;
		}

//		startIpcamStream(avIndex);

//		AVAPIs.avClientStop(avIndex);
//		System.out.printf("avClientStop OK\n");
//		IOTCAPIs.IOTC_Session_Close(sid);
//		System.out.printf("IOTC_Session_Close OK\n");
//		AVAPIs.avDeInitialize();
//		IOTCAPIs.IOTC_DeInitialize();
//		System.out.printf("StreamClient exit...\n");
	}

	public static void startIpcamStream(int acton) {
 
				AVAPIs av = new AVAPIs();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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

//				// iAction == MotionEvent.ACTION_DOWN
//				byte[] bdaata = SMsgAVIoctrlPtzCmd.parseContent(
//						(byte) AVIOCTRLDEFs.AVIOCTRL_PTZ_DOWN,
//						(byte) AVIOCTRLDEFs.PTZ_SPEED,
//						(byte) AVIOCTRLDEFs.PTZ_POINT, (byte) 0, (byte) 0,
//						(byte) 0);
//				int ret = AVAPIs.avSendIOCtrl(avIndex,
//						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_PTZ_COMMAND, bdaata,
//						bdaata.length);
//				// iAction == MotionEvent.ACTION_UP
//
//				bdaata = SMsgAVIoctrlPtzCmd.parseContent(
//						(byte) AVIOCTRLDEFs.AVIOCTRL_PTZ_UP,
//						(byte) AVIOCTRLDEFs.PTZ_SPEED,
//						(byte) AVIOCTRLDEFs.PTZ_POINT, (byte) 0, (byte) 0,
//						(byte) 0);
//				AVAPIs.avSendIOCtrl(avIndex,
//						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_PTZ_COMMAND, bdaata,
//						bdaata.length);
//
//				// iAction == MotionEvent.ACTION_LEFT
//
//				bdaata = SMsgAVIoctrlPtzCmd.parseContent(
//						(byte) AVIOCTRLDEFs.AVIOCTRL_PTZ_LEFT,
//						(byte) AVIOCTRLDEFs.PTZ_SPEED,
//						(byte) AVIOCTRLDEFs.PTZ_POINT, (byte) 0, (byte) 0,
//						(byte) 0);
//				AVAPIs.avSendIOCtrl(avIndex,
//						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_PTZ_COMMAND, bdaata,
//						bdaata.length);
//
//				// iAction == MotionEvent.ACTION_RIGHT
//
//				bdaata = SMsgAVIoctrlPtzCmd.parseContent(
//						(byte) AVIOCTRLDEFs.AVIOCTRL_PTZ_RIGHT,
//						(byte) AVIOCTRLDEFs.PTZ_SPEED,
//						(byte) AVIOCTRLDEFs.PTZ_POINT, (byte) 0, (byte) 0,
//						(byte) 0);
//				AVAPIs.avSendIOCtrl(avIndex,
//						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_PTZ_COMMAND, bdaata,
//						bdaata.length);
//
//				// iAction == MotionEvent.ACTION_STOP
//				try {
//					Thread.sleep(4000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				bdaata = SMsgAVIoctrlPtzCmd.parseContent(
//						(byte) AVIOCTRLDEFs.AVIOCTRL_PTZ_STOP,
//						(byte) AVIOCTRLDEFs.PTZ_SPEED,
//						(byte) AVIOCTRLDEFs.PTZ_POINT, (byte) 0, (byte) 0,
//						(byte) 0);
//				AVAPIs.avSendIOCtrl(avIndex,
//						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_PTZ_COMMAND, bdaata,
//						bdaata.length);

				
				System.out.printf("avSendIOCtrl ret[%d]max[%d]\n", ret,
						bdaata.length);
				int[] ioCtrlType = new int[1];
				byte[] ioCtrlBuf = new byte[1024];

			 
				AVAPIs.avSendIOCtrlExit(0);

 
	}

	private static final String HEXES = "0123456789ABCDEF";

	static String getHex(byte[] raw, int size) {

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
