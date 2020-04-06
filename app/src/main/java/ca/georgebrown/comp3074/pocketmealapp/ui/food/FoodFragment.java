package ca.georgebrown.comp3074.pocketmealapp.ui.food;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import ca.georgebrown.comp3074.pocketmealapp.GPStracker;
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


        GPStracker gps = new GPStracker(getActivity());
        Location l = gps.getLocation();
        if(l == null){
            Toast.makeText(getContext(), "There is an issue with retrieving your location.", Toast.LENGTH_SHORT).show();
        }
        else{
            double longitude = l.getLongitude();
            double latitude = l.getLatitude();
            LoginActivity.dbHelper.getSpecificArrayList(LoginActivity.currentUser.getDisplayName(),longitude,latitude, li, getActivity());
        }

        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Food f = ((Food) parent.getItemAtPosition(position));

                // Create fragment and give it an argument specifying the article it should show
                FoodDetailsFragment newFragment = new FoodDetailsFragment();
                Bundle args = new Bundle();
                args.putString("FoodName", f.getFoodname());
                args.putString("FoodUsername", f.getUsername());
                args.putString("Allergies", f.getAllergies());
                args.putString("Description", f.getDescription());
                args.putString("Expiry", f.getExpiry_date());
                newFragment.setArguments(args);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                /*
                Intent i = new Intent(getActivity(),FoodDetailActivity.class);
                i.putExtra("FoodType",f.getFoodname());
                i.putExtra("FoodUsername",f.getUsername());
             // i.putExtra("FoodIngre",f.getDescription());
                i.putExtra("Expiry",f.getExpiry_date());
                startActivity(i);
                /////////////////////////////////////////////////////////////////////////
                FragmentTransaction transection=getFragmentManager().beginTransaction();
                FoodDetailsFragment mfragment=new FoodDetailsFragment();
                //using Bundle to send data
                Bundle bundle=new Bundle();
                bundle.putString("FoodEmail",f.getEmail());
                bundle.putString("FoodName",f.getFoodname());
                //bundle.putString("FoodExpiryDate",f.getExpiry_date());
                mfragment.setArguments(bundle); //data being send to SecondFragment
                transection.replace(R.id.container???, mfragment);
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