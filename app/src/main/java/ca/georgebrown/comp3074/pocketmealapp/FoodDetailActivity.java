package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ca.georgebrown.comp3074.pocketmealapp.ui.profile.ProfileFragment;

public class FoodDetailActivity extends AppCompatActivity {
    public static String usernameProfile= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        TextView txtVFoodType = findViewById(R.id.textVFoodName);
       // TextView txtVIngre = findViewById(R.id.textVDescription);
        TextView txtVUsername = findViewById(R.id.textVUsernamePro);
        TextView txtVExpiry = findViewById(R.id.textVAllergies);

        String str_FoodType = getIntent().getExtras().getString("FoodType");
        final String str_Username = getIntent().getExtras().getString("FoodUsername");
      //  String str_Ingre = getIntent().getExtras().getString("FoodIngre");
        String str_Expiry = getIntent().getExtras().getString("Expiry");

        txtVFoodType.setText("Food Type:  "+str_FoodType);
      //  txtVIngre.setText("Ingredient:  "+ str_Ingre);
        txtVUsername.setText("Username:  "+ str_Username);
        txtVExpiry.setText("Expiry Date:  " +str_Expiry);
        txtVUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------go to profile fragment with passed parameters str_Username--------------


                // Intent i = new Intent(FoodDetailActivity.this, ProfileFragment.class);
                usernameProfile = str_Username;
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, ProfileFragment).commit();

                // startActivity(i);
            }
        });

    }
}
