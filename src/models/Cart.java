package models;

public class Cart extends User {
    private int id;
    
    public Cart() {}
    
    public Cart(int cartId) {
        this.id = cartId;
    }
    
    public int getCartId() {
        return id;
    }
    
    public void setCartId(int cartId) {
        this.id = cartId;
    }
    
}
