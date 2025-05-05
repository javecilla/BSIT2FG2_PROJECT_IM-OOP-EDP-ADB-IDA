package test;

import controllers.CartItemController;
import helpers.Response;
import models.CartItem;

public class UpdateCartItemQuantity {
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();
    
    public static void main(String[] args) {
        int cartItemId = 1;
        //pag magbabawas ng quantity make it negative, positive pag increase
        int decreaseQuantity = -3;
        int increaseQuantity = 2;
                
        Response<CartItem> updateResponse = CART_ITEM_CONTROLLER.updateCartItemQuantity(
            cartItemId,
            decreaseQuantity
        );
        if (updateResponse.isSuccess()) {
            System.out.println(updateResponse.getMessage());
        } else {
            System.out.println(updateResponse.getMessage());
        }
    }
    
}

