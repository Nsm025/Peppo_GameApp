package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.os.Handler;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button flash;
private SoundPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sound = new SoundPlayer(this);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Handler musicStopHandler = new Handler();
        Runnable musicStopRunable = new Runnable() {
            @Override
            public void run() {
                sound.playHitSound();
            }
        };

        musicStopHandler.postDelayed(musicStopRunable, 100);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                startFlash();
            }
        },500);



    }
    public void startFlash(){
        flash=(Button)findViewById(R.id.btnflash);

        Animation mAnimation = new AlphaAnimation(1,0);
        mAnimation.setDuration(800);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        flash.startAnimation(mAnimation);


    }


    public void second_event(View view) {

        Intent intent = new Intent(this, MainActivity2.class);

        sound.btnClick();
        startActivity(intent);

    }
}