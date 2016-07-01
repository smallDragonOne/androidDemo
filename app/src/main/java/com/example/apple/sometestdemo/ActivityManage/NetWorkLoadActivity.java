package com.example.apple.sometestdemo.ActivityManage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.View.NaveBarManger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by apple on 16/6/30.
 * 网络加载
 */
public class NetWorkLoadActivity extends BaseActivity {

    private String[] strArr = {"Get请求" ,"Post请求"};
    private List<String> arrStr = new ArrayList<>();
    private ArrayAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_load_layout);
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("网络加载");
        init();
    }


    /***********************初始化***********************/

    private void init(){
        initWedget();
        initData();
        createAdapter();
        initEvent();
    }


    private void initWedget(){
        listView = (ListView)findViewById(R.id.list);
    }

    private void initData(){
        Collections.addAll(arrStr,strArr);
    }

    private void createAdapter(){
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.addAll(arrStr);
    }


    private void initEvent(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        intent = new Intent(NetWorkLoadActivity.this, GetRequestActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(NetWorkLoadActivity.this, PostRequestActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

}
