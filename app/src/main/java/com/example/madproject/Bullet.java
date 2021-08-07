package com.example.madproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.madproject.flyhigh_view.screenRatioX;
import static com.example.madproject.flyhigh_view.screenRatioY;

public class Bullet {
    int x, y,  width, height;
    Bitmap bullet;

    Bullet (Resources res) {

        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = bullet.getWidth(); //this bullet is too big for screen so reduce its size.
        height = bullet.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screenRatioX); // make it compatible for other screens
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false); //resize the bullet

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height); //rectangle class
        //rectangle created around the bullet
    }
}
