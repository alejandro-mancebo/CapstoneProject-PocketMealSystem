package ca.georgebrown.comp3074.pocketmealapp.ui.add_food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;
import ca.georgebrown.comp3074.pocketmealapp.R;

public class AddFoodFragment extends Fragment {

    private AddFoodViewModel addFoodViewModel;

    // UI Navigation Code Variables
    private Date foodExpDate;
    private EditText foodName, foodDisc, foodAllergy, foodExp;
    private Button addFood;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addFoodViewModel =
                ViewModelProviders.of(this).get(AddFoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_food, container, false);
        final TextView textView = root.findViewById(R.id.text_add_food);
        addFoodViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        foodName = root.findViewById(R.id.foodnameEdit);
        foodDisc = root.findViewById(R.id.foodDescEdit);
        foodAllergy = root.findViewById(R.id.allergyEdit);
        foodExp = root.findViewById(R.id.expDateEdit);

        addFood = root.findViewById(R.id.addFoodBTN);

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // What happens when someone clicks Add Food button.
                // Check Expiry Date provided by the user.
                if (foodExp.getText().toString().matches("\\d{2}-\\d{2}-\\d{2}")) {

                    // Create food object and place it inside database. Use the LoginActivity.currentUser.getDisplayName() to see what user it should go under.

                } else {
                    // Show error msg
                    Toast.makeText(getContext(), "Please enter a valid expiry date using the format provided.", Toast.LENGTH_LONG).show();
                }

            }
        });

        return root;
    }
}