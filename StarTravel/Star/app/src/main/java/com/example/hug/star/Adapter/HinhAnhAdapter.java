package com.example.hug.star.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hug.star.R;
import com.example.hug.star.model.HinhAnh;

import java.util.List;

/**
 * Created by LTN on 07-Mar-17.
 */

public class HinhAnhAdapter extends BaseAdapter {
    Context mcontext;
    int myLayout;
    List<HinhAnh> arrayHinhAnh;

    public HinhAnhAdapter(Context context, int layout, List<HinhAnh> arrHinhAnh){
        mcontext = context;
        myLayout = layout;
        arrayHinhAnh = arrHinhAnh;
    }

    @Override
    public int getCount() {
        return arrayHinhAnh.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);
        // GAN GIA TRI
       // TextView title = (TextView)convertView.findViewById(R.id.twTitle);
        //title.setText(arrayMeoDuLich.get(position).Title);
        // Kieu Int thi (String.valueOf())
        //TextView content = (TextView)convertView.findViewById(R.id.twContent);
        //content.setText(arrayMeoDuLich.get(position).Content);

        //ImageView imageProduct = (ImageView) convertView.findViewById(R.id.imageViewBNK);
        //String_To_ImageView(arrayHinhAnh.get(position).Image, imageProduct);

        return convertView;
    }

    public void String_To_ImageView(String strBase64, ImageView imw){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imw.setImageBitmap(decodedByte);
    }
}
