package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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

        String str_FullName = getIntent().getExtras().getString("FullName");
        String str_City = getIntent().getExtras().getString("CityPro");
        final String str_EmailPro = getIntent().getExtras().getString("EmailPro");
        String str_Bio = getIntent().getExtras().getString("Bio");

        //set edit text
        txtEmailEdit.setText(str_EmailPro);
        txtCityEdit.setText(str_City);
        String arr_FullName[] = str_FullName.split("\\s+",2);
        txtfName.setText(arr_FullName[0]);
        txtlName.setText(arr_FullName[1]);
        txtBio.setText(str_Bio);

        //Pass command

        final Map<String,String> mapCommand = new HashMap<String,String>();

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapCommand.put("email",txtEmailEdit.getText().toString());
                mapCommand.put("first_name",txtfName.getText().toString());
                mapCommand.put("last_name",txtlName.getText().toString());
                mapCommand.put("bio",txtBio.getText().toString());
                String str_City_postal=txtCityEdit.getText().toString()+"_"+txtDigit.getText().toString().equals("");
                if(!txtCityEdit.getText().toString().equals("") && txtDigit.getText().toString().equals("")){
                    str_City_postal = txtCityEdit.getText().toString()+"_";
                }
                else if(txtCityEdit.getText().toString().equals("") && !txtDigit.getText().toString().equals("")){
                    str_City_postal = "_"+txtDigit.getText().toString();

                }
                 mapCommand.put("city_postalcode",str_City_postal);
                mapCommand.put("password",txtPass.getText().toString());
                for(Map.Entry mapElement : mapCommand.entrySet()){
                    if(!mapElement.getValue().toString().equals("")) {

                         if (mapElement.getKey().toString().equals("password")
                                && !txtPass.getText().toString().equals(txtPass2.getText().toString())){

                         }
                        else{
                            LoginActivity.dbHelper.updateUserInfo(str_EmailPro,mapElement.getValue().toString(),mapElement.getKey().toString());

                        }

                    }
                    else{continue;}


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
