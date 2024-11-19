package views;

import models.User;
import models.Ingredient;
import models.Category;
import models.Food;
import controllers.UserController;
import controllers.IngredientController;
import controllers.CategoryController;
import controllers.FoodController;
import controllers.CartController;
import helpers.Response;
import enums.UserRoles;

import java.util.Scanner;
import java.util.List;
import models.Cart;
import models.CartItem;

public class RunnerTest {
    protected static final Scanner SCANNER = new Scanner(System.in);
    protected static final UserController LOGIN_CONTROLLER = new UserController();
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    protected static final CategoryController CATEGORY_CONTROLLER = new CategoryController();
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    protected static final CartController CART_CONTROLLER = new CartController();
 
    private static int categoriesCounter = 0;
    private static int foodsCounter = 0;
    public static void main(String[] args) {
        int userChoice = 0;
        String userRole = "";
        boolean startPanel = true;
        boolean loggedIn = false;
                    
        do {
            // Show the main start panel (role selection)
            if (startPanel) {
                System.out.println("[1] User");
                System.out.println("[2] Admin");
                System.out.println("[3] Exit\n");
                System.out.print("Enter your choice: ");
                userChoice = SCANNER.nextInt();

                switch (userChoice) {
                    case 1: // CLIENT
                        userRole = UserRoles.CLIENT.name();
                        break;
                    case 2: // ADMIN
                        userRole = UserRoles.ADMIN.name();
                        break;
                    case 3: // Exit
                        System.out.println("Exiting system...");
                        return; 
                    default:
                        System.out.println("Invalid choice, please select again.");
                }
            }
            
            boolean loginSuccess = false;

            while (!loginSuccess) {
                System.out.println("\nLogin to your account");

                System.out.print("Username: ");
                String usernameInput = SCANNER.next().trim();

                System.out.print("Password: ");
                String passwordInput = SCANNER.next().trim();

                System.out.println("Logging in...\n");
                // Attempt login with username, password, and role
                Response<User> loginResponse = LOGIN_CONTROLLER.loginUser(usernameInput, passwordInput, userRole);
                if (loginResponse.isSuccess()) {
                    System.out.println(loginResponse.getMessage());
                    //JOptionPane.showMessageDialog(null,loginResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                    User user = loginResponse.getData();
                    System.out.println("\nWelcome back, " + user.getFullName());
                    loggedIn = true;
                    loginSuccess = true;  // Exit the loop if login is successful
                    
                    // If login is successful, show dashboard based on user role
                    if (loggedIn) { 
                        if (UserRoles.ADMIN.name().equals(userRole)) {
                            adminDashboard(user);
                        } else if (UserRoles.CLIENT.name().equals(userRole)){
                            clientDashboard(user); 
                        }

                        loggedIn = false; // Logout after navigating the dashboard to return to the role selection screen
                        startPanel = true;
                    }
                } else {
                    System.out.println("Failed to login! Invalid credentials");
                    System.out.println("Please try again.\n");
                }
            }
        
            

        } while (true);
    }
    
    /*
      ==========================================================================
      PANEL/s FOR ADMIN
      ==========================================================================
    */
    
