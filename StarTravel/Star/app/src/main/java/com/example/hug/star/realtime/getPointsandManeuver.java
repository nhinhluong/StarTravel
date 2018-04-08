package com.example.hug.star.realtime;

import com.example.hug.star.nearby.Readjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HuG on 2/21/2017.
 */

public class getPointsandManeuver extends Readjson {

    public static ArrayList maneuver = new ArrayList();
    public static ArrayList Latpoint = new ArrayList();
    public static ArrayList Lngpoint = new ArrayList();

    public void setRealTime(double olat, double olng, double dlat, double dlng) throws IOException, JSONException {

        String url = "http://maps.googleapis.com/maps/api/directions/json?" +
                "origin="+ String.valueOf(olat)+","+ String.valueOf(olng)+
                "&destination="+ String.valueOf(dlat)+","+ String.valueOf(dlng)+
                "&sensor=false&language=en";
        System.out.println(url);

        JSONObject a = readJsonFromUrl(url);
        JSONArray routes = a.getJSONArray("routes");
        JSONObject routes0 = routes.getJSONObject(0);
        JSONArray legs = routes0.getJSONArray("legs");
        JSONObject legs0 = legs.getJSONObject(0);
        JSONArray steps = legs0.getJSONArray("steps");

        ////////////////
        for (int i = 0; i < steps.length(); i++) {
            JSONObject stepsi = steps.getJSONObject(i);
            JSONObject startlocation = stepsi.getJSONObject("start_location");

            String editlat = startlocation.getString("lat");
            editlat = editlat.replaceAll(",", "");
            Latpoint.add(editlat);
            Lngpoint.add(startlocation.getString("lng"));

            if (!stepsi.has("maneuver") && i == 0) {
                String out = stepsi.getString("html_instructions");
                out = out.replaceAll("<b>", "");
                out = out.replaceAll("</b>", "");
                maneuver.add(out);
            } else {
                // just get mauneuver in i > 0
                if (!stepsi.has("maneuver")) {
                    maneuver.add("go on");
                } else {
                    String out = stepsi.getString("maneuver");
                    out = out.replaceAll("-"," ");
                    maneuver.add(out);

                }
            }
        }
    }
}
