package ca.georgebrown.comp3074.pocketmealapp.ui.add_food;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import ca.georgebrown.comp3074.pocketmealapp.Food;
import ca.georgebrown.comp3074.pocketmealapp.GPStracker;
import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;
import ca.georgebrown.comp3074.pocketmealapp.R;

public class AddFoodFragment extends Fragment {

    private AddFoodViewModel addFoodViewModel;

    // UI Navigation Code Variables
    private Date foodExpDate;
    private EditText foodName, foodDisc, foodAllergy, foodExp;
    private Button addFood;
    private Double foodLat, foodLon;

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
        // What happens when someone clicks Add Food button.
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check Expiry Date provided by the user.
                if (foodExp.getText().toString().matches("\\d{2}-\\d{2}-\\d{2}")) {
                    Food f = new Food("Type"+foodName.getText().toString()
                            ,foodExp.getText().toString()
                            ,foodAllergy.getText().toString()+foodDisc.getText().toString());
                    // Set the location to the chosen location on the map or default the current location of the user's device
                    f.setUserPoint(foodLat,foodLon);
                    // Place food into the database
                    LoginActivity.dbHelper.addFood(LoginActivity.currentUser.getDisplayName(),foodName.getText().toString(),f);
                } else {
                    // Show error msg
                    Toast.makeText(getContext(), "Please enter a valid expiry date using the format provided.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Map Search Setup
        //searchView = root.findViewById(R.id.sv_location);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                GPStracker gps = new GPStracker(getActivity());
                Location l = gps.getLocation();

                if(l == null){
                    Toast.makeText(getContext(), "There is an issue with retrieving your location.", Toast.LENGTH_SHORT).show();
                }
                else{
                    foodLat = l.getLatitude();
                    foodLon = l.getLongitude();

                    LatLng originalLatLng = new LatLng(foodLat, foodLon);

                    CameraPosition googlePlex = CameraPosition.builder()
                            .target(originalLatLng)
                            .zoom(10)
                            .bearing(0)
                            .tilt(45)
                            .build();
                    mMap.addMarker(new MarkerOptions().position(originalLatLng));
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);
                }

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(latLng));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        foodLat = latLng.latitude;
                        foodLon = latLng.longitude;
                    }
                });
            }
        });

        // What happens when someone looks up an address
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                String location = searchView.getQuery().toString();
//                List<Address> addressList = null;
//
//                if(location != null || !location.equals("")){
//                    Geocoder geocoder = new Geocoder(getActivity());
//                    try{
//                        addressList = geocoder.getFromLocationName(location, 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
//                    map.addMarker(new MarkerOptions().position(latLng).title(location));
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

//        mapFragment.getMapAsync(this);

        return root;
    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//
//    }
}
