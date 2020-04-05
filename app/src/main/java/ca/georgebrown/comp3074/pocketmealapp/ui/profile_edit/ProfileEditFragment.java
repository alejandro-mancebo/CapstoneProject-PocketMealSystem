package ca.georgebrown.comp3074.pocketmealapp.ui.profile_edit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.georgebrown.comp3074.pocketmealapp.R;

public class ProfileEditFragment extends Fragment {

    private ProfileEditViewModel profileEditViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileEditViewModel =
                ViewModelProviders.of(this).get(ProfileEditViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        final TextView textView = root.findViewById(R.id.text_profile_edit);

        profileEditViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
