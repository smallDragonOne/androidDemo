package com.example.apple.sometestdemo.Bean;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by apple on 16/7/1.
 */
public class UrlRequestBeans {

    private WeakReference<Activity> context_weak;
    private String url;
    private Options blocks;

    public boolean isCancel = false;
    public boolean isBegin = false;

    public UrlRequestBeans(Activity activity , String url , Options options){
        context_weak = new WeakReference<Activity>(activity);
        this.url = url;
        this.blocks = options;
    }

    public Activity getContext(){
        return context_weak.get();
    }


    public String getUrl(){
        return url;
    }

    public Options getBlocks(){
        return blocks;
    }



    public static abstract class Options{
        public void beforeRequest(){}

        public void onError(){}

        public void onGetData(String Json){}
    }


}
