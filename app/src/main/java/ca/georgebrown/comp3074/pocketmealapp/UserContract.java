package ca.georgebrown.comp3074.pocketmealapp;

import android.provider.BaseColumns;

public final class UserContract {

    private UserContract(){}
    public static class UserEntity implements BaseColumns{

        //must add image for profile


        public static final String TABLE_NAME = "User_table";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_PHONE = "phone_number";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_PROVINCE = "province";
        public static final String COLUMN_NAME_POSTAL_CODE = "postal_code";
        public static final String COLUMN_NAME_TYPE = "type";

        public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_NAME_TYPE + " TEXT, " +
                COLUMN_NAME_FIRST_NAME + " TEXT, " + COLUMN_NAME_LAST_NAME + " TEXT, " +
                COLUMN_NAME_PASSWORD + " TEXT, " + COLUMN_NAME_PHONE + " TEXT NULL, " +
                COLUMN_NAME_ADDRESS + " TEXT NULL, " + COLUMN_NAME_PROVINCE + " TEXT NULL, " +
                COLUMN_NAME_POSTAL_CODE + " TEXT NULL)";



        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }

}
