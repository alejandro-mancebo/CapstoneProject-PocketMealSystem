package ca.georgebrown.comp3074.pocketmealapp;

import java.util.ArrayList;

public class User {

    // Variables
    private String first_name;
    private String last_name;
    private String email;



    private String username;
    // combination of city and 2 first characters of postal code example torontom5
    private String city_postalcode;
    private ArrayList<Food> foodList;

    // Getters and Setters
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCity_postalcode() {
        return city_postalcode;
    }
    public void setCity_postalcode(String city_postalcode) { this.city_postalcode = city_postalcode; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }


    public ArrayList<Food> getFoodArrayList() { return foodList; }
    public void setFoodArrayList(ArrayList<Food> f) { this.foodList = f; }

    // Constructor
    public User( String email,
          String first_name,
          String last_name,
          String city, String postalCode) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.city_postalcode = city.toLowerCase() + postalCode.toLowerCase();

        Food f = new Food("","","");
        foodList = new ArrayList<>();
    }

    // Behaviours
    public boolean addFood(Food food1) {
            if(foodList.size() <= 20 ){
                foodList.add(food1);
                return true;
            }
            //limit the size of the food which can be added
       return false;
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
}
