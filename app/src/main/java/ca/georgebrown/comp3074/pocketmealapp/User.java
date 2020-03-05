package ca.georgebrown.comp3074.pocketmealapp;

import android.util.Log;

import java.util.ArrayList;

public class User {

        //private int id;
        private String first_name;
/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 */

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCity_postalcode() {
        return city_postalcode;
    }

    public void setCity_postalcode(String city_postalcode) {
        this.city_postalcode = city_postalcode;
    }

    private String last_name;
        private String password;
        private String email;

        private String city_postalcode; // combination of city and 2 first characters of postal code
                                    // example torontom5

    private ArrayList<Food> foodList;
        private Point userPoint;

//if guest user click on register all those params should be fill ""
        public User( String email,
                          String first_name,
                          String last_name,
                          String password,
                    String city_PostalCode) {
            this.email = email;
            this.first_name = first_name;
            this.last_name = last_name;
            this.password = password;


            this.city_postalcode = city_PostalCode;

            // can check type of user before initiating the food list
            userPoint = new Point(0,0);

                Food f = new Food("","","");
                foodList = new ArrayList<>();


        }



    public boolean addFood(Food food1) {

            if(foodList.size() <= 20 ){ //
            foodList.add(food1);

            return true;}
            //limit the size of the food which can be added

       return false; }

    public ArrayList<Food> getFoodArrayList() {
        return foodList;
    }

    public void setFoodArrayList(ArrayList<Food> f) {
         this.foodList = f;
    }
/*
    public int searchFood(String foodname){
      for(int x=0;x<foodList.size();x++) {
          if (foodList.get(x).getCategory().equals(foodname)) {

          return x;
          }
      }

    return -1;}

    public boolean deleteFood(String foodname){
     int pos =  searchFood(foodname);
            if(pos != -1){

                foodList.remove(pos);
                return true;
            }
    return false;}
*/
//get both lon and lat
    public Point getUserPoint() {
        return userPoint;
    }

    //when user login
     public void setUserPoint(double lat,double lon){


            userPoint.setLatitude(lat);
            userPoint.setLongitude(lon);
     }


     //when user log out you need to reset their coordinates
     public void resetUserPoint(){

           userPoint.setLongitude(0);
            userPoint.setLatitude(0);
     }





}
