package com.example.apple.sometestdemo.ActivityManage;

import android.os.Bundle;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.View.MyCustomView;
import com.example.apple.sometestdemo.View.NaveBarManger;

/**
 * Created by apple on 16/6/27.
 * 自定义view
 */
public class CustomViewActivity extends BaseActivity{

    private MyCustomView customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_layout);
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("自定义View");
        init();
    }

    private void init(){
        initWedget();
    }


    private void initWedget(){
        customView = (MyCustomView)findViewById(R.id.custom_view);
    }

}
