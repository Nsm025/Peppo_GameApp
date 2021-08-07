package com.example.madproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Gamelogicttt  {
    private int[][] gameBoard;
    private String[] playerNames = {"Player 1", "Player 2"};
    //1st ele --> row, 2nd ele --> col, 3rd ele --> line type
    private int[] winType = {-1, -1, -1};
    private Button playAgainbtn;
    private Button homebtn;
    private TextView playerTurn;
    private int player = 1; // player1 will always go first


    Gamelogicttt(){

        gameBoard = new int[3][3];

        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                gameBoard[r][c]= 0; //to check wch place is freely available.
            }
        }
    }


    public boolean updateGameBoard(int row, int col) {
        if (gameBoard[row - 1][col - 1] == 0) { //row-1 col-1 becoz index value starts from 0
            gameBoard[row - 1][col - 1] = player; // set the spot with users marker.
            if(player == 1){
                playerTurn.setText((playerNames[1] + " 's Turn")); //player default names.
            } else{
                playerTurn.setText((playerNames[0] + " 's Turn"));
            }
            return true;
        } else {
            return false; //spot isnt available.
        }
    }
    public boolean winnerCheck(){

        boolean isWinner = false;
        for(int r=0; r<3; r++){ //rows check
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard [r][0] != 0){
               winType = new int[] {r, 0, 1}; //hortizontal line. wintype==1
                isWinner = true;
            }
        }
        for(int c=0; c<3; c++){ //col check
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] && gameBoard [0][c] != 0){
               winType = new int[] {0,c,2}; //vertical line, wintype==2
                isWinner = true;
            }
        }
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard [0][0] != 0){
            winType = new int[] {0,2,3}; //wintype == 3 0th row ans 2nd col becoz it starts at 0th row & ends at last col.
            isWinner = true; //diagonal check negative slope
        }
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard [2][0] != 0){
            winType = new int[] {2,2,4}; //wintype == 4 2nd row and col becoz line starts and ends at last row and last col.
            isWinner = true; // diagonal check positive slope
        }
        int boardFilled = 0;
        for (int r=0; r<3; r++) {
            for (int c = 0; c < 3; c++) {
          if(gameBoard[r][c] != 0){
                boardFilled += 1;
              }
            }
        }

      if(isWinner){


          playAgainbtn.setVisibility(View.VISIBLE);
          homebtn.setVisibility(View.VISIBLE);
          playerTurn.setText((playerNames[player-1]+ " " + "Won!!!")); // index value starts with 0 so minus 1.

          return true;
      }
      else if(boardFilled == 9){

          playAgainbtn.setVisibility(View.VISIBLE);
          homebtn.setVisibility(View.VISIBLE);
          playerTurn.setText("Tie Game!!!");

          winType = new int[] {-1,-1,-1};
          return true;
      }else {
          return false;
      }
    }
    public void resetGame() { //reset board to empty board
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                gameBoard[r][c]= 0;
            }
        }
        player = 1; // to make play again btn func and the last game played winner name vanish.
        playAgainbtn.setVisibility(View.GONE);
        homebtn.setVisibility(View.GONE);
        playerTurn.setText((playerNames[0] + " 's turn")); //start with an another game
    }
    public void setPlayAgainbtn(Button playAgainbtn){
        this.playAgainbtn = playAgainbtn;
    }
    public void setHomebtn(Button homebtn){
        this.homebtn = homebtn;
    }
    public void setPlayerTurn(TextView playerTurn){
        this.playerTurn= playerTurn;
    }
    public void setPlayerNames(String[] playerNames){
        this.playerNames = playerNames;
    }


    public int[][] getGameBoard() {
        return gameBoard;
    }
    public void setPlayer(int player){
        this.player = player;
    }
    public int getPlayer(){
         return player;
    }
    public int[] getWinType() {
        return winType;
    }
}
