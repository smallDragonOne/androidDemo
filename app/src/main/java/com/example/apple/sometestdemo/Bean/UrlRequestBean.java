//package com.example.apple.sometestdemo.Bean;
//
//import android.app.Activity;
//
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONObject;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by wj on 2015/7/26.
// * 一个请求时可能有多个接口需要访问
// * 这里封装每个访问的相关数据
// */
//
//public class UrlRequestBean {
//    private List<BasicNameValuePair> postValue = null;
//    /**
//     * 引用提出请求的activity,但不影响activity的回收
//     */
//    private WeakReference<Activity> context_weak;
//    private String url = null;
//    private Options blocks;
//    /**
//     * 是否取消任务
//     */
//    public boolean isCanel = false;
//    /**
//     * 提供给管理器使用，标识是否已经在执行网络请求
//     */
//    public boolean isBegin = false;
//
//    public UrlRequestBean(Activity context,String url, Options action) {
//        postValue = new ArrayList<>();
//        context_weak = new WeakReference<>(context);
//        this.url = url;
//        this.blocks = action;
//    }
//
//    /**
//     * 添加参数
//     *
//     * @param key   key
//     * @param value value
//     */
//    public void addKeyValuePair(String key, String value) {
//        postValue.add(new BasicNameValuePair(key, value));
//    }
//    public Activity getContext(){
//        return context_weak.get();
//    }
//    public String getUrl() {
//        return this.url;
//    }
//
//    public List<BasicNameValuePair> getPostValue() {
//        return this.postValue;
//    }
//
//    public Options getBlocks() {
//        return this.blocks;
//    }
//
//   public static abstract class Options {
//        /**
//         * 请求开始前执行的操作
//         */
//       public void beforeRequest(){}
//
//        /**
//         * 出错时执行的操作
//         */
//        public void onError(){}
//
//        /**
//         * 获取数据后执行的操作
//         */
//        public void onGetData(JSONObject s){}
//
//        /**
//        * 要跳转登陆页前执行 add 2015.8.31
//        */
//       public void beforeGOlogin(){}
//    }
//}
