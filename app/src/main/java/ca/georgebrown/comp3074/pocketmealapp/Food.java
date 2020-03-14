package ca.georgebrown.comp3074.pocketmealapp;

public class Food {

    private String foodname;
    private Double distance;
    private String category;
    private String expiry_date;
    private String ingredients;
    private String email;


    private String description, Allergies;
    private Point userPoint;

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
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
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
        return Allergies;
    }

    public void setAllergies(String allergies) {
        Allergies = allergies;
    }


    public void setUserPoint(double lat,double lon){
        userPoint.setLatitude(lat);
        userPoint.setLongitude(lon);
    }

    public Food(String category, String expiry_date, String ingredients) {

        //this.food_name = food_name;
        this.category = category;
        this.expiry_date = expiry_date;
        this.ingredients = ingredients;

        userPoint = new Point(0,0);
    }

    public Food(String foodname,
            String category,
            String expiry_date,
            String ingredients) {

        this.foodname = foodname;
        this.category = category;
        this.expiry_date = expiry_date;
        this.ingredients = ingredients;

        userPoint = new Point(0,0);
    }

    public Food(String foodname, String category, String expiry_date, String ingredients, String email) {

        this.foodname = foodname;
        this.category = category;
        this.expiry_date = expiry_date;
        this.ingredients = ingredients;
        this.email = email;
        // can check type of user before initiating the food list

        userPoint = new Point(0,0);
    }


    public String toString() {
        return  "     Category :" + category + "     Expiry Date :" + expiry_date +"     Ingredient:"+ingredients + "\n";
    }

}
