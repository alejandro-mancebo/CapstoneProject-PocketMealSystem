package ca.georgebrown.comp3074.pocketmealapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutId,null);}

        TextView txt = convertView.findViewById(R.id.textVFoodname);

        int km = (int) Math.round(getItem(position).getDistance());

       txt.setText(getItem(position).getFoodname() + "                Distance :"+ km +" km");

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
