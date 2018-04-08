package com.example.hug.star.direction;

/**
 * Created by HuG on 4/5/2017.
 */

public class changedistanceandduration {
    public static double changedistance(String text){
        double result = 0;
        String edittext ="";
        edittext = text.replace("km","");
        result = Double.valueOf(edittext);
        return  result;
    }
    public static int changeduration(String text){
        int result = 0;
        String edittext ="";
        edittext = text.replace("min","");
        result = Integer.valueOf(edittext);
        return  result;
    }
}
