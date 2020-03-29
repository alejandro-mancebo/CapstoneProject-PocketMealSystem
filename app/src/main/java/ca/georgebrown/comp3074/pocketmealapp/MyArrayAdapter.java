package ca.georgebrown.comp3074.pocketmealapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Food> {

    int layoutId;
    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List<Food> objects) {
        super(context, resource, objects);
        layoutId = resource;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutId,null);}

        if(layoutId == R.layout.food_item_design) {
            TextView txt = convertView.findViewById(R.id.textVFoodname);

            int km = (int) Math.round(getItem(position).getDistance());

            txt.setText(getItem(position).getFoodname() + "                Distance :" + km + " km");
        }


        else{
            TextView txtFoodName = convertView.findViewById(R.id.textVMyFoodName);
            txtFoodName.setText(getItem(position).getFoodname());
            ImageView btnEditFood = convertView.findViewById(R.id.imageVEditFood);
            ImageView btnDelete = convertView.findViewById(R.id.imageVDel);

          //  final View ConvertView = convertView;
            btnEditFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(),MyFoodDetailActivity.class);

                    i.putExtra("FoodType",getItem(position).getFoodname());
                    i.putExtra("FoodUsername",LoginActivity.currentUser.getDisplayName());
                    //  String str_Ingre = getIntent().getExtras().getString("FoodIngre");
                    i.putExtra("Expiry",getItem(position).getExpiry_date());
                    getContext().startActivity(i);

                }
            });


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.dbHelper.deleteFood(LoginActivity.currentUser.getDisplayName(),getItem(position).getFoodname());
                     LoginActivity.dbHelper.getDonorFoodList(LoginActivity.currentUser.getDisplayName(),MyFoodListActivity.myfoodList,getContext());
                    //call function again..
                }
            });

        }

        return convertView;
    }

private void setEmptyText(TextView txt){
        if(txt.getText().equals("")){

            txt.setVisibility(View.GONE);

        }

}

private void fillEmptyText(TextView t, String value){

    t.setText(value);

}
}
