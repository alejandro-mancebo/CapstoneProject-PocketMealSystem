package ca.georgebrown.comp3074.pocketmealapp;

public class Point {

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    private double longitude,latitude;

   public Point(double lon, double lat){

       longitude = lon;
       latitude = lat;

   }
}
