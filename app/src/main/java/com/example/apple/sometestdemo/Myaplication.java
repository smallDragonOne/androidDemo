package com.example.apple.sometestdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.apple.sometestdemo.NetworkManage.NetworkStateManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by apple on 16/6/23.
 */
public class Myaplication extends Application {

    private static Myaplication myaplication;

    private static Activity topActivity;

    private List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        myaplication = this;
        NetworkStateManager.instance().init(this);
    }

    public Myaplication(){}

    /**
     * 添加栈顶activity
     * @param activity  栈顶activity
     */
    public static void setTopActivity(Activity activity){
        Myaplication.topActivity = activity;
    }


    public static Activity getTopActivity(){
        return Myaplication.topActivity;
    }

    /**
     *
     * @param activity 添加的activity
     */
    public void addActivity(Activity activity){
        if (activity != null){
            activities.add(activity);
        }
    }

    /**
     *
     * @param activity  移除的activity
     */
    public void removeActivty(Activity activity){
        if (activities.contains(activity)){
            activities.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除所有activity
     */
    public void removeAllActivity(){
        for (Activity activity: activities){
            activity.finish();
            activities.remove(activity);
        }

        activities = null;
        System.exit(0);
    }

}
