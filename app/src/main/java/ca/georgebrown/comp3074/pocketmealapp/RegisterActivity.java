package ca.georgebrown.comp3074.pocketmealapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    // Hristo UI Navigation Code Variables
    private EditText usernameField, emailField, fnameField, lnameField, cityField, postalField, passField, passConfirmField;
    private CheckBox checkAgreement;
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Intent returnIntent = new Intent();

        // Hristo UI Navigation Code
        // Register Button on Register Page
        reg = findViewById(R.id.btnSaveEdit);
        // REGISTER page fields needed to register user
        usernameField = findViewById(R.id.txtEditUsername);
        emailField = findViewById(R.id.txtEmailEdit);
        passField = findViewById(R.id.txtPassEdit);
        passConfirmField = findViewById(R.id.txtPassEdit2);
        fnameField = findViewById(R.id.txtfNameEdit);
        lnameField = findViewById(R.id.txtLNameEdit);
        cityField = findViewById(R.id.txtcityEdit);
        checkAgreement = (CheckBox)findViewById(R.id.checkBoxAgree);
//      postalField = findViewById(R.id.txtDigitEdit);

        // User inputs must be:

        reg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                final String userName = usernameField.getText().toString();
                String userEmail = emailField.getText().toString();
                String password = passField.getText().toString();
                String passwordConfirm = passConfirmField.getText().toString();

                // Sanitized
                if(checkAgreement.isChecked() == true) {
                    if (passField.length() > 7 && password.equals(passwordConfirm)) {
                        // Creating user object from provided fields.
                        final User u = new User(userEmail,
                                fnameField.getText().toString(),
                                lnameField.getText().toString(),
                                cityField.getText().toString(),
                                ""); // postalField.getText().toString()

                        // Registration process with firebase.
                        LoginActivity.mAuth = FirebaseAuth.getInstance();
                        LoginActivity.mAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    LoginActivity.currentUser = LoginActivity.mAuth.getCurrentUser();
                                    LoginActivity.currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(userName)
                                                        .setPhotoUri(Uri.parse("https://www.hipsiti.com/uploads/default-profile.png"))
                                                        .build();

                                                LoginActivity.currentUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("PROFILE_UPDATE", "User profile updated.");
                                                        }
                                                    }
                                                });
                                                LoginActivity.dbHelper.insertUser(usernameField.getText().toString(), u);
                                                returnIntent.putExtra("MESSAGE", "Registration was successful. Please check your email for verification.");
                                                setResult(2, returnIntent);
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Your password must be 8 characters long and match the confirm password.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please accept our terms of agreement.", Toast.LENGTH_LONG).show();
                }
            }
        });

        checkAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAgreement.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "You have accepted our Terms of Agreement", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), AgreementTermsActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
