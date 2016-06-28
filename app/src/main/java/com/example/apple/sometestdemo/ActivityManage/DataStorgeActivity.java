package com.example.apple.sometestdemo.ActivityManage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.View.NaveBarManger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by zj on 16/6/24.
 * 数据存储
 */
public class DataStorgeActivity extends BaseActivity {

    private ListView listView;

    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datastorage_layout);
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("数据存储");
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    /*****************************初始化*******************/
    private void init(){
        initWedget();
        initAdapter();
        initData();
        initEvent();
    }

    /**
     * 初始化控件
     */
    private void initWedget(){
        listView = (ListView)findViewById(R.id.list);

    }

    private void initData(){
        List<String> arrStr = new ArrayList<>();
        Collections.addAll(arrStr,getResources().getStringArray(R.array.dataSorge));
        adapter.addAll(arrStr);
    }

    private void initAdapter(){
        adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
    }

    private void initEvent(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        intent = new Intent(DataStorgeActivity.this , SharePrefereceStore.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(DataStorgeActivity.this , FileStoreActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent = new Intent(DataStorgeActivity.this , SqliteActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
