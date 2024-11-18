package controllers;

import models.Cart;
import models.CartItem;
import models.Food;
import helpers.Response;

public class CartController {
    private Cart cart = new Cart();

    public Response<Cart> addToCart(int foodId, String foodName, double foodPrice, int quantity) {
        if (quantity <= 0) {
            return Response.error("Quantity must be greater than 0.");
        }

        // Check if the food item is already in the cart
        boolean itemExists = false;
        for (CartItem item : cart.getItems()) {
            if (item.getFoodId() == foodId) {
                // Increase the quantity if the item already exists
                item.increaseQuantity(quantity);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            // Add a new item if it doesn't exist
            cart.addItem(new CartItem(foodId, foodName, foodPrice, quantity));
        }

        return Response.success("Item added to cart.", cart);
    }


    public Response<Cart> removeFromCart(int foodId) {
        CartItem itemToRemove = null;

        // Find the item by ID
        for (CartItem item : cart.getItems()) {
            if (item.getFoodId() == foodId) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            cart.removeItem(itemToRemove);
            return Response.success("Item removed from cart.", cart);
        } else {
            return Response.error("Item not found in the cart.");
        }
    }

    public Response<Cart> updateQuantity(int foodId, int quantity) {
        if (quantity <= 0) {
            return Response.error("Quantity must be greater than 0.");
        }

        for (CartItem item : cart.getItems()) {
            if (item.getFoodId() == foodId) {
                item.setQuantity(quantity);
                return Response.success("Quantity updated.", cart);
            }
        }

        return Response.error("Item not found in the cart.");
    }

    public Response<Cart> viewCart() {
        return Response.success("Cart retrieved successfully.", cart);
    }
}
