package models;

public class Food extends Category {
    private int foodId;
    private String foodName;
    private double price;
    
    public Food() {
        super();
    }
    
    public Food(int foodId, String foodName, double price) {
        super();
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }
    
    public Food(int foodId, String foodName, double price, Category category) {
        super(category.getCategoryId(), category.getCategoryName());
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
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
 
    @Override
    public void displayInfo() {
        System.out.printf("%-10d %-30s $%-9.2f %-20s%n", 
                          foodId, 
                          foodName, 
                          price, 
                          super.getCategoryName()
        );
    }
}