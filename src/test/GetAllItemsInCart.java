package test;

import controllers.CartItemController;
import helpers.Response;
import java.util.List;
import models.CartItem;

public class GetAllItemsInCart {
    protected static final CartItemController CART_ITEM_CONTROLLER = new CartItemController();

    public static void main(String[] args) {
        int cartId = 3; 
        Response<List<CartItem>> response = CART_ITEM_CONTROLLER.getAllItemsInCart(cartId);
        //ikaw bahala pano mo idi display yung cart items, if naka table ba or what.
        if (response.isSuccess()) {
            System.out.println(response.getMessage());
            List<CartItem> items = response.getData();
            for (CartItem item : items) {
                System.out.println("Cart Item ID: " + item.getCartItemId() +
                                   ", Food: " + item.getFood().getFoodName() +
                                   ", Quantity: " + item.getCartItemQuantity() +
                                   ", Price: " + item.getFood().getPrice() +
                                   ", Subtotal: " + (item.getCartItemQuantity() * item.getFood().getPrice()));
            }
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}