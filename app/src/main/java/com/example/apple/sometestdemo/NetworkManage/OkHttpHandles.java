package com.example.apple.sometestdemo.NetworkManage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.widget.Toast;

import com.example.apple.sometestdemo.ActivityManage.FileStoreActivity;
import com.example.apple.sometestdemo.Bean.UrlRequestBeans;
import com.example.apple.sometestdemo.Utils.Mylog;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zj on 16/6/30.
 * 网络请求封装
 */
public class OkHttpHandles {

    private static final OkHttpClient client = new OkHttpClient();

    final static Handler handler = new Handler();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    /**
     * 同步线程获取响应
     * @param request
     * @return
     * @throws IOException
     */
    public static Response getExecute(Request request) throws IOException{
        return client.newCall(request).execute();
    }

    public  static  void getEnqueue(Request request ,Callback callback) throws IOException{
         client.newCall(request).enqueue(callback);
    }

    public static  void getHttpRequests(String url){
        final Request request = new Request.Builder().url(url).build();

        try{
            getEnqueue(request, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    new Runnable() {
                        @Override
                        public void run() {

                        }
                    };
                    //options.onError();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int resultCode = response.code();
                            Message msg = new Message();
                            msg.what = resultCode;
                            Bundle data = new Bundle();

                            data.putString("value", "values");
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }
                    });
                    //options.onGetData(response.body().string());
                }
            });
        }
        catch (IOException io){
            io.printStackTrace();
        }
    }

    public  static void getHttpRequest(String url , final Activity activity,final CallBackOptions options){
        options.requestBegin();
        Request request = new Request.Builder().url(url).build();
        try{
            getEnqueue(request, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            options.requesrFailure();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.isSuccessful()){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    options.reposeData(response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    else {
                        Mylog.dLog("请求失败lllll");
                        Toast.makeText(activity,"请求失败",Toast.LENGTH_SHORT).show();
                    }

                }

            });

        }
        catch (IOException io){
            io.printStackTrace();
        }
    }

    public static Call getCallRequest(String url){
        final Request request = new Request.Builder().url(url).build();
        return client.newCall(request);
    }

    /**
     * 抽象类封装回调数据
     */
    public static abstract class CallBackOptions{

        public abstract void requestBegin();

        public abstract void requesrFailure();

        public abstract void reposeData(String JsonData);
    }


    /// get请求  异步请求
    public  static String getHttpRequest(String url){

        String responseStr = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try{

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Mylog.dLog("失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Mylog.dLog("成功");
                    String str = response.body().string();
                    Mylog.dLog(str);
                }
            });
        }

        catch (Exception ex){
            ex.printStackTrace();
        }
        if (responseStr == null){
            Mylog.dLog("空的");
        }
        return responseStr;
    }

    /// post请求

    public static String postRequest(String url ,String json){

        String responseStr = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(OkHttpHandles.JSON,json);
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            responseStr = response.body().string();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Mylog.dLog(responseStr);
        return responseStr;
    }



}
