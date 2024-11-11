package models;

public class Food {
    private int foodId;
    private String foodName;
    private double price;
    private Category category;
    
    public Food() {}
    
    public Food(int foodId, String foodName, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }
    
    public Food(int foodId, String foodName, double price, Category category) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.category  = category ;
    }

    // Getters and setters for all fields
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }

    public void displayInfo() {
        System.out.printf("%-10d %-30s $%-9.2f %-20s%n", 
                          foodId, 
                          foodName, 
                          price, 
                          category.getCategoryName());
    }

}
