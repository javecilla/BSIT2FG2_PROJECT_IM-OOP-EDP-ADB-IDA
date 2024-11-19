package controllers;

import java.sql.SQLException;
import java.util.List;

import models.Cart;
import models.CartItem;
import models.Food;
import models.Recipe;
import models.Customer;
import models.Ingredient;
import models.Recipe;
import services.RecipeService;
import services.IngredientService;
import helpers.Response;
import helpers.Date;

public class CartController {
    private final Cart cart;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    
    public CartController() {
        this.recipeService = new RecipeService();
        this.ingredientService = new IngredientService();
        this.cart = new Cart();
    }
    
    public Response<Cart> addToCart(int foodId, String foodName, double foodPrice, int quantity) {
        if(quantity <= 0) {
            return Response.error("Quantity must be greater than 0.");
        }

        // Check if the food item is already in the cart
        boolean itemExists = false;
        for(CartItem item : cart.getItems()) {
            if (item.getFoodId() == foodId) {
                // Increase the quantity if the item already exists
                item.increaseQuantity(quantity);
                itemExists = true;
                break;
            }
        }

        if(!itemExists) {
            // Add a new item if it doesn't exist
            cart.addItem(new CartItem(foodId, foodName, foodPrice, quantity));
        }

        return Response.success("Item added to cart.", cart);
    }
    


    public Response<Cart> removeFromCart(int foodId) {
        CartItem itemToRemove = null;

        // Find the item by ID
        for(CartItem item : cart.getItems()) {
            if (item.getFoodId() == foodId) {
                itemToRemove = item;
                break;
            }
        }

        if(itemToRemove != null) {
            cart.removeItem(itemToRemove);
            return Response.success("Item removed from cart.", cart);
        } else {
            return Response.error("Item not found in the cart.");
        }
    }

    public Response<Cart> updateQuantity(int foodId, int quantity) {
        if(quantity <= 0) {
            return Response.error("Quantity must be greater than 0.");
        }

        for(CartItem item : cart.getItems()) {
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
    
    public Response<Cart> checkOutOrder() {
        // Check if the cart is empty
        if(cart == null || cart.getItems().isEmpty()) {
            return Response.error("The cart is empty. Please add items before checking out.");
        }
        
        try {
            Customer customer = new Customer();
            
            for(CartItem item : cart.getItems()) {
                //get all ingredients information  base on the food id
                Recipe recipe = recipeService.getById(item.getFoodId());
                recipe.getRecipeQuantity();
                //1 * 2 = 2;
                int newQuantity = recipe.getRecipeQuantity() * item.getQuantity();
                
                //loop all the ingredients na bumubuo sa food
                //kase kada order sa food mag babawas ng quantity
                //per ingridients
                List<Ingredient> ingredients = recipe.getIngredients();
                for(Ingredient ingredient : ingredients) {
                    //update the table ingredients base on the ingredient id
                    ingredientService.updateQuantity(ingredient.getIngredientId(), newQuantity);
                }  
               
                /*
                #insert these data in table SALE [Sales_Date, Customer_ID, Net_Total]
                
                Sale sale = new Sale(
                    Date.getCurrentDate(),
                    customer.getCustomerId(),
                    cart.getTotalAmount()
                ); 
                saleService.create(sale);
                
                
                #insert these data in table SALE_DETAILS [Food_ID, Sales_ID, Item_Quantity]                
                salesDetailsService.create(
                    new SalesDetails(
                        item.getFoodId(),
                        sale.getSaleId(),
                        item.getQuantity()
                    )
                );
                */  
            }
            
            
            return Response.success("Your order is successfully checked out! Thankyou for your order.", null);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }

       
        
    }

}
