package com.example.apple.sometestdemo.BroadcastManage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.apple.sometestdemo.ActivityManage.BaseActivity;
import com.example.apple.sometestdemo.InterfaceManage.NetworkChange;
import com.example.apple.sometestdemo.NetworkManage.NetworkStateManager;
import com.example.apple.sometestdemo.Utils.Mylog;

/**
 * Created by zj on 16/7/1.
 * 网络广播
 */
public class NetworkBroadcast extends BroadcastReceiver {

    private NetworkChange change = BaseActivity.networkChange;

    @Override
    public void onReceive(Context context, Intent intent) {
        Mylog.dLog("接收到了");
        if (NetworkStateManager.instance().isNetworkConnected()){

        }
    }


}
