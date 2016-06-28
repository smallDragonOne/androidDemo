//package com.example.apple.sometestdemo.NetworkManage;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.Toast;
//
//import com.example.hikvision.R;
//import com.example.hikvision.beans.UrlRequestBean;
//import com.example.hikvision.db.DButil;
//import com.example.hikvision.utils.CanGoToLoginActivity;
//import com.example.hikvision.utils.LogContent;
//import com.example.hikvision.utils.SSLSocketFactoryEx;
//import com.example.hikvision.utils.SomeUtils;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.CoreConnectionPNames;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
//
//import java.lang.ref.WeakReference;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by wj on 2015/7/26.
// * 使用单例+访问者
// * 每次begin()执行都将当前队列中的任务全部执行并移除，每一批任务执行只会读取一次token
// * doRequestNonToken(final UrlRequestBean urlBean)用于访问无需token的接口
// */
//public enum UrlRequestManager {
//    Instance;
//    private List<UrlRequestBean> requestData = new LinkedList<>();
//    private boolean isRefreshing = false;
//    final private int limit = 6000_000;
//
//    /**
//     * 添加任务入队
//     *
//     * @param bean UrlRequestBean
//     */
//    public void addRequest(UrlRequestBean bean) {
//        requestData.add(bean);
//    }
//
//    /**
//     * 激活任务队列
//     * 如果当前正在刷新token，等待服务器响应，则直接返回，
//     * token刷新完成后会自动遍历执行队中未开始的任务；
//     * 否则激活新一轮队列轮询
//     */
//    public void begin() {
//        if (isRefreshing) {
//            return;
//        }
//        //当前页面
//        final Activity context = MyApplication.getTopActivity();
//        //判断网络连接
//        boolean net = NetworkStateManager.instance().isNetworkConnected();
//        LogContent.printLog("网络连接:" + net);
//        if (!net) {
//            Toast.makeText(context, "请检查网络连接", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        //获取token
//        if(isInvaild(context)){
//            final String appSecret = DButil.getValue(context, "app_secret");
//            final String url = SomeUtils.getUrl(R.string.json_login) + "token.json";
//            UrlRequestBean bean = new UrlRequestBean(context, url, new UrlRequestBean.Options() {
//                @Override
//                public void beforeRequest() {
//
//                }
//
//                @Override
//                public void onError() {
//                    // 回登录页
//                    doSthAndReLogin();
//                }
//
//                @Override
//                public void onGetData(JSONObject json) {
//                    //获取数据成功
//                    try {
//                        DButil db = new DButil();
//                        String access_token = json.getString("access_token");
//                        db.updateValue(context, "access_token", access_token);
//                        db.updateValue(context, "access_token_time", String.valueOf(System.currentTimeMillis()));
//                        doRequestCurrent(access_token);
//                    } catch (Exception e) {
//                        // 回登录页 {"errcode":"42101","errmsg":"app_secret_invalid"}
//                        doSthAndReLogin();
//                    }
//                }
//            });
//            bean.addKeyValuePair("app_secret", appSecret);
//
//            // 防止handler操作时被回收
//            addRequest(bean);
//            this.doRequestNonToken(bean);
//        } else {
//            // token未超时
//            doRequestCurrent(DButil.getValue(context, "access_token"));
//        }
//    }
//
//    /**
//     * token是否已过期
//     */
//    public boolean isInvaild(Activity context){
//        long oldTime = Long.valueOf(DButil.getValue(context, "access_token_time"));
//        long newTime = System.currentTimeMillis();
//        // token超时, 用appSecret 刷新 token
//        if (newTime - oldTime >= limit){
//            return  true;
//        }else{
//            return false;
//        }
//    }
//
//    /**
//     * 用于刷新token，登录等不需要token的接口
//     * @param urlBean UrlRequestBean
//     */
//    public void doRequestNonToken(final UrlRequestBean urlBean) {
//        if (!urlBean.isCanel && !urlBean.isBegin) {
//            // 开始刷新token
//            isRefreshing = true;
//            // urlBean状态变更为正在执行中
//            urlBean.isBegin = true;
//            urlBean.getBlocks().beforeRequest();
//
//            final Handler handler = new MyHandle(urlBean, true);
//            HttpPostThread httpPostThread = new HttpPostThread(handler, urlBean, null);
//            new Thread(httpPostThread).start();
//        }
//        else{
//            if(urlBean.isCanel){
//                removeRequest(urlBean);
//            }
//        }
//    }
//
//    // *************  私有方法，建立并获取数据及执行相关操作
//
//    /**
//     * 执行批量访问
//     *
//     * @param token access_token
//     */
//    private void doRequestCurrent(final String token) {
//        for (UrlRequestBean bean : requestData) {
//            if (!bean.isCanel && !bean.isBegin) {
//                bean.isBegin = true;
//                doRequest(bean, token);
//            }
//        }
//        // 移除已取消的bean
//        Iterator<UrlRequestBean> iterator = requestData.iterator();
//        while(iterator.hasNext()){
//            UrlRequestBean bean = iterator.next();
//            if(bean.isCanel){
//                iterator.remove();
//            }
//        }
//    }
//
//    /**
//     * 访问接口，不会自动获取token
//     *
//     * @param urlBean UrlRequestBean
//     */
//    private void doRequest(final UrlRequestBean urlBean, final String token) {
//        if (!urlBean.isCanel) {
//            urlBean.getBlocks().beforeRequest();
//            if (token != null) {
//                final Handler handler = new MyHandle(urlBean, false);
//                HttpPostThread httpPostThread = new HttpPostThread(handler, urlBean, token);
//                new Thread(httpPostThread).start();
//            }
//        }
//    }
//
//    /**
//     * handler实现
//     */
//    static private class MyHandle extends Handler {
//        private WeakReference<UrlRequestBean> urlBean_weak;
//        private boolean isTokenRequest = false;
//
//        MyHandle(UrlRequestBean urlBean, boolean value) {
//            this.urlBean_weak = new WeakReference<>(urlBean);
//            isTokenRequest = value;
//        }
//
//        public void handleMessage(Message msg) {
//            UrlRequestBean urlBean = urlBean_weak.get();
//            if (urlBean.getContext() != null || isTokenRequest) {
//                switch (msg.what) {
//                    case 200:
//                        // 获取网络成功
//                        Bundle data = msg.getData();
//                        String s = data.getString("value");
//                        // 返回信息输出
//                        LogContent.printLog(s);
//
//                        if (!urlBean.isCanel && s != null) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(s);
//                                final Activity context = MyApplication.getTopActivity();
//                                CanGoToLoginActivity canGoToLoginActivity = new CanGoToLoginActivity();
//                                boolean goLogin = canGoToLoginActivity.isGoToLoginActivity(jsonObject);
//                                if(goLogin){
//                                    // 返回登陆页
//                                    urlBean.getBlocks().beforeGOlogin();
//                                    canGoToLoginActivity.goToLoginActivity(context);
//                                }
//                                else {
//                                    // 解析数据
//                                    urlBean.getBlocks().onGetData(jsonObject);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                urlBean.getBlocks().onError();
//                            }
//                        }
//                        break;
//                    default:
//                        LogContent.printLog(msg.what+":handler.default");
//                        if (!urlBean.isCanel) {
//                            urlBean.getBlocks().onError();
//                        }
//                        break;
//                }
//            }
//            // 移除已完成的访问。
//            UrlRequestManager requestManager = UrlRequestManager.Instance;
//            requestManager.removeRequest(urlBean);
//            requestManager.isRefreshing = false;
//        }
//    }
//
//    /**
//     * 异步线程
//     */
//    private class HttpPostThread implements Runnable {
//        /**
//         * handler处理
//         */
//        final private Handler handler;
//        final private UrlRequestBean urlBean;
//        final private String token;
//
//        public HttpPostThread(Handler handler, UrlRequestBean urlBean, String token) {
//            this.handler = handler;
//            this.urlBean = urlBean;
//            this.token = token;
//        }
//
//        @Override
//        public void run() {
//            // 获取我们回调主ui的message
//            String url = urlBean.getUrl();
//            try {
//                HttpPost httpRequest = new HttpPost(url);
//
//                List<BasicNameValuePair> postValue = urlBean.getPostValue();
//                if (token != null) {
//                    postValue.add(new BasicNameValuePair("access_token", token));
//                }
//
//                httpRequest.setEntity(new UrlEncodedFormEntity(postValue, HTTP.UTF_8));
//
//                HttpClient client = SSLSocketFactoryEx.getNewHttpClient();
//                // 请求超时
//                client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
//                // 读取超时
//                client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
//                HttpResponse response = client.execute(httpRequest);
//                int resultCode = response.getStatusLine().getStatusCode();
//                if (resultCode == 200) {
//                    // 获得数据
//                    String str = EntityUtils.toString(response.getEntity());
//                    Message msg = new Message();
//                    msg.what = resultCode;
//                    Bundle data = new Bundle();
//                    data.putString("value", str);
//                    msg.setData(data);
//                    handler.sendMessage(msg);
//                } else {
//                    // 错误
//                    Message msg = new Message();
//                    msg.what = resultCode;
//                    handler.sendMessage(msg);
//                }
//            } catch (Exception e) {
//                // 超时 or数据解析出错
//                e.printStackTrace();
//                Message msg = new Message();
//                msg.what = -1;
//                handler.sendMessage(msg);
//            }
//        }
//    }
//
//    /**
//     * 执行完的任务出队
//     *
//     * @param bean UrlRequestBean
//     */
//    private void removeRequest(UrlRequestBean bean) {
//        requestData.remove(bean);
//    }
//
//    /**
//     * token过期
//     */
//    public void doSthAndReLogin(){
//        for (UrlRequestBean bean : requestData) {
//            if (!bean.isCanel && !bean.isBegin) {
//                bean.getBlocks().beforeGOlogin();
//                // 设为取消
//                bean.isCanel = true;
//            }
//        }
//        // 回登录页
//        final Activity context = MyApplication.getTopActivity();
//        new CanGoToLoginActivity().goToLoginActivity(context);
//    }
//}
