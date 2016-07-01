package com.example.apple.sometestdemo.ActivityManage;

import android.os.Bundle;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.View.NaveBarManger;

/**
 * Created by apple on 16/6/30.
 * post请求
 */
public class PostRequestActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_request_layout);
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("Post请求");
    }

}
