package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import ca.georgebrown.comp3074.pocketmealapp.ui.profile.ProfileFragment;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final EditText txtEmailEdit = findViewById(R.id.txtEmailEdit);
        final EditText txtfName = findViewById(R.id.txtfNameEdit);
        final EditText txtlName = findViewById(R.id.txtLNameEdit);
        final EditText txtCityEdit = findViewById(R.id.txtcityEdit);
        final EditText txtDigit = findViewById(R.id.txtDigitEdit);
        final EditText txtPass = findViewById(R.id.txtPassEdit);
        final EditText txtPass2 = findViewById(R.id.txtPassEdit2);
        final EditText txtBio = findViewById(R.id.editBioText);
        Button btnSaveEdit = findViewById(R.id.btnSaveEdit);

        final String str_FullName = getIntent().getExtras().getString("FullName");
        final String str_City = getIntent().getExtras().getString("CityPro");
        final String str_EmailPro = getIntent().getExtras().getString("EmailPro");
        final String str_Bio = getIntent().getExtras().getString("Bio");
        final String str_Digit = getIntent().getExtras().getString("digitPro");
        //set edit text
        txtEmailEdit.setText(str_EmailPro);
        txtCityEdit.setText(str_City);
        txtDigit.setText(str_Digit);
        String arr_FullName[] = str_FullName.split("\\s+",2);
        final String fN = arr_FullName[0];
        final String lN = arr_FullName[1];
        txtfName.setText(fN);
        txtlName.setText(lN);
        txtBio.setText(str_Bio);

        //Pass command

        final Map<String,String> mapCommand = new HashMap<>();

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapCommand.put("email",txtEmailEdit.getText().toString());
                mapCommand.put("first_name",txtfName.getText().toString());
                mapCommand.put("last_name",txtlName.getText().toString());
                mapCommand.put("bio",txtBio.getText().toString());
                mapCommand.put("city_postalcode",txtCityEdit.getText().toString()+"_"+txtDigit.getText().toString());
                mapCommand.put("password",txtPass.getText().toString());


                for(Map.Entry mapElement : mapCommand.entrySet()){

                    Log.d("Whoo",mapElement.toString());

                    if(mapElement.getValue().toString().isEmpty()
                            || mapElement.getValue().toString().equals(str_EmailPro)
                            || mapElement.getValue().toString().equals(str_Bio)
                            || mapElement.getValue().equals(fN)
                            || mapElement.getValue().equals(lN)
                            || mapElement.getValue().equals(str_City+"_"+str_Digit)
                            || mapElement.getKey().toString().equals("password")
                             && !mapElement.getValue().toString().equals(txtPass2.getText().toString())
                            || mapElement.getValue().toString().equals("")
                    ) {

                        continue;
                    }

                    else{

                        LoginActivity.dbHelper.updateUserInfo(LoginActivity.currentUser.getDisplayName(), mapElement.getValue().toString(), mapElement.getKey().toString());

                    }


                }

               //-------------------go to profile fragment, do not use finish() so the page can be refresh and have new value-------------


                // Intent i = new Intent(EditActivity.this,ProfileFragment.class);

              //  ProfileFragment fragment = new ProfileFragment();
              //  FragmentManager fragmentManager = getSupportFragmentManager();
              //  fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


    }
}
