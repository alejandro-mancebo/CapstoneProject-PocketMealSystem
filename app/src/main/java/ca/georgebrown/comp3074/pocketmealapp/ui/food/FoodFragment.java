package ca.georgebrown.comp3074.pocketmealapp.ui.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.Serializable;

import ca.georgebrown.comp3074.pocketmealapp.DBHelper;
import ca.georgebrown.comp3074.pocketmealapp.Food;
import ca.georgebrown.comp3074.pocketmealapp.FoodDetailActivity;
import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;
import ca.georgebrown.comp3074.pocketmealapp.R;
import ca.georgebrown.comp3074.pocketmealapp.drawer_activity;
import ca.georgebrown.comp3074.pocketmealapp.ui.food_details.FoodDetailsFragment;

public class FoodFragment extends Fragment {

    private FoodViewModel foodViewModel;

    public static ListView li;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodViewModel =
                ViewModelProviders.of(this).get(FoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_food, container, false);
        li = root.findViewById(R.id.listView1);


        LoginActivity.dbHelper.getSpecificArrayList(LoginActivity.currentUser.getDisplayName(),50.0,50.0,li, getActivity());

        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Food f = ((Food) parent.getItemAtPosition(position));
                Intent i = new Intent(getActivity(),FoodDetailActivity.class);
                i.putExtra("FoodItem",f.getEmail());
                startActivity(i);
                /*
                FragmentTransaction transection=getFragmentManager().beginTransaction();
                FoodDetailsFragment mfragment=new FoodDetailsFragment();
//using Bundle to send data
                Bundle bundle=new Bundle();
                bundle.putString("FoodEmail",f.getEmail());
                bundle.putString("FoodName",f.getFoodname());
                //bundle.putString("FoodExpiryDate",f.getExpiry_date());
                mfragment.setArguments(bundle); //data being send to SecondFragment
                transection.replace(R.id.?, mfragment);
                transection.commit();*/
            }
        });

        final TextView textView = root.findViewById(R.id.text_food);
        foodViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        return root;
    }
}