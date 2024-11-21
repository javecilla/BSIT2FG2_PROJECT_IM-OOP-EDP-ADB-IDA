package models;

public class SalesDetails {
    private int quantity;
    private Food food;
    private Category category;
    private Sale sale;
    private Customer customer;
    private User user;
    private UserInfo userInfo;
    
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
