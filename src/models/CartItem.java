package models;

/**
 * The CartItem class represents an individual item in the cart, 
 * including its food details, price, and quantity. 
 */
public class CartItem {
    private int foodId;
    private String foodName;
    private double foodPrice;
    private int quantity;

    public CartItem() {}
    
    public CartItem(int foodId, String foodName, double foodPrice, int quantity) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        setQuantity(quantity);  // Ensures that quantity can't be negative
    }

    // Getters and setters
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

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return foodPrice * quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to increase must be positive.");
        }
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to decrease must be positive.");
        }
        if (this.quantity - amount < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than zero.");
        }
        this.quantity -= amount;
    }
}
