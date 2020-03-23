package ca.georgebrown.comp3074.pocketmealapp;

public class Food {

    private String category;
    private String username, email, foodname, post_date, expiry_date, description, allergies;
    private Point userPoint;
    private Double distance;

    public String getUsername() {
        return username; }

    public void setUsername(String username) {
        this.username = username; }
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public void setUserPoint(Point userPoint) {
        this.userPoint = userPoint;
    }
    public String getFoodname() {
        return foodname;
    }
    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getExpiry_date() { return expiry_date; }
    public void setExpiry_date(String expiry_date) { this.expiry_date = expiry_date; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Point getUserPoint() { return userPoint; }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAllergies() {
        return allergies;
    }
    public void setAllergies(String allergies) {
        allergies = allergies;
    }

    public void setUserPoint(double lat,double lon){
        userPoint.setLatitude(lat);
        userPoint.setLongitude(lon);
    }

    public Food(String category, String expiry_date) {

        //this.food_name = food_name;
        this.category = category;
        this.expiry_date = expiry_date;

        userPoint = new Point(0,0);
    }

    public Food(String foodname,
            String category,
            String expiry_date) {

        this.foodname = foodname;
        this.category = category;
        this.expiry_date = expiry_date;

        userPoint = new Point(0,0);
    }

    public Food(String foodname, String category, String expiry_date, String email) {

        this.foodname = foodname;
        this.category = category;
        this.expiry_date = expiry_date;
        this.email = email;
        // can check type of user before initiating the food list

        userPoint = new Point(0,0);
    }

    public String toString() {
        return  "     Category :" + category + "     Expiry Date :" + expiry_date + "\n";
    }

}
