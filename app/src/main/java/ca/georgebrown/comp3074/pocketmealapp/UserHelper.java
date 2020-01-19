package ca.georgebrown.comp3074.pocketmealapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User_table.db";
    public static final int DATABASE_VERSION = 1;
    public UserHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.UserEntity.SQL_CREATE);
        Log.d("DB","DB User is successfully created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
