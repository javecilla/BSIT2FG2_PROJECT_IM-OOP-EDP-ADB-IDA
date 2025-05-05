package models;

public class CartItem {
    private int id;
    private Cart cart;    
    private Food food;                    
    private int itemQuantity;               
    
    public CartItem() {}
    
    public CartItem(int id, Cart cart, Food food, int itemQuantity) {
        this.id = id;
        this.cart = cart;
        this.food = food;
        this.itemQuantity = itemQuantity;
    }
    
    public int getCartItemId() {
        return id;
    }
    
    public void setCartItemId(int cartId) {
        this.id = cartId;
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    public Food getFood() {
        return food;
    }
    
    public void setFood(Food food) {
        this.food = food;
    }
    
    public int getCartItemQuantity() {
        return itemQuantity;
    }
    
    public void setCartItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
