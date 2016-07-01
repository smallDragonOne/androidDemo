package com.example.apple.sometestdemo.NetworkManage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.apple.sometestdemo.Bean.UrlRequestBeans;
import com.example.apple.sometestdemo.Myaplication;
import com.example.apple.sometestdemo.Utils.Mylog;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by apple on 16/7/1.
 * 使用单例+访问者
 * 每次begin()执行都将当前队列中的任务全部执行并移除，每一批任务执行只会读取一次token
 * doRequestNonToken(final UrlRequestBean urlBean)用于访问无需token的接口
 */
public enum  OkhttpRequestManage {

    instance;
    private List<UrlRequestBeans> requestData = new LinkedList<>();

    public void addRequest(UrlRequestBeans beans){
        requestData.add(beans);
    }

    /**
     * 激活任务队列
     * 如果当前正在刷新token，等待服务器响应，则直接返回，
     * token刷新完成后会自动遍历执行队中未开始的任务；
     * 否则激活新一轮队列轮询
     */
    public void begin(){

        final Activity context = Myaplication.getTopActivity();

        boolean netVisable = NetworkStateManager.instance().isNetworkConnected();
        Mylog.dLog(String.valueOf(netVisable));
        if (!netVisable){
            Toast.makeText(context,"网络不可以用",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            doRequestCurrent();
        }
    }

    // *************  私有方法，建立并获取数据及执行相关操作

    /**
     * 执行批量访问
     */
    private void doRequestCurrent(){
        for (UrlRequestBeans bean : requestData) {
            if (!bean.isCancel && !bean.isBegin) {
                bean.isBegin = true;
                doRequest(bean);
            }
        }


        // 移除已取消的bean
        Iterator<UrlRequestBeans> iterator = requestData.iterator();
        while(iterator.hasNext()){
            UrlRequestBeans bean = iterator.next();
            if(bean.isCancel){
                iterator.remove();
            }
        }
    }


    /**
     * 访问接口，不会自动获取token
     *
     * @param urlBean UrlRequestBean
     */
    private void doRequest(final UrlRequestBeans urlBean) {
        if (!urlBean.isCancel) {
            urlBean.getBlocks().beforeRequest();

            final Handler handler = new MyHandle(urlBean, false);

            OkHttpHandles.getCallRequest(urlBean.getUrl()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message msg = new Message();
                    msg.what = -2;
                    handler.sendMessage(msg);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {
                        Message msg = new Message();
                        msg.what = response.code();
                        Bundle data = new Bundle();
                        data.putString("value", response.body().string());
                        msg.setData(data);
                        handler.sendMessage(msg);
                    } else {
                        // 错误
                        Message msg = new Message();
                        msg.what = response.code();
                        handler.sendMessage(msg);
                    }
                }
            });

            //HttpPostThread httpPostThread = new HttpPostThread(handler, urlBean);
            //new Thread(httpPostThread).start();
        }
    }


    private static class MyHandle extends Handler{
        private WeakReference<UrlRequestBeans> urlBean_weak;
        private boolean isTokenRequest = false;

        MyHandle(UrlRequestBeans urlBean, boolean value) {
            this.urlBean_weak = new WeakReference<>(urlBean);
            isTokenRequest = value;
        }

        @Override
        public void handleMessage(Message msg) {
            UrlRequestBeans urlRequestBeans = urlBean_weak.get();
            if (urlRequestBeans.getContext() != null || isTokenRequest){
                Mylog.dLog(String.valueOf(msg.what));
                switch (msg.what){
                    case 200:
                        // 获取网络成功
                        Bundle data = msg.getData();
                        String s = data.getString("value");
                        urlRequestBeans.getBlocks().onGetData(s);
                        Mylog.dLog(s);
                        break;
                    case -2:
                        Mylog.dLog("访问失败");
                        break;
                    default:
                        Mylog.dLog(msg.what+":handler.default");
                        if (!urlRequestBeans.isCancel) {
                            urlRequestBeans.getBlocks().onError();
                        }
                        break;
                }
            }
            /// 移除完成访问
            OkhttpRequestManage manage = OkhttpRequestManage.instance;
            manage.removeRequest(urlRequestBeans);
        }
    }


    private class HttpPostThread implements Runnable {

        /**
         * handler处理
         */
        final private Handler handler;
        final private UrlRequestBeans urlBean;

        public HttpPostThread(Handler handler, UrlRequestBeans urlBean){
            this.handler = handler;
            this.urlBean = urlBean;
        }
        @Override
        public void run() {


        }
    }

    /**
     * 执行完的任务出队
     * @param bean UrlRequestBean
     */
    private void removeRequest(UrlRequestBeans bean) {
        requestData.remove(bean);
    }

}
