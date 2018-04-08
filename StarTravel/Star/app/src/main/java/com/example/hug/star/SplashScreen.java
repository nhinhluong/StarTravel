package com.example.hug.star;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    private static final long DELAY = 3000;
    private boolean displayed = false;
    private Handler handler;
    ImageView img;
    TextView tw1, tw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        img = (ImageView)findViewById(R.id.imageViewSplash);
        tw1 = (TextView)findViewById(R.id.twSplash1);
        tw2 = (TextView)findViewById(R.id.twSplash2);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
            }
        }, 3000);
    }


}

