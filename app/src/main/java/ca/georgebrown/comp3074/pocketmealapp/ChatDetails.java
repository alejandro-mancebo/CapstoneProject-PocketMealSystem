package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        final ListView messages = findViewById(R.id.MessageListV);
        Button btnSendMess = findViewById(R.id.btnSend);
        final EditText message = findViewById(R.id.editTextSend);
        final String str_Receiver = getIntent().getExtras().getString("Receiver");
        LoginActivity.dbHelper.getMessages(LoginActivity.currentUser.getDisplayName(),str_Receiver,messages,this);

        btnSendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!message.getText().toString().equals("")){
                Chat chat = new Chat(LoginActivity.currentUser.getDisplayName(),message.getText().toString(),str_Receiver);
                LoginActivity.dbHelper.sendChat(chat);
               message.setText("");
                  LoginActivity.dbHelper.getMessages(LoginActivity.currentUser.getDisplayName(),str_Receiver,messages,v.getContext());

              }
            }
        });
    }
}
