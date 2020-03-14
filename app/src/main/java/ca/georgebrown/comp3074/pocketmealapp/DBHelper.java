package ca.georgebrown.comp3074.pocketmealapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DBHelper {

    private FirebaseDatabase reff;
    private DatabaseReference reffUserManager;
    private User user;

    private ArrayList<User> userArrayList;
    private ArrayList<Food> foodList;

    public DBHelper() {

        reff = FirebaseDatabase.getInstance();
        reffUserManager = reff.getReference("UserManager");
    }


    public void insertUser(final String username, final User u) {

        reffUserManager.orderByKey().equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("===", String.valueOf(dataSnapshot.exists()));
                        if (dataSnapshot.exists()) {
                            Log.d("Insert:", "User already exists");

                        } else {
                            //  Food f = new Food("","","");
                            //       u.foodList.add(f);

                            reffUserManager.child(username).setValue(u);

                            Log.d("Insert:", "New User insert successfully");
                        }

                        // Log.d("===", String.valueOf(b));
                        // user = childSnapshot.getValue(User.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public void updateUserInfo(final String email, final String newData, final String columnToBeUpdated) {

        reffUserManager.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (dataSnapshot.exists()) {
                            //update email,  not lat and lon and id
                            String username = dataSnapshot.getKey().toString();
                            if (columnToBeUpdated.equals("email")) {

                                DatabaseReference specificReff = reff.getReference("UserManager/" + username);
                                specificReff.child(columnToBeUpdated).setValue(newData);

                                user.updateEmail(newData);

                            }

                            if(columnToBeUpdated.equals("password")){

                                user.updatePassword(newData);

                            }

                            if (columnToBeUpdated.equals("first_name") || columnToBeUpdated.equals("last_name") || columnToBeUpdated.equals("city_postalcode")) {
                                DatabaseReference specificReff = reff.getReference("UserManager/" + username);
                                specificReff.child(columnToBeUpdated).setValue(newData);
                                Log.d("User Info Update", "User Information updated successfully");

                            }
                        }
                        //make sure city_postalcode is in lowercase extremely important

                        else {
                            Log.d("Data:", "Data doesn't exist");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        //update email,  not lat and lon and id
    }


    public void deleteUser(final String email) {

        reffUserManager.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("===", String.valueOf(dataSnapshot.exists()));
                        if (dataSnapshot.exists()) {
                            String username = dataSnapshot.getKey().toString();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete();
                            reffUserManager.child(username).removeValue();
                        } else {

                            Log.d("Delete User ", "User does not exit");


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("error", databaseError.toString());
                    }
                });

    }

    public void addFood(final String username, final String foodname, final Food f1) {


        reffUserManager.orderByKey().equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("===", String.valueOf(dataSnapshot.exists()));
                        if (dataSnapshot.exists()) {

                            DatabaseReference specificReff = reff.getReference("UserManager/" + username + "/FoodList");

                            specificReff.child(foodname).setValue(f1);


                            Log.d("FoodInsert:", "Food insert successfully");

                        } else {

                            Log.d("FoodInsert:", "Food already exists or user not in DB");
                        }


                        // Log.d("===", String.valueOf(b));
                        // user = childSnapshot.getValue(User.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//check if you can add more


    }

    public void updateFood(final String username, final String newData, final String columnToBeUpdated, final String Foodname) {


        reff.getReference("UserManager/" + username + "/FoodList/" + Foodname)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            //update email,  not lat and lon and id
                            if (columnToBeUpdated.equals("foodname")) {
                                String cat = dataSnapshot.child("category").getValue().toString();
                                String ingredients = dataSnapshot.child("ingredients").getValue().toString();
                                String expi = dataSnapshot.child("expiry_date").getValue().toString();

                                Food f = new Food(cat, expi, ingredients);

                                addFood(username, newData, f);
                                Log.d("Food Update", "Food updated");


                            } else if (columnToBeUpdated.equals("category") || columnToBeUpdated.equals("expiry_date") ||
                                    columnToBeUpdated.equals("ingredients")) {
                                DatabaseReference specificReff = reff.getReference("UserManager/" + username + "/FoodList/" + Foodname);
                                specificReff.child(columnToBeUpdated).setValue(newData);
                                Log.d("Food Update", "Food updated");

                            }
                        }
