package ca.georgebrown.comp3074.pocketmealapp.ui.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ca.georgebrown.comp3074.pocketmealapp.Chat;
import ca.georgebrown.comp3074.pocketmealapp.ChatDetails;
import ca.georgebrown.comp3074.pocketmealapp.LoginActivity;
import ca.georgebrown.comp3074.pocketmealapp.R;
import ca.georgebrown.comp3074.pocketmealapp.User;

public class MessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel =
                ViewModelProviders.of(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);



        ListView li = root.findViewById(R.id.userListV);
        LoginActivity.dbHelper.getReceiverList(getActivity(),li,LoginActivity.currentUser.getDisplayName());

        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String receiverUserName = ((User)parent.getItemAtPosition(position)).getUsername();
                Intent i = new Intent(getActivity(), ChatDetails.class);
                i.putExtra("Receiver",receiverUserName);
                startActivity(i);

            }
        });
        messagesViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}