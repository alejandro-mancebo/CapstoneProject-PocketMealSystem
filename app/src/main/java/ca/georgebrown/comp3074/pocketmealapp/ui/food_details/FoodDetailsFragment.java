package ca.georgebrown.comp3074.pocketmealapp.ui.food_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.HashMap;
import java.util.Map;

import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;
import ca.georgebrown.comp3074.pocketmealapp.R;
import ca.georgebrown.comp3074.pocketmealapp.ui.profile.ProfileFragment;

public class FoodDetailsFragment extends Fragment {

    private FoodDetailsViewModel foodDetailsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodDetailsViewModel =
                ViewModelProviders.of(this).get(FoodDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_food_details, container, false);
        final TextView textView = root.findViewById(R.id.text_food_details);

        final TextView txtVFoodType = root.findViewById(R.id.textVFoodName);
        final TextView txtVDescript = root.findViewById(R.id.textVDescription);
        final TextView txtAllergies = root.findViewById(R.id.textVAllergies);
        final TextView txtVPostDate = root.findViewById(R.id.textVPostDate);
        final TextView txtVExpiry = root.findViewById(R.id.textVExpiryDate);
        TextView txtVUsername = root.findViewById(R.id.textVUsernamePro);

        final String str_Username = getArguments().getString("FoodUsername"); // savedInstanceState.getString("FoodUsername");

        final String str_FoodName = getArguments().getString("FoodName");
        final String str_Allergies = getArguments().getString("Allergies");
        final String str_Description = getArguments().getString("Description");
        final String str_Expiry = getArguments().getString("Expiry");


//        final String str_FoodName = getIntent().getExtras().getString("FoodName");
//        final String str_Description = getIntent().getExtras().getString("Description");
//        final String str_Allergies = getIntent().getExtras().getString("Allergies");
//        final String str_Expiry = getIntent().getExtras().getString("Expiry");

        txtVFoodType.setText(str_FoodName);
        txtAllergies.setText(str_Allergies);
        txtVDescript.setText(str_Description);
        txtVUsername.setText(str_Username);
        txtVExpiry.setText(str_Expiry);

        txtVUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create fragment and give it an argument specifying the article it should show
                ProfileFragment newFragment = new ProfileFragment();
                Bundle args = new Bundle();
                args.putString("Username", str_Username);
                newFragment.setArguments(args);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });


         foodDetailsViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}