package ca.georgebrown.comp3074.pocketmealapp;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

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

    public void updateUserInfo(final String username, final String newData, final String columnToBeUpdated) {

        reff.getReference("UserManager/" + username)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            //update email,  not lat and lon and id

                            if (columnToBeUpdated.equals("email")) {


                                int id = Integer.parseInt(dataSnapshot.child("id").getValue().toString());
                                String email = newData;
                                String cityPostalCode = dataSnapshot.child("city_postalcode").getValue().toString();
                                String first_name = dataSnapshot.child("first_name").getValue().toString();
                                String last_name = dataSnapshot.child("last_name").getValue().toString();
                                /*String pass = dataSnapshot.child("password").getValue().toString();*/
                                String type = dataSnapshot.child("type").getValue().toString();

                                user = new User(email, first_name, last_name, "", "");
                                user.setCity_postalcode(cityPostalCode);

                                double lon = Double.parseDouble(dataSnapshot.child("userPoint/longitude").getValue().toString());
                                double lat = Double.parseDouble(dataSnapshot.child("userPoint/latitude").getValue().toString());
                                // user.setUserPoint(lat, lon);
                                reffUserManager.child(LoginActivity.filterEmailKey(newData)).setValue(user);


                                Log.d("===", String.valueOf(dataSnapshot.child("FoodList").exists()));
                                if (dataSnapshot.child("FoodList").exists()) {
                                    for (DataSnapshot fooddata : dataSnapshot.child("FoodList").getChildren()) {
                                        Log.d("===", fooddata.child("category").getValue().toString());
                                        String category = fooddata.child("category").getValue().toString();
                                        String expi = fooddata.child("expiry_date").getValue().toString();
                                        String ingredients = fooddata.child("ingredients").getValue().toString();
                                        Food f = new Food(category, expi, ingredients);

                                        reff.getReference("UserManager/" + LoginActivity.filterEmailKey(newData) + "/FoodList")
                                                .child(fooddata.getKey().toString()).setValue(f);
                                    }
                                }

                                reffUserManager.child(username).removeValue();

                            }

                            if (columnToBeUpdated.equals("first_name") || columnToBeUpdated.equals("last_name") ||
                                    columnToBeUpdated.equals("password") || columnToBeUpdated.equals("city_postalcode")) {
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

    public void updateUserPoint(final String username, final double lon, final double lat) {

        reffUserManager.orderByKey().equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("===", String.valueOf(dataSnapshot.exists()));
                        if (dataSnapshot.exists()) {
                            DatabaseReference specificReff = reff.getReference("UserManager/" + username + "/userPoint");
                            specificReff.child("latitude").setValue(lat);
                            specificReff.child("longitude").setValue(lon);
                            Log.d("User Point Update", "User point updated");

                        } else {
                            Log.d("Data:", "Data doesn't exist");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void deleteUser(final String username) {

        reffUserManager.orderByKey().equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("===", String.valueOf(dataSnapshot.exists()));
                        if (dataSnapshot.exists()) {

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


    public void getSpecificArrayList(String username, final ListView listView, final Context context) {

        //need to impliment it
//check by city, first two characters of the postal code and those online, who have point lat and lon != 0 if the userr has food.
//check if the user exist
//instead of returning the arraylist we will put the adapter and the list here..
        //  userArrayList = new ArrayList<User>();
        foodList = new ArrayList<Food>();
        reff.getReference("UserManager/" + username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    String city_postal = dataSnapshot.child("city_postalcode").getValue().toString();
                    Log.d("===", city_postal);
                    final double lonMainUser = Double.parseDouble(dataSnapshot.child("userPoint/longitude").getValue().toString());
                    double latMainUser = Double.parseDouble(dataSnapshot.child("userPoint/latitude").getValue().toString());

                    reffUserManager.orderByChild("city_postalcode").equalTo(city_postal).limitToFirst(30)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        for (DataSnapshot dataUser : dataSnapshot.getChildren()) {
                                            //check by city, first two characters of the postal code and those online, who have point lat and lon != 0 if the userr has food.
//check if the user exist
                                            //calculate by distance after having the array...
                                            int id = -1;
                                            String email = dataUser.child("email").getValue().toString();
                                            String cityPostalCode = dataUser.child("city_postalcode").getValue().toString();
                                            String first_name = dataUser.child("first_name").getValue().toString();
                                            String last_name = dataUser.child("last_name").getValue().toString();
                                            String pass = "";
                                            String type = "";

                                            user = new User(email, first_name, last_name, "", "");
                                            user.setCity_postalcode(cityPostalCode);

                                            double lon = Double.parseDouble(dataUser.child("userPoint/longitude").getValue().toString());
                                            double lat = Double.parseDouble(dataUser.child("userPoint/latitude").getValue().toString());
                                            // user.setUserPoint(lat, lon);


                                            if (dataUser.child("FoodList").exists()) {
                                                int count = 0;
                                                for (DataSnapshot fooddata : dataUser.child("FoodList").getChildren()) {
                                                    Log.d("===", fooddata.child("category").getValue().toString());
                                                    if (count <= 6) {
                                                        String category = fooddata.child("category").getValue().toString();
                                                        String expi = fooddata.child("expiry_date").getValue().toString();
                                                        String ingredients = fooddata.child("ingredients").getValue().toString();
                                                        Food f = new Food(fooddata.getKey().toString(), category, expi, ingredients, user.getEmail());
                                                        //  Log.d("keys", fooddata.getKey().toString());
                                                        foodList.add(f);
                                                        user.addFood(f);
                                                        count++;
                                                    } else {
                                                        break;
                                                    }

                                                }
                                            }


                                            if (lat != 0 && lon != 0 && user.getFoodArrayList() != null) {
                                                // Log.d("userfood", user.getFoodArrayList().toString());
                                                // userArrayList.add(user);

                                            }

                                        }

                                        //if it is working we will sort

                                        MyArrayAdapter myArrayAdapter = new MyArrayAdapter(context, R.layout.food_item_design, foodList);
                                        listView.setAdapter(myArrayAdapter);
                                        myArrayAdapter.notifyDataSetChanged();
                                        Log.d("===", String.valueOf(lonMainUser));
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