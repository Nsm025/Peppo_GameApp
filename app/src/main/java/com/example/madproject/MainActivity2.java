package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class MainActivity2 extends AppCompatActivity {
    SliderView sliderView;
    ScrollView scrollView;
    TextView textView;
    TextView about;
    FloatingActionButton downbutton, upbutton,sidebutton;
    int[] images = {R.drawable.shooter,
    R.drawable.corona,
    R.drawable.ttt};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sliderView= findViewById(R.id.imageslide);
        scrollView= findViewById(R.id.scroll);
        upbutton= findViewById(R.id.upbutton);
        downbutton= findViewById(R.id.downbutton);
        sidebutton= findViewById(R.id.sidebutton);
        textView= findViewById(R.id.textView);
        about= findViewById(R.id.about);

       SliderAdapter sliderAdapter = new SliderAdapter(images);
       sliderView.setSliderAdapter(sliderAdapter);
       sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
       sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
       sliderView.startAutoCycle();



    }

    public void scrolldown(View view) {
        scrollView.scrollTo(0, (int) about.getY());
    }

    public void scrollup(View view) {
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        downbutton.show();
    }

    public void login(View view) {
        Intent intent = new Intent(this, loginPage.class);
        startActivity(intent);
    }


}