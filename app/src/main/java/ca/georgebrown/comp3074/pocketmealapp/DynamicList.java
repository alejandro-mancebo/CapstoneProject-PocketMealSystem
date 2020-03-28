package ca.georgebrown.comp3074.pocketmealapp;

import java.util.ArrayList;
import java.util.Collections;



public class DynamicList {

  public static ArrayList<Food> foodList = new ArrayList<>();
    public DynamicList(){


    }
  public static void insert(Food f){
//insertion sort modify...
          Food rightPos = f;
        int leftPos = foodList.size()-1;

        while(leftPos>=0 && foodList.get(leftPos).getDistance() > rightPos.getDistance()){

            if(foodList.size()== leftPos+1){

                foodList.add(leftPos+1,foodList.get(leftPos));
            }
          else{   foodList.set(leftPos+1,foodList.get(leftPos)); }
           leftPos--;

        }

        if(foodList.size() == leftPos+1) {
            foodList.add(leftPos + 1, rightPos);
        }
        else{ foodList.set(leftPos + 1, rightPos);}

  }



}
