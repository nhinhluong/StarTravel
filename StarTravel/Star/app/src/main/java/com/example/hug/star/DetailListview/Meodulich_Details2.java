package com.example.hug.star.DetailListview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hug.star.Adapter.MeoDuLichAdapter;
import com.example.hug.star.PostActivity;
import com.example.hug.star.R;

public class Meodulich_Details2 extends AppCompatActivity {
    ImageView hinhanh;
    TextView tvTitle, tvContent;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meodulich__details2);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        hinhanh = (ImageView)findViewById(R.id.imv_details1);
        scrollView = (ScrollView)findViewById(R.id.scrollView1);
        tvContent = (TextView)findViewById(R.id.textViewContent1);
        tvTitle = (TextView)findViewById(R.id.textViewTitle1);

        Intent intent = getIntent();
        String mainimage = intent.getStringExtra("mainimage");
        // tvNamePlace.setText(image);
        String content = intent.getStringExtra("content");
        tvContent.setText(content);
        String title = intent.getStringExtra("title");
        tvTitle.setText(title);
        String_To_ImageView(mainimage, hinhanh);

    }
    public void String_To_ImageView(String strBase64, ImageView imw){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imw.setImageBitmap(decodedByte);
    }

}
