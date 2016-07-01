package com.example.apple.sometestdemo.DataStorage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by apple on 16/6/24.
 */
public class SharePrefeceHandle {


    /**
     * 持久化文件名
     */
    private static final String[] prefeceName= {"sharePreferece"};
    /**
     * 存入值
     * @param activity
     * @param str     持久化文件名
     * @param hashMap
     */
    public static void SetSharePrefeceHandle(Activity activity , String str ,HashMap<String,String> hashMap){
        SharedPreferences preferences = activity.getSharedPreferences(str , Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        Set<Map.Entry<String,String>> sets = hashMap.entrySet();
        for (Map.Entry<String,String> entry: sets){
            editor.putString(entry.getKey(),entry.getValue());
        }
        editor.commit();
    }


    /**
     * 取出值
     * @param activity
     * @param str
     * @return
     */
    public static SharedPreferences getPreferences(Activity activity , String str){
        SharedPreferences preferences = activity.getSharedPreferences(str, Context.MODE_PRIVATE);
        return preferences;
    }


    public static String[] getPreferecName(){
        return prefeceName;
    }

}
