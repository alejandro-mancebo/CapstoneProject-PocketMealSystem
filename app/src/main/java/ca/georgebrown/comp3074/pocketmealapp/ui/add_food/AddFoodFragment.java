package ca.georgebrown.comp3074.pocketmealapp.ui.add_food;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

import ca.georgebrown.comp3074.pocketmealapp.R;

public class AddFoodFragment extends Fragment {

    private AddFoodViewModel addFoodViewModel;

    // UI Navigation Code Variables
    private Date foodExp;
    private EditText foodName, foodDisc, foodAllergy;
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

        addFood = root.findViewById(R.id.addFoodBTN);

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // What happens when someone clicks add food button.
            }
        });

        return root;
    }
}