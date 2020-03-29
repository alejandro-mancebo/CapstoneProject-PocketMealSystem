package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MyFoodListActivity extends AppCompatActivity {
    public static ListView myfoodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_list);

         myfoodList = findViewById(R.id.MyFoodList);

        LoginActivity.dbHelper.getDonorFoodList(LoginActivity.currentUser.getDisplayName(),myfoodList,this);

    }
}