    private static void adminDashboard(User user) {
        int choice;
        do {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("[1] Manage Stocks");
            System.out.println("[2] Others admin menu...");
            System.out.println("[3] Profile");
            System.out.println("[4] Log Out\n");
            System.out.print("Enter your choice: ");
            choice = SCANNER.nextInt();

            switch (choice) {
                case 1:
                    adminManageStock();
                    break;
                case 2:
                    System.out.println("add mo nalang mga other panel for admin, apply mo lang similar logic");
                    break;
                case 3:
                    System.out.println("Your Profile");
                    showProfile(user);
                    break;

                case 4:
                    System.out.println("Logging out...");
                    return;  // Exit admin dashboard and go back to role selection
                default:
                    System.out.println("Invalid choice. Returning to dashboard.");
            }
        } while (true); 
    }

private static void adminManageStock() {
    int choice;
    boolean showLowStock = false; // Flag to track if we're showing low stock
    boolean showAllStocks = false; // Flag to track if we're showing all stocks
    boolean showOneStocks = false; // Flag to track if we're showing one item's full info
    do {
        if (showLowStock) {
            // Show low stock items after user selects Show All Low Stocks
            showLowStock();

            // After showing low stock, return to showing all items, unless the user selects otherwise
            showLowStock = false; 
            showAllStocks = true;
            showOneStocks = false;
        } else if (showOneStocks) {
            // Show one ingredient's full information
            showOneItemInformation();

            showLowStock = false; 
            showAllStocks = false;
            showOneStocks = false;
        } else if (showAllStocks) {
            // Show the full list of ingredients
            System.out.println("\n--- Manage Stock ---");
            showAllStocks();
            

            showLowStock = false; 
            showOneStocks = false;
        }

        // Ask admin what action they want to do
        System.out.println("\n--- Select Action ---");
        System.out.println("[1] Show All");
        System.out.println("[2] Show All (Low Stocks)");
        System.out.println("[3] Update (Stocks)");
        System.out.println("[4] Update (Re-order Points)");
        System.out.println("[5] Show One (All Information)");
        System.out.println("[6] Check One (Reorder or Not)");
        System.out.println("[7] Back\n");
        System.out.print("Enter your choice: ");
        choice = SCANNER.nextInt();

        // Handle the user's choice
        switch (choice) {
            case 1:
                showLowStock = false;  
                showAllStocks = true; 
                break;
            case 2:
                showLowStock = true;   
                showAllStocks = false; 
                showOneStocks = false;
                break;
            case 3:
                updateStocks();   
                break;
            case 4:
                updateReorderPoints();  
                break;
            case 5:
                showOneStocks = true;    
                showLowStock = false;   
                showAllStocks = false;
                break;
            case 6:
                checkForReorder();   //pag mag check if need na ba mag reoder sa isang item or not
                break;    
            case 7:
                return;  // Back to admin dashboard
            default:
                System.out.println("Invalid choice. Returning to manage stock menu.");
        }
    } while (true);
}


    private static void showLowStock() {
        // Fetch ingredients with low stock
        Response<List<Ingredient>> ingredientsWithLowStocksResponse = INGREDIENT_CONTROLLER.getAllIngredientsLowStocks();
        if (ingredientsWithLowStocksResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsWithLowStocksResponse.getData();
            displayIngredientItems(ingredients);
        } else {
            System.out.println("Error: " + ingredientsWithLowStocksResponse.getMessage());
        }
    }

    private static void showOneItemInformation() {
        System.out.print("Enter ingredient id: ");
        String ingredientIDInput = SCANNER.next().trim();

        Response<Ingredient> ingredientsResponse = INGREDIENT_CONTROLLER.getIngredientById(Integer.parseInt(ingredientIDInput));
        if (ingredientsResponse.isSuccess()) {
            Ingredient ingredient = ingredientsResponse.getData();
            System.out.println("Ingredient ID: " + ingredient.getIngredientId());
            System.out.println("Ingredient Name: " + ingredient.getIngredientName());
            System.out.println("Current Stock: " + ingredient.getQuantity());
            System.out.println("Re-order Points: " + ingredient.getReorderPoint());
            System.out.println("\nSupplier Info: ");
            System.out.println("\tSupplier ID: " + ingredient.getSupplierId());
            System.out.println("\tFull Name: " + ingredient.getSupplierName());
            System.out.println("\tContact Number: " + ingredient.getContactNumber());
            System.out.println("\tAddress: " + ingredient.getAddress());
        } else {
            System.out.println("Error retrieving ingredient: " + ingredientsResponse.getMessage());
        }
    }

    private static void showAllStocks() {
        // Fetch all ingredients (not just low stock)
        Response<List<Ingredient>> ingredientsResponse = INGREDIENT_CONTROLLER.getAllIngredients();
        if (ingredientsResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsResponse.getData();
            displayIngredientItems(ingredients);
        } else {
            System.out.println("Error: " + ingredientsResponse.getMessage());
        }
    }
    
    private static void checkForReorder() {
        System.out.print("Enter ingredient id: ");
        String ingredientIDInput = SCANNER.next().trim();
        
        
        Response<String> reorderCheckResponse = INGREDIENT_CONTROLLER.checkReorderNeed(Integer.parseInt(ingredientIDInput));
        if(reorderCheckResponse.isSuccess()) {
            System.out.println(reorderCheckResponse.getMessage());
        } else {
            System.out.println("Error checking ingredient: " + reorderCheckResponse.getMessage());
        }
    }

