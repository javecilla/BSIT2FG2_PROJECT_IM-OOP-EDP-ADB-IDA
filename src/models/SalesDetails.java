package models;
/**
 * Represents the `SalesDetails` table in the database.
 * The SalesDetails class provides detailed information about items included in a sale, such as the 
 * quantity of each item, the associated Food object, and the specific Sale it is part of. This class 
 * encapsulates the transactional details of each sale while maintaining relationships with the relevant 
 * entities like Customer and Food.
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
