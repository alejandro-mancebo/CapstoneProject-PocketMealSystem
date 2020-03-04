# CapstoneProject-PocketMealSystem
Instruction about how to use db functions
Almost every function for the database are done. 2 functions and the chat system need to be implemented.
- insertUser(final String username, final User u) takes the key which is the email split by @
     example: email@gmail.com username = email.
     for that use MainActivity.filterEmailKey(Put the email here it will return the username)
     User object is needed in the registration you will ask the user to put his city and the 2 characters of 
     his postal code in lower case extremely important. For the rest of their info check the parameter needed.
     
 - updateUserInfo(final String username, final String newData, final String columnToBeUpdated) 
    new data is the new value. Check the Firebase database columnToBeUpdated = city_postalcode,email,first_name,last_name,password
    case sensitive.
    
 - updateUserPoint(final String username, final double lon, final double lat) 
     longitude, latitude
     

- deleteUser(final String username) 
   if user exits 
   
- addFood(final String username, final String foodname, final Food f1)
  Food is an object.
  
  
- updateFood(final String username, final String newData,final String columnToBeUpdated ,final String Foodname)
     newData => check updateUserInfo , columnToBeUpdated = category,expiry_date,ingredients

- deleteFood(final String username, final String foodname) 
    clear to understand
- getSpecificArrayList(String username, final ListView listView, final Context context)
    //
- getDonorFoodList(String username, final ListView lView, final Context context) 
   //
