package com.example.apple.sometestdemo.ActivityManage;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import java.lang.Override;


/**
 *  by wj on 2015/8/6.
 *  退出所有activity
 */
public class ExitActivity extends BaseActivity {
    private  static boolean isExit=false;
    public static void actionStart(Context context){
        ExitActivity.isExit = true;

        Intent intent = new Intent(context,ExitActivity.class);
        // 清除该进程空间的所有Activity。
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ExitActivity.isExit){
            ExitActivity.isExit = false;
            //MyApplication.setIsopen(false);
            finish();
            //System.exit(0);会导致关闭服务
        }else{
            ///MyApplication.setIsopen(true);
            Intent intent = new Intent(this, LaunchActivity.class);
            // 引导页
            //overridePendingTransition(R.animator.guide_page, 0);
            startActivity(intent);

        }

    }

 /*   @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
            System.exit(0);
        }
    }*/
}
