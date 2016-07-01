package com.example.apple.sometestdemo.ActivityManage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.apple.sometestdemo.InterfaceManage.NetworkChange;
import com.example.apple.sometestdemo.Myaplication;
import com.example.apple.sometestdemo.Utils.Mylog;

/**
 * 公共基类activity
 * Created by zj on 16/6/23.
 */
public class BaseActivity extends Activity implements NetworkChange{

    public static NetworkChange networkChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Myaplication.setTopActivity(this);
        BaseActivity.networkChange = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Myaplication.setTopActivity(this);
    }

    @Override
    public void onNetChage() {
        Mylog.dLog("网络不可用");
        Toast.makeText(this,"当前网络不可用，请检查你的网络",Toast.LENGTH_SHORT).show();
    }
}
