package com.example.hug.star.nearby;

import com.example.hug.star.setting.SettingActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Readnextpage extends Readjson {

	public ArrayList<String> getNextpage(String urlin) throws IOException, JSONException, InterruptedException {
		JSONObject nearby = readJsonFromUrl(urlin);
		ArrayList<String> urls = new ArrayList<>();
		urls.add(urlin);
		if (SettingActivity.resfultvalue == 40) {
			if (nearby.has("next_page_token")) {
				String add = nearby.getString("next_page_token");
				String newurl = urlin + "&pagetoken=" + add;
				urls.add(newurl);
			}

		}
		return urls;
	}


}
