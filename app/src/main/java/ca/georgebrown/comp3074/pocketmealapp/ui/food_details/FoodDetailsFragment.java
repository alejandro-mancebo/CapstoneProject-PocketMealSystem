package ca.georgebrown.comp3074.pocketmealapp.ui.food_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ca.georgebrown.comp3074.pocketmealapp.Food;
import ca.georgebrown.comp3074.pocketmealapp.R;
import ca.georgebrown.comp3074.pocketmealapp.ui.food.FoodFragment;

public class FoodDetailsFragment extends Fragment {

    private FoodDetailsViewModel foodDetailsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodDetailsViewModel =
                ViewModelProviders.of(this).get(FoodDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_food_details, container, false);
        final TextView textView = root.findViewById(R.id.text_food_details);

         foodDetailsViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}