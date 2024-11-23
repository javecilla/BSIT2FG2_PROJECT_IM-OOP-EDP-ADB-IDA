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
    private Customer customer;      // Customer involved in the sale.
    
    // Constructors
    public SalesDetails() {}
    
    public SalesDetails(int quantity) {
        this.quantity = quantity;
    }
    
    public SalesDetails(Food food, Sale sale, int quantity) {
        this.food = food;
        this.sale = sale;
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
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    } 
}
