package com.example.apple.sometestdemo.ActivityManage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.DataStorage.MySqliteHandle;
import com.example.apple.sometestdemo.View.NaveBarManger;

/**
 * Created by zj on 16/6/27.
 * 数据库存储
 */
public class SqliteActivity extends BaseActivity implements View.OnClickListener {

    private EditText Ed_user;
    private EditText Ed_age;
    private Button  Bt_entry;
    private Button  Bt_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_layout);
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null).setTitle("sqlite存储");
        init();
    }


    private void init(){
        initWedget();
        initEvent();
        initData();
    }

    private void initWedget(){
        Ed_user = (EditText)findViewById(R.id.user_edit);
        Ed_age = (EditText)findViewById(R.id.age_edit);
        Bt_entry = (Button)findViewById(R.id.entry_btn);
        Bt_out = (Button)findViewById(R.id.out_btn);
    }

    private void initEvent(){
        Bt_entry.setOnClickListener(this);
        Bt_out.setOnClickListener(this);
    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {

        MySqliteHandle dataBaseHandle = new MySqliteHandle(this,"androidDemo.db",null,1);

        switch (v.getId()){

            case R.id.entry_btn:
                SQLiteDatabase db = dataBaseHandle.getWritableDatabase();
                db.execSQL("insert into person(name ,age) values ('"+Ed_user.getText().toString().trim()+"','"+Ed_age.getText().toString().trim()+"')");
                db.close();
                break;
            case R.id.out_btn:
                db = dataBaseHandle.getReadableDatabase();
                String users = "";
                String age = "";
                Cursor cursor = db.rawQuery("select * from person order by id desc limit 1",null);
                while (cursor.moveToNext()){
                    int index = cursor.getColumnIndex("id");
                    users = cursor.getString(cursor.getColumnIndex("name"));
                    age = cursor.getString(cursor.getColumnIndex("age"));
                }
                db.close();
                Toast.makeText(this,"姓名: "+users+" , 年龄: "+age+"" ,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}
