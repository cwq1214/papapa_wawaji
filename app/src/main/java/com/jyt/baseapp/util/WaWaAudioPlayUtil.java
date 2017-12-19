package com.jyt.baseapp.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.jyt.baseapp.R;

import java.io.IOException;

/**
 * Created by chenweiqi on 2017/12/18.
 */

public class WaWaAudioPlayUtil {
    Context mContext;

    AudioManager mAudioManager;
    MediaPlayer actionPlayer;
    MediaPlayer backgroundMusicPlayer;
    MediaPlayer direction;
    MediaPlayer catchit;
    MediaPlayer start;
    String[] bgmName = {"moinoi.mp3","nyokki.mp3"};

    public static final int TYPE_BACKGROUND = 0;

    public static final int TYPE_START = 1;
    public static final int TYPE_CATCH = 2;
    public static final int TYPE_DIRECTION = 3;
    int type;

    public void init(Context context){
        this.mContext = context;
        mAudioManager = (AudioManager)context. getSystemService(Context.AUDIO_SERVICE);



        actionPlayer = new MediaPlayer();
        try {
            direction = new MediaPlayer();
            AssetFileDescriptor afd = mContext.getAssets().openFd("direction.wav");
            direction.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
            direction.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            catchit = new MediaPlayer();
            AssetFileDescriptor afd = mContext.getAssets().openFd("catchit.wav");
            catchit.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
            catchit.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            start = new MediaPlayer();
            AssetFileDescriptor afd = mContext.getAssets().openFd("start.wav");
            start.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
            start.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }


        backgroundMusicPlayer = new MediaPlayer();
        backgroundMusicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        backgroundMusicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
//                playBackgroundMusic();
            }
        });
    }

    public void setBackgroundMusicIndex(int bgmIndex) {
//        Uri mUri = Uri.parse("android.resource://" + mContext.getPackageName() + "/"+ R.raw.moinoi);
        try {
//            backgroundMusicPlayer.setDataSource(mContext, mUri);
            AssetFileDescriptor afd = mContext.getAssets().openFd(bgmName[bgmIndex-1]);

            backgroundMusicPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());

            backgroundMusicPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            backgroundMusicPlayer.prepareAsync();

//            backgroundMusicPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void playBackgroundMusic(){
//        backgroundMusicPlayer.start();
    }

    public void play(int type){
            String fileName = null;
            switch (type){
                case TYPE_START:
                    System.out.println("start");
                    start.start();
                    fileName = "start.wav";
                    break;
                case TYPE_DIRECTION:
                    System.out.println("direction");
                    direction.start();
                    fileName = "direction.wav";
                    break;
                case TYPE_CATCH:
                    catchit.start();
                    fileName = "catchit.wav";
                    break;
            }



    }


    public void stopPlayBackgroundMusic(){
        if (backgroundMusicPlayer!=null)
            backgroundMusicPlayer.stop();
    }
    public void stopPlayAction(){
        actionPlayer .stop();
    }

}
