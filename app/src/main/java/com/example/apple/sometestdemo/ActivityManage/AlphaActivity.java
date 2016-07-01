package com.example.apple.sometestdemo.ActivityManage;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.View.NaveBarManger;

/**
 * Created by apple on 16/6/29.
 * alpha动画
 */
public class AlphaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView Im_anim;
    private Button Bt_anim;
    private Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alpha_layout);
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("alpha动画");
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Im_anim.setAnimation(animation);

    }

    /********************初始化******************/
    private void init(){
        initWedget();
        initEvent();
    }


    private void initWedget(){
        Im_anim = (ImageView)findViewById(R.id.panda_anim);
        Bt_anim = (Button)findViewById(R.id.anim_btn);

        animation = AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
    }

    private void initEvent(){
        Im_anim.setOnClickListener(this);
        Bt_anim.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anim_btn:
                Im_anim.startAnimation(animation);
                break;
        }
    }
}
