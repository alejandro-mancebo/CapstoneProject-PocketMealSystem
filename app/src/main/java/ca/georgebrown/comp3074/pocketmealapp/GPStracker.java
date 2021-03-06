package ca.georgebrown.comp3074.pocketmealapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GPStracker implements LocationListener{

    Context context;

    public GPStracker(Context c) {
        context = c;
    }

    public Location getLocation(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "GPS permissions not granted.", Toast.LENGTH_SHORT ).show();
            return  null;
        }
        //LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //boolean isGPSEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isGPSEnable = LoginActivity.lManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnable){
            LoginActivity.lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000,10, this);
            Location l = LoginActivity.lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        } else {
            Toast.makeText(context,"Please enable GPS on your device.", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        getLocation();
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
