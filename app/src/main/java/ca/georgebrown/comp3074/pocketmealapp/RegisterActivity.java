package ca.georgebrown.comp3074.pocketmealapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    // Hristo UI Navigation Code Variables
    private EditText emailField, fnameField, lnameField, postalField, passField, passConfirmField;
    private Button reg;

    // Firebase Varriables
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Hristo UI Navigation Code
        // Register Button on Register Page
        reg = findViewById(R.id.registerFinalBTN);
        // REGISTER page fields needed to register user
        emailField = findViewById(R.id.emailEdit);
        passField = findViewById(R.id.passEdit);
        passConfirmField = findViewById(R.id.passEdit2);
        fnameField = findViewById(R.id.fNameEdit);
        lnameField = findViewById(R.id.lNameEdit);
        postalField = findViewById(R.id.postalEdit);

        // User inputs must be:

        reg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String userName = emailField.getText().toString();
                String password = passField.getText().toString();
                String passwordConfirm = passConfirmField.getText().toString();

                // Sanitized
                if (passField.length() > 7 && password.equals(passwordConfirm) ) {

                    SecurePasswordStorage passManager = new SecurePasswordStorage();

                    try {
                        // String hashPassword = passManager.signUp(userName, password);
                        User u = new User(
                            userName,
                            fnameField.getText().toString(),
                            lnameField.getText().toString(),
                            postalField.getText().toString().toLowerCase());
                            /*hashPassword,*/

                        auth = FirebaseAuth.getInstance();

                        // This code is used for email send with firebase.
                        auth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    user = auth.getCurrentUser();
                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "Registration was successful. Please check your email for verification.", Toast.LENGTH_LONG).show();
                                                }
                                                else {
                                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                    });
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        // For testing purposes
                        LoginActivity.dbHelper.insertUser(LoginActivity.filterEmailKey(userName),u);
                        Log.d("Registration", " > user registered ");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("Registration", " > user not registered ");
                }
            }
        });



        // Made into a object
        // Placed into firebase

        // Lastly Open The Confirm Page
    }
}
