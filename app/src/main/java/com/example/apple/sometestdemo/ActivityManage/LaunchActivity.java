package com.example.apple.sometestdemo.ActivityManage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.apple.sometestdemo.R;

/**
 * Created by zj on 16/6/23.
 * 启动页面
 */
public class LaunchActivity extends BaseActivity {

    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(this , R.layout.startmin_layout ,null);

        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /// 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1f);
        aa.setDuration(1000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void redirectTo(){
        Intent intent =  new Intent(this , MainActivity.class);
        startActivity(intent);
        finish();
    }
}
