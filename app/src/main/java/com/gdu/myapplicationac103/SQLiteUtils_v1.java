package com.gdu.myapplicationac103;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Czm on 2021/5/27 14:09.
 */
public class SQLiteUtils_v1 {

    DatabaseHelper dbHelp;
    SQLiteDatabase db;


    public SQLiteUtils_v1(Context context) {
        init(context);
    }

    private void init(Context context) {
        dbHelp = new DatabaseHelper(context, "twisted_db", null, 1);
        db = dbHelp.getWritableDatabase();
    }

    public void insert(String _dates, String _times, double _mileage, double _heat) {
        SQLiteDatabase writableDatabase = dbHelp.getWritableDatabase();// 获取数据库对象
        String sql = "insert into user1 (_dates,_times,_mileage,_heat) values(?,?,?,?)";
        Object[] bindArgs = new Object[]{_dates, _times, _mileage, _heat};
        writableDatabase.execSQL(sql, bindArgs);
        writableDatabase.close();
    }

    public void delete(int id) {
        // 获取数据库对象
        SQLiteDatabase writableDatabase = dbHelp.getWritableDatabase();
        String sql = "delete from user1 where _id=?;";
        Object[] bindagrs = new Object[]{id};
        writableDatabase.execSQL(sql, bindagrs);
        //关闭数据库
        writableDatabase.close();
    }

    public List<String> query() {
        SQLiteDatabase writableDatabase = dbHelp.getReadableDatabase();// 获取数据库对象
        String sql = "select * from user1;";
        Cursor rawQuery = writableDatabase.rawQuery(sql, null);
        List<String> users = new ArrayList<>();
        while (rawQuery.moveToNext()) {
rawQuery.getString(Integer.parseInt("name")).toString();
        }
        rawQuery.close();
        writableDatabase.close();
        return users;
    }

    class DatabaseHelper extends SQLiteOpenHelper {
        String[] tables = new String[]{
                "create table if not exists user1(" +
                        "_id integer primary key autoincrement," +
                        "_dates text not null," +
                        "_times text not null," +
                        "_mileage float not null," +
                        "_heat float not null)",

                "create table if not exists user2(" +
                        "_id integer primary key autoincrement," +
                        "_dates text not null," +
                        "_times text not null," +
                        "_mileage float not null," +
                        "_heat float not null)"
        };

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            //此处建表
            for (String sql : tables) {
                db.execSQL(sql);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
