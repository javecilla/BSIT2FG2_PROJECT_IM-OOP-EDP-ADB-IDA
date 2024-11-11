package models;

public class Food {
    private String foodId;
    private String foodName;
    private double price;
    private String categoryId;  
    
    private String categoryName;  
    
    public Food() {}
    
    public Food(String foodId, String foodName, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }
    
    public Food(String foodId, String foodName, double price, String categoryName) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.categoryName = categoryName;
    }
    
    public Food(String foodId, String foodName, double price, String categoryId, String categoryName) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    
    // Getters and setters for all fields
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
