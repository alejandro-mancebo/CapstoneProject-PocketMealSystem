package ca.georgebrown.comp3074.pocketmealapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Timer;
import java.util.TimerTask;

public class ChatDetails extends AppCompatActivity {
     ListView messages;
    String str_Receiver;
    Timer timer = new Timer();
    Context c = this;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
         messages = findViewById(R.id.MessageListV);
        Button btnSendMess = findViewById(R.id.btnSend);
        final EditText message = findViewById(R.id.editTextSend);


         str_Receiver = getIntent().getExtras().getString("Receiver");

       // LoginActivity.dbHelper.getMessages(LoginActivity.currentUser.getDisplayName(),str_Receiver,messages,c);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LoginActivity.dbHelper.getMessages(LoginActivity.currentUser.getDisplayName(),str_Receiver,messages,c);

            }
        },0,900);


        btnSendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!message.getText().toString().equals("")){


                Chat chat = new Chat(LoginActivity.currentUser.getDisplayName(),message.getText().toString(),str_Receiver);
                LoginActivity.dbHelper.sendChat(chat);
                message.setText("");
                //  LoginActivity.dbHelper.getMessages(LoginActivity.currentUser.getDisplayName(),str_Receiver,messages,c);

              }
            }
        });


    }

    public  void onResume() {
        super.onResume();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LoginActivity.dbHelper.getMessages(LoginActivity.currentUser.getDisplayName(),str_Receiver,messages,c);

            }
        },0,1000);
    }
    public void onPause() {
        super.onPause();

        timer.cancel();
    }

public void onStop() {
    super.onStop();
    timer.cancel();
}
}
