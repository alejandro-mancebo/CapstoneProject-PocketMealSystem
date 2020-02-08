package ca.georgebrown.comp3074.pocketmealapp;

public class Food {


    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    private String foodname;
        private String category;
        private String expiry_date;
        private String ingredients;


        public Food(
                        String category,
                        String expiry_date,
                        String ingredients) {

            //this.food_name = food_name;
            this.category = category;
            this.expiry_date = expiry_date;
            this.ingredients = ingredients;


        }

    public Food(String foodname,
            String category,
            String expiry_date,
            String ingredients) {

        this.foodname = foodname;
        this.category = category;
        this.expiry_date = expiry_date;
        this.ingredients = ingredients;


    }


        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getExpiry_date() {
            return expiry_date;
        }

        public void setExpiry_date(String expiry_date) {
            this.expiry_date = expiry_date;
        }

        public String getIngredients() {
            return ingredients;
        }

        public void setIngredients(String ingredients) {
            this.ingredients = ingredients;
        }


        public String toString() {
            return  "     Category :" + category + "     Expiry Date :" + expiry_date +"     Ingredient:"+ingredients + "\n";
        }

}
