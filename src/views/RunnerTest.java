package views;

import models.User;
import models.Ingredient;
import controllers.UserController;
import controllers.IngredientController;
import helpers.Response;
import enums.UserRoles;

import java.util.Scanner;
import java.util.List;

public class RunnerTest {
    protected static final Scanner SCANNER = new Scanner(System.in);
    protected static final UserController LOGIN_CONTROLLER = new UserController();
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
 
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
                        loggedIn = login(userRole);
                        break;
                    case 2: // ADMIN
                        userRole = UserRoles.ADMIN.name();
                        loggedIn = login(userRole);
                        break;
                    case 3: // Exit
                        System.out.println("Exiting system...");
                        return; 
                    default:
                        System.out.println("Invalid choice, please select again.");
                }
            }

            // If login is successful, show dashboard based on user role
            if (loggedIn) { 
                if (UserRoles.ADMIN.name().equals(userRole)) {
                    adminDashboard();
                } else if (UserRoles.CLIENT.name().equals(userRole)){
                    userDashboard(); // Implement user dashboard here (similar to Admin Dashboard)
                }

                loggedIn = false; // Logout after navigating the dashboard to return to the role selection screen
                startPanel = true;
            }

        } while (true);
    }

    private static boolean login(String userRole) {
        boolean loggedIn = false;
        boolean loginSuccess = false;

        while (!loginSuccess) {
            System.out.println("\nLogin to your account");

            System.out.print("Username: ");
            String usernameInput = SCANNER.next().trim();

            System.out.print("Password: ");
            String passwordInput = SCANNER.next().trim();

            // Attempt login with username, password, and role
            Response<User> loginResponse = LOGIN_CONTROLLER.loginUser(usernameInput, passwordInput, userRole);
            if (loginResponse.isSuccess()) {
                System.out.println(loginResponse.getMessage());
                User user = loginResponse.getData();
                System.out.println("\nWelcome back, " + user.getFullName());
                loggedIn = true;
                loginSuccess = true;  // Exit the loop if login is successful
            } else {
                System.out.println("Failed to login! Invalid credentials");
                System.out.println("Please try again.\n");
            }
        }

        return loggedIn;
    }

    private static void adminDashboard() {
        int choice;
        do {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("[1] Manage Stocks");
            System.out.println("[2] Manage Food");
            System.out.println("[4] Log Out\n");
            System.out.print("Enter your choice: ");
            choice = SCANNER.nextInt();

            switch (choice) {
                case 1:
                    adminManageStock();
                    break;
                case 2:
                    System.out.println("Show manage food menu panel");
                    // Add logic for food management here
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;  // Exit admin dashboard and go back to role selection
                default:
                    System.out.println("Invalid choice. Returning to dashboard.");
            }
        } while (true); // Keep the dashboard running until the user logs out
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

            // Reset flags to return to previous state
            showLowStock = false; 
            showAllStocks = false;
            showOneStocks = false;
        } else if (showAllStocks) {
            // Show the full list of ingredients
            System.out.println("\n--- Manage Stock ---");
            showAllStocks();
            
            // After showing all stocks, ask the user to select another action
            showLowStock = false;  // Make sure the other flags are reset
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
                showLowStock = false;  // Show all ingredients
                showAllStocks = true;  // Flag to show all ingredients
                break;
            case 2:
                showLowStock = true;   // Show low stock ingredients
                showAllStocks = false; // Reset the other flags
                showOneStocks = false;
                break;
            case 3:
                updateStocks();   // Implement stock update logic
                break;
            case 4:
                updateReorderPoints();   // Implement re-order points update logic
                break;
            case 5:
                showOneStocks = true;    // Show one ingredient's full information
                showLowStock = false;    // Reset flags
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
            ingredient.display();
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

    private static void userDashboard() {
        // Implement user dashboard logic here (for example, displaying user orders, etc.)
        System.out.println("Welcome to the User Dashboard!");
    }
}