    private static void updateStocks() {
        System.out.print("Enter ingredient id: ");
        String ingredientIDInput = SCANNER.next().trim();

        System.out.print("Enter quantity: ");
        String ingredientQuantityInput = SCANNER.next().trim();
        
        Response<Ingredient> updateStocksResponse = INGREDIENT_CONTROLLER.updateQuantity(
            Integer.parseInt(ingredientIDInput),
            Integer.parseInt(ingredientQuantityInput)
        );
        if(updateStocksResponse.isSuccess()) {
            System.out.println(updateStocksResponse.getMessage());
        } else {
            System.out.println("Error updating ingredient: " + updateStocksResponse.getMessage());
        }
    }
    
    private static void updateReorderPoints() {
        System.out.print("Enter ingredient id: ");
        String ingredientIDInput = SCANNER.next().trim();

        System.out.print("Enter reorder points: ");
        String ingredientReorderPointsInput = SCANNER.next().trim();
        
        Response<Ingredient> updateReorderPointsResponse = INGREDIENT_CONTROLLER.updateReorderPoints(
            Integer.parseInt(ingredientIDInput),
            Integer.parseInt(ingredientReorderPointsInput)
        );
        if(updateReorderPointsResponse.isSuccess()) {
            System.out.println(updateReorderPointsResponse.getMessage());
        } else {
            System.out.println("Error updating ingredient: " + updateReorderPointsResponse.getMessage());
        }
    }

    private static void displayIngredientItems(List<Ingredient> ingredients) {
        System.out.printf("\n%-10s %-30s %-10s %-20s%n", "Ingredient ID", "Name", "Current Stock", "Reorder Point");
        for (Ingredient ingredient : ingredients) {
            System.out.printf("%-10s %-30s $%-10s %-20s%n", 
                ingredient.getIngredientId(), 
                ingredient.getIngredientName(), 
                ingredient.getQuantity(), 
                ingredient.getReorderPoint()
            );
        }
        System.out.println("Total Records: " + ingredients.size());
    }

    
    /*
      ==========================================================================
      PANEL/s FOR CLIENT / CUSTOMER
      ==========================================================================
    */
    private static void clientDashboard(User user) {
        int choice;
        do {
            System.out.println("\n--- Client Dashboard ---");
            System.out.println("[1] Go Shop");
            System.out.println("[2] My Cart");
            System.out.println("[3] Profile");
            System.out.println("[4] Log Out\n");
            System.out.print("Enter your choice: ");
            choice = SCANNER.nextInt();

            switch (choice) {
                case 1:
                    //System.out.println("show yung mga list of categories");
                    showCategoryMenu();
                    break;
                case 2:
                    showCart();
                    break;
                case 3:
                    System.out.println("Your Profile");
                    showProfile(user);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;  // Exit client dashboard and go back to role selection
                default:
                    System.out.println("Invalid choice. Returning to dashboard.");
            }
        } while (true); 
    }
    
    private static void showProfile(User user) {
        System.out.println("Full Name: " + user.getFullName());
        System.out.println("Address: " + user.getFullAddress());
    }
    
    private static void showCart() {
        System.out.println("\n--- Your Cart ---");
        Response<Cart> cartResponse = CART_CONTROLLER.viewCart();

        if (cartResponse.isSuccess()) {
            Cart cart = cartResponse.getData();

            if (cart.getItems().isEmpty()) {
                System.out.println("Your cart is empty.");
            } else {
                int itemNumber = 1;
                System.out.printf("%-5s %-20s %-10s %-10s %-10s\n", "No.", "Food Name", "Price", "Qty", "Total");
                System.out.println("-----------------------------------------------------------");

                for (CartItem item : cart.getItems()) {
                    double totalPrice = item.getTotalPrice();
                    System.out.printf("%-5d %-20s %-10.2f %-10d %-10.2f\n", 
                        itemNumber++, 
                        item.getFoodName(), 
                        item.getFoodPrice(), 
                        item.getQuantity(), 
                        totalPrice
                    );
                }

                System.out.println("-----------------------------------------------------------");
                System.out.printf("%-35s %-10.2f\n", "Total Amount:", cart.getTotalAmount());
            }
        } else {
            System.out.println("Error: " + cartResponse.getMessage());
            return; // Exit if there's an issue fetching the cart
        }

        // Provide options to the user
        int choice;
        do {
            System.out.println("\nOptions:");
            System.out.println("[1] Edit Order");
            System.out.println("[2] Remove Order");
            System.out.println("[3] Checkout Order");
            System.out.println("[4] Back");
            System.out.print("Enter your choice: ");
            choice = SCANNER.nextInt();

            if(choice == 1) {
                System.out.println("edit order");
                //CART_CONTROLLER.updateQuantity(id, newQuantity);
            } else if (choice == 2) {
                System.out.println("remove order");
                //CART_CONTROLLER.removeFromCart(id);
            } else if (choice == 3) {
                //checkout order
                Response<Cart> cartOrderReponse = CART_CONTROLLER.checkOutOrder();
                if (cartOrderReponse.isSuccess()) {
                    System.out.println(cartOrderReponse.getMessage());
                } else {
                    System.out.println("Error: " + cartOrderReponse.getMessage());
                }
            } else if(choice == 4) {
                 return; // Back to the previous menu
            } else {
                System.out.println("Invalid choice. Please select again.");
            }
        } while (true);
    }

    
    private static void showFoodMenu(int categoryId) {
        int choice;
        do {
            displayFoodListsByCategory(categoryId); 
            System.out.print("Enter your choice: ");
            choice = SCANNER.nextInt();

            // Check if the choice is valid for categories (1 to foodsCounter)
            if (choice > 0 && choice <= foodsCounter) {
                Response<Food> foodResponse = FOOD_CONTROLLER.getFoodById(choice);
                if (foodResponse.isSuccess()) {
                    Food selectedFood = foodResponse.getData();
                    promptQuantityOrder(selectedFood.getFoodId(), selectedFood.getFoodName(), selectedFood.getPrice());
                } else {
                    System.out.println("Error: " + foodResponse.getMessage());
                }
            } else if (choice == (foodsCounter + 1)) { 
                return;  // Go back to the category menu
            } else {
                System.out.println("Invalid choice. Please select again.");
            }
        } while (true);
    }
    
