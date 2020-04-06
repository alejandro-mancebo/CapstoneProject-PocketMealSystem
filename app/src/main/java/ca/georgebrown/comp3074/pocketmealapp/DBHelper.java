package ca.georgebrown.comp3074.pocketmealapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DBHelper {

    private FirebaseDatabase reff;
    private DatabaseReference reffUserManager;
    private DatabaseReference reffChatManager;
    private DatabaseReference imgDatabaseReff;
    private StorageReference imgStorageReff;

    public static MessageArrayAdapter messAdapter;
    private ArrayList<User> receiverList = new ArrayList<>();
    private ArrayList<Chat> messages = new ArrayList<>();

    public DBHelper() {

        reff = FirebaseDatabase.getInstance();
        reffUserManager = reff.getReference("UserManager");
        reffChatManager = reff.getReference("ChatManager");
        imgStorageReff = FirebaseStorage.getInstance().getReference("pics");
        imgDatabaseReff = FirebaseDatabase.getInstance().getReference("pics");

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

        reff.getReference("UserManager/"+username)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (dataSnapshot.exists()) {

                            if (columnToBeUpdated.equals("email")) {

                                DatabaseReference specificReff = reff.getReference("UserManager/" + username);
                                specificReff.child(columnToBeUpdated).setValue(newData);

                                user.updateEmail(newData);
                                Log.d("User Info Update", "User Information updated successfully");
                            }

                          else if(columnToBeUpdated.equals("password")){

                                user.updatePassword(newData);
                                Log.d("User Info Update", "User Information updated successfully");
                            }
                           else if(columnToBeUpdated.equals("city_postalcode")){
                              String lastChar = String.valueOf(newData.charAt(newData.length()-1));
                              String firstChar = String.valueOf(newData.charAt(0));
                                DatabaseReference specificReff = reff.getReference("UserManager/" + username);
                                String arr_city_pos[]=  dataSnapshot.child("city_postalcode").toString().split("_",2);
                                if(lastChar.equals("_")){
                                    specificReff.child(columnToBeUpdated).setValue(newData+arr_city_pos[1]);
                                    Log.d("User Info Update", "User Information updated successfully");
                                }
                                else if(firstChar.equals("_")){
                                    specificReff.child(columnToBeUpdated).setValue(arr_city_pos[0]+newData);
                                    Log.d("User Info Update", "User Information updated successfully");
                                }
                                else{

                                    specificReff.child(columnToBeUpdated).setValue(newData);
                                    Log.d("User Info Update", "User Information updated successfully");
                                }

                            }

                          else if (columnToBeUpdated.equals("first_name") || columnToBeUpdated.equals("last_name")  || columnToBeUpdated.equals("bio")) {
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

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void updateFood(final String username, final String newData, final String columnToBeUpdated, final String Foodname) {


        reff.getReference("UserManager/" + username + "/FoodList/" + Foodname)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            //update email,  not lat and lon and id
                          if (columnToBeUpdated.equals("category") || columnToBeUpdated.equals("expiry_date") ||
                                    columnToBeUpdated.equals("ingredients") || columnToBeUpdated.equals("allergies")) {
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

    public void getSpecificArrayList(final String username, final Double mainLon, final Double mainLat, final ListView listView, final Context context) {
        
        DynamicList.foodList.clear();
        //  userArrayList = new ArrayList<User>();

        reff.getReference("UserManager/" + username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    String city_postal = dataSnapshot.child("city_postalcode").getValue().toString();
                    // Log.d("===", city_postal);
                    final Point point1 = new Point(mainLon,mainLat);

                    reffUserManager.orderByChild("city_postalcode").equalTo(city_postal).limitToFirst(50).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null ) {
                                for (DataSnapshot dataUser : dataSnapshot.getChildren()) {
                                   if(dataUser.getKey().toString().equals(username)){continue;}

                                    String email = dataUser.child("email").getValue().toString();
                                    double lon  =0;
                                    double lat = 0;
                                    // user.setUserPoint(lat, lon);

                                    if (dataUser.child("FoodList").exists()) {
                                        int count = 0;
                                        for (DataSnapshot fooddata : dataUser.child("FoodList").getChildren()) {
                                          //  Log.d("===", fooddata.child("category").getValue().toString());

                                            lat = Double.parseDouble(fooddata.child("userPoint/latitude").getValue().toString());
                                            lon = Double.parseDouble(fooddata.child("userPoint/longitude").getValue().toString());

                                            if (count <= 7 && lat !=0 && lon !=0) {
                                                String category = fooddata.child("category").getValue().toString();
                                                String expi = fooddata.child("expiry_date").getValue().toString();
                                             //   String description = fooddata.child("description").getValue().toString(); // description was ingredients before Hristo edit
                                                Food f = new Food(fooddata.getKey().toString(), category, expi, email);
                                                f.setUserPoint(lat,lon);
                                                Point point2 = new Point(lon,lat);
                                                f.setDistance(getDistance(point1,point2));
                                                f.setUsername(dataUser.getKey().toString());
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

                                //  Log.d("List", DynamicList.foodList.toString());
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

        DynamicList.foodList.clear();
        reff.getReference("UserManager/" + username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot fooddata : dataSnapshot.child("FoodList").getChildren()) {
                        Log.d("===", fooddata.child("category").getValue().toString());

                        String category = fooddata.child("category").getValue().toString();
                        String expi = fooddata.child("expiry_date").getValue().toString();
                       // String ingredients = fooddata.child("ingredients").getValue().toString();

                        Food f = new Food(fooddata.getKey().toString(), category, expi);

                        DynamicList.foodList.add(f);

                    }
                    MyArrayAdapter arrayAdapter = new MyArrayAdapter(context, R.layout.myfoodlist_design, DynamicList.foodList);
                    lView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setProfileInfo(String username, final TextView City_postal, final TextView fullName, final TextView Email, final TextView Bio, final ImageView imageView){ //, final TextView digit

        reff.getReference("UserManager/"+username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String email = dataSnapshot.child("email").getValue().toString();
                    String lastName = dataSnapshot.child("last_name").getValue().toString();
                    String firstName = dataSnapshot.child("first_name").getValue().toString();
                    String city = dataSnapshot.child("city_postalcode").getValue().toString();
                    String bio = "";
                    if(dataSnapshot.child("bio").exists()){
                        bio = dataSnapshot.child("bio").getValue().toString();
                    }

                    fullName.setText(firstName +"  "+lastName);
                    Email.setText(email);
                    Bio.setText(bio);
                    // String arr_city[] = city.split("_",2);
                    City_postal.setText(city); //arr_city[0]

                    // digit.setText(arr_city[1]);
                    // for now user cannot update username
                    // textview will be set here..

                    // Setting image for profile needs to get UID of certain user.
                    // HOW DO YOU FIND UID OF USER WITH NAME X SET THAT TO EQUAL userID and code will work!
                    String userID = LoginActivity.mAuth.getInstance().getCurrentUser().getUid(); // NEEDS FIX BY STEEVEN
                    String photoID = userID + ".jpg";
                    LoginActivity.mStorageRef.child(photoID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(imageView);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Toast.makeText(drawer_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    Log.d("User does not exit", "No Data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // and chat function remaining

    public void sendChat(final Chat chat){

        reff.getReference("ChatManager/"+chat.getSender()+'_'+chat.getReceiver()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    reffChatManager.child(chat.getSender()+"_"+chat.getReceiver()).push().setValue(chat);
                }
                else{
                   reff.getReference("ChatManager/"+chat.getReceiver()+"_"+chat.getSender()).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                           if(dataSnapshot1.exists()){
                               reffChatManager.child(chat.getReceiver()+"_"+chat.getSender()).push().setValue(chat);
                           }
                           else{
                               reffChatManager.child(chat.getSender()+"_"+chat.getReceiver()).push().setValue(chat);
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

    }

    private void deleteMessages(final Chat chat){
        reff.getReference("ChatManager/"+chat.getSender()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){ reffChatManager.child(chat.getSender()).child(chat.getReceiver()).removeValue(); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getReceiverList(final Context context, final ListView li, final String username){

        receiverList.clear();
        reffChatManager.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("===", String.valueOf(dataSnapshot.exists()));
                if(dataSnapshot.exists()){
                  for(DataSnapshot data : dataSnapshot.getChildren()) {
                      String key = data.getKey().toString();
                      if (key.contains(username)) {
                          String arr[] = key.split("_", 2);
                          User user = new User("", "", "", "", "");

                          if (!arr[0].equals(username)) {
                              user.setUsername(arr[0]);
                              Log.d("===", arr[0]);
                          } else {
                              user.setUsername(arr[1]);
                          }
                          receiverList.add(user);
                          //find a way to improve filter
                          //Log.d("===", user.getUsername().toString());
                      }
                  }
                   // Log.d("===", receiverList.toString());

                }
                else{


                }
                UserArrayAdapter userAdapter = new UserArrayAdapter(context,R.layout.users_item_design,receiverList );
                li.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getMessages(final String username, final String receiver, final ListView li , final Context c){

        reff.getReference("ChatManager/"+username+"_"+receiver).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    messages.clear();
                    for(DataSnapshot chat : dataSnapshot.getChildren()) {
                        String receiver = chat.child("receiver").getValue().toString();
                        String sender = chat.child("sender").getValue().toString();
                        String message = chat.child("message").getValue().toString();

                        Chat chat1 = new Chat(sender, message, receiver);
                        messages.add(chat1);
                    }
                }
                else{
                    reff.getReference("ChatManager/"+receiver+"_"+username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            if (dataSnapshot1.exists()) {
                                messages.clear();
                                for(DataSnapshot chat : dataSnapshot1.getChildren()) {
                                    String receiver = chat.child("receiver").getValue().toString();
                                    String sender = chat.child("sender").getValue().toString();
                                    String message = chat.child("message").getValue().toString();

                                    Chat chat1 = new Chat(sender, message, receiver);
                                    messages.add(chat1);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                messAdapter = new MessageArrayAdapter(c,R.layout.message_details_design,messages);
                messAdapter.notifyDataSetChanged();
                li.setAdapter(messAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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

    // UNSURE IF IT WORKS!
    private void getProfilePic(final Activity activity, final Context context, final OnSuccessListener<Uri> uri, final ImageView imageView, final Toast errToast){
        imgDatabaseReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(context).load(uri).into(imageView); // imgStorageReff.child(LoginActivity.currentUser.getUid() + ".JPEG")
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                errToast.makeText(activity, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}