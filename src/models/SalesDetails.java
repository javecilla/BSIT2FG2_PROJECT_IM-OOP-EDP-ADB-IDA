package models;

public class SalesDetails {
    private int quantity;
    private Food food;
    private Sale sale;
     
    public SalesDetails() {}
    
    public SalesDetails(int quantity) {
        this.quantity = quantity;
    }
    
    public SalesDetails(Food food, Sale sale, int quantity) {
        this.food = food;
        this.sale = sale;
        this.quantity = quantity;
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
    
    public Sale getSale() {
        return sale;
    }
    
    public void setSale(Sale sale) {
        this.sale = sale;
    } 
}
