package ca.georgebrown.comp3074.pocketmealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import ca.georgebrown.comp3074.pocketmealapp.ui.profile.ProfileFragment;

public class EditActivity extends AppCompatActivity {

    private ImageView btnProfileImageView;

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
        btnProfileImageView = findViewById(R.id.profileImageView);

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

        btnProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicktureIntent();
            }
        });

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

    private Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void takePicktureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            uploadImageAndSaveUri(imageBitmap);
            btnProfileImageView.setImageBitmap(imageBitmap);
        }
    }

    private  void uploadImageAndSaveUri(final Bitmap imageBitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("pics/"+LoginActivity.mAuth.getInstance().getCurrentUser().getUid());
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] image = baos.toByteArray();
        final UploadTask upload = storageRef.putBytes(image);
        final ProgressBar progressBar = findViewById(R.id.progressbar_pic);
        progressBar.setVisibility(View.VISIBLE);
        upload.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> uploadTask) {
                progressBar.setVisibility(View.INVISIBLE);
                if(uploadTask.isSuccessful()){
                    storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> urlTask) {
                            imageUri = urlTask.getResult();
                            btnProfileImageView.setImageBitmap(imageBitmap);
                        }
                    });
                }else{
                    Toast.makeText(EditActivity.this, uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
