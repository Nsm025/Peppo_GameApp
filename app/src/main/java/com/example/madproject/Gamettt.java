package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Gamettt extends AppCompatActivity {
  private TicTacToeBoard tictactoeBoard;
    private SoundPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamettt);
        sound = new SoundPlayer(this);

        Button playAgainbtn=findViewById(R.id.play_again);
        Button homebtn=findViewById(R.id.homebtn);
        TextView playerTurn=findViewById(R.id.player_display);
        playAgainbtn.setVisibility(View.GONE);
        homebtn.setVisibility(View.GONE);


        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");
        if(playerNames !=null){
            playerTurn.setText((playerNames[0] + " 's Turn"));// initial value of player name in the array.

        }
        tictactoeBoard= findViewById(R.id.ticTacToeBoard);

        tictactoeBoard.setUpGame(playAgainbtn, homebtn,playerTurn,playerNames);

    }

    public void playagain(View view) {
    tictactoeBoard.resetGame();
    tictactoeBoard.invalidate();
        sound.btnClick();
    }

    public void home(View view) {
        sound.btnClick();
        Intent intent = new Intent(this, tictactoe.class);
        startActivity(intent);
    }
}