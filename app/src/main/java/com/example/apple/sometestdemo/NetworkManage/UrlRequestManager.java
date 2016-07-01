package com.example.apple.sometestdemo.NetworkManage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract;
import android.widget.Toast;


import com.example.apple.sometestdemo.Myaplication;
import com.example.apple.sometestdemo.Utils.Mylog;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import com.example.apple.sometestdemo.ApiManage.ApiUtils;
import com.example.apple.sometestdemo.ApiManage.DESEncrypt;
import com.example.apple.sometestdemo.Bean.UrlRequestBean;

/**
 * Created by wj on 2015/7/26.
 * 使用单例+访问者
 * 每次begin()执行都将当前队列中的任务全部执行并移除，每一批任务执行只会读取一次token
 * doRequestNonToken(final UrlRequestBean urlBean)用于访问无需token的接口
 */
public enum UrlRequestManager {
    Instance;
    private List<UrlRequestBean> requestData = new LinkedList<>();
    /**
     * 添加任务入队
     * @param bean UrlRequestBean
     */
    public void addRequest(UrlRequestBean bean) {
        requestData.add(bean);
    }

    /**
     * 激活任务队列
     * 如果当前正在刷新token，等待服务器响应，则直接返回，
     * token刷新完成后会自动遍历执行队中未开始的任务；
     * 否则激活新一轮队列轮询
     */
    public void begin() {
        //当前页面
        final Activity context = Myaplication.getTopActivity();
        //判断网络连接
        boolean net = NetworkStateManager.instance().isNetworkConnected();
        Mylog.dLog(String.valueOf(net));
        if (!net) {
            Toast.makeText(context, "请检查网络连接", Toast.LENGTH_LONG).show();
            return;
        }
        doRequestCurrent();
    }
    // *************  私有方法，建立并获取数据及执行相关操作

    /**
     * 执行批量访问
     */
    private void doRequestCurrent() {
        for (UrlRequestBean bean : requestData) {
            if (!bean.isCanel && !bean.isBegin) {
                bean.isBegin = true;
                doRequest(bean);
            }
        }
        // 移除已取消的bean
        Iterator<UrlRequestBean> iterator = requestData.iterator();
        while(iterator.hasNext()){
            UrlRequestBean bean = iterator.next();
            if(bean.isCanel){
                iterator.remove();
            }
        }
    }

    /**
     * 访问接口，不会自动获取token
     *
     * @param urlBean UrlRequestBean
     */
    private void doRequest(final UrlRequestBean urlBean) {
        if (!urlBean.isCanel) {
            urlBean.getBlocks().beforeRequest();
            final Handler handler = new MyHandle(urlBean, false);
            HttpPostThread httpPostThread = new HttpPostThread(handler, urlBean);
            new Thread(httpPostThread).start();
        }
    }

    /**
     * handler实现
     */
    static private class MyHandle extends Handler {
        private WeakReference<UrlRequestBean> urlBean_weak;
        private boolean isTokenRequest = false;

        MyHandle(UrlRequestBean urlBean, boolean value) {
            this.urlBean_weak = new WeakReference<>(urlBean);
            isTokenRequest = value;
        }
        public void handleMessage(Message msg) {
            UrlRequestBean urlBean = urlBean_weak.get();
            if (urlBean.getContext() != null || isTokenRequest) {
                switch (msg.what) {
                    case 200:
                        // 获取网络成功
                        Bundle data = msg.getData();
                        String s = data.getString("value");

                        if (!urlBean.isCanel && s != null) {
                            try {
                                // 返回信息输出
                                Mylog.dLog(new DESEncrypt().decrypt(s));
                                JSONObject jsonObject = new JSONObject(new DESEncrypt().decrypt(s));
                                // 解析数据
                                urlBean.getBlocks().onGetData(jsonObject);
                            } catch (Exception e) {
                                e.printStackTrace();
                                urlBean.getBlocks().onError();
                            }
                        }
                        break;
                    default:
                        Mylog.dLog(msg.what+":handler.default");
                        if (!urlBean.isCanel) {
                            urlBean.getBlocks().onError();
                        }
                        break;
                }
            }
            // 移除已完成的访问。
            UrlRequestManager requestManager = UrlRequestManager.Instance;
            requestManager.removeRequest(urlBean);
        }
    }

    /**
     * 异步线程
     */
    private class HttpPostThread implements Runnable {
        /**
         * handler处理
         */
        final private Handler handler;
        final private UrlRequestBean urlBean;

        public HttpPostThread(Handler handler, UrlRequestBean urlBean) {
            this.handler = handler;
            this.urlBean = urlBean;
        }

        @Override
        public void run() {
            // 获取我们回调主ui的message
            String url = urlBean.getUrl();
            try {
                HttpPost httpRequest = new HttpPost(url);

                List<BasicNameValuePair> postValue = urlBean.getPostValue();
                //得到token信息 放入请求列表中
                List<BasicNameValuePair> tokenlist=ApiUtils.getList(null);
                for(int i=0;i<tokenlist.size();i++){
                    postValue.add(new BasicNameValuePair(tokenlist.get(i).getName(),tokenlist.get(i).getValue()));
                }
                httpRequest.setEntity(new UrlEncodedFormEntity(postValue, HTTP.UTF_8));
                HttpClient client = new DefaultHttpClient();
                // 请求超时
                client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
                // 读取超时
                client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
                HttpResponse response = client.execute(httpRequest);
                int resultCode = response.getStatusLine().getStatusCode();
                if (resultCode == 200) {
                    // 获得数据
                    String str = EntityUtils.toString(response.getEntity());
                    Message msg = new Message();
                    msg.what = resultCode;
                    Bundle data = new Bundle();
                    data.putString("value", str);
                    msg.setData(data);
                    handler.sendMessage(msg);
                } else {
                    // 错误
                    Message msg = new Message();
                    msg.what = resultCode;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                // 超时 or数据解析出错
                e.printStackTrace();
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }
        }
    }
    /**
     * 执行完的任务出队
     * @param bean UrlRequestBean
     */
    private void removeRequest(UrlRequestBean bean) {
        requestData.remove(bean);
    }
}
