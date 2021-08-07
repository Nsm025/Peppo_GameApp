package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Playersetupttt extends AppCompatActivity {
    private EditText player1;
    private EditText player2;
    private SoundPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playersetupttt);
        sound = new SoundPlayer(this);
        player1=findViewById(R.id.editTextTextPersonName);
        player2=findViewById(R.id.editTextTextPersonName2);
    }

    public void submitbuttonclick(View view) {
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();
        Intent intent = new Intent(this, Gamettt.class);
        intent.putExtra("PLAYER_NAMES", new String[] {player1Name, player2Name});
        sound.btnClick();
        startActivity(intent);

    }
}