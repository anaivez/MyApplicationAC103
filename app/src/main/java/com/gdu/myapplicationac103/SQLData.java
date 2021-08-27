package com.gdu.myapplicationac103;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Czm on 2021/8/26 16:02.
 */
public class SQLData {

    private static SQLData instance;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private SQLData(Context context) {
        dbHelper = new DBHelper(context, "yz.db", null, 3);
        db = dbHelper.getWritableDatabase();
    }

    public static synchronized SQLData getInstance(Context context) {
        if (instance == null) {
            instance = new SQLData(context);
        }
        return instance;
    }


    /**
     * @return id
     */
    public long install() {
        ContentValues values = new ContentValues();
        values.put("NAME", "twisted");
        values.put("TIME", "asd");
        values.put("CALORIE", 260);
        values.put("MILEAGE", 1061);
        values.put("AVG_VELOCITY", 1230);
        values.put("CREATE_DATE", System.currentTimeMillis());
        Log.d("GR", "install: "+System.currentTimeMillis());
        return db.insert("user", null, values);
    }

    public boolean del(int id) {
        return db.delete("user", "NAME in (?)", new String[]{"twisted"}) > 0;
    }

    public List<User> select(){
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        List<User> users=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id=cursor.getInt(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                Integer time = cursor.getInt(cursor.getColumnIndex("TIME"));
                Integer calorie = cursor.getInt(cursor.getColumnIndex("CALORIE"));
                Integer mileage = cursor.getInt(cursor.getColumnIndex("MILEAGE"));
                Integer avg = cursor.getInt(cursor.getColumnIndex("AVG_VELOCITY"));
                long create = cursor.getLong(cursor.getColumnIndex("CREATE_DATE"));
                User user=new User(id,name,time,calorie,mileage,avg,create);
                //Log.d("debug",
                //        String.format("name: %s, time: %d, calorie: %d, create:"+create, name, time, calorie));
                //Log.d("debug", "select: "+user.toString());
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }
}
