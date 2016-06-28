package com.example.apple.sometestdemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

//import com.example.hikvision.R;
//import com.example.hikvision.db.DButil;
//import com.example.hikvision.manager.MyApplication;
//import com.example.hikvision.ui.RoundImageView;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.assist.FailReason;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//
//import org.androidpn.client.NotificationService;
//import org.androidpn.client.ServiceManager;
//import org.androidpn.client.XmppManager;
//import org.androidpn.client.api.ApiUtils;
//import org.androidpn.client.api.DESEncrypt;
//import org.androidpn.client.api.HttpRequest;
//import org.androidpn.client.new_add.BindUserIQ;
//import org.androidpn.client.new_add.LogManager;
//import org.jivesoftware.smack.packet.IQ;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 一些求值，计算等方法
 * Created by wj on 2015/7/31.
 */
public class SomeUtils {

    /**
     * 得到屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    /**
     * 有缺省图
     * @param iv  ImageView
     * @param imageUrl imageUrl
     */
//    public static void setImageLoader(final ImageView iv, String imageUrl) {
//        DisplayImageOptions  options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .showImageOnLoading(R.drawable.default_img)
//                .showImageForEmptyUri(R.drawable.default_img)
//                .showImageOnFail(R.drawable.default_img)
//                .resetViewBeforeLoading(false)  // default
//                .build();
//        ImageLoader.getInstance().displayImage(imageUrl, iv, options);
//    }

//    /**
//     * 无缺省图
//     * @param iv ImageView
//     * @param imageUrl imageUrl
//     */
//    public static void setImageLoader2(final ImageView iv, String imageUrl) {
//        DisplayImageOptions  options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();
//        // loadImage会对相同URI的请求进行过滤，而displayImage不会
//        // 未避免首页的轮播图片无法正常显示，这里用displayImage
//        ImageLoader.getInstance().displayImage(imageUrl, iv, options);
//    }
//    //加载头像
//    public static void setImageLoader3(final RoundImageView iv, String imageUrl) {
//        DisplayImageOptions  options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .showImageOnLoading(R.drawable.tou)
//                .showImageForEmptyUri(R.drawable.tou)
//                .showImageOnFail(R.drawable.tou)
//                .resetViewBeforeLoading(false)  // default
//                .build();
//        ImageLoader.getInstance().displayImage(imageUrl, iv, options);
//    }
//    //带后期处理的
//    public static void setImageLoader4(final ImageView iv, String imageUrl,ImageLoadingListener listener) {
//        DisplayImageOptions  options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .showImageOnLoading(R.drawable.tou)
//                .showImageForEmptyUri(R.drawable.tou)
//                .showImageOnFail(R.drawable.tou)
//                .resetViewBeforeLoading(false)  // default
//                .build();
//        ImageLoader.getInstance().displayImage(imageUrl, iv, options, listener);
//    }
//
    /**
     * 动态计算listiview高度 解决和scrollview冲突
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        MyAdapter listAdapter= (MyAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 绑定用户名
     */
    /*
    public static void sendBindUserIQ(final String loginName,final String loginTime){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 等待NotificationService,XmppManager加载完成
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NotificationService notificationService = NotificationService.getNotificationService();
                if (notificationService != null) {
                    final XmppManager xmppManager = notificationService.getXmppManager();
                    if (xmppManager != null) {
                        // 通过接口绑定用户名
                        LogManager.d("SomeUtils", "sendBindUserAPI");
                        String username = xmppManager.getUsername();
                        Map<String, String> parameters = ApiUtils.getMap(null);
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("{");
                        stringBuffer.append("\"username\":\""+username+"\"");
                        stringBuffer.append(",\"loginName\":\""+loginName+"\"");
                        stringBuffer.append(",\"loginTime\":\"" + loginTime + "\"");
                        stringBuffer.append(",\"appid\":\"1\"");
                        stringBuffer.append("}");
                        DESEncrypt des =new DESEncrypt();
                        String str = stringBuffer.toString();

                        try {
                            str = des.encrypt(str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        parameters.put("content", str);

                        String result = HttpRequest.sendPost("http://"+xmppManager.getXmppHost() +":"+xmppManager.getWebPort()+"/bindloginname.api", parameters);
                        try {
                            result =des.decrypt(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LogManager.d("SomeUtils.sendBindUserAPI", result);

                        // 通过IQ绑定用户名
                        LogManager.d("SomeUtils", "sendBindUserIQ");
                        if (!xmppManager.isAuthenticated()) {
                            try {
                                synchronized (xmppManager) {
                                    LogManager.d("SomeUtils", "waiting for Authenticated..");
                                    xmppManager.wait();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        LogManager.d("SomeUtils", "Authenticated already,send BindUserIQ now..");
                        BindUserIQ bindUserIQ = new BindUserIQ();
                        bindUserIQ.setType(IQ.Type.SET);
                        bindUserIQ.setLoginName(loginName);
                        bindUserIQ.setLoginTime(loginTime);
                        bindUserIQ.setAppid("1");
                        xmppManager.getConnection().sendPacket(bindUserIQ);
                    }
                }
            }
        }).start();
    }
*/
    /**
     * 消息初次被查看时告知服务器
     * @param msgid 消息ID
     */
   /* public static void messageOnclick(final String msgid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 等待NotificationService,XmppManager加载完成
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NotificationService notificationService = NotificationService.getNotificationService();
                if (notificationService != null) {
                    final XmppManager xmppManager = notificationService.getXmppManager();
                    if (xmppManager != null) {
                        // 通过接口绑定用户名
                        LogManager.d("SomeUtils", "messageOnclick");
                        Map<String, String> parameters = ApiUtils.getMap(null);
                        DESEncrypt des = new DESEncrypt();
                        StringBuilder strs = new StringBuilder();
                        strs.append("{");
                        strs.append("\"msgid\":\""+msgid+"\"");
                        strs.append(",\"appid\":\""+xmppManager.appid+"\"");
                        strs.append(",\"phonetype\":\"android\"");
                        strs.append(",\"loginName\":\""+ DButil.getValue(MyApplication.getTopActivity(),"user_name")+"\"");
                        strs.append("}");
                        String str = strs.toString();
                        try {
                            str = des.encrypt(str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        parameters.put("content", str);
                        String result = HttpRequest.sendPost("http://" + xmppManager.getXmppHost() +":"+xmppManager.getWebPort() + "/msgclick.api", parameters);
                        try {
                            result = des.decrypt(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LogManager.d("SomeUtils.messageOnclick", result);
                    }
                }
            }
        }).start();
    }
*/
    /**
     * 让所有数字保留 两个小数点
     * @param num 数字
     * @return
     */
    public static String numberformat(String num){
        DecimalFormat df = new DecimalFormat("0.00");//小数点后面保留两位
        df.setRoundingMode(RoundingMode.HALF_UP);//进位
        return df.format(Double.valueOf(num));
    }
}
