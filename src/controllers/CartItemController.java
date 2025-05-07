package controllers;

import java.sql.SQLException;

import models.CartItem;
import models.Cart;
import helpers.Response;
import interfaces.IOperatorsValidators;
import java.util.List;
import models.Food;
import models.Ingredient;
import models.Recipe;
import models.User;
import services.CartItemService;
import services.CartService;
import services.FoodService;
import services.RecipeService;
import services.UserService;

public class CartItemController implements IOperatorsValidators<CartItem> {
    private final CartItemService cartItemService;   
    private final CartService cartService; 
    private final FoodService foodService; 
    private final RecipeService recipeService; 
    private final UserService userService; 
    
    public CartItemController() {
        this.cartItemService = new CartItemService();   
        this.cartService = new CartService();   
        this.foodService = new FoodService();   
        this.recipeService = new RecipeService();
        this.userService = new UserService();
    }
    
    
    public Response<CartItem> addItemToCart(int cartId, int foodId, int quantity) {
        Cart c = new Cart();
        c.setCartId(cartId);
        
        Food f = new Food();
        f.setFoodId(foodId);
        
        CartItem newItem = new CartItem();
        newItem.setCart(c);
        newItem.setFood(f);
        newItem.setCartItemQuantity(quantity);
        
        Response<CartItem> validationResponse = validateCreate(newItem);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        try {
            Cart cart  = cartService.getById(cartId);
            if (cart == null) return Response.error("Cart not found with ID: " + cartId);
            
            Food food  = foodService.getById(foodId);
            if (food == null) return Response.error("Food not found with ID: " + cartId);
            
            //check if the each ingredient quantity of the food selected 
            //is sufficient paba or hindi, if succient pa then allow to order
            //pag hindi return food is currentlt not available
            Recipe recipe = recipeService.getAllIngredientsInFood(foodId);
            if(recipe == null) return Response.error("Failed to fetch recipe details for food ID: " + foodId);
            
            for(Ingredient ingredient : recipe.getIngredients()) {
                Response<String> reorderCheckResponse = new IngredientController().checkReorderNeed(ingredient.getIngredientId());
                if(!reorderCheckResponse.isSuccess()) {
                    return Response.error("Opps sorry, The food " + recipe.getFood().getFoodName() + " you selected is currently not available.");
                } 
            }
            
            //check if the food item is already in the cart
            CartItem existingItem = null;
            for (CartItem item : cartItemService.getAllItemsInCart(cartId)) {
                if (item.getFood().getFoodId() == foodId) {
                    existingItem = item;
                    break;
                }
            }
            
            if (existingItem != null) {
                // Update quantity if item exists
                int newQuantity = existingItem.getCartItemQuantity() + quantity;
                boolean updated = cartItemService.updateQuantity(cartId, foodId, newQuantity);
                if (!updated) {
                    return Response.error("Failed to update cart item quantity.");
                }
                existingItem.setCartItemQuantity(newQuantity);
            } else {
                // Insert new cart item
                boolean created = cartItemService.create(newItem);
                if (!created) {
                    return Response.error("Failed to add item to cart.");
                }
            }

            return Response.success("Item added to cart.");
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    public Response<List<CartItem>> getAllItemsInCart(int cartId) {
        try {
            List<CartItem> items = cartItemService.getAllItemsInCart(cartId);

            if (items == null || items.isEmpty()) {
                return Response.error("No items found in cart with ID: " + cartId);
            }

            return Response.success("Cart items retrieved successfully", items);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    public Response<CartItem> updateCartItemQuantity(int cartItemId, int quantity) {
        try {            
            CartItem item = cartItemService.getById(cartItemId);
            if (item == null) return Response.error("Item not found with ID: " + cartItemId);
                        
            int currentQuantity = item.getCartItemQuantity();
            int newQuantity = currentQuantity + quantity;
            
            item.setCartItemQuantity(quantity);
            
            if (newQuantity == 0) {
                boolean deleted = cartItemService.delete(cartItemId);
                if (!deleted) {
                    return Response.error("Failed to remove cart item.");
                }
                return Response.success("Cart item removed, because quantity becomes 0", null);
            } else {
                boolean updated = cartItemService.updateQuantity(cartItemId, newQuantity);
                if (!updated) {
                    return Response.error("Failed to update cart item quantity.");
                }
                
                return Response.success("Cart item quantity updated.", item);
            }
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    public Response<CartItem> getCartItemById(int id) {
        try {
            CartItem item = cartItemService.getById(id);

            if (item == null) {
                return Response.error("Item not found with ID: " + id);
            }

            return Response.success("Item retrieved successfully", item);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    public Response<Cart> getCartByUserId(int userId) {
        try {
            Cart cart = cartService.getByUserId(userId);
            if (cart == null) {
                return Response.error("Item not found with ID: " + userId);
            }
            
//            Cart data = new Cart();
//            data.setCartId(cart.getCartId());
//            data.set

            return Response.success("Item retrieved successfully", cart);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    
    public Response<Boolean> checkoutCartItem(int userId, int riderId) {
        try {
            User user = userService.getById(userId);
            if (user == null) return Response.error("User not found with ID: " + userId);

            boolean checked = cartItemService.checkout(userId, riderId);

            return (checked)
                ? Response.success("Checkout successfully")
                : Response.error("Failed to checkout item in cart.");
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    public Response<Boolean> removeItemFromCart(int itemId) {
        Response<Boolean> validationResponse = validateDelete(itemId);
        if (!validationResponse.isSuccess()) {
            return Response.error("Validation failed: " + validationResponse.getMessage());
        }

        try {
            CartItem item = cartItemService.getById(itemId);
            if (item == null) return Response.error("Igtem not found with ID: " + itemId);

            boolean isDeleted = cartItemService.delete(itemId);

            return (isDeleted)
                ? Response.success("Item deleted successfully")
                : Response.error("Failed to delete item in cart.");
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    @Override
    public Response<CartItem> validateCreate(CartItem item) {
        if (item.getCart().getCartId() <= 0) {
            return Response.error("Invalid cart id");
        }
        if (item.getFood().getFoodId() <= 0) {
            return Response.error("Invalid food id");
        }
        if (item.getCartItemQuantity() < 0) {
            return Response.error("Quantity cannot be negative.");
        }
        
        return Response.success("Validation successful", item);
    }

    @Override
    public Response<CartItem> validateUpdate(CartItem item) {
        if (item.getCart() == null || item.getCart().getCartId() <= 0) {
            return Response.error("Invalid cart ID.");
        }
        if (item.getFood() == null || item.getFood().getFoodId() <= 0) {
            return Response.error("Invalid food ID.");
        }
        if (item.getCartItemQuantity() < 0) {
            return Response.error("Quantity cannot be negative.");
        }
        return Response.success("Validation passed", item);
    }

    @Override
    public Response<Boolean> validateDelete(int id) {
        if (id <= 0) {
            return Response.error("Invalid ID!");
        }
        
        return Response.success("Validation successful", true);
    }

    
}
