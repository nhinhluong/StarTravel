package com.example.hug.star.DetailListview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hug.star.Adapter.HinhAnhAdapter;
import com.example.hug.star.R;
import com.example.hug.star.model.HinhAnh;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Details1 extends AppCompatActivity {
    ImageView img;
    TextView tvGioiThieu, tvNamePlace;
    ScrollView scrollView;
    ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details1);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        img = (ImageView)findViewById(R.id.imv_main1);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        tvGioiThieu = (TextView)findViewById(R.id.textViewGioiThieu);
        tvNamePlace = (TextView)findViewById(R.id.textViewNamePlace);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
       // tvNamePlace.setText(image);
        String content = intent.getStringExtra("gioithieu");
        tvGioiThieu.setText(content);
        String nameplace = intent.getStringExtra("nameplace");
        tvNamePlace.setText(nameplace);

        String_To_ImageView(image, img);


    }
    public void String_To_ImageView(String strBase64, ImageView imw){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imw.setImageBitmap(decodedByte);
    }

}
