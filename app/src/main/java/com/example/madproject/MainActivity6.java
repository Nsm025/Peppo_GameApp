package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity6 extends AppCompatActivity {
    private ImageButton shooter;
    private ImageButton tic;
    private ImageButton virus;
    private RelativeLayout unlock;
    private SoundPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        sound = new SoundPlayer(this);

        shooter = findViewById(R.id.shooter);
        tic = findViewById(R.id.tic);
        virus=findViewById(R.id.corona);

        unlock=findViewById(R.id.unlock);

        SharedPreferences preferencesGames = getSharedPreferences("SCORE", Context.MODE_PRIVATE);
        int score = preferencesGames.getInt("SCORE", 0);

        if(score>=5) {
            unlock.setVisibility(View.GONE);

        }

        shooter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if(score >=5){

                   unlock.setVisibility(View.GONE);

                   tic.setImageResource(R.drawable.pickme);
                   shooter.setImageResource(R.drawable.shooter);
                   virus.setImageResource(R.drawable.pickme);
                   startActivity(new Intent(MainActivity6.this, fly_high.class));

               } else {
                   Toast.makeText(MainActivity6.this, "not enough score", Toast.LENGTH_SHORT).show();
               }
           }
       });

    }
    public void play1(View view) {
        tic.setImageResource(R.drawable.ttt);
        shooter.setImageResource(R.drawable.pickme);
        virus.setImageResource(R.drawable.pickme);
        Intent intent = new Intent(this, tictactoe.class);
        startActivity(intent);
    }

    public void play2(View view) {
        tic.setImageResource(R.drawable.pickme);
        shooter.setImageResource(R.drawable.pickme);
        virus.setImageResource(R.drawable.corona);
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

   // public void play3(View view) {
//        SharedPreferences preferencesGames = getSharedPreferences("GAMES", Context.MODE_PRIVATE);
//        int games = preferencesGames.getInt("GAMES", 0);
//        if (gamesP == true){
//
//            tic.setImageResource(R.drawable.pickme);
//            shooter.setImageResource(R.drawable.shooter);
//            virus.setImageResource(R.drawable.pickme);
//            startActivity(new Intent(MainActivity6.this, fly_high.class));
//
//        }
//        else if(games>=5){
//            gamesP=true;
//            lock.setVisibility(View.GONE);
//            tic.setImageResource(R.drawable.pickme);
//            shooter.setImageResource(R.drawable.shooter);
//            virus.setImageResource(R.drawable.pickme);
//            Intent intent = new Intent(this, fly_high.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(MainActivity6.this, "games played is not more than 5", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void exit(View view) {
        sound.btnClick();
        this.finishAffinity();
    }
}