package com.example.apple.sometestdemo.ActivityManage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.View.NaveBarManger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by apple on 16/6/27.
 * 自定义控件
 */
public class CustomControl extends BaseActivity {

    private ListView listView;
    private ArrayAdapter adapter;
    private String[] arrStr = {"自定义View"};
    private List<String>  arrList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_ctl_layout);
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("自定义控件");
        init();
    }

    /*＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊初始化＊＊＊＊＊＊＊＊＊＊＊＊＊*/
    private void init(){
        initWedget();
        initData();
        createAdapter();
        setAdapterEvent();
    }


    private void initWedget(){
        listView = (ListView)findViewById(R.id.list);
    }

    private void createAdapter(){
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        adapter.addAll(arrList);
        listView.setAdapter(adapter);
    }

    private void initData(){
        Collections.addAll(arrList , arrStr);
    }


    private void setAdapterEvent(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(CustomControl.this,CustomViewActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
