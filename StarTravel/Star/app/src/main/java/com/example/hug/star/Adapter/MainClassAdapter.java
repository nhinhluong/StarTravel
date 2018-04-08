package com.example.hug.star.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hug.star.DetailListview.Details1;
import com.example.hug.star.R;
import com.example.hug.star.model.MainClass;
import com.example.hug.star.model.MeoDuLich;

import java.util.List;

/**
 * Created by LTN on 08-Mar-17.
 */

public class MainClassAdapter extends BaseAdapter {
    Context mcontext;
    int myLayout;
    List<MainClass> arrayMainClass;
    public MainClassAdapter(Context context, int layout, List<MainClass> arrMainClass){
        mcontext = context;
        myLayout = layout;
        this.arrayMainClass = arrMainClass;
    }
    @Override
    public int getCount() {
        return arrayMainClass.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);
        // GAN GIA TRI
        TextView namePlace = (TextView)convertView.findViewById(R.id.twNamePlace);
        namePlace.setText(arrayMainClass.get(position).NamePlace);
        // Kieu Int thi (String.valueOf())
       // TextView content = (TextView)convertView.findViewById(R.id.twContent);
        //content.setText(arrayMainClass.get(position).GioiThieu);

        ImageView imageProduct = (ImageView) convertView.findViewById(R.id.imageViewMA);
        String_To_ImageView(arrayMainClass.get(position).Image, imageProduct);
        imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mcontext.getApplicationContext(), Details1.class);
                it.putExtra("nameplace",arrayMainClass.get(position).NamePlace);
                it.putExtra("gioithieu",arrayMainClass.get(position).GioiThieu);
                it.putExtra("image",arrayMainClass.get(position).Image);
                ((Activity)mcontext).startActivity(it);
            }
        });

        return convertView;
    }
    public void String_To_ImageView(String strBase64, ImageView imw){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imw.setImageBitmap(decodedByte);
    }
}
