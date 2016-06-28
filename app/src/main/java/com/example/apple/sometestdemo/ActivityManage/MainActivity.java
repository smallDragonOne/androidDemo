package com.example.apple.sometestdemo.ActivityManage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.Utils.MyAdapter;
import com.example.apple.sometestdemo.Utils.ViewHolder;
import com.example.apple.sometestdemo.View.NaveBarManger;

import java.util.ArrayList;
import java.util.List;
import com.example.apple.sometestdemo.Bean.mainBean;

/**
 * 主页
 * Created by zj on 16/6/23.
 */
public class MainActivity extends BaseActivity {

    private List<String> listData = new ArrayList<>();
    private int resource = R.array.main_array;
    private List<mainBean> datas = new ArrayList<>();
    private MyAdapter<mainBean> adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        new NaveBarManger(this, null, NaveBarManger.navType.normal,null);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /******************************初始化************************/
    /**
     * 初始化
     */
    private void init(){
        initWedget();
        initData();
        CreateAdapter();
    }

    /**
     * 初始化控件
     */
    private void initWedget(){
        listView = (ListView)findViewById(R.id.list);

    }

    /**
     * 适配器初始化
     */
    private void CreateAdapter(){
        adapter = new MyAdapter<mainBean>(this,datas,R.layout.main_item) {
            @Override
            protected void convertToView(ViewHolder holder, mainBean bean) {
                holder.setText(R.id.titles ,bean.getTitle()).setImage(R.id.image,bean.getImage());
                switch (holder.getMposition()){
                    case 0:
                        holder.setClick(R.id.list_item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this , DataStorgeActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 1:
                        holder.setClick(R.id.list_item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this , CustomControl.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    default:
                        break;

                }
            }
        };
        listView.setAdapter(adapter);
    }


    private void initData(){
        String[] arr = getResources().getStringArray(resource);
        for (int i = 0 ,len = arr.length; i < len ; i ++ ){
            mainBean mainBean = new mainBean(R.mipmap.main_con, arr[i]);
            datas.add(mainBean);
        }
    }
}
