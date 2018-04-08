package com.example.hug.star;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroduceActivity extends AppCompatActivity {
    ImageView img1;
    TextView tx1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img1 = (ImageView)findViewById(R.id.imageViewIntro);
        tx1 = (TextView) findViewById(R.id.tvIntro);

        img1.setImageResource(R.drawable.star);

    }
}
