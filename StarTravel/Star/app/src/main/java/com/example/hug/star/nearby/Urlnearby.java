package com.example.hug.star.nearby;

public class Urlnearby implements APIskey {

	public String createUrlnearby(String lat, String lng, String type, String name, String radius){
		String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
		url = url+lat+",%20"+lng+"&radius="+radius+"&types="+type+"&name="+name+"&key="+key;
		//// them api key ... neu co loi
		
		return url;
	}
	
}
