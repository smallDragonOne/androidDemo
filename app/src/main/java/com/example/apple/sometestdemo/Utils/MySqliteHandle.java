package com.example.apple.sometestdemo.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 16/6/27.
 */
public class MySqliteHandle extends SQLiteOpenHelper {

    public MySqliteHandle(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table person(id int identity(1,1) primary key , name varchar(20) , age varchar(20))");
        db.execSQL("CREATE table testTb(id int identity(1,1) primary key , score int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
