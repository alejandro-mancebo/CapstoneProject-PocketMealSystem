package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        TextView t = findViewById(R.id.textViewDet1);

        String s = getIntent().getExtras().getString("FoodItem");
        t.setText(s);

    }
}
