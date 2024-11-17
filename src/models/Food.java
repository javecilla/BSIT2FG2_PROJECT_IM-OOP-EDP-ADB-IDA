package models;

public class Food extends Category {
    private int id;
    private String name;
    private double price;
    
    public Food() {
        super();
    }
    
    public Food(int foodId, String foodName, double price) {
        super();
        this.id = foodId;
        this.name = foodName;
        this.price = price;
    }
    
    public Food(int foodId, String foodName, double price, Category category) {
        super(category.getCategoryId(), category.getCategoryName());
        this.id = foodId;
        this.name = foodName;
        this.price = price;
    }
     
    // Getters and setters for all fields
    public int getFoodId() {
        return id;
    }
    
    public void setFoodId(int foodId) {
        this.id = foodId;
    }
    
    public String getFoodName() {
        return name;
    }
    
    public void setFoodName(String foodName) {
        this.name = foodName;
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
                          id, 
                          name, 
                          price, 
                          super.getCategoryName()
        );
    }
}