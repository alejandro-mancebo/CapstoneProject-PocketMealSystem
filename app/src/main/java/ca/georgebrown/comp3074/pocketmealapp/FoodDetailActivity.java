package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        TextView txtVFoodType = findViewById(R.id.textVFoodName);
        TextView txtVIngre = findViewById(R.id.textVDescription);
        TextView txtVUsername = findViewById(R.id.textVUsername);
        TextView txtVExpiry = findViewById(R.id.textVAllergies);

        String str_FoodType = getIntent().getExtras().getString("FoodType");
        String str_Username = getIntent().getExtras().getString("FoodUsername");
        String str_Ingre = getIntent().getExtras().getString("FoodIngre");
        String str_Expiry = getIntent().getExtras().getString("Expiry");

        txtVFoodType.setText("Food Type:  "+str_FoodType);
        txtVIngre.setText("Ingredient:  "+ str_Ingre);
        txtVUsername.setText("Username:  "+ str_Username);
        txtVExpiry.setText("Expiry Date:  " +str_Expiry);
        txtVUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //go to profile page
                Intent i = new Intent(FoodDetailActivity.this,drawer_activity.class);
                startActivity(i);
            }
        });

    }
}
