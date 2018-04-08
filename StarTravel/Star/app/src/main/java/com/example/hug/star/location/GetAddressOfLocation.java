package com.example.hug.star.location;

import com.example.hug.star.nearby.APIskey;
import com.example.hug.star.nearby.Readjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by HuG on 2/20/2017.
 */

public class GetAddressOfLocation extends Readjson implements APIskey {

    public String getAddress(double lat, double lng) throws IOException, JSONException {
        String result = "";
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+
                String.valueOf(lat)+","+ String.valueOf(lng)+"&key="+key;
        JSONObject json = readJsonFromUrl(url);
        JSONArray results = json.getJSONArray("results");
        JSONObject result0 = results.getJSONObject(0);
        result = result0.getString("formatted_address");
        //System.out.println("------------------"+result);
        miniTranslate translate = new miniTranslate();
        result = translate.translateAdress(result);
        //System.out.println("------------------"+result);
        return result;
    }
}
