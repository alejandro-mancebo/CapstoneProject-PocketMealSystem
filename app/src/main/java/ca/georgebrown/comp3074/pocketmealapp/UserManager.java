package ca.georgebrown.comp3074.pocketmealapp;

import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> userList = new ArrayList<User>();
    //have user list



    public void addUser(User u1){


        //no duplicate user, insert sort before add;
        userList.add(u1);
    }

  //  private void insertionSortModified(User u1){


  //      int rightPos = userList.size()-1;


 //           while(rightPos>=0 && userList.get(rightPos).getEmail().compareTo(u1.getEmail())<0){


   //     }

  //  }


    public int binarySearchByName(String key){

/*  int hi = userList.size();
        int lo = 0;
        int mid;
        while(hi >= lo){
           mid = (hi+lo)/2;
            if(userList.get(mid).getEmail().equals(key)){

                return mid;
            }
           else if(userList.get(mid).getEmail().compareTo(key)>0){
              hi = mid-1;

           }

           else{

               lo = mid+1;
            }

        }
*/
    return -1;

    }

public boolean deleteUser(String email){
        int pos = binarySearchByName(email);
        if(pos != -1){

            userList.remove(pos);
            return true;
        }

return false;}
  //binary search by name to search user other solution create user object same as the one you search then use .contains()

    public ArrayList<User> getUserArrayList(){

        return userList;
    }

//delete function

  //  public User getUser(String email) {
  //      for (int i = 0; i < USERS.size(); i++) {
  //          if (USERS.get(i).getEmail().equals(email))
  //              return USERS.get(i);
  //      }
   //     return null;
   // }






//        public double getDistance(User user) {
//            return getDistance(user.latitud, user.longitud);
//        }

//        public double getDistance(String latitud, String longitud) {
//
//            double latitud1 = Double.parseDouble(latitud);
//            double longitud1 = Double.parseDouble(longitud);
//            double latitud2 = Double.parseDouble(this.latitud);
//            double longitud2 = Double.parseDouble(this.longitud);
//
//            double latDistance = (latitud2 - latitud1) * (latitud2 - latitud1);
//            double lonDistance = (longitud2 - longitud1) * (longitud2 - longitud1);
//            return Math.sqrt(latDistance + lonDistance);
//        }


        public String toString() {
            return "";
        }

}
