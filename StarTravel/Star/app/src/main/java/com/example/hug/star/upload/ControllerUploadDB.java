package com.example.hug.star.upload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hug.star.R;

public class ControllerUploadDB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_upload_db);

        TextView upMain = (TextView)findViewById(R.id.upMain);
        TextView upMeoDuLich = (TextView)findViewById(R.id.upMeoDuLich);
        //TextView upDiaDanh = (TextView)findViewById(R.id.upDiaDanh);

        upMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerUploadDB.this, UploadMainTable.class);
                startActivity(intent);
            }
        });

        upMeoDuLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerUploadDB.this, UploadMeoDuLich.class);
                startActivity(intent);
            }
        });
        //upDiaDanh.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(ControllerUploadDB.this, UploadDiaDanh.class);
        //        startActivity(intent);
         //   }
        //});
    }
}
