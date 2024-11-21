package models;
/**
 * Represents the `SalesDetails` table in the database.
 * Stores detailed information about items in a sale, including the quantity, associated food, category,
 * and references to the customer, user, and their user information involved in the sale.
 */
public class SalesDetails {
    private int quantity;           // Quantity of food items sold.
    private Food food;              // Food item associated with the sale.
    private Category category;      // Category of the food item.
    private Sale sale;              // The sale associated with this sales detail.
    private Customer customer;      // Customer involved in the sale.
    private User user;              // User (client) processing the sale.
    private UserInfo userInfo;      // User information related to the user processing the sale.
    
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
    
    public SalesDetails(Food food, Sale sale, int quantity, Category category, Customer customer, User user, UserInfo userInfo) {
        this.food = food;
        this.sale = sale;
        this.quantity = quantity;
        this.category = category;
        this.customer = customer;
        this.user = user;
        this.userInfo = userInfo;
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
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    } 
    
    public UserInfo getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
