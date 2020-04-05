package ca.georgebrown.comp3074.pocketmealapp.ui.profile_edit;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import ca.georgebrown.comp3074.pocketmealapp.EditActivity;
import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;
import ca.georgebrown.comp3074.pocketmealapp.R;
import ca.georgebrown.comp3074.pocketmealapp.Upload;

import static android.app.Activity.RESULT_OK;
import static ca.georgebrown.comp3074.pocketmealapp.LoginActivity.mDatabaseRef;

public class ProfileEditFragment extends Fragment {

    private ProfileEditViewModel profileEditViewModel;

    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int PICK_IMAGE_REQUEST = 110;

    private Uri mImageUri;
    private StorageTask mUploadTask;

    private StorageReference pStorageRef;

    private ImageView btnProfileImageView;
    private ProgressBar progressBar;

    private EditText txtEmailEdit;
    private EditText txtfName;
    private EditText txtlName;
    private EditText txtCityEdit;
    private EditText txtDigit;
    private EditText txtPass;
    private EditText txtPass2;
    private EditText txtBio;

    private EditText mEditTextFileName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileEditViewModel =
                ViewModelProviders.of(this).get(ProfileEditViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        final TextView textView = root.findViewById(R.id.text_profile_edit);


        pStorageRef = FirebaseStorage.getInstance().getReference("pics");
        btnProfileImageView = root.findViewById(R.id.profileImageView);
        progressBar = root.findViewById(R.id.progressbar_pic);

        txtEmailEdit = root.findViewById(R.id.txtEmailEdit);
        txtfName = root.findViewById(R.id.txtfNameEdit);
        txtlName = root.findViewById(R.id.txtLNameEdit);
        txtCityEdit = root.findViewById(R.id.txtcityEdit);
        txtDigit = root.findViewById(R.id.txtDigitEdit);
        txtPass = root.findViewById(R.id.txtPassEdit);
        txtPass2 = root.findViewById(R.id.txtPassEdit2);
        txtBio = root.findViewById(R.id.editBioText);
        Button btnSelectPic = root.findViewById(R.id.btnSelectPic);
        Button btnSaveEdit = root.findViewById(R.id.btnSaveEdit);

        final String str_FullName = getArguments().getString("FullName");
        final String str_City = getArguments().getString("CityPro");
        final String str_EmailPro = getArguments().getString("EmailPro");
        final String str_Bio = getArguments().getString("Bio");
        final String str_Digit = getArguments().getString("digitPro");

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
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    //takePicktureIntent();
                }
            }
        });

        btnSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    //openFileChooser();
                }
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
                            || mapElement.getValue().toString().equals("")){
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

        profileEditViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    // Initiates the camera for a picture.
//    private void takePicktureIntent(){
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    // Waits for result ether from camera or file chooser.
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//            String path = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, LoginActivity.currentUser.getUid(), null);
//            mImageUri = Uri.parse(path);
//
//            uploadFile();
//            btnProfileImageView.setImageBitmap(imageBitmap);
//        }
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null) {
//            mImageUri = data.getData();
//            Picasso.get().load(mImageUri).into(btnProfileImageView);
//            uploadFile();
//        }
//    }

    // Opens file chooser.
//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }

    // Grabs the file extension.
//    private String getFileExtension(Uri uri) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }

//    private void uploadFile() {
//        if (mImageUri != null) {
//            StorageReference fileReference = pStorageRef.child("pics/").child(LoginActivity.mAuth.getInstance().getCurrentUser().getUid() + "." + getFileExtension(mImageUri));
//
//            mUploadTask = fileReference.putFile(mImageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    progressBar.setProgress(0);
//                                }
//                            }, 500);
//
//                            Toast.makeText(EditActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
//                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
//                                    taskSnapshot.getUploadSessionUri().toString()); // .getDownloadUrl() insted of .getUploadSessionUri()
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(EditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            progressBar.setProgress((int) progress);
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
//        }
//    }
}
