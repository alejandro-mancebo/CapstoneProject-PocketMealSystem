package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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

                // Create fragment and give it an argument specifying the article it should show
                ProfileFragment newFragment = new ProfileFragment();
                Bundle args = new Bundle();
                int position = 0;
                args.putInt(str_Username, position);
                newFragment.setArguments(args);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

    }
}
