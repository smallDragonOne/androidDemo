package com.example.apple.sometestdemo.ActivityManage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apple.sometestdemo.R;
import com.example.apple.sometestdemo.Utils.FileMange;
import com.example.apple.sometestdemo.View.NaveBarManger;

import java.io.FileInputStream;

/**
 * Created by zj on 16/6/24.
 * 文件存储
 */
public class FileStoreActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_filepath;
    private TextView tv_visible;
    private EditText editText;
    private TextView tv_input;
    private Button  writeBtn;
    private Button  readBtn;
    private Button  sdWirteBtn;
    private Button  sdReadBtn;
    private TextView tv_sdPathBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filestore_layout);
        new NaveBarManger(this,null, NaveBarManger.navType.backonly,null);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    /*************************初始化*********************/

    private void init(){
        initWedget();
        initEvent();
    }

    private void initWedget(){
        tv_filepath = (TextView)findViewById(R.id.pathread);
        tv_visible = (TextView)findViewById(R.id.tv_read);
        editText = (EditText)findViewById(R.id.output_btn);
        tv_input = (TextView)findViewById(R.id.input_btn);
        writeBtn = (Button)findViewById(R.id.goBtn);
        readBtn = (Button)findViewById(R.id.outBtn);
        sdWirteBtn = (Button)findViewById(R.id.sd_btn_write);
        sdReadBtn = (Button)findViewById(R.id.sd_btn_read);
        tv_sdPathBtn = (TextView)findViewById(R.id.sd_path_btn);
    }

    /**
     * 初始化事件
     */
    private void initEvent(){
        tv_filepath.setOnClickListener(this);
        writeBtn.setOnClickListener(this);
        readBtn.setOnClickListener(this);
        sdWirteBtn.setOnClickListener(this);
        sdReadBtn.setOnClickListener(this);
        tv_sdPathBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goBtn:
                try{
                    FileMange.writeFile(this,"fileName",editText.getText().toString().trim(),MODE_PRIVATE);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.outBtn:
                try{
                    tv_input.setText(FileMange.readFile(this, "fileName"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.pathread:
                tv_visible.setText(FileMange.getFilePath(this,"fileName",MODE_PRIVATE));
                break;
            case R.id.sd_btn_write:
                FileMange.saveToSdCard(this,"sdFile.txt",editText.getText().toString().trim(),false);
                break;
            case R.id.sd_btn_read:
                tv_input.setText(FileMange.getSdCardFile(this, "sdFile.txt"));
                break;
            case R.id.sd_path_btn:
                tv_visible.setText(FileMange.getSdCardFilePath(this));
                break;
            default:
                break;
        }
    }
}
