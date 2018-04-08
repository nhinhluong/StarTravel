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
import com.example.hug.star.DetailListview.Meodulich_Details2;
import com.example.hug.star.R;
import com.example.hug.star.model.MeoDuLich;

import java.util.List;

/**
 * Created by LTN on 04-Mar-17.
 */

public class MeoDuLichAdapter extends BaseAdapter {
    Context mcontext;
    int myLayout;
    List<com.example.hug.star.model.MeoDuLich> arrayMeoDuLich;

    public MeoDuLichAdapter(Context context, int layout, List<MeoDuLich> arrMeoDuLich){
        mcontext = context;
        myLayout = layout;
        arrayMeoDuLich = arrMeoDuLich;
    }

    @Override
    public int getCount() {
        return arrayMeoDuLich.size();
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
        TextView title = (TextView)convertView.findViewById(R.id.twTitle);
        title.setText(arrayMeoDuLich.get(position).Title);
        // Kieu Int thi (String.valueOf())
        TextView content = (TextView)convertView.findViewById(R.id.twContent);
        content.setText(arrayMeoDuLich.get(position).Content);

        ImageView imageProduct = (ImageView) convertView.findViewById(R.id.imageViewMDL);
        String_To_ImageView(arrayMeoDuLich.get(position).MainImage, imageProduct);


        TextView xemthem = (TextView)convertView.findViewById(R.id.twXemThem);

        xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mcontext.getApplicationContext(), Meodulich_Details2.class);
                it.putExtra("title",arrayMeoDuLich.get(position).Title);
                it.putExtra("content",arrayMeoDuLich.get(position).Content);
                it.putExtra("mainimage",arrayMeoDuLich.get(position).MainImage);
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
