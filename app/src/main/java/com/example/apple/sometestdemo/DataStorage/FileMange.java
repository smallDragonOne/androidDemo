package com.example.apple.sometestdemo.DataStorage;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zj on 16/6/25.
 */
public class FileMange {


    private  static String fileDir = "someTeDir";
    /**
     *
     * @param activity
     * @param fileName     文件名
     * @param writeContent 写入内容
     * @param mode         写入模式
     */
    public static void writeFile(Activity activity,String fileName, String writeContent ,int mode){
        try{
            FileOutputStream stream = activity.openFileOutput(fileName, mode);
            byte[] bytes = writeContent.getBytes();
            stream.write(bytes);
            stream.close();
        }
        catch (Exception ee){
            ee.printStackTrace();
        }
    }


    /**
     * 读取文件数据
     * @param activity
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readFile(Context activity,String fileName){
        String readContent = "";
        try{
            FileInputStream stream = activity.openFileInput(fileName);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            //int length = stream.available();
            //byte[] bytes = new byte[length];
            byte[] bytes = new byte[1024];
            int len = 0 ;
            while ((len = stream.read(bytes)) != -1){
                byteStream.write(bytes,0,len);
            }
            //stream.read(bytes);

//            try{
//                readContent = new String(bytes,"utf-8");
//            }
//            catch (UnsupportedEncodingException ee){
//                ee.printStackTrace();
//            }

            readContent = byteStream.toString();
            byteStream.close();
            stream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  readContent;
    }


    /**
     * 获取默认文件路径
     * @return
     */
    public static String getFilePath(Context activity,String fileName, int mode){
        return activity.getDir(fileName ,mode).getAbsolutePath();
    }

    /**
     * 获取保存在sd文件路径
     * @param activity
     * @return
     */
    public static String getSdCardFilePath(Context activity){
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try{
                path = Environment.getExternalStorageDirectory().getCanonicalPath() + File.separator +fileDir ;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(activity , "sd卡没有插入或者当前为保护状态" , Toast.LENGTH_SHORT).show();
        }
        return path;

    }


    /**
     * sd卡写入文件
     * @param activity
     * @param fileName  文件名
     * @param content   写入文件内容
     * @param mode      写入文件模式
     */
    public static void saveToSdCard(Context activity,String fileName , String content,boolean mode) {
        String str = Environment.getExternalStorageState();
        if (str.equals(Environment.MEDIA_MOUNTED)){
            try{
                /// 创建文件夹
                File files = new File(Environment.getExternalStorageDirectory().getCanonicalPath() + File.separator + fileDir);
                if (!files.exists()){
                    files.mkdirs();
                }
                File file = new File(Environment.getExternalStorageDirectory().getCanonicalPath() + File.separator + fileDir + File.separator + fileName);
                //File file = new File(Environment.getExternalStorageDirectory(),fileName);
                FileOutputStream stream = new FileOutputStream(file,false);
                stream.write(content.getBytes());
                stream.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(activity,"SD卡不存在或者为保护状态",Toast.LENGTH_SHORT).show();
        }

    }


    /**
     *
     * @param activity
     * @param fileName  文件名
     * @return
     */
    public static String getSdCardFile(Context activity,String fileName){
        String readContent = "";
        String str = Environment.getExternalStorageState();
        if (str.equals(Environment.MEDIA_MOUNTED)){
            try{
                FileInputStream stream = new FileInputStream(Environment.getExternalStorageDirectory().getCanonicalPath() + File.separator + fileDir + File.separator + fileName);
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len = 0 ;
                while((len = stream.read(bytes)) != -1){
                    byteStream.write(bytes,0,len);
                }
                readContent = byteStream.toString();
                byteStream.close();
                stream.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(activity,"SD卡不存在或为保护状态",Toast.LENGTH_LONG).show();
        }
        return readContent;
    }

    /**
     * 修改保存的文件目录
     * @param fileDir
     */
    public static void getFileDir(String fileDir){
        FileMange.fileDir = fileDir;
    }

}
