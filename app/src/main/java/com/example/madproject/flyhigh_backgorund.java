package com.example.madproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class flyhigh_backgorund {
    int x=0,y=0;
    Bitmap background;
    flyhigh_backgorund (int screenX, int screenY, Resources res) {

        background= BitmapFactory.decodeResource(res, R.drawable.flyhighbg);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }
}
