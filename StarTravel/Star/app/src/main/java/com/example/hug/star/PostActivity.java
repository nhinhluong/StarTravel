package com.example.hug.star;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hug.star.Adapter.MeoDuLichAdapter;
import com.example.hug.star.DetailListview.Meodulich_Details2;
import com.example.hug.star.model.MeoDuLich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    ListView lwDienDan;
    ArrayList<MeoDuLich> mangMeoDuLich;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_dan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lwDienDan = (ListView)findViewById(R.id.lwDienDan);
        mangMeoDuLich = new ArrayList<MeoDuLich>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new PostActivity.LoadData().execute(getString(R.string.localhost_meodulich));
            }
        });
    }

    /////////Child Class
    private class LoadData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PostActivity.this);
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return GET_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    mangMeoDuLich.add(new com.example.hug.star.model.MeoDuLich(
                            object.getString("title"),
                            object.getString("mainimage"),
                            object.getString("content")
                    ));
                }
                final MeoDuLichAdapter adapter1 = new MeoDuLichAdapter(
                        PostActivity.this,
                        R.layout.row_diendan,
                        mangMeoDuLich
                );
                lwDienDan.setAdapter(adapter1);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private static String GET_URL(String theUrl){
        StringBuilder builder = new StringBuilder();
        try{
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                builder.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return builder.toString();
    }
}


