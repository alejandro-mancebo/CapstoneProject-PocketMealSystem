package ca.georgebrown.comp3074.pocketmealapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {


    public static long addUser(UserHelper user,String email,String type,String fName,String lName,String password,String phone,String address,String province,String postalC){

        SQLiteDatabase db = user.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserContract.UserEntity.COLUMN_NAME_EMAIL,email);
        cv.put(UserContract.UserEntity.COLUMN_NAME_TYPE,type);
        cv.put(UserContract.UserEntity.COLUMN_NAME_FIRST_NAME,fName);
        cv.put(UserContract.UserEntity.COLUMN_NAME_LAST_NAME,lName);
        cv.put(UserContract.UserEntity.COLUMN_NAME_PASSWORD,password);
        cv.put(UserContract.UserEntity.COLUMN_NAME_PHONE,phone);
        cv.put(UserContract.UserEntity.COLUMN_NAME_ADDRESS,address);
        cv.put(UserContract.UserEntity.COLUMN_NAME_PROVINCE,province);
        cv.put(UserContract.UserEntity.COLUMN_NAME_POSTAL_CODE,postalC);



        return db.insert(UserContract.UserEntity.TABLE_NAME,null,cv);

    }

    public static long editUser(UserHelper user,String email,String type,String fName,String lName,String password,String phone,String address,String province,String postalC){

        SQLiteDatabase db = user.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserContract.UserEntity.COLUMN_NAME_EMAIL,email);
        cv.put(UserContract.UserEntity.COLUMN_NAME_TYPE,type);
        cv.put(UserContract.UserEntity.COLUMN_NAME_FIRST_NAME,fName);
        cv.put(UserContract.UserEntity.COLUMN_NAME_LAST_NAME,lName);
        cv.put(UserContract.UserEntity.COLUMN_NAME_PASSWORD,password);
        cv.put(UserContract.UserEntity.COLUMN_NAME_PHONE,phone);
        cv.put(UserContract.UserEntity.COLUMN_NAME_ADDRESS,address);
        cv.put(UserContract.UserEntity.COLUMN_NAME_PROVINCE,province);
        cv.put(UserContract.UserEntity.COLUMN_NAME_POSTAL_CODE,postalC);



        String whereClause = " email=? ";
        String[]whereArgs = {email};
        return db.update(UserContract.UserEntity.TABLE_NAME,cv,whereClause,whereArgs);

    }






    public static long deleteUser(UserHelper user, String email){

        SQLiteDatabase db = user.getWritableDatabase();
        String whereClause = " email=? ";
        String[]whereArgs = new String[]{email};

        return db.delete(UserContract.UserEntity.TABLE_NAME,whereClause,whereArgs);

    }







    public static Cursor getUserRow(UserHelper userHelper, String email){

        SQLiteDatabase db = userHelper.getReadableDatabase();

        String [] projection = {
                UserContract.UserEntity.COLUMN_NAME_EMAIL,
                UserContract.UserEntity.COLUMN_NAME_TYPE,
                UserContract.UserEntity.COLUMN_NAME_FIRST_NAME,
                UserContract.UserEntity.COLUMN_NAME_LAST_NAME,
                UserContract.UserEntity.COLUMN_NAME_PASSWORD,
                UserContract.UserEntity.COLUMN_NAME_PHONE,
                UserContract.UserEntity.COLUMN_NAME_ADDRESS,
                UserContract.UserEntity.COLUMN_NAME_PROVINCE,
                UserContract.UserEntity.COLUMN_NAME_POSTAL_CODE,
        };
        String selection = " email=? ";
        String[] selectionArgs = {email}; //or new String[]{routename}
        return db.query(
                UserContract.UserEntity.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );



    }




    public static Cursor getAllUsers(UserHelper userHelper){

        SQLiteDatabase db = userHelper.getReadableDatabase();

        String [] projection = {
                UserContract.UserEntity.COLUMN_NAME_EMAIL,
                UserContract.UserEntity.COLUMN_NAME_TYPE,
                UserContract.UserEntity.COLUMN_NAME_FIRST_NAME,
                UserContract.UserEntity.COLUMN_NAME_LAST_NAME,
                UserContract.UserEntity.COLUMN_NAME_PASSWORD,
                UserContract.UserEntity.COLUMN_NAME_PHONE,
                UserContract.UserEntity.COLUMN_NAME_ADDRESS,
                UserContract.UserEntity.COLUMN_NAME_PROVINCE,
                UserContract.UserEntity.COLUMN_NAME_POSTAL_CODE,
        };
        String selection = null;
        String[] selectionArgs = null; //or new String[]{routename}
        return db.query(
                UserContract.UserEntity.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );



    }

