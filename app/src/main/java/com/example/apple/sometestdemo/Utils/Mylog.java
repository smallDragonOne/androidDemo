package com.example.apple.sometestdemo.Utils;

import android.util.Log;

/**
 * Created by apple on 16/6/24.
 */
public class Mylog {

    private static String str = "log";
    public static void dLog( String msg){
        Log.d(str ,msg);
    }

    public static void eLog(String msg){
        Log.e(str,msg);
    }
}
