package com.example.apple.sometestdemo.ActivityManage;

import android.app.Activity;
import android.os.Bundle;

import com.example.apple.sometestdemo.Myaplication;

/**
 * 公共基类activity
 * Created by zj on 16/6/23.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Myaplication.setTopActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Myaplication.setTopActivity(this);
    }
}
