package com.example.hug.star.location;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class miniTranslate {
   public static String translateAdress(String a) {
      String finalString = "";
      String[] b = a.split(",");
      
      for(String c : b){
    	  if(c.indexOf("Tỉnh")!= -1 ){
    		  c = c.replace("Tỉnh", "");
    		  c = c+" province";
    	  }
    	  if(c.indexOf("Thành phố")!= -1 || c.indexOf("TP.")!= -1 ||c.indexOf("Tp")!= -1){
    		  c = c.replace("Thành phố", "");
    		  c = c.replace("TP.", "");
    		  c = c.replace("Tp", "");
    		  c = c+" city";
    	  }
    	  if(c.indexOf("Phường")!= -1 || c.indexOf("P.") != -1){
    		  c = c.replace("Phường", "");
    		  c = c.replace("P.", "");
    		  c = c+" ward";
    	  }
    	  if(c.indexOf("Huyện")!= -1 || c.indexOf("huyện")!= -1 ||c.indexOf("Quận")!= -1){
    		  c = c.replace("Huyện", "");
    		  c = c.replace("huyện", "");
    		  c = c.replace("Quận", "");
    		  c = c+" district";
    	  }
    	  if(c.indexOf("Đường")!= -1 ){
    		  c = c.replace("Đường", "");
    		  c = c+" street";
    	  }
    	  if(c.indexOf("Thị Trấn")!= -1 || c.indexOf("TT.")!= -1 || c.indexOf("tt.")!= -1){
    		  c = c.replace("Thị trấn", "");
    		  c = c.replace("TT.", "");
    		  c = c.replace("tt.", "");
    		  c = c+" burgh";
    	  }
    	  if(c.indexOf("ấp")!= -1 || c.indexOf("Ấp")!= -1 ){
    		  c = c.replace("Ấp", "");
    		  c = c.replace("ấp", "");
    		  c = c+" hamlet";
    	  }
    	  if(c.indexOf("tổ")!= -1 ){
    		  c = c.replace("tổ", "");
    		  c = c+" group";
    	  }
    	  if(c.indexOf("Hẻm")!= -1 ){
    		  c = c.replace("Hẻm", "");
    		  c = c+" alley";
    	  }
    	 ///// ++++
    	  finalString = finalString.replaceAll("Đ", "D") +c+",";
    	  finalString = finalString.replaceAll("đ", "d");
      }
	return removeAccent(finalString);
   }

   public static String removeAccent(String s) {
	   String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
	   Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	   return pattern.matcher(temp).replaceAll("");
	  	}
}