    private static void promptQuantityOrder(int foodId, String foodName, double foodPrice) {
        int quantity;
        do {
            System.out.println("\n--- Client Dashboard ---");
            System.out.println("Food Name: " + foodName);
            System.out.println("Food Price: " + foodPrice);
            System.out.println("-------------------------\n");
            System.out.println("[0] Back");
            System.out.print("Enter quantity: ");
            quantity = SCANNER.nextInt();

            if (quantity > 0) {
                Response<Cart> addToCartResponse = CART_CONTROLLER.addToCart(foodId, foodName, foodPrice, quantity);
                if (addToCartResponse.isSuccess()) {
                    System.out.println("Item added to cart successfully.");
                } else {
                    System.out.println("Error: " + addToCartResponse.getMessage());
                }
            } else if (quantity == 0) { 
                return;  // Go back to the food menu
            } else {
                System.out.println("Invalid choice. Please select again.");
            }
        } while (true);
    }       
    
    private static void displayFoodListsByCategory(int categoryId) {
        foodsCounter = 0;  
        
        Response<List<Food>> foodsResponse = FOOD_CONTROLLER.getFoodsByCategory(categoryId);
        if (foodsResponse.isSuccess()) {
            List<Food> foods = foodsResponse.getData();
            System.out.println("\n--- Select Food ---");
            for (Food food : foods) {
                System.out.println("[" + food.getFoodId() + "] \t" + food.getFoodName() + "\t\t" + food.getPrice());
                foodsCounter++;
            }
 
            System.out.println("[" + (foods.size() + 1) + "] Back\n");
        } else {
            System.out.println("Error: " + foodsResponse.getMessage());
        }
    }
    
    private static void showCategoryMenu() {
        int choice;
        do {
            displayCategories(); 
            System.out.print("Enter your choice: ");
            choice = SCANNER.nextInt();

            // Check if the choice is valid for categories (1 to categoriesCounter)
            if (choice > 0 && choice <= categoriesCounter) {
                showFoodMenu(choice);
            } else if (choice == (categoriesCounter + 1)) { 
                return;  // Go back to the client dashboard
            } else {
                System.out.println("Invalid choice. Please select again.");
            }
        } while (true);
    }
    
    private static void displayCategories() {
        categoriesCounter = 0;  
        
        Response<List<Category>> categoriesResponse = CATEGORY_CONTROLLER.getAllCategories();
        if (categoriesResponse.isSuccess()) {
            List<Category> categories = categoriesResponse.getData();
            System.out.println("\n--- Select Category ---");
            for (Category category : categories) {
                System.out.println("[" + category.getCategoryId() + "] " + category.getCategoryName());
                categoriesCounter++;
            }
 
            System.out.println("[" + (categories.size() + 1) + "] Back\n");
        } else {
            System.out.println("Error: " + categoriesResponse.getMessage());
        }
    }
}