//make sure city_postalcode is in lowercase extremely important

                        else {
                            Log.d("Update Food:", "Data doesn't exist");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }


    public void updateFoodPoint(final String email, final String foodname, final double lon, final double lat) {

        reffUserManager.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("===", String.valueOf(dataSnapshot.exists()));
                        if (dataSnapshot.exists() && dataSnapshot.child("foodList/"+foodname).exists()) {
                            String username = dataSnapshot.getKey().toString();
                            DatabaseReference specificReff = reff.getReference("UserManager/" + username + "foodList/"+foodname);
                            specificReff.child("userPoint/latitude").setValue(lat);
                            specificReff.child("userPoint/longitude").setValue(lon);
                            Log.d("Food Point Update", "Food point updated");

                        } else {
                            Log.d("Data:", "Data doesn't exist");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    public void deleteFood(final String username, final String foodname) {

        reff.getReference("UserManager/" + username + "/FoodList/" + foodname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    DatabaseReference specReff = reff.getReference("UserManager/" + username + "/FoodList");
                    specReff.child(foodname).removeValue();
                    Log.d("Delete Food", "Food deleted...");

                } else {


                    Log.d("Delete Update", "Food or User doesn't exit");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void getSpecificArrayList(String username, final Double mainLon, final Double mainLat, final ListView listView, final Context context) {
              DynamicList.foodList.clear();
        //  userArrayList = new ArrayList<User>();

    //  final  Map<Double,Food> map = new TreeMap<>();
        //foodList = new ArrayList<Food>();
        reff.getReference("UserManager/" + username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    String city_postal = dataSnapshot.child("city_postalcode").getValue().toString();
                    Log.d("===", city_postal);
                         final Point point1 = new Point(mainLon,mainLat);

                    reffUserManager.orderByChild("city_postalcode").equalTo(city_postal).limitToFirst(50)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        for (DataSnapshot dataUser : dataSnapshot.getChildren()) {

                                            String email = dataUser.child("email").getValue().toString();
                                            double lon  =0;
                                            double lat = 0;


                                            // user.setUserPoint(lat, lon);

                                            if (dataUser.child("FoodList").exists()) {
                                                int count = 0;
                                                for (DataSnapshot fooddata : dataUser.child("FoodList").getChildren()) {
                                                    Log.d("===", fooddata.child("category").getValue().toString());

                                                    lat = Double.parseDouble(fooddata.child("userPoint/latitude").getValue().toString());
                                                    lon = Double.parseDouble(fooddata.child("userPoint/longitude").getValue().toString());

                                                    if (count <= 7 && lat !=0 && lon !=0) {
                                                        String category = fooddata.child("category").getValue().toString();
                                                        String expi = fooddata.child("expiry_date").getValue().toString();
                                                        String ingredients = fooddata.child("ingredients").getValue().toString();
                                                        Food f = new Food(fooddata.getKey().toString(), category, expi, ingredients, email);
                                                        f.setUserPoint(lat,lon);
                                                        Point point2 = new Point(lon,lat);
                                                        f.setDistance(getDistance(point1,point2));
                                                        //foodList.add(f);
                                                        DynamicList.insert(f);
                                                        //map.put(getDistance(point1,point2),f);
                                                        count++;
                                                    } else {
                                                        break;
                                                    }
                                                }
                                            }
                                        }


                                        Log.d("List", DynamicList.foodList.toString());
                                        MyArrayAdapter myArrayAdapter = new MyArrayAdapter(context, R.layout.food_item_design, DynamicList.foodList);
                                        listView.setAdapter(myArrayAdapter);
                                        myArrayAdapter.notifyDataSetChanged();


                                        //Log.d("===", String.valueOf(lonMainUser));
                                        //do item event listener here and intent then call get user to set their the text

                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//if user is null there is no data

    }


    public void getDonorFoodList(String username, final ListView lView, final Context context) {
        final ArrayList<Food> foodArrayList = new ArrayList<>();
        reff.getReference("UserManager/" + username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot fooddata : dataSnapshot.child("FoodList").getChildren()) {
                        Log.d("===", fooddata.child("category").getValue().toString());

                        String category = fooddata.child("category").getValue().toString();
                        String expi = fooddata.child("expiry_date").getValue().toString();
                        String ingredients = fooddata.child("ingredients").getValue().toString();
                        Food f = new Food(fooddata.getKey().toString(), category, expi, ingredients);

                        foodArrayList.add(f);

                    }

                    MyArrayAdapter arrayAdapter = new MyArrayAdapter(context, R.layout.food_item_design, foodArrayList);
                    lView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


//displayFoodDetails() take text and set, displayProfileUser() basically setter for text
// and chat function remaining


    private double getDistance(Point p1, Point p2) {

        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(p2.getLatitude() - p1.getLatitude());  // deg2rad below
        double dLon = deg2rad(p2.getLongitude() - p1.getLongitude());
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(p1.getLatitude())) * Math.cos(deg2rad(p2.getLatitude())) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in km
        return d;


    }

    private double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }


}