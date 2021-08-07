package com.example.madproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TicTacToeBoard extends View {
    private final  int boardColor;
    private final  int XColor;
    private final  int OColor;
    private final  int winningLineColor;
    private boolean winningLine = false;
    private final Paint paint = new Paint();
    private final Gamelogicttt game;
    private int cellSize = getWidth()/3;
    private SoundPool soundPool;
    private int sound;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);


        game = new Gamelogicttt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(context, R.raw.tictactoe, 1);


        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard,
                0,0);
        try{
           boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);
        }finally{
            a.recycle();
        }

    }
    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension/3; //becoz 3 box per row
        setMeasuredDimension(dimension, dimension);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);
        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }

    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY(); // x and y position of user's touch

        int action = event.getAction(); //make sure user touch on game board
        if(action == MotionEvent.ACTION_DOWN){ // update board with marker set by user

            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize); // cal row and col in relation to board
            if (!winningLine) { // once there is  winner no more markers can be added.
                if (game.updateGameBoard(row, col)) {
                    invalidate(); // if its true redraw board.
                    if(game.winnerCheck()){
                        winningLine = true;
                        soundPool.play(sound, 1, 1, 0, 0, 1);
                        invalidate();

                    }
                    if (game.getPlayer() % 2 == 0) { //switch player if player 2 has played thn its player 1's turn.
                        game.setPlayer(game.getPlayer() - 1); //so 2-1, player1 turn
                    } else {
                        game.setPlayer(game.getPlayer() + 1); // player2 turn
                    }
                }
            }
            invalidate(); // redraw game board

            return true;
        }
        return false;
    }
    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
          for(int c=1; c<3; c++){ // draw column
              canvas.drawLine(cellSize*c, 0, cellSize*c, canvas.getWidth(),paint);
          }
        for(int r=1; r<3; r++) { //draw row
            canvas.drawLine(0, cellSize*r, canvas.getWidth(),cellSize*r, paint);
        }
        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void drawMarkers(Canvas canvas){
            for (int r=0; r<3; r++){
                for (int c=0; c<3; c++){
                  if(game.getGameBoard()[r][c] !=0){
                      if (game.getGameBoard()[r][c] !=1){
                          drawX(canvas, r, c);
                      } else{
                          drawO(canvas, r, c);
                      }
                  }
                }
            }
        }
        private void drawX(Canvas canvas, int row, int col){
         paint.setColor(XColor);

         canvas.drawLine((float)((col+1)*cellSize - cellSize*0.2),
                 (float)(row*cellSize+ cellSize*0.2),
                 (float)(col*cellSize+ cellSize*0.2),
                 (float)((row+1)*cellSize - cellSize*0.2),
                 paint);
            canvas.drawLine((float)(col*cellSize + cellSize*0.2),
                    (float)(row*cellSize+ cellSize*0.2),
                    (float)((col+1)*cellSize- cellSize*0.2),
                    (float)((row+1)*cellSize - cellSize*0.2),
                    paint);
        }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(OColor);
        canvas.drawOval((float)(col*cellSize+ cellSize*0.2),
                (float)(row*cellSize+ cellSize*0.2),
                (float)((col*cellSize + cellSize)- cellSize*0.2),
                (float)((row*cellSize + cellSize)- cellSize*0.2),
                paint);
    }
    //winning lines
    private void drawHorizontalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col, row*cellSize + (float) cellSize/2, //cellsize/2 becoz line drawn at the middle of x's and o's.
                cellSize*3,
                row*cellSize +  (float)cellSize/2,
                paint); //row*cellsize will draw a line at that particular row and as per the col passed.
    }

    private void drawVerticalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col*cellSize +  (float)cellSize/2, row,
                col*cellSize +  (float)cellSize/2, cellSize*3, paint);
    }
    private void drawDiagonalLinePos(Canvas canvas){
        canvas.drawLine(0,cellSize*3,cellSize*3,0,paint);
    }
    private void drawDiagonalLineNeg(Canvas canvas){
        canvas.drawLine(0,0,cellSize*3,cellSize*3,paint);
    }
    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        switch(game.getWinType()[2]){
            case 1:
                drawHorizontalLine(canvas, row, col);

                break;
            case 2:
                drawVerticalLine(canvas, row, col);

                break;
            case 3:
                drawDiagonalLineNeg(canvas);

                break;
            case 4:
                drawDiagonalLinePos(canvas);

                break;

        }
    }


    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] names){
        game.setPlayAgainbtn(playAgain);
        game.setHomebtn(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names); //this func basically sets the btn text view visible whn req.
    }
    public void resetGame() {
        game.resetGame();
        winningLine = false;
    }
    }

