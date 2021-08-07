package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class tictactoe extends AppCompatActivity {
    private SoundPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);
        sound = new SoundPlayer(this);
    }

    public void submitname(View view) {
        sound.btnClick();
        Intent intent = new Intent(this, Playersetupttt.class);
        startActivity(intent);
    }

    public void gohome(View view) {
        Intent intent = new Intent(this, MainActivity6.class);
        startActivity(intent);
    }
}