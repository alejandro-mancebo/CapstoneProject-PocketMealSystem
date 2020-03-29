package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyFoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_detail);




        EditText txtVFoodType = findViewById(R.id.textVFoodName);
        // TextView txtVIngre = findViewById(R.id.textVDescription);
        TextView txtVUsername = findViewById(R.id.textVUsernamePro);
        EditText txtVExpiry = findViewById(R.id.textVAllergies);
        Button btnEditFood = findViewById(R.id.btnSaveEditFood);
        String str_FoodType = getIntent().getExtras().getString("FoodType");
        //final String str_Username = getIntent().getExtras().getString("FoodUsername");
        //  String str_Ingre = getIntent().getExtras().getString("FoodIngre");
        String str_Expiry = getIntent().getExtras().getString("Expiry");

        txtVFoodType.setText(str_FoodType);
        //  txtVIngre.setText("Ingredient:  "+ str_Ingre);
        txtVUsername.setText(LoginActivity.currentUser.getDisplayName());
        txtVExpiry.setText(str_Expiry);

        btnEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //save in db and go to add fragment
            }
        });

    }
}
