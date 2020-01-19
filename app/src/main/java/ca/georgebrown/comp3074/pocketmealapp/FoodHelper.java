package ca.georgebrown.comp3074.pocketmealapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FoodHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Food_table.db";
    public static final int DATABASE_VERSION = 1;
    public FoodHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FoodContract.FoodEntity.SQL_CREATE);
        Log.d("DB","DB Food. is successfully created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
