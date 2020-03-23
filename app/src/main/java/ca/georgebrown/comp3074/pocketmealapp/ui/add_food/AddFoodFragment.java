package ca.georgebrown.comp3074.pocketmealapp.ui.add_food;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import ca.georgebrown.comp3074.pocketmealapp.Food;
import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;
import ca.georgebrown.comp3074.pocketmealapp.R;

public class AddFoodFragment extends Fragment implements OnMapReadyCallback {

    private AddFoodViewModel addFoodViewModel;

    // UI Navigation Code Variables
    private Date foodExpDate;
    private EditText foodName, foodDisc, foodAllergy, foodExp;
    private Button addFood;

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addFoodViewModel =
                ViewModelProviders.of(this).get(AddFoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_food, container, false);
        final TextView textView = root.findViewById(R.id.text_add_food);
        addFoodViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // UI Setup
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

                    Food f = new Food("Type"+foodName.getText().toString()
                            ,foodExp.getText().toString()
                            ,foodAllergy.getText().toString()+foodDisc.getText().toString());

                    LoginActivity.dbHelper.addFood(LoginActivity.currentUser.getDisplayName(),foodName.getText().toString(),f);
                    // Create food object and place it inside database. Use the LoginActivity.currentUser.getDisplayName() to see what user it should go under.

                } else {
                    // Show error msg
                    Toast.makeText(getContext(), "Please enter a valid expiry date using the format provided.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Map Search Setup
        searchView = root.findViewById(R.id.sv_location);
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.google_map);
        // What happens when someone looks up an address
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getActivity());
                    try{
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }
}
