package com.example.apple.sometestdemo.DataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 16/6/27.
 */
public class MySqliteHandle extends SQLiteOpenHelper {

    /**
     * 数据库版本
     */
    private static int version ;

    /**
     * 数据库名
     */
    private static String dbName = "androidDemoDb";
    public MySqliteHandle(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        MySqliteHandle.version = version;
    }

    public MySqliteHandle(Context context , String name){
        this(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table person(id int identity(1,1) primary key , name varchar(20) , age varchar(20))");
        db.execSQL("CREATE table testTb(id int identity(1,1) primary key , score int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 获取数据库名
     * @return
     */
    public static String getDbName(){
        return MySqliteHandle.dbName;
    }
}
