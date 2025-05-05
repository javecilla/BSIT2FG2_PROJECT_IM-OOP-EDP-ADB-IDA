package test;

import controllers.CartItemController;
import helpers.Response;
import models.CartItem;

public class RemoveItemFromCart {
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();
    
    public static void main(String[] args) {
        int cartItemId = 2;                
        Response<Boolean> response = CART_ITEM_CONTROLLER.removeItemFromCart(cartItemId);
        if (response.isSuccess()) {
            System.out.println(response.getMessage());
        } else {
            System.out.println(response.getMessage());
        }
    }
    
}



