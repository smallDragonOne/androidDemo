package com.example.apple.sometestdemo.DataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作.
 */
public class DButil {
    /**
     * 该方法是查询一个列名，返回一个列的值
     * @param context 上下文，直接把调用方的this传入即可
     * @param valueName 列名，要查询的列名 如app_secret数据
     * @return  返回值为一个字符串，列名对应的值,如果返回值为null，有可能为没有此列，也有可能为空
     */
    public static synchronized String getValue(Context context,String valueName) {
        String result = null;
        Cursor c;
        MySqliteHandle db = new MySqliteHandle(context,MySqliteHandle.getDbName());
        SQLiteDatabase dbRead = db.getReadableDatabase();
        c = dbRead.query("user",new String[]{valueName},null,null,null,null,null);
        while (c.moveToNext()) {
            result= c.getString(c.getColumnIndex(valueName));
        }
        c.close();
        dbRead.close();
        db.close();
        return result;
    }

    /**
     *  该方法是更新一条 app_secret数据
     * 或者是一条access_token数据
     * @param context 上下文，直接把调用方的this传入即可
     * @param key   列名
     * @param value 新值
     * @return 。
     */
    public boolean updateValue(Context context,String key,String value) {
        MySqliteHandle db = new MySqliteHandle(context, MySqliteHandle.getDbName());
        SQLiteDatabase dbWrite = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(key,value);
        dbWrite.update("user",cv,"_id=?",new String[]{"1"});
        dbWrite.close();
        db.close();
        return true;
    }
}
