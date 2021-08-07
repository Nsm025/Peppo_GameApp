package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class flyhigh_view extends SurfaceView implements Runnable{

   private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private flight flight;
    private Random random;
    private SoundPool soundPool;
    private int sound;
    private flyhigh_backgorund bg1, bg2;
    private Bird[] birds;
    private SharedPreferences prefs;
    private List<Bullet> bullets;
    private fly_high_action activity;

    public flyhigh_view(fly_high_action activity,int screenX, int screenY) {
        super(activity);
        this.activity= activity;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

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

        sound = soundPool.load(activity, R.raw.shoot, 1);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        bg1 = new flyhigh_backgorund(screenX, screenY, getResources());
        bg2 = new flyhigh_backgorund(screenX, screenY, getResources());

        flight = new flight(this, screenY, getResources());
        bullets = new ArrayList<>();
        bg2.x = screenX;
        paint= new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);
         birds = new Bird[4];
         for(int i=0; i<4; i++){
             Bird bird = new Bird(getResources());
             birds[i] = bird;
         }
         random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying) {

            update ();
            draw ();
            sleep ();

        }

    }
    private void update(){

        bg1.x -= 10 * screenRatioX ;
        bg2.x -= 10 * screenRatioX;
        if (bg1.x + bg1.background.getWidth() < 0) {
            bg1.x = screenX;
        }

        if (bg2.x + bg2.background.getWidth() < 0) {
            bg2.x = screenX;
        }
        if (flight.isGoingUp)
            flight.y -= 30 * screenRatioY;
        else
            flight.y += 30 * screenRatioY;
        if (flight.y < 0)
            flight.y = 0; // if flight is off the screen from top view set to 0

        if (flight.y >= screenY - flight.height)
            flight.y = screenY - flight.height; // if flight is off the screen from bottom bring it to correct position.

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {

            if (bullet.x > screenX) // if bullet is off the screen
                trash.add(bullet); // add bullet to trash list whn bullet is off the screen whn update method called each time.

            bullet.x += 50 * screenRatioX; //moving the bullet on x axis and make it compatible to other screens.

            for (Bird bird : birds) {

                if (Rect.intersects(bird.getCollisionShape(),
                        bullet.getCollisionShape())) {
                    score++;
                    bird.x = -500; //bird pos off the screen
                    bullet.x = screenX + 500; // bullet off the screen
                    bird.wasShot = true;

                }

            }
        }
        for (Bullet bullet : trash) // this loop for each bullet in the trash list.
            bullets.remove(bullet);// remove it from the trash
        for (Bird bird : birds) {

            bird.x -= bird.speed; // move bird towards the flight.
            if (bird.x + bird.width < 0) { // bird is off the screen from left side.

                if (!bird.wasShot) {
                        isGameOver = true;
                        return;
                    }
                int bound = (int) (30 * screenRatioX); // set compatible to all screens.
                bird.speed = random.nextInt(bound); //random speed for bird
                if (bird.speed < 10 * screenRatioX) // check bird speed is less than 10 pixels if so thn bird wont move.
                    bird.speed = (int) (10 * screenRatioX); // set it to 10 px.
                bird.x = screenX; // set bird towards end of screen right side.
                bird.y = random.nextInt(screenY - bird.height); // screeny- birdheight due to random func if it returns screeny thn bird will be off the screen.

                bird.wasShot = false;
            }
             if(Rect.intersects(bird.getCollisionShape(),flight.getCollisionShape())){
                 isGameOver = true;
                 return;
             }
        }

    }
    private void draw(){
        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(bg1.background, bg1.x, bg1.y, paint);
            canvas.drawBitmap(bg2.background, bg2.x, bg2.y, paint);

            for (Bird bird : birds)
                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
            canvas.drawText(score + "", screenX / 2f, 164, paint);
            if(isGameOver){
                isPlaying = false;
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();
                return;
            }
            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint); // draw the picture on the screen

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, fly_high.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
    }

    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume() {
        isPlaying=true;
        thread = new Thread(this);
        thread.start();
    }
    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenX / 2){
                    flight.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    flight.toShoot++;
                break;
        }
        return true;
    }

    public void newBullet() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2); // these above lines will set the bullet near the wings of the flight.
        bullets.add(bullet); //add bullet to bullet list.
    }
}
