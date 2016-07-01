package com.example.apple.sometestdemo.ActivityManage;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.apple.sometestdemo.Bean.UrlRequestBeans;
import com.example.apple.sometestdemo.NetworkManage.OkHttpHandles;
import com.example.apple.sometestdemo.NetworkManage.OkhttpRequestManage;
import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.Utils.Mylog;
import com.example.apple.sometestdemo.View.NaveBarManger;

/**
 * Created by apple on 16/6/30.
 */
public class GetRequestActivity extends BaseActivity implements View.OnClickListener{

    private EditText Ed_url;
    private TextView Tv_load1;
    private Button Bt_request;
    private ProgressBar progressBar;
    String url = "http://www.weather.com.cn/data/cityinfo/101010100.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getnetwork_layout);
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("Get请求");
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ///requestNet();
        /// "http://www.weather.com.cn/data/cityinfo/101010100.html"
        //new RequestNetWorkManage().getHttpRequest("http://www.weather.com.cn/data/cityinfo/101010100.html");

    }




    /**********************初始化**********************/
    private void init(){
        initWedget();
        initEvent();
    }


    private void initWedget(){
        Ed_url = (EditText)findViewById(R.id.url_edit);
        Tv_load1 = (TextView)findViewById(R.id.tv_load);
        Bt_request = (Button)findViewById(R.id.request_btn);
        progressBar = (ProgressBar)findViewById(R.id.progress);
    }

    private void initEvent(){
        Bt_request.setOnClickListener(this);
    }



    /*********************网络请求*********************/

    /**
     * 测试
     */
    @Deprecated
    private void requestNet(){
        OkHttpHandles.getHttpRequest(url, this, new OkHttpHandles.CallBackOptions() {
            @Override
            public void requestBegin() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void requesrFailure() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void reposeData(String JsonData) {
                progressBar.setVisibility(View.GONE);
                Tv_load1.setText(JsonData);
            }
        });

    }

    private void requestNetOffice(){
        OkhttpRequestManage manage = OkhttpRequestManage.instance;
        Mylog.dLog("进入了");
        UrlRequestBeans urlBeans = new UrlRequestBeans(this, url, new UrlRequestBeans.Options() {
            @Override
            public void beforeRequest() {
                Mylog.dLog("进入");
            }

            @Override
            public void onError() {

            }

            @Override
            public void onGetData(String Json) {
                Tv_load1.setText(Json);
            }
        });
        manage.addRequest(urlBeans);
        manage.begin();
    }







    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.request_btn:
                url = Ed_url.getText().toString().trim();
                requestNetOffice();
                break;
            default:
                break;
        }
    }
}
