package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegistActivity extends AppCompatActivity {

    // Hristo UI Navigation Code Variables
    private EditText emailField, fnameField, lnameField, postalField, passField, passConfirmField;
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

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
        // Sanitized
        // Made into a object
        // Placed into firebase

        // Lastly Open The Confirm Page
    }
}
