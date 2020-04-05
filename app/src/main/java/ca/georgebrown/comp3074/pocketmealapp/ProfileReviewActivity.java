package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileReviewActivity extends AppCompatActivity {

    // UI Navigation Code Variables
    private TextView txtProfileDescriptionContent, txtProfileReviewContent;
    private EditText editTxtLeaveReview;
    private Button btnSubmitReview, btnViewListFood, btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_review);

        txtProfileDescriptionContent = (TextView) findViewById(R.id.text_profile_description_content);
        txtProfileReviewContent = (TextView) findViewById(R.id.text_profile_review_content);
        editTxtLeaveReview = findViewById(R.id.editText_leave_review);

        btnSubmitReview = findViewById(R.id.button_submit_review);
        btnViewListFood = findViewById(R.id.button_view_list_food);
        btnChat = findViewById(R.id.button_chat);


        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnViewListFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
