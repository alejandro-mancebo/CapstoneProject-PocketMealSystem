package ca.georgebrown.comp3074.pocketmealapp;

import android.provider.BaseColumns;

public final class FoodContract {

    private FoodContract(){}

   public static class FoodEntity implements BaseColumns {

       public static final String TABLE_NAME = "Food_table";
       public static final String COLUMN_NAME_FOOD_NAME = "food_name";
       public static final String COLUMN_NAME_CATEGORY = "category";
       public static final String COLUMN_NAME_EXPIRY_DATE = "expiry_date";
       public static final String COLUMN_NAME_INGREDIENTS = "ingredients";
       public static final String COLUMN_NAME_EMAIL = "email";
//EXPIRY DATE

       public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( "
               + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_EMAIL + " TEXT NOT NULL, "
               + COLUMN_NAME_FOOD_NAME + " TEXT NOT NULL, " + COLUMN_NAME_CATEGORY + " TEXT NOT NULL, "
               + COLUMN_NAME_EXPIRY_DATE + " TEXT NOT NULL, " + COLUMN_NAME_INGREDIENTS + " TEXT NOT NULL, "
               + " CONSTRAINT EMAIL_ID FOREIGN KEY ("+ COLUMN_NAME_EMAIL +") REFERENCES "+
               UserContract.UserEntity.TABLE_NAME +"("+ UserContract.UserEntity.COLUMN_NAME_EMAIL +") ON DELETE CASCADE);";
// FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ADD RELATIONSHIP TO OTHER DATABASE.

       public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;







   }


}
