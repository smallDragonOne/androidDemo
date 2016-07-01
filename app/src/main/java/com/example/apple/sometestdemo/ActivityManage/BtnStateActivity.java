package com.example.apple.sometestdemo.ActivityManage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.View.NaveBarManger;

/**
 * Created by apple on 16/6/28.
 *  按钮点击
 */
public class BtnStateActivity extends BaseActivity implements View.OnClickListener{

    private Button Bt_Click;
    private Button Bt_Coreners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btn_state_layout);

        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("点击按钮");
        init();
    }


    /************************初始化*************************/
    private void init(){
        initWedget();
        initEvent();
    }


    private void initWedget(){
        Bt_Click = (Button)findViewById(R.id.btn_click);
        Bt_Coreners = (Button)findViewById(R.id.btn_corners);
    }

    private void initEvent(){
        Bt_Click.setOnClickListener(this);
        Bt_Coreners.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_click:
                Toast.makeText(this,"点击了按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_corners:
                Toast.makeText(this,"点击了圆弧按钮",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
