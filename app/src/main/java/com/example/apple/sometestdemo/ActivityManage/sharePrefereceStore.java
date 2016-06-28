package com.example.apple.sometestdemo.ActivityManage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.sometestdemo.DataStorage.SharePrefeceHandle;
import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.Utils.Mylog;
import com.example.apple.sometestdemo.View.NaveBarManger;

import java.util.HashMap;
import java.util.Map;

/**
 * 持久化存储
 * Created by zj on 16/6/24.
 */
public class SharePrefereceStore extends BaseActivity implements View.OnClickListener {

    private EditText editText;
    private TextView tv_outnum;
    private Button ImBtn;
    private Button outBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_prefre_layoyt);

        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("sharePrefereces");
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /*************************初始化*****************/

    private void init(){
        initWedget();
        initData();
        initEvent();
    }


    /// 初始化控件
    private void initWedget(){
        editText = (EditText)findViewById(R.id.enter_num);
        tv_outnum = (TextView)findViewById(R.id.out_num);
        ImBtn = (Button)findViewById(R.id.goBtn);
        outBtn = (Button)findViewById(R.id.outBtn);
    }

    private void initEvent(){
        ImBtn.setOnClickListener(this);
        outBtn.setOnClickListener(this);
    }

    /// 初始化数据
    private void initData(){

    }


    /***********************实现接口********************/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goBtn:
                HashMap<String,String> map = new HashMap<>();
                map.put("storeIn",editText.getText().toString().trim());
                SharePrefeceHandle.SetSharePrefeceHandle(this, "sharePreferece", map);
                break;
            case R.id.outBtn:
                SharedPreferences preferences = SharePrefeceHandle.getPreferences(this,"sharePreferece");
                tv_outnum.setText(preferences.getString("storeIn","null"));
                break;
            default:
                break;
        }
    }
}

