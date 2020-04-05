package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MyFoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_detail);

        final TextView txtVFoodType = findViewById(R.id.textVFoodName);
        final EditText txtVDescript = findViewById(R.id.textVDescription);
        final EditText txtAllergies = findViewById(R.id.textVAllergies);
        TextView txtVUsername = findViewById(R.id.textVUsernamePro);
        final EditText txtVExpiry = findViewById(R.id.textVAllergies);

        Button btnEditFood = findViewById(R.id.btnSaveEditFood);

        final String str_FoodName = getIntent().getExtras().getString("FoodName");
        final String str_Description = getIntent().getExtras().getString("Description");
        final String str_Allergies = getIntent().getExtras().getString("Allergies");
        final String str_Expiry = getIntent().getExtras().getString("Expiry");

        txtVFoodType.setText(str_FoodName);
        txtAllergies.setText(str_Allergies);
        txtVDescript.setText(str_Description);
        txtVUsername.setText(LoginActivity.currentUser.getDisplayName());
        txtVExpiry.setText(str_Expiry);

        final Map<String,String> commandFood = new HashMap<>();


        btnEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                commandFood.put("description",txtVDescript.getText().toString());
                commandFood.put("allergies",txtAllergies.getText().toString());
                commandFood.put("expiry",txtVExpiry.getText().toString());

                for(Map.Entry mapElement : commandFood.entrySet()){
                     if(mapElement.getValue().toString().equals("") ||
                             mapElement.getValue().toString().equals(str_Allergies)
                     || mapElement.getValue().toString().equals(str_Description) &&
                             mapElement.getValue().toString().equals(str_Expiry)){

                         continue;
                     }
                     else{
                          LoginActivity.dbHelper.updateFood(LoginActivity.currentUser.getDisplayName(),mapElement.getValue().toString(),mapElement.getKey().toString(),str_FoodName);
                     }
                }
                //save in db and go to add fragment
            }
        });

    }
}
