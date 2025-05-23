package controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import core.Cart;
import core.CartItem;
import core.Session;
import models.Recipe;
import models.Ingredient;
import models.Sale;
import models.SalesDetails;
//import models.Customer;
import models.Courier;
import services.*;
import enums.*;
import helpers.*;
import models.Food;
import models.User;

public class CartController {
    private final RecipeService recipeService;
    //private final IngredientService ingredientService;
    private final SaleService saleService;
    private final SalesDetailsService salesDetailsService;
    private final CourierService courierService;
    private final Cart cart;
    
    public CartController() {
        this.recipeService = new RecipeService();
        //this.ingredientService = new IngredientService();
        this.saleService = new SaleService();
        this.salesDetailsService = new SalesDetailsService();
        this.courierService = new CourierService();
        this.cart = new Cart();
    }
    
    public Response<Cart> addToCart(int foodId, String foodName, double foodPrice, int quantity) {
        if(quantity <= 0) {
            return Response.error("Quantity must be greater than 0.");
        }
        
        try {
            //check first if the each ingredient quantity of the food selected 
            //is sufficient paba or hindi, if succient pa then allow to order
            //pag hindi return food is currentlt not available
            Recipe recipe = recipeService.getAllIngredientsInFood(foodId);
            if(recipe == null) return Response.error("Failed to fetch recipe details for food ID: " + foodId);
            
            for(Ingredient ingredient : recipe.getIngredients()) {
                Response<String> reorderCheckResponse = new IngredientController().checkReorderNeed(ingredient.getIngredientId());
                if(!reorderCheckResponse.isSuccess()) {
                    //System.out.println(reorderCheckResponse.getMessage());
                    return Response.error("Opps sorry, The food " + foodName + " you selected is currently not available.");
                } 
            }
        } catch(SQLException e) {
            return Response.error("Something went wrong during checkout: " + e.getMessage());
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
            //get all rider that are avaiable
            List<Courier> couriers = courierService.getByStatus(Text.capitalizeFirstLetterInString(CourierStatus.AVAILABLE.name()));
            if(couriers == null || couriers.isEmpty()) {
                throw new SQLException("No couriers found");
            }
            
            // Randomly select a rider
            Random random = new Random();
            int randomIndex = random.nextInt(couriers.size());  //get a random index from 0 to couriers.size() - 1
            int selectedRiderId = couriers.get(randomIndex).getRiderId();
            Courier courier = new Courier();
            courier.setRiderId(selectedRiderId);
            
            User customer = Session.getLoggedInUser();//Session.getLoggedInCustomer();
            //Create a sale record (once for the entire checkout)
            Sale sale = new Sale(
                Date.getCurrentDate(), 
                cart.getTotalAmount(),
                cart.getPaymentAmount()
            );
            sale.setCustomer(customer);
            sale.setCourier(courier);
            

            boolean isSaleCreated = saleService.create(sale);
            if(!isSaleCreated) {
                return Response.error("Failed to create a new sale record.");
            }
            
            boolean isCourierStatusUpdated = courierService.updateStatus(
               courier.getRiderId(),
               Text.capitalizeFirstLetterInString(CourierStatus.UNAVAILABLE.name())    
            );
            if(!isCourierStatusUpdated) {
                return Response.error("Failed to update courier.");
            }

            //iterate over cart items
            for(CartItem item : cart.getItems()) {
           
                //get all ingredients information  base on the food id
                Recipe recipe = recipeService.getAllIngredientsInFood(item.getFoodId());
                if(recipe == null) {
                    return Response.error("Failed to fetch recipe details for food ID: " + item.getFoodId());
                }
                
                //get the recipe quantity record and multiply it by the customer's order quantity
                int totalQuantity = recipe.getRecipeQuantity() * item.getQuantity();
                
                //System.out.println("totalQuantity to reduce: " + totalQuantity + " for food " + item.getFoodName());
                //loop all the ingredients na bumubuo sa food
                //kase kada order sa food mag babawas ng quantity
                //per ingridients
                for(Ingredient ingredient : recipe.getIngredients()) {
                    // Calculate the new ingredient stock change
                    int quantityChange = -totalQuantity; // Negative because we’re reducing stock
                    //System.out.println("HALA: " + quantityChange);
                    if (totalQuantity <= 0 ) return Response.error("Quantity change cannot be negative or zero.");
     
                    Response<Ingredient> updateStocksResponse = new IngredientController().updateQuantity(
                            ingredient.getIngredientId(), quantityChange);
                    if(!updateStocksResponse.isSuccess()) {
                        return Response.error("Error updating ingredient: " + updateStocksResponse.getMessage());
                    } 
                }
            
                //insert sales details for each item
                SalesDetails salesDetails = new SalesDetails(item.getQuantity());
                salesDetails.setFood(new Food(
                    item.getFoodId(), 
                    item.getFoodName(), 
                    item.getFoodPrice()
                ));
                salesDetails.setSale(sale);
                boolean isSalesDetailsCreated = salesDetailsService.create(salesDetails);
                if(!isSalesDetailsCreated) {
                    return Response.error("Failed to create sales details for food ID: " + item.getFoodId());
                }
            }
            
            //clear the cart after successful ng checkout
            cart.clearCart();
            
            return Response.success("Your order is successfully checked out! Thank you for your order.", null);
        } catch (SQLException e) {
            
            return Response.error("Something went wrong during checkout: " + e.getMessage());
        }
    }
    
    public Response<List<SalesDetails>> getOrderReports() {
        try {
            List<SalesDetails> salesDetals = salesDetailsService.getSalesDetails();
            
            if(salesDetals == null || salesDetals.isEmpty()) {
                return Response.success("No foods found", Collections.emptyList());
            }
            
            return Response.success("Sales details retrieved successfully", salesDetals);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

}
