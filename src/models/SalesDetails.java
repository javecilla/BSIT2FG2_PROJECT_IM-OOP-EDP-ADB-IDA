package models;
/**
 * Represents the `SalesDetails` table in the database.
 * Stores detailed information about items in a sale, including the quantity, associated food, category,
 * and references to the customer, user, and their user information involved in the sale.
 */
public class SalesDetails {
    private int quantity;           // Quantity of food items sold.
    private Food food;              // Food item associated with the sale.
    private Sale sale;              // The sale associated with this sales detail.
 

    // Constructors
    public SalesDetails() {}
    
    public SalesDetails(int quantity) {
        this.quantity = quantity;
    }
    
    // Getters and setters for all fields
    public int getItemQuantity() {
        return quantity;
    }
    
    public void setItemQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Food getFood() {
        return food;
    }
    
    public void setFood(Food food) {
        this.food = food;
    }
    
    public Sale getSale() {
        return sale;
    }
    
    public void setSale(Sale sale) {
        this.sale = sale;
    } 
}
