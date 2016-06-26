package com.example.apple.sometestdemo.View;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.sometestdemo.R;

/**
 * Created by apple on 16/6/23.
 * 高度封装的导航管理控制
 */
public class NaveBarManger {

    private Activity activity;
    private View view;
    private RelativeLayout leftImg;
    private clickEventClass clickEvent;
    private Button rightBtn;

    private TextView title;
    private Button backBtn;
    private navType type;
    public enum navType{
         normal,
         minUse,
         backonly
    }

    /**
     *
     * @param activity  活动
     * @param view
     * @param type
     * @param event
     */
    public NaveBarManger(Activity activity,View view ,navType type , clickEventClass event){
        this.activity = activity;
        this.view = view;
        this.clickEvent = event;
        this.type = type;
        dealNavType();
    }

    /**
     *
     * 初始化控件
     */
    private void dealNavType(){
         rightBtn = (Button)activity.findViewById(R.id.right_btn);
         title = (TextView)activity.findViewById(R.id.nav_title);
        backBtn = (Button)activity.findViewById(R.id.backBtn);
        leftImg = (RelativeLayout)activity.findViewById(R.id.back_image);
        setSwichType();
    }

    /**
     *
     * @param event 添加事件,修改导航的一个事件，其他事件也必须重复写，否则会调用抽象类里的方法
     */
    @Deprecated
    public void setClikEvent(clickEventClass event){
        this.clickEvent = event;
        setSwichType();
    }


    /**
     *  根据类型设置相关事件
     */
    private void setSwichType(){
        switch (type){
            case normal:
                break;
            case minUse:
                break;
            case backonly:
                leftImg.setVisibility(View.VISIBLE);
                leftImg.setOnClickListener(setBackToActivity());
            default:
                break;
        }
    }
    /**
     * 捕获外部访问
     */
    public static abstract class clickEventClass{

        public View.OnClickListener rightBtnEvent(){return null;}

        public View.OnClickListener leftBtnEvent(){return null;}
    }


    /**
     *
     * @return 常用返回事件（不需要其他设置仅返回）
     */
    public View.OnClickListener setBackToActivity(){

        if (clickEvent != null && clickEvent.leftBtnEvent() != null){
            return clickEvent.leftBtnEvent();
        }
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NaveBarManger.this.activity.finish();
            }
        };

    }



    /**
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }

}