//------------------------------------------------------------------------------------------



    public static long addLocation(PointHelper pointHelper, String email, double lon, double lat){

        SQLiteDatabase db = pointHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PointContract.PointEntity.COLUMN_NAME_EMAIL,email);
        cv.put(PointContract.PointEntity.COLUMN_NAME_LONG,lon);
        cv.put(PointContract.PointEntity.COLUMN_NAME_LAT,lat);

        return db.insert(PointContract.PointEntity.TABLE_NAME,null,cv);

    }

    public static long updateLocation (PointHelper pointHelper,String email, double lon, double lat){

        SQLiteDatabase db = pointHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PointContract.PointEntity.COLUMN_NAME_EMAIL,email);
        cv.put(PointContract.PointEntity.COLUMN_NAME_LONG,lon);
        cv.put(PointContract.PointEntity.COLUMN_NAME_LAT,lat);


        String whereClause = " email=? ";
        String[]whereArgs = {email};
        return db.update(PointContract.PointEntity.TABLE_NAME,cv,whereClause,whereArgs);

    }

    public static Cursor getLocationRow(PointHelper pointHelper,String email){


        SQLiteDatabase db = pointHelper.getReadableDatabase();

        String [] projection = {
                PointContract.PointEntity._ID,
                PointContract.PointEntity.COLUMN_NAME_EMAIL,
                PointContract.PointEntity.COLUMN_NAME_LONG,
                PointContract.PointEntity.COLUMN_NAME_LAT,

        };
        String selection = " email=? ";
        String[] selectionArgs = {email};
        return db.query(
                PointContract.PointEntity.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );



    }

//can create function to return a list of all food near a location.
//---------------------------------------------------------------------------------------------------------------

    public static long addFood(FoodHelper foodHelper, String email, String foodname, String category,String expiryD,String ingredients){

        SQLiteDatabase db = foodHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_EMAIL,email);
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_FOOD_NAME,foodname);
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_CATEGORY,category);
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_EXPIRY_DATE,expiryD);
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_INGREDIENTS,ingredients);

        return db.insert(FoodContract.FoodEntity.TABLE_NAME,null,cv);

    }


    public static long updateFood(FoodHelper foodHelper, String email, String foodname, String category,String expiryD,String ingredients){

        SQLiteDatabase db = foodHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_EMAIL,email);
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_FOOD_NAME,foodname);
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_CATEGORY,category);
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_EXPIRY_DATE,expiryD);
        cv.put(FoodContract.FoodEntity.COLUMN_NAME_INGREDIENTS,ingredients);

        String whereClause = " email=? ";
        String[]whereArgs = {email};
        return db.update(FoodContract.FoodEntity.TABLE_NAME,cv,whereClause,whereArgs);

    }


    public static long deleteFood(FoodHelper foodHelper, String foodname){

        SQLiteDatabase db = foodHelper.getWritableDatabase();


        String whereClause = " food_name=? ";
        String[]whereArgs = {foodname};
        return db.delete(FoodContract.FoodEntity.TABLE_NAME,whereClause,whereArgs);

    }



    public static Cursor getDonorSpecificFoodList(FoodHelper foodHelper,String email){


        SQLiteDatabase db = foodHelper.getReadableDatabase();

        String [] projection = {
                FoodContract.FoodEntity._ID,
                FoodContract.FoodEntity.COLUMN_NAME_EMAIL,
                FoodContract.FoodEntity.COLUMN_NAME_FOOD_NAME,
                FoodContract.FoodEntity.COLUMN_NAME_CATEGORY,
                FoodContract.FoodEntity.COLUMN_NAME_EXPIRY_DATE,
                FoodContract.FoodEntity.COLUMN_NAME_INGREDIENTS,

        };
        String selection = " email=? ";
        String[] selectionArgs = {email};
        return db.query(
                FoodContract.FoodEntity.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );



    }










}
