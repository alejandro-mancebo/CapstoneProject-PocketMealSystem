package ca.georgebrown.comp3074.pocketmealapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserArrayAdapter extends ArrayAdapter<User> {

        int layoutId;
public UserArrayAdapter(@NonNull Context context, int resource, @NonNull List<User> objects){
        super(context,resource,objects);
        layoutId=resource;
        }
@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if(convertView==null){
        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView=inflater.inflate(layoutId,null);}

        TextView txt = convertView.findViewById(R.id.textVUserChat);

        txt.setText(getItem(position).getUsername());

        return convertView;
        }
}
