package com.example.hug.star.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hug.star.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class UploadMainTable extends AppCompatActivity {
    Button btn, btnList;
    ImageView imageView;
    EditText nameplace;
    EditText gioithieu;
    EditText diadiem;
    int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_main_table);
        btn = (Button)findViewById(R.id.btnbutton);
        imageView = (ImageView)findViewById(R.id.imageViewMain);
        nameplace = (EditText)findViewById(R.id.edNamePlace);
        gioithieu = (EditText)findViewById(R.id.edGioiThieu);
        diadiem = (EditText)findViewById(R.id.edDiaDiem);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String title = titlename.getText().toString();
                //String content = contentname.getText().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new UploadMainTable.UploadData().execute();
                    }
                });
            }
        });
    }

    public String ImageView_To_String(ImageView iv){
        BitmapDrawable bitmapDrawable = (BitmapDrawable)iv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] bytes = stream.toByteArray();
        String strImage = Base64.encodeToString(bytes, Base64.DEFAULT);
        return  strImage;
    }

    private class UploadData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            return POST_URL(getResources().getString(R.string.localhost_maintable), null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("")){
                Toast.makeText(UploadMainTable.this, "Upload failed", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(UploadMainTable.this, "Upload success", Toast.LENGTH_LONG).show();
            }
        }


    }
    private String POST_URL(String url, String type){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List nameValues = new ArrayList(3);
        //int userid = Integer.parseInt(user_id.getText().toString());
        nameValues.add(new BasicNameValuePair("nameplace", nameplace.getText().toString()));
        nameValues.add(new BasicNameValuePair("gioithieu", gioithieu.getText().toString()));
        nameValues.add(new BasicNameValuePair("diadiem", diadiem.getText().toString()));
        //nameValues.add(new BasicNameValuePair("user_id",userid+""));

        String sHinh = ImageView_To_String(imageView);
        nameValues.add(new BasicNameValuePair("image", sHinh));
        // POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValues, "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String kq = "";
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            kq = EntityUtils.toString(httpEntity);

        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return  kq;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(bitmap);
            ///*
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageView = (ImageView)findViewById(R.id.imageViewMain);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
            //*/
            //

        }
    }
}
