package test;

import controllers.CartItemController;
import helpers.Response;
import models.CartItem;

public class CheckoutCartItems {
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();
    
    public static void main(String[] args) {
        int userId = 1004; 
        
        //dito sa rider randomized nalang gawin mo 
        //gamitin mo COURIER_CONTROLLER.getCourierByStatus("Available")
        //para i retrieved lahat ng rider na avail lang, tapos random selection nalang
        //once na nag checkout
        
        int riderId = 17; 
        
        Response<Boolean> response = CART_ITEM_CONTROLLER.checkoutCartItem(userId, riderId);
        if (response.isSuccess()) {
            System.out.println(response.getMessage());            
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}
