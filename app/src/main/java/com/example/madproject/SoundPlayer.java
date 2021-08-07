package com.example.madproject;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX =100;

    private static  SoundPool soundPool;
    private  static int hitSound;
    private static int collectSound;
    private static int loseSound;
    private static int clickSound;
    private static int tttSound;

    public SoundPlayer(Context context){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            audioAttributes= new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

            hitSound = soundPool.load(context, R.raw.welcome, 1);
            collectSound = soundPool.load(context, R.raw.collect, 1);
            loseSound = soundPool.load(context, R.raw.lose, 1);
            clickSound= soundPool.load(context, R.raw.click, 1);
            tttSound = soundPool.load(context, R.raw.tictactoe, 1);
    }
    public void playHitSound(){
        soundPool.play(hitSound, 1.0f,1.0f,1,0,1.0f);

    }
//   public void pauseHitSound(){
//
//        soundPool.stop(hitSound);
//   }
   public void collectSound(){

        soundPool.play(collectSound, 1.0f,1.0f,1,0,1.0f);
   }
    public void loseSound(){

        soundPool.play(loseSound, 1.0f,1.0f,1,0,1.0f);
    }
    public void btnClick(){

        soundPool.play(clickSound, 1.0f,1.0f,1,0,1.0f);
    }
    public void tttWin(){

        soundPool.play(tttSound, 1.0f,1.0f,1,0,1.0f);
    }

}
