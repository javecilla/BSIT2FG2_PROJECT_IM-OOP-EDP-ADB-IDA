package test;

import controllers.CartController;
import controllers.CartItemController;
import core.Session;
import helpers.Response;
import models.Cart;
import models.CartItem;

public class AddItemToCart {
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();
    //protected static final CartController CART_CONTROLLER = new CartController();
    
    public static void main(String[] args) {
        
        //getCartByUserId
        int cartId = 3;
//        Response<Cart> cart = CART_ITEM_CONTROLLER.getCartByUserId(Session.getLoggedInUser().getUserId());
//       if(cart.isSuccess()){
//           cartId = cart.getData().getCartId();
//       }    
        
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
