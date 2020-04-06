package ca.georgebrown.comp3074.pocketmealapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class LoginActivity extends AppCompatActivity {

    // Firebase Variables
    public static DBHelper dbHelper;
    public static FirebaseAuth mAuth;
    public static FirebaseUser currentUser;
    public static StorageReference mStorageRef;
    public static DatabaseReference mDatabaseRef;

    // Location Manager
    public static LocationManager lManager;

    // UI Navigation Code Variables
    private EditText emailField, passField;
    private Button logIn, reg, guest;

    // Registration Request Code
    static final int REGISTRATION_REQUEST = 2;
    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // This is the creation of the LOGIN PAGE
        requestStoregePremission();

        // Initialize Firebase Variables
        dbHelper = new DBHelper();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        // UI Navigation Code
        logIn = findViewById(R.id.loginBTN);
        reg = findViewById(R.id.registerBTN);
        guest = findViewById(R.id.guestLoginBTN);

        // LOGIN page credential fields needed to extract what user has entered
        emailField = findViewById(R.id.loginEmailText);
        passField = findViewById(R.id.loginPasswordText);

        // Guest Login
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent logIntent = new Intent(LoginActivity.this, drawer_activity.class);
                LoginActivity.this.startActivity(logIntent);

            }
        });

        // Pressing Login Code
        logIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                // Firebase Authentication
                if(emailField.getText().toString().isEmpty() && passField.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
                }
                else{
                    String inputUser = emailField.getText().toString();
                    String inputPass = passField.getText().toString();


                    mAuth = FirebaseAuth.getInstance();

                    // Clear Password
                    passField.setText("");

                    mAuth.signInWithEmailAndPassword(inputUser, inputPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                currentUser = mAuth.getCurrentUser();

                                if(currentUser.isEmailVerified()){
                                    updateUI(currentUser);
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Please verify your email address.", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    inputPass = null;
                }
            }
        });

        // Pressing Register Code
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(regIntent, REGISTRATION_REQUEST);
            }
        });
    }

    public static String filterEmailKey(String email) {
        // String str_final = "";
        String[] str = email.split("@", 2);
        String str_final = str[0];

        if (str[0].contains(".")) {
            String[] str_2 = str[0].split("\\.", 2);
            str_final = str_2[0] + str_2[1];

        }

        return str_final;
    }

    // Change UI according to user data.
    public void updateUI(FirebaseUser user){
        // If user is logged in and verified he is takes to the food screen instantly.
        if(user != null && user.isEmailVerified()){
            //testing purpose drawer_activity replaced
            startActivity(new Intent(LoginActivity.this, drawer_activity.class));
        }
        else {
            Toast.makeText(this,"There was an error updating the UI due to user not being found.", Toast.LENGTH_LONG).show();
        }
    }

    // What happens when you finish registration
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == REGISTRATION_REQUEST) {
            // Make sure the request was successful
            if (resultCode == 2) {
                String message = data.getStringExtra("MESSAGE");
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void requestStoregePremission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed for the food list that will automatically sort based on your current position.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //Toast.makeText(LoginActivity.this, "Location permission GRANTED", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this, "Location permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*private void readData(final MyCallback myCall) {

        reffUserManager.orderByKey().equalTo(filterEmailKey("Stephan.junior@gmail.com"))
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("===", String.valueOf(dataSnapshot.exists()));

                        for (DataSnapshot dataObject : dataSnapshot.getChildren()) {


                            Log.d("===", dataObject.child("first_name").getValue().toString());
                           int id = Integer.parseInt(dataObject.child("id").getValue().toString());
                           String email = dataObject.child("email").getValue().toString();
                           String city_PostalCode = dataObject.child("city_PostalCode").getValue().toString();
                           String first_name = dataObject.child("first_name").getValue().toString();
                           String last_name = dataObject.child("last_name").getValue().toString();
                           String pass = dataObject.child("password").getValue().toString();
                            String type = dataObject.child("type").getValue().toString();

                            user = new User(
                                  id,email,first_name,last_name,pass,type,city_PostalCode


                            );
                            double lon = Double.parseDouble(dataObject.child("userPoint/longitude").getValue().toString());
                            double lat = Double.parseDouble(dataObject.child("userPoint/latitude").getValue().toString());
                          user.setUserPoint(lat,lon);

                        }

                        myCall.onCallback(user);


                        // user = childSnapshot.getValue(User.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }


    interface MyCallback {
        void onCallback(User user);

    }*/
}


