package test;

import controllers.CartItemController;
import helpers.Response;
import models.CartItem;

public class AddItemToCart {
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();
    
    public static void main(String[] args) {
        int cartId = 3; 
        int foodId = 7; 
        int quantity = 3;
        
        Response<CartItem> response = CART_ITEM_CONTROLLER.addItemToCart(cartId, foodId, quantity);
        if (response.isSuccess()) {
            System.out.println(response.getMessage());            
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}
