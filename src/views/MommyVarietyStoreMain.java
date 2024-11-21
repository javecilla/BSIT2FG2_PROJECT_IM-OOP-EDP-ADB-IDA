//eto
package views;
import controllers.CartController;
import models.User;
import models.Ingredient;
import models.Category;
import controllers.UserController;
import controllers.IngredientController;
import controllers.CategoryController;
import controllers.FoodController;
import helpers.Response;
import enums.UserRoles;
import java.util.List;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import models.Cart;
import models.CartItem;
import models.Food;

public class MommyVarietyStoreMain {
    User userHolder;
    String userPassword = "admin";
    
    String userRole = "";
    String quantityType = "add";
    
    protected static final UserController LOGIN_CONTROLLER = new UserController();
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    protected static final CategoryController CATEGORY_CONTROLLER = new CategoryController();
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    protected static final CartController CART_CONTROLLER = new CartController();
    
     static int categoriesCounter = 0;
     static int foodsCounter = 0;
     int foodCounter = 0;
    
    boolean startPanel = true;
    boolean loggedIn = false;
    
    LocalDate date = LocalDate.now();
    int userPayment;
    
    public static void main(String[] args) {
        MommyVarietyStoreMain store = new MommyVarietyStoreMain();
        store.home();
    }
    
    void home(){
        while(true){
            Object[] options = {"USER", "ADMIN"};
        
            // Display JOptionPane with custom buttons
            int choice = JOptionPane.showOptionDialog(null, "=====================WELCOME TO MOMMY'S VARIETY STORE======================\n", "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            // Handle the button clicks
            switch (choice) {
                case 0:
                    //userType = "Client";
                    userRole = UserRoles.CLIENT.name();
                    // Code for Browse Menu/Order Now
                    logInForm();
                    break;
                case 1:
                    userRole = UserRoles.ADMIN.name();
                    //userType = "Admin";
                    logInForm();
                    break;
                default:
                    // Code if no button is clicked or the dialog is closed
                    systemExit();
                    break;
            }
        }
    }
    
    void clientDashboard(User user){
        Object[] options = {"ORDER NOW", "PROFILE","LOG OUT"};
        
            // Display JOptionPane with custom buttons
            int choice = JOptionPane.showOptionDialog(null, "=====================WELCOME TO MOMMY'S VARIETY STORE======================\nWelcome back!, " + user.getFullName(), "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch(choice){
                case 0:
                    foodCategoryChoice();
                    break;
                case 1:
                    showProfile(user);
                    clientDashboard(user);
                    break;
                case 2:
                    home();
                    break;
                default:
                    systemExit();
                    break;
            }
    }
    
    void foodCategoryChoice(){
        showCategoryMenu();
    }
    
    void systemExit(){
        int exitIndex = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit confirmation", JOptionPane.YES_NO_OPTION);
            if(exitIndex == JOptionPane.YES_OPTION){
                System.exit(0);
            }else{
                if(exitIndex == JOptionPane.CLOSED_OPTION){
                    System.exit(0);
                }
            return;
            }
    }
    
    void payment(){
        /*        if(totalFoodCount <= 0){
        JOptionPane.showMessageDialog(null, "No food/s to check out!", "MOMMY'S VARIETY STORE",JOptionPane.INFORMATION_MESSAGE);
        return;
        }
        while(true){
        try{
        String input = JOptionPane.showInputDialog(null, "Payable Amount:" + totalFoodPrice + "\nEnter Payment: ", "MOMMY'S VARIETY STORE",JOptionPane.PLAIN_MESSAGE);
        if(input == null){
        break;
        }else{
        userPayment = Integer.parseInt(input);
        if(userPayment < totalFoodPrice){
        JOptionPane.showMessageDialog(null, "Not enough balance to check out this food/s!!");
        return;
        }else{
        checkOut();
        return;
        }
        }
        }catch(NumberFormatException er){
        JOptionPane.showMessageDialog(null, "Error!", "ERROR!", JOptionPane.ERROR_MESSAGE);
        continue;
        }
        }*/
    }

    void checkOut() {

    }
    
    void logInForm(){
        boolean loginSuccess = false;
        while(!loginSuccess){
        String userNameHolder = JOptionPane.showInputDialog(null,"Enter UserName: ", "MOMMY'S VARIETY STORE",JOptionPane.INFORMATION_MESSAGE);
        if(userNameHolder ==  null){
            home();
        }
        String userPasswordHolder = JOptionPane.showInputDialog(null, "Enter Password: ", "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        if(userPasswordHolder == null){
            JOptionPane.showMessageDialog(null, "NO INPUTTED PASSWORD", "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            logInForm();
        }
        
        Response<User> loginResponse = LOGIN_CONTROLLER.loginUser(userNameHolder, userPasswordHolder, userRole);
        
        if(loginResponse.isSuccess()){
            
            JOptionPane.showMessageDialog(null, loginResponse.getMessage());
            User user = loginResponse.getData();
            userHolder = user;
            
            loggedIn = true;
            loginSuccess = true;
            userHolder = user;
            if (UserRoles.ADMIN.name().equals(userRole)) {
                adminSection(user);
            } else if (UserRoles.CLIENT.name().equals(userRole)){
                clientDashboard(user);
            }
            loggedIn = false; // Logout after navigating the dashboard to return to the role selection screen
            startPanel = true;
            
            
            
            return;
        }else{
            JOptionPane.showMessageDialog(null, "INVALID CREDENTIALS!","ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            continue;
        }
        }
    }
    
    void checkOutDone(){
        
    }
    
    void adminSection(User user){
        int choice;
        boolean showLowStock = false; // Flag to track if we're showing low stock
        boolean showAllStocks = false; // Flag to track if we're showing all stocks
        boolean showOneStocks = false; // Flag to track if we're showing one item's full info
        while(true){
         Object[] options = {"MANAGE STOCKS", "PROFILE","LOG OUT"};
            int adminChoice = JOptionPane.showOptionDialog(null, "===================================WELCOME TO ADMIN SECTION====================================\n" + "Welcome Back!, " + user.getFullName(), "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            
            switch(adminChoice){
                case 0:
                    String input = JOptionPane.showInputDialog(null, "--- Select Action ---\n"
                                                                    + "[1] Show All\n"
                                                                    + "[2] Show All (Low Stocks)\n"
                                                                    + "[3] Update (Stocks)\n"
                                                                    + "[4] Update (Re-order Points)\n"
                                                                    + "[5] Show One (All Information)\n"
                                                                    + "[6] Check One (Reorder or Not)\n"
                                                                    + "[7] Back","MOMMY'S VERIETY STORE", JOptionPane.PLAIN_MESSAGE);
                    if(input == null){
                        break;
                    }
                    switch(input){
                        case "1":
                            showAllStocks();
                            break;
                        case "2":
                            showLowStock();
                            break;
                        case "3":
                            updateStocks();
                            break;
                        case "4":
                            updateReorderPoints();
                            break;
                        case "5":
                            showOneItemInformation();
                            break;
                        case "6":
                            checkForReorder();
                            break;
                        case "7":
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "INVALID INPUT!!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);      
                    }
                    break;
                case 1:
                    showProfile(user);
                    break;
                case 2:
                    home();
                    break;
                default:
                    systemExit();
                    break;
            }
        }
    }
    
    void showLowStock() {
        // Fetch ingredients with low stock
        Response<List<Ingredient>> ingredientsWithLowStocksResponse = INGREDIENT_CONTROLLER.getAllIngredientsLowStocks();
        if (ingredientsWithLowStocksResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsWithLowStocksResponse.getData();
            //displayIngredientItems(ingredients);
            JOptionPane.showMessageDialog(null, displayIngredientItems(ingredients),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, ingredientsWithLowStocksResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        }
    }

      void showOneItemInformation() {
        while(true){
          try{
              //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientIDInput == null){
                return;
            }

            Response<Ingredient> ingredientsResponse = INGREDIENT_CONTROLLER.getIngredientById(Integer.parseInt(ingredientIDInput));
            if (ingredientsResponse.isSuccess()) {
                Ingredient ingredient = ingredientsResponse.getData();
                //ingredient.display();
                JOptionPane.showMessageDialog(null, ingredient.display(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //System.out.println("Error retrieving ingredient: " + ingredientsResponse.getMessage());
                JOptionPane.showMessageDialog(null,"Error retrieving ingredient: " + ingredientsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
            
          }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
          }
        }
    }

      void showAllStocks() {
        // Fetch all ingredients (not just low stock)
        Response<List<Ingredient>> ingredientsResponse = INGREDIENT_CONTROLLER.getAllIngredients();
        if (ingredientsResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsResponse.getData();
            JOptionPane.showMessageDialog(null, displayIngredientItems(ingredients),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, ingredientsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
      void checkForReorder() {
        //System.out.print("Enter ingredient id: ");while(true){
          while(true){
            try{
                String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
                if(ingredientIDInput == null){
                    return;
                }
        
                    Response<String> reorderCheckResponse = INGREDIENT_CONTROLLER.checkReorderNeed(Integer.parseInt(ingredientIDInput));
                    if(reorderCheckResponse.isSuccess()) {
                    //System.out.println(reorderCheckResponse.getMessage());
                    JOptionPane.showMessageDialog(null, reorderCheckResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //System.out.println("Error checking ingredient: " + reorderCheckResponse.getMessage());
                    JOptionPane.showMessageDialog(null,"Error checking ingredient: " + reorderCheckResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                }
                    
            }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
          }
    }

      void updateStocks(){
        while(true){
          try{
            //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientIDInput == null){
                return;
            }

            //System.out.print("Enter quantity: ");
            String ingredientQuantityInput = JOptionPane.showInputDialog(null,"Enter quantity: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientQuantityInput == null){
                return;
            }
        
            Response<Ingredient> updateStocksResponse = INGREDIENT_CONTROLLER.updateQuantity(
                Integer.parseInt(ingredientIDInput),
                Integer.parseInt(ingredientQuantityInput)
            );
            if(updateStocksResponse.isSuccess()) {
                //System.out.println(updateStocksResponse.getMessage());
                JOptionPane.showMessageDialog(null, updateStocksResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //System.out.println("Error updating ingredient: " + updateStocksResponse.getMessage());
                JOptionPane.showMessageDialog(null, "Error updating ingredient: " + updateStocksResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
            }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
          }
        
    }
    
      void updateReorderPoints() {
        while(true){
          try{
            //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientIDInput == null){
                return;
            }

            // System.out.print("Enter reorder points: ");
            String ingredientReorderPointsInput = JOptionPane.showInputDialog(null,"Enter reorder points: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
        
            if(ingredientReorderPointsInput == null){
                return;
            }
        
            Response<Ingredient> updateReorderPointsResponse = INGREDIENT_CONTROLLER.updateReorderPoints(
                Integer.parseInt(ingredientIDInput),
                Integer.parseInt(ingredientReorderPointsInput)
            );
            if(updateReorderPointsResponse.isSuccess()) {
                //System.out.println(updateReorderPointsResponse.getMessage());
                JOptionPane.showMessageDialog(null, updateReorderPointsResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //System.out.println("Error updating ingredient: " + updateReorderPointsResponse.getMessage());
                JOptionPane.showMessageDialog(null, "Error updating ingredient: " + updateReorderPointsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
          }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
          }
    }

    public String displayIngredientItems(List<Ingredient> ingredients) {
        StringBuilder output = new StringBuilder();
    
        // Header
        output.append("Ingredient ID\t");
        output.append("Name\t");
        output.append("Current Stock\t");
        output.append("Reorder Point\n");
    
        // Rows
        for (Ingredient ingredient : ingredients) {
            output.append(String.format("%-10s %-30s $%-10s %-20s%n", 
                ingredient.getIngredientId(), 
                ingredient.getIngredientName(),
                ingredient.getQuantity(), 
                ingredient.getReorderPoint()));
        }
    
        // Footer
        output.append("Total Records: ").append(ingredients.size());
    
        return output.toString();
    }
    
    void showProfile(User user) {
        /*        System.out.println("Full Name: " + user.getFullName());
        System.out.println("Address: " + user.getFullAddress());*/
        JOptionPane.showMessageDialog(null, "YOUR PROFILE:\n\nFull Name: " + user.getFullName() + "\nAddress: " + user.getFullAddress(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
    }
    
    void showCart() {
            //System.out.println("\n--- Your Cart ---");
            Object[] options = {"OK", "EDIT QUANTITY","REMOVE ITEM" ,"CHECK OUT"};
            String cartHolder = "\n-------------------------------------------- Your Cart ---------------------------------------------";
            Response<Cart> cartResponse = CART_CONTROLLER.viewCart();

            if (cartResponse.isSuccess()) {
                Cart cart = cartResponse.getData();

                if (cart.getItems().isEmpty()) {
                    //System.out.println("Your cart is empty.");
                    JOptionPane.showMessageDialog(null, "Your cart is empty.", "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                    foodCategoryChoice();
                } else {
                    int itemNumber = 1;
                    //System.out.printf("%-5s %-20s %-10s %-10s %-10s\n", "No.", "Food Name", "Price", "Qty", "Total");
                    cartHolder += "\n" +"No.    "+ "Food Name                               "+"Price                      "+ "Qty                   "+ "Total\n";
                    //System.out.println("-----------------------------------------------------------");
                    cartHolder += "--------------------------------------------------------------------------------------------------------";

                for (CartItem item : cart.getItems()) {
                    double totalPrice = item.getTotalPrice();
                    
                    cartHolder += "\n" + itemNumber++ +"    "+ item.getFoodName() +"                        "+ item.getFoodPrice() +"                   "+ item.getQuantity() +"                  "+ totalPrice;
                }

                //System.out.println("-----------------------------------------------------------");
                cartHolder += "\n----------------------------------------------------------------------------------------------------------\n";
                //System.out.printf("%-35s %-10.2f\n", "Total Amount:", cart.getTotalAmount());
                cartHolder += "Total Amount: Php "+ cart.getTotalAmount() + "";
                //JOptionPane.showMessageDialog(null, cartHolder, "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
                int choice = JOptionPane.showOptionDialog(null, cartHolder, "MOMMY'S VARIETY STORE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                
                if(choice == 0){
                    foodCategoryChoice();
                }else if(choice == 1){
                    //editQuantity
                    quantityType = "edit";
                    foodCategoryChoice();
                }else if(choice == 2){
                    quantityType = "remove";
                    foodCategoryChoice();
                }else if(choice == 3){
                    //checkout
                }else{
                    foodCategoryChoice();
                }
                return ;
            }
        } else {
            //System.out.println("Error: " + cartResponse.getMessage());
            JOptionPane.showMessageDialog(null, "Error: " + cartResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            return ; // Exit if there's an issue fetching the cart
        }

        // Provide options to the user
    }

    
     void showFoodMenu(int categoryId) {
        try{
        do {
            String holder = displayFoodListsByCategory(categoryId); 
            //System.out.print("Enter your choice: ");
            String input = JOptionPane.showInputDialog(null, holder + "\nEnter your choice: ", "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
            
            if(input == null){
                foodCategoryChoice();
                return;
            }
            
            int choice = Integer.parseInt(input);

            // Check if the choice is valid for categories (1 to foodsCounter)
            if (choice > 0 && choice <= foodCounter) {
                Response<Food> foodResponse = FOOD_CONTROLLER.getFoodById(choice);
                if (foodResponse.isSuccess()) {
                    Food selectedFood = foodResponse.getData();
                    promptQuantityOrder(selectedFood.getFoodId(), selectedFood.getFoodName(), selectedFood.getPrice());
                } else {
                    //System.out.println("Error: " + foodResponse.getMessage());
                    JOptionPane.showMessageDialog(null, "Error: " + foodResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                }
            } else if (choice == (foodCounter + 1)) {
                showCart();
                return;  // Go back to the category menu
            }else if(choice == (foodCounter + 2)){
                clientDashboard(userHolder);
                return;
            }else if(choice == (foodCounter + 3)) {
                foodCategoryChoice();
                return;
            }else {
                //System.out.println("Invalid choice. Please select again.");
                JOptionPane.showMessageDialog(null, "Invalid choice. Please select again.", "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
        } while (true);
        }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er,"ERROR!", JOptionPane.ERROR_MESSAGE);
            showFoodMenu(categoryId);
        }
    }
    
     void promptQuantityOrder(int foodId, String foodName, double foodPrice) {
        int quantity = 1;
        try{
        do {
            if(quantityType.equals("add") || quantityType.equals("edit")){
                String clientDashboard = "\n------------- Client Dashboard -------------\n"
                                    + "Food Name: " + foodName + "\n"
                                    + "Food Price: " + foodPrice + "\n"
                                    + "------------------------------\n"
                                    + "[0] Back\n\n"
                                    + "Enter quantity: ";
            
            String input = JOptionPane.showInputDialog(null, clientDashboard, "MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);

            if(input == null){
                return;
            }
            
            quantity = Integer.parseInt(input);
            }

            if (quantity > 0) {
                if(quantityType.equals("add")){
                        Response<Cart> addToCartResponse = CART_CONTROLLER.addToCart(foodId, foodName, foodPrice, quantity);
                    if (addToCartResponse.isSuccess()) {
                        //System.out.println("Item added to cart successfully.");
                        JOptionPane.showMessageDialog(null, "Item added to cart successfully.","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to view your cart?", "MOMMY'S VARIETY STORE", JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION){
                            showCart();
                        }else{
                            continue;
                        }
                    } else {
                        //System.out.println("Error: " + addToCartResponse.getMessage());
                        JOptionPane.showMessageDialog(null, "Error: " + addToCartResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(quantityType.equals("edit")){
                    Response<Cart> updateQuantityResponse = CART_CONTROLLER.updateQuantity(foodId, quantity);
                    if (updateQuantityResponse.isSuccess()) {
                        //System.out.println("Item added to cart successfully.");
                        JOptionPane.showMessageDialog(null, "Item Update quanity successfully.","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                        quantityType = "add";
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to view your cart?", "MOMMY'S VARIETY STORE", JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION){
                            showCart();
                        }else{
                            
                            continue;
                        }
                    } else {
                        //System.out.println("Error: " + addToCartResponse.getMessage());
                        JOptionPane.showMessageDialog(null, "Error: " + updateQuantityResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(quantityType.equals("remove")){
                    Response<Cart> removeFromCartResponse = CART_CONTROLLER.removeFromCart(foodId);
                    if (removeFromCartResponse.isSuccess()) {
                        //System.out.println("Item added to cart successfully.");
                        JOptionPane.showMessageDialog(null, "Item removed successfully.","MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
                        quantityType = "add";
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to view your cart?", "MOMMY'S VARIETY STORE", JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION){
                            showCart();
                        }else{
                            continue;
                        }
                    } else {
                        //System.out.println("Error: " + addToCartResponse.getMessage());
                        JOptionPane.showMessageDialog(null, "Error: " + removeFromCartResponse.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (quantity == 0) { 
                return;  // Go back to the food menu
            } else {
                //System.out.println("Invalid choice. Please select again.");
                JOptionPane.showMessageDialog(null, "Invalid choice. Please select again.","MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                continue;
            }
        
        } while (true);
        }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er, "ERROR", JOptionPane.ERROR_MESSAGE);
            promptQuantityOrder(foodId,foodName,foodPrice);
        }
    }       
    
     String displayFoodListsByCategory(int categoryId) {
        foodsCounter = 0;
        
        Response<List<Food>> foodsResponse = FOOD_CONTROLLER.getFoodsByCategory(categoryId);
        if (foodsResponse.isSuccess()) {
            List<Food> foods = foodsResponse.getData();
            String selectFood = "------------------- Select Food -------------------\n";
            //System.out.println("\n--- Select Food ---");
            for (Food food : foods) {
                //System.out.println("[" + food.getFoodId() + "] \t" + food.getFoodName() + "\t\t" + food.getPrice());
                selectFood += "[" + food.getFoodId() + "]     " + food.getFoodName() + "     " + food.getPrice()+"\n";
                foodsCounter++;
                foodCounter = food.getFoodId();
            }
 
            //System.out.println("[" + (foods.size() + 1) + "] Back\n");
            selectFood += "[" + (foodCounter + 1) + "]    Cart\n";
            selectFood += "[" + (foodCounter + 2) + "]    Dashboard\n";
            selectFood += "[" + (foodCounter + 3) + "]    Back\n";
            
            return selectFood;
        } else {
            JOptionPane.showMessageDialog(null, "Error: " + foodsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }
    
     void showCategoryMenu() {
        int choice;
        try{
        do {
            String holder = displayCategories(); 
            //System.out.print("Enter your choice: ");
            String input = JOptionPane.showInputDialog(null, holder + "\nEnter your choice: ","MOMMY'S VARIETY STORE", JOptionPane.PLAIN_MESSAGE);
            //choice = SCANNER.nextInt();
            
            if(input == null){
                clientDashboard(userHolder);
            }
            choice = Integer.parseInt(input);

            // Check if the choice is valid for categories (1 to categoriesCounter)
            if (choice > 0 && choice <= categoriesCounter) {
                showFoodMenu(choice);
            } else if (choice == (categoriesCounter + 1)) {
                showCart();
                return;  // Go back to the client dashboard
            }else if (choice == (categoriesCounter + 2)) { 
                clientDashboard(userHolder);
                return;  // Go back to the client dashboard
            }else {
                //System.out.println("Invalid choice. Please select again.");
                JOptionPane.showMessageDialog(null,"Invalid choice. Please select again." , "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
        } while (true);
        }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            showCategoryMenu();
        }
    }
    
     String displayCategories() {
        categoriesCounter = 0;  
        
        Response<List<Category>> categoriesResponse = CATEGORY_CONTROLLER.getAllCategories();
        if (categoriesResponse.isSuccess()) {
            List<Category> categories = categoriesResponse.getData();
            String selectCategory = "------------------- Select Category -------------------\n";
            //System.out.println("\n--- Select Category ---");
            for (Category category : categories) {
                //System.out.println("[" + category.getCategoryId() + "] " + category.getCategoryName());
                selectCategory += "[" + category.getCategoryId() + "]    " + category.getCategoryName() + "\n";
                categoriesCounter++;
            }
 
            //System.out.println("[" + (categories.size() + 1) + "] Back\n");
            selectCategory += "[" + (categories.size() + 1) + "]    Cart\n";
            selectCategory += "[" + (categories.size() + 2) + "]    Dashboard\n";
            return selectCategory;
        } else {
            //System.out.println("Error: " + categoriesResponse.getMessage());
            JOptionPane.showMessageDialog(null, "Error: " + categoriesResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }
}
