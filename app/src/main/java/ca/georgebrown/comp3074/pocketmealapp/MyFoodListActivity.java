package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class MyFoodListActivity extends AppCompatActivity {
    public static ListView myfoodList;
    public static Context contextMyFoodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_list);

         myfoodList = findViewById(R.id.MyFoodList);
       contextMyFoodList=  this;
        LoginActivity.dbHelper.getDonorFoodList(LoginActivity.currentUser.getDisplayName(),myfoodList,this);

    }
}
