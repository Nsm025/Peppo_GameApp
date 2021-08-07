package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;

public class MainActivity4 extends AppCompatActivity {
    ImageButton play, more, shop;
    private SoundPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sound = new SoundPlayer(this);
        play = (ImageButton) findViewById(R.id.play);
        more = (ImageButton) findViewById(R.id.more);
        shop = (ImageButton) findViewById(R.id.shop);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.btnClick();
                startActivity(new Intent(getApplicationContext(), MainActivity5.class));
                finish();
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this, ShopActivity.class));
                finish();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(MainActivity4.this, more);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent, chooser;
                        int id = item.getItemId();
                        if(id == R.id.feedback){
                            intent = new Intent(Intent.ACTION_SEND);
                            intent.setData(Uri.parse("mail to: "));
                            String[] to = {"nehanasm3@gmail.com"};
                            intent.putExtra(Intent.EXTRA_EMAIL, to);
                            intent.setType("message/rfc822");
                            chooser = Intent.createChooser(intent, "Send Feedback");
                            startActivity(chooser);
                        }

                        if (id == R.id.share){
                            intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Corona");
                            String sAux= "\n Let me recomand you this game \n \n";
                            sAux = sAux+ "https://play.google.com/store/apps/details?id=com.angrymonkey.angrymonkey \n\n";
                            intent.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(intent, "Share"));
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void homescreen(View view) {

            Intent intent = new Intent(this, MainActivity6.class);
            startActivity(intent);

    }
}