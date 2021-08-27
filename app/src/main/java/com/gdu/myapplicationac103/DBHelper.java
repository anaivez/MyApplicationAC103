package com.gdu.myapplicationac103;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description:
 * Created by Czm on 2021/8/26 16:04.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 单位说明
         * time/s
         * calorie
         * mileage/m
         * avg_velocity/m
         */
        String sql = "create table user(" +
                "ID integer primary key autoincrement," +
                "NAME varchar(20)," +
                "TIME integer," +
                "CALORIE integer," +
                "MILEAGE integer," +
                "AVG_VELOCITY integer,"+
                "CREATE_DATE interger)";
        //执行sql语句
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
