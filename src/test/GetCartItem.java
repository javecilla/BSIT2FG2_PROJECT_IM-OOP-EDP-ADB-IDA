package test;

import models.CartItem; 
import controllers.CartItemController;
import helpers.Response;

public class GetCartItem {
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();
    
    public static void main(String[] args) {    
        int userId = 1;
        Response<CartItem> response = CART_ITEM_CONTROLLER.getCartItemById(userId);
        if (response.isSuccess()) {
            CartItem item = response.getData();
            
            System.out.println("Cart Item ID: " + item.getCartItemId());
            System.out.println("Cart ID: " + item.getCart().getCartId());
            System.out.println("Food Id: " + item.getFood().getFoodId());
            System.out.println("Food Name: " + item.getFood().getFoodName());
            System.out.println("Food Price: " + item.getFood().getPrice());
            System.out.println("Cart Item ID: " + item.getCartItemQuantity());
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}
