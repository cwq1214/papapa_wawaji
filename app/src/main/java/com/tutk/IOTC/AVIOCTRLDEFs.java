package com.tutk.IOTC;
 

public class AVIOCTRLDEFs {

	/* AVAPIs IOCTRL Message Type */
	public static final int IOTYPE_USER_IPCAM_START = 0x01FF;
	public static final int IOTYPE_USER_IPCAM_STOP = 0x02FF;

 
	public static final int IOTYPE_USER_IPCAM_PTZ_COMMAND = 0x1001;
	public static final int AVIOCTRL_PTZ_STOP = 0;
	public static final int AVIOCTRL_PTZ_UP = 1;
	public static final int AVIOCTRL_PTZ_DOWN = 2;
	public static final int AVIOCTRL_PTZ_LEFT = 3;
	public static final int AVIOCTRL_PTZ_RIGHT = 6;
	public static final int PTZ_SPEED = 8;
	public static final int PTZ_POINT = 100;
}
