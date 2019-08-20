package com.example.cc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_THING = "create table Thing ("
            + "content text,"
            + "date text )";

    public ToDoDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
        Log.d("qazxcv", "成功了hhh");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_THING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Thing");
        onCreate(db);
    }
}
