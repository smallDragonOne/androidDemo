package com.example.apple.sometestdemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Xml;

import com.example.apple.sometestdemo.Myaplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by apple on 16/6/25.
 */
public class FileMange {

    /**
     * 写入文件内容
     * @param activity
     * @param fileName     文件名
     * @param writeContent 写入内容
     * @throws IOException
     */
    public static void writeFile(Activity activity,String fileName, String writeContent) throws IOException{
        try{
            FileOutputStream stream = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
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
    public static String readFile(Activity activity,String fileName) throws IOException{
        String readContent = "";
        try{
            FileInputStream stream = activity.openFileInput(fileName);
            int length = stream.available();
            byte[] bytes = new byte[length];
            stream.read(bytes);

            try{
                readContent = new String(bytes,"utf-8");
            }
            catch (UnsupportedEncodingException ee){
                ee.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  readContent;
    }


    /**
     * 获取文件路径
     * @return
     */
    public static String getFilePath(Activity activity,String fileName, int mode){
        return activity.getDir(fileName ,mode).getAbsolutePath();
    }

}
