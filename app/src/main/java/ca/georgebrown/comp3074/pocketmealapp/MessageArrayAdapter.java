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

public class MessageArrayAdapter extends ArrayAdapter<Chat> {

    int layoutId;

    public MessageArrayAdapter(@NonNull Context context, int resource, @NonNull List<Chat> objects){
        super(context,resource,objects);
        layoutId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(layoutId,null);
        }

        TextView txt = convertView.findViewById(R.id.txtVMessage);
        if(LoginActivity.currentUser.getDisplayName().equals(getItem(position).getSender()) || LoginActivity.currentUser.getDisplayName().equals(getItem(position).getReceiver())){
            txt.setText(padLeftSpaces("You : "+getItem(position).getMessage(),110));
        }
        else{
            txt.setText(getItem(position).getSender() +" : "+getItem(position).getMessage());
        }

        return convertView;
    }

    public static String padLeftSpaces(String str, int n) {
        return String.format("%1$" + n + "s", str);
    }
}

