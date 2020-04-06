package ca.georgebrown.comp3074.pocketmealapp.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import ca.georgebrown.comp3074.pocketmealapp.Chat;
import ca.georgebrown.comp3074.pocketmealapp.ChatDetails;
import ca.georgebrown.comp3074.pocketmealapp.EditActivity;
import ca.georgebrown.comp3074.pocketmealapp.FoodDetailActivity;
import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;
import ca.georgebrown.comp3074.pocketmealapp.R;
import ca.georgebrown.comp3074.pocketmealapp.ui.food_details.FoodDetailsFragment;
import ca.georgebrown.comp3074.pocketmealapp.ui.profile_edit.ProfileEditFragment;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView textView = root.findViewById(R.id.text_gallery);
        final TextView txtUsername = root.findViewById(R.id.textVUsernamePro);
        final TextView txtCity = root.findViewById(R.id.textVCity);
        final TextView txtFullName = root.findViewById(R.id.txtVFullName);
        final TextView txtBio = root.findViewById(R.id.txtVprofileBio);
        final TextView txtEmail = root.findViewById(R.id.textVEmailPro);
//        final TextView digit = root.findViewById(R.id.textVDigitPro);
        final ImageView imageView = root.findViewById(R.id.imageView2);

        ImageButton btnEdit = root.findViewById(R.id.EditBtn);
        Button btnChat = root.findViewById(R.id.btnChat);

        String str_Username = "";
        if(getArguments() != null){

            str_Username = getArguments().getString("Username");

        };

//change it to the parameter username passed
        if(str_Username.equals("")){
            btnChat.setVisibility(View.GONE);

            if(LoginActivity.currentUser != null){
                txtUsername.setText(LoginActivity.currentUser.getDisplayName());
                LoginActivity.dbHelper.setProfileInfo(LoginActivity.currentUser.getDisplayName(),txtCity,txtFullName,txtEmail,txtBio, imageView); // ,digit
            }

            btnEdit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   // Create fragment and give it an argument specifying the article it should show
                   ProfileEditFragment newFragment = new ProfileEditFragment();
                   Bundle args = new Bundle();
                   args.putString("FullName", txtFullName.getText().toString());
                   args.putString("CityPro", txtCity.getText().toString());
                   args.putString("EmailPro", txtEmail.getText().toString());
//                   args.putString("digitPro", digit.getText().toString());
                   args.putString("Bio", txtBio.getText().toString());
                   newFragment.setArguments(args);

                   FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                   // Replace whatever is in the fragment_container view with this fragment,
                   // and add the transaction to the back stack so the user can navigate back
                   transaction.replace(R.id.nav_host_fragment, newFragment);
                   transaction.addToBackStack(null);

                   // Commit the transaction
                   transaction.commit();

//                   Intent i = new Intent(getActivity(), EditActivity.class);
//
//                   i.putExtra("FullName",txtFullName.getText().toString());
//                   i.putExtra("CityPro",txtCity.getText().toString());
//                   i.putExtra("EmailPro",txtEmail.getText().toString());
//                   i.putExtra("digitPro",digit.getText().toString());
//                   i.putExtra("Bio",txtBio.getText());
//
//                   startActivity(i);

               }
            });
       }
       else{
           btnEdit.setVisibility(View.GONE);
           txtUsername.setText(str_Username);
           LoginActivity.dbHelper.setProfileInfo(str_Username,txtCity,txtFullName,txtEmail,txtBio, imageView); // ,digit
           //use chat btn here
            final String finalStr_Username = str_Username;
            btnChat.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent i = new Intent(getActivity(), ChatDetails.class);
                   i.putExtra("Receiver", finalStr_Username);
                   startActivity(i);
               }
            });
       }

        profileViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}