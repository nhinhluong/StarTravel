package com.example.hug.star.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hug.star.MapsActivity;
import com.example.hug.star.R;

public class SettingActivity extends AppCompatActivity implements OnItemSelectedListener {

    String radius[] = {"1000m" , "800m", "600m"};
    String result[] = {"20", "40"};
    String language[] ={"English", "Vietnamese"};
    Spinner radiussn;
    Spinner resultsn;

    public static  int radiusvalue = 1000;
    public static  int resfultvalue = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();
        ArrayAdapter<String> adapterradius = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,radius);
        adapterradius.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        radiussn = (Spinner)findViewById(R.id.spinnerRadius);
        radiussn.setAdapter(adapterradius);
        radiussn.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapterresult = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,result);
        adapterresult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultsn  = (Spinner)findViewById(R.id.spinnerresult);
        resultsn.setAdapter(adapterresult);
        resultsn.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapterlanguage = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,language);
        adapterlanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner language  = (Spinner)findViewById(R.id.spinnerLanguage);
        language.setAdapter(adapterlanguage);

        Button bntSave = (Button)findViewById(R.id.buttonsave);
        bntSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSetting = new Intent(SettingActivity.this, MapsActivity.class);
                startActivity(toSetting);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item  = parent.getItemAtPosition(position).toString();
        if(parent == radiussn){
            radiusvalue = Integer.valueOf(item.replace("m",""));
        }
        if (parent == resultsn){
            resfultvalue = Integer.valueOf(item);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